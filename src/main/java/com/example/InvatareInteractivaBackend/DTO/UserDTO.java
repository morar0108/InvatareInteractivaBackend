package com.example.InvatareInteractivaBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private String gender;
    private String mobileNumber;
    private String email;
    private String username;
    private String password;
    private String token;
    private boolean active;

}
