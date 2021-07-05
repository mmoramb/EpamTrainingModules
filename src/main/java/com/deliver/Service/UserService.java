package com.deliver.Service;

import com.deliver.dao.UserDao;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.deliver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * Gets user by its id.
     *
     * @return User.
     */
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @return User.
     */
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDao.getUsersByName(name,pageSize,pageNum);
    }

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param user User data.
     * @return Created User object.
     */
    public User createUser(User user) {
        return userDao.adduser(user);
    }

    /**
     * Updates user using given data.
     *
     * @param user User data for update. Should have id set.
     * @return Updated User object.
     */
    public User updateUser(User user) {
        return userDao.updateuser(user);
    }

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    public boolean deleteUser(long userId) {
        return userDao.removeuser(userId);
    }
}
