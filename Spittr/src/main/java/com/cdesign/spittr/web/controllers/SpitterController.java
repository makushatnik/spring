package com.cdesign.spittr.web.controllers;

import com.cdesign.spittr.config.ConstantManager;
import com.cdesign.spittr.data.entity.Spitter;
import com.cdesign.spittr.data.service.EmailService;
import com.cdesign.spittr.data.service.FileStorageService;
import com.cdesign.spittr.data.service.SpitterService;
import com.cdesign.spittr.data.service.SpittleService;
import com.cdesign.spittr.security.SpitterAuthentication;
import com.cdesign.spittr.security.TokenAuthenticationService;
import com.cdesign.spittr.web.forms.SignUpForm;
import com.cdesign.spittr.web.response.LoginResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Ageev Evgeny on 28.08.2016.
 */
@RestController
@RequestMapping("/spitter")
public class SpitterController {

    @Inject
    private SpittleService spittleService;

    @Inject
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Inject
    private TokenAuthenticationService tokenAuthenticationService;

    @Inject
    private SpitterService spitterService;

    @Inject
    private FileStorageService fileStorageService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private EmailService emailService;

    @RequestMapping(value = "/register", method = GET)
    public @ResponseBody
    Spitter showForm() {
        return new Spitter();
    }

    @RequestMapping(value = "/register", method = POST)
    public LoginResponse processForm(@Valid SignUpForm singUpForm,
                              //@RequestParam(value = "photo", required = false) MultipartFile photo,
                              Errors errors,
                              HttpServletResponse httpResponse) throws Exception {
        if (errors.hasErrors()) {
            throw new BadCredentialsException("Form has errors.");
        }

        if (!singUpForm.getPassword().equals(singUpForm.getPasswordAccept())) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            throw new BadCredentialsException("Password and accept password must be equals");
        }

        Spitter spitter = singUp(singUpForm);

        // Add the custom token as HTTP header to the response
        final String token = tokenAuthenticationService.addAuthentication(httpResponse, spitter);
        httpResponse.setStatus(HttpStatus.OK.value());
        httpResponse.sendRedirect("/" + spitter.getUsername());
        return new LoginResponse(token, spitter);
    }

    @RequestMapping(value = "/{username}/photo", method = POST)
    public Spitter sendPhoto(@RequestParam MultipartFile photo,
                            @PathVariable String username, Principal principal)
            throws Exception {
        final Spitter mySpitter = ((SpitterAuthentication) principal).getDetails();
        String photoFile = fileStorageService.saveFile("user_cover_photo_" + UUID.randomUUID(), photo);
        mySpitter.setPhotoUrl(photoFile);

        spitterService.save(mySpitter);
        return mySpitter;
    }

    @Secured({"ROLE_SPITTER", "ROLE_PREMIUM", "ROLE_ADMIN"})
    @RequestMapping(value = "/{username}", method = GET)
    public Spitter showProfile(@PathVariable String username, Principal principal) throws Exception {
        final Spitter mySpitter = ((SpitterAuthentication) principal).getDetails();
        final Spitter spitter = spitterService.findByUserName(username);
        if (spitter == null) {
            throw new EntityNotFoundException("User not found");
        }

        return spitter;
    }

    @Secured({"ROLE_SPITTER", "ROLE_PREMIUM", "ROLE_ADMIN"})
    @RequestMapping(value = "/me", method = GET)
    public Spitter showMe(Principal principal) {
        final Spitter mySpitter = ((SpitterAuthentication) principal).getDetails();
        if (mySpitter == null) {
            throw new EntityNotFoundException("User not found");
        }
        return mySpitter;
    }

    @RequestMapping(value = "/login", method = POST)
    public LoginResponse login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse httpResponse) {
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        final Spitter spitter = (Spitter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Add the custom token as HTTP header to the response
        final String token = tokenAuthenticationService.addAuthentication(httpResponse, spitter);

        return new LoginResponse(token, spitter);
    }

    private Spitter singUp(SignUpForm singUpForm) throws IOException, MessagingException {
        Spitter spitter = spitterService.findByUserName(singUpForm.getEmail());
        if (spitter != null) {
            throw new BadCredentialsException(
                    "This email has already been used to create an account. Sign in or reset your password to access the account.");
        }

        spitter = spitterService.findByUserName(singUpForm.getUsername());
        if (spitter != null) {
            throw new BadCredentialsException("Username already exists. Please try again.");
        }

        final String hashedPassword = passwordEncoder.encode(singUpForm.getPassword());
        spitter = singUpForm.toSpitter();
        spitter.setPassword(hashedPassword);
        //spitter.setVerified(false);
        spitterService.save(spitter);

        //if success, save a picture
        MultipartFile profilePicture = singUpForm.getProfilePicture();
        String path = "/tmp/spittr/" + spitter.getUsername() + ".jpg";
        File photoFile = new File(path);
        profilePicture.transferTo(new File(path));
        if (photoFile.exists()) {
            spitter.setPhotoUrl(ConstantManager.BASE_URL + "/" + path);
            spitterService.save(spitter);
        }

        emailService.sendWelcomeEmail(spitter);

        return spitter;
    }
}
