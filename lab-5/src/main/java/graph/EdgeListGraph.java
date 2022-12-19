package graph;

import graph.drawing.DrawingApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EdgeListGraph extends Graph {
    private final List<Edge> edges;

    public EdgeListGraph(int vertexCount, List<Edge> edges, DrawingApi drawingApi) {
        super(drawingApi, vertexCount);
        this.edges = edges;
    }

    public EdgeListGraph(int vertexCount, List<Edge> edges) {
        super(vertexCount);
        this.edges = edges;
    }

    protected void drawEdges() {
        for (Edge edge : edges) {
            drawEdge(edge.v, edge.u);
        }
    }

    public static EdgeListGraph readFromFile(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new RuntimeException(path + " is not a file");
        }
        Scanner scanner = new Scanner(path);
        int vertexCount = scanner.nextInt();
        List<Edge> edges = new ArrayList<>();
        while (scanner.hasNextInt()) {
            int v = scanner.nextInt();
            int u = scanner.nextInt();
            edges.add(new Edge(v, u));
        }
        return new EdgeListGraph(vertexCount, edges);
    }

    public record Edge(int v, int u) {
    }
}
