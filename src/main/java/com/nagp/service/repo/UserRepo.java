package com.nagp.service.repo;

import com.nagp.service.beans.Errors;
import com.nagp.service.beans.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class UserRepo
{
    private static UserRepo mInstance;
    private ArrayList<User> list = null;

    public static UserRepo getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new UserRepo();
        }

        return mInstance;
    }

    private UserRepo()
    {
        list = new ArrayList<User>();
    }

    // retrieve array from anywhere
    public ArrayList<User> getUserList()
    {
        return this.list;
    }

    //Add element to array
    public User addToList(User user, Errors errors)
    {
        if (list.indexOf(user) < 0)
        {
            user.setId(UUID.randomUUID());
            list.add(user);
            return user;
        }
        else
        {
            errors.rejectValue("Duplicate user", "Error while adding to list");
            return null;
        }
    }
}
