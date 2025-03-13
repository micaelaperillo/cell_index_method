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

    public static void main(String[] args) throws IOException {
        ParametersParser parser= new ParametersParser();
        parser.parseParams();
        long startTime=System.currentTimeMillis();

        cellIndexMethod(parser);

        long endTime=System.currentTimeMillis();
        System.out.println("algorithm ended in " + (endTime-startTime) + "ms");
    }

    public static void cellIndexMethod(ParametersParser parser) throws IOException{
        cells=new ArrayList<>();

        M= parser.getMatrixSize();
        L= parser.getSquareLength();
        N= parser.getParticlesAmount();
        contourEnabled=parser.isContourEnabled();
        interactionRadius= parser.getInteractionRadius();

        List<Particle> particleList=parser.getParticles();
        for(int i=0;i<M*M;i++){
            cells.add(new ArrayList<>());
        }

        for(Particle p: particleList){
            p.cellX=getCellCoordinate(p.getPos_x());
            p.cellY=getCellCoordinate(p.getPos_y());
            int cellIndex=p.cellX*M+p.cellY;
            p.setCellIndex(cellIndex);
            cells.get(cellIndex).add(p);
        }

        for(int i=0;i<cells.size();i++){ //calcular el indice de la lista en funcion de su posicion como matriz
            int row=i/M;
            int col=i%M;

            int topRow=(row-1+M)%(M);
            int topIndex=topRow*(M)+col;

            int rightCol = (col+1) % (M);
            int rightIndex = (row * (M)) + rightCol;

            int topRightIndex=topRow*M + rightCol;

            int bottomRow = (row+1 +M) % (M);
            int bottomRightCol=(col+1)%(M);
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
            if(p.id != particle.id && p.distance(particle) - p.radius-particle.radius < interactionRadius){
                p.addNeighbour(particle);
                particle.addNeighbour(p);
            }
        }

    }

    private static int getCellCoordinate(double position){
        return (int)Math.floor(position/((double) L /M));
    }
    private static int getCellIndex(double x,double y){
        return getCellCoordinate(x)*M + getCellCoordinate(y);
    }

    public void bruteForce(ParametersParser parser){

    }

}
