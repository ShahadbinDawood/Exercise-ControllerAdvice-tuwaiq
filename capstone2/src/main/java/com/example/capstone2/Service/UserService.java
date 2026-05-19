package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final EmailValidationService emailValidationService;
    public List<User>  getAllUser (){
        return userRepository.findAll();
    }
    public void addUser(User user){
        if (!emailValidationService.isValidEmail(user.getEmail())) {
            throw new ApiException("Email is not valid or does not exist");
        }
        userRepository.save(user);

    }
    public void updateUser (Integer id , User user){
        User oldUser = userRepository.findUserById(id);
        if (oldUser==null){
            throw new ApiException("User not found");
        }
        if (!emailValidationService.isValidEmail(user.getEmail())) {
            throw new ApiException("Email is not valid or does not exist");
        }
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setPassword(user.getPassword());
        oldUser.setRole(user.getRole());
        oldUser.setBio(user.getBio());
        userRepository.save(oldUser);
    }
    public void deleteUser(Integer id ){
        User user = userRepository.findUserById(id);
        if (user==null){
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }
}
