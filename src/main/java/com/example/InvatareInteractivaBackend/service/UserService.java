package com.example.InvatareInteractivaBackend.service;

import com.example.InvatareInteractivaBackend.DTO.UserDTO;
import com.example.InvatareInteractivaBackend.configuration.Hasher;
import com.example.InvatareInteractivaBackend.exceptions.UserNotFoundException;
import com.example.InvatareInteractivaBackend.model.AuthRequest;
import com.example.InvatareInteractivaBackend.model.User;
import com.example.InvatareInteractivaBackend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;



    public UserService(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    public User saveUser(User user) throws Exception, UserNotFoundException {
        user.setUsername(generateUsername(user.getFirstName(), user.getLastName()));
        String notEncodedPassword = user.getPassword();
        String encodedPassword = Hasher.hash(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User changeUserPassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        String notEncodedPassword = user.getPassword();
        if(Objects.equals(Hasher.hash(oldPassword), notEncodedPassword)
                && Objects.equals(Hasher.hash(oldPassword), user.getPassword())) {
            String encodedPassword = Hasher.hash(newPassword);
            user.setPassword(encodedPassword);
        } else {
            throw new IllegalArgumentException("Old password does not match.");
        }
        return userRepository.save(user);
    }

    /**
     * Changes the activation status of a user.
     * Every user can be activated.
     * Only users that have all bugs closed can be deactivated
     *
     * @param username must not be null or empty string
     * @return true if the status has been changed, false otherwise
     */
    public boolean changeActivationStatus(String username) {
        if (username == null || username.isEmpty())
            return false;
        User user = userRepository.findByUsername(username);
        if (user == null)
            return false;

        user.setActive(!user.isActive());
        userRepository.save(user);
        return true;
    }

    public void deactivateUser(String username, List<User> authorizedViewers) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
        }
    }

    public void activateUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setActive(true);
            userRepository.save(user);
        }
    }

    public boolean isActive(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername());
        if (user != null) {
            return user.isActive();
        }
        return false;
    }

    public User updateUser(UserDTO user, String username, String updatedBy) throws UserNotFoundException {
        User newUser = userRepository.findByUsername(username);
        UserDTO oldUserDTO = convertEntityToDto(newUser);
        if (user.getFirstName() != null) {
            newUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            newUser.setLastName(user.getLastName());
        }
        if (user.getMobileNumber() != null) {
            newUser.setMobileNumber(user.getMobileNumber());
        }
        if (user.getGender() != null) {
            newUser.setGender(user.getGender());
        }
        if (user.getEmail() != null) {
            newUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            newUser.setPassword(Hasher.hash(user.getPassword()));
        }
        User updatedByUser = findUserByUsername(updatedBy);
        return userRepository.save(updatedByUser);
    }

    public List<UserDTO> getAllUsers() {
        return ((List<User>) userRepository.findAll())
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }


    public UserDTO convertEntityToDto(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .mobileNumber(user.getMobileNumber())
                .password(user.getPassword())
                .token(user.getToken())
                .active(user.isActive())
                .build();
    }

    public User convertDtoToEntity(UserDTO userDTO)  {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .gender(userDTO.getGender())
                .mobileNumber(userDTO.getMobileNumber())
                .active(userDTO.isActive())
                .password(userDTO.getPassword())
                .token(userDTO.getToken())
                .build();
    }

    public List<User> getAllUsersDetails() {
        return (List<User>) userRepository.findAll();
    }


    public String login(AuthRequest authRequest) {
        try {
            User user = findUserByUsername(authRequest.getUsername());
            if (checkPassword(user, authRequest.getPassword())) {
                final String token = getJWTToken(authRequest.getUsername());
                user.setToken(token);
                userRepository.save(user);
                return token;
            } return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String loginUser(AuthRequest authRequest){
        try {
            User user = findUserByUsername(authRequest.getUsername());
            if (user != null){
                return "passed";
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String loginPass(AuthRequest authRequest){
        try {
            User user = findUserByUsername(authRequest.getUsername());
            if (checkPassword(user, authRequest.getPassword())) {
                return "passed";
            } return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean logout(String username) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                user.setToken("");
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean checkPassword(User user, String password) {
        System.out.println("check");
        return user.getPassword().equals(Hasher.hash(password));
    }

    private String getJWTToken(String username) {
        Set<String> permissionSet = new HashSet<>();
        User user = findUserByUsername(username);
        String secretKey = "mySecretKey";
        String token = Jwts
                .builder()
                .setId("msgJWT")
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return token;
    }

    public String generateUsername(String firstname,String lastname) {
        String username = "";
        if (lastname.length() < 5) {
            username += lastname;
        } else {
            username += lastname.substring(0, 5);
        }
        username += firstname.substring(0, 1);
        int count = 1;
        while (userRepository.findByUsername(username) != null || count == firstname.length() - 1) {
            username += firstname.substring(count, count + 1);
            count++;
        }
        return username.toLowerCase();
    }


}
