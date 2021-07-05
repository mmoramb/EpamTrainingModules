package com.deliver.mocking;

import com.deliver.Service.EventService;
import com.deliver.dao.EventDao;
import com.deliver.model.Event;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventMockServiceTest {

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private EventService eventService;
    private Event event;
    private List<Event> events;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.event = new Event(10,"eventTitle", LocalDate.now());
        this.events = new ArrayList<>();
        this.events.add(event);
    }

    @Test
    public void givenUsers_thenCreateUser(){
        when(eventDao.addEvent(any(Event.class))).thenReturn(event);
        Event event1 = eventService.createEvent(null);
        Assert.assertNotNull(event1);
        Assert.assertEquals(10, event.getId());
    }

    @Test
    public void givenRequiredParams_thenGetEventsForDay(){
        when(eventDao.getEventsForDay(any(Date.class),any(Integer.class),any(Integer.class)))
                .thenReturn(events);
        List<Event> events1 = eventService.getEventsForDay(null, 1,1);
        Assert.assertNotNull(events1);
        Assert.assertEquals(1, events1.size());
    }

}
