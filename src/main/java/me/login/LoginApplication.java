package me.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class LoginApplication {

	private Logger logger = LoggerFactory.getLogger(LoginApplication.class);

	public static void main(String[] args) {
		try {
			new LoginApplication().run(args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void run(String... args) throws Exception {
		logger.info("#### Start UI ####");
		JFrame mainFrame = new JFrame();
		mainFrame.setVisible(true);
	}
}
