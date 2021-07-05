package com.deliver.dao;

import com.deliver.model.Category;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.deliver.model.User;
import com.deliver.storage.Storage;
import com.deliver.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class TicketDao {
    private static final Logger LOGGER = LogManager.getLogger(TicketDao.class);

    @Autowired
    private Storage storage;

    public boolean removeTicket(long ticketId){
        LOGGER.info("removing ticket");
        if (this.storage.data.containsKey("ticket"+ticketId)){
            if (this.storage.data.remove("ticket"+ticketId) != null)
                return true;
        }
        LOGGER.info("there was a problem removing the ticket");
        return false;
    }

    public Ticket updateTicket(Ticket ticket){
        LOGGER.info("updating ticket");
        if (this.storage.data.containsKey("ticket"+ticket.getId())){
            return (Ticket)this.storage.data.replace("ticket"+ticket.getId(), ticket);
        }
        LOGGER.info("there was a problem updating the ticket");
        return null;
    }

    public Ticket addTicket(long userId, long eventId, int place, Category category) {
        LOGGER.info("adding new ticket");
        Ticket ticket = null;
        boolean isPlaceUsed = this.storage.data.entrySet().stream().filter(e -> {
            if(e.getKey().startsWith("ticket")){
                if (((Ticket)e.getValue()).getPlace() == place)
                    return true;
            }
            return false;
        }).findFirst().isEmpty();
        if (isPlaceUsed){
            Event event2 = Utils.findEvent(Long.valueOf(eventId), (HashMap<String, Object>) this.storage.data);
            User user1 = Utils.findUser(Long.valueOf(userId), (HashMap<String, Object>) this.storage.data);
            Long newVal = this.storage.data.entrySet().stream().filter(e -> {
                if(e.getKey().startsWith("ticket"))
                    return true;
                return false;
            }).count();
            newVal += 1;
            ticket = new Ticket(newVal,user1,event2,category,place);
            this.storage.data.put("ticket"+newVal, ticket);
            LOGGER.info("ticket added correctly");
        }else{
            LOGGER.info("ticket not added, already exists one ticket with the same place");
            throw new IllegalStateException("place for ticket has been used");
        }
        return ticket;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum){
        LOGGER.info("getting tickets by userId");
        int endSublist = pageNum == 1 ? pageSize : pageNum * pageSize;
        int initSubList = pageNum == 1 ? 0 : endSublist - pageSize;

        List<Ticket> tickets = this.storage.data.entrySet().stream().filter(e->{
            boolean isFiltered = false;
            if (e.getKey().startsWith("ticket")){
                Ticket ticket = (Ticket) e.getValue();
                if (ticket.getUser().getId() == user.getId()){
                    isFiltered = true;
                }
            }
            return isFiltered;
        }).map(entry -> {
            return (Ticket)entry.getValue();
        }).sorted((o1, o2) -> {
            return  o2.getEvent().getDate().compareTo(o1.getEvent().getDate());
        }).collect(Collectors.toList());
        LOGGER.info("tickets retreaved successfuly");
        return tickets.subList(initSubList, endSublist > tickets.size() ? tickets.size() : endSublist);
    }
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        LOGGER.info("getting tickets by eventId");
        int startEvents = pageNum == 1 ? pageSize : pageNum * pageSize;
        int helpStartE = pageNum == 1 ? 0 : startEvents - pageSize;

        List<Ticket> tickets = this.storage.data.entrySet().stream().filter(e->{
            boolean isFiltered = false;
            if (e.getKey().startsWith("ticket")){
                Ticket ticket = (Ticket) e.getValue();
                if (ticket.getEvent().getId() == event.getId()){
                    isFiltered = true;
                }
            }
            return isFiltered;
        }).map(entry -> {
            return (Ticket)entry.getValue();
        }).sorted((o1, o2) -> {
            return  o1.getUser().getEmail().compareTo(o2.getUser().getEmail());
        }).collect(Collectors.toList());
        LOGGER.info("tickets retreaved successfully");
        return tickets.subList(helpStartE, startEvents > tickets.size() ? tickets.size() : startEvents);
    }
}
