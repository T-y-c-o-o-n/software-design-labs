package graph;

import graph.drawing.DrawingApi;
import graph.drawing.javafx.JavaFXDrawingAPI;
import graph.drawing.swing.SwingDrawingAPI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class Main extends Application {

    private static DrawingApi drawingApi;
    private static Graph graph;

    @Override
    public void start(Stage stage) {
        drawingApi = new JavaFXDrawingAPI(stage);
        graph.setDrawingApi(drawingApi);
        graph.drawGraph();
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        // TODO: parse args
        if (args == null || args.length != 3) {
            System.out.println("Usage: $0 javafx/swing adj/edges file");
            return;
        }
        Path path = Path.of(args[2]);
        if ("adj".equals(args[1])) {
            graph = AdjMatrixGraph.readFromFile(path);
        } else if ("edges".equals(args[1])) {
            graph = EdgeListGraph.readFromFile(path);
        } else {
            // TODO
        }
        if ("javafx".equals(args[0])) {
            Application.launch();
        } else if ("swing".equals(args[0])) {
            drawingApi = new SwingDrawingAPI();
            graph.setDrawingApi(drawingApi);
            graph.drawGraph();
        } else {
            // TODO
        }
    }
}
