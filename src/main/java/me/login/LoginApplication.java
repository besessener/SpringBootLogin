package me.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(LoginApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("#### HUI ####");
	}
}
