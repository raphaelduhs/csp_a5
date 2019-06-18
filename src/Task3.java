import org.jacop.constraints.Sum;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

import java.util.ArrayList;

public class Task3 {

    public static void main(String[] args) {

        double starttime = System.currentTimeMillis();

        Store store = new Store(); // define FD store
        int size = 23;   // set size for the chessboard and the number of queens
        int fields = size*size;

        // define finite domain variables ///////////////////////////////
        IntVar[] v = new IntVar[fields];
        for (int i = 0; i < fields; i++)
            v[i] = new IntVar(store, "v" + i, 0, 1);

        //constraints //////////////////////////////////////////////////

        //row
        for (int i = 0; i < fields; i = i + size) {
            IntVar[] vtmp = new IntVar[size];//{v[i], v[i + 1], v[i + 2], v[i + 3]};
            for(int j = 0; j < size; j++){
                vtmp[j] = v[i+j];
            }
            IntVar sum = new IntVar(store, "sum",0,1);
            store.impose(new Sum(vtmp,sum));
        }


        //column
        for (int i = 0; i < size; i++) {
            IntVar[] vtmp = new IntVar[size];//{v[i],v[i+4],v[i+8],v[i+12],};
            int index = 0;
            for(int j = 0; j < fields; j += size){
                vtmp[index] = v[i+j];
                index++;
            }
            IntVar sum = new IntVar(store, "sum",0,1);
            store.impose(new Sum(vtmp,sum));
        }

        // diagonal: top left -> bottom right
        for( int k = 0 ; k < size * 2 ; k++ ) {
            ArrayList<IntVar> list = new ArrayList<>();
            for( int j = 0 ; j <= k ; j++ ) {
                int i = k - j;
                if( i < size && j < size ) {
                    list.add(v[i*size+j]);
                    //System.out.print( v[i*size+j] + " " );
                }
            }
            IntVar[] vtemp = list.toArray(new IntVar[list.size()]);
            IntVar sum = new IntVar(store, "sum",0,1);
            store.impose(new Sum(vtemp,sum));
            //System.out.println();
        }

        // diagonal: bottom left -> top right
        int xStart = size-1;
        int yStart = 1;

        while(true){
            int xLoop, yLoop;
            if(xStart>=0){
                xLoop = xStart;
                yLoop = 0;
                xStart--;
            }else if(yStart<size){
                xLoop = 0;
                yLoop = yStart;
                yStart++;
            }else
                break;

            ArrayList<IntVar> list = new ArrayList<>();

            for(;xLoop<size && yLoop<size; xLoop++, yLoop++){
                list.add(v[xLoop*size+yLoop]);
                //System.out.print( v[xLoop*size+yLoop] + " " );
            }
            IntVar[] vtemp = list.toArray(new IntVar[list.size()]);
            IntVar sum = new IntVar(store, "sum",0,1);
            store.impose(new Sum(vtemp,sum));
            //System.out.println();
        }

        //max amount of queens
        IntVar sumGlobal = new IntVar(store, "sumGlobal",size,size);
        store.impose(new Sum(v,sumGlobal));


        // search for a solution and print results ////////////////////////////////
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new SimpleSelect<IntVar>(v,
                        new SmallestDomain<>(),
                        new IndomainRandom<>());

        boolean result = label.labeling(store, select);

        double endtime = System.currentTimeMillis();

        // print chess board
        if(result){
            for (int i = 0; i < fields; i++) {
                if (i % size == 0) {
                    System.out.println();
                }
                System.out.print(v[i].value() + " | ");
            }
        }else {
            System.out.println("*** No");
        }

        double duration = (endtime-starttime)/1000;

        System.out.println("Zeit: " + duration );


    }
}