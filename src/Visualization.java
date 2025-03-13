package cell_index_method.src;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Visualization {

    public static void main(String[] args) throws Exception {

        int id = 65;

        int multiplier = 10;

        ParametersParser parser = new ParametersParser();
        parser.parseParams();

        JFrame frame = new JFrame("Cell Index Method - Simulacion de Sistemas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(parser.getSquareLength() * (multiplier + 1), parser.getSquareLength() * (multiplier + 1));
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        Map<Integer, List<Integer>> neighbors = parseOutputFile();

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (Particle p : parser.getParticles()) {
                    g2d.setColor(Color.BLACK);
                    if (p.id == id) {
                        g2d.setColor(Color.RED);
                    }
                    if (neighbors.get(id).contains(p.id)) {
                        g2d.setColor(Color.GREEN);
                    }
                    g2d.fillOval((int) Math.ceil(p.getPos_x() * multiplier) , (int) Math.ceil(p.getPos_y() * multiplier), (int) Math.ceil(p.radius * multiplier)*2, (int) Math.ceil(p.radius * multiplier)*2 );
                }
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }

    private static Map<Integer, List<Integer>> parseOutputFile() throws IOException {
        Scanner scanner = new Scanner(new File("output"));

        Map<Integer, List<Integer>> neighbors = new HashMap<>();

        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            List<Integer> neighbors_ids = new ArrayList<>();
            for (int i = 1; i < line.length; i++) {
                neighbors_ids.add(Integer.parseInt(line[i]));
            }
            neighbors.put(Integer.parseInt(line[0]), neighbors_ids);
        }

        return neighbors;

    }
}
