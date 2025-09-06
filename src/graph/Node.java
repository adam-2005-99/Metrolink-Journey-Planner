import java.util.LinkedHashSet;

/**
 * Represents a station in the metrolink 
 */

public class Node {
   
    private String id;
    private LinkedHashSet<Edge> edges;
    private Node next;
    private boolean open;

    /**
     * Creates an instance of Node
     * @param label the name of the station , used as identifier for the node
     */
    public Node (String label){
        this.id = label;
        this.edges = new LinkedHashSet<Edge>();
        this.open = true;
    }

    /**
     * getter for the id of the node
     * @return a string value of the id of the node
     */
    public String getID(){
        return this.id;
    }

    /**
     * getter for the edges of the node
     * @return LikedHashSet of the edges of the node
     */
    public LinkedHashSet<Edge> getEdges(){
        return this.edges;
    }

    /**
     * getter for the next node 
     * @return Node object of the next node 
     */
    public Node getNxtNode(){
        return this.next;
    }


    public boolean getOpen(){
        return this.open;
    }

    /**
     * setter for the next node
     * @param next the new Next node 
     */
    public void setNextNode(Node next){
        this.next = next;
    }

    /**
     * setter for the node id
     * @param newID a string value of the new id of the node
     */
    public void setID(String newID){
        this.id = newID;
    }

    /**
     * setter for the open status of the station
     * @param status a boolean value for the open attribute
     */
    public void setOpen(boolean status){
        this.open = status;
        
    }

}
