package com.microservice.weighttrackerapp.tracker;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller("error")
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", ex.getStackTrace());
        mv.addObject("url", request.getRequestURL());
        mv.setViewName("errorPage");// passing it to error.jsp
        return mv;
    }

}
