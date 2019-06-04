

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

public class Main {
    static Main m = new Main();

    public static void main(String[] args) {

        Allsolution allsolution = new Allsolution();


        Store store = new Store(); // define FD store
        int size = 18;

        // define finite domain variables
        //a1-a4 sind hilfvariablen
        //x1 - x4 ist der Übertrag
        String[] variables = {"w", "e", "i", "b", "n", "l", "x1", "x2", "x3", "x4", "a1", "a2", "a3", "a4", "m1", "m2", "m3", "m4"};


        IntVar[] v = new IntVar[size];
        for (int i = 0; i < size; i++)
            v[i] = new IntVar(store, variables[i], 0, 9); // define constraints

        //higher order
        store.impose(new XplusYeqZ(v[3], v[4], v[10])); // b+n = a1
        store.impose(new XmulCeqZ(v[6], 10, v[14])); // x1*10 = m1
        store.impose(new XplusYeqZ(v[1], v[14], v[10])); // e+m1 = a1


        store.impose(new XplusYplusQeqZ(v[6], v[2], v[2], v[11])); // x1+i+i = a2
        store.impose(new XmulCeqZ(v[7], 10, v[15])); // x2*10 = m2
        store.impose(new XplusYeqZ(v[3], v[15], v[11])); // b+m2 = a2

        store.impose(new XplusYplusQeqZ(v[7], v[1], v[1], v[12])); // x2+e+e = a3
        store.impose(new XmulCeqZ(v[8], 10, v[16])); // x3*10 = m3
        store.impose(new XplusYeqZ(v[1], v[16], v[12])); // e+m3 = a3

        store.impose(new XplusYplusQeqZ(v[8], v[0], v[0], v[13])); // x3+w+w = a4
        store.impose(new XmulCeqZ(v[9], 10, v[17])); // x4*10 = m4
        store.impose(new XplusYeqZ(v[2], v[17], v[13])); // i+m4 = a4

        //global
        store.impose(new Alldifferent(v));


        //binary
        store.impose(new XneqC(v[0], 0)); // w != 0
        store.impose(new XneqC(v[5], 0)); // l != 0
        store.impose(new XeqY(v[9], v[5])); // x4 = l


        // search for a solution and print results
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<IntVar>(store, v,
                        new IndomainMin<IntVar>());

        boolean result = search.labeling(store, select);

        //System.out.println("HUGO" + search.getSolution());


        if (result)
            System.out.println("Solution: " + v[0] + ", " + v[1] + ", " +
                    v[2] + ", " + v[3]);
        else {

            System.out.println("*** No");
        }


    }
}