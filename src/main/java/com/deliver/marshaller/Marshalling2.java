package com.deliver.marshaller;

import com.deliver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBContextFactory;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class Marshalling2 {
    public TicketListContainer unmarshalling() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(TicketListContainer.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = ResourceUtils.getFile("classpath:preloadTickets.xml");
        TicketListContainer ticketListContainer = (TicketListContainer) unmarshaller.unmarshal(file);

        return ticketListContainer;
    }
}
