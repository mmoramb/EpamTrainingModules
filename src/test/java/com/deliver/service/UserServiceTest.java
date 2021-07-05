package com.deliver.service;


import com.deliver.Service.TicketService;
import com.deliver.Service.UserService;
import com.deliver.dao.UserDao;
import com.deliver.model.User;
import com.deliver.storage.Storage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserService.class, UserDao.class, Storage.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserById(){
        Assert.assertEquals(3L, userService.getUserById(3L).getId());
    }

    @Test
    public void testGetUserByEmail(){
        Assert.assertEquals("tickets5@gmail.com", userService.getUserByEmail("tickets5@gmail.com").getEmail());
    }
    /*
    @Test
    public void testGetUserByName(){
        Assert.assertEquals(2, userService.getUsersByName("user5",2,1).size());
    }
    */
    @Test
    public void testCreateUser(){
        User user = new User(7,"user7","tickets6@gmail.com");
        Assert.assertEquals(7, userService.createUser(user).getId());
    }
    @Test
    public void testUpdateUser(){
        User user = new User(7,"user8","tickets6@gmail.com");
        Assert.assertEquals(7, userService.createUser(user).getId());
        user.setName("user8");
        Assert.assertEquals("user8", userService.updateUser(user).getName());
    }
/*
    @Test
    public void testDeleteUser(){
        Assert.assertTrue(userService.deleteUser(6));
        Assert.assertNull(userService.getUserById(6));
    }
*/
}
