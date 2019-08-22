package com.microservice.weighttrackerapp.tracker.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WelcomeController {
    @GetMapping(value = "/")
    public String showWelcomePage(ModelMap model) { //@RequestParam String name,
        model.put("name", getLoggedInUserName());

        return "welcomePage"; //jsp
    }

    //LOGOUT
    @GetMapping(value = "/logout")
    public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) { //@RequestParam String name,
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    private String getLoggedInUserName() {
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
}
