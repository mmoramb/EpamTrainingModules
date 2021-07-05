package com.deliver.storage;

import com.deliver.SpringXMLConfigurationMain;
import com.deliver.model.Category;
import com.deliver.model.Event;
import com.deliver.model.Ticket;
import com.deliver.model.User;
import com.deliver.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
@Component
@PropertySource("classpath:application.properties")
public class Storage implements BeanFactoryPostProcessor {
    private static final Logger LOGGER = LogManager.getLogger(Storage.class);
    @Value("${loadFilePath}")
    private String loadFilePath;

    public Map<String, Object> data = new HashMap<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

      File file = null;
        String content = null;
        try {
            LOGGER.info("reading file from classpath to preload information");
            LOGGER.debug("some log debug info");
            LOGGER.error("some log error info");
            System.out.println(this.loadFilePath);
            file = ResourceUtils.getFile("classpath:preloadInit.txt");
            Stream<String> fileInfo = Files.lines(file.toPath());

            fileInfo.forEach(l -> {
                String[] line = l.split(",");
                switch (line[0]){
                    case "event":
                        Event event = new Event(Long.valueOf(line[1]),line[2],LocalDate.parse(line[3],
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        data.put(line[0]+line[1], event);
                        break;
                    case "ticket":
                        Event event2 = Utils.findEvent(Long.valueOf(line[3]), (HashMap<String, Object>) this.data);
                        User user1 = Utils.findUser(Long.valueOf(line[2]), (HashMap<String, Object>) this.data);
                        Ticket ticket = new Ticket(Long.valueOf(line[1]),user1,event2,
                                Category.valueOf(line[4]),Integer.valueOf(line[5]));
                        data.put(line[0]+line[1], ticket);
                        break;
                    case "user":
                        User user = new User(Long.valueOf(line[1]),line[2],line[3]);
                        data.put(line[0]+line[1], user);
                        break;
                }
            });
            LOGGER.info("finished preloading info from file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
