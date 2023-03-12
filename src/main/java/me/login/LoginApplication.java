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
		logger.info("#### Create UI ####");
		JFrame loginFrame = createLoginFrame();
		JFrame registerFrame = createRegisterFrame();

		logger.info("#### Start UI ####");
		loginFrame.setVisible(true);
		registerFrame.setVisible(true);
	}

	private JFrame createLoginFrame() {
		logger.info("#### Create Login UI ####");
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(290, 215);

		frame.setLayout(null);

		JLabel userLabel = new JLabel("Kennung:");
		userLabel.setBounds(10, 10, 80, 25);
		frame.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(10, 35, 250, 25);
		frame.add(userText);

		JLabel passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(10, 70, 80, 25);
		frame.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(10, 95, 250, 25);
		frame.add(passwordText);

		JButton registerButton = new JButton("registrieren...");
		registerButton.setBounds(10, 140, 110, 25);
		frame.add(registerButton);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(150, 140, 110, 25);
		frame.add(loginButton);

		return frame;
	}

	private JFrame createRegisterFrame() {
		logger.info("#### Create Login UI ####");
		JFrame frame = new JFrame("Registrierung");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(290, 275);

		frame.setLayout(null);

		JLabel userLabel = new JLabel("Kennung:");
		userLabel.setBounds(10, 10, 80, 25);
		frame.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(10, 35, 250, 25);
		frame.add(userText);

		JLabel passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(10, 70, 80, 25);
		frame.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(10, 95, 250, 25);
		frame.add(passwordText);

		JLabel passwordConfirmLabel = new JLabel("Passwort bestätigen:");
		passwordConfirmLabel.setBounds(10, 130, 150, 25);
		frame.add(passwordConfirmLabel);

		JPasswordField passwordConfirmText = new JPasswordField(20);
		passwordConfirmText.setBounds(10, 155, 250, 25);
		frame.add(passwordConfirmText);

		JButton cancelButton = new JButton("abbrechen");
		cancelButton.setBounds(10, 200, 110, 25);
		frame.add(cancelButton);

		JButton registerButton = new JButton("registrieren");
		registerButton.setBounds(150, 200, 110, 25);
		frame.add(registerButton);

		return frame;
	}
}
