package com.example.bookshop.service;

import com.example.bookshop.dto.UserDTO;
import com.example.bookshop.entity.User;
import com.example.bookshop.payload.Request.SignInRequest;
import com.example.bookshop.payload.Request.SignUpRequest;

import java.util.List;

public interface UserService {
    void updateClassifications(int id, String classifications);
    void changePassword(int id, String oldPassword, String newPassword );
    UserDTO registerUser(SignUpRequest userRegistrationDTO);
    UserDTO loginUser(SignInRequest signInRequest);
    User findByUsername(String username);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(int id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(int id, UserDTO userDTO);
    void deleteUser(int id);
}
