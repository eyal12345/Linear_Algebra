package Features.Mathematical_Matrices;

import Features.MenuActions;
import Features.ShareTools;
import java.util.Scanner;
import java.io.PrintWriter;

public class Receive_Matrices extends ShareTools implements MenuActions {
    private final float[][] L;

    public Receive_Matrices(float[][] nL, String method, String format, PrintWriter writer) {
        super(method, format, writer);
        this.L = nL;
    }

    /////////////////////////////////////////////// Input Matrices ///////////////////////////////////////////////
    // create diagonal matrix
    private static float[][] Create_Diagonal_Matrix(int n) {
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
    private static float[][] Create_Upper_Matrix(int n) {
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

    // get the LU decomposition by multiplication of L and U (first algorithm)
    private void from_LU_To_M_V1(float[][] L, float[][] U) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(L,U);
            writer.println("M = ");
            writer.println(Display_Status_Matrix(M,format));
        } else {
            writer.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    // get the LL' decomposition by multiplication of L and L' (first algorithm)
    private void from_LLT_To_M_V1(float[][] L, float[][] LT) {
        float[][] M = Mul_Mats(L,LT);
        writer.println("M = ");
        writer.println(Display_Status_Matrix(M,format));
    }

    // get the LDL' decomposition by multiplication of L, D and L' (first algorithm)
    private void from_LDLT_To_M_V1(float[][] L, float[][] D, float[][] LT) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(Mul_Mats(L,D),LT);
            writer.println("M = ");
            writer.println(Display_Status_Matrix(M,format));
        } else {
            writer.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LU decomposition by multiplication of L and U (second algorithm)
    private void from_LU_To_M_V2(float[][] L, float[][] U) {
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
            writer.println("M = ");
            writer.println(Display_Status_Matrix(M,format));
        } else {
            writer.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (second algorithm)
    private void from_LLT_To_M_V2(float[][] L, float[][] LT) {
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
        writer.println("M = ");
        writer.println(Display_Status_Matrix(M,format));
    }

    // get the LDL' decomposition by multiplication of L, D and L' (second algorithm)
    private void from_LDLT_To_M_V2(float[][] L, float[][] D, float[][] LT) {
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
            writer.println("M = ");
            writer.println(Display_Status_Matrix(M,format));
        } else {
            writer.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    @Override
    // get the matrix by a multiplication components
    public void User_Interface(float[][] L) throws Exception {
        int n = L.length;
        float[][] U, D, LT;
        switch (method) {
            case "LU_Multiplication" -> {
                writer.println("find the matrix by L and U multiplication:");
                U = Create_Upper_Matrix(n);
                writer.println("U = ");
                writer.println(Display_Status_Matrix(U,format));
                from_LU_To_M_V1(L,U);
            }
            case "LLT_Multiplication" -> {
                writer.println("find the matrix by L and L' multiplication:");
                LT = Transpose(L);
                writer.println("L' = ");
                writer.println(Display_Status_Matrix(LT,format));
                from_LLT_To_M_V1(L,LT);
            }
            case "LDLT_Multiplication" -> {
                writer.println("find the matrix by L, D and L' multiplication:");
                D = Create_Diagonal_Matrix(n);
                writer.println("D = ");
                writer.println(Display_Status_Matrix(D,format));
                LT = Transpose(L);
                writer.println("L' = ");
                writer.println(Display_Status_Matrix(LT,format));
                from_LDLT_To_M_V1(L,D,LT);
            }
            case "LU_Multiplication_2" -> {
                writer.println("find the matrix by L and U multiplication:");
                U = Create_Upper_Matrix(n);
                writer.println("U = ");
                writer.println(Display_Status_Matrix(U,format));
                from_LU_To_M_V2(L,U);
            }
            case "LLT_Multiplication_2" -> {
                writer.println("find the matrix by L and L' multiplication:");
                LT = Transpose(L);
                writer.println("L' = ");
                writer.println(Display_Status_Matrix(LT,format));
                from_LLT_To_M_V2(L,LT);
            }
            case "LDLT_Multiplication_2" -> {
                writer.println("find the matrix by L, D and L' multiplication:");
                D = Create_Diagonal_Matrix(n);
                writer.println("D = ");
                writer.println(Display_Status_Matrix(D,format));
                LT = Transpose(L);
                writer.println("L' = ");
                writer.println(Display_Status_Matrix(LT,format));
                from_LDLT_To_M_V2(L,D,LT);
            }
            default -> throw new Exception("you entered invalid number");
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    @Override
    // check if user input is valid
    public void Run_Progress() throws Exception {
        if (format.equals("decimal") || format.equals("rational")) {
            int m = L.length, n = L[0].length;
            writer.println("receive the M matrix writerom the L and another matrices (" + m + "*" + n + " size):");
            writer.println(Display_Status_Matrix(L,format));
            if (m != n) {
                writer.println("this is a matrix which is not square matrix");
            } else {
                writer.println("L = ");
                writer.println(Display_Status_Matrix(L,format));
                if (!Is_Lower_Triangular(L)) {
                    writer.println("this is a matrix which is not lower triangular");
                } else {
                    User_Interface(L);
                }
            }
            writer.close();
        } else {
            throw new Exception("you entered invalid values for the L matrix");
        }
    }
}
