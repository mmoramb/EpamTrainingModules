package com.deliver.model;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.crypto.dsig.XMLObject;

/**
 * Created by maksym_govorischev.
 */
@XmlRootElement(name = "ticket")
public class Ticket {//extends XMLObject
    private long id;
    private long eventId;
    private long userId;
    private User user;
    private Event event;
    private Category category;
    private int place;

    public Ticket() {
    }

    public Ticket(long id, User user, Event event, Category category, int place) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.category = category;
        this.place = place;
    }

    /**
     * Ticket Id. UNIQUE.
     * @return Ticket Id.
     */
    @XmlAttribute(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    @XmlAttribute(name = "eventId")
    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
    @XmlAttribute(name = "userId")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    @XmlAttribute(name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @XmlAttribute(name = "place")
    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
    /*
    long getId();
    void setId(long id);
    long getEventId();
    void setEventId(long eventId);
    long getUserId();
    void setUserId(long userId);
    Category getCategory();
    void setCategory(Category category);
    int getPlace();
    void setPlace(int place);
*/
}
