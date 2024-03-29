package Features;

import Tools.ShareTools;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Decompose_Matrices extends ShareTools {
    private final float[][] M;
    private final String fn;
    private final String ne;
    private PrintWriter fr;

    public Decompose_Matrices(float[][] nM, String fn, String ne) {
        this.M = nM;
        this.fn = fn;
        this.ne = ne;
        this.fr = null;
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
    private boolean Is_Symmetrical_Matrix(float[][] M) {
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
    private boolean Is_Values_Positives(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] < 0) {
                return false;
            }
        }
        return true;
    }

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
    // show elementary actions for sum between rows in the matrices
    private void Sum_Elementary_Action(float k, int j, int i) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            k = (float) (Math.round(k * 10000.0) / 10000.0);
            if (k > 0) {
                if (k % 1 == 0) {
                    if (k == 1) {
                        fr.println("R" + r + " --> R" + r + " - R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    fr.println("R" + r + " --> R" + r + " - " + k + "*R" + c);
                } else if (fn.equals("rational")) {
                    fr.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c);
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        fr.println("R" + r + " --> R" + r + " + R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + " + (int) ((-1) * k) + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    fr.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c);
                } else if (fn.equals("rational")) {
                    fr.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c);
                }
            }
            fr.println();
        }
    }

    // show elementary actions for multiplication of a row in the matrices
    private void Mul_Elementary_Action(float k, int j) {
        if (k != 1) {
            int r = j + 1;
            k = (float) (Math.round(k * 10000.0) / 10000.0);
            if (k % 1 == 0) {
                if (k == -1) {
                    fr.println("R" + r + " --> - R" + r);
                } else {
                    fr.println("R" + r + " --> " + (int) k + "*R" + r);
                }
            } else if (fn.equals("decimal")) {
                fr.println("R" + r + " --> " + k + "*R" + r);
            } else if (fn.equals("rational")) {
                fr.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r);
            }
            fr.println();
        }
    }

    ////////////////////////////////////// Methods to Solution (Decompose M) ////////////////////////////////////////
    // get the LU decomposition of M (first algorithm)
    private void From_M_To_LU_V1(float[][] M) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        fr.println("transform M matrix to U by an upper ranking:");
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                L[j][i] = M[j][i] / M[i][i];
                Sum_Elementary_Action(L[j][i],j,i);
                for (int k = 0; k < n; k++) {
                    M[j][k] -= M[i][k] * L[j][i];
                }
                M[j][i] = 0;
                if (L[j][i] != 0) {
                    fr.println(Display_Status_Matrix(M,fn));
                }
            }
            for (int k = i; k < n; k++) {
                U[i][k] = M[i][k];
            }
        }
        fr.println("L = ");
        fr.println(Display_Status_Matrix(L,fn));
        fr.println("U = ");
        fr.println(Display_Status_Matrix(U,fn));
    }

    // get the LL' decomposition of M (first algorithm)
    private void From_M_To_LLT_V1(float[][] M) {
        if (Is_Symmetrical_Matrix(M) && Is_Values_Positives(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] LT = new float[n][n];
            fr.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n && M[i][i] != 0; i++) {
                for (int j = i + 1; j < n; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        fr.println(Display_Status_Matrix(M,fn));
                    }
                }
                float c = (float) Math.sqrt(M[i][i]);
                Mul_Elementary_Action(1 / c,i);
                for (int k = i; k < n; k++) {
                    M[i][k] /= c;
                    L[k][i] = M[i][k];
                    LT[i][k] = L[k][i];
                }
                if (c != 1) {
                    fr.println(Display_Status_Matrix(M,fn));
                }
            }
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (first algorithm)
    private void From_M_To_LDLT_V1(float[][] M) {
        if (Is_Symmetrical_Matrix(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] D = new float[n][n];
            float[][] LT = new float[n][n];
            fr.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n; i++) {
                L[i][i] = 1;
                for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        fr.println(Display_Status_Matrix(M,fn));
                    }
                }
                D[i][i] = M[i][i];
                if (D[i][i] != 0) {
                    Mul_Elementary_Action(1 / D[i][i],i);
                    for (int k = i; k < n; k++) {
                        M[i][k] /= D[i][i];
                        L[k][i] = M[i][k];
                        LT[i][k] = L[k][i];
                    }
                    if (D[i][i] != 1) {
                        fr.println(Display_Status_Matrix(M,fn));
                    }
                } else {
                    LT[i][i] = 1;
                }
            }
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("D = ");
            fr.println(Display_Status_Matrix(D,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix");
        }
    }

    // get the LU decomposition of M (second algorithm)
    private void From_M_To_LU_V2(float[][] M) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = 0; j < n; j++) {
                int m = Math.min(i,j);
                for (int k = 0 ;k < m ;k++) {
                    M[i][j] -= L[i][k] * U[k][j];
                }
                if (i <= j || U[j][j] == 0) {
                    U[i][j] = M[i][j];
                } else {
                    L[i][j] = M[i][j] / U[j][j];
                }
            }
        }
        fr.println("L = ");
        fr.println(Display_Status_Matrix(L,fn));
        fr.println("U = ");
        fr.println(Display_Status_Matrix(U,fn));
    }

    // get the LL' decomposition of M (second algorithm)
    private void From_M_To_LLT_V2(float[][] M) {
        if (Is_Symmetrical_Matrix(M) && Is_Values_Positives(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] LT = new float[n][n];
            for (int j = 0; j < n; j++) {
                for (int i = j; i < n; i++) {
                    for (int k = 0; k < j; k++) {
                        M[i][j] -= L[i][k] * LT[k][j];
                    }
                    L[i][j] = M[i][j] / (float) Math.sqrt(M[j][j]);
                    LT[j][i] = L[i][j];
                }
            }
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (second algorithm)
    private void From_M_To_LDLT_V2(float[][] M) {
        if (Is_Symmetrical_Matrix(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] D = new float[n][n];
            float[][] LT = new float[n][n];
            for (int j = 0; j < n; j++) {
                L[j][j] = 1;
                for (int i = j; i < n; i++) {
                    for (int k = 0; k < j; k++) {
                        M[i][j] -= L[i][k] * D[k][k] * LT[k][j];
                    }
                    if (M[j][j] != 0) {
                        L[i][j] = M[i][j] / M[j][j];
                        LT[j][i] = L[i][j];
                    } else {
                        LT[i][i] = 1;
                    }
                }
                D[j][j] = M[j][j];
            }
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("D = ");
            fr.println(Display_Status_Matrix(D,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix");
        }
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
                From_M_To_LU_V1(M);
            }
            case 2 -> {
                fr.println("find L and L' by decomposition of M:");
                From_M_To_LLT_V1(M);
            }
            case 3 -> {
                fr.println("find L, D and L' by decomposition of M:");
                From_M_To_LDLT_V1(M);
            }
            case 4 -> {
                fr.println("find L and U by decomposition of M:");
                From_M_To_LU_V2(M);
            }
            case 5 -> {
                fr.println("find L and L' by decomposition of M:");
                From_M_To_LLT_V2(M);
            }
            case 6 -> {
                fr.println("find L, D and L' by decomposition of M:");
                From_M_To_LDLT_V2(M);
            }
            default -> throw new Exception("you entered an invalid number");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = M.length, n = M[0].length;
            LocalDateTime cur = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File file = new File("Results/Decompose_Matrices/R" + n + "/" + ne.split("\\.")[0] + "_" + cur.format(formatter) + ".txt");
            fr = new PrintWriter(new FileWriter(file, true));
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
