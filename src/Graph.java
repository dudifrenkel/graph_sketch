import java.util.ArrayList;

/**
 * This class represent undirected graph
 */
public class Graph {

    /* Constants */
    private static final int MAX_NODES = 26;
    public static final int FIRST_NODE = 0;
    public static final int SEC_NODE = 1;
    private static final int NODE_NUM_IN_EDGE = 2;
    private static final Node DELETED_NODE = null;
    /* Exception Messages */
    private static final String NODE_ALREADY_EXISTS = "Node already exists";
    public static final String ILLEGAL_NODE_NAME = "Illegal node name";
    private static final String NODE_NOT_EXISTS = "The node does not exists: ";
    private static final String ILLEGAL_EDGE = "Illegal edge was provided: ";
    private static final String EDGE_ALREADY_IN = "The edge already in the graph";
    private static final String EDGE_NOT_EXISTS = "The given Edge doesn't exists in the graph";
    /* to String constants */
    private static final String NODE_LIST_TIT = "\nNode list:\n";
    private static final String EDGES_LIST_TIT = "\nEdges List:\n";


    private Node[] nodes;
    private int nodesNum;
    private ArrayList<Node.Edge> edges;

    /**
     * Empty constructor - create empty graph
     */
    Graph() {
        nodesNum = 0;
        nodes = new Node[MAX_NODES];
        edges = new ArrayList<>();
    }

    /**
     * The method gets tuple of characters and add each of the given nodes to the neighbor list of the second
     * @param edge Character array
     */
    public void addEdge(Character[] edge) throws Exception {
        Node.Edge newEdge;
        if ( !isLegalEdge(edge) ) {                                            //  checks the given edge is legal
            throw new Exception(ILLEGAL_EDGE);
        }
        if (!(isNodeExists(edge[FIRST_NODE])) || !(isNodeExists(edge[SEC_NODE]))) { //  checks the nodes exists in the graph
            throw new Exception(ILLEGAL_EDGE + NODE_NOT_EXISTS);
        }
        newEdge = getsEdgeFromChar(edge);
        if (edges.contains(newEdge)) {                                    //  checks the edge not already in the graph
            throw new Exception(EDGE_ALREADY_IN);
        }
        edges.add(newEdge);
    }

    /**
     * The method gets edge in string form and return the edge in Character array type.
     * @param edge String array
     */
    public Character[] strToChar(String[] edge) throws Exception {
        if ((edge[FIRST_NODE] == null) || (edge[SEC_NODE] == null)) {
            throw new Exception(ILLEGAL_EDGE);
        }
        if ((edge[Graph.FIRST_NODE].length() != 1) || (edge[Graph.SEC_NODE].length() != 1)) {   //  check for legal length
            throw new Exception(ILLEGAL_EDGE);
        }
        Character[] chEdge = new Character[2];
        chEdge[FIRST_NODE] = edge[FIRST_NODE].charAt(0);
        chEdge[SEC_NODE] = edge[SEC_NODE].charAt(0);
        return chEdge;
    }

    /* The method gets LEGAL edge and checks if need to swap according to the order:
     * the node with the smallest index ( according to getNodeIndex() ) will come first  */
    private Character[] needSwap(Character[] edge) {
        Character[] fixEdge = new Character[2];
        if (getNodeIndex(edge[0]) > getNodeIndex(edge[1])) {               //  swap the nodes in case the first one has
            fixEdge[0] = edge[1];                                         //  bigger index than the other.
            fixEdge[1] = edge[0];
        } else {
            fixEdge = edge;
        }
        return fixEdge;
    }

    /**
     * This method add the given node to the graph and throw exception if already exists or not legal name.
     * update node counter.
     *
     * @param name the node name
     * @param posX the X coordinate on the board
     * @param posY the Y coordinate on the board
     */
    public void addNode(char name, int posX, int posY) throws Exception {
        int nodeIndex;
        if (checkNodeLegal(Character.toString(name))) {
            nodeIndex = getNodeIndex(name);
            if (this.nodes[nodeIndex] != null) {          // checks the new node NOT already in the graph
                throw new Exception(NODE_ALREADY_EXISTS);
            }
            this.nodes[nodeIndex] = new Node(Character.toUpperCase(name), posX, posY);
            this.nodesNum++;
        }
    }

    /**
     * The method delete the given edge from the graph.
     * @throws Exception if the the edge not exists in the graph.
     */
    public void delNode(Character name) throws Exception {
        name = Character.toUpperCase(name);
        if (!(isNodeExists(name))) {
            throw new Exception(NODE_NOT_EXISTS + name);
        }
        ArrayList<Node.Edge> copyEdgeArr = new ArrayList<>(edges);      // get copy of the edges array

        for (Node.Edge e : copyEdgeArr) {           // delete all the edges that connect to the node
            if (e.nodeInEdge(name)) {
                edges.remove(e);
            }
        }
        this.nodes[getNodeIndex(name)] = DELETED_NODE;
    }


    /**
     * The method delete the given edge from the graph
     * @param edge edge that have
     * @throws Exception if the the edge not exists in the graph
     */
    public void delEdge(Character[] edge) throws Exception {
        if ( !isLegalEdge(edge) ) {                                            //  checks the given edge is legal
            throw new Exception(ILLEGAL_EDGE);
        }

        Node.Edge edgeToDel;
        edge = needSwap(edge);
        edgeToDel = getsEdgeFromChar(edge);

        if (!(isEdgeExists(edgeToDel))) {
            throw new Exception(EDGE_NOT_EXISTS);
        }
        this.edges.remove(edgeToDel);
    }


    /* this method gets edge in array of characters and return the object edge if the two nodes exists in the graph */
    private Node.Edge getsEdgeFromChar(Character[] array) throws Exception{
        Node.Edge edgeToDel;
        if ( !(isNodeExists(array[FIRST_NODE])) || !(isNodeExists(array[SEC_NODE])) ){
            throw new Exception(EDGE_NOT_EXISTS);
        }
        array = needSwap(array);
        Character nodeA= array[FIRST_NODE];
        Character nodeB = array[SEC_NODE];
        edgeToDel = new Node.Edge(getNode(nodeA),getNode(nodeB));
        return edgeToDel;
    }
    /**
     * The method return nodes.
     */
    public Node[] getNodeArr() {
        return nodes;
    }

    /**
     * The method return node according to the given char
     */
    private Node getNode(char n) throws Exception {
        return nodes[getNodeIndex(n)];
    }

    /**
     * The method return arrayList of all the edge in the graph.
     */
    public ArrayList<Node.Edge> getEdgesArr() {
        return this.edges;
    }

    /**
     * This method checks if the given node is legal: has 1 char, it's a letter, return node index
     */
    public boolean checkNodeLegal(String node) throws Exception {
        if (node.length() != 1) {
            throw new Exception(ILLEGAL_NODE_NAME);
        }

        Character nodeName = node.charAt(0);
        if (!Character.isLetter(nodeName)) {
            throw new Exception(ILLEGAL_NODE_NAME);
        }
        return true;
    }

    /* The method gets String that represent node and return the numeric value of it */
    private int getNodeIndex(char nodeName) {
        nodeName = Character.toUpperCase(nodeName);         // all the nodes are upper case
        return (int) nodeName - 'A';                           // gets the numeric value of the node
    }

    /**
     * The method checks if the given node exists in the graph.
     * @param n node name
     * @return true if yes otherwise false.
     */
    public boolean isNodeExists(char n) {
        if (nodes[getNodeIndex(n)] != null) {
            return true;
        }
        return false;
    }

    /**
     * The method checks if the given edge exists in the graph
     * @return true if the edge exists otherwise false.
     */
    public boolean isEdgeExists(Node.Edge edge) {
        return this.edges.contains(edge);
    }

    @Override
    public String toString() {
        String st = new String(NODE_LIST_TIT);
        for (Node n : this.nodes) {
            if (n != null) {
                st += n + ", ";
            }
        }
        st += EDGES_LIST_TIT;
        for (Node.Edge edge : this.edges) {
            st += edge;
        }
        return st;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) obj;
        Node[] oNodesArr = other.getNodeArr();
        if (this.getCurrNodesNum() != other.getCurrNodesNum()) {       //  checks same number of nodes
            return false;
        }
        for (int i = 0; i < this.nodes.length; i++) {                    //  checks same name on each node
            if (!(nodes[i].equals(oNodesArr[i]))) {
                return false;
            }
        }
        ArrayList<Node.Edge> oEdgesArr = other.getEdgesArr();
        if (this.edges.size() != oEdgesArr.size()) {                   //  checks same number of edges
            return false;
        }
        for (int i = 0; i < oEdgesArr.size(); i++) {
            if (!(oEdgesArr.contains(this.edges.get(i)))) {        //  checks one contains all the edges of other
                return false;
            }
        }

        return true;
    }

    /**
     * @return current number of nodes in the graph.
     */
    public int getCurrNodesNum() {
        return nodesNum;
    }

    public void clearGraph(){
        nodesNum = 0;
        nodes = new Node[MAX_NODES];
        edges = new ArrayList<>();
    }

    /* The method checks if the given edge is legal - length and nodes name and return true if does */
    private boolean isLegalEdge(Character[] edge){
        if (edge.length != NODE_NUM_IN_EDGE){
            return false;
        }
        if ( !Character.isLetter(edge[0]) || !Character.isLetter(edge[1]) ){
            return false;
        }
        return true;
    }
}