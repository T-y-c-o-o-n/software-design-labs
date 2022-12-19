package graph;

import graph.drawing.DrawingApi;

public abstract class Graph {

    /**
     * Bridge to drawing api
     */
    protected DrawingApi drawingApi;

    protected final int vertexCount;
    protected final double verticesGraphRadius;
    protected final double vertexRadius;
    public Graph(DrawingApi drawingApi, int vertexCount) {
        this(vertexCount);
        this.drawingApi = drawingApi;
    }

    public Graph(int vertexCount) {
        this.drawingApi = null;
        this.vertexCount = vertexCount;
        this.verticesGraphRadius = 300d;
        this.vertexRadius = 50d;
    }

    public void setDrawingApi(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public void drawGraph() {
        drawVertices();
        drawEdges();
    }

    protected void drawVertices() {
        for (int i = 0; i < vertexCount; i++) {
            double x = getX(i);
            double y = getY(i);
            drawingApi.drawCircle(x, y, vertexRadius);
            drawingApi.drawText(x, y, Integer.toString(i + 1));
        }
    }

    protected double getX(int vertex) {
        double alpha = getAlpha(vertex);
        return drawingApi.getDrawingAreaWidth() / 2d + verticesGraphRadius * Math.cos(alpha);
    }

    protected double getY(int vertex) {
        double alpha = getAlpha(vertex);
        return drawingApi.getDrawingAreaHeight() / 2d + verticesGraphRadius * Math.sin(alpha);
    }

    protected double getAlpha(int vertex) {
        return Math.toRadians((360d / (double) vertexCount) * vertex);
    }

    protected abstract void drawEdges();

    protected void drawEdge(int v, int u) {
        double deltaX = getX(u) - getX(v);
        double deltaY = getY(u) - getY(v);
        double deltaHyp = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double shiftX = vertexRadius * deltaX / deltaHyp;
        double shiftY = vertexRadius * deltaY / deltaHyp;
        drawingApi.drawLine(
            getX(v) + shiftX,
            getY(v) + shiftY,
            getX(u) - shiftX,
            getY(u) - shiftY
        );
    }
}
