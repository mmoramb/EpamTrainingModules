package com.deliver.service;


import com.deliver.Service.EventService;
import com.deliver.config.AppConfig;
import com.deliver.dao.EventDao;
import com.deliver.model.Event;
import com.deliver.storage.Storage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EventService.class, EventDao.class, Storage.class})
public class EventServiceTest {
    @Autowired
    private EventService eventService;

    @Test
    public void getEventById(){
        Assert.assertEquals(5L,eventService.getEventById(5L).getId());
    }

    @Test
    public void getElementsByTitle_whenPageIsGreater(){
        List<Event> events = eventService.getEventsByTitle("party5",2,6);
        Assert.assertEquals(0, events.size());
    }

    @Test
    public void getElementsByTitle_whenPageItemsExist(){
        List<Event> events = eventService.getEventsByTitle("party5",2,2);
        Assert.assertEquals("party5", events.get(events.size()-1).getTitle());
    }

    @Test
    public void getElementsByDay_whenPageElementsExists(){
        List<Event> events = eventService.getEventsForDay(new Date(2021,04,10), 2,2);
        Assert.assertEquals(LocalDate.of(2021,04,10), events.get(events.size()-1).getDate());
    }

    @Test
    public void getElementsByDay_whenPageElementsDontExists(){
        Assert.assertEquals(0,eventService.getEventsForDay(new Date(2021,04,10), 2,4).size());
    }

    @Test
    public void testCreateEvent(){
        Event event = eventService.createEvent(new Event(10,"event10", LocalDate.of(2021,06,23)));
        Assert.assertNotNull(event);
        Assert.assertEquals(10L, event.getId());
    }

    @Test
    public void testUpdateEvent(){
        Event event = eventService.createEvent(new Event(10,"event10", LocalDate.of(2021,06,23)));
        Assert.assertNotNull(event);
        event.setTitle("event10Up");
        Event event1 = eventService.updateEvent(event);
        Assert.assertEquals(event.getTitle(), event1.getTitle());
    }
/*
    @Test
    public void testDeleteEvent(){
        Assert.assertTrue(eventService.deleteEvent(9L));
    }
    */
}
