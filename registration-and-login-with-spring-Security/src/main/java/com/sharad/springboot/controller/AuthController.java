package com.sharad.springboot.controller;

import com.sharad.springboot.dto.UserDto;
import com.sharad.springboot.entiry.User;
import com.sharad.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.Binding;
import javax.swing.plaf.PanelUI;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    //handler Method to homePage Request
    @GetMapping("/index")
    public String homePage(){
        return "index";
    }

    //handler method to handle login request
    @GetMapping("/login")
    public String  login(){
        return  "login";
    }


    //Handler Method to handle the user registration form
    @GetMapping("/register")
    public String showRegistrationform(Model model){
        // model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "register";

    }
    //handler method to handle user form submission request
    @PostMapping("/register/save")
    public  String registration(@Valid  @ModelAttribute("user") UserDto userDto
                            , BindingResult result,
                                Model model){
        User exixtingUser= userService.findUserByEmail(userDto.getEmail());
        if(exixtingUser !=null && exixtingUser.getEmail()!=null && exixtingUser.getEmail().isEmpty()){
            result.rejectValue("email",null,"there is already an account registered " +
                    "with same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user",userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";

    }

    //handler method to handle List od users
    @GetMapping("/users")
    public  String users(Model model){
        List<UserDto> users=userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    //


}
