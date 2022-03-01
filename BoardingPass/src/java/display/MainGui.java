package display;

import domain.BoardingPass;
import domain.BoardingPassTicket;
import domain.Person;
import services.LocationAPI;
import services.ScannerFile;
import services.UseFile;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainGui implements ActionListener {
    private JButton buttonEnter, buttonClose;
    private JTextField tfName, tfEmail, tfPhone, tfGender, tfAge, tfDate, tfDestination, tfDeparture;
    private JComboBox gender;

    public JPanel panelOne() {
        // Button
        buttonEnter = new JButton("Enter");
        buttonClose = new JButton("Close");

        // Combo-box
        ArrayList<String> g = new ArrayList<>(){{
            add("Male");
            add("Female");
        }};
        gender = new JComboBox(g.toArray());

        // Text-field
        tfName = new JTextField();
        tfEmail = new JTextField();
        tfPhone = new JTextField();
        tfGender = new JTextField();
        tfAge = new JTextField();
        tfDate = new JTextField();
        tfDestination = new JTextField();
        tfDeparture = new JTextField();

        // Action Listener
        //Handler handle = new Handler();
        buttonEnter.addActionListener(this);
        buttonClose.addActionListener(this);

        // Labels:
        JLabel blank = new JLabel("");
        JLabel labelHeader = new JLabel("Please Enter Your Information Below 👇👇👇👇👇👇👇");
        labelHeader.setForeground(new Color(0x0bb000));
        // Name
        JLabel labelName = new JLabel("Name:");
        // Email
        JLabel labelEmail = new JLabel("Email: (example@example.com)");
        // Phone
        JLabel labelPhone = new JLabel("Phone #: (10 digits)");
        // Gender
        JLabel labelGender = new JLabel("Gender:");
        // Age
        JLabel labelAge = new JLabel("Age:");
        // Date
        JLabel labelDate = new JLabel("Date: (MM/DD/YY)");
        // Destination
        JLabel labelDestination = new JLabel("Destination:");
        // Departure Time
        JLabel labelDepartureTime = new JLabel("Departure Time: (HH:MM)");

        // Panel:
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(10, 2, 10, 0));
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.add(labelHeader);
        panel.add(blank);
        panel.add(labelName);
        panel.add(tfName);
        panel.add(labelEmail);
        panel.add(tfEmail);
        panel.add(labelPhone);
        panel.add(tfPhone);
        panel.add(labelGender);
        panel.add(gender);
        panel.add(labelAge);
        panel.add(tfAge);
        panel.add(labelDate);
        panel.add(tfDate);
        //panel.add(labelOrigin); panel.add(tfOrigin);
        panel.add(labelDestination);
        panel.add(tfDestination);
        panel.add(labelDepartureTime);
        panel.add(tfDeparture);
        panel.add(buttonClose);
        panel.add(buttonEnter);
        panel.setVisible(true);

        return panel;
    }

    public JPanel panelTwo(BoardingPassTicket bp) {
        // Ticket label
        JLabel labelPrice = new JLabel("Price: $" + String.valueOf(bp.getBp().getTotalPrice()));
        JLabel labelPhone = new JLabel("Your Phone #: " + String.valueOf(bp.getPerson().getPhone()));
        JLabel labelDeparture = new JLabel("Departure Time: " + bp.getBp().getDeparture());
        JLabel labelName = new JLabel("Your Name: " + bp.getPerson().getName());
        JLabel labelId = new JLabel("Your ID: " + String.valueOf(bp.getBp().getId()));
        JLabel labelAge = new JLabel( "Your Age: " + String.valueOf(bp.getPerson().getAge()));
        JLabel labelGender = new JLabel("Gender: " + bp.getPerson().getGender());
        JLabel labelOrigin = new JLabel("Origin: " + bp.getBp().getOrigin());
        JLabel labelDestination = new JLabel("Destination: " + bp.getBp().getDestination());
        JLabel labelEta = new JLabel("ETA: " + bp.getBp().getEta());

        // Panel two
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(5, 2, 10, 0));
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.add(labelId); panel.add(labelPrice);
        panel.add(labelName); panel.add(labelPhone);
        panel.add(labelAge); panel.add(labelGender);
        panel.add(labelOrigin); panel.add(labelDestination);
        panel.add(labelDeparture); panel.add(labelEta);

        return panel;
    }

    private void Frame() {
            // Input Frame:
            JFrame iframe = new JFrame();
            iframe.add(panelOne(), BorderLayout.CENTER);
            //iframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            iframe.setTitle("Boarding Pass");
            iframe.pack();
            iframe.setSize(670, 500);
            iframe.setVisible(true);
            iframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            //return iframe;
    }

    private void checkName(String name) throws  IllegalArgumentException{
        boolean isAlphabetical = name.chars().mapToObj(c -> (char) c).map(c -> Character.isAlphabetic(c))
                .reduce(true, (last, next) -> last && next);
        if(name.length() == 0){
            throw new IllegalArgumentException("Invalid name: You must input at least one character into the name " +
                    "field.");
        }
        if(!isAlphabetical){
            throw new IllegalArgumentException("Invalid name: All characters in your response must be alphabetical.");
        }
    }

    private void checkEmail(String email) throws IllegalArgumentException{
        Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = emailPattern.matcher(email);
        if(!matcher.matches()){
            throw new IllegalArgumentException("Invalid email. Please follow the pattern xxxx@xxx.com");
        }
    }

    private void checkNumber(String number) throws IllegalArgumentException{
        long numDigits = (long) number.chars().mapToObj(c -> (char) c).filter(c -> Character.isDigit(c)).count();
        if(numDigits != 10){
            throw new IllegalArgumentException("Invalid number. Your response must contain 10 digits.");
        }
    }

    private void checkAge(String age) throws IllegalArgumentException{
        boolean isAllDigits = age.chars().mapToObj(c -> (char) c).map(c -> Character.isDigit(c) && !c.equals('-'))
                .reduce(true, (last, next) -> last && next);
        if(!isAllDigits){
            throw new IllegalArgumentException("Invalid age. You input must be a positive whole number containing all " +
                    "digits");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ScannerFile.ScannerGui sf = new ScannerFile.ScannerGui();
        UseFile useFile = new UseFile();
        LocationAPI locationAPI = new LocationAPI();
        BoardingPassTicket bpt = null;
        try {
            bpt = sf.getUserInput(500);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Enter Button
        if (e.getSource() == buttonEnter) {

            //Standard Price Set to 500;
            String personName = tfName.getText();
            String email = tfEmail.getText();
            String phoneNumber = tfPhone.getText();
            String age = tfAge.getText();
            this.checkName(personName);
            this.checkEmail(email);
            this.checkNumber(phoneNumber);
            this.checkAge(age);
            Person p = new Person(tfName.getText(), tfEmail.getText(),
                    Long.parseLong(tfPhone.getText().replaceAll("[^\\d]","")), String.valueOf(gender.getSelectedItem()),
                    Integer.parseInt(tfAge.getText().replaceAll("[^\\d]","")));
            bpt.setPerson(p);
            //Osita//tfOrigin.getText() -> location string array
            //bpt.getBp().setOrigin(location[new Random().nextInt(location.length)]);
            bpt.getBp().setDate(tfDate.getText());
            bpt.getBp().setDestination(tfDestination.getText());
            bpt.getBp().setDeparture(tfDeparture.getText());
            //ETA based on Google API distance and 500mph flight speed
            try {
                locationAPI.generateETA(bpt);
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            catch (IllegalArgumentException iae){
                System.out.println(iae.getMessage());
                iae.printStackTrace();
            }
            //Prints Ticket
            try {
                useFile.printBoardingPassTicket(bpt);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Result Frame:
            JFrame rFrame = new JFrame();
            rFrame.add(panelTwo(bpt), BorderLayout.CENTER);
            //rFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            rFrame.setTitle("Your Ticket");
            rFrame.pack();
            rFrame.setSize(670, 500);
            rFrame.setVisible(true);
            rFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            //System.exit(0);
        }
        // Close Button
        if (e.getSource() == buttonClose) {
            System.exit(0);
        }
    }

    public MainGui() {
        Frame();
    }
}



