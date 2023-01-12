package ui;

import static ui.DogAppGraphicsUI.createAndShowGUI;

//runs the whole adopt dog program
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
