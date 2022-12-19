package graph.drawing.javafx;

import graph.drawing.DrawingApi;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class JavaFXDrawingAPI implements DrawingApi {

    private final Stage stage;
    private final Collection<Node> nodes;

    public JavaFXDrawingAPI(Stage stage) {
        stage.setTitle("Graph visualisation with JavaFX API");
        this.stage = stage;
        stage.setWidth(1000);
        stage.setHeight(1000);
        nodes = new ArrayList<>();
    }

    @Override
    public long getDrawingAreaWidth() {
        return (long) stage.getWidth();
    }

    @Override
    public long getDrawingAreaHeight() {
        return (long) stage.getHeight();
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        Circle circle = new Circle(x, y, r);
        nodes.add(circle);
        stage.setScene(new Scene(new Group(nodes)));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(5);
        nodes.add(line);
        stage.setScene(new Scene(new Group(nodes)));
    }

    @Override
    public void drawText(double x, double y, String value) {
        Text text = new Text(x, y, value);
        text.setStroke(Color.BLUE);
        text.setStyle("-fx-font-size: 24;");
        nodes.add(text);
        stage.setScene(new Scene(new Group(nodes)));
    }
}
