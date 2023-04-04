/*
Uppgift 3 - Lek med geometri
namn : kristoffer danbrant
studentid: krda1698 @ Uppsala Universitet
e-post: k.danbrant@icloud.com
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * A panel that displays geometric figures that can be selected and moved by the user.
 */
public class GeometricFigures extends JPanel {
    private List<Figure> figures = new ArrayList<Figure>();
    private Figure selectedFigure = null;
    private Point mousePosition = null;
    
    
    /**
     * Constructs a GeometricFigures panel with a gray background and mouse listeners for selecting and moving figures.
     */
    public GeometricFigures() {
        setBackground(Color.GRAY);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectFigureAt(e.getPoint());
            }
        });


        
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedFigure != null) {
                    selectedFigure.moveBy(e.getX() - mousePosition.x, e.getY() - mousePosition.y);
                    mousePosition = e.getPoint();
                    repaint();
                }
            }
        });
    }


       /**
     * Adds a new figure to the panel and selects it.
     *
     * @param figure the figure to add
     */
    public void addFigure(Figure figure) {
        figures.add(figure);
        selectedFigure = figure;
        repaint();
    }



    private void selectFigureAt(Point point) {
        for (int i = figures.size() - 1; i >= 0; i--) {
            Figure figure = figures.get(i);
            if (figure.contains(point)) {
                selectedFigure = figure;
                mousePosition = point;
                figures.remove(figure);
                figures.add(figure);
                repaint();
                return;
            }
        }
        selectedFigure = null;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Figure figure : figures) {
            figure.paint(g2d);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Geometric Figures");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GeometricFigures panel = new GeometricFigures();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
        panel.addFigure(new Circle(50, 50, 30, Color.RED));
        panel.addFigure(new Triangle(100, 100, 50, Color.BLUE));
        panel.addFigure(new Square(150, 150, 40, Color.YELLOW));
    }
}


/**
 * An interface for geometric figures that can be painted, checked for containing a point, and moved.
 */
interface Figure {

    /**
     * Paints the figure on a Graphics2D object.
     *
     * @param g2d the Graphics2D object to paint on
     */
    void paint(Graphics2D g2d);

    /**
     * Checks if the figure contains a given point.
     *
     * @param point the point to check
     * @return true if the figure contains the point, false otherwise
     */
    boolean contains(Point point);


     /**
     * Moves the figure by a given delta in x and y.
     *
     * @param dx the delta in x
     * @param dy the delta in y
     */
    void moveBy(int dx, int dy);
}



class Circle implements Figure {
    private int x;
    private int y;
    private int radius;
    private Color color;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public boolean contains(Point point) {
        return Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2) <= Math.pow(radius, 2);
    }

    public void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }
}

class Triangle implements Figure {
    private int x;
    private int y;
    private int side;
    private Color color;
    private Polygon polygon;

    public Triangle(int x, int y, int side, Color color) {
      this.x = x;
  this.y = y;
  this.side = side;
  this.color = color;
  int[] xpoints = {x, x + side, x + side / 2};
  int[] ypoints = {y, y, y + (int) (side * Math.sin(Math.PI / 3))};
  polygon = new Polygon(xpoints, ypoints, 3);
}

public void paint(Graphics2D g2d) {
  g2d.setColor(color);
  g2d.fillPolygon(polygon);
}

public boolean contains(Point point) {
  return polygon.contains(point);
}

public void moveBy(int dx, int dy) {
  x += dx;
  y += dy;
  polygon.translate(dx, dy);
}
}

class Square implements Figure {
private int x;
private int y;
private int side;
private Color color;

public Square(int x, int y, int side, Color color) {
    this.x = x;
    this.y = y;
    this.side = side;
    this.color = color;
}

public void paint(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fillRect(x, y, side, side);
}

public boolean contains(Point point) {
    return point.x >= x && point.x <= x + side && point.y >= y && point.y <= y + side;
}

public void moveBy(int dx, int dy) {
    x += dx;
    y += dy;
  }
}
