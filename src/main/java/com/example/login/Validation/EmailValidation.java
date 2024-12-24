package com.example.login.Validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.login.Repository.UserRepo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        try {
            return userRepo.findByEmail(email).isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
