package cell_index_method.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ParametersParser {
    private int particlesAmount;
    private int areaLength;


    private final List<Particle> particles=new ArrayList<>();


    public void parseStaticFile(String filePath) throws IOException{
        Scanner scanner= new Scanner(new File(filePath));

        particlesAmount =scanner.nextInt();
        areaLength=scanner.nextInt();


        for(int i=0;scanner.hasNext();i++){
            String line =scanner.next();
            String[] lineSplit=line.split(" ");
            particles.add(new Particle(i,Double.parseDouble(lineSplit[0]),Integer.parseInt(lineSplit[1])));
        }
    }

    public void parseDynamicFile(String filePath) throws IOException{

        Scanner scanner =new Scanner(new File(filePath));

       for(int i=0; scanner.hasNext();i++){
           double period=scanner.nextDouble();
           for(int particleNumber=0;i<particlesAmount;i++){
               double x=scanner.nextDouble();
               double y= scanner.nextDouble();
               Particle particle=particles.get(particleNumber);
               particle.setPos_x(x);
               particle.setPos_y(y);
           }
       }

    }
}
