package com.example.lab11.Service;

import com.example.lab11.Api.ApiException;
import com.example.lab11.Model.User;
import com.example.lab11.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        if (user == null) {
            throw new ApiException("User not found");
        }
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User oldUser = userRepository.findUserByUserId(id);
        if (oldUser == null) {
            throw new ApiException("User not found");
        }
        oldUser.setUserName(user.getUserName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setRegistrationDate(user.getRegistrationDate());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserByUserId(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }
}


