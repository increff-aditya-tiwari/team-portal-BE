package com.increff.teamer.api;

import com.increff.teamer.dao.UserDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.UserData;
import com.increff.teamer.pojo.UserPojo;
import com.increff.teamer.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApi implements UserDetailsService {

    @Autowired
    UserDao userDao;
    @Autowired
    ConvertUtil convertUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo userPojo  = userDao.findByUserName(username);
        if(userPojo == null){
            throw new UsernameNotFoundException("User Nor found");
        }
        return convertUtil.convertPojoToData(userPojo,UserData.class);
    }

    public UserPojo saveUser(UserPojo userPojo) throws CommonApiException {
        validateCreateUser(userPojo);
        return userDao.save(userPojo);
    }

    public List<UserPojo> getAllUser() throws CommonApiException{
        return userDao.findAll();
    }
    public UserPojo getUserByUsername(String username) throws CommonApiException{
        return userDao.findByUserName(username);
    }

    public UserData getCurrentUser() throws CommonApiException {
        return (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void validateCreateUser(UserPojo userPojo) throws CommonApiException{
        UserPojo existingByUsername = userDao.findByUserName(userPojo.getUsername());
        UserPojo existingByEmail = userDao.findByEmail(userPojo.getEmail());
        if(existingByEmail != null || existingByUsername != null){
            throw new CommonApiException(HttpStatus.FOUND,"User is Already Present With Same "+" Username Or Email");
        }
    }

    public UserPojo isValidUser(Long userId) throws CommonApiException{
        UserPojo userPojo = userDao.findByUserId(userId);
        if(userPojo == null) {
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"User with User Id "+ userId + " is Invalid");
        }
        return userPojo;
    }
}
