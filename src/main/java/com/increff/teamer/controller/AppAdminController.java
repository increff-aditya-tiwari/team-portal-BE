package com.increff.teamer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RequestMapping(value = "event")
public class AppAdminController {

}
