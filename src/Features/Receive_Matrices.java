package Features;

import Tools.ShareTools;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Receive_Matrices extends ShareTools {
    private final float[][] L;
    private final String fn;
    private final String ne;
    private PrintWriter fr;

    public Receive_Matrices(float[][] nL, String repr, String file) {
        this.L = nL;
        this.fn = repr;
        this.ne = file.split("\\.")[0];
        this.fr = null;
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for receive matrices
    private static void User_Menu_Receive() {
        System.out.println("choose number method to solution:");
        System.out.println("1. LU decomposition by L and U multiplication (first method)");
        System.out.println("2. LL' decomposition by L and L' multiplication (first method)");
        System.out.println("3. LDL' decomposition by L, D and L' multiplication (first method)");
        System.out.println("4. LU decomposition by L and U multiplication (second method)");
        System.out.println("5. LL' decomposition by L and L' multiplication (second method)");
        System.out.println("6. LDL' decomposition by L, D and L' multiplication (second method)");
    }

    /////////////////////////////////////////////// Input Matrices ///////////////////////////////////////////////
    // create diagonal matrix
    public static float[][] Create_Diagonal_Matrix(int n) {
        Scanner sc = new Scanner(System.in);
        float[][] D = new float[n][n];
        System.out.println("insert values to the main diagonal:");
        for (int i = 0; i < n; i++) {
            System.out.print("D[" + i + "][" + i + "]->");
            D[i][i] = sc.nextFloat();
        }
        return D;
    }

    // create upper matrix
    public static float[][] Create_Upper_Matrix(int n) {
        Scanner sc = new Scanner(System.in);
        float[][] U = new float[n][n];
        System.out.println("insert values to the upper side:");
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                System.out.print("U[" + i + "][" + j + "]->");
                U[i][j] = sc.nextFloat();
            }
        }
        return U;
    }

    ///////////////////////////////////////////////// Questions //////////////////////////////////////////////////
    // check if all the values in the main diagonal which are equals to 1
    private boolean Is_One_Slant(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] != 1) {
                return false;
            }
        }
        return true;
    }

    /////////////////////////////////////// Methods to Solution (Receive M) /////////////////////////////////////////
    // get the LU decomposition by multiplication of L and U (first algorithm)
    private void From_LU_To_M_V1(float[][] L, float[][] U) {
        if (Is_One_Slant(L)) {
            fr.println("M = ");
            fr.println(Display_Status_Matrix(Mul_Mats(L,U),fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (first algorithm)
    private void From_LLT_To_M_V1(float[][] L, float[][] LT) {
        float[][] M = Mul_Mats(L,LT);
        fr.println("M = ");
        fr.println(Display_Status_Matrix(M,fn));
    }

    // get the LDL' decomposition by multiplication of L, D and L' (first algorithm)
    private void From_LDLT_To_M_V1(float[][] L, float[][] D, float[][] LT) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(Mul_Mats(L,D),LT);
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LU decomposition by multiplication of L and U (second algorithm)
    private void From_LU_To_M_V2(float[][] L, float[][] U) {
        if (Is_One_Slant(L)) {
            int n = L.length;
            float[][] M = new float[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int m = Math.min(i,j);
                    for (int k = 0; k <= m; k++) {
                        M[i][j] += L[i][k] * U[k][j];
                    }
                }
            }
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (second algorithm)
    private void From_LLT_To_M_V2(float[][] L, float[][] LT) {
        int n = L.length;
        float[][] M = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int m = Math.min(i,j);
                for (int k = 0; k <= m; k++) {
                    M[i][j] += L[i][k] * LT[k][j];
                }
            }
        }
        fr.println("M = ");
        fr.println(Display_Status_Matrix(M,fn));
    }

    // get the LDL' decomposition by multiplication of L, D and L' (second algorithm)
    private void From_LDLT_To_M_V2(float[][] L, float[][] D, float[][] LT) {
        if (Is_One_Slant(L)) {
            int n = L.length;
            float[][] M = new float[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int m = Math.min(i,j);
                    for (int k = 0; k <= m; k++) {
                        M[i][j] += L[i][k] * D[k][k] * LT[k][j];
                    }
                }
            }
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    /////////////////////////////////////// User Interface (Receive M) ///////////////////////////////////////
    // get the matrix by a multiplication components
    public void Receive_Matrix(float[][] L) throws Exception {
        int n = L.length;
        Scanner sc = new Scanner(System.in);
        User_Menu_Receive();
        int op = sc.nextInt();
        float[][] U, D, LT;
        switch (op) {
            case 1:
                fr.println("find the matrix by L and U multiplication:");
                U = Create_Upper_Matrix(n);
                fr.println("U = ");
                fr.println(Display_Status_Matrix(U,fn));
                From_LU_To_M_V1(L,U);
                break;
            case 2:
                fr.println("find the matrix by L and L' multiplication:");
                LT = Transpose(L);
                fr.println("L' = ");
                fr.println(Display_Status_Matrix(LT,fn));
                From_LLT_To_M_V1(L,LT);
                break;
            case 3:
                fr.println("find the matrix by L, D and L' multiplication:");
                D = Create_Diagonal_Matrix(n);
                fr.println("D = ");
                fr.println(Display_Status_Matrix(D,fn));
                LT = Transpose(L);
                fr.println("L' = ");
                fr.println(Display_Status_Matrix(LT,fn));
                From_LDLT_To_M_V1(L,D,LT);
                break;
            case 4:
                fr.println("find the matrix by L and U multiplication:");
                U = Create_Upper_Matrix(n);
                fr.println("U = ");
                fr.println(Display_Status_Matrix(U,fn));
                From_LU_To_M_V2(L,U);
                break;
            case 5:
                fr.println("find the matrix by L and L' multiplication:");
                LT = Transpose(L);
                fr.println("L' = ");
                fr.println(Display_Status_Matrix(LT,fn));
                From_LLT_To_M_V2(L,LT);
                break;
            case 6:
                fr.println("find the matrix by L, D and L' multiplication:");
                D = Create_Diagonal_Matrix(n);
                fr.println("D = ");
                fr.println(Display_Status_Matrix(D,fn));
                LT = Transpose(L);
                fr.println("L' = ");
                fr.println(Display_Status_Matrix(LT,fn));
                From_LDLT_To_M_V2(L,D,LT);
                break;
            default:
                throw new Exception("you entered an invalid number");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = L.length, n = L[0].length;
            LocalDateTime cur = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File file = new File("Results/Receive_Matrices/" + ne + "_" + cur.format(formatter) + ".txt");
            fr = new PrintWriter(new FileWriter(file, true));
            fr.println("receive the M matrix from the L and another matrices (" + m + "*" + n + " size):");
            fr.println(Display_Status_Matrix(L,fn));
            if (m != n) {
                fr.println("this is a matrix which is not a square matrix");
            } else {
                fr.println("L = ");
                fr.println(Display_Status_Matrix(L,fn));
                if (!Is_Lower_Triangular(L)) {
                    fr.println("this is a matrix which is not a lower triangular");
                } else {
                    Receive_Matrix(L);
                }
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid values for the L matrix");
        }
    }
}
