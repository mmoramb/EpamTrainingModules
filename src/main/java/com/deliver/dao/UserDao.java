package com.deliver.dao;

import com.deliver.model.User;
import com.deliver.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Repository
public class UserDao{
    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    @Autowired
    private Storage storage;

    public User getUserById(long id){
        LOGGER.info("getting user by id");
        User user = null;
        if (this.storage.data.containsKey("user"+id))
            user = (User) this.storage.data.get("user"+id);
        LOGGER.info("used does no exist");
        return user;
    }

    public User adduser(User user){
        LOGGER.info("adding new user");
        this.storage.data.putIfAbsent("user"+user.getId(), user);
        LOGGER.info("user added correctly");
        return user;
    }

    public boolean removeuser(long userId){
        LOGGER.info("removing user");
        if (this.storage.data.containsKey("user"+userId)){
            if (this.storage.data.remove("user"+userId) != null)
                return true;
        }
        LOGGER.info("there was a problem removing the user");
        return false;
    }

    public User updateuser(User user){
        LOGGER.info("updating user");
        if (this.storage.data.containsKey("user"+user.getId())){
            this.storage.data.replace("user"+user.getId(), user);
            return user;
        }
        LOGGER.info("there was a problem updating the user");
        return null;
    }

    public User getUserByEmail(String email) {
        LOGGER.info("getting user by email");
        AtomicReference<User> user = null;
        Optional<Map.Entry<String, Object>> userEmail = this.storage.data.entrySet().stream().filter((entry)->{
            return entry.getKey().startsWith("user") && ((User)entry.getValue()).getEmail().equals(email);
        }).findFirst();
        return  userEmail.isEmpty()? null : (User) userEmail.get().getValue();
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        LOGGER.info("getting users by name: "+name);
        List<User> users = this.storage.data.entrySet().stream().filter(entry -> {
            boolean isEqual = false;
            if(entry.getKey().startsWith("user")){
                User user = (User)entry.getValue();
                isEqual = user.getName().equals(name);
            }
            return isEqual;
        }).map(element -> {
            return  (User)element.getValue();
        }).collect(Collectors.toList());

        int startEvents = pageNum == 1 ? pageSize : pageNum * pageSize;
        int helpStartE = pageNum == 1 ? 0 : startEvents - pageSize;
        List<User> subList = users.subList(helpStartE,startEvents);
        LOGGER.info("number or users by name found :" + subList.size());
        return subList;
    }
}
