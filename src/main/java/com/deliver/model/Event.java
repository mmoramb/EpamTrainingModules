package com.deliver.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
//@Component
public class Event {
    //public void init(){}
    //public void cleanup(){}
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    private long id;
    private String title;
    private LocalDate date;

    public Event() {
    }
    public Event(long id, String title, LocalDate date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /*
    long getId();
    void setId(long id);
    String getTitle();
    void setTitle(String title);
    Date getDate();
    void setDate(Date date);
    */
}
