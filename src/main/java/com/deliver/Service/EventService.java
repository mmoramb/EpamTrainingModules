package com.deliver.Service;

import com.deliver.dao.EventDao;
import com.deliver.facade.BookingFacade;
import com.deliver.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class EventService{
    @Autowired
    private EventDao eventDao;

    /**
     * Gets event by its id.
     * @return Event.
     */
    public Event getEventById(long eventId){
        return eventDao.getEvent(eventId);
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param title Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum){
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     * @param day Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum){
        return this.eventDao.getEventsForDay(day, pageSize,pageNum);
    }

    /**
     * Creates new event. Event id should be auto-generated.
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event){
        return eventDao.addEvent(event);
    }

    /**
     * Updates event using given data.
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    public Event updateEvent(Event event){
        return eventDao.updateEvent(event);
    }

    /**
     * Deletes event by its id.
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    public boolean deleteEvent(long eventId){
        return eventDao.removeEvent(eventId);
    }
}
