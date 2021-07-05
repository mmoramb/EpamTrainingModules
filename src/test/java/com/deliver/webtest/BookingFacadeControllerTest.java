package com.deliver.webtest;

import com.deliver.Service.EventService;
import com.deliver.Service.TicketService;
import com.deliver.Service.UserService;
import com.deliver.config.AppConfig;
import com.deliver.facade.BookingFacade;
import com.deliver.model.Category;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.deliver.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class BookingFacadeControllerTest {
    private MockMvc mockMvc;
    @Mock
    private EventService eventService;
    @Mock
    private TicketService ticketService;
    @Mock
    private UserService userService;

    private User user;
    private Event event;
    private Ticket ticket;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new BookingFacade(eventService,
                ticketService, userService)).build();

        this.user = new User(7, "ticketUser", "ticketUser@gmail.com");
        this.event = new Event(10, "ticketEvent", LocalDate.now());
        this.ticket = new Ticket(13, user, event, Category.PREMIUM, 15);
    }
    @Test
    public void whenEventAndUserExistsAndPlaceNotUsed_thenBookTicket() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);
        when(eventService.createEvent(any(Event.class))).thenReturn(event);
        when(ticketService.bookTicket(any(Long.class),any(Long.class),any(Integer.class),any(Category.class)))
                .thenReturn(ticket);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id","7");
        params.add("name","ticketUser");
        params.add("email","ticketUser@gmail.com");
        mockMvc.perform(get("/createUser").params(params))
                .andExpect(status().isOk());

        MultiValueMap<String, String> paramsEvent = new LinkedMultiValueMap<>();
        paramsEvent.add("id","10");
        paramsEvent.add("title","ticketEvent");
        paramsEvent.add("date","2021/07/05");
        mockMvc.perform(get("/createEvent").params(paramsEvent))
                .andExpect(status().isOk());

        MultiValueMap<String, String> paramsTicket = new LinkedMultiValueMap<>();
        paramsTicket.add("userId","7");
        paramsTicket.add("eventId","10");
        paramsTicket.add("place","15");
        paramsTicket.add("category","PREMIUM");

        mockMvc.perform(get("/bookTicket").params(paramsTicket))
                .andExpect(status().isOk());
    }

    @Test
    public void testBooking() throws Exception {
        when(userService.getUserById(any(Long.class))).thenReturn(user);

        mockMvc.perform(get("/user/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenEmailParameter_thenUsersByEmailStatus200() throws Exception {
        when(userService.getUserByEmail(any(String.class)))
                .thenReturn(this.user);

        mockMvc.perform(get("/usersByEmail").param("email","tickets5@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().size(1));
    }

    @Test
    public void whenPdf_thenReturnAplicationpdf() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("eventId","4");
        params.add("pageSize","2");
        params.add("pageNum", "1");
        when(ticketService.getBookedTickets(any(Event.class),any(Integer.class),any(Integer.class)))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/exportPdf").params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/pdf"));
    }

}
