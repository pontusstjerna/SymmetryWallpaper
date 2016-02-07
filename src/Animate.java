import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by Pontus on 2015-12-14.
 */
public class Animate extends JPanel implements ActionListener {

    public static final int WIDTH = 800;  // For game area
    public static final int HEIGHT = 600;
    public static final int RENDER_FREQ = 30;

    private MovingGraph graph;

    public static void main(String[] args) {
        Animate anim = new Animate();

        anim.initGraphics();
        anim.initEvents();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(46, 46, 45));
        g2.fillRect(0,0,WIDTH, HEIGHT);

        for(int i = 0; i < graph.getEdges().length; i++){
            g2.setStroke(new BasicStroke(graph.getThickness(i)));
            graph.getEdge(i).changeColor();

            g2.setColor(new Color(graph.getEdge(i).getR(),
                    graph.getEdge(i).getG(), graph.getEdge(i).getB(),graph.getEdge(i).getAlpha()));

            g2.drawLine(graph.getEdgeX(i, 0), graph.getEdgeY(i, 0),
                    graph.getEdgeX(i, 1), graph.getEdgeY(i, 1));


            /*
            g2.setColor(new Color(209, 209, 209, graph.getEdge(i).getAlpha()));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(graph.getEdgeX(i, 0), graph.getEdgeY(i, 0),
                    graph.getEdgeX(i, 1), graph.getEdgeY(i, 1));
*/
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void initGraphics() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame window = new JFrame();

        window.getRootPane().setDoubleBuffered(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Moving Vertices");

        window.setResizable(false);
        window.validate();
        window.add(this);

        // --- End GUI

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        


        graph = new MovingGraph(WIDTH/2,0);
        graph.createStdGraph(WIDTH, HEIGHT);

        //graph.setThickness(0.3f);
        graph.setColor(0);
        graph.setColorChange(true);
    }

    public void initEvents() {
        Timer timer = new Timer(RENDER_FREQ, this);
        timer.start();
    }
}
