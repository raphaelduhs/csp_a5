import org.jacop.constraints.*;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

public class Task2 {
    static Task2 m = new Task2();

    public static void main(String[] args) {

        Allsolution allsolution = new Allsolution();


        Store store = new Store(); // define FD store
        int size = 16;

        // define finite domain variables


        IntVar[] v = new IntVar[size];
        for (int i = 0; i < size; i++)
            v[i] = new IntVar(store, "v"+i, 1, 4); // define constraints

        //reihe
        for (int i = 0; i < size; i=i+4) {
            IntVar[] vtmp = {v[i],v[i+1],v[i+2],v[i+3],};
            store.impose(new Alldifferent(vtmp));
        }

        //splate
        for (int i = 0; i < 4; i++) {
            IntVar[] vtmp = {v[i],v[i+4],v[i+8],v[i+12],};
            store.impose(new Alldifferent(vtmp));
        }

        // quadrant

        //q1
        IntVar[] vtmpq1 = {v[0],v[1],v[4],v[5],};
        store.impose(new Alldifferent(vtmpq1));

        //q2
        IntVar[] vtmpq2 = {v[2],v[3],v[6],v[7],};
        store.impose(new Alldifferent(vtmpq2));

        //q3
        IntVar[] vtmpq3= {v[8],v[9],v[12],v[13],};
        store.impose(new Alldifferent(vtmpq3));

        //q4
        IntVar[] vtmpq4= {v[10],v[11],v[14],v[15],};
        store.impose(new Alldifferent(vtmpq4));



        // search for a solution and print results
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<IntVar>(store, v,
                        new IndomainMin<IntVar>());

        boolean result = search.labeling(store, select);

        //System.out.println("HUGO" + search.getSolution());


        if (result)

            for (int i = 0; i < size; i++) {

                if (i%4==0) {
                    System.out.println("");
                }

                System.out.print(" " + v[i].value() + " | ");



            }
        else {

            System.out.println("*** No");
        }


    }
}