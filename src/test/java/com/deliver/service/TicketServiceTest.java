package com.deliver.service;


import com.deliver.Service.EventService;
import com.deliver.Service.TicketService;
import com.deliver.dao.EventDao;
import com.deliver.dao.TicketDao;
import com.deliver.model.Category;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.deliver.model.User;
import com.deliver.storage.Storage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:ApplicationContext.xml"})
@ContextConfiguration(classes = {TicketService.class, TicketDao.class, Storage.class})
public class TicketServiceTest {

    @Autowired
    TicketService ticketService;

    @Test(expected = IllegalStateException.class)
    public void testBookTicket_whenPlaceAlreadyBooked(){
        Ticket ticket = ticketService.bookTicket(2,2,6, Category.PREMIUM);
    }
    @Test
    public void testBookTicket_whenPlaceIsAvailable(){
        Ticket ticket = ticketService.bookTicket(2,2,2, Category.PREMIUM);
        assertTrue(ticket.getEvent().getId() == 2 && ticket.getUser().getId() == 2 && ticket.getPlace() == 2);
    }

    @Test
    public void testBookedTickets_OrderedDescendingByEventDate(){
        User user = new User(1,"userName", "userEmail");
        List<Ticket> usersTicket = this.ticketService.getBookedTickets(user, 2,1);
        usersTicket.forEach(e -> {
            System.out.println(e.getEvent().getId()+" date: "+e.getEvent().getDate());
        });
        assertTrue(usersTicket.get(0).getEvent().getDate().isAfter(usersTicket.get(1).getEvent().getDate()));
        Assert.assertEquals(2, usersTicket.size());
    }

    @Test
    public void testBookedTicketsByEvent_OrderedAscendingByUserEmail(){
        Event event = new Event(4, "eventName", LocalDate.now());
        List<Ticket> usersTicket = this.ticketService.getBookedTickets(event, 2,1);
        assertTrue(usersTicket.get(0).getUser().getEmail().compareTo(usersTicket.get(1).getUser().getEmail()) < 0);
        Assert.assertEquals(2, usersTicket.size());
    }

    @Test
    public void testCancelTicket(){
        assertTrue(ticketService.cancelTicket(6));
    }
}
