package com.flexdevit.relay42.iot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotCollectorServer {
    public static void main( String[] args ) {
        SpringApplication.run(IotCollectorServer.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.save(new User("massimo", "123456", Role.ADMIN));
//            userService.save(new User("koen", "123456", Role.USER));
//        };
//    }
}
