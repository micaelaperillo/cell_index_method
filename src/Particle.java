package cell_index_method.src;

import java.lang.Integer;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Particle implements Comparable<Particle> {

    Integer id;
    double pos_x;
    double pos_y;
    int cellX,cellY;
    double radius;
    int cellIndex;
    int property;
    Set<Particle> neighbours=new TreeSet<>();


    public Particle(Integer id, double radius, int property){
        this.id = id;
        this.radius = radius;
        this.property=property;
    }
    public Set<Particle> getNeighbours(){
        return Set.copyOf(neighbours);
    }

    public boolean addNeighbour(Particle p){
       return neighbours.add(p);
    }

    public void setPos_x(double x){
        this.pos_x=x;
    }
    public void setPos_y(double y){
        this.pos_y=y;
    }
    public void setCellX(int cellX){this.cellX=cellX;}
    public int getCellX(){return cellX;}
    public void setCellY(int cellY){this.cellY=cellY;}
    public int getCellY(){return cellY;}


    public double getPos_y() {
        return pos_y;
    }

    public double getPos_x() {
        return pos_x;
    }
    public int getCellIndex(){
        return cellIndex;
    }
    public void setCellIndex(int cellIndex){this.cellIndex=cellIndex;}
    @Override
    public int compareTo(Particle p) {
        int idComparison = this.id.compareTo(p.id);
        if (idComparison != 0) {
            return idComparison;
        }
        int xComparison = Double.compare(this.pos_x, p.pos_x);
        if (xComparison != 0) {
            return xComparison;
        }
        return Double.compare(this.pos_y, p.pos_y);
    }

    public boolean overlaps(Particle p) {
        return p.pos_x >= this.pos_x - radius && p.pos_x <= this.pos_x + radius
                && p.pos_y >= this.pos_y - radius && p.pos_y <= this.pos_y + radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pos_x, pos_y, radius);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Particle p = (Particle) obj;
        return Objects.equals(id, p.id) &&
                Objects.equals(pos_x, p.pos_x) &&
                Objects.equals(pos_y, p.pos_y);
    }

    public Double distance(Particle p,boolean contourEnabled,double L) {
        if(!contourEnabled)
            return Math.sqrt(Math.pow(this.pos_x - p.pos_x, 2) + Math.pow(this.pos_y - p.pos_y, 2));;
        double dx=Math.abs(this.pos_x-p.pos_x);
        double dy=Math.abs(this.pos_y-p.pos_y);
        if(dx>L/2)
            dx=L-dx;
        if(dy>L/2)
            dy=L-dy;
        return Math.sqrt((dx*dx)+(dy*dy));
    }
}
