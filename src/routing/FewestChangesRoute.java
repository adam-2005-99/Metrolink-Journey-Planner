import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import javax.swing.JTextArea;



/**
 * This class finds the route with the fewest line changes using a modified Dijkstra algorithm.
 * If multiple paths have the same number of changes, the one with the lower overall time is chosen.
 */

public class FewestChangesRoute {
   
    private Map<String, Float> distances = new HashMap<>(); // Maps distances to the source node for (station :: line) 
    private Map<String, Integer> changes = new HashMap<>(); // Maps the (station, line) with changes to the source node/station
    private Map<String, String> previousStationLine = new HashMap<>(); // Maps each station-line pair to its predecessor in the path
    private HashSet<String> visited = new HashSet<>(); // tracks the visited (station, line)
    private String endKey = null; // The key (station :: line) for the end station


    /**
     * Helper method which creates a String key made up of station name and line colour
     * @param station the name of the station
     * @param line the name of the line 
     * @return the combined key as "station::line"
     */
    private String makeKey(String station, String line){
        return station + "::" + line;
    }

    /**
     * Helper method which splits the key to two parts station name and the line colour
     * @param key the combined key as "station::line"
     * @return array of String which contains the station name and line colour
     */
    private String[] parseKey(String key){
        return key.split("::");
    }
    

    /**
     * Finds the path with fewest changes between two stations in the graph and modify the maps needed
     * to print the path
     * @param graph the graph in which the stations are 
     * @param start the name of the station to strat at
     * @param end the name of the destination
     */
    public void findRoute(Graph graph, String start, String end){

        // Reset maps and sets before computing a new route
        this.distances.clear();
        this.changes.clear();
        this.previousStationLine.clear();
        this.visited.clear();
        this.endKey = null;

        // Set the distance and changes of the start to zero
        this.distances.put(makeKey(start, null), 0f);
        this.changes.put(makeKey(start, null), 0);
        this.previousStationLine.put(makeKey(start, null), null);

        
        // Use the priority queue to get the StationChanges with smallest value for changes
        PriorityQueue<StationChanges> queue = new PriorityQueue<>();
        queue.add(new StationChanges(start, 0, 0f,null));

        while(!queue.isEmpty()){
            // Get the next unvisited StationChanges with fewest changes
            StationChanges current = queue.poll();
            // use (stationName, line) as a key to check all possible paths to avoid marking the station as visited when it is first reached
            String currentKey = makeKey(current.station, current.line); 

            // Check if the station is open
            if(!graph.findNode(current.station).getOpen()){
                continue;
            }
            
            // Skip visited keys
            if (visited.contains(currentKey)) continue;
            visited.add(currentKey);

            // When end is reached the fewest changes path is found; set endKey to currentKey
            if(current.station.equals(end)){
                endKey = currentKey;
                break;  
            } 
            
            Node node = graph.findNode(current.station);
            // Check all the neighbours of the node
            for(Edge edge : node.getEdges()){
                String currentLine = current.line;  
               
                // Get the time needed to get for the neighbor via the current edge
                float newTime = distances.getOrDefault(currentKey, Float.POSITIVE_INFINITY) + edge.getTime();
                // Changes in lines needed to get to the neighbour
                int newChanges = changes.getOrDefault(currentKey, Integer.MAX_VALUE);
                // When line changes add two mins and increase linechanges
                if(currentLine != null && !currentLine.equals(edge.getLine())){
                    newTime +=2;
                    newChanges++;
                }   
                
                String neighbor = edge.getEndNode().getID();
                String nKey = makeKey(neighbor, edge.getLine()); 
                // If the newChanges is less than the currentchanges modify the maps and add neighbor to the queue
                if(newChanges < changes.getOrDefault(nKey, Integer.MAX_VALUE)){
                    this.distances.put(nKey, newTime);
                    this.changes.put(nKey, newChanges);
                    this.previousStationLine.put(nKey, currentKey);
                    queue.add(new StationChanges(neighbor, newChanges,newTime, edge.getLine()));
                }
                
            }
        }
        
        
    }

    /**
     * Helper method which builds the final path from the previousStationLine
     * @return an ArrayList which represents the final path
     */
    private ArrayList<String> buildPath(){
        ArrayList<String> path = new ArrayList<>();
        String current = endKey;
        while(current != null){
            path.add(0, current);
            current = previousStationLine.get(current);
        }

        return path;
    }


    
    /**
     * Displays the route with fewest lineChanges between two stations 
     * it uses the @see {@link #findRoute(Graph, String, String)} to modify the maps 
     * @param graph the graph that represents the metrolink
     * @param start the name of start station
     * @param end the name of the destination
     * @param resultArea the JTextArea in the GUI on which the result will appear
     */
    public void displayRoute(Graph graph, String start, String end, JTextArea resultArea){
        // Call findRoute to the path with fewest changes
        findRoute(graph, start, end);

        // Check if no path exists
        if(endKey == null){
            resultArea.setText("No path found between " + start + " and " + end + ".");
            return;
        }

        // Get the list of the keys that build the path
        ArrayList<String> finalPath = buildPath();

        //StirngBuilder to build the final result
        StringBuilder resultString = new StringBuilder("*** Fewest Changes Route ***\n");

        
        String prevLine = null;
        for(int i = 0; i < finalPath.size()-1; i++){
            String[] fromKey = parseKey(finalPath.get(i));          
            String[] toKey = parseKey(finalPath.get(i+1));                              

            String fromStation = fromKey[0]; // the name of the station we will move from
            String toStation = toKey[0]; // the name of the station we will move to
            String currentLine = toKey[1]; // the line that links the two stations
            
            // when line is changed display that on the TextArea
            if(prevLine != null && !prevLine.equals(currentLine)){
                resultString.append(fromStation + " on the " + prevLine + " line\n");
                resultString.append("** Change to the "  + currentLine + " line" + " at " + fromStation + " **\n");
                
            }
            resultString.append(fromStation + " on the " + currentLine + " line\n");
            // display the last station when we reach the end
            if (toStation.equals(end)){
                resultString.append(toStation + " on the " + currentLine + " line\n");
            }
            
            prevLine = currentLine;
        }
        
        // the overall changes and the overall time are the values of (endKey) in the last station
        resultString.append("Overall Changes = " + changes.get(endKey)+"\n");
        resultString.append("Overall Journey Time (mins) = " + distances.get(endKey)+"\n");

        // Put the reusltString in the resultArea
        resultArea.setText(resultString.toString());
    }
    
    
    /**
     * Represents current stationName ,lineColour, time and changes needed to source
     * It implements Comparable interface to make prioritising in the queue
     * based on line changes
     */
    private class StationChanges implements Comparable<StationChanges>{
        String station;
        String line;
        int changes;
        float time;
     
        public StationChanges(String name, int changes, float time, String line){
            this.station = name;
            this.changes = changes;
            this.time = time;
            this.line = line;
        }
        
        /**
         * @param other StationChanges object to compare with
         * @return a negative value if this station has less changes; positive if it has more
         * zero if the changes are equal
         */
        @Override
        public int compareTo(StationChanges other) {
            int compare = Integer.compare(this.changes, other.changes);
            if(compare == 0){
                return Float.compare(this.time, other.time);
            }

            return compare;
        } 
      
    }
    
}




