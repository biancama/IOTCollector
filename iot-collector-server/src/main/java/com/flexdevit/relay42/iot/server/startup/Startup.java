package com.flexdevit.relay42.iot.server.startup;

import com.flexdevit.relay42.iot.server.model.security.Role;
import com.flexdevit.relay42.iot.server.model.security.User;
import com.flexdevit.relay42.iot.server.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Startup {
    @Autowired
    private UserService userService;
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        userService.save(new User("massimo", "123456", Role.ADMIN));
        userService.save(new User("koen", "123456", Role.USER));
    }
}
