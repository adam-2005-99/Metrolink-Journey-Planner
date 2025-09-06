import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import javax.swing.JTextArea;


/**
 * This class finds the shortest route between two nodes in a graph
 * and prints that path using Dijkstra Algorithm
 */
public class ShortestRoute{
    
    private Map<String, Float> distances = new HashMap<>();  // Maps distances to the source node for (station, line) 
    private Map<String, String> previousStationLine = new HashMap<>(); //Maps each station-line pair to its predecessor in the path
    private HashSet<String> visited = new HashSet<>(); // The visited (station, line) 
    private String endKey; // // The key (station :: line) for the end station


    /**
     * Helper method which creates a key formed of station name and line colour
     * @param station the name of the station
     * @param line the colour of the line
     * @return the combined key as "station::line"
     */
    private String makeKey(String station, String line){
        return station + "::" + line;
    }
    
    /**
     * Helper method which splits the key to two parts station name and line colour
     * @param key a key which represets station name and line colour
     * @return an array of Strings which contains the station and line colour
     */
    private String[] parseKey(String key){
        return key.split("::");
    }
    
    
    /**
     * Finds the shortest path between two stations in the graph and modify the maps needed
     * to print the path
     * @param graph the graph in which the stations are 
     * @param start the name of the station to start at
     * @param end the name of the destination
     */
    public void findRoute(Graph graph, String start, String end){

        // Reset internal state before computing a new route
        this.distances.clear();
        this.visited.clear();
        this.previousStationLine.clear();
        this.endKey = null;


        // Set the distance for the start to zero
        this.distances.put(makeKey(start, null), 0f);
        this.previousStationLine.put(makeKey(start, null), null);

        // Priority Queue to get the StationTime with lowest value for time
        PriorityQueue<StationTime> queue = new PriorityQueue<>();
        queue.add(new StationTime(start, null,0f));
        
        while(!queue.isEmpty()){
            // Get the next unvisited StationTime with the shortest time
            StationTime current = queue.poll();
            // To avoid marking a station as visited before checking all lines that lead to it; use (stationName, line) as a key 
            String currentKey = makeKey(current.station, current.line);  
            
            // Check if the station is open
            if(!graph.findNode(current.station).getOpen()){
                continue;
            }
            
            // Skip if this (station, line) pair has already been visited
            if(visited.contains(currentKey)) continue; 
            visited.add(currentKey);

            // If end is reached shortest path is found; so break after saving the endKey
            if(current.station.equals(end)){
                this.endKey = currentKey;
                break;
            }
            Node node = graph.findNode(current.station);
            //Check the neighbors of the node
            for(Edge edge : node.getEdges()){
                String currentLine = current.line;  
            
                // Find the time to reach the neighbor via this edge
                float newTime  = this.distances.getOrDefault(currentKey, Float.POSITIVE_INFINITY) + edge.getTime();
                // Line change needs two mins more
                if(currentLine != null && !currentLine.equals(edge.getLine())){
                    newTime +=2;
                }   
                String neighbor = edge.getEndNode().getID();
                String neighborKey = makeKey(neighbor, edge.getLine());     
                // If the newtime is shorter update it in the maps,and add neighbor to the queue
                if(newTime < distances.getOrDefault(neighborKey, Float.POSITIVE_INFINITY)){
                    this.distances.put(neighborKey, newTime);
                    this.previousStationLine.put(neighborKey, currentKey);
                    queue.add(new StationTime(neighbor, edge.getLine(),newTime));
                }
                
            }
        }
        
        
    }

    /**
     * A helper method which builds the final route based on the previousStationLine map
     * @return ArrayList of the keys that forms the final path
     */
    private ArrayList<String> buildRoute(){
        ArrayList<String> pathList = new ArrayList<>();
        String current = this.endKey;
        while (current != null) {
            pathList.add(0, current);
            current = previousStationLine.get(current);
        }
        return pathList;

    }
    

    /**
     * Displays the shortest route between two stations.
     * This method runs {@link #findRoute(Graph, String, String)} internally
     * and displays the result in the GUI.
     *
     * @param graph the metro graph containing stations and edges
     * @param start the name of the starting station
     * @param end the name of the destination station
     * @param resultArea the JTextArea UI component where the route will be displayed
     */
    public void diplayRoute(Graph graph, String start, String end, JTextArea resultArea){
        // Call findRoute method to get the shortest route
        findRoute(graph, start, end);

        // Check if the path exists
        if (endKey == null) {
            resultArea.setText("No route found between " + start + " and " + end + ".");
            return;
        }
        
        // Get the list of the keys that build the path
        ArrayList<String> finalPath = buildRoute();
        //StirngBuilder to build the final result
        StringBuilder routeText = new StringBuilder("*** Shortest Route ***\n");
        
        String prevLine = null; 
        for(int i = 0; i < finalPath.size()-1; i++){
            String[] fromKey = parseKey(finalPath.get(i)); 
            String[] toKey = parseKey(finalPath.get(i+1));  

            String from = fromKey[0]; // departure station
            String to = toKey[0];  // the name of the station which we move to
            String currLine = toKey[1];  // the line used between these two stations
            
            if(prevLine != null && !prevLine.equals(currLine)){
                // Display line change
                routeText.append(from + " on the " + prevLine + " line\n");
                routeText.append("** Change to the "  + currLine + " line" + " at " + from + " **\n");
            }
            
            routeText.append(from + " on the " + currLine + " line\n");
            // Display the end station with its previous line when end reached
            if (to.equals(end)){
                routeText.append(to + " on the " + currLine + " line\n");
            }
            
            prevLine = currLine;
        }
        // The overall journey time is value of the key (endKey) in distances map
        routeText.append("Overall Journey Time (mins) = " + this.distances.get(endKey)+"\n");

        // Put routeText in the resultArea
        resultArea.setText(routeText.toString());
    }
    

     /**
     * Represents a station with its associated line and the time from the source.
     * Used for prioritisation in the shortest path calculation (Dijkstra's algorithm).
     */
    private class StationTime implements Comparable<StationTime>{
        String station;
        String line;
        float time;
    
        public StationTime(String name, String line, float time){
            this.station = name;
            this.line = line;
            this.time = time;
        }
         /**
         * @param other StationTime object to compare with
         * @return a negative value if this staion has shorter time; positive if it has longer
         * zero if time is equal
         */
        @Override
        public int compareTo(StationTime other) {
            return Float.compare(this.time, other.time); 
        } 
      
    }


}