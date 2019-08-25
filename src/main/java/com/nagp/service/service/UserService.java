package com.nagp.service.service;

import com.nagp.service.beans.Errors;
import com.nagp.service.beans.User;
import com.nagp.service.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{

    private UserRepo userRepo;

    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public User saveUser(User user, Errors errors)
    {
        return userRepo.addToList(user, errors);
    }

    public List getUsers()
    {
        return userRepo.getUserList();
    }
}
