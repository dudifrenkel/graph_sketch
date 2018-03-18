import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Dudi on 10/05/2017.
 */
public class GraphGr extends JPanel implements ActionListener {
    public static final String INSERT_NODE_MSG = "Please Insert new Node (a-z)";
    public static final String NEW_NODE_TIT = "Add new node";
    public static final String ADD_EDGE = "Add Edge";
    public static final String DEL_EDGE = "Delete Edge";
    public static final String DEL_NODE = "Delete Node";
    public static final String CLEAR = "Clear";
    public static final int DEFALUT_NODE_SIZE = 20;
    public static final String INSERT_FIRST_NODE_MSG = "Please insert the first Node";
    public static final String INSERT_SECOND_NODE_MSG = "Please insert the second Node";
    public static final String NODE_TO_DEL_MSG = "Please insert node to delete";

    private Graph graph;
    JButton addEdge;
    JButton delEdge;
    JButton delNode;
    JButton clear;

    GraphGr(Graph graph){
        super();
        this.graph = graph;

        addEdge = new JButton(ADD_EDGE);
        delEdge = new JButton(DEL_EDGE);
        delNode = new JButton(DEL_NODE);
        clear = new JButton(CLEAR);
        this.add(addEdge);
        this.add(delEdge);
        this.add(delNode);
        this.add(clear);


        addEdge.addActionListener(this);
        delEdge.addActionListener(this);
        delNode.addActionListener(this);
        clear.addActionListener(this);
        addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent event){
                String newNode;
                super.mouseClicked(event);
                try{
                    newNode = JOptionPane.showInputDialog(null,INSERT_NODE_MSG, NEW_NODE_TIT, JOptionPane.OK_CANCEL_OPTION);
                    if  (newNode!=null)  {            // node was insert by user
                        if(newNode.length()!=1){
                            throw new Exception(Graph.ILLEGAL_NODE_NAME);
                        }
                        graph.addNode(newNode.charAt(0),event.getX(),event.getY());
                        repaint();
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,e.getMessage());
                }
            }
        });


    }


    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Node[] nodesArr = graph.getNodeArr();
        ArrayList<Node.Edge> edgesArr = graph.getEdgesArr();
        Node currNode;

        for (int i=0; i<nodesArr.length; i++){
            if (nodesArr[i]!=null){
                currNode = nodesArr[i];
                drawNode(graphics,currNode.getName(),currNode.getPosX(),currNode.getPosY());
            }
        }

        for (Node.Edge edge:edgesArr) {
            drawEdge(graphics,edge);
        }
        repaint();
    }

    /* The method draw Node according to the given parameters */
    private void drawNode(Graphics gr,char name, int posX, int posY) {
        char[] arr = new char[1];
        arr[0]=name;
        gr.drawOval(posX,posY,DEFALUT_NODE_SIZE,DEFALUT_NODE_SIZE);
        gr.drawChars(arr,0,1,posX+DEFALUT_NODE_SIZE/2-3,posY+DEFALUT_NODE_SIZE/2+3);
    }

    /* The method draw Edge according to the given parameters */
    private void drawEdge(Graphics gr, Node.Edge edge) {
        Node a = edge.getFirst();
        Node b = edge.getSec();
        int x1 = a.getPosX();
        int y1 = a.getPosY();
        int x2 = b.getPosX();
        int y2 = b.getPosY();
        if (x1<x2){
            x1 += DEFALUT_NODE_SIZE;
            y1 += DEFALUT_NODE_SIZE/2+3;
            y2 += DEFALUT_NODE_SIZE/2+3;
        }
        else {
            x2 += DEFALUT_NODE_SIZE;
            y2 += DEFALUT_NODE_SIZE/2+3;
            y1 += DEFALUT_NODE_SIZE/2+3;
        }
        gr.drawLine(x1,y1,x2,y2);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String []inputEdge = new String[2];
        Character []edge;

        if (event.getSource() == addEdge){
            inputEdge[Graph.FIRST_NODE] = JOptionPane.showInputDialog(INSERT_FIRST_NODE_MSG);
            inputEdge[Graph.SEC_NODE] = JOptionPane.showInputDialog(INSERT_SECOND_NODE_MSG);
            try{
                edge = graph.strToChar(inputEdge);
                graph.addEdge(edge);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }

        if (event.getSource() == delEdge){
            inputEdge[0] = JOptionPane.showInputDialog(INSERT_FIRST_NODE_MSG);
            inputEdge[1] = JOptionPane.showInputDialog(INSERT_SECOND_NODE_MSG);
            try{
                edge = graph.strToChar(inputEdge);
                graph.delEdge(edge);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }

        if (event.getSource() == delNode){
            String inNode = JOptionPane.showInputDialog(NODE_TO_DEL_MSG);
            try{
                graph.checkNodeLegal(inNode);
                graph.delNode(inNode.charAt(0));
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }

        if (event.getSource() == clear){
            graph.clearGraph();
        }
    }


}
