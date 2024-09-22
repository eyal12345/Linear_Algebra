package Features.Invertible_Matrices;

import Features.ShareTools;
import Features.Invertible_Matrices.Solution_Methods.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Invertible_Matrices extends ShareTools {
    public float[][] M;
    public float[][] InvM;

    public Invertible_Matrices(float[][] nM, String fn, String ne, PrintWriter fr) {
        super(fn, ne, fr);
        this.M = nM;
        this.InvM = Unit_Matrix(nM.length);
    }

    /////////////////////////////////////////////// Write Methods /////////////////////////////////////////////////
    // display current status of the matrices M and InvM each time of iteration on an element
    public void Write_Status_Matrices(float[][] M, float[][] InvM) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.print(" | ");
            for (int j = 0; j < n; j++) {
                if ((Math.round(InvM[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(InvM[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(InvM[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(InvM[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
        this.M = M; this.InvM = InvM;
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    private static void User_Menu_Invertible() {
        System.out.println("choose number method to solution:");
        System.out.println("1. invert a matrix by formula: Inv(M) = (1/|M|) * Adj(M)");
        System.out.println("2. invert a matrix by ranking rows");
        System.out.println("3. invert a matrix by elementary matrices");
    }

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if exist in the matrix a zeros row
    public boolean Is_Zero_Row(float[][] A, int r) {
        int n = A[0].length;
        for (int j = 0; j < n; j++) {
            if (A[r][j] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if exist in the vector a zeros column
    public boolean Is_Zero_Col(float[][] v, int c) {
        int m = v.length, n = v[0].length;
        for (int i = 0; i < m && c < n; i++) {
            if (v[i][c] != 0) {
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    public int Index_UnZero_Value(float[][] M, int k) {
        int n = M.length;
        for (int i = k + 1; i < n + k; i++) {
            if (M[i % n][k] != 0) {
                return i % n;
            }
        }
        return -1;
    }

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // replace between two rows in the matrices
    public void Retreat_Rows_Matrices(float[][] M, float[][] InvM, int r1, int r2) {
        int n = M[0].length, m = InvM[0].length;
        for (int j = 0; j < n; j++) {
            float k = M[r1][j];
            M[r1][j] = M[r2][j];
            M[r2][j] = k;
        }
        for (int j = 0; j < m; j++) {
            float k = InvM[r1][j];
            InvM[r1][j] = InvM[r2][j];
            InvM[r2][j] = k;
        }
    }

    // multiplication of two matrices in same matrix
    public void Mul_Mats_Matrices(float[][] E, float[][] M, float[][] InvM) {
        int m = M.length, n = InvM[0].length;
        float[][] EM = new float[m][n];
        float[][] EInvM = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    EM[i][j] += E[i][k] * M[k][j];
                    EInvM[i][j] += E[i][k] * InvM[k][j];
                }
            }
        }
        this.M = EM; this.InvM = EInvM;
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    public float[][] Invertible_Direct(float[][] M) {
        int n = M.length;
        float det = Determinant(M);
        if (det == 0) {
            fr.println("this is a singular matrix");
            return null;
        } else {
            float[][] InvM = new float[n][n];
            float[][] adj = Adjoint(M);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    InvM[i][j] = (1 / det) * adj[i][j];
                }
            }
            return InvM;
        }
    }

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose option in order to correctness check for M matrix
    private void Invert_Matrix(float[][] M) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_Invertible();
        int op = sc.nextInt();
        InvM = Unit_Matrix(M.length);
        switch (op) {
            case 1 -> {
                fr.println("implement the solution by formula: Inv(M) = (1/|M|) * Adj(M)");
                InvM = Invertible_Direct(M);
            }
            case 2 -> {
                Write_Status_Matrices(M,InvM);
                fr.println("implement the solution by ranking rows method:");
                Ranking_Rows_Method met = new Ranking_Rows_Method(M,fn,ne,fr);
                InvM = met.Ranking_Rows_Action(M);
            }
            case 3 -> {
                Write_Status_Matrices(M,InvM);
                fr.println("implement the solution by elementary matrices method:");
                Elementary_Matrices_Method met = new Elementary_Matrices_Method(M,fn,ne,fr);
                InvM = met.Elementary_Matrices_Action(M);
            }
            default -> throw new Exception("you entered an invalid number");
        }
        if (InvM != null) {
            fr.println("the invertible of this matrix is:");
            fr.println(Display_Status_Matrix(InvM,fn));
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = M.length, n = M[0].length;
            fr.println("invert the next matrix (" + m + "*" + n + " size):");
            fr.println(Display_Status_Matrix(M,fn));
            if (m != n) {
                fr.println("this is a matrix which is not a square matrix");
            } else {
                Invert_Matrix(M);
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
