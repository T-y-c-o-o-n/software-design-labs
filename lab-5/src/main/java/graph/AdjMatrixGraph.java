package graph;

import graph.drawing.DrawingApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class AdjMatrixGraph extends Graph {

    private final boolean[][] adjMatrix;

    public AdjMatrixGraph(boolean[][] adjMatrix, DrawingApi drawingApi) {
        super(drawingApi, adjMatrix.length);
        this.adjMatrix = adjMatrix;
    }

    public AdjMatrixGraph(boolean[][] adjMatrix) {
        super(adjMatrix.length);
        this.adjMatrix = adjMatrix;
    }

    public static AdjMatrixGraph readFromFile(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new RuntimeException(path + " is not a file");
        }
        Scanner scanner = new Scanner(path);
        int vertexCount = scanner.nextInt();
        boolean[][] adjMatrix = new boolean[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (scanner.hasNext() && !"0".equals(scanner.next())) {
                    adjMatrix[i][j] = true;
                }
            }
        }
        return new AdjMatrixGraph(adjMatrix);
    }

    @Override
    protected void drawEdges() {
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[0].length; j++) {
                if (adjMatrix[i][j]) {
                    drawEdge(i, j);
                }
            }
        }
    }
}
