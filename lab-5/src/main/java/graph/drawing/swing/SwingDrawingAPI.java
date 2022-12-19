package graph.drawing.swing;

import graph.drawing.DrawingApi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingDrawingAPI extends JFrame implements DrawingApi {

    private final List<Circle> circles = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();
    private final List<Text> texts = new ArrayList<>();

    public SwingDrawingAPI() {
        super("Graph visualisation with Swing API");
        setSize(1000, 1000);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        for (Circle circle : circles) {
            g.drawOval(circle.x - circle.r, circle.y - circle.r, circle.r * 2, circle.r * 2);
        }
        for (Line line : lines) {
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
        for (Text text : texts) {
            g.getFont();
            g.setFont(new Font("Serif", Font.PLAIN, 40));
            g.drawString(text.text, text.x - 15, text.y + 15);
        }
    }

    @Override
    public long getDrawingAreaWidth() {
        return getWidth();
    }

    @Override
    public long getDrawingAreaHeight() {
        return getHeight();
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        circles.add(new Circle((int) x, (int) y, (int) r));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        lines.add(new Line((int) x1, (int) y1, (int) x2, (int) y2));
    }

    @Override
    public void drawText(double x, double y, String value) {
        texts.add(new Text((int) x, (int) y, value));
    }

    private record Circle(int x, int y, int r) {
    }

    private record Line(int x1, int y1, int x2, int y2) {
    }

    private record Text(int x, int y, String text) {
    }
}
