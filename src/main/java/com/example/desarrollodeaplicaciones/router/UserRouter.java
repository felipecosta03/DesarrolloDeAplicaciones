package com.example.desarrollodeaplicaciones.router;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public abstract class UserRouter extends BaseRouter {}
