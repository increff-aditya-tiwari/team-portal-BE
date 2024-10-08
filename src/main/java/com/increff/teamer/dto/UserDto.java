package com.increff.teamer.dto;

import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.UserData;
import com.increff.teamer.model.data.UserUiData;
import com.increff.teamer.model.form.CreateUserForm;
import com.increff.teamer.model.form.LoginForm;
import com.increff.teamer.pojo.UserPojo;
import com.increff.teamer.flowApi.UserFlowApi;
import com.increff.teamer.util.ConvertUtil;
import com.increff.teamer.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDto {

    @Autowired
    private UserFlowApi userFlowApi;
    @Autowired
    private ValidationUtil validationUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConvertUtil convertUtil;

    public UserData userLogin(LoginForm loginForm) throws CommonApiException{
        validationUtil.validateForm(loginForm);
        return userFlowApi.userLogin(loginForm);
    }

    public UserData createUser(CreateUserForm createUserForm) throws CommonApiException{
        validationUtil.validateForm(createUserForm);
        createUserForm.setPassword(this.bCryptPasswordEncoder.encode(createUserForm.getPassword()));
        UserPojo userPojo = convertUtil.convertDataToPojo(createUserForm,UserPojo.class);
        return userFlowApi.createUser(userPojo);
    }

    public List<UserUiData> getAllUser() throws CommonApiException{
        return userFlowApi.getAllUser();
    }

    public void logout() throws CommonApiException{
        userFlowApi.logout();
    }
}
