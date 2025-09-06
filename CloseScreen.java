import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * CloseScreen is dialog window which appears when the user clicks "Add Closure" on the main window
 * It allows user to input the name of a station which will be set as closed after submission
 */

public class CloseScreen extends JDialog implements ActionListener{
    
    private JButton add = new JButton("Add Closure");
    private JButton cancel = new JButton("Cancel");
    private JTextField stationTextField = new JTextField();
    private Graph metroLink = new Graph();
    
    /**
     * Constructor which creats an instance of CloseScreen
     * @param parent the JFrame which launches the dialog
     * @param metroGraph the graph which represents the MtroLink network
     */
    public CloseScreen(JFrame parent, Graph metroGraph){
        super(parent, "Add Closure", true);
        this.metroLink = metroGraph;

        // initialising some fonts to be used in the GUI
        Font Serfi = new Font("Serif", Font.BOLD, 15);
        Font Serfi_two = new Font("Serif", Font.ITALIC, 16);
        
        // Create and align the main panel in the dialog
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main , BoxLayout.Y_AXIS));
        main.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        
        // Create form panel which holds the stationLabel and input textbox
        JPanel form = new JPanel();
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        
        

        // Create label for the closed station
        JLabel stationLabel = new JLabel("The Name of the Closure Station");
        // stationLabel.setFont(Serfi);
        
        
        
        // Add the label and textBox
        form.add(stationLabel);
        form.add(stationTextField);
        
        // Create buttons panel to hold buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add listeners for the buttons and add them to buttons panel
        add.addActionListener(this);
        cancel.addActionListener(this);
        buttons.add(add);
        buttons.add(cancel);
        
        // Add form and buttons panels to the main panel
        main.add(form);
        main.add(buttons);
        this.setContentPane(main);
        this.pack();
        this.setLocationRelativeTo(parent); // set the dialog to the middle of the parent
        this.setSize(300,150);
        this.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == add){
            String stationName = stationTextField.getText().trim();

            // Check if stationTextField is not empty
            if(stationName.isEmpty()){
                JOptionPane.showMessageDialog(this, "Station Field Must be Filled", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the Node exists in the metroLink Graph
            Node node = metroLink.findNode(stationName);
            if(node == null){
                JOptionPane.showMessageDialog(this, "Station Not Found", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the station is already closed
            if(!node.getOpen()){
                JOptionPane.showMessageDialog(this, "The Station is already Closed", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            metroLink.addClosure(stationName);
            JOptionPane.showMessageDialog(this, "Closure Added Successfully", "Action Message", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

        if(e.getSource() == cancel){
            this.dispose();
        }
    }


}
    
