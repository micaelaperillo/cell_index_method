package cell_index_method.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ParametersParser {
    private int particlesAmount;
    private int squareLength;
    private int matrixSize;
    private double interactionRadius;
    private boolean contourEnabled;

    private final List<Particle> particles=new ArrayList<>();

    public void parseParams() throws IOException{
        interactionRadius=Double.parseDouble(System.getProperty("rc","0.25"));
        matrixSize=Integer.parseInt(System.getProperty("L","20"));
        String dynamicPath=System.getProperty("dpath");
        String staticPath=System.getProperty("spath");
        String contour=System.getProperty("contour","false");
        contourEnabled=Boolean.parseBoolean(contour);
        if(dynamicPath==null || staticPath==null){
            throw new IllegalArgumentException("missing file parameter");
        }
        parseFiles(staticPath,dynamicPath);

    }
    public List<Particle> getParticles(){
        return List.copyOf(particles);
    }
    public boolean isContourEnabled() {
        return contourEnabled;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public int getSquareLength() {
        return squareLength;
    }

    public int getParticlesAmount() {
        return particlesAmount;
    }

    private void parseFiles(String staticFilePath, String dynamicFilePath) throws IOException{
        parseStaticFile(staticFilePath);
        parseDynamicFile(dynamicFilePath);
    }

    private void parseStaticFile(String filePath) throws IOException{
        Scanner scanner= new Scanner(new File(filePath));

        particlesAmount =scanner.nextInt();
        squareLength=scanner.nextInt();


        for(int i=0;scanner.hasNext();i++){
            String line =scanner.next();
            String[] lineSplit=line.split(" ");
            particles.add(new Particle(i,Double.parseDouble(lineSplit[0]),Integer.parseInt(lineSplit[1])));
        }
    }

    private void parseDynamicFile(String filePath) throws IOException{

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
