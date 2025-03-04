package by.tms.bookstore.controller;

import by.tms.bookstore.dto.BookDto;
import by.tms.bookstore.dto.UserDto;
import by.tms.bookstore.models.UserBookList;
import by.tms.bookstore.services.UserBookListService;
import by.tms.bookstore.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
public class UserController {
    @Autowired
    private UserBookListService userBookListService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user" ) UserDto userDto){
        return "books/register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user" ) UserDto userDto, Model model){
        userService.save(userDto);
        model.addAttribute("message", "Registered successfully");
        log.info("User registered");
        return "books/register";
    }

    @GetMapping("/login")
    public String login(){
        return "books/login";
    }



    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        List<UserBookList> list = userBookListService.getAllUserBooks();
        model.addAttribute("book", list);
        return "books/userBooks";
    }

    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        return "books/available";
    }
}
