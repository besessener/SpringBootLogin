package me.login;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import me.login.models.IdentificationDataDto;
import me.login.services.AuthenticationService;
import me.login.services.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@SpringBootApplication
public class LoginApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(LoginApplication.class);
    private JFrame loginFrame;
    private JFrame registerFrame;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    private Validator validator;

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(LoginApplication.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            prepareUI();
        });
    }

    private Image getLoginImage() {
        String filename = "login.jpg";
        try {
            File imageFile = new File(filename);
            if(imageFile.exists() && imageFile.isFile()) {
                return ImageIO.read(imageFile);
            }

            return ImageIO.read(getClass().getResourceAsStream("/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void prepareUI() {
        logger.info("#### Create UI ####");
        loginFrame = createLoginFrame();
        registerFrame = createRegisterFrame();

        logger.info("#### Start UI ####");
        loginFrame.setVisible(true);
        registerFrame.setVisible(false);
    }

    private JFrame createLoginFrame() {
        logger.info("#### Create Login UI ####");
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(290, 475);
        frame.setResizable(false);
        ImageIcon imageIcon = new ImageIcon(getLoginImage());
        JLabel loginImageLabel = (new JLabel(imageIcon));
        loginImageLabel.setBounds(10, 10, 256, 256);
        frame.add(loginImageLabel);

        frame.setLayout(null);

        JLabel userLabel = new JLabel("Identification:");
        userLabel.setBounds(10, 280, 150, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(10, 305, 250, 25);
        frame.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 340, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(10, 365, 250, 25);
        frame.add(passwordText);

        JButton registerButton = new JButton("register...");
        registerButton.setBounds(10, 400, 110, 25);
        frame.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.setVisible(false);
                registerFrame.setLocation(loginFrame.getLocation());
                registerFrame.setVisible(true);
            }
        });

        JButton loginButton = new JButton("login");
        loginButton.setBounds(150, 400, 110, 25);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AuthenticationService.AuthenticationStatus authenticationStatus = authenticationService.authenticate(userText.getText(), passwordText.getText());
                String message = "Login failed with reason: " + authenticationStatus;
                if (authenticationStatus.equals(AuthenticationService.AuthenticationStatus.AUTHENTICATED)) {
                    message = "Login succeeded.";
                }

                JOptionPane.showMessageDialog(null, message, "Login Result", 1);
            }
        });

        return frame;
    }

    private JFrame createRegisterFrame() {
        logger.info("#### Create Login UI ####");
        JFrame frame = new JFrame("Registration");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(290, 275);
        frame.setResizable(false);

        frame.setLayout(null);

        JLabel userLabel = new JLabel("Identification:");
        userLabel.setBounds(10, 10, 150, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(10, 35, 250, 25);
        frame.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 70, 80, 25);
        frame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(10, 95, 250, 25);
        frame.add(passwordText);

        JLabel passwordConfirmLabel = new JLabel("Confirmed Password:");
        passwordConfirmLabel.setBounds(10, 130, 150, 25);
        frame.add(passwordConfirmLabel);

        JPasswordField passwordConfirmText = new JPasswordField(20);
        passwordConfirmText.setBounds(10, 155, 250, 25);
        frame.add(passwordConfirmText);

        JButton cancelButton = new JButton("cancel");
        cancelButton.setBounds(10, 200, 110, 25);
        frame.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.setLocation(registerFrame.getLocation());
                loginFrame.setVisible(true);
                registerFrame.setVisible(false);
            }
        });

        JButton registerButton = new JButton("register");
        registerButton.setBounds(150, 200, 110, 25);
        frame.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Set<ConstraintViolation<IdentificationDataDto>> violations = registrationService.registerNewUser(userText.getText(), passwordText.getText(), passwordConfirmText.getText());

                String message = "Registration succeeded.";

                if (null == violations) {
                    message = "Registration failed with reason: User exists already.";
                } else {
                    StringBuilder errorMessage = new StringBuilder();
                    boolean registrationSuccessful = violations.isEmpty();
                    if (registrationSuccessful) {
                        loginFrame.setLocation(registerFrame.getLocation());
                        loginFrame.setVisible(true);
                        registerFrame.setVisible(false);
                    } else {
                        for (String violationString : violations.iterator().next().getMessage().split(",")) {
                            errorMessage.append("\n");
                            errorMessage.append(violationString);
                        }

                        message = "Registration failed with reason:" + errorMessage.toString();
                    }
                }

                JOptionPane.showMessageDialog(null, message, "Login Result", 1);
            }
        });

        return frame;
    }
}
