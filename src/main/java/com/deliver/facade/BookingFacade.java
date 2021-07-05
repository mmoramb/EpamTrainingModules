package com.deliver.facade;

import com.deliver.Service.EventService;
import com.deliver.Service.TicketService;
import com.deliver.Service.UserService;
import com.deliver.dao.EventDao;
import com.deliver.exception.CustomException;
import com.deliver.marshaller.Marshalling2;
import com.deliver.model.Category;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.deliver.model.User;
import com.itextpdf.text.DocumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Groups together all operations related to tickets booking.
 * Created by maksym_govorischev.
 */
@Controller
@PropertySource("classpath:application.properties")
public class BookingFacade {
    private static final Logger LOGGER = LogManager.getLogger(BookingFacade.class);

    @Autowired
    private Marshalling2 marshalling2;

    @Value("${preloadTickets}")
    private String loadTicketsPath;

    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;

    public BookingFacade(EventService eventService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index(){
        LOGGER.info("on index page");
        return "index";
    }

    @GetMapping("/exportPdf")
    public void exportPdf(@RequestParam int eventId, @RequestParam int pageSize,
                          @RequestParam int pageNum, HttpServletResponse servletResponse) throws DocumentException, IOException {
        LOGGER.info("on method to generate pdf");
        servletResponse.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ticketsByEvent.pdf";
        servletResponse.setHeader(headerKey, headerValue);

        Event event = new Event();
        event.setId(eventId);
        List<Ticket> tickets = this.ticketService.getBookedTickets(event, pageSize,pageNum);
        LOGGER.info("information for pdf retreaved");
        UserPDFExporter exporter = new UserPDFExporter(tickets);
        LOGGER.info("pdf created");
        exporter.export(servletResponse);
    }

    @GetMapping("/exceptionHandling")
    public String exceptionHandling(){
        if (1==1){
            throw new CustomException("throwing a exception");
        }
        return "index";
    }

    /**
     * Gets event by its id.
     *
     * @return Event.
     */
    @GetMapping("/event/{eventId}")
    public String getEventById(@PathVariable long eventId, Model model) {
        LOGGER.info("start getting event by id");
        Event event = eventService.getEventById(eventId);
        if (event != null){
            LOGGER.info("event found and retreaved successfully");
        }
        model.addAttribute("event", event);

        return "event";
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param title    Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    @GetMapping("/events")
    public String getEventsByTitle(@RequestParam(name = "title") String title,@RequestParam(name = "pageSize") int pageSize,
                                   @RequestParam(name = "pageNum") int pageNum, Model model) {
        LOGGER.info("starting getEventsByTitle");
        List<Event> events = this.eventService.getEventsByTitle(title,pageSize,pageNum);
        LOGGER.info("events retraved successfully");
        model.addAttribute("events", events);
        return "events";
    }

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    @GetMapping("/eventsByDay")
    public String getEventsForDay(@RequestParam(name = "day") String day,@RequestParam(name = "pageSize") int pageSize,
                                       @RequestParam(name = "pageNum") int pageNum, Model model) {
        LOGGER.info("starting request eventsByDay");
        String[] date = day.split("/");
        List<Event> events = this.eventService.getEventsForDay(new Date(Integer.valueOf(date[0]),Integer.valueOf(date[1]),Integer.valueOf(date[2])),pageSize,pageNum);
        LOGGER.info("events by day retreaved correctly");
        model.addAttribute("events", events);
        return "events";
    }

    /**
     * Updates event using given data.
     *
     * @param id event id.
     * @param title event title
     * @param date event date
     * @return Updated Event object.
     */
    @GetMapping("/updateEvent")
    public String updateEvent(@RequestParam long id, @RequestParam String title,
                             @RequestParam  String date) {
        LOGGER.info("starting request updateEvent");
        String[] dateInfo = date.split("/");
        Event event = new Event(id,title, LocalDate.of(Integer.valueOf(dateInfo[0]),
                Integer.valueOf(dateInfo[1]),Integer.valueOf(dateInfo[2])));
        this.eventService.updateEvent(event);
        LOGGER.info("event updated correctly");
        return "index";
    }

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    @DeleteMapping("/deleteEvent/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {
        LOGGER.info("starting request  deleteEvent");
        if (this.eventService.deleteEvent(eventId)){
            LOGGER.info("event has been deleted");
        }
        return "redirect:index";
    }

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param id event id.
     * @param title event title
     * @param date event date
     * @return index page
     */
    //@PostMapping("/event")
    @GetMapping("/createEvent")
    public String createEvent(@RequestParam long id, @RequestParam String title,
                              @RequestParam  String date) {
        LOGGER.info("starting request createEvent");
        String[] dateInfo = date.split("/");
        Event event = new Event(id,title, LocalDate.of(Integer.valueOf(dateInfo[0]),
                Integer.valueOf(dateInfo[1]),Integer.valueOf(dateInfo[2])));
        this.eventService.createEvent(event);
        LOGGER.info("event created correctly");
        return "index";
    }

    /**
     * Gets user by its id.
     *
     * @return User.
     */
    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable long userId, Model model) {
        LOGGER.info("starting request getUserById");
        User user = userService.getUserById(userId);
        if (user != null){
            LOGGER.info("user was found correctly");
        }
        model.addAttribute("user", user);

        return "user";
    }

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @return User.
     */
    @GetMapping("/usersByEmail")
    public String getUserByEmail(@RequestParam(name = "email") String email, Model model) {
        LOGGER.info("starting request usersByEmail");
        User user = this.userService.getUserByEmail(email);
        if (user != null){
            LOGGER.info("user found correctly");
        }
        model.addAttribute("user", user);
        return "user";
    }

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    @GetMapping("/users")
    public String getUsersByName(@RequestParam String name,@RequestParam int pageSize,
                                 @RequestParam int pageNum, Model model) {
        LOGGER.info("starting request users");
        List<User> users = this.userService.getUsersByName(name,pageSize,pageNum);
        LOGGER.info("user found correctly");
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param id user id.
     * @param name user name
     * @param email user email
     * @return Created User object.
     */
    @GetMapping("/createUser")
    public String createUser(@RequestParam long id,@RequestParam String name,
                           @RequestParam String email) {
        LOGGER.info("starting request createUser");
        User user = new User(id, name, email);
        this.userService.createUser(user);
        LOGGER.info("user created correctly");
        return "index";
    }

    /**
     * Updates user using given data.
     *
     * @param id user id
     * @param name user name
     * @param email user email
     * @return Updated User object.
     */
    @GetMapping("/updateUser")
    public String updateUser(@RequestParam long id,@RequestParam String name,
                           @RequestParam String email) {
        LOGGER.info("starting request updateUser");
        User user = new User(id, name, email);
        this.userService.updateUser(user);
        LOGGER.info("user updated correctly");
        return "index";
    }

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    @DeleteMapping(value = "/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        LOGGER.info("starting request delete user");
        if (this.userService.deleteUser(userId)){
            LOGGER.info("user deleted correctly");
        }

        return "index";
    }

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param userId   User Id.
     * @param eventId  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws IllegalStateException if this place has already been booked.
     */
    @GetMapping("/bookTicket")
    public String bookTicket(@RequestParam long userId,@RequestParam long eventId,@RequestParam int place,
                             @RequestParam Category category) {
        LOGGER.info("starting request bookTicket");
        this.ticketService.bookTicket(userId,eventId,place,category);
        LOGGER.info("ticket booked correctly");
        return "index";
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param userId     User id
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @param model model to load the data to be returned
     * @return List of Ticket objects.
     */
    @GetMapping("ticketsByUser")
    public String getBookedTickets(@RequestParam long userId,@RequestParam int pageSize,
                                   @RequestParam int pageNum, Model model) {
        LOGGER.info("starting request ticketsByUser");
        User user = new User();
        user.setId(userId);

        List<Ticket> tickets = this.ticketService.getBookedTickets(user, pageSize, pageNum);
        LOGGER.info("tickets by user retrieved successfully");
        model.addAttribute("tickets", tickets);

        return "tickets";
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param eventId    Event id
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @param model user to attach the information to be returned
     * @return List of Ticket objects.
     */
    @GetMapping("ticketsByEvent")
    public String getBookedTickets(@RequestParam int eventId,@RequestParam int pageSize,
                                         @RequestParam int pageNum, Model model) {
        LOGGER.info("starting request ticketsByEvent");
        Event event = new Event();
        event.setId(eventId);
        List<Ticket> tickets = this.ticketService.getBookedTickets(event, pageSize, pageNum);
        model.addAttribute("tickets", tickets);
        LOGGER.info("ticketsByEvent retrieved successfully");
        return "tickets";
    }

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    @DeleteMapping("/cancelTicket/{ticketId}")
    public String cancelTicket(@PathVariable Long ticketId) {
        LOGGER.info("strating request cancelTicket");
        if (this.ticketService.cancelTicket(ticketId)){
            LOGGER.info("ticket deleted successfully");
        }
        return "redirect:/index";
    }

    @GetMapping("/preloadTickets")
    public String preloadTickets() {
        LOGGER.info("starting request preloadTickets");
        List<Ticket> tickets = null;
        try {
            tickets = marshalling2.unmarshalling().getTickets();
            if (tickets.size() > 0){
                for (Ticket t:tickets) {
                    Ticket ticket = this.ticketService.bookTicket(t.getUserId(),t.getEventId(),t.getPlace(),t.getCategory());
                    System.out.println(ticket.getId());
                }
            }
            LOGGER.info("tickets loaded successfully");
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }

        return "index";
    }

}
