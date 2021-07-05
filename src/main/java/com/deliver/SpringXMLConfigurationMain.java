package com.deliver;

import com.deliver.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SpringXMLConfigurationMain implements WebApplicationInitializer {
    /* private static final Logger LOGGER = LogManager.getLogger(SpringXMLConfigurationMain.class);

     public static void main( String[] args ){
         ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("");
         applicationContext.registerShutdownHook();*/
        /*ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ac.close();
    }
*/
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("myServlet",
                new DispatcherServlet(context));
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/");
    }
}
