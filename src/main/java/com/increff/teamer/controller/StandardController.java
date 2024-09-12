package com.increff.teamer.controller;

import com.increff.teamer.dao.NewExpenseDao;
import com.increff.teamer.dto.NotificationDto;
import com.increff.teamer.flowApi.ExpenseClaimFlowApi;
import com.increff.teamer.flowApi.EventFlowApi;
import com.increff.teamer.flowApi.TeamFlowApi;
import com.increff.teamer.pojo.ExpensePojo;
import com.increff.teamer.util.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        allowCredentials = "true"
)
public class StandardController {

    @Autowired
    TeamFlowApi teamService;
    @Autowired
    EventFlowApi eventFlowApi;
    @Autowired
    ExpenseClaimFlowApi claimService;
    @Autowired
    private WebSocketHandler tradeWebSocketHandler;
    @Autowired
    private NotificationDto notificationDto;
    @Autowired
    private NewExpenseDao newExpenseDao;


//    @PostMapping("/create")
//    public TeamPojo createTeam(@RequestBody CreateTeamForm createUserForm) throws Exception {
//        return teamDto.createTeam(createUserForm);
//    }

//    @GetMapping("/get-teams")
//    public ResponseEntity<?> getAllTeam() throws Exception {
//        try {
//            return ResponseEntity.status(200).body(teamService.getAllTeam());
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }

//    @PostMapping("/map-user-team")
//    public void mapUserTeam(@RequestBody TeamUserMapForm teamUserMapForm) throws Exception{
//        System.out.println("this is form "+teamUserMapForm.toString());
//        try {
//            teamService.teamJoinInvite(teamUserMapForm);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }

//    @PostMapping("/join-team")
//    public ResponseEntity<?> TeamJoinRequest(@RequestBody TeamUserMapForm teamUserMapForm) throws Exception{
//        try {
//            teamService.teamJoinRequest(teamUserMapForm.getTeamId());
//            return ResponseEntity.status(200).body(null);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

//    @GetMapping("/team-join-requests/{teamId}")
//    public ResponseEntity<?> getAllRequestsForTeam(@PathVariable("teamId") Long teamId) throws Exception{
//        try {
//            return ResponseEntity.status(200).body(teamService.getAllOpenRequestsForTeam(teamId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }
//    @GetMapping("/team-join-invites/{teamId}")
//    public ResponseEntity<?> getAllInvitesFromTeam(@PathVariable("teamId") Long teamId) throws Exception{
//        try {
//            return ResponseEntity.status(200).body(teamService.getAllOpenInvitesForTeam(teamId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }


//    @PostMapping("/team-join-request-update")
//    public void updateJoinTeamRequest(@RequestBody UpdateRequestForm updateRequestForm) throws Exception {
//        try {
//            teamService.updateTeamJoinRequestInvite(updateRequestForm);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }
//    @GetMapping("/get-team-members/{teamId}")
//    public ResponseEntity<?> getAllTeamMember(@PathVariable("teamId") Long teamId)throws Exception{
//        try {
//            return ResponseEntity.status(200).body(teamService.getAllTeamMembers(teamId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }

//    @PostMapping("/remove-team-member")
//    public void removeTeamMember(@RequestBody  DeleteTeamMemberForm deleteTeamMemberForm) throws Exception {
////        System.out.println("this is team ID "+deleteTeamMemberForm.getTeamId()+"this is userId"+deleteTeamMemberForm.getUserId());
//
//        try {
//            teamService.removeTeamMember(deleteTeamMemberForm);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }

//    @GetMapping("/get-user-team/{userId}")
//    public ResponseEntity<?> getUserTeamList(@PathVariable("userId") Long userId) throws Exception{
//        try {
//            return ResponseEntity.status(200).body(teamService.getTeamByUserId(userId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }









//    @PostMapping("/create-event")
//    public void createEvent(@RequestBody CreateEventFrom createEventFrom) throws Exception {
////        System.out.println("this is event form in controller"+createEventFrom.toString());
//        try {
//            eventFlowApi.createEvent(createEventFrom);
////            return ResponseEntity.ok("Event Created successfully");
//        }catch (Exception e){
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }


//    @GetMapping("/get-events")
//    public ResponseEntity<?> getAllEvents() throws Exception {
//        try {
//            return ResponseEntity.status(200).body(eventFlowApi.getAllEvent());
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }
//    @PostMapping("/map-event-participant")
//    public void mapEventParticipant(@RequestBody EventParticipantOldForm eventParticipantForm) throws Exception {
//        try {
//            eventFlowApi.mapEventParticipant(eventParticipantForm);
////            return ResponseEntity.ok("Participant added successfully");
//        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//            throw new Exception(e.getMessage());
//        }
//    }

//    @PostMapping("/join-event")
//    public ResponseEntity<?> eventJoinRequest(@RequestBody EventParticipantOldForm eventParticipantOldForm) throws  Exception{
//        try {
//            eventFlowApi.eventJoinRequest(eventParticipantOldForm.getEventId());
//            return ResponseEntity.status(200).body(null);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

//    @GetMapping("/event-join-requests/{eventId}")
//    public ResponseEntity<?> getAllRequestsForEvent(@PathVariable("eventId") Long eventId) throws Exception{
//        try {
//            return ResponseEntity.status(200).body(eventFlowApi.getAllOpenRequestsForEvent(eventId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }

//    @PostMapping("/event-join-request-update")
//    public void updateJoinEventRequest(@RequestBody UpdateRequestForm updateRequestForm) throws Exception {
//        try {
//            eventFlowApi.updateJoinEventRequestInvite(updateRequestForm);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }
//    @GetMapping("/get-event-participants/{eventId}")
//    public ResponseEntity<?> getAllEventParticipants(@PathVariable("eventId") Long eventId)throws Exception{
//        try {
//            return ResponseEntity.status(200).body(eventFlowApi.getAllEventParticipants(eventId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }

//    @PostMapping("/remove-event-participant")
//    public void removeEventParticipant(@RequestBody  DeleteEventParticipantFrom deleteEventParticipantFrom) throws Exception {
//        try {
//            eventFlowApi.removeEventParticipant(deleteEventParticipantFrom);
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }

//    @GetMapping("/get-participant-events/{eventId}")
//    public ResponseEntity<?> getParticipantEventList(@PathVariable("eventId") Long eventId) throws Exception{
//        try {
//            return ResponseEntity.status(200).body(eventFlowApi.getEventByParticipantId(eventId));
//        }catch (Exception e){
////            throw new Exception(e.getMessage());
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-event/{eventId}")
//    public ResponseEntity<?> getEventByEventId(@PathVariable("eventId") Long eventId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getEventByEventId(eventId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }


//    @PostMapping(value = "/add-claim/{eventId}")
//    public ResponseEntity<?> addClaim(@PathVariable("eventId") Long eventId){
//        try {
//            claimService.addClaim(eventId);
//            return ResponseEntity.status(200).body(null);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @PostMapping(value = "/add-expense")
//    public ResponseEntity<?> addExpense(@RequestBody AddExpenseForm addExpenseForm) {
//        try {
//            claimService.addExpense(addExpenseForm);
//            return ResponseEntity.status(200).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @PostMapping(value = "/add-file-expense",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> addFileExpense(@ModelAttribute NewAddExpenseForm newAddExpenseForm) {
//        System.out.println("thi si expense form " + newAddExpenseForm);
//        try {
//            claimService.addFileExpense(newAddExpenseForm);
//            return ResponseEntity.status(200).body("Expense added successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @PostMapping(value = "/remove-expense/{expenseId}")
//    public ResponseEntity<?> removeExpense(@PathVariable("expenseId") Long expenseId) {
//        try {
//            claimService.removeExpense(expenseId);
//            return ResponseEntity.status(200).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-expense/{expenseId}")
//    public ResponseEntity<?> getExpenseById(@PathVariable("expenseId") Long expenseId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getExpenseById(expenseId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-file-expense/{expenseId}")
//    public ResponseEntity<?> getFileExpenseById(@PathVariable("expenseId") Long expenseId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getFileExpenseById(expenseId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @PostMapping("/update-expense")
//    public ResponseEntity<?> updateExpense(@RequestBody ExpenseDetailsPojo expenseDetailsPojo){
//        try {
//            claimService.updateExpense(expenseDetailsPojo);
//            return ResponseEntity.status(200).body(null);
//
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @PostMapping("/claim-approval-update")
//    public ResponseEntity<?> updateClaimApproval(@RequestBody UpdateClaimApprovalForm updateClaimApprovalForm){
//        try {
//            claimService.updateClaimApproval(updateClaimApprovalForm);
//            return ResponseEntity.status(200).body(null);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-event-claims/{eventId}")
//    public ResponseEntity<?> getEventClaims(@PathVariable("eventId") Long eventId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getEventClaims(eventId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-claim-expenses/{claimId}")
//    public ResponseEntity<?> getClaimExpenses(@PathVariable("claimId") Long claimId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getClaimFileExpenses(claimId));
////            return ResponseEntity.status(200).body(claimService.getClaimExpenses(claimId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/download-file/{expenseId}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable("expenseId") Long expenseId) {
//        System.out.println("We are in the download");
//        Optional<ExpensePojo> expenseOpt = newExpenseDao.findById(expenseId);
//        if (expenseOpt.isPresent()) {
//            ExpensePojo expense = expenseOpt.get();
//            byte[] fileContent = expense.getAttachmentDetail();
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
////            headers.setContentDisposition(ContentDisposition.builder("attachment")
////                    .filename(expense.getAttachmentFileName()) // Assumes you store file name
////                    .build());
//            System.out.println("we are sending file");
//            return ResponseEntity.status(HttpStatus.OK).body(fileContent);
////            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @GetMapping("/get-claim-file-expenses/{claimId}")
//    public ResponseEntity<?> getClaimFileExpenses(@PathVariable("claimId") Long claimId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getClaimFileExpenses(claimId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }



//    @GetMapping("/get-claim-approvals/{claimId}")
//    public ResponseEntity<?> getAllClaimApprovals(@PathVariable("claimId") Long claimId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getAllClaimApprovals(claimId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//    @GetMapping("/get-pending-claim-approval")
//    public ResponseEntity<?> getAllPendingClaimApproval(){
//        try {
//            return ResponseEntity.status(200).body(claimService.getAllPendingClaimsApprovalForUser());
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-claim/{claimId}")
//    public ResponseEntity<?> getClaimById(@PathVariable("claimId") Long claimId){
//        try {
//            return ResponseEntity.status(200).body(claimService.getClaimById(claimId));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

//    @GetMapping("/get-all-notification/{userId}")
//    public List<NotificationPojo> getAllNotification(@PathVariable("userId") Long userId) throws CommonApiException{
//        return notificationDto.getAllNotification(userId);
//    }


}
