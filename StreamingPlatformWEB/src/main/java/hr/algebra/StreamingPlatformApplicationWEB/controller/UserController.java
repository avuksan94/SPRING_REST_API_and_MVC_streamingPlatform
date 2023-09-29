package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.blModels.UserModel;
import hr.algebra.bll.service.*;
import hr.algebra.dal.entity.Country;
import hr.algebra.dal.entity.Role;
import hr.algebra.dal.entity.User;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {
    private final UserServiceImpl _userService;
    private final RoleServiceImpl _roleService;
    private final CountryServiceImpl _countryService;
    private final SecurityServiceImpl _securityService;
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService, CountryServiceImpl coutnryService, SecurityServiceImpl securityService) {
        _userService = userService;
        _roleService = roleService;
        _countryService = coutnryService;
        _securityService = securityService;
    }

    @GetMapping("/list")
    public String listUsers(Model theModel) {
        List<User> users = _userService.findAll();

        // add to the spring model
        theModel.addAttribute("users", users);

        return "users/list-users";
    }

    @GetMapping("/listSearch")
    public String searchUsers(User User, Model model, String keyword) {
        List<User> users;
        if(keyword != null) {
            users = _userService.getByKeyword(keyword);
        }else {
            users = _userService.findAll();
        }
        model.addAttribute("users", users);

        return "users/list-users";
    }

    @GetMapping("/showFormForAddUser")
    public String showFormForAddUser(Model theModel){

        //create the model attribute to bind form data
        UserModel user = new UserModel();
        theModel.addAttribute("user", user);

        List<Country> countries = _countryService.findAll();
        theModel.addAttribute("countries", countries);

        return "users/user-form";
    }

    @GetMapping("/showFormForUpdateUser")
    public String showFormForUpdateUser(@RequestParam("username") String username,Model theModel){
        //get the user from the service
        User user = _userService.findByUsername(username);

        //set user int the model to prepopulate the form
        theModel.addAttribute("user", user);

        List<Country> countries = _countryService.findAll();
        theModel.addAttribute("countries", countries);

        //send over to our form
        return "users/user-form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        // Check for validation errors
        List<Country> countries = _countryService.findAll();
        //need countries loaded like this or every time there is a validation error display the countries
        //are set to null and it crashes the application
        model.addAttribute("countries", countries);
        if (bindingResult.hasErrors()) {
            return "users/user-form";
        }

        try {
            // If were here, it means the user with the given username exists. So this is an update
            User existingUserByEmail = _userService.findByEmail(user.getEmail());
            if (!existingUserByEmail.getUsername().equals(user.getUsername())) {
                model.addAttribute("errorMessage", "Email already exists for another user!");
                return "users/user-form";
            }
        } catch (CustomNotFoundException e) {
            // This means the user doesn't exist, so it's a new user creation.
            try {
                User existingUserByEmail = _userService.findByEmail(user.getEmail());
                // If were here, it means the email is already used by another user.
                model.addAttribute("errorMessage", "User with that email already exists!");
                return "users/user-form";
            } catch (CustomNotFoundException ex) {
                // This is expected as were in the process of creating a new user. No action is needed.
            }
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setSecurityToken("Not implemented in MVC");
        user.setPassword(_securityService.doBCryptPassEncoding(user.getPassword()));
        user.setConfirmed(true);

        _userService.save(user);

        //Giving basic role here for the users that the admin creates
        Role r = new Role(user.getUsername(), "ROLE_USER");
        _roleService.save(r);

        return "redirect:/users/list";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("username") String username){
        _roleService.deleteByUsername(username);

        //delete the user
        _userService.deleteByUsername(username);

        //redirect to the /users/list
        return "redirect:/users/list";
    }

    //special for admins -- ADMIN form is only used for create//update never
    @GetMapping("/showFormForAddAdmin")
    public String showFormForAddAdmin(Model theModel){

        //create the model attribute to bind form data
        UserModel user = new UserModel();
        theModel.addAttribute("user", user);

        List<Country> countries = _countryService.findAll();
        theModel.addAttribute("countries", countries);

        return "users/admin-form";
    }

    @PostMapping("/saveAdmin")
    public String saveAdmin(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        // Always load countries to avoid null crashes during validation errors
        List<Country> countries = _countryService.findAll();
        model.addAttribute("countries", countries);

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "users/user-form";
        }

        // Check if the username already exists
        try {
            User existingUserByUsername = _userService.findByUsername(user.getUsername());
            model.addAttribute("errorMessage", "Username already exists!");
            return "users/user-form";
        } catch (CustomNotFoundException e) {
            // This is expected if the username isn't taken. No action is needed.
        }

        // Check if email already exists
        try {
            User existingUserByEmail = _userService.findByEmail(user.getEmail());
            model.addAttribute("errorMessage", "User with that email already exists!");
            return "users/user-form";
        } catch (CustomNotFoundException e) {
            // This is expected as we're in the process of creating a new admin. No action is needed.
        }

        // Set additional fields for the new admin
        user.setCreatedAt(LocalDateTime.now());
        user.setSecurityToken("Not implemented in MVC");
        user.setPassword(_securityService.doBCryptPassEncoding(user.getPassword()));
        user.setConfirmed(true);

        // Save the new admin to the database
        _userService.save(user);

        // Assign the ROLE_ADMIN and ROLE_USER role to the new admin
        Role r = new Role(user.getUsername(), "ROLE_USER");
        Role r2 = new Role(user.getUsername(), "ROLE_ADMIN");
        _roleService.save(r);
        _roleService.save(r2);

        return "redirect:/users/list";
    }
}
