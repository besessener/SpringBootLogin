package me.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

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
		startUI();
	}

	private void startUI() {
		logger.info("#### Start UI ####");
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(290, 150);

		frame.setLayout(null);

		JLabel userLabel = new JLabel("Kennung:");
		userLabel.setBounds(10, 10, 80, 25);
		frame.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		frame.add(userText);

		JLabel passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(10, 40, 80, 25);
		frame.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		frame.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(10, 80, 80, 25);
		frame.add(loginButton);

		JButton registerButton = new JButton("neuer Zugang");
		registerButton.setBounds(180, 80, 80, 25);
		frame.add(registerButton);

		frame.setVisible(true);
	}
}
