package com.deliver.dao;

import com.deliver.model.Event;
import com.deliver.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EventDao{
    private static final Logger LOGGER = LogManager.getLogger(EventDao.class);

    @Autowired
    private Storage storage;

    public Event getEvent(long id){
        Event event = null;
        if (this.storage.data.containsKey("event"+id))
            event = (Event) this.storage.data.get("event"+id);

        LOGGER.info("getting event by id");
        return event;
    }

    public Event addEvent(Event event){
        this.storage.data.putIfAbsent("event"+event.getId(), event);
        return event;
    }

    public boolean removeEvent(long eventId){
        if(this.storage.data.remove("event"+eventId) != null)
            return true;

        LOGGER.info("event wans't removed correctly");
        return false;
    }

    public Event updateEvent(Event event){
        if (this.storage.data.containsKey("event"+event.getId())){
            this.storage.data.replace("event"+event.getId(), event);
            return event;
        }
        LOGGER.info("event wasn't updated correctly");
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        int startEvents = pageNum == 1 ? pageSize : pageNum * pageSize;
        int helpStartE = pageNum == 1 ? 0 : startEvents - pageSize;
        AtomicInteger counter = new AtomicInteger(0);

        List<Event> events = new ArrayList<>();
        this.storage.data.forEach((key, val)->{
            if (key.startsWith("event")){
                Event event = (Event)val;
                if (event.getTitle().equals(title)) {
                    if (counter.get() >= helpStartE && counter.get() < startEvents){
                        events.add(event);
                    }
                    counter.addAndGet(1);
                }
            }
        });
        LOGGER.info("retreaving events by title");
        return events;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        int startEvents = pageNum == 1 ? pageSize : pageNum * pageSize;
        int helpStartE = pageNum == 1 ? 0 : startEvents - pageSize;
        AtomicInteger counter = new AtomicInteger(0);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(day);
        LocalDate date = LocalDate.of(day.getYear(), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        List<Event> events = new ArrayList<>();
        this.storage.data.forEach((key, val)->{
            if (key.startsWith("event")){
                Event event = (Event)val;
                if (event.getDate().isEqual(date)){
                    if (counter.get() >= helpStartE && counter.get() < startEvents){
                        events.add(event);
                    }
                    counter.addAndGet(1);
                }
            }
        });
        LOGGER.info("retreaving event by day");
        return events;
    }
}
