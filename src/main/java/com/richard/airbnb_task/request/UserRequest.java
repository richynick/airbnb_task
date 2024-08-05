package com.richard.airbnb_task.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String email;
    private String fullName;
    private String password;
    private String phoneNumber;
    private String username;
}
