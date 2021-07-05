package com.deliver.utils;

import com.deliver.model.Event;
import com.deliver.model.User;

import java.util.HashMap;

public class Utils {

    public static User findUser(long userId, HashMap<String, Object> data) {
        return  (User) data.entrySet().stream().filter(e->{
            boolean isUser = false;
            if (e.getKey().startsWith("user")){
                User user = (User) e.getValue();
                if (user.getId()==userId){
                    isUser = true;
                }
            }
            return isUser;
        }).findFirst().get().getValue();
    }

    public static Event findEvent(long eventId, HashMap<String, Object> data) {
        return  (Event)data.entrySet().stream().filter(e->{
            boolean isEvent = false;
            if (e.getKey().startsWith("event")){
                Event event1 = (Event) e.getValue();
                if (event1.getId()==eventId){
                    isEvent = true;
                }
            }
            return isEvent;
        }).findFirst().get().getValue();
    }
}
