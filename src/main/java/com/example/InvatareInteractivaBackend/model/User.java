package com.example.InvatareInteractivaBackend.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "`User`")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;

    @Column(name="active", nullable = true)
    private boolean active = true;

    @Column(name="gender", nullable = true)
    private String gender;

    @Column(name = "firstname", length = 30, nullable = false)
    @Length(max = 30)
    @NotNull
    @NotBlank(message = "First name is mandatory!")
    private String firstName;

    @Column(name = "lastname", length = 30, nullable = false)
    @Length(max = 30)
    @NotNull
    @NotBlank(message = "Last name is mandatory!")
    private String lastName;

    @Column(name = "mobilenumber", length = 30, nullable = false, unique = true)
    @Length(max = 30)
    @NotBlank(message = "Phone number is mandatory!")
    @NotNull
    private String mobileNumber;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    @Length(max = 50)
    @NotNull
    @NotBlank(message = "Email is mandatory!")
    @Email
    private String email;

    @Column(name = "username", length = 30, nullable = false, unique = true)
    @Length(max = 30)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "token", length = 400)
    private String token;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", active=" + active +
                ", gender='" + gender + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

