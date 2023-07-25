package Features;

import Tools.ShareTools;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Invertible_Matrices extends ShareTools {
    private String fn;
    private PrintWriter fr;

    public Invertible_Matrices(String rep) {
        fn = rep;
        fr = null;
    }

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the matrix M in the matrices format
    private void Write_Exercise(float[][] M) {
        int n = M.length;
        fr.println("invert the next matrix (" + n + "*" + n + " size):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
    }

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
                    fr.print(ShareTools.convertDecimalToFraction(M[i][j]));
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
                    fr.print(ShareTools.convertDecimalToFraction(InvM[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
    }

    // determine what kind of matrix
    private String Which_Type_Triangular(float[][] M, boolean flag) {
        if (ShareTools.Is_Upper_Triangular(M) && ShareTools.Is_Lower_Triangular(M)) {
            return "M is already parallel triangular so now will be change directly to I:";
        } else if (ShareTools.Is_Upper_Triangular(M) && !ShareTools.Is_Lower_Triangular(M) && flag) {
            return "M is already upper triangular so now we'll go directly to the lower ranking:";
        } else if (!ShareTools.Is_Upper_Triangular(M) && ShareTools.Is_Lower_Triangular(M) && !flag) {
            return "M is already lower triangular so now we'll go directly to the upper ranking:";
        } else if (!ShareTools.Is_Upper_Triangular(M) && ShareTools.Is_Lower_Triangular(M) && flag) {
            return "transform L matrix to I by an upper ranking:";
        } else if (ShareTools.Is_Upper_Triangular(M) && !ShareTools.Is_Lower_Triangular(M) && !flag) {
            return "transform U matrix to I by a lower ranking:";
        } else if (flag) {
            return "transform M matrix to U by an upper ranking:";
        } else {
            return "transform M matrix to L by a lower ranking:";
        }
    }

    // show the resulting solution as a matrix representation
    private void Write_Solution(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(ShareTools.convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    private static void User_Menu_System() {
        System.out.println("choose number method to solution:");
        System.out.println("1. invert a matrix by upper ranking");
        System.out.println("2. invert a matrix by lower ranking");
        System.out.println("3. invert a matrix by parallel ranking");
        System.out.println("4. invert a matrix by elementary ranking");
        System.out.println("5. invert a matrix by formula: Inv(M) = (1/|M|) * Adj(M)");
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    private static int Index_UnZero_Value(float[][] M, int k) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[k][i] != 0) {
                return i % n;
            }
        }
        return -1;
    }

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // replace between two rows in a matrices
    private static void Retreat_Rows_Matrices(float[][] M, float[][] invM, int r1, int r2) {
        int n = M.length;
        for (int j = 0; j < n; j++) {
            float t = M[r1][j];
            M[r1][j] = M[r2][j];
            M[r2][j] = t;
            float inv_t = invM[r1][j];
            invM[r1][j] = invM[r2][j];
            invM[r2][j] = inv_t;
        }
    }

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
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
                    fr.println("R" + r + " --> R" + r + " - " + ShareTools.convertDecimalToFraction(k) + "*R" + c);
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
                    fr.println("R" + r + " --> R" + r + " + " + ShareTools.convertDecimalToFraction(-k) + "*R" + c);
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
                fr.println("R" + r + " --> " + ShareTools.convertDecimalToFraction(k) + "*R" + r);
            }
            fr.println();
        }
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // invert the M matrix by an upper ranking and then a lower ranking
    private float[][] Upper_Ranking_Method(float[][] M, float[][] InvM) {
        fr.println(Which_Type_Triangular(M,true));
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i);
                fr.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrices(M,InvM,i,r);
                Write_Status_Matrices(M,InvM);
            }
            for (int j = i + 1; j < n; j++) {
                if (M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Write_Status_Matrices(M,InvM);
                } if (ShareTools.Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Write_Status_Matrices(M,InvM);
                }
            }
            if (ShareTools.Is_Upper_Triangular(M) && !ShareTools.Is_Lower_Triangular(M)) {
                fr.print("and now ");
                return Lower_Ranking_Method(M,InvM);
            }
        }
        if (!ShareTools.Is_Unit_Matrix(M)) {
            fr.println("still not yet received an unit matrix");
            return Lower_Ranking_Method(M,InvM);
        }
        return InvM;
    }

    // invert the M matrix by a lower ranking and then an upper ranking
    private float[][] Lower_Ranking_Method(float[][] M, float[][] InvM) {
        fr.println(Which_Type_Triangular(M,false));
        int n = M.length;
        for (int i = n - 1; i >= 0; i--) {
            if (M[i][i] == 0) {
                int r = n - 1 - Index_UnZero_Value(M,i);
                fr.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrices(M,InvM,i,r);
                Write_Status_Matrices(M,InvM);
            }
            for (int j = i - 1; j >= 0; j--) {
                if (M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = n - 1; k >= 0; k--) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Write_Status_Matrices(M,InvM);
                } if (ShareTools.Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Write_Status_Matrices(M,InvM);
                }
            }
            if (!ShareTools.Is_Upper_Triangular(M) && ShareTools.Is_Lower_Triangular(M)) {
                fr.print("and now ");
                return Upper_Ranking_Method(M,InvM);
            }
        }
        if (!ShareTools.Is_Unit_Matrix(M)) {
            fr.println("still not yet received an unit matrix");
            return Upper_Ranking_Method(M,InvM);
        }
        return InvM;
    }

    // invert the M matrix by parallel ranking
    private float[][] Parallel_Ranking_Method(float[][] M) {
        fr.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        float[][] InvM = ShareTools.Unit_Matrix(n);
        while (!ShareTools.Is_Unit_Matrix(M)) {
            for (int i = 0; i < n; i++) {
                if (M[i][i] == 0) {
                    int r = Index_UnZero_Value(M,i);
                    fr.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
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
                    } if (ShareTools.Is_Unit_Vector(M,j) && M[j][j] != 1) {
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
        float[][] E = ShareTools.Unit_Matrix(n);
        float[][] InvM = ShareTools.Unit_Matrix(n);
        while (!ShareTools.Is_Unit_Matrix(M)) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i);
                fr.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                ShareTools.Retreat_Rows_Matrix(E,i,r);
                M = ShareTools.Mul_Mats(E,M);
                InvM = ShareTools.Mul_Mats(E,InvM);
                E = ShareTools.Unit_Matrix(n);
                Write_Status_Matrices(M,InvM);
            } if (i != j && M[j][i] != 0) {
                E[j][i] -= (M[j][i] / M[i][i]);
                Sum_Elementary_Action(-E[j][i],j,i);
                M = ShareTools.Mul_Mats(E,M);
                InvM = ShareTools.Mul_Mats(E,InvM);
                E = ShareTools.Unit_Matrix(n);
                M[j][i] = 0;
                Write_Status_Matrices(M,InvM);
            } if (ShareTools.Is_Unit_Vector(M,j) && M[j][j] != 1) {
                E[j][j] = 1 / M[j][j];
                Mul_Elementary_Action(E[j][j],j);
                M = ShareTools.Mul_Mats(E,M);
                InvM = ShareTools.Mul_Mats(E,InvM);
                E = ShareTools.Unit_Matrix(n);
                M[j][j] = 1;
                Write_Status_Matrices(M,InvM);
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return InvM;
    }

    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    private float[][] Invertible_Direct(float[][] M) {
        int n = M.length;
        float det = ShareTools.Determinant(M);
        float[][] InvM = new float[n][n];
        float[][] adj = ShareTools.Adjoint(M);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                InvM[i][j] = (1 / det) * adj[i][j];
            }
        }
        return InvM;
    }

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose option in order to correctness check for M matrix
    private void Invert_Matrix(float[][] M) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_System();
        int op = sc.nextInt();
        Write_Status_Matrices(M, ShareTools.Unit_Matrix(M.length));
        switch (op) {
            case 1:
                fr.println("implement the solution by upper ranking method:");
                M = Upper_Ranking_Method(M, ShareTools.Unit_Matrix(M.length));
                break;
            case 2:
                fr.println("implement the solution by lower ranking method:");
                M = Lower_Ranking_Method(M, ShareTools.Unit_Matrix(M.length));
                break;
            case 3:
                fr.println("implement the solution by parallel ranking method:");
                M = Parallel_Ranking_Method(M);
                break;
            case 4:
                fr.println("implement the solution by elementary ranking method:");
                M = Elementary_Matrices_Method(M);
                break;
            case 5:
                fr.println("implement the solution by formula: Inv(M) = (1/|M|) * Adj(M)");
                M = Invertible_Direct(M);
                break;
            default:
                throw new Exception("you entered an invalid number");
        }
        fr.println("the invertible of this matrix is:");
        Write_Solution(M);
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Check_User_Input(float[][] M) throws Exception {
        int m = M.length, n = M[0].length;
        if (m == n) {
            if (fn.equals("decimal") || fn.equals("rational")) {
                LocalDateTime cur = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
                File file = new File("Results/Invertible_Matrices_(" + n + "x" + n + " size)_" + cur.format(formatter) + ".txt");
                fr = new PrintWriter(new FileWriter(file, true));
                Write_Exercise(M);
                float det = ShareTools.Determinant(M);
                if (det == 0) {
                    fr.println("this is a singular matrix");
                } else if (n > 1) { // 2*2 size or higher
                    Invert_Matrix(M);
                } else { // 1*1 size
                    float c = 1 / M[0][0];
                    String msg = "the invertible of this matrix is: ";
                    if (c % 1 == 0) {
                        msg += (int) c;
                    } else if (fn.equals("decimal")) {
                        msg += c;
                    } else if (fn.equals("rational")) {
                        msg += ShareTools.convertDecimalToFraction(c);
                    }
                    fr.println(msg);
                }
                fr.close();
            } else {
                throw new Exception("you entered invalid value for a representation elementary actions and solution");
            }
        } else {
            throw new Exception("your input does not meet the conditions for invertible matrices");
        }
    }
}
