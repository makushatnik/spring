package com.cdesign.spittr.web.forms;

import com.cdesign.spittr.data.entity.Spitter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
public class SignUpForm {

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Userтame is required")
    private String username;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Accept password is required")
    private String passwordAccept;

    private MultipartFile profilePicture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordAccept() {
        return passwordAccept;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Spitter toSpitter() {
        if (!password.equals(passwordAccept)) return null;
        return new Spitter(username, password, firstName, lastName, email);
    }
}
