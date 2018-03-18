
/**
 *  This class implements Node in graph
 */
public class Node{

    Character name;
    int posX;
    int posY;

    Node(Character name, int posX, int posY){
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public Character getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public String toString() {
        return ""+getName()+"";
    }

    @Override
    public boolean equals(Object obj){
        if ( !(obj instanceof Node) ){
            return false;
        }
        if (this.getName()!=((Node) obj).getName()){    //  checks the they both has the same name
            return false;
        }
        return true;
    }

    /**
     * This method represent an edge between two nodes in graph
     */
    public static class Edge{
        private Node nodeA;
        private Node nodeB;

        public Edge(Node a, Node b){
            nodeA = a;
            nodeB = b;
        }

        @Override
        public String toString() {
            return "("+getFirst().getName()+","+getSec().getName()+")";
        }

        @Override
        public boolean equals(Object obj){
            if ( !(obj instanceof Edge) ){
                return false;
            }
            if ( !(this.getFirst().equals(((Edge) obj).getFirst())) ){      //  checks the they both has the same name
                return false;
            }

            if ( !(this.getSec().equals(((Edge) obj).getSec())) ){          //  checks the they both has the same name
                return false;
            }
            return true;
        }

        /**
         * @return the first node
         */
        public Node getFirst(){
            return nodeA;
        }

        /**
         * @return the second node
         */
        public Node getSec(){
            return nodeB;
        }

        /**
         * Checks if the given node name is part of the edge
         * @param name node name
         * @return true if its part of the edge
         */
        public boolean nodeInEdge(Character name){
            if ( (getFirst().getName()==name) || (getSec().getName()==name) ){
                return true;
            }
            return false;
        }
    }
}