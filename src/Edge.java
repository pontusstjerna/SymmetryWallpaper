package pontus.symmetry;

import java.util.Random;

/**
 * Created by Pontus on 2015-12-14.
 */

public class Edge {

    private int[] start, end;

    private float thickness = 0.13f;
    private int alpha = 255;
    private int change = 1;
    private int rSpeed = 6;
    private boolean showRgb = false;
    private boolean changeColor = true;
    int[] cIntervals =
            {100, 130, //Red min max
                    120, 150,  //Green min max
                    200, 250};//Blue min max
    private int[] rgbColor = {cIntervals[1], cIntervals[3], cIntervals[5]};

    private Random rand = new Random();
    private int speed = rand.nextInt(rSpeed);

    public Edge(int[] start, int[] end) {
        this.start = start;
        this.end = end;
    }

    public int[] getRgbColor() {
        return rgbColor;
    }

    public int[] getRgbaColor() {
        return new int[]{rgbColor[0], rgbColor[1], rgbColor[2], alpha};
    }

    public float getThickness() {
        return thickness;
    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }

    public int getR() {
        return colorSafe(rgbColor[0]);
    }

    public int getG() {
        return colorSafe(rgbColor[1]);
    }

    public int getB() {
        return colorSafe(rgbColor[2]);
    }

    public int getAlpha() {
        return colorSafe(alpha);
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public void setColorChange(boolean willChange){changeColor = willChange;}

    private int colorSafe(int colValue) {
        if (colValue > 255) {
            return 255;
        } else if (colValue < 1) {
            return 1;
        }
        return colValue;
    }

    void changeColor() {
        for (int i = 0; i < rgbColor.length && changeColor; i++) {
            if (rgbColor[i] < cIntervals[i * 2]) {
                rgbColor[i] += rand.nextInt(speed + 1) + speed;
            } else if (rgbColor[i] > cIntervals[i * 2 + 1]) {
                rgbColor[i] -= rand.nextInt(speed + 1) + speed;
            } else {
                rgbColor[i] += change * rand.nextInt(speed + 1);
            }
        }

        if (showRgb) {
            System.out.println("Red: " + rgbColor[0] + " Green: " + rgbColor[1] + " Blue: " + rgbColor[2] + " Alpha: " + alpha);
        }

        alpha += change * speed;

        if (alpha > 250) {
            change = -1;
            speed = rand.nextInt(rSpeed);
        } else if (alpha < 10) {
            change = 2;
            speed = rand.nextInt(rSpeed);
        }
    }

    public void setRgb(){

    }

    public void setColor(int color){

        switch (color){
            case 0: //BLUE
                cIntervals = new int[]{70, 130, //Red min max
                        120, 160,  //Green min max
                        200, 250};//Blue min max};
                break;
            case 1: //GREEN
                cIntervals = new int[]{30, 100, //Red min max
                        150, 250,  //Green min max
                        100, 140};//Blue min max};
                break;
            case 2: //RED
                cIntervals = new int[]{180, 250, //Red min max
                        50, 90,  //Green min max
                        50, 90};//Blue min max};
                break;
            case 3: //YELLOW
                cIntervals = new int[]{190, 250, //Red min max
                        100, 250,  //Green min max
                        10, 80};//Blue min max};
                break;
            case 4: //Purple
                cIntervals = new int[]{140, 250, //Red min max
                        50, 120,  //Green min max
                        150, 250};//Blue min max};
                break;
            case 5: //Cyan
                cIntervals = new int[]{50, 100, //Red min max
                        100, 250,  //Green min max
                        150, 250};//Blue min max};
                break;
            case 6: //Rainbow
                cIntervals = new int[]{5, 250, //Red min max
                        5, 250,  //Green min max
                        5, 250};//Blue min max};
        }
        rgbColor = new int[]{cIntervals[1], cIntervals[3], cIntervals[5]};
    }
}
