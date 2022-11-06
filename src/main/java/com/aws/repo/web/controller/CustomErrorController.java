package com.aws.repo.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public ModelAndView errorHandler(HttpServletRequest req, Exception e) {
        // Get status code to determine which view should be returned
        Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception ex = (Exception) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        // In this case, status code will be shown in a view
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("code", statusCode.toString());
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    public String getErrorPath() {
        return "/error";
    }
}

