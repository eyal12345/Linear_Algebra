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
        M = nM;
        InvM = Unit_Matrix(M.length);
        fn = repr;
        ne = file.split("\\.")[0];
        fr = null;
    }

    /////////////////////////////////////////////// Write Methods /////////////////////////////////////////////////
    // display current status of the matrices M and InvM each time of iteration on an element
    private void Write_Status_Matrices() {
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
    }

    // determine what kind of matrix
    private String Which_Type_Triangular(float[][] M, boolean flag) {
        if (Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
            return "M is already parallel triangular so now will be change directly to I:";
        } else if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M) && flag) {
            return "M is already upper triangular so now we'll go directly to the lower ranking:";
        } else if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M) && !flag) {
            return "M is already lower triangular so now we'll go directly to the upper ranking:";
        } else if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M) && flag) {
            return "transform L matrix to I by an upper ranking:";
        } else if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M) && !flag) {
            return "transform U matrix to I by a lower ranking:";
        } else if (flag) {
            return "transform M matrix to U by an upper ranking:";
        } else {
            return "transform M matrix to L by a lower ranking:";
        }
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    private static void User_Menu_Invertible() {
        System.out.println("choose number method to solution:");
        System.out.println("1. invert a matrix by upper ranking");
        System.out.println("2. invert a matrix by lower ranking");
        System.out.println("3. invert a matrix by parallel ranking");
        System.out.println("4. invert a matrix by elementary ranking");
        System.out.println("5. invert a matrix by formula: Inv(M) = (1/|M|) * Adj(M)");
    }

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
    // show elementary actions for replace between rows in the system
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
    // invert the M matrix by an upper ranking and then a lower ranking
    private float[][] Upper_Ranking_Method() {
        fr.println(Which_Type_Triangular(M,true));
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i,true);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_Matrices(M,InvM,i,r);
                Write_Status_Matrices();
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
                    Write_Status_Matrices();
                } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Write_Status_Matrices();
                }
            }
            if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M)) {
                fr.print("and now ");
                return Lower_Ranking_Method();
            }
        }
        if (!Is_Unit_Matrix(M)) {
            fr.println("still not yet received an unit matrix");
            return Lower_Ranking_Method();
        }
        return InvM;
    }

    // invert the M matrix by a lower ranking and then an upper ranking
    private float[][] Lower_Ranking_Method() {
        fr.println(Which_Type_Triangular(M,false));
        int n = M.length;
        for (int i = n - 1; i >= 0; i--) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i,false);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_Matrices(M,InvM,i,r);
                Write_Status_Matrices();
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
                    Write_Status_Matrices();
                } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Write_Status_Matrices();
                }
            }
            if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
                fr.print("and now ");
                return Upper_Ranking_Method();
            }
        }
        if (!Is_Unit_Matrix(M)) {
            fr.println("still not yet received an unit matrix");
            return Upper_Ranking_Method();
        }
        return InvM;
    }

    // invert the M matrix by parallel ranking
    private float[][] Parallel_Ranking_Method() {
        fr.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        InvM = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            for (int i = 0; i < n; i++) {
                if (M[i][i] == 0) {
                    int r = Index_UnZero_Value(M,i,true);
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_Matrices(M,InvM,i,r);
                    Write_Status_Matrices();
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
                        Write_Status_Matrices();
                    } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                        float c = 1 / M[j][j];
                        Mul_Elementary_Action(c,j);
                        for (int k = 0; k < n; k++) {
                            InvM[j][k] /= M[j][j];
                        }
                        M[j][j] = 1;
                        Write_Status_Matrices();
                    }
                }
            }
        }
        return InvM;
    }

    // invert the M matrix by parallel elementary matrices
    private float[][] Elementary_Matrices_Method() {
        fr.println("transform M matrix to I by an elementary matrices:");
        int n = M.length, i = 0, j = 0;
        float[][] E = Unit_Matrix(n);
        InvM = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i,true);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_Matrix(E,i,r);
                M = Mul_Mats(E,M);
                InvM = Mul_Mats(E,InvM);
                E = Unit_Matrix(n);
                Write_Status_Matrices();
            } if (i != j && M[j][i] != 0) {
                E[j][i] -= (M[j][i] / M[i][i]);
                Sum_Elementary_Action(-E[j][i],j,i);
                M = Mul_Mats(E,M);
                InvM = Mul_Mats(E,InvM);
                E = Unit_Matrix(n);
                M[j][i] = 0;
                Write_Status_Matrices();
            } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                E[j][j] = 1 / M[j][j];
                Mul_Elementary_Action(E[j][j],j);
                M = Mul_Mats(E,M);
                InvM = Mul_Mats(E,InvM);
                E = Unit_Matrix(n);
                M[j][j] = 1;
                Write_Status_Matrices();
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return InvM;
    }

    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    private float[][] Invertible_Direct() {
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

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose option in order to correctness check for M matrix
    private void Many_Variables_Matrix() throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_Invertible();
        int op = sc.nextInt();
        Write_Status_Matrices();
        switch (op) {
            case 1:
                fr.println("implement the solution by upper ranking method:");
                M = Upper_Ranking_Method();
                break;
            case 2:
                fr.println("implement the solution by lower ranking method:");
                M = Lower_Ranking_Method();
                break;
            case 3:
                fr.println("implement the solution by parallel ranking method:");
                M = Parallel_Ranking_Method();
                break;
            case 4:
                fr.println("implement the solution by elementary ranking method:");
                M = Elementary_Matrices_Method();
                break;
            case 5:
                fr.println("implement the solution by formula: Inv(M) = (1/|M|) * Adj(M)");
                M = Invertible_Direct();
                break;
            default:
                throw new Exception("you entered an invalid number");
        }
        fr.println("the invertible of this matrix is:");
        fr.println(Display_Status_Matrix(M,fn));
    }

    //////////////////////////////////////////////// 1*1 Matrix ///////////////////////////////////////////////
    private void Single_Variable_Matrix() {
        float c = 1 / M[0][0];
        String msg = "the invertible of this matrix is: ";
        if (c % 1 == 0) {
            msg += (int) c;
        } else if (fn.equals("decimal")) {
            msg += c;
        } else if (fn.equals("rational")) {
            msg += convertDecimalToFraction(c);
        }
        fr.println(msg);
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
                } else if (n > 1) { // 2*2 size or higher
                    Many_Variables_Matrix();
                } else { // 1*1 size
                    Single_Variable_Matrix();
                }
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
