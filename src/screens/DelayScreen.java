import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DelayScreen is a dialog window that appears when the user clicks "Add Delay" on the main screen.
 * It allows the user to input two stations, a line color, and a delay time.
 * Once submitted, the delay time is added to the weight of the corresponding edge in the MetroLink graph.
 */


public class DelayScreen extends JDialog implements ActionListener {
    
    private JButton add = new JButton("Add Delay");
    private JButton cancel = new JButton("Cancel");
    private JTextField station1 = new JTextField();
    private JTextField station2 = new JTextField();
    private JTextField lineColour = new JTextField();
    private JTextField timeDelay = new JTextField();
    private Graph metroLink = new Graph();

    /**
     * Constructor which creates an instance of DelayScreen
     * @param parent the JFrame which launches this dialog
     * @param metroGraph the Graph which represents the metroLink network
     */
    public DelayScreen(JFrame parent, Graph metroGraph){
        super(parent, "Add Delay", true);
        this.metroLink = metroGraph;

        // initialising some fonts to be used in the GUI
        Font Serfi = new Font("Serif", Font.ITALIC, 15);
        Font Serfi_two = new Font("Serif", Font.ITALIC, 16);

        

        // Create and align input field labels
        JLabel from = new JLabel("From");
        from.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel to = new JLabel("To");
        to.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel line = new JLabel("Line");
        line.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel delay = new JLabel("Delay");
        delay.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Main panel of the dialog
        JPanel main = new JPanel();
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        
        // Panel for form fields (labels + inputs)
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);


        // Align all text fields to the left
        station1.setAlignmentX(Component.LEFT_ALIGNMENT);
        station2.setAlignmentX(Component.LEFT_ALIGNMENT);
        lineColour.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeDelay.setAlignmentX(Component.LEFT_ALIGNMENT);


        // Add all labels and corresponding input fields to the form
        form.add(from);
        form.add(station1);
        form.add(to);
        form.add(station2);
        form.add(line);
        form.add(lineColour);
        form.add(delay);
        form.add(timeDelay);
        
        // Create buttons panel and assign listeners
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.add(add);
        buttons.add(cancel);
        add.addActionListener(this);
        cancel.addActionListener(this);

    
        // Add the form and buttons panels to the main panel
        main.add(form);
        main.add(buttons);
        setContentPane(main);
        pack(); // size just enough for content
        setLocationRelativeTo(parent); // center on parent
        this.setSize(350,300);
        this.setVisible(true);
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == add){
            String s1 = station1.getText().trim();
            String s2 = station2.getText().trim();
            String line = lineColour.getText().trim();
            String delayStr = timeDelay.getText().trim();

            // Check if all Fields are filled
            if(s1.isEmpty() || s2.isEmpty()|| line.isEmpty() || delayStr.isEmpty()){
                JOptionPane.showMessageDialog(this, "All Fields Must be Filled", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Node node1 = metroLink.findNode(station1.getText().trim());
            Node node2 = metroLink.findNode(station2.getText().trim());
            // Check if Station one exists
            if(node1 == null){
                JOptionPane.showMessageDialog(this, "The First Station is Not Found", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
                
            }
            // Check if Station two exists
            if(node2 == null){
                JOptionPane.showMessageDialog(this, "The Second Station is Not Found", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
                
            }
            
            // Check if the Edge Exists
            if(!metroLink.connectedViaLine(node1, node2, line)){
                JOptionPane.showMessageDialog(this, "The Connection is not Found between the Stations", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check if the Delay time is correct
            float delay = 0;
            try {
                delay = Float.parseFloat(delayStr);
                if(delay < 0){
                    JOptionPane.showMessageDialog(this, "Delay Time Must be a Non-Negative Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException exeption) {
                JOptionPane.showMessageDialog(this, "Delay Must be a Valid Number", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            metroLink.addDelay(s1, s2, line, delay);
            JOptionPane.showMessageDialog(this, "Delay Added Successfully", "Successful Action",JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }

        if(e.getSource() == cancel){
            dispose();
        }
    }


}
