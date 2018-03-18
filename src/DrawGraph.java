import javax.swing.*;

/**
 * This class helps to draw graphs
 */
public class DrawGraph {
    public static final int DEFAULT_FRAME_SIZE = 600;
    public static final String TITLE = "Draw Graph";

    DrawGraph(){
        try {
            /** Random graph for tests **/
//            Character[] no = {'a','b','c','D','e'};
//            Character[][] ed = {{'a','b'},{'d','e'}};
//            Graph graph = new Graph(no,ed);

            Graph graph = new Graph();
            GraphGr graphic = new GraphGr(graph);
            JFrame window = new JFrame(TITLE);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.add(graphic);
            window.setSize(DEFAULT_FRAME_SIZE, DEFAULT_FRAME_SIZE);
            window.setVisible(true);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());
                return;
        }
    }

    public static void main(String[]args){
        new DrawGraph();
    }
}
