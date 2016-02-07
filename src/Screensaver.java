/**
 * Created by Pontus on 2015-12-16.
 */

import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Screensaver extends JPanel implements ActionListener {

    public int WIDTH = 800;  // For game area
    public int HEIGHT = 600;
    public static final int RENDER_FREQ = 30;

    private MovingGraph graph;
    private Dimension screenSize;
    private JFrame window;

    public static void main(String[] args) {
        Screensaver s = new Screensaver();

        s.initGraphics();
        s.initEvents();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(46, 46, 45));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        try {
            for (int i = 0; i < graph.getEdges().length; i++) {
                g2.setStroke(new BasicStroke(graph.getThickness(i)));
                graph.getEdge(i).changeColor();

                g2.setColor(new Color(graph.getEdge(i).getR(),
                        graph.getEdge(i).getG(), graph.getEdge(i).getB(), graph.getEdge(i).getAlpha()));

                g2.drawLine(graph.getEdgeX(i, 0), graph.getEdgeY(i, 0),
                        graph.getEdgeX(i, 1), graph.getEdgeY(i, 1));
            }
        }catch(NullPointerException e){

        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    KeyListener kl = new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    };

    public void initGraphics() {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();

        window = new JFrame();

        window.getRootPane().setDoubleBuffered(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Moving Vertices");

        window.setUndecorated(true);
        window.setResizable(false);
        window.validate();
        window.add(this);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(window);

        // --- End GUI

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        window.getContentPane().setCursor(blankCursor);

        window.addKeyListener(kl);
        window.setVisible(true);


        graph = new MovingGraph(WIDTH / 2, 0);
        graph.createStdGraph(WIDTH, HEIGHT);
    }

    public void initEvents() {
        Timer timer = new Timer(RENDER_FREQ, this);
        timer.start();
    }
}

