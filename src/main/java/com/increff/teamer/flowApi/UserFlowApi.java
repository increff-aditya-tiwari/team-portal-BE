package com.increff.teamer.flowApi;

import com.increff.teamer.api.AccessApi;
import com.increff.teamer.api.UserApi;
import com.increff.teamer.dao.AccessDao;
import com.increff.teamer.dao.UserAccessMappingDao;
import com.increff.teamer.dao.UserDao;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.UserData;
import com.increff.teamer.model.data.UserUiData;
import com.increff.teamer.model.form.LoginForm;
import com.increff.teamer.pojo.UserPojo;
import com.increff.teamer.util.ConvertUtil;
import com.increff.teamer.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserFlowApi {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    ConvertUtil convertUtil;
    @Autowired
    UserDao userDao;
    @Autowired
    AccessDao accessDao;
    @Autowired
    UserAccessMappingDao userAccessMappingDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AccessApi accessApi;
    @Autowired
    UserApi userApi;

    public UserData userLogin(LoginForm loginForm) throws CommonApiException {
        authentication(loginForm.getUsername(),loginForm.getPassword());
        UserData userData = (UserData)userApi.loadUserByUsername(loginForm.getUsername());
        userData.setAuthorities(accessApi.getUserAuthority(userData.getUserId()));
        userData.setJwtToken(jwtUtils.generateToken(userData));
        return userData;
    }

    public UserData createUser(UserPojo userPojo) throws CommonApiException {
        userPojo = userApi.saveUser(userPojo);
        accessApi.generateDefaultAccess(userPojo);
        UserData userData = convertUtil.convertPojoToData(userPojo,UserData.class);
        userData.setAuthorities(accessApi.getUserAuthority(userPojo.getUserId()));
        return userData;
    }


    private void authentication(String username,String password) throws CommonApiException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (BadCredentialsException e){
            throw new CommonApiException(HttpStatus.BAD_REQUEST,"User Credentials are invalid");
        }catch (UsernameNotFoundException e){
            throw new CommonApiException(HttpStatus.NOT_FOUND,"User not found");
        }
    }

    public UserData getCurrentUser() throws Exception {
        return (UserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<UserUiData> getAllUser() throws CommonApiException{
        List<UserPojo> userPojoList = userApi.getAllUser();
        List<UserUiData> userUiDataList = new ArrayList<>();
        for(UserPojo userPojo : userPojoList){
            userUiDataList.add(convertUtil.convertPojoToData(userPojo,UserUiData.class));
        }
       return userUiDataList;
    }
}
