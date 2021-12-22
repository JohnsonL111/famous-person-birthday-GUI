import javax.swing.*;

public class BirthdayTrackerApp {
    /**
     * Runs our application.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // create the frame on the event dispatching thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
