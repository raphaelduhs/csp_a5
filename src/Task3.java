import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Max;
import org.jacop.constraints.Sum;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

public class Task3 {

    static Task3 m = new Task3();

    public static void main(String[] args) {

        Allsolution allsolution = new Allsolution();


        Store store = new Store(); // define FD store
        int size = 4;
        int fields = size*size;


        // define finite domain variables


        IntVar[] v = new IntVar[fields];
        for (int i = 0; i < fields; i++)
            v[i] = new IntVar(store, "v" + i, 0, 1); // define constraints

        //reihe
        for (int i = 0; i < fields; i = i + 4) {

            IntVar[] vtmp = {v[i], v[i + 1], v[i + 2], v[i + 3],};
            IntVar sum = new IntVar(store, "sum",1,1);
            store.impose(new Sum(vtmp,sum));
        }


        //splate
        for (int i = 0; i < size; i++) {
            IntVar[] vtmp = {v[i],v[i+4],v[i+8],v[i+12],};
            IntVar sum = new IntVar(store, "sum",1,1);
            store.impose(new Sum(vtmp,sum));
        }


        //in summe 4
        IntVar sum = new IntVar(store, "sum",4,4);
        store.impose(new Sum(v,sum));

        //max 1
        IntVar max = new IntVar(store, "max",0,1);

        // rechts ausgerichtet

        /*

        IntVar[] vtmpr = {v[1],v[4]};
        store.impose(new Max(vtmpr,max));

        IntVar[] vtmpr2 = {v[2],v[5], v[8]};
        store.impose(new Max(vtmpr2,max));

        IntVar[] vtmpr3 = {v[3],v[6], v[9], v[12]};
        store.impose(new Max(vtmpr3,max));

        IntVar[] vtmpr4 = {v[7],v[10], v[13]};
        store.impose(new Max(vtmpr4,max));

        IntVar[] vtmpr5 = {v[11],v[14]};
        store.impose(new Max(vtmpr5,max));

        //*****************************

        // links ausgerichtet

        IntVar[] vtmpl = {v[8],v[13]};
        store.impose(new Max(vtmpl,max));

        IntVar[] vtmpl2 = {v[4],v[9], v[14]};
        store.impose(new Max(vtmpl2,max));

        IntVar[] vtmpl3 = {v[0],v[5], v[10], v[15]};
        store.impose(new Max(vtmpl3,max));

        IntVar[] vtmpl4 = {v[1],v[6], v[11]};
        store.impose(new Max(vtmpl4,max));

        IntVar[] vtmpl5 = {v[2],v[7]};
        store.impose(new Max(vtmpl5,max));


        */



        // search for a solution and print results
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<IntVar>(store, v,
                        new IndomainMin<IntVar>());

        boolean result = search.labeling(store, select);

        //System.out.println("HUGO" + search.getSolution());


        if (result)

            for (int i = 0; i < fields; i++) {

                if (i % 4 == 0) {
                    System.out.println("");
                    System.out.println("------------------");
                }

                System.out.print(" " + v[i].value() + " | ");

            }


        else {

            System.out.println("*** No");
        }


    }
}