package com.increff.teamer.flowApi;

import com.increff.teamer.dao.*;
import com.increff.teamer.model.constant.EventCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.form.AddExpenseForm;
import com.increff.teamer.model.form.UpdateClaimApprovalForm;
import com.increff.teamer.pojo.*;
import com.increff.teamer.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

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


    @Transactional
    public void addClaim(Long eventId) throws Exception {
        EventCategory eventCategory = eventDao.findByEventId(eventId).getEventCategory();
        ClaimPojo pendingClaimPojo = claimDao.findByEventIdAndClaimStatus(eventId,RequestStatus.PENDING);
        ClaimPojo approvedClaimPojo = claimDao.findByEventIdAndClaimStatus(eventId,RequestStatus.APPROVED);
        if(pendingClaimPojo == null && approvedClaimPojo == null){
            ClaimPojo claimPojo = new ClaimPojo(eventId,
                    RequestStatus.PENDING,
                    0L,
                    userService.getCurrentUser().getUserId());
            claimDao.save(claimPojo);
            //Adding Claim approval
            EventCategoryApprovalSequencePojo eventCategoryApprovalSequencePojo = eventCategoryApprovalSequenceDao.findByEventCategoryAndApprovalStage(eventCategory,1L);
            ClaimApprovalPojo claimApprovalPojo = new ClaimApprovalPojo(claimPojo.getClaimId(),
                    RequestStatus.PENDING
                    ,eventCategoryApprovalSequencePojo.getApprovalRequiredBy()
                    ,eventCategoryApprovalSequencePojo.getApprovalStage(),
                    eventCategory);
            claimApprovalDao.save(claimApprovalPojo);
        }else {
            throw new Exception("Already have one active Claim");
        }

    }

    @Transactional
    public void addExpense(AddExpenseForm addExpenseForm) throws Exception {
        Optional<ClaimPojo> claimPojoOptional = claimDao.findById(addExpenseForm.getClaimId());
        if(claimPojoOptional.isPresent()){
            ClaimPojo claimPojo = claimPojoOptional.get();
            if(claimPojo.getClaimStatus() == RequestStatus.PENDING){
                ExpenseDetailsPojo expenseDetailsPojo = new ExpenseDetailsPojo(addExpenseForm.getClaimId(),
                        addExpenseForm.getExpenseAmount(),
                        addExpenseForm.getInvoiceNo(),
                        addExpenseForm.getInvoiceDate(),
                        addExpenseForm.getDescription(),
                        addExpenseForm.getAttachment());
                expenseDetailsPojo = expenseDetailsDao.save(expenseDetailsPojo);
//                ClaimPojo claimPojo = claimPojoOptional.get();
                claimPojo.setTotalExpenseAmount(claimPojo.getTotalExpenseAmount() + expenseDetailsPojo.getExpenseAmount());
                claimDao.save(claimPojo);
            }else {
                throw new Exception("Don't Find any Active Claim");
            }
        }else {
            throw new Exception("Don't Find any Claim");
        }
    }

//    public void addFileExpense(NewAddExpenseForm newAddExpenseForm) throws IOException {
//
//        NewExpensePojo newExpensePojo = new NewExpensePojo();
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
//            newExpensePojo.setAttachment(attachment.getBytes());
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



    @Transactional
    public void removeExpense(Long expenseId) throws Exception {
        Optional<ExpenseDetailsPojo> expenseDetailsPojoOptional = expenseDetailsDao.findById(expenseId);
        if(expenseDetailsPojoOptional.isPresent()){
            ExpenseDetailsPojo expenseDetailsPojo = expenseDetailsPojoOptional.get();
            Optional<ClaimPojo> claimPojoOptional  = claimDao.findById(expenseDetailsPojo.getClaimId());
            ClaimPojo claimPojo = claimPojoOptional.get();
            claimPojo.setTotalExpenseAmount(claimPojo.getTotalExpenseAmount()-expenseDetailsPojo.getExpenseAmount());
            claimDao.save(claimPojo);
            expenseDetailsDao.delete(expenseDetailsPojo);
        }else {
            throw new Exception("Expense is Not Present");
        }
    }

    @Transactional
    public void updateExpense(ExpenseDetailsPojo expenseDetailsPojo) throws Exception {
        ExpenseDetailsPojo existingExpense = expenseDetailsDao.findById(expenseDetailsPojo.getExpenseId())
                .orElseThrow(() -> new Exception("Expense not found"));

        ClaimPojo claimPojo = claimDao.findById(existingExpense.getClaimId()).orElse(null);
        if (claimPojo != null) {
            if(claimPojo.getClaimStatus() == RequestStatus.PENDING){
                Long totalExpense = claimPojo.getTotalExpenseAmount() - existingExpense.getExpenseAmount() + expenseDetailsPojo.getExpenseAmount();
                claimPojo.setTotalExpenseAmount(totalExpense);
                claimDao.save(claimPojo);
                existingExpense.setExpenseAmount(expenseDetailsPojo.getExpenseAmount());
                existingExpense.setInvoiceDate(expenseDetailsPojo.getInvoiceDate());
                existingExpense.setInvoiceNo(expenseDetailsPojo.getInvoiceNo());
                existingExpense.setDescription(expenseDetailsPojo.getDescription());
                existingExpense.setAttachment(expenseDetailsPojo.getAttachment());

                expenseDetailsDao.save(existingExpense);
            }else {
                throw new Exception("Claim is Not Active");
            }

        }else {
            throw new Exception("Don't Find any Claim");
        }


    }
    public ExpenseDetailsPojo getExpenseById(Long expenseId){
        Optional<ExpenseDetailsPojo> expenseDetailsPojoOptional = expenseDetailsDao.findById(expenseId);
        return expenseDetailsPojoOptional.orElse(null);
    }

//    public NewExpensePojo getFileExpenseById(Long expenseId){
//        Optional<NewExpensePojo> newExpensePojo = newExpenseDao.findById(expenseId);
//        return newExpensePojo.orElse(null);
//    }

    @Transactional
    public void updateClaimApproval(UpdateClaimApprovalForm updateClaimApprovalForm){
        Optional<ClaimApprovalPojo> claimApprovalPojoOptional = claimApprovalDao.findById(updateClaimApprovalForm.getClaimApprovalId());
        if(claimApprovalPojoOptional.isPresent()){
            ClaimApprovalPojo claimApprovalPojo = claimApprovalPojoOptional.get();
            claimApprovalPojo.setApprovalStatus(updateClaimApprovalForm.getApprovalStatus());
            claimApprovalPojo.setRemarks(updateClaimApprovalForm.getRemarks());
            claimApprovalPojo = claimApprovalDao.save(claimApprovalPojo);
            if(updateClaimApprovalForm.getApprovalStatus() == RequestStatus.APPROVED){
                EventCategoryApprovalSequencePojo eventCategoryApprovalSequencePojo = eventCategoryApprovalSequenceDao.findByEventCategoryAndApprovalStage(claimApprovalPojo.getEventCategory(),claimApprovalPojo.getApprovalStage()+1);
                if(eventCategoryApprovalSequencePojo != null){
                    ClaimApprovalPojo newClaimApprovalPojo = new ClaimApprovalPojo(claimApprovalPojo.getClaimId(),
                            RequestStatus.PENDING,
                            eventCategoryApprovalSequencePojo.getApprovalRequiredBy(),
                            eventCategoryApprovalSequencePojo.getApprovalStage(),
                            claimApprovalPojo.getEventCategory());
                    claimApprovalDao.save(newClaimApprovalPojo);
                }else{
                    updateClaim(claimApprovalPojo.getClaimId(),RequestStatus.APPROVED);
                }
            }else {
                updateClaim(claimApprovalPojo.getClaimId(),RequestStatus.REJECTED);
            }
        }
    }

    public List<ClaimPojo> getEventClaims(Long eventId){
        return claimDao.findAllByEventId(eventId);
    }

    public List<ExpenseDetailsPojo> getClaimExpenses(Long claimId){
        return expenseDetailsDao.findAllByClaimId(claimId);
    }

    public List<ClaimApprovalPojo> getAllPendingClaimsApprovalForUser() throws Exception {
        return claimApprovalDao.findAllByApprovalRequiredByAndApprovalStatus(userService.getCurrentUser().getUserId(),
                RequestStatus.PENDING);
    }

    public List<ClaimApprovalPojo> getAllClaimApprovals(Long claimId) throws Exception {
        return claimApprovalDao.findAllByClaimId(claimId);
    }

    public void updateClaim(Long claimId,RequestStatus newClaimStatus){
        Optional<ClaimPojo> claimPojoOptional= claimDao.findById(claimId);
        if(claimPojoOptional.isPresent()){
            ClaimPojo claimPojo = claimPojoOptional.get();
            claimPojo.setClaimStatus(newClaimStatus);
            claimDao.save(claimPojo);
        }
    }

    public EventPojo getEventByEventId(Long eventId){
        Optional<EventPojo> eventPojo = eventDao.findById(eventId);
        return eventPojo.orElse(null);
    }

    public ClaimPojo getClaimById(Long claimId){
        Optional<ClaimPojo> claimPojo = claimDao.findById(claimId);
        return claimPojo.orElse(null);
    }
    private Long totalExpense(List<AddExpenseForm> expenseFormList){
        return expenseFormList.stream()
                .map(AddExpenseForm::getExpenseAmount)
                .reduce(0L, Long::sum);
    }
}
