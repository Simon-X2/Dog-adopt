package ui;

import model.Dog;
import model.Event;
import model.EventLog;
import model.ListOfDog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

//SOURCE: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial
// /uiswing/examples/components/ListDemoProject/src/components/ListDemo.java

//Runs Jpanel and creates all the graphical interfaces for the application

public class DogAppGraphicsUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String viewString = "View information";
    private static final String adoptString = "Adopt";
    private static final String loadString = "Load Favourites";
    private static final String saveString = "Save Favourites";
    private static final String viewFavouriteString = "View Favourites";
    private JButton adoptButton;
    private JButton viewButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton viewFavouriteButton;
    private static final String JSON_STORE = "./data/listofdog.json";
    private ListOfDog featuredDog;
    private ListOfDog favouriteList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JScrollPane listScrollPane;
    private JPanel buttonPane;

    // runs j frame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public DogAppGraphicsUI() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        listModel.addElement("shadow");
        listModel.addElement("lightning");
        listModel.addElement("teddy");

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        initializeList();
        initializeButtons();
        intializePanel();

        init();

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        setVisible(true);
        buttonPane.setVisible(true);

    }

    private static void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    //EFFECTS: Create a panel that uses BoxLayout.
    private void intializePanel() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(adoptButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(viewButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.add(viewFavouriteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    //MODIFIES: this
    //EFFECTS: Initializes all buttons and adds a listener to them
    private void initializeButtons() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewListener());

        adoptButton = new JButton(adoptString);
        adoptButton.setActionCommand(adoptString);
        adoptButton.addActionListener(new AdoptListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        viewFavouriteButton = new JButton(viewFavouriteString);
        viewFavouriteButton.setActionCommand(viewFavouriteString);
        viewFavouriteButton.addActionListener(new ViewFavouriteListener());
    }

    //MODIFIES: this
    //EFFECTS: Initializes the default list model and adds a scroll pane to the list
    private void initializeList() {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(list);
    }


    //Effects: creates the adopt action listener that handles the adoption buttons
    class AdoptListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();

            Dog chosenDog = favouriteList.findDog(listModel.getElementAt(index).toString(), featuredDog.getListOfDog());


            if (!alreadyInList(chosenDog)) {
                printPopIUp(chosenDog);
            } else {
                JFrame thisFrame = new JFrame();
                JOptionPane.showMessageDialog(thisFrame, "He / she is already in your list!");
            }


            listModel.remove(index);


            int size = listModel.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                adoptButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //EFFECTS: prints the chosen dog's information in a pop up window
    private void printPopIUp(Dog chosenDog) {
        addFavouriteDog(chosenDog);
        JFrame thisFrame = new JFrame();
        ImageIcon dogPicture = new ImageIcon(chosenDog.getImage());
        dogPicture = resizePhoto(dogPicture);
        JOptionPane.showMessageDialog(thisFrame, "Successfully Adopted!" + "\n" + "Name: " + "\n"
                        + chosenDog.getName() + "\n"
                        + "Breed: " + chosenDog.getBreed() + "\n" + "Color: " + chosenDog.getColor() + "\n"
                        + "Age: " + chosenDog.getAge(), chosenDog.getName(),
                JOptionPane.INFORMATION_MESSAGE, dogPicture);
    }

    //EFFECTS: checks if dog is already in a list
    public boolean alreadyInList(Dog dog) {
        return favouriteList.getListOfDog().contains(dog);
    }

    //Effects: creates the view favourite action listener that handles the viewFavourite buttons
    class ViewFavouriteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFrame thisFrame = new JFrame();
            JOptionPane.showMessageDialog(thisFrame, printAllDog());
            System.out.println("bruh");
        }
    }

    //EFFECTS: prints out all dogs in the favourite list
    private String printAllDog() {
        if (!favouriteList.isEmpty()) {

            String name = "Your favourite list:";

            for (Dog dog : favouriteList.getListOfDog()) {
                name = name + "\n" + dog.getName();
            }
            return name;
        }
        return "Your favourite list is still empty!";
    }

    //Effects: creates the load action listener that handles the load favourites buttons
    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadList();
            JFrame thisFrame = new JFrame();
            JOptionPane.showMessageDialog(thisFrame, "Successfully loaded your favourite list!");
        }
    }

    //Effects: creates the load action listener that handles the save favourites buttons
    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveList();
            JFrame thisFrame = new JFrame();
            JOptionPane.showMessageDialog(thisFrame, "Successfully saved your favourite list!");
        }
    }

    //EFFECTS: Resizes a photo to an icon
    private ImageIcon resizePhoto(ImageIcon dogPicture) {
        ImageIcon imageIcon = dogPicture;
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newImage);
        return imageIcon;
    }

    //Effects: creates the view action listener that handles the view information buttons
    class ViewListener implements ActionListener {
        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();

            Dog chosenDog = featuredDog.findDog(listModel.getElementAt(index).toString(), featuredDog.getListOfDog());

            JFrame thisFrame = new JFrame();
            ImageIcon dogPicture = new ImageIcon(chosenDog.getImage());
            dogPicture = resizePhoto(dogPicture);
            JOptionPane.showMessageDialog(thisFrame, "Name: " + chosenDog.getName() + "\n"
                            + "Breed: " + chosenDog.getBreed() + "\n" + "Color: " + chosenDog.getColor() + "\n"
                            + "Age: " + chosenDog.getAge(), chosenDog.getName(),
                    JOptionPane.INFORMATION_MESSAGE, dogPicture);



            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

    }


    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                adoptButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                adoptButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.

//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JFrame frame = new JFrame("Dog Adoption");
        frame.addWindowListener(new MyWindowListener());
        //Create and set up the content pane.
        JComponent newContentPane = new DogAppGraphicsUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    static class MyWindowListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {

        }

        public void windowClosing(WindowEvent arg0) {
            printLog();
            System.exit(0);

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


    //EFFECTS: initializes the program
    public void init() {
        Dog shadow = new Dog("shadow", "Black", "Terrier", 5,
                "data/blackterrier.jpg");
        Dog teddy = new Dog("teddy", "White", "Poodle", 6, "data/whitepoodle.jpg");
        Dog lightning = new Dog("lightning", "Gold", "Golden Retriever", 2,
                "data/goldenrretriver.jpg");


        featuredDog = new ListOfDog();
        favouriteList = new ListOfDog();

        featuredDog.add(shadow);
        featuredDog.add(teddy);
        featuredDog.add(lightning);
    }


    //REQUIRES: Dog cannot be duplicate
    //MODIFIES: this
    //EFFECT: adds a dog to the favourite list
    private void addFavouriteDog(Dog dog) {
        favouriteList.addDog(dog);
    }


    //EFFECTS: Prints list of dogs to choose from
    public void displayListOfDog(ListOfDog listOfDog) {
        for (Dog d : listOfDog.getListOfDog()) {
            System.out.println(d.getName());
        }
    }

    // *Credit: From JSon sample code
    // EFFECTS: saves the listofdog to file
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(favouriteList);
            jsonWriter.close();
            System.out.println("Saved your favourite list" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads listofdog from file
    // *Credit: From sample code
    private void loadList() {
        try {
            favouriteList = jsonReader.read();
            System.out.println("Loaded favourite list" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



}