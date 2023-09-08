package hr.algebra.streamingPlatform.rest;

import hr.algebra.bll.blModels.UserModel;
import hr.algebra.bll.service.RoleServiceImpl;
import hr.algebra.bll.service.SecurityServiceImpl;
import hr.algebra.bll.service.UserServiceImpl;
import hr.algebra.dal.entity.Role;
import hr.algebra.dal.entity.User;
import hr.algebra.utils.alreadyExistsErrors.CustomAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserServiceImpl _userService;
    @Autowired
    private final SecurityServiceImpl _securityService;
    @Autowired
    private final RoleServiceImpl _roleService;

    public UserController(UserServiceImpl userService, SecurityServiceImpl securityService,RoleServiceImpl roleService) {
        _userService = userService;
        _securityService = securityService;
        _roleService = roleService;
    }

    @Async
    @GetMapping("/users")
    public CompletableFuture<List<UserModel>> findAll(){
        List<User> users = _userService.findAll();
        List<Role> userRoles = _roleService.findAll();

        Map<String, List<Role>> userRoleMap = userRoles.stream()
                .collect(Collectors.groupingBy(Role::getUsername));

        List<UserModel> userModels = users.stream().map(user -> {
            Set<String> roles = userRoleMap.getOrDefault(user.getUsername(), Collections.emptyList())
                    .stream().map(Role::toString)
                    .collect(Collectors.toSet());

            return new UserModel(
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getCountryOfResidenceId(),
                    roles
            );
        }).collect(Collectors.toList());

        return CompletableFuture.completedFuture(userModels);
    }

    @Async
    @GetMapping("/users/{username}")
    public CompletableFuture<UserModel> getUser(@PathVariable String username){
        User user = _userService.findByUsername(username);

        List<Role> userRoles = _roleService.findByUsername(username);

        Set<String> roles = userRoles.stream()
                .map(Role::toString)
                .collect(Collectors.toSet());

        UserModel userModel = new UserModel(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getCountryOfResidenceId(),
                roles
        );

        return CompletableFuture.completedFuture(userModel);
    }


    @Async
    @PostMapping("/users")
    public CompletableFuture<UserModel> addUser(@Valid @RequestBody UserModel userModel){

        List<User> allDBUsers = _userService.findAll();

        //this is firstOrDefault in c#
        Optional<User> result = allDBUsers.stream()
                .filter(userRes -> userModel.getUsername().equals(userRes.getUsername())
                        || userModel.getEmail().equals(userRes.getEmail()))
                .findFirst();

        if(result.isPresent()){
            throw new CustomAlreadyExistsException("User with that username or email already exists!");
        }

        User user = new User(
                userModel.getUsername(),
                LocalDateTime.now(),
                null,
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                _securityService.doBCryptPassEncoding(userModel.getPassword()),
                userModel.getPhone(),
                false,
                "Not implemented",
                userModel.getCountryOfResidenceId()
        );

        //should save roles only if the user is ok
        _userService.save(user);

        for (String role : userModel.getRoles()){
            Role r = new Role(userModel.getUsername(), role);
            _roleService.save(r);
        }

        return CompletableFuture.completedFuture(userModel);
    }


}
