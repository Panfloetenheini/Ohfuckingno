import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Roman on 07.07.2016.
 */
class Field {
    private int field[];
    private int res;
    private LinkedList<Integer> protectedFields = new LinkedList<Integer>();

    public Field (int res) {
        this.res = res;
        field = new int[res*res];
    }

    public int get(int x, int y) {
        if (x<1 || y>res) {
            throw new NumberFormatException();
        }
        return field[(x-1)*res+y-1];
    }

    public int getResolution() {
        return res;
    }

    public boolean isEverythingFilledOut(){
        for (int i=0; i<field.length ; i++) {
            if (field[i]==-1)
                return false;
        }
        return true;
    }

    public void rotate(int x, int y) {
        if(get(x,y)==0)
            set(-1,x,y);
        else if(get(x,y)==-1)
            set(-2,x,y);
        else if(get(x,y)==-2)
            set(0,x,y);
    }

    public void set(int value, int x, int y) {
        if (x<1 || x>res || y>res || y<1 || value>res || value<-2) {
            throw new NumberFormatException();
        }
        if (!isProtected(x,y))
            field[(x-1)*res+y-1] = value;
    }

    private boolean isProtected(int x, int y) {
        return protectedFields.contains((x-1)*res+y-1);
    }

    public void parseUsermapString(String[] input) {
        for(int i=0; i<input.length;i++) {
            if (!input[i].matches("^[0-9,-]+") || !input[i].matches("^[0-9,-]+"))
                throw new NumberFormatException();
            String[] temp = input[i].split(",");
            for (int j=0; j<temp.length;j++) {
                this.set(Integer.parseInt(temp[j]),j+1,i+1);
                if (Integer.parseInt(temp[j])!=-1)
                    protectedFields.add((j+1-1)*res+i+1-1);
            }
        }
    }

    public boolean checkIfEverythingIsRight(){ //miese laufzeit aber wayne, könnte man direkt mit der protected list machen, ist aber schicker, mois
        for (int i=1;i<res;i++) {
            for (int j=1;j<res;j++) {
                if (isProtected(i,j) && get(i,j)!=-1) {
                    if (getNeighbors(i,j)!=get(i,j)) {
                        //System.out.println("error at "+i+" "+j+"="+getNeighbors(i,j));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void solve() {
        //TODO
    }

    private int getNeighbors(int x, int y) {
        int neighbors = 0;
        boolean right=true,left=true,up=true,down=true;
        for (int i=1;i<=res;i++) { //x nach rechts
            if (right)
                if (x+i<=res && get(x+i,y)==Ohfuckingno.RED) {
                    right = false;
                } else if (x+i<=res ) {
                    neighbors++;
                }
            if (left)
                if (x-i>0 && get(x-i,y)==Ohfuckingno.RED) {
                    left = false;
                } else if (x-i>0) {
                    neighbors++;
                }
            if (down)
                if (y+i<=res && get(x,y+i)==Ohfuckingno.RED ) {
                    down = false;
                } else if (y+i<=res) {
                    neighbors++;
                }
            if (up)
                if (y-i>0 && get(x,y-i)==Ohfuckingno.RED) {
                    up = false;
                } else if (y-i>0) {
                    neighbors++;
                }
            if (!up && !down && !left && !right)
                return neighbors;
        }
        return neighbors;
    }
}
