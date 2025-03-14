package cell_index_method.src;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CellIndexMethod {

    private static List<List<Particle>> cells;//celdas que contienen las particulas
    private static int M,L,N;
    private static boolean contourEnabled;
    private static double interactionRadius;
    private static List<Particle> particleList;
    public static void main(String[] args) throws IOException {
        ParametersParser parser= new ParametersParser();
        parser.parseParams();

        M= parser.getMatrixSize();
        L= parser.getSquareLength();
        N= parser.getParticlesAmount();
        contourEnabled=parser.isContourEnabled();
        interactionRadius= parser.getInteractionRadius();
        particleList=parser.getParticles();

        long startTime=System.currentTimeMillis();

        cellIndexMethod();

        long endTime=System.currentTimeMillis();
        System.out.println("algorithm ended in " + (endTime-startTime) + "ms");

        // Ejercicio 2
        testAlgo(200, 1000);
        testAlgo(100, 1000);
        testAlgo(50, 1000);
    }

    private static void testAlgo(Integer N, Integer M) throws IOException {
        L = 20;
        interactionRadius = 1;
        Double r = 0.25;
        particleList = generateParticles(N, r);

        System.out.println("Number of particles: " + N + " Matrix size: " + M);
        long startTime=System.currentTimeMillis();
        cellIndexMethod();
        long endTime=System.currentTimeMillis();
        System.out.println("Algorithm ended in " + (endTime-startTime) + "ms");
    }

    private static List<Particle> generateParticles(Integer n, Double r) {
        List<Particle> particles = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Particle p = new Particle(i, r,1);
            p.setPos_x(Math.random()*L);
            p.setPos_y(Math.random()*L);
            particles.add(p);
        }

        return particles;
    }

    public static void cellIndexMethod() throws IOException{
        cells=new ArrayList<>();

        for(int i=0;i<M*M;i++){
            cells.add(new ArrayList<>());
        }

        for(Particle p: particleList){
            p.cellX=getCellCoordinate(p.getPos_x());
            p.cellY=getCellCoordinate(p.getPos_y());
            int cellIndex=p.cellY*M+p.cellX;
            p.setCellIndex(cellIndex);
            cells.get(cellIndex).add(p);
        }

        for(int i=0;i<cells.size();i++){ //calcular el indice de la lista en funcion de su posicion como matriz
            int row=i/M;
            int col=i%M;
            int topRow,rightCol,bottomRow,bottomRightCol;
            if(contourEnabled){
                topRow=(row-1+M)%(M);
                rightCol =(col+1) % (M);
                bottomRow=(row+1 +M) % (M);
                bottomRightCol=(col+1)%(M);
            }
            else {
                topRow = Math.max(row - 1, 0);
                rightCol =Math.min(col+1,M-1);
                bottomRow=Math.min(row+1,M-1);
                bottomRightCol=Math.min(col+1,M-1);
            }

            int topIndex=topRow*(M)+col;
            int rightIndex = (row * (M)) + rightCol;
            int topRightIndex=topRow*M + rightCol;
            int bottomRightIndex = (bottomRow * (M)) + bottomRightCol;
            for(Particle p:cells.get(i)){


                checkCell(p,i);
                checkCell(p,rightIndex); //indice a la derecha
                checkCell(p,bottomRightIndex); //indice abajo a la derecha
                checkCell(p,topIndex);//indice arriba
                checkCell(p,topRightIndex); //indice arriba a la dercecha
            }
        }

        Path path= Paths.get("output");
        Files.deleteIfExists(path);

        for(Particle p:particleList){
            StringBuilder stringToWrite=new StringBuilder().append(p.id);
            for(Particle neighbour:p.getNeighbours()){
                stringToWrite.append(" ").append(neighbour.id);
            }
            stringToWrite.append("\n");
            Files.write(path,stringToWrite.toString().getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }
    }

    private static void checkCell(Particle p,int cellIndex){
        for(Particle particle:cells.get(cellIndex)){
            if(!p.id.equals(particle.id) && p.distance(particle,contourEnabled,L) - p.radius-particle.radius < interactionRadius){
                p.addNeighbour(particle);
                particle.addNeighbour(p);
            }
        }

    }

    private static int getCellCoordinate(double position){
        return (int)Math.floor(position/((double) L /M));
    }


    public void bruteForce(){
        for(Particle p:particleList){
            for(Particle particle:particleList){
                if(!p.id.equals(particle.id) && p.distance(particle,contourEnabled,L) - p.radius-particle.radius < interactionRadius){
                    p.addNeighbour(particle);
                    particle.addNeighbour(p);
                }
            }
        }
    }

}
