package com.deliver.facade;

import com.deliver.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public User findUser(@PathVariable int userId){
        return new User();
    }
}
