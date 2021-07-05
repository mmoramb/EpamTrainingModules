package com.deliver.facade;

import com.deliver.Service.TicketService;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.itextpdf.text.DocumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class BookingController {
    private static final Logger LOGGER = LogManager.getLogger(BookingController.class);
    private TicketService ticketService;

    public BookingController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping(value = "getTicketsByEvent", headers = "accept=application/pdf")
    public ModelAndView getBookedTickets(@RequestParam int eventId, @RequestParam int pageSize,
                                         @RequestParam int pageNum) {
        LOGGER.info("on method to get the pdf");
        ModelAndView andView = new ModelAndView();
        Event event = new Event();
        event.setId(eventId);
        List<Ticket> tickets = this.ticketService.getBookedTickets(event, pageSize, pageNum);
        LOGGER.info("information for pdf ready");
        andView.setViewName("tickets");
        andView.addObject("tickets", tickets);

        return andView;
    }
}
