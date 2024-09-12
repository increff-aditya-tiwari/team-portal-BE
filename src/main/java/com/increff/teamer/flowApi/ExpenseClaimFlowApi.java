package com.increff.teamer.flowApi;

import com.increff.teamer.api.*;
import com.increff.teamer.dao.*;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.form.AddExpenseForm;
import com.increff.teamer.model.form.NewAddExpenseForm;
import com.increff.teamer.model.form.UpdateClaimApprovalForm;
import com.increff.teamer.pojo.*;
import com.increff.teamer.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseClaimFlowApi {

    @Autowired
    EventDao eventDao;
    @Autowired
    UserFlowApi userService;
    @Autowired
    ClaimDao claimDao;
    @Autowired
    ConvertUtil convertUtil;
    @Autowired
    ExpenseDetailsDao expenseDetailsDao;
    @Autowired
    EventCategoryApprovalSequenceDao eventCategoryApprovalSequenceDao;
    @Autowired
    ClaimApprovalDao claimApprovalDao;

    @Autowired
    NewExpenseDao newExpenseDao;
    @Autowired
    EventApi eventApi;
    @Autowired
    ClaimApi claimApi;
    @Autowired
    UserApi userApi;
    @Autowired
    EventCategoryApprovalSequenceApi eventCategoryApprovalSequenceApi;
    @Autowired
    ClaimApprovalApi claimApprovalApi;
    @Autowired
    ExpenseApi expenseApi;


    @Transactional
    public ClaimPojo addClaim(Long eventId) throws CommonApiException {
        EventPojo eventPojo = eventApi.isValidEvent(eventId);
        claimApi.validateClaimToAdd(eventId);
        ClaimPojo claimPojo = new ClaimPojo(eventId,
                RequestStatus.PENDING,
                0L,
                userApi.getCurrentUser().getUserId());
        claimPojo = claimApi.saveClaim(claimPojo);

        //Adding Claim approval

        addClaimApproval(eventPojo.getEventCategory(),claimPojo.getClaimId(),1L);
        return claimPojo;
    }

    public void addClaimApproval(EventCategory eventCategory, Long claimId, Long approvalStage) throws CommonApiException{
        EventCategoryApprovalSequencePojo eventCategoryApprovalSequencePojo = eventCategoryApprovalSequenceApi.getApprovalDetail(eventCategory,approvalStage);
        if(eventCategoryApprovalSequencePojo != null){
            ClaimApprovalPojo claimApprovalPojo = new ClaimApprovalPojo(claimId,
                    RequestStatus.PENDING
                    ,eventCategoryApprovalSequencePojo.getApprovalRequiredBy()
                    ,eventCategoryApprovalSequencePojo.getApprovalStage(),
                    eventCategory);
            claimApprovalApi.setClaimApproval(claimApprovalPojo);
        }
    }



    @Transactional
    public ExpensePojo addNewExpense(NewAddExpenseForm newAddExpenseForm) throws CommonApiException, IOException {
        ExpensePojo expensePojo = convertUtil.convertDataToPojo(newAddExpenseForm, ExpensePojo.class);
        // Convert MultipartFile to byte[] and set it
        System.out.println("this is new expense " + expensePojo.getInvoiceNo() + " and claim " + expensePojo.getClaimId() + " and ex id " + expensePojo.getExpenseId());
        expensePojo.setExpenseId(null);
        MultipartFile attachment = newAddExpenseForm.getAttachmentDetail();
        expensePojo.setAttachmentDetail(attachment.getBytes());
        expensePojo.setAttachmentFileName(attachment.getOriginalFilename());
        expensePojo = expenseApi.setExpense(expensePojo);

        updateClaimExpenseAmount(expensePojo.getExpenseAmount(), expensePojo.getClaimId());

        return expensePojo;
    }



    public void updateClaimExpenseAmount(Long expenseAmountToAdd,Long claimId) throws CommonApiException{
        ClaimPojo claimPojo = claimApi.isValidClaim(claimId);
        claimPojo.setTotalExpenseAmount(claimPojo.getTotalExpenseAmount() + expenseAmountToAdd);
        claimApi.saveClaim(claimPojo);
    }

    @Transactional
    public void removeExpense(Long expenseId) throws CommonApiException {
        ExpensePojo expensePojo = expenseApi.isValidExpense(expenseId);
        expenseApi.removeExpense(expensePojo);
        expensePojo.setExpenseAmount(expensePojo.getExpenseAmount()*(-1L));
        updateClaimExpenseAmount(expensePojo.getExpenseAmount(), expensePojo.getClaimId());
    }

    public ExpensePojo getExpenseById(Long expenseId) throws CommonApiException{
        return expenseApi.isValidExpense(expenseId);
    }

    @Transactional
    public ExpensePojo updateExpense(ExpensePojo expensePojo) throws CommonApiException{
        ExpensePojo existingExpensePojo = expenseApi.isValidExpense(expensePojo.getExpenseId());
        ClaimPojo claimPojo = claimApi.validClaimToUpdateExpense(expensePojo.getClaimId());
        Long expenseAmountToAdd = expensePojo.getExpenseAmount() - existingExpensePojo.getExpenseAmount();
        updateClaimExpenseAmount(expenseAmountToAdd,claimPojo.getClaimId());
        return expenseApi.updateExpense(expensePojo,existingExpensePojo);
    }



    @Transactional
    public ClaimApprovalPojo updateClaimApproval(UpdateClaimApprovalForm updateClaimApprovalForm) throws CommonApiException{
        ClaimApprovalPojo claimApprovalPojo = validateUpdateClaimApproval(updateClaimApprovalForm);
        System.out.println("this is update re "+updateClaimApprovalForm.getRemarks() + " app " + claimApprovalPojo.getRemarks());
        claimApprovalApi.setClaimApproval(claimApprovalPojo);
        if(updateClaimApprovalForm.getApprovalStatus() == RequestStatus.APPROVED){
            EventCategoryApprovalSequencePojo eventCategoryApprovalSequencePojo = eventCategoryApprovalSequenceDao.findByEventCategoryAndApprovalStage(claimApprovalPojo.getEventCategory(),claimApprovalPojo.getApprovalStage()+1);
            if(eventCategoryApprovalSequencePojo != null){
                addClaimApproval(claimApprovalPojo.getEventCategory(),claimApprovalPojo.getClaimId(),claimApprovalPojo.getApprovalStage()+1);
            }else{
                updateClaimStatus(claimApprovalPojo.getClaimId(),RequestStatus.APPROVED);
            }
        }else {
            updateClaimStatus(claimApprovalPojo.getClaimId(),RequestStatus.REJECTED);
        }
        return claimApprovalPojo;
    }



    public ClaimApprovalPojo validateUpdateClaimApproval(UpdateClaimApprovalForm updateClaimApprovalForm) throws CommonApiException{
        ClaimApprovalPojo claimApprovalPojo = claimApprovalApi.isValidClaimApproval(updateClaimApprovalForm.getClaimApprovalId());
        if(updateClaimApprovalForm.getApprovalStatus() != RequestStatus.APPROVED && updateClaimApprovalForm.getApprovalStatus() != RequestStatus.REJECTED){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"Claim Status is not valid");
        }
        claimApprovalPojo.setApprovalStatus(updateClaimApprovalForm.getApprovalStatus());
        claimApprovalPojo.setRemarks(updateClaimApprovalForm.getRemarks());
        return claimApprovalPojo;
    }

    private void updateClaimStatus(Long claimId,RequestStatus newClaimStatus) throws CommonApiException{
        claimApi.updateClaimStatus(claimId,newClaimStatus);
    }

    public List<ClaimPojo> getEventClaims(Long eventId) throws CommonApiException {
        eventApi.isValidEvent(eventId);
        return claimApi.getEventClaims(eventId);
    }

    public List<ExpensePojo> getClaimExpenses(Long claimId) throws CommonApiException {
        claimApi.isValidClaim(claimId);
        return expenseApi.getClaimExpenses(claimId);
    }

    public List<ClaimApprovalPojo> getAllClaimApprovals(Long claimId) throws CommonApiException {
        claimApi.isValidClaim(claimId);
        return claimApprovalApi.getAllClaimApprovals(claimId);
    }

    public List<ClaimApprovalPojo> getAllPendingClaimsApprovalForUser() throws CommonApiException {
        return claimApprovalApi.getAllPendingClaimsApprovalForUser(userApi.getCurrentUser().getUserId());
    }

    public ClaimPojo getClaimById(Long claimId) throws CommonApiException {
        return claimApi.isValidClaim(claimId);
    }

    public byte[] downloadExpenseAttachment(Long expenseId) throws CommonApiException{
        return expenseApi.getExpenseAttachment(expenseId).getAttachmentDetail();
    }










    private void updateClaim(Long claimId,RequestStatus newClaimStatus){
        Optional<ClaimPojo> claimPojoOptional= claimDao.findById(claimId);
        if(claimPojoOptional.isPresent()){
            ClaimPojo claimPojo = claimPojoOptional.get();
            claimPojo.setClaimStatus(newClaimStatus);
            claimDao.save(claimPojo);
        }
    }



    public ExpensePojo getFileExpenseById(Long expenseId){
        Optional<ExpensePojo> newExpensePojo = newExpenseDao.findById(expenseId);
        return newExpensePojo.orElse(null);
    }





//    public List<ExpenseDetailsPojo> getClaimExpenses(Long claimId){
////        return newExpenseDao.findAllByClaimId(claimId);
//        return expenseDetailsDao.findAllByClaimId(claimId);
//    }











    private Long totalExpense(List<AddExpenseForm> expenseFormList){
        return expenseFormList.stream()
                .map(AddExpenseForm::getExpenseAmount)
                .reduce(0L, Long::sum);
    }


    //    @Transactional
//    public void addExpense(AddExpenseForm addExpenseForm) throws Exception {
//        Optional<ClaimPojo> claimPojoOptional = claimDao.findById(addExpenseForm.getClaimId());
//        if(claimPojoOptional.isPresent()){
//            ClaimPojo claimPojo = claimPojoOptional.get();
//            if(claimPojo.getClaimStatus() == RequestStatus.PENDING){
//                ExpenseDetailsPojo expenseDetailsPojo = new ExpenseDetailsPojo(addExpenseForm.getClaimId(),
//                        addExpenseForm.getExpenseAmount(),
//                        addExpenseForm.getInvoiceNo(),
//                        addExpenseForm.getInvoiceDate(),
//                        addExpenseForm.getDescription(),
//                        addExpenseForm.getAttachment());
//                expenseDetailsPojo = expenseDetailsDao.save(expenseDetailsPojo);
////                ClaimPojo claimPojo = claimPojoOptional.get();
//                claimPojo.setTotalExpenseAmount(claimPojo.getTotalExpenseAmount() + expenseDetailsPojo.getExpenseAmount());
//                claimDao.save(claimPojo);
//            }else {
//                throw new Exception("Don't Find any Active Claim");
//            }
//        }else {
//            throw new Exception("Don't Find any Claim");
//        }
//    }

    //    public void addFileExpense(NewAddExpenseForm newAddExpenseForm) throws IOException {
//
//        ExpensePojo newExpensePojo = new ExpensePojo();
//        newExpensePojo.setClaimId(newAddExpenseForm.getClaimId());
//        newExpensePojo.setExpenseAmount(newAddExpenseForm.getExpenseAmount());
//        newExpensePojo.setInvoiceNo(newAddExpenseForm.getInvoiceNo());
//        newExpensePojo.setInvoiceDate(newAddExpenseForm.getInvoiceDate());
//        newExpensePojo.setDescription(newAddExpenseForm.getDescription());
//        newExpensePojo.setClaimId(newAddExpenseForm.getClaimId());
//
//        // Convert MultipartFile to byte[] and set it
//        MultipartFile attachment = newAddExpenseForm.getAttachment();
//        if (attachment != null && !attachment.isEmpty()) {
//            newExpensePojo.setAttachmentDetail(attachment.getBytes());
//            newExpensePojo.setAttachmentFileName(attachment.getOriginalFilename());  // Save the file name
//        }
//
//        newExpenseDao.save(newExpensePojo);
//        Optional<ClaimPojo> claimPojoOptional = claimDao.findById(newExpensePojo.getClaimId());
//        if(claimPojoOptional.isPresent()){
//            ClaimPojo claimPojo = claimPojoOptional.get();
//            claimPojo.setTotalExpenseAmount(claimPojo.getTotalExpenseAmount() + newAddExpenseForm.getExpenseAmount());
//            claimDao.save(claimPojo);
//        }
//    }

    //    @Transactional
//    public void updateExpense1(ExpenseDetailsPojo expenseDetailsPojo) throws Exception {
//        ExpenseDetailsPojo existingExpense = expenseDetailsDao.findById(expenseDetailsPojo.getExpenseId())
//                .orElseThrow(() -> new Exception("Expense not found"));
//
//        ClaimPojo claimPojo = claimDao.findById(existingExpense.getClaimId()).orElse(null);
//        if (claimPojo != null) {
//            if(claimPojo.getClaimStatus() == RequestStatus.PENDING){
//                Long totalExpense = claimPojo.getTotalExpenseAmount() - existingExpense.getExpenseAmount() + expenseDetailsPojo.getExpenseAmount();
//                claimPojo.setTotalExpenseAmount(totalExpense);
//                claimDao.save(claimPojo);
//                existingExpense.setExpenseAmount(expenseDetailsPojo.getExpenseAmount());
//                existingExpense.setInvoiceDate(expenseDetailsPojo.getInvoiceDate());
//                existingExpense.setInvoiceNo(expenseDetailsPojo.getInvoiceNo());
//                existingExpense.setDescription(expenseDetailsPojo.getDescription());
//                existingExpense.setAttachment(expenseDetailsPojo.getAttachment());
//
//                expenseDetailsDao.save(existingExpense);
//            }else {
//                throw new Exception("Claim is Not Active");
//            }
//
//        }else {
//            throw new Exception("Don't Find any Claim");
//        }
//
//
//    }
}
