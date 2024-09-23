package Features.Decompose_Matrices;

import Features.ShareTools;
import Features.Decompose_Matrices.Solution_Methods.*;
import java.util.Scanner;
import java.io.PrintWriter;

public class Decompose_Matrices extends ShareTools {
    private final float[][] M;

    public Decompose_Matrices(float[][] nM, String fn, String ne, PrintWriter fr) {
        super(fn, ne, fr);
        this.M = nM;
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for decompose matrices
    private static void User_Menu_Decompose() {
        System.out.println("choose number method to solution:");
        System.out.println("1. get L and U matrices by decomposition of M (first method)");
        System.out.println("2. get L and L' matrices by decomposition of M (first method)");
        System.out.println("3. get L, D and L' matrices by decomposition of M (first method)");
        System.out.println("4. get L and U matrices by decomposition of M (second method)");
        System.out.println("5. get L and L' matrices by decomposition of M (second method)");
        System.out.println("6. get L, D and L' matrices by decomposition of M (second method)");
    }

    ///////////////////////////////////////////////// Questions //////////////////////////////////////////////////
    // check if the matrix is a symmetrical matrix
    public boolean Is_Symmetrical_Matrix(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] != M[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if all the values in the main diagonal which are positive
    public boolean Is_Values_Positives(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] < 0) {
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////// User Interface (Decompose M) //////////////////////////////////////
    // get the matrix components
    private void Decompose_Matrix(float[][] M) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_Decompose();
        int op = sc.nextInt();
        switch (op) {
            case 1 -> {
                fr.println("find L and U by decomposition of M:");
                LU_Decomposition met = new LU_Decomposition(M,fn,ne,fr);
                met.From_M_To_LU_V1(M);
            }
            case 2 -> {
                fr.println("find L and L' by decomposition of M:");
                LLT_Decomposition met = new LLT_Decomposition(M,fn,ne,fr);
                met.From_M_To_LLT_V1(M);
            }
            case 3 -> {
                fr.println("find L, D and L' by decomposition of M:");
                LDLT_Decomposition met = new LDLT_Decomposition(M,fn,ne,fr);
                met.From_M_To_LDLT_V1(M);
            }
            case 4 -> {
                fr.println("find L and U by decomposition of M:");
                LU_Decomposition met = new LU_Decomposition(M,fn,ne,fr);
                met.From_M_To_LU_V2(M);
            }
            case 5 -> {
                fr.println("find L and L' by decomposition of M:");
                LLT_Decomposition met = new LLT_Decomposition(M,fn,ne,fr);
                met.From_M_To_LLT_V2(M);
            }
            case 6 -> {
                fr.println("find L, D and L' by decomposition of M:");
                LDLT_Decomposition met = new LDLT_Decomposition(M,fn,ne,fr);
                met.From_M_To_LDLT_V2(M);
            }
            default -> throw new Exception("you entered an invalid number");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = M.length, n = M[0].length;
            fr.println("decompose the next matrix (" + m + "*" + n + " size):");
            fr.println(Display_Status_Matrix(M,fn));
            if (m != n) {
                fr.println("this is a matrix which is not a square matrix");
            } else {
                fr.println("M = ");
                fr.println(Display_Status_Matrix(M,fn));
                Decompose_Matrix(M);
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
