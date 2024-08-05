package com.richard.airbnb_task.service;

import com.richard.airbnb_task.model.User;

public interface UserService {

    User findUserProfiledByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long userId) throws Exception;

}
