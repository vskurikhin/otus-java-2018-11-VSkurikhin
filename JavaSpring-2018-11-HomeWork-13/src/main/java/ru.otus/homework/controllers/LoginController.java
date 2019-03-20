package ru.otus.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static ru.otus.homework.controllers.Constants.*;

@Controller
public class LoginController
{
    @GetMapping(REQUEST_LOGIN)
    public String loginPage(@RequestParam(required = false) boolean fail, Model model)
    {
        model.addAttribute(MODEL_LOGIN_ERROR, fail);

        return VIEW_LOGIN;
    }
}
