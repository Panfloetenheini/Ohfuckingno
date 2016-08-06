import java.awt.*;

import static java.lang.System.exit;

/**
 * Created by Roman on 07.07.2016.
 */
public class Ohfuckingno {
    public static final int EMTPY = -1;
    public static final int RED = 0;
    public static final int BLUE = -2;
    Field f;
    public Ohfuckingno (String[] input) {
        f = new Field(5);
        f.parseUsermapString(input);
        init();
    }

    public static void main(String args[]) {
        String input[] = {
                "-1,1,-1,-1,3",
                "1,-1,-1,3,-1",
                "-1,2,-1,-1,2",
                "-1,5,-1,-1,-1",
                "2,-1,0,0,-1"};
        //-1 leer
        //1+ blau
        //0 rot
        //-2 selbst belegt
        String inputx[] = {
                "0,0,0,0,0",
                "0,0,0,0,0",
                "0,1,0,0,0",
                "0,0,0,0,0",
                "0,0,0,0,0"};
        Ohfuckingno o = new Ohfuckingno(input);
    }

    private void init() {
        drawField();
        StdDraw.show();
        game();
    }

    public void game() {
        while (true) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            if (StdDraw.mousePressed()) {
                int xround = (int)(x*f.getResolution())+1 ;
                int yround = f.getResolution()-(int)(y*f.getResolution()) ;
                if (xround<=f.getResolution() && xround>0 && yround<=f.getResolution() && yround>0) {
                    f.rotate(xround, yround);
                    System.out.println("x="+xround+",y="+yround+",value="+f.get(xround,yround)+"finished?="+f.isEverythingFilledOut());
                    if (f.isEverythingFilledOut() && f.checkIfEverythingIsRight())
                        exit(0);
                    StdDraw.show(100);
                }
                StdDraw.clear();
                drawField();
                StdDraw.show();
            }
            StdDraw.show(10);
        }
    }

    public void drawField() {
        for (int x=1;x<=f.getResolution();x++) {
            for (int y=1;y<=f.getResolution();y++) {
                double ix = (x - 0.5) / (double) f.getResolution();
                double iy = (f.getResolution() - y + 0.5) / (double) f.getResolution();
                double radius = (1.0 / f.getResolution()) / 2.1;
                if (f.get(x, y) == 0) {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.filledCircle(ix, iy, radius);
                }
                else if (f.get(x, y) == -1) {
                    StdDraw.setPenColor(Color.GRAY);
                    StdDraw.filledCircle(ix, iy, radius);
                }
                else {
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.filledCircle(ix,iy,radius);
                    StdDraw.setPenColor(Color.WHITE);
                    if (f.get(x, y) != -2)
                        StdDraw.text(ix,iy,f.get(x,y)+"");
                }
            }
        }
    }
}
