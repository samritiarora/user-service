package com.nagp.service.controller;

import com.nagp.service.beans.Errors;
import com.nagp.service.beans.User;
import com.nagp.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/nagp/api")
@Controller
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController
{
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/user")
    //@CrossOrigin(origins = "*", allowedHeaders = "*")
    public @ResponseBody ResponseEntity saveUser(@Valid @RequestBody User user)
    {
        LOGGER.info("Received request to create user {}", user);

        Errors errors = new Errors();
        try
        {
            User u = userService.saveUser(user, errors);

            if (errors.isEmpty())
            {
                return ResponseEntity.ok().body(u);
            } else
            {
                return ResponseEntity.badRequest().body(errors);
            }
        } catch (Exception ex)
        {
            LOGGER.error("Unknown exception occurred while trying to send message to ems:", ex);
            errors.rejectValue("UNKNOWN_EXCEPTION", "Unknown exception occurred while trying to send message to EMS.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }

    @GetMapping("/users")
    //@CrossOrigin(origins = "*", allowedHeaders = "*")
    public @ResponseBody ResponseEntity getUsers()
    {
        LOGGER.info("Received request to fetch users");

        Errors errors = new Errors();
        try
        {
            List u = userService.getUsers();

            if (errors.isEmpty())
            {
                HttpHeaders res = new HttpHeaders();
                res.add("Access-Control-Allow-Origin", "*");
                res.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                res.add("Access-Control-Max-Age", "3600");
                res.add("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                res.add("Access-Control-Expose-Headers", "xsrf-token");
                return ResponseEntity.ok().headers(res).body(u);
            } else
            {
                return ResponseEntity.badRequest().body(errors);
            }
        } catch (Exception ex)
        {
            LOGGER.error("Unknown exception occurred while trying to send message to ems:", ex);
            errors.rejectValue("UNKNOWN_EXCEPTION", "Unknown exception occurred while trying to send message to EMS.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
