package com.deliver.integration;

import com.deliver.Service.EventService;
import com.deliver.Service.TicketService;
import com.deliver.Service.UserService;
import com.deliver.config.AppConfig;
import com.deliver.facade.BookingFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
@ContextConfiguration(classes = {BookingFacade.class, EventService.class, UserService.class,
        TicketService.class, EventDao.class, UserDao.class, TicketDao.class, Storage.class})
*/

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//@SpringBootTest
//@AutoConfigureWebMvc
//@WebMvcTest(BookingFacade.class)

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class BookingFacadeTest {
    private MockMvc mockMvc;
    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;
    //@Autowired
    //private BookingFacade bookingFacade;
    @Before
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(new BookingFacade(eventService,
                ticketService, userService)).build();
    }

    @Test
    public void testBooking() throws Exception {
        mockMvc.perform(get("/user/2"))
                .andExpect(status().isOk());
                // /user/2
/*
        User user = new User(7, "user7", "tickets6@gmail.com");
        //User user1 = bookingFacade.createUser(user);
        //Assert.assertNotNull(user1);
        Event event = new Event(9,"party6", LocalDate.now());
        //Event event1 = bookingFacade.createEvent(event);
        //Assert.assertNotNull(event1);
        bookingFacade.bookTicket(user.getId(), event.getId(), 2, Category.STANDARD);
        //Assert.assertNotNull(ticket);
        //bookingFacade.cancelTicket(ticket.getId());
        //List<Ticket> tockets = bookingFacade.getBookedTickets(user, 2,1);
        //Assert.assertTrue(tockets.size() == 0);
*/
    }
}
