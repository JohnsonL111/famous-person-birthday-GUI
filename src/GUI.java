import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GUI implements ActionListener {
    DatePicker datePicker = new DatePicker();

    JFrame appFrame;
    JLabel text;

    /**
     * Constructor that creates the GUI
     */
    public GUI() {
        // Define basic dimensions etc.
        appFrame = new JFrame("CMPT 213 Birthday Tracker");
        appFrame.setSize(620, 200);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setVisible(true);

        // restricts date selection to only 2021
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        LocalDate beginning = LocalDate.of(2021, 1, 1);
        LocalDate ending = LocalDate.of(2021, 12, 31);
        datePicker = new DatePicker(datePickerSettings);
        datePickerSettings.setDateRangeLimits(beginning, ending);

        // sets up our panels.
        JPanel topPanel = new JPanel();
        appFrame.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        appFrame.add(bottomPanel, BorderLayout.SOUTH);

        // Adds our date panel to the left.
        JPanel datePanel = new JPanel();
        datePanel.add(datePicker);
        bottomPanel.add(datePanel, BorderLayout.WEST);
        text = new JLabel("Enter the date and press the 'Check Birthday' button");
        topPanel.add(text);

        // Adds our button to the right.
        JButton confirmBtn = new JButton("Check Birthday");
        bottomPanel.add(confirmBtn, BorderLayout.EAST);
        confirmBtn.addActionListener(this);

        // Sets up the top panel.
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Famous Person with the same birthday.");
        Border compound = BorderFactory.createCompoundBorder(
                emptyBorder, titledBorder);
        topPanel.setBorder(compound);
    }

    /**
     * Handles clicking button.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // For button clicks.
        switch (e.getActionCommand()) {
            case "Check Birthday":
                // Parse date data.
                LocalDate date = datePicker.getDate();
                checkBirthdayValid(date);
                break;
        }
    }

    // throws dialog if null date. Else parse file to see if birthday exits and paste onto screen.
    private void checkBirthdayValid(LocalDate date) {
        if (date == null) {
            JOptionPane.showMessageDialog(appFrame, "Date cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            // parse selected date info.
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            String dateString = month + "," + day;
            FamousPerson person = checkFile(dateString);

            // Handles cases for fil parsing result.
            if (person == null) {
                text.setText("No one on the system was born on this day.");
            }else {
                text.setText(person.toString());
            }
        }
    }

    // parses and checks if there is a birthday match.
    public FamousPerson checkFile(String birthday) {
        try {
            File file = new File("famousBDays.txt");
            Scanner parser = new Scanner(file);
            int i = 0;
            // checks if birthday matches.
            while (parser.hasNextLine()) {
                String data = parser.nextLine();
                if (i == 0 || i%3 == 0) {
                    String[] split = data.split(",");
                    String date = split[1] + "," + split[2];

                    // Gets date as LocalDate and creates new FamousPerson object.
                    if (date.equals(birthday)) {
                        int year = 0;
                        int month = 0;
                        int day = 0;
                        try{
                            year = Integer.parseInt(split[0]);
                            month = Integer.parseInt(split[1]);
                            day = Integer.parseInt(split[2]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                        LocalDate localDate = LocalDate.of(year, month, day);
                        String name = parser.nextLine();
                        String knownFor = parser.nextLine();
                        return new FamousPerson(name, localDate, knownFor);
                    }
                }
                ++i;
            }

            // always close file
            parser.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // If object DNE .
        return null;
    }


}
