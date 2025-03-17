package com.example.demo.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.example.demo.controller.dto.response.EmployeeCredentialsDto;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;

@Service
public class CredentialGeneratorService {

    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_-+=<>?";
    private static final String ALL_CHARS = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + SPECIAL;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates username for an employee based on their name and company
     */
    public String generateUsername(Employee employee, Company company) {
        String firstName = employee.getFirstName().toLowerCase().replaceAll("\\s+", "");
        String lastName = employee.getLastName().toLowerCase().replaceAll("\\s+", "");
        String companyName = company.getName().toLowerCase().replaceAll("\\s+", "");
        
        return firstName + "." + lastName + "@" + companyName;
    }

    /**
     * Generates a secure password with minimum requirements:
     * - At least 12 characters
     * - At least 1 uppercase letter
     * - At least 1 lowercase letter
     * - At least 1 digit
     * - At least 1 special character
     */
    public String generateSecurePassword() {
        int passwordLength = 12 + RANDOM.nextInt(4); // 12-15 characters
        
        StringBuilder password = new StringBuilder(passwordLength);
        
        // Add at least one of each required character type
        password.append(CHAR_LOWERCASE.charAt(RANDOM.nextInt(CHAR_LOWERCASE.length())));
        password.append(CHAR_UPPERCASE.charAt(RANDOM.nextInt(CHAR_UPPERCASE.length())));
        password.append(DIGIT.charAt(RANDOM.nextInt(DIGIT.length())));
        password.append(SPECIAL.charAt(RANDOM.nextInt(SPECIAL.length())));
        
        // Fill the remaining password length with random characters
        for (int i = 4; i < passwordLength; i++) {
            password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }
        
        // Shuffle the characters to ensure randomness
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = RANDOM.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }
        
        return new String(passwordArray);
    }
    
    /**
     * Generates employee credentials (username and password)
     */
    public EmployeeCredentialsDto generateCredentials(Employee employee, Company company) {
        String username = generateUsername(employee, company);
        String password = generateSecurePassword();
        
        return new EmployeeCredentialsDto(username, password);
    }
}