package com.deliver.suit;

import com.deliver.service.EventServiceTest;
import com.deliver.service.TicketServiceTest;
import com.deliver.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EventServiceTest.class, UserServiceTest.class, TicketServiceTest.class})
public class ServiceSuitTest {
}
