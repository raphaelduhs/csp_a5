import org.jacop.constraints.Sum;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.*;

public class Task3 {

    public static void main(String[] args) {

        Store store = new Store(); // define FD store
        int size = 4;
        int fields = size*size;

        // define finite domain variables
        IntVar[] v = new IntVar[fields];
        for (int i = 0; i < fields; i++)
            v[i] = new IntVar(store, "v" + i, 0, 1);

        //constraints

        //row
        for (int i = 0; i < fields; i = i + 4) {
            IntVar[] vtmp = {v[i], v[i + 1], v[i + 2], v[i + 3]};
            IntVar sum = new IntVar(store, "sum",0,1);
            store.impose(new Sum(vtmp,sum));
        }


        //column
        for (int i = 0; i < size; i++) {
            IntVar[] vtmp = {v[i],v[i+4],v[i+8],v[i+12],};
            IntVar sum = new IntVar(store, "sum",0,1);
            store.impose(new Sum(vtmp,sum));
        }


        //max amount of queens
        IntVar sumGlobal = new IntVar(store, "sumGlobal",size,size);
        store.impose(new Sum(v,sumGlobal));

        // diagonal rechts

        IntVar[] vtmpr = {v[1],v[4]};
        IntVar sum = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpr,sum));

        IntVar[] vtmpr2 = {v[2],v[5], v[8]};
        IntVar sum2 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpr2,sum2));

        IntVar[] vtmpr3 = {v[3],v[6], v[9], v[12]};
        IntVar sum3 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpr3,sum3));

        IntVar[] vtmpr4 = {v[7],v[10], v[13]};
        IntVar sum4 = new IntVar(store, "sum",0,1);
         store.impose(new Sum(vtmpr4,sum4));

        IntVar[] vtmpr5 = {v[11],v[14]};
        IntVar sum5 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpr5,sum5));

        //*****************************

        // diagonal links

        IntVar[] vtmpl = {v[8],v[13]};
        IntVar sum6 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpl,sum6));

        IntVar[] vtmpl2 = {v[4],v[9], v[14]};
        IntVar sum7 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpl2,sum7));

        IntVar[] vtmpl3 = {v[0],v[5], v[10], v[15]};
        IntVar sum8 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpl3,sum8));

        IntVar[] vtmpl4 = {v[1],v[6], v[11]};
        IntVar sum9 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpl4,sum9));

        IntVar[] vtmpl5 = {v[2],v[7]};
        IntVar sum10 = new IntVar(store, "sum",0,1);
        store.impose(new Sum(vtmpl5,sum10));


        // search for a solution and print results
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new SimpleSelect<IntVar>(v,
                        new SmallestDomain<IntVar>(),
                        new IndomainMin<IntVar>());

        boolean result = label.labeling(store, select);

        if(result){
            for (int i = 0; i < fields; i++) {
                if (i % 4 == 0) {
                    System.out.println("");
                    System.out.println("------------------");
                }
                System.out.print(" " + v[i].value() + " | ");
            }
        }else {
            System.out.println("*** No");
        }
    }
}