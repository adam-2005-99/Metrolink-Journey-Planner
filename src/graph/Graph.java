import java.util.*;

/**
 * Represents the metroLink with nodes for stations and edges for connections
 */
public class Graph {
    
    private Node headNode;
    
    /**
    * Finds the node which its key(name) was passed and return it
    *
    * @param label The name of the station to add
    * @return node object if it is found, null otherwise
    */
    public Node findNode(String label){
       Node node = headNode;
       while(node != null){
           if(node.getID().equals(label))return node;
           node = node.getNxtNode();
       }
       return null;
    }


    /**
     * Adds delay time to the weigth of edge connecting the stations give via the given line
     * @param l1 the name of the first station
     * @param l2 the name of the second station
     * @param line the line connecting the stations
     * @param delay the delay time to be added to the weight
     */
    public void addDelay(String l1, String l2, String line, float delay){
        Node node1 = findNode(l1);
        Node node2 = findNode(l2);

        for(Edge e : node1.getEdges()){
            if(e.getEndNode().getID().equals(l2) && e.getLine().equals(line))
                e.addDelay(delay);
        }

        for(Edge e : node2.getEdges()){
            if(e.getEndNode().equals(node1) && e.getLine().equals(line))
                e.addDelay(delay);
        }

    }


    /**
     * Sets the status of the given station as closed
     * @param stationName the name of the station to be closed
     */
    public void addClosure(String stationName){
        Node station = findNode(stationName);
        station.setOpen(false);
    }





    /**
	 * Adds Node to the Graph
	 *
	 * @param label the name of the station to add
	 */
    public void addNode(String label){
        if(findNode(label) == null){
            Node node = new Node(label);
            node.setNextNode(headNode);
            headNode = node;
        }
    }


    /**
     * Adds edge to the graph
     * @param l1 the String key of the first node
     * @param l2 the String key of the second node
     * @param line the line which links between the two nodes
     * @param time the weight of the edge
     */
    public void addEdge(String l1, String l2, String line, float time){
        Node node1 = findNode(l1);
        Node node2 = findNode(l2);
        if(node1 != null && node2 != null){
            node1.getEdges().add(new Edge(node2, line, time));
            node2.getEdges().add(new Edge(node1, line, time));
        }else{
            System.out.println("One of the stations or both are not found. Edge is not added");
        }
    }


    /**
    * Removes edge from the graph
    * @param l1 the String key of the first node
    * @param l2 the String key of the second node
    */
    public void removeEdge(String l1, String l2){
        Node node1 = findNode(l1);
        Node node2 = findNode(l2);
        if(node1 != null && node2 != null){
            for(Edge edge: node1.getEdges()){
                if(edge.getEndNode().equals(node2)){
                    node1.getEdges().remove(edge);
                    break;
                }
                    
            }
            
            for(Edge edge: node2.getEdges()){
                if(edge.getEndNode().equals(node1)){
                    node2.getEdges().remove(edge);
                    break;
                }
            }
        }
    }


    /**
     * Finds the number of Node in the graph 
     * @return an integer value of the number of nodes in the graph
     */
    public int getSize(){
        int size = 0;
        Node node = headNode;
        while(node != null){
            size++;
            node = node.getNxtNode();
        } 
        return size;
    }


    /**
     * Prints the Nodes in the graph and edges that links them with line colour and the weight
     */
    public void print(){
        Node node = headNode;
        while(node != null){
            for(Edge neighbor: node.getEdges()){
                System.out.println("[" + node.getID() + " to "+ neighbor.getEndNode().getID() + ", " + neighbor.getLine() + ", " + neighbor.getTime() + " min]");
            }
            node = node.getNxtNode();
        }
    }

    /**
     * getter for the head node of the graph
     * @return Node object of the head node of the graph
     */
    public Node getHeaNode(){
        return this.headNode;
    }

    /**
     * setter for the head node of the graph
     * @param newHead the node which will be the new head for the graph
     */
    public void setHeadNode(Node newHead){
        this.headNode = newHead;
    }


    /**
     * Checks if node2 is adjecnet to node1
     * @param node1 the node which we want to check its neibours
     * @param node2 the node which we want to check if it is a neighbour of node1
     * @return ture if they are adjecent and false otherwise
     */
    public boolean connectedViaLine(Node node1, Node node2, String lineColour){
        for(Edge e : node1.getEdges()){
            if(e.getEndNode().equals(node2) && e.getLine().equals(lineColour))
                return true;
        }
        return false;
    }


}
