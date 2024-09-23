package Features.Invertible_Matrices;

import Features.ShareTools;
import java.util.Scanner;
import java.io.PrintWriter;

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
    private void Write_Status_Matrices(float[][] M, float[][] InvM) {
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

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    private float[][] Invertible_Direct(float[][] M) {
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

    // invert the M matrix by parallel ranking
    private float[][] Ranking_Rows_Action(float[][] M) {
        fr.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        float[][] InvM = this.InvM;
        while (!Is_Unit_Matrix(M)) {
            for (int i = 0; i < n; i++) {
                if (M[i][i] == 0 && !Is_Zero_Col(M,i)) {
                    int r = Index_UnZero_Value(M,i);
                    Retreat_Elementary_Description(i,r);
                    Retreat_Rows_Matrix(M,i,r);
                    Retreat_Rows_Matrix(InvM,i,r);
                    Write_Status_Matrices(M,InvM);
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && M[j][i] != 0) {
                        float c = M[j][i] / M[i][i];
                        Sum_Elementary_Description(c,j,i);
                        for (int k = 0; k < n; k++) {
                            M[j][k] -= M[i][k] * c;
                            InvM[j][k] -= InvM[i][k] * c;
                        }
                        M[j][i] = 0;
                        Write_Status_Matrices(M,InvM);
                    }
                    if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                        fr.println("this is a singular matrix");
                        return null;
                    } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                        float c = 1 / M[j][j];
                        Mul_Elementary_Description(c,j);
                        for (int k = 0; k < n; k++) {
                            InvM[j][k] /= M[j][j];
                        }
                        M[j][j] = 1;
                        Write_Status_Matrices(M,InvM);
                    }
                }
            }
        }
        return InvM;
    }

    // invert the M matrix by parallel elementary matrices
    private float[][] Elementary_Matrices_Action(float[][] M) {
        fr.println("transform M matrix to I by an elementary matrices:");
        int n = M.length, i = 0, j = 0;
        float[][] InvM = this.InvM;
        float[][] E = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            if (M[i][i] == 0 && !Is_Zero_Col(M,i)) {
                int r = Index_UnZero_Value(M,i);
                Retreat_Elementary_Description(i,r);
                Retreat_Rows_Matrix(E,i,r);
                M = Mul_Mats(E,M); InvM = Mul_Mats(E,InvM); E = Unit_Matrix(n);
                Write_Status_Matrices(M,InvM);
            }
            if (i != j && M[j][i] != 0) {
                E[j][i] -= (M[j][i] / M[i][i]);
                Sum_Elementary_Description(-E[j][i],j,i);
                M = Mul_Mats(E,M); InvM = Mul_Mats(E,InvM); E = Unit_Matrix(n);
                M[j][i] = 0;
                Write_Status_Matrices(M,InvM);
            }
            if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                fr.println("this is a singular matrix");
                return null;
            } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                E[j][j] = 1 / M[j][j];
                Mul_Elementary_Description(E[j][j],j);
                M = Mul_Mats(E,M); InvM = Mul_Mats(E,InvM); E = Unit_Matrix(n);
                M[j][j] = 1;
                Write_Status_Matrices(M,InvM);
            }
            if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return InvM;
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
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
                InvM = Ranking_Rows_Action(M);
            }
            case 3 -> {
                Write_Status_Matrices(M,InvM);
                fr.println("implement the solution by elementary matrices method:");
                InvM = Elementary_Matrices_Action(M);
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
