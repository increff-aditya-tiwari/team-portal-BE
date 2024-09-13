package com.increff.teamer.controller;

import com.increff.teamer.dto.NotificationDto;
import com.increff.teamer.dto.UserDto;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.UserData;
import com.increff.teamer.model.data.UserUiData;
import com.increff.teamer.model.form.CreateUserForm;
import com.increff.teamer.model.form.LoginForm;
import com.increff.teamer.flowApi.UserFlowApi;
import com.increff.teamer.pojo.NotificationPojo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequestMapping(value = "user")
public class UserController {


    @Autowired
    private UserDto userDto;
    @Autowired
    private NotificationDto notificationDto;


    @PostMapping("/login")
    public UserData userLogin(@RequestBody LoginForm loginForm) throws CommonApiException {
        return userDto.userLogin(loginForm);
    }

    @PostMapping("/create")
    public UserData createUser(@RequestBody CreateUserForm createUserForm) throws CommonApiException {
        return userDto.createUser(createUserForm);
    }

    @GetMapping("/get-all")
    public List<UserUiData> getAllUser() throws CommonApiException{
        return userDto.getAllUser();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws CommonApiException{
        userDto.logout();
    }

    @GetMapping("/get-all-notification/{userId}")
    public List<NotificationPojo> getAllNotification(@PathVariable("userId") Long userId) throws CommonApiException{
        return notificationDto.getAllNotification(userId);
    }

}
