package com.increff.teamer.api;

import com.increff.teamer.dao.AccessDao;
import com.increff.teamer.dao.UserAccessMappingDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.constant.Authority;
import com.increff.teamer.pojo.AccessPojo;
import com.increff.teamer.pojo.UserAccessMappingPojo;
import com.increff.teamer.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccessApi {
    @Autowired
    AccessDao accessDao;
    @Autowired
    UserAccessMappingDao userAccessMappingDao;

    public Set<Authority> getUserAuthority(Long userId) throws CommonApiException {
        List<UserAccessMappingPojo> userAccessMappingPojoList = userAccessMappingDao.findAllByUserId(userId);
        Set<Authority> authoritySet = new HashSet<>();
        userAccessMappingPojoList.forEach(userAccessMappingPojo -> {
            AccessPojo accessPojo  = accessDao.findByAccessId(userAccessMappingPojo.getAccessId());
            authoritySet.add(new Authority(accessPojo.getAccessName()));
        });
        return authoritySet;
    }


    public void generateDefaultAccess(UserPojo userPojo) throws CommonApiException {
        String NORMAL = "normal";
        AccessPojo accessPojo = accessDao.findByAccessName(NORMAL);
        validateUserAccessMapping(userPojo.getUserId(),accessPojo.getAccessId());
        UserAccessMappingPojo userAccessMappingPojo = new UserAccessMappingPojo();
        userAccessMappingPojo.setAccessAssignedBy(userPojo.getUserId());
        userAccessMappingPojo.setAccessId(accessPojo.getAccessId());
        userAccessMappingPojo.setUserId(userPojo.getUserId());
        userAccessMappingDao.save(userAccessMappingPojo);
    }

    private void validateUserAccessMapping(Long userId,Long accessId) throws CommonApiException{
        UserAccessMappingPojo existingByUserIdAndAccessId = userAccessMappingDao.findByUserIdAndAccessId(userId,accessId);
        if(existingByUserIdAndAccessId != null){
            throw new CommonApiException(HttpStatus.FOUND,"User Already Have this Access");
        }
    }
}
