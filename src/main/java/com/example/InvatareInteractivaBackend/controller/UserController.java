package com.example.InvatareInteractivaBackend.controller;

import com.example.InvatareInteractivaBackend.DTO.UserDTO;
import com.example.InvatareInteractivaBackend.exceptions.UserNotFoundException;
import com.example.InvatareInteractivaBackend.model.AuthRequest;
import com.example.InvatareInteractivaBackend.model.User;
import com.example.InvatareInteractivaBackend.service.TokenService;
import com.example.InvatareInteractivaBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*", originPatterns = "*", allowedHeaders = "*")
@RestController
public class UserController {

    private TokenService tokenService;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService){
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users")
    public List<UserDTO> showUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/userByUsername")
    public User getUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/user/isActive")
    public boolean isActive(@RequestBody AuthRequest authRequest){
        return userService.isActive(authRequest);
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public UserDTO createUserDTO(@RequestBody UserDTO userDTO) throws Exception, UserNotFoundException {
        User user = this.userService.convertDtoToEntity(userDTO);
        User userCreated = this.userService.saveUser(user);
        return userService.convertEntityToDto(userCreated);
    }

    @RequestMapping(value = "/userUpdate", method = RequestMethod.PUT)
    public User updateUser(@RequestBody UserDTO userDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String author) throws UserNotFoundException {
        return userService.updateUser(userDTO, userDTO.getUsername(),tokenService.getUsernameFromJWT(author));
    }

    @RequestMapping(value = "/passwordChange", method = RequestMethod.POST)
    public User changePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        return userService.changeUserPassword(username, oldPassword, newPassword);
    }

    @PostMapping("/user/activateUser")
    public void activateUser(@RequestBody String username) {
        userService.activateUser(username);
    }


    @PostMapping("/user/deactivateUser")
    public void deactivateUser(@RequestBody String username) {
        List<User> authorizedViewers = new ArrayList<>();
        List<User> users = userService.getUsers();
        userService.deactivateUser(username,authorizedViewers);
    }

    @PostMapping("/user/changeStatus")
    public boolean changeActivationStatus(@RequestBody String username) {
        List<User> authorizedViews = new ArrayList<>();
        List<User> users = userService.getUsers();
        return userService.changeActivationStatus(username);
    }

    @PostMapping("/user/checkUser")
    public String loginUser(@RequestBody AuthRequest authRequest){
        return userService.loginUser(authRequest);
    }

    @PostMapping("/user/checkPass")
    public String loginPass(@RequestBody AuthRequest authRequest){
        return userService.loginPass(authRequest);
    }

    @PostMapping("/user/login")
    public String login(@RequestBody AuthRequest authRequest){
        return userService.login(authRequest);
    }

    @PostMapping("/user/logout")
    public boolean logout(@RequestBody String username) {
        return userService.logout(username);
    }

}
