/**
 * Represnts the conection between stations
 */

public class Edge {
    private Node edgeEnd;
    private String lineColour;
    private float time;

    /**
    * Creates an instance of Edge
    * @param edgeEnd the node the edge ends at 
    * @param lineColour the metro line that the edge represents
    * @param time the weight of the edge
    */ 
    public Edge(Node edgeEnd, String line, float time){
        this.edgeEnd = edgeEnd;
        this.lineColour = line;
        this.time = time;
    }

    /**
     * getter for the node at the end of the edge
     * @return the end node of the edge
     */
    public Node getEndNode(){
        return this.edgeEnd;
    }

    /**
     * getter for the line colour of the edge
     * @return String value of the line colour
     */
    public String getLine(){
        return this.lineColour;
    }

    /**
     * getter for the weight of the edge
     * @return a float value of the weight of the edge
     */
    public float getTime(){
        return this.time;
    }

    /**
     * setter for the edge end
     * @param newEnd Node object which is the new end of the edge
     */
    public void setEndNode(Node newEnd){
        this.edgeEnd = newEnd;
    }

    /**
     * setter for the line colour of the edge
     * @param newLineColour string value of the new colour
     */
    public void setLine(String newLineColour){
        this.lineColour = newLineColour;
    }

    /**
     * setter for the time weight of the edge 
     * @param newTime float value of the new time 
     */
    public void setTime(float newTime){
        this.time = newTime;
    }

    /**
     * set the new time with the delay
     */
    public void addDelay(float delay){
        this.time += delay;
    }

}
