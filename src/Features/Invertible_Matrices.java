package Features;

import Tools.ShareTools;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Invertible_Matrices extends ShareTools {
    private float[][] M;
    private float[][] InvM;
    private final String fn;
    private final String ne;
    private PrintWriter fr;

    public Invertible_Matrices(float[][] nM, String repr, String file) {
        this.M = nM;
        this.InvM = null;
        this.fn = repr;
        this.ne = file.split("\\.")[0];
        this.fr = null;
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

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    private int Index_UnZero_Value(float[][] M, int k) {
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
    private void Retreat_Rows_Matrices(float[][] M, float[][] InvM, int r1, int r2) {
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
    private void Mul_Mats_Matrices(float[][] E, float[][] M, float[][] InvM) {
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

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
    // show elementary actions for replace between rows in the matrices
    private void Retreat_Elementary_Action(int i, int j) {
        int r1 = i + 1, r2 = j + 1;
        if (r1 <= r2) {
            fr.println("R" + r1 + " <--> R" + r2);
        } else {
            fr.println("R" + r2 + " <--> R" + r1);
        }
        fr.println();
    }

    // show elementary actions for sum between rows in the matrices
    private void Sum_Elementary_Action(float k, int j, int i) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
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
                        fr.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
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
            k = (float) (Math.round(k * 1000.0) / 1000.0);
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

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    private float[][] Invertible_Direct(float[][] M) {
        int n = M.length;
        float det = Determinant(M);
        float[][] InvM = new float[n][n];
        float[][] adj = Adjoint(M);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                InvM[i][j] = (1 / det) * adj[i][j];
            }
        }
        return InvM;
    }

    // invert the M matrix by parallel ranking
    private float[][] Ranking_Rows_Method(float[][] M) {
        fr.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        float[][] InvM = this.InvM;
        while (!Is_Unit_Matrix(M)) {
            for (int i = 0; i < n; i++) {
                if (M[i][i] == 0) {
                    int r = Index_UnZero_Value(M,i);
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_Matrices(M,InvM,i,r);
                    Write_Status_Matrices(M,InvM);
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && M[j][i] != 0) {
                        float c = M[j][i] / M[i][i];
                        Sum_Elementary_Action(c,j,i);
                        for (int k = 0; k < n; k++) {
                            M[j][k] -= M[i][k] * c;
                            InvM[j][k] -= InvM[i][k] * c;
                        }
                        M[j][i] = 0;
                        Write_Status_Matrices(M,InvM);
                    }
                    if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                        float c = 1 / M[j][j];
                        Mul_Elementary_Action(c,j);
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
    private float[][] Elementary_Matrices_Method(float[][] M) {
        fr.println("transform M matrix to I by an elementary matrices:");
        int n = M.length, i = 0, j = 0;
        float[][] InvM = this.InvM;
        float[][] E = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_Matrix(E,i,r);
                Mul_Mats_Matrices(E,M,InvM);
                M = this.M; InvM = this.InvM; E = Unit_Matrix(n);
                Write_Status_Matrices(M,InvM);
            }
            if (i != j && M[j][i] != 0) {
                E[j][i] -= (M[j][i] / M[i][i]);
                Sum_Elementary_Action(-E[j][i],j,i);
                Mul_Mats_Matrices(E,M,InvM);
                M = this.M; InvM = this.InvM; E = Unit_Matrix(n);
                M[j][i] = 0;
                Write_Status_Matrices(M,InvM);
            }
            if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                E[j][j] = 1 / M[j][j];
                Mul_Elementary_Action(E[j][j],j);
                Mul_Mats_Matrices(E,M,InvM);
                M = this.M; InvM = this.InvM; E = Unit_Matrix(n);
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

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose option in order to correctness check for M matrix
    private void Invert_Matrix(float[][] M) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_Invertible();
        int op = sc.nextInt();
        InvM = Unit_Matrix(M.length);
        switch (op) {
            case 1:
                fr.println("implement the solution by formula: Inv(M) = (1/|M|) * Adj(M)");
                InvM = Invertible_Direct(M);
                break;
            case 2:
                Write_Status_Matrices(M,InvM);
                fr.println("implement the solution by ranking rows method:");
                InvM = Ranking_Rows_Method(M);
                break;
            case 3:
                Write_Status_Matrices(M,InvM);
                fr.println("implement the solution by elementary matrices method:");
                InvM = Elementary_Matrices_Method(M);
                break;
            default:
                throw new Exception("you entered an invalid number");
        }
        fr.println("the invertible of this matrix is:");
        fr.println(Display_Status_Matrix(InvM,fn));
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = M.length, n = M[0].length;
            LocalDateTime cur = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File file = new File("Results/Invertible_Matrices/" + ne + "_" + cur.format(formatter) + ".txt");
            fr = new PrintWriter(new FileWriter(file, true));
            fr.println("invert the next matrix (" + m + "*" + n + " size):");
            fr.println(Display_Status_Matrix(M,fn));
            if (m != n) {
                fr.println("this is a matrix which is not a square matrix");
            } else {
                float det = Determinant(M);
                if (det == 0) {
                    fr.println("this is a singular matrix");
                } else {
                    Invert_Matrix(M);
                }
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
