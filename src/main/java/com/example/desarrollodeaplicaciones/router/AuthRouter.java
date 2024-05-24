package com.example.desarrollodeaplicaciones.router;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public abstract class AuthRouter extends BaseRouter {}
