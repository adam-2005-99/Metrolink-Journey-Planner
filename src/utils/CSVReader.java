import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextArea;


/**
 * Reads a CSV file and Creates a graph for the metroLink
 */
public class CSVReader {
    
    /**
     * This method reads a CSV file and return a graph that represents the metroLink
     * @param filePath the path of the CSV file 
     * @return a Graph with all nodes (Stations) and Edges (Connections) of the metroLink
     */
    public Graph getGraph(String filePath, JTextArea resulTextArea){
        String line = "";
        Graph metroLink = new Graph();
        
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            while((line = buffer.readLine()) != null){
                if(line.startsWith("From")) continue;
                String[] values = line.split(",");
                
                String from = values[0];
                String to = values[1];
                String lineColour = values[2];
                float time = Float.parseFloat(values[3]);
            
                // Add nodes for stations
                metroLink.addNode(from);
                metroLink.addNode(to);
    
                // Add edge between the two stations
                metroLink.addEdge(from, to, lineColour, time);
    
    
            }
        } catch (FileNotFoundException e){
            resulTextArea.append("File Not Found\n");
            
        } catch(IOException e){
            resulTextArea.append("I/O Erorr\n");
        }

        return metroLink;
    }

}
            
