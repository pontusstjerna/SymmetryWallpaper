/**
 * Created by Pontus on 2015-12-14.
 */
package pontus.symmetry;
public class MovingGraph {

    private int[] origin = {0, 0};
    private Edge[] e;
    private int[][] v;
    private int scale = 1;

    public MovingGraph(int originX, int originY) {
        this.origin[0] = originX;
        this.origin[1] = originY;
    }

    void createStdGraph(int width, int height) {

        final int scaleF = 23;
        scale = Math.min(width, height) / scaleF;
        int yOffset = (height / 2) - ((20 * scale) / 2);

        int x = origin[0];
        int y = origin[1] + yOffset;
        // int y = origin[1] + (20*(height/25))/8;

        v = new int[][]
                {{x - 5 * scale, y},    //0
                        {x - 9 * scale, y + 6 * scale}, //1
                        {x, y + 20 * scale},    //2
                        {x, y + 13 * scale},    //3
                        {x + 5 * scale, y},     //4
                        {x + 9 * scale, y + 6 * scale}};//5

        e = new Edge[]{
                new Edge(v[0], v[1]),//1
                new Edge(v[1], v[2]),//2
                new Edge(v[1], v[3]),//3
                new Edge(v[1], v[4]),//4
                new Edge(v[0], v[3]),//5
                new Edge(v[0], v[4]),//6
                new Edge(v[0], v[5]),//7
                new Edge(v[4], v[3]),//8
                new Edge(v[5], v[3]),//9
                new Edge(v[4], v[5]),//10
                new Edge(v[5], v[2]),//11
                new Edge(v[3], v[2])//12
        };
    }

    public Edge[] getEdges() {
        return e;
    }

    public Edge getEdge(int i) {
        return e[i];
    }

    public int getEdgeX(int edgeIndex, int vertex) {

        if (vertex == 0) {
            return e[edgeIndex].getStart()[0];
        } else {
            return e[edgeIndex].getEnd()[0];
        }
    }

    public int getEdgeY(int edgeIndex, int vertex) {

        if (vertex == 0) {
            return e[edgeIndex].getStart()[1];
        } else {
            return e[edgeIndex].getEnd()[1];
        }
    }

    public float getThickness(int i) {
        return e[i].getThickness() * scale;
    }

    public void setThickness(float thickness){
        for(int i = 0; i < e.length; i++){
            e[i].setThickness(thickness);
        }
    }

    public void setColor(int color){
        for(int i = 0; i < e.length; i++){
            e[i].setColor(color);
        }
    }

    public void setColorChange(boolean willChange){
        for(int i = 0; i < e.length; i++){
            e[i].setColorChange(willChange);
        }
    }
}


