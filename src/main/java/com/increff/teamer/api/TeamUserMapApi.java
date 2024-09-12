package com.increff.teamer.api;

import com.increff.teamer.dao.RequestDetailDao;
import com.increff.teamer.dao.TeamDao;
import com.increff.teamer.dao.UserDao;
import com.increff.teamer.dao.UserTeamMappingDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.RequestCategory;
import com.increff.teamer.model.constant.RequestStatus;
import com.increff.teamer.model.constant.RequestType;
import com.increff.teamer.model.form.TeamUserMapForm;
import com.increff.teamer.pojo.RequestDetailPojo;
import com.increff.teamer.pojo.TeamPojo;
import com.increff.teamer.pojo.UserPojo;
import com.increff.teamer.pojo.UserTeamMappingPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamUserMapApi {
    @Autowired
    UserTeamMappingDao userTeamMappingDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RequestDetailDao requestDetailDao;

    public void addTeamMember(TeamUserMapForm teamUserMapForm) throws CommonApiException {
        List<UserTeamMappingPojo> userTeamMappingPojoList = validateAddTeamMember(teamUserMapForm);
        userTeamMappingDao.saveAll(userTeamMappingPojoList);
    }

    private List<UserTeamMappingPojo> validateAddTeamMember(TeamUserMapForm teamUserMapForm) throws CommonApiException{
        List<UserTeamMappingPojo> userTeamMappingPojoList = new ArrayList<>();
        for(Long userId : teamUserMapForm.getUserIds()){
            UserPojo userPojo = userDao.findByUserId(userId);
            if(userPojo == null) {
                throw new CommonApiException(HttpStatus.BAD_REQUEST,"Some users are Invalid");
            }else {
                UserTeamMappingPojo userTeamMappingPojo = userTeamMappingDao.findByUserIdAndTeamId(userId,teamUserMapForm.getTeamId());
                if(userTeamMappingPojo == null){
                    userTeamMappingPojo= new UserTeamMappingPojo(userId,teamUserMapForm.getTeamId());
                    userTeamMappingPojoList.add(userTeamMappingPojo);
                }
            }
        }
        if(userTeamMappingPojoList.isEmpty()){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"User is Already Present in the Team");
        }
        return userTeamMappingPojoList;
    }

    public List<UserTeamMappingPojo> findAllByTeamId(Long teamId) throws CommonApiException{
        return userTeamMappingDao.findAllByTeamId(teamId);
    }

    public UserTeamMappingPojo findByUserIdAndTeamId(Long userId,Long teamId) throws CommonApiException{
        return userTeamMappingDao.findByUserIdAndTeamId(userId,teamId);
    }

    public void delete(UserTeamMappingPojo userTeamMappingPojo) throws CommonApiException{
        userTeamMappingDao.delete(userTeamMappingPojo);
    }

    public List<UserTeamMappingPojo> findAllByUserId(Long userId) throws CommonApiException{
        return userTeamMappingDao.findAllByUserId(userId);
    }

    public Boolean validateRequest(RequestDetailPojo requestDetailPojo) throws CommonApiException{
        UserTeamMappingPojo userTeamMappingPojo = null;
        if(requestDetailPojo.getRequestCategory() == RequestCategory.REQUEST){
            userTeamMappingPojo = userTeamMappingDao.findByUserIdAndTeamId(requestDetailPojo.getRequestBy(),requestDetailPojo.getRequestId());
        } else if (requestDetailPojo.getRequestCategory() == RequestCategory.INVITE) {
            userTeamMappingPojo = userTeamMappingDao.findByUserIdAndTeamId(requestDetailPojo.getRequestFor(),requestDetailPojo.getRequestId());
        }
        return userTeamMappingPojo == null;
    }
}
