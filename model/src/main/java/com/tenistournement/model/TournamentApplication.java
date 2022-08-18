package com.tenistournement.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.tenistournement.model.greeting.*;

@SpringBootApplication
public class TournamentApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TournamentApplication.class, args);
		GreetingClient greetingClient = context.getBean(GreetingClient.class);
		// We need to block for the content here or the JVM might exit before the message is logged
		System.out.println(">> message = " + greetingClient.getMessage().block());
		//SpringApplication.run(ModelApplication.class, args);
	}

}
