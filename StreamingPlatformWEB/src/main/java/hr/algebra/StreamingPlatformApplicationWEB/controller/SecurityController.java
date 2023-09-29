package hr.algebra.StreamingPlatformApplicationWEB.controller;

import hr.algebra.bll.blModels.UserModel;
import hr.algebra.bll.service.CountryServiceImpl;
import hr.algebra.bll.service.RoleServiceImpl;
import hr.algebra.bll.service.SecurityServiceImpl;
import hr.algebra.bll.service.UserServiceImpl;
import hr.algebra.dal.entity.Country;
import hr.algebra.dal.entity.Role;
import hr.algebra.dal.entity.User;
import hr.algebra.utils.notFoundErrors.CustomNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("security")
public class SecurityController {
    private final CountryServiceImpl _countryService;
    private final UserServiceImpl _userService;
    private final RoleServiceImpl _roleService;
    private final SecurityServiceImpl _securityService;

    public SecurityController(CountryServiceImpl countryService, UserServiceImpl userService, RoleServiceImpl roleService, SecurityServiceImpl securityService) {
        _countryService = countryService;
        _userService = userService;
        _roleService = roleService;
        _securityService = securityService;
    }

    @GetMapping("/loginUser")
    public String showMyLoginPage() {
        return "security/security-login";
    }

    @PostMapping("/manualLogout")
    public String manualLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/security/security-login?logoutManual=true";
    }

    @GetMapping("/showFormCreateUser")
    public String showFormCreateUser(Model theModel){

        //create the model attribute to bind form data
        UserModel user = new UserModel();
        theModel.addAttribute("user", user);

        List<Country> countries = _countryService.findAll();
        theModel.addAttribute("countries", countries);

        return "security/security-registration";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        // Check for validation errors
        List<Country> countries = _countryService.findAll();
        //need countries loaded like this or every time there is a validation error display the countries
        //are set to null and it crashes the application
        model.addAttribute("countries", countries);
        if (bindingResult.hasErrors()) {
            return "security/security-registration";
        }

        try {
            // If were here, it means the user with the given username exists. So this is an update
            User existingUserByEmail = _userService.findByEmail(user.getEmail());
            if (!existingUserByEmail.getUsername().equals(user.getUsername())) {
                model.addAttribute("errorMessage", "Email already exists for another user!");
                return "security/security-registration";
            }
        } catch (CustomNotFoundException e) {
            // This means the user doesnt exist, so its a new user creation.
            try {
                User existingUserByEmail = _userService.findByEmail(user.getEmail());
                // If we are here, it means the email is already used by another user.
                model.addAttribute("errorMessage", "User with that email already exists!");
                return "security/security-registration";
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

        return "redirect:/security/loginUser";
    }
}
