import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * GUI application for finding routes between stations in the MetroLink network.
 * 
 * The interface is divided into two sections:
 *   Left side: Input fields for station names, search options (checkboxes), and buttons.
 *   Right side: A text area for displaying the resulting route.
 * 
 * Users can select to find either the shortest path or the path with fewest line changes.
 */

public class GUI extends JFrame implements ActionListener {
    
    private JTextArea resultTextArea = new JTextArea();
    private JTextField startField = new JTextField();
    private JTextField endField = new JTextField();
    private JCheckBox shortTimeCheckBox = new JCheckBox();
    private JCheckBox fewChangeCheckBox = new JCheckBox();
    private Graph metroGraph = new Graph();
    private JButton clearButton = new JButton();
    private JButton findPathButton = new JButton();
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JButton delayButton = new JButton();
    private JButton closureButton = new JButton();


    /**
     * Constructor for the GUI
     * @param filePath the path of the CSV file that includes MetroLink data
     */
    public GUI(String filePath){
        
        // Initialising some fonts to be used in the GUI
        Font Serfi = new Font("Serif", Font.ITALIC, 15);
        Font Serfi_three = new Font("Serif", Font.ITALIC, 16);
        Font Serfi_two = new Font("Serif", Font.PLAIN, 15);
        
        // Declaring some dimensions
        Dimension size = new Dimension(300,35);
        Dimension size_two = new Dimension(200,35);


        /*
         * Set title, font, size, layout of the GUI
         */
        this.setTitle("Journey Planner");
        this.setFont(Serfi);
        this.setSize(900, 500);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*
         * Read the CSV file and create the metrolink graph
         */
        CSVReader reader = new CSVReader();
        this.metroGraph = reader.getGraph(filePath, resultTextArea);

        

        // Create result panel
        JPanel resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createTitledBorder("Output Path"));

        // Set font and borders of the result text area
        resultTextArea.setFont(Serfi_two);
        this.resultTextArea.setEditable(false);
        resultTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Put the result text area inside scroll pane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(450,440));
        resultPanel.add(scrollPane);


        /*
         * Set buttons texts and fonts
         * Set buttons size
         * Set listeners for buttons
         */
        this.findPathButton.setText("Find Path");
        this.findPathButton.setFont(Serfi);
        this.findPathButton.setPreferredSize(size);
        this.findPathButton.addActionListener(this); 
        
        this.clearButton.setText("Clear");
        this.clearButton.setPreferredSize(size_two);
        this.clearButton.setFont(Serfi);
        this.clearButton.addActionListener(this);
        
        this.delayButton.setText("Add Delay");
        this.delayButton.setPreferredSize(size_two);
        this.delayButton.setFont(Serfi);
        this.delayButton.addActionListener(this);
        
        this.closureButton.setText("Add Closure");
        this.closureButton.setPreferredSize(size_two);
        this.closureButton.setFont(Serfi);
        this.closureButton.addActionListener(this);
        

        /*
         * Set titles and fonts of the checkboxes
         * 
         * Put them in a button group so only one can
         * be checked at once
         */
        this.shortTimeCheckBox.setText("Shortest Path");
        this.shortTimeCheckBox.setFont(Serfi_three);
        this.fewChangeCheckBox.setText("Few Changes");
        this.fewChangeCheckBox.setFont(Serfi_three);
        this.buttonGroup.add(shortTimeCheckBox);
        this.buttonGroup.add(fewChangeCheckBox);

        
        /*
         * Create the leftside Panel
         * Set its layout
         */
        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BoxLayout(leftSide, BoxLayout.Y_AXIS));
        leftSide.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));



        /*
         * Create start panel which holds start station text field
         */
        JPanel startPanel = new JPanel();
        startPanel.setBorder(BorderFactory.createTitledBorder("Start Station"));
        this.startField.setPreferredSize(new Dimension(370,45));     
        startPanel.add(this.startField);
        
        /*
         * Create destination panel which holds destination text field
         */
        JPanel destinationPanel = new JPanel();
        destinationPanel.setBorder(BorderFactory.createTitledBorder("Destination"));
        this.endField.setPreferredSize(new Dimension(370,45));
        destinationPanel.add(this.endField);

        // Add start & destination panels to the leftSide panel
        leftSide.add(startPanel);
        leftSide.add(destinationPanel);
        

        /*
         * Create routing panel which holds searching checkboxes
         */
        JPanel routingPanel = new JPanel();
        routingPanel.setBorder(BorderFactory.createTitledBorder("Routing Options"));
        routingPanel.add(shortTimeCheckBox);
        routingPanel.add(fewChangeCheckBox);

        // Add the routing panel to the leftSide panel
        leftSide.add(routingPanel);
        

        /*
         * Create delays & closure panel which holds add delay button
         * and add closure button
         */
        JPanel delayClosurePanel = new JPanel();
        delayClosurePanel.setBorder(BorderFactory.createTitledBorder("Delays & Closures"));
        delayClosurePanel.add(delayButton);
        delayClosurePanel.add(closureButton);

        // Add the delays and closure panel to the leftside panel
        leftSide.add(delayClosurePanel);
        

        /*
         * Create searching panel which holds findPath button
         */
        JPanel searchingPanel = new JPanel();
        searchingPanel.setBorder(BorderFactory.createTitledBorder("Searching"));
        searchingPanel.add(findPathButton);

        // Add searching panel to the leftSide panel
        leftSide.add(searchingPanel);


        /*
         * Create clearing panel which holds clear button
         */
        JPanel clearingPanel = new JPanel();
        clearingPanel.setBorder(BorderFactory.createTitledBorder("Clearing"));
        clearingPanel.add(clearButton);

        // Add clearing panel to the leftSide panel
        leftSide.add(clearingPanel);


        /*
         * Add the leftSide panel to the GUI
         * Add the resultPanel to the GUI
         * Make GUI visible
         */       
        this.add(leftSide, BorderLayout.WEST);
        this.add(resultPanel, BorderLayout.CENTER);
        this.setVisible(true);

        

    }



    /**
     * Handles button actions for the Find Path and Clear buttons.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        /*
         * When clearButton is clicked clear all 
         * textFields and checkBoxes
         */
        if(e.getSource() == this.clearButton){
            this.startField.setText("");
            this.endField.setText("");
            this.resultTextArea.setText("");
            this.buttonGroup.clearSelection();

        }


        // if delayButton button is clicked create add delayButton screen
        if(e.getSource() == this.delayButton){
            DelayScreen delayScreen = new DelayScreen(this, metroGraph);
        }

        // if closureButton button is clicked create add closureButton screen
        if(e.getSource() == this.closureButton){
            CloseScreen closeScreen = new CloseScreen(this, metroGraph);
        }



        /*
         * When findPathButton is clicked find
         * the path based on the option chosen
         */
        if(e.getSource() == this.findPathButton){
            String input1 = startField.getText().trim();
            String input2 = endField.getText().trim();

            // Check if the names exist in the graph
            if (metroGraph.findNode(input1) == null || metroGraph.findNode(input2) == null){
                resultTextArea.setText("");
                if(metroGraph.findNode(startField.getText()) == null){
                    this.resultTextArea.append("Invalid Start\n");
                }
                if(metroGraph.findNode(endField.getText()) == null){
                    this.resultTextArea.append("Invalid End\n");
                }
                return;
            }    
            
            //Check if the start and end are open          
            if(!metroGraph.findNode(input1).getOpen() ||!metroGraph.findNode(input2).getOpen()){
                resultTextArea.setText("");
                if(!metroGraph.findNode(input1).getOpen()){
                    this.resultTextArea.append("The Start Station is Closed\n");
                }
                if(!metroGraph.findNode(input2).getOpen()){
                    this.resultTextArea.append("The End Station is Closed\n");
                }
                return;
            }

            // if Shortest Time option is chosen
            if(shortTimeCheckBox.isSelected()){
                ShortestRoute shortestRoute = new ShortestRoute();
                shortestRoute.diplayRoute(metroGraph, input1, input2, resultTextArea);
            }
            // if Fewest Changes option is chosen
            if(fewChangeCheckBox.isSelected()){
                FewestChangesRoute fewestChangesRoute = new FewestChangesRoute();
                fewestChangesRoute.displayRoute(metroGraph, input1, input2, resultTextArea);
            }

        }
        
    }

    
}





