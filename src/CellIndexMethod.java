package cell_index_method.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CellIndexMethod {

    private static List<List<Particle>> cells;//celdas que contienen las particulas
    private static int M,L,N;
    private static boolean contourEnabled;

    public static void main(String[] args) throws IOException {
        ParametersParser parser= new ParametersParser();
        parser.parseParams();
        long startTime=System.currentTimeMillis();

        cellIndexMethod(parser);

        long endTime=System.currentTimeMillis();
        System.out.println("algorithm ended in " + (endTime-startTime) + "ms");
    }

    public static void cellIndexMethod(ParametersParser parser){
        cells=new ArrayList<>();

        M= parser.getMatrixSize();
        L= parser.getSquareLength();
        N= parser.getParticlesAmount();

        for(int i=0;i<M;i++){
            cells.add(new ArrayList<>());
        }

        for(Particle p: parser.getParticles()){
            int cellIndex=getCellIndex(p.getPos_x(),p.getPos_y());
            p.setCellIndex(cellIndex);
            cells.get(cellIndex).add(p);
        }
        for(int i=0;i<cells.size();i++){
            for(Particle p:cells.get(i)){
                checkCell(p,i);
                checkCell(p,i+1);
                checkCell(p,i-L);
                checkCell(p,i+L);
                checkCell(p,i+L+1);
            }
        }
    }

    private static void checkCell(Particle p,int cellIndex){
        if(cellIndex>=M && !contourEnabled)
            return;

    }

    private static int getCellCoordinate(double position){
        return (int)Math.floor(position/L);
    }
    private static int getCellIndex(double x,double y){
        return getCellCoordinate(x)*M + getCellCoordinate(y);
    }

    public void bruteForce(ParametersParser parser){

    }

}
