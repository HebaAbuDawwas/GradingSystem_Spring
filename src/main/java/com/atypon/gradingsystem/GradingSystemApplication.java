package com.atypon.gradingsystem;

import com.atypon.gradingsystem.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GradingSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(GradingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {


            StudentService service = ctx.getBean(StudentService.class);
            service.addInitialData();
            //service.validateUserLogin("hdawwas","hrmad");
        };
    }
}
