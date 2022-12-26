package com.example.contactsbook.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Contact {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters")
    private String firstName;
    @NotEmpty(message = "LastName should not be empty")
    @Size(min = 2,max = 30,message = "lastName should be between 2 and 30 characters")
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    private Long phoneNumber;

    public Contact(String firstName, String lastName, String email, Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
