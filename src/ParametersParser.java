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
        interactionRadius=Double.parseDouble(System.getProperty("rc","1"));
        matrixSize=Integer.parseInt(System.getProperty("M","20"));
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
            particles.add(new Particle(i,Double.parseDouble(scanner.next()),(int)Double.parseDouble(scanner.next())));
        }
    }

    private void parseDynamicFile(String filePath) throws IOException{
        Scanner scanner =new Scanner(new File(filePath));

        double period=scanner.nextDouble();
        for(int particleNumber=0;particleNumber<particlesAmount;particleNumber++){
            double x=Double.parseDouble(scanner.next());
            double y= Double.parseDouble(scanner.next());
            Particle particle=particles.get(particleNumber);
            particle.setPos_x(x);
            particle.setPos_y(y);
           }
       }

}
