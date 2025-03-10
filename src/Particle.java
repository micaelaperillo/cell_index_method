import java.lang.Integer;
import java.util.Objects;

public class Particle implements Comparable<Particle> {

    Integer id;
    Integer pos_x;
    Integer pos_y;
    Integer radius;

    Particle(Integer id, Integer pos_x, Integer pos_y, Integer radius){
        this.id = id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.radius = radius;
    }

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
        return Integer.compare(this.pos_y, p.pos_y);
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

    public Double distance(Particle p) {
        return Math.sqrt(Math.pow(this.pos_x - p.pos_x, 2) + Math.pow(this.pos_y - p.pos_y, 2));
    }
}
