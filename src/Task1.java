

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

import java.lang.reflect.Array;


public class Task1 {

    static Task1 m = new Task1();

    public static void main(String[] args) {


        Store store = new Store(); // define FD store

        int size = 19;

        // define finite domain variables
        //a1-a4 sind hilfvariablen
        //x1 - x4 ist der Übertrag
        String[] variables = {"w", "e", "i", "b", "n", "l", "x1", "x2", "x3", "x4", "a1", "a2", "a3", "a4", "m1", "m2", "m3", "m4", "ten"};


        IntVar[] v = new IntVar[size];
        //IntVar[] v_var = new IntVar[variables.length];

        for (int i = 0; i < size; i++) {


            if (variables[i].substring(0, 1).equals("a")) {

                v[i] = new IntVar(store, variables[i], 0, 20);// define helping constraints


            } else if (variables[i].substring(0, 1).equals("m")) {

                v[i] = new IntVar(store, variables[i], 0, 20);// define helping constraints


            } else if (variables[i].substring(0, 1).equals("x")) {

                v[i] = new IntVar(store, variables[i], 0, 1);// define helping constraints


            } else if (variables[i].equals("ten")) {

                v[i] = new IntVar(store, variables[i], 10, 10);// define helping constraints


            } else {

                v[i] = new IntVar(store, variables[i], 0, 9);// define constraints

            }

        }

        //all dif just on the variable of the quiz not the Helping variables
        IntVar[] vtmpl = {v[0], v[1], v[2], v[3], v[4], v[5]};
        //global
        store.impose(new Alldiff(vtmpl));



        //higher order
        store.impose(new XplusYeqZ(v[3], v[4], v[10])); // b+n = a1
        store.impose(new XplusYeqZ(v[1], v[14], v[10])); // e+m1 = a1
        store.impose(new XmulCeqZ(v[6], 10, v[14])); // x1*10 = m1


        store.impose(new XplusYplusQeqZ(v[6], v[2], v[2], v[11])); // x1+i+i = a2
        store.impose(new XmulYeqZ(v[7], v[18], v[15])); // x2*10 = m2
        store.impose(new XplusYeqZ(v[3], v[15], v[11])); // b+m2 = a2


        store.impose(new XplusYplusQeqZ(v[7], v[1], v[1], v[12])); // x2+e+e = a3
        store.impose(new XmulCeqZ(v[8], 10, v[16])); // x3*10 = m3
        store.impose(new XplusYeqZ(v[1], v[16], v[12])); // e+m3 = a3


        store.impose(new XplusYplusQeqZ(v[8], v[0], v[0], v[13])); // x3+w+w = a4
        store.impose(new XmulCeqZ(v[9], 10, v[17])); // x4*1 = m4
        store.impose(new XplusYeqZ(v[2], v[17], v[13])); // i+m4 = a4


        //unary
        store.impose(new XneqC(v[0], 0)); // w != 0
        store.impose(new XneqC(v[5], 0)); // l != 0

        //binary
        store.impose(new XeqY(v[5], v[9])); // x4 = l


        // search for a solution and print results
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                new InputOrderSelect<IntVar>(store, v,
                        new IndomainMin<IntVar>());

        boolean result = search.labeling(store, select);


        if (result)
            System.out.println("Solution: " + v[0] + ", " + v[1] + ", " +
                    v[2] + ", " + v[3] + ", " + v[4] + ", " + v[5]);
        else {

            System.out.println("*** No");

            for (int i = 0; i < size; i++) {

                System.out.println(v[i]);

            }
        }


    }

}