package Features;

import Tools.ShareTools;
import java.util.Scanner;
import java.util.Vector;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class System_Linear_Equations_Extended extends ShareTools {
    private float[][] A;
    private float[][] b;
    private float[][] x;
    private final String fn;
    private PrintWriter fr;

    public System_Linear_Equations_Extended(float[][] nA, float[][] nb, String rep) {
        A = nA;
        b = nb;
        x = null;
        fn = rep;
        fr = null;
    }

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the system Ax = b in the linear equations format
    private void Write_Exercise() {
        int m = A.length, n = A[0].length;
        fr.println(Executive_Order(m,n));
        if (n == 1) {
            if (A[0][0] % 1 == 0) {
                if (A[0][0] == 1) {
                    if (b[0][0] % 1 == 0) {
                        fr.println("x = " + (int) b[0][0]);
                    } else {
                        fr.println("x = " + b[0][0]);
                    }
                } else if (A[0][0] == -1) {
                    if (b[0][0] % 1 == 0) {
                        fr.println("-x = " + (int) b[0][0]);
                    } else {
                        fr.println("-x = " + b[0][0]);
                    }
                } else {
                    if (b[0][0] % 1 == 0) {
                        fr.println((int) A[0][0] + "x = " + (int) b[0][0]);
                    } else {
                        fr.println((int) A[0][0] + "x = " + b[0][0]);
                    }
                }
            } else {
                if (b[0][0] % 1 == 0)
                    fr.println(A[0][0] + "x = " + (int) b[0][0]);
                else
                    fr.println(A[0][0] + "x = " + b[0][0]);
            }
        } else {
            for (int i = 0; i < m; i++) {
                fr.print("eq" + (i + 1) + ": ");
                for (int j = 0; j < n; j++) {
                    if (A[i][j] > 0) {
                        fr.print("+ ");
                        if (Math.abs(A[i][j]) == 1) {
                            fr.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            fr.print((int) A[i][j] + "*x" + (j + 1) + " ");
                        } else {
                            fr.print(A[i][j] + "*x" + (j + 1) + " ");
                        }
                    } else if (A[i][j] < 0) {
                        fr.print("- ");
                        if (Math.abs(A[i][j]) == 1) {
                            fr.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            fr.print((int) Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        } else {
                            fr.print(Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        }
                    } else {
                        fr.print(" ".repeat(7));
                    }
                }
                if (b[i][0] % 1 == 0) {
                    fr.println(" = " + (int) b[i][0]);
                } else {
                    fr.println(" = " + b[i][0]);
                }
            }
        }
        fr.println();
    }

    // display current status of the system Ax = b each time of iteration on an element
    private void Write_Status_System() {
        int m = A.length, n = A[0].length, k = b[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(A[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(A[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(A[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(A[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.print(" | ");
            for (int j = 0; j < k; j++) {
                if ((Math.round(b[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(b[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(b[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(b[i][j]));
                } if (j != k - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
    }

    // determine what kind of matrix
    private String Which_Type_Triangular(float[][] A, boolean flag) {
        if (Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            return "A is already parallel triangular so now will be change directly to I:";
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A) && flag) {
            return "A is already upper triangular so now we'll go directly to the lower ranking:";
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A) && !flag) {
            return "A is already lower triangular so now we'll go directly to the upper ranking:";
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A) && flag) {
            return "transform L matrix to I by an upper ranking:";
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A) && !flag) {
            return "transform U matrix to I by a lower ranking:";
        } else if (flag) {
            return "transform A matrix to U by an upper ranking:";
        } else {
            return "transform A matrix to L by a lower ranking:";
        }
    }

    // show the resulting solution as a vector representation
    private void Write_Solution() {
        int m = x.length, n = x[0].length;
        String s = "", st = "";
        if (Is_Zero_Col(x,0) && !Is_Zero_Col(x,1)) { // λu
            fr.println("the solution is an infinite set of vectors in R" + m + " space which are linearly dependents in the vector space:");
            fr.print("x = ");
            st += " when ";
            for (int t = 1; t < n && !Is_Zero_Col(x,t); t++) {
                s = (Is_Zero_Col(x,2)) ? s + "λ*" : s + "λ" + t + "*";
                if (n == 2) {
                    st += "λ it's a free scalar";
                } else if (t == n - 1) {
                    st += "λ" + t + " it's a free scalars";
                } else {
                    st += "λ" + t + ",";
                }
                s += Display_Vector(t);
                if (!Is_Zero_Col(x,t + 1)) {
                    s += " + ";
                }
            }
            st += " that belongs to the R set";
        } else if (!Is_Zero_Col(x,0) && !Is_Zero_Col(x,1)) { // x0 + λu
            fr.println("the solution is an infinite set of vectors in R" + m + " space which are linearly dependents in the vector space:");
            fr.print("x = ");
            s += Display_Vector(0);
            st += " when ";
            for (int t = 1; t < m; t++) {
                if (!Is_Zero_Col(x,t)) {
                    s = (Is_Zero_Col(x,2)) ? s + " + λ*" : s + " + λ" + t + "*";
                    if (n == 2) {
                        st += "λ it's a free scalar";
                    } else if (t == n - 1) {
                        st += "λ" + t + " it's a free scalars";
                    } else {
                        st += "λ" + t + ",";
                    }
                    s += Display_Vector(t);
                }
            }
            st += " that belongs to the R set";
        } else { // x0
            fr.println("exist a single solution in R" + m + " space for the system which is:");
            fr.print("x = ");
            s += Display_Vector(0);
        }
        fr.println(s + st);
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    private void User_Menu_System() {
        System.out.println("choose the number of method to solution:");
        System.out.println("1. upper --> lower ranking method");
        System.out.println("2. lower --> upper ranking method");
        System.out.println("3. parallel ranking method");
        System.out.println("4. elementary matrices method");
    }

    /////////////////////////////////////////////// Display Format ///////////////////////////////////////////////
    // display the coordinates as a vector representation
    private String Display_Vector(int c) {
        int m = x.length;
        String s = "(";
        for (int i = 0; i < m; i++) {
            if ((Math.round(x[i][c] * 1000.0) / 1000.0) % 1 == 0) {
                s += (int) (Math.round(x[i][c] * 1000.0) / 1000.0);
            } else if (fn.equals("decimal")) {
                s += Math.round(x[i][c] * 1000.0) / 1000.0;
            } else if (fn.equals("rational")) {
                s += convertDecimalToFraction(x[i][c]);
            } if (i != m - 1) {
                s += " ,";
            }
        }
        s += ")";
        return s;
    }

    ////////////////////////////////////////////////// Quantity //////////////////////////////////////////////////
    // invoke name of file by quantity of equations and unknowns
    private String Name_File(int m, int n) {
        if (m == 1 && n == 1) {
            return "Linear_Equation_(1_Equation)(1_Unknown)";
        } else if (m == 1 && n > 1) {
            return "Linear_Equation_(1_Equation)(" + n + "_Unknowns)";
        } else {
            return "System_Linear_Equations_(" + m + "_Equations)(" + n + "_Unknowns)";
        }
    }

    // invoke executive order by quantity of equations and unknowns
    private String Executive_Order(int m, int n) {
        if (m == 1 && n == 1) {
            return "solve the next equation in R1 space:";
        } else if (m == 1 && n > 1) {
            return "solve the next equation in R" + n + " space:";
        } else {
            return "solve the next system in R" + n + " space:";
        }
    }

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if the specific row in the matrix is a unit vector
    private boolean Is_Exist_Vector(int r) {
        int n = A[0].length;
        float[] v = Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Index_for_Unit_Vector(v);
        for (int i = 0; i < n; i++) {
            if (i != r && Is_Unit_Vector(A,r) && Is_Unit_Vector(A,i)) {
                int c2 = Index_for_Unit_Vector(Row_from_Matrix(A,i));
                if (c1 == c2 && c1 != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if exists two vectors in the matrix which are linearly dependent
    private boolean Is_Linear_Dependent_Rows() {
        int m = A.length, n = A[0].length;
        for (int r1 = 0; r1 < m - 1; r1++) {
            for (int r2 = r1 + 1; r2 < m; r2++) {
                if (!Is_Unit_Vector(A,r1) && !Is_Unit_Vector(A,r2)) {
                    Vector<Float> R  = new Vector<Float>();
                    for (int j = 0; j < n; j++) {
                        if (A[r1][j] != 0 || A[r2][j] != 0) {
                            R.add(A[r1][j] / A[r2][j]);
                            if (r2 == r1 + 1 && Is_Equals_Values(R) && R.size() >= 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // check if exists two vectors in the matrix which are linearly independent
    private boolean Is_Linear_Independent_System() {
        int m = A.length, n = A[0].length;
        for (int r = 0; r < m; r++) {
            if (Is_Zero_Row(A,r) && b[r][0] != 0) {
                return true;
            }
        }
        for (int r1 = 0; r1 < m - 1; r1++) {
            for (int r2 = r1 + 1; r2 < m; r2++) {
                Vector<Float> R  = new Vector<Float>();
                for (int j = 0; j < n; j++) {
                    if (A[r1][j] != 0 || A[r2][j] != 0) {
                        R.add(A[r1][j] / A[r2][j]);
                    }
                }
                if (Is_Equals_Values(R) && R.size() > 1 && (b[r1][0] != 0 || b[r2][0] != 0) && (b[r1][0] / b[r2][0] != R.get(0))) {
                    return true;
                }
            }
        }
        return false;
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // find two columns in the matrix which are linearly dependent
    private int Linear_Dependent_Columns() {
        int m = A.length, n = A[0].length;
        for (int c1 = 0; c1 < n - 1; c1++) {
            for (int c2 = c1 + 1; c2 < n; c2++) {
                Vector<Float> C  = new Vector<Float>();
                for (int r = 0; r < m; r++) {
                    if (A[r][c1] != 0 || A[r][c2] != 0) {
                        C.add(A[r][c1] / A[r][c2]);
                    }
                }
                if (Is_Equals_Values(C) && C.size() > 1) {
                    return c1;
                }
            }
        }
        return -1;
    }

    // get the specific column index of the row requested from the matrix which are indicating a unit vector
    private int Index_Row_from_Matrix(int r) {
        int n = A[0].length;
        float[] v = Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Index_for_Unit_Vector(v);
        for (int i = 0; i < n; i++) {
            int c2 = Index_for_Unit_Vector(Row_from_Matrix(A,i));
            if (c1 == c2 && c1 != -1) {
                return i;
            }
        }
        return -1;
    }

    // get the index from the vector that is indicating a unit vector
    private int Index_for_Unit_Vector(float[] v) {
        int n = v.length;
        for (int c = 0; c < n; c++) {
            if (v[c] != 0) {
                return c;
            }
        }
        return -1;
    }

    // get the index starting from the specific column in the matrix which are him value not equal to 0 with or in the negative direction
    private int Index_UnZero_Value(int k, boolean flag) {
        int n = A.length;
        if (flag) {
            for (int i = k + 1; i < n + k; i++) {
                if (A[i % n][k] != 0) {
                    return i % n;
                }
            }
        } else {
            for (int i = n + k - 1; i > k - 1; i--) {
                if (A[i % n][k] != 0) {
                    return i % n;
                }
            }
        }
        return -1;
    }

    // get the index column from the specific row in the matrix which are indicating intersection between zero rows and zero columns
    private int Intersection_Zero_Row_Col(int r) {
        int n = A[0].length;
        for (int c = 0; c < n; c++) {
            if (Is_Zero_Col(A,c) && Is_Zero_Row(A,r)) {
                return c;
            }
        }
        return -1;
    }

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // replace between two rows in a system Ax = b
    private void Retreat_Rows_System(int r1, int r2) {
        int n = A[0].length, m = b[0].length;
        for (int j = 0; j < n; j++) {
            float k = A[r1][j];
            A[r1][j] = A[r2][j];
            A[r2][j] = k;
        }
        for (int j = 0; j < m; j++) {
            if (!Is_Zero_Col(b,j)) {
                float k = b[r1][j];
                b[r1][j] = b[r2][j];
                b[r2][j] = k;
            }
        }
    }

    ///////////////////////////////////////////// Change Dimensions //////////////////////////////////////////////
    // add more zero rows in the matrix by needed
    private float[][] Increase_Rows_in_Matrix(int m) {
        int n = A[0].length;
        float[][] nA = new float[n][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nA[i][j] = A[i][j];
            }
        }
        return nA;
    }

    // add more zero rows in the vector by needed
    private float[][] Increase_Rows_in_Vector(int m) {
        int n = b.length;
        float[][] nb = new float[m][1];
        for (int i = 0; i < n; i++) {
            nb[i][0] = (i < m) ? b[i][0] : 0;
        }
        return nb;
    }

    // add a new column to the vector
    private float[][] Increase_Cols_in_Vector() {
        int m = b.length, n = b[0].length;
        float[][] nb = new float[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nb[i][j] = b[i][j];
            }
        }
        return nb;
    }

    // remove the last column from the vector
    private float[][] Decrease_Col_in_Vector(float c) {
        int m = b.length;
        float[][] nb = new float[m][1];
        for (int i = 0; i < m; i++) {
            nb[i][0] = b[i][0] + c * b[i][1];
        }
        return nb;
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

    // show elementary actions for sum between rows in the system
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
                    if (k % 1 == 0) {
                        fr.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - " + k + "*R" + c);
                    }
                } else if (fn.equals("rational")) {
                    String v = convertDecimalToFraction(k);
                    if (!v.equals("1")) {
                        fr.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - R" + c);
                    }
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        fr.println("R" + r + " --> R" + r + " + R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    if (k % 1 == 0) {
                        fr.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c);
                    }
                } else if (fn.equals("rational")) {
                    String v = convertDecimalToFraction(-k);
                    if (!v.equals("-1")) {
                        fr.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + R" + c);
                    }
                }
            }
            fr.println();
        }
    }

    // show elementary actions for multiplication of a row in the system
    private void Mul_Elementary_Action(float k, int j) {
        if (k != 1) {
            int r = j + 1;
            if (k % 1 == 0) {
                if (k == -1) {
                    fr.println("R" + r + " --> - R" + r);
                } else {
                    fr.println("R" + r + " --> " + (int) k + "*R" + r);
                }
            } else if (fn.equals("decimal")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k % 1 == 0) {
                    fr.println("R" + r + " --> " + (int) k + "*R" + r);
                } else {
                    fr.println("R" + r + " --> " + k + "*R" + r);
                }
            } else if (fn.equals("rational")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k == -1) {
                    fr.println("R" + r + " --> - R" + r);
                } else {
                    fr.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r);
                }
            }
            fr.println();
        }
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // solve system of linear equations Ax = b by an upper ranking and then a lower ranking
    private float[][] Upper_Ranking_Method() {
        fr.println(Which_Type_Triangular(A,true));
        int n = A.length;
        for (int i = 0; i < n; i++) {
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            define_free_variable(i);
            if (A[i][i] == 0) {
                int r = Index_UnZero_Value(i,true);
                int l = Index_Row_from_Matrix(i);
                if (Is_Exist_Vector(i) && l < i) {
                    r = Index_UnZero_Value(i,false);
                } if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(i,r);
                    Write_Status_System();
                }
            }
            int t = b[0].length - 1;
            for (int j = i + 1; j < n; j++) {
                if (A[i][i] != 0 && A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = 0; k < n; k++) {
                        A[j][k] -= A[i][k] * c;
                        if (k <= t) {
                            b[j][k] -= b[i][k] * c;
                        }
                    }
                    A[j][i] = 0;
                    Write_Status_System();
                }
                A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
                if (Is_Unit_Vector(A,j)) {
                    int d = Index_for_Unit_Vector(Row_from_Matrix(A,j));
                    if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                        float c = 1 / A[j][d];
                        Mul_Elementary_Action(c,j);
                        for (int k = 0; k <= t; k++) {
                            b[j][k] /= A[j][d];
                        }
                        A[j][d] = 1;
                        Write_Status_System();
                    }
                }
            }
            if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A)) {
                fr.print("and now ");
                return Lower_Ranking_Method();
            }
        }
        if (!Is_Unit_Matrix(A)) {
            fr.println("still not yet received an unit matrix");
            return Lower_Ranking_Method();
        }
        return b;
    }

    // solve system of linear equations Ax = b by a lower ranking and then an upper ranking
    private float[][] Lower_Ranking_Method() {
        fr.println(Which_Type_Triangular(A,false));
        int n = A.length;
        for (int i = n - 1; i >= 0; i--) {
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            define_free_variable(i);
            if (A[i][i] == 0) {
                int r = Index_UnZero_Value(i,false);
                int l = Index_Row_from_Matrix(i);
                if (Is_Exist_Vector(i) && l < i) {
                    r = Index_UnZero_Value(i,true);
                } if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(i,r);
                    Write_Status_System();
                }
            }
            int t = b[0].length - 1;
            for (int j = i - 1; j >= 0; j--) {
                if (A[i][i] != 0 && A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = n - 1; k >= 0; k--) {
                        A[j][k] -= A[i][k] * c;
                        if (k <= t) {
                            b[j][k] -= b[i][k] * c;
                        }
                    }
                    A[j][i] = 0;
                    Write_Status_System();
                }
                A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
                if (Is_Unit_Vector(A,j)) {
                    int d = Index_for_Unit_Vector(Row_from_Matrix(A,j));
                    if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                        float c = 1 / A[j][d];
                        Mul_Elementary_Action(c,j);
                        for (int k = 0; k <= t; k++) {
                            b[j][k] /= A[j][d];
                        }
                        A[j][d] = 1;
                        Write_Status_System();
                    }
                }
            }
            if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
                fr.print("and now ");
                return Upper_Ranking_Method();
            }
        }
        if (!Is_Unit_Matrix(A)) {
            fr.println("still not yet received an unit matrix");
            return Upper_Ranking_Method();
        }
        return b;
    }

    // solve system of linear equations Ax = b by parallel ranking
    private float[][] Parallel_Ranking_Method() {
        fr.println("transform A matrix to I by a parallel ranking:");
        int n = A.length;
        while (!Is_Unit_Matrix(A)) {
            for (int i = 0; i < n; i++) {
                A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
                define_free_variable(i);
                if (A[i][i] == 0) {
                    int r = Index_UnZero_Value(i,true);
                    if (r >= 0 && r < n && r != i) {
                        A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                        Retreat_Elementary_Action(i,r);
                        Retreat_Rows_System(i,r);
                        Write_Status_System();
                    }
                }
                int t = b[0].length - 1;
                for (int j = 0; j < n; j++) {
                    if (i != j && A[i][i] != 0 && A[j][i] != 0) {
                        float c = A[j][i] / A[i][i];
                        Sum_Elementary_Action(c,j,i);
                        for (int k = 0; k < n; k++) {
                            A[j][k] -= A[i][k] * c;
                            if (k <= t) {
                                b[j][k] -= b[i][k] * c;
                            }
                        }
                        A[j][i] = 0;
                        Write_Status_System();
                    }
                    A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
                    if (Is_Unit_Vector(A,j)) {
                        int d = Index_for_Unit_Vector(Row_from_Matrix(A,j));
                        if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                            float c = 1 / A[j][d];
                            Mul_Elementary_Action(c,j);
                            for (int k = 0; k <= t; k++) {
                                b[j][k] /= A[j][d];
                            }
                            A[j][d] = 1;
                            Write_Status_System();
                        }
                    }
                }
            }
        }
        return b;
    }

    // solve system of linear equations Ax = b by parallel elementary matrices
    private float[][] Elementary_Matrices_Method() {
        fr.println("transform A matrix to I by an elementary matrices:");
        int n = A.length, i = 0, j = 0;
        float[][] E = Unit_Matrix(n);
        while (!Is_Unit_Matrix(A)) {
            define_free_variable(i);
            if (A[i][i] == 0) {
                int r = Index_UnZero_Value(i,true);
                if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_Matrix(E,i,r);
                    A = Mul_Mats(E,A);
                    b = Mul_Mats(E,b);
                    E = Unit_Matrix(n);
                    Write_Status_System();
                }
            } if (i != j && A[i][i] != 0 && A[j][i] != 0) {
                E[j][i] -= (A[j][i] / A[i][i]);
                Sum_Elementary_Action(-E[j][i],j,i);
                A = Mul_Mats(E,A);
                b = Mul_Mats(E,b);
                E = Unit_Matrix(n);
                A[j][i] = 0;
                Write_Status_System();
            }
            A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
            if (Is_Unit_Vector(A,j)) {
                int d = Index_for_Unit_Vector(Row_from_Matrix(A,j));
                if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                    E[j][j] = 1 / A[j][d];
                    Mul_Elementary_Action(E[j][j],j);
                    A = Mul_Mats(E,A);
                    b = Mul_Mats(E,b);
                    E = Unit_Matrix(n);
                    A[j][d] = 1;
                    Write_Status_System();
                }
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return b;
    }

    ////////////////////////////////////////////// Free Variable ///////////////////////////////////////////////
    // fill rows to full square system
    private void fill_square_system() {
        int m = A.length, n = A[0].length;
        if (m < n) {
            if (n - m == 1) {
                fr.println("added one more row of zeros in order to get a square completion");
            } else {
                fr.println("added " + (n - m) + " more rows of zeros in order to get a square completion");
            }
            A = Increase_Rows_in_Matrix(m);
        }
        b = Increase_Rows_in_Vector(n);
    }

    // define free variable in row "i" where exist zero rows in the matrix
    private void define_free_variable(int i) {
        if (Is_Zero_Row(A,i) && !Is_Linear_Dependent_Rows()) {
            int n = A.length, t = b[0].length - 1;
            if (t == 1 && !Is_Zero_Row(b,i)) {
                float c = - b[i][0] / b[i][1];
                if (c % 1 == 0) {
                    fr.println("exist certainly const for scalar (λ = " + (int) c + ") so we simplify the vector b");
                } else {
                    fr.println("exist certainly const for scalar (λ = " + c + ") so we simplify the vector b");
                }
                b = Decrease_Col_in_Vector(c);
                t--;
                Write_Status_System();
            }
            int d = n, d1 = Intersection_Zero_Row_Col(i), d2 = Linear_Dependent_Columns();
            if (d1 != -1) {
                d = d1;
            } else if (d2 != -1) {
                d = d2;
            } else if (!Is_Exist_Vector(i)) {
                d = i;
            } if (d < n) {
                A[i][d] = 1;
                fr.println("define a new column in the vector b when x" + (d + 1) + " is a free variable in R" + n + " space:");
                b = Increase_Cols_in_Vector();
                b[i][++t] = 1;
                Write_Status_System();
            }
        }
    }

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose action in order to solve a system Ax = b
    private void Many_Variables_System() throws Exception {
        if (Is_Linear_Independent_System()) {
            fr.println("does not an exists solutions for this system");
        } else {
            fill_square_system();
            Scanner sc = new Scanner(System.in);
            User_Menu_System();
            int op = sc.nextInt();
            Write_Status_System();
            switch (op) {
                case 1:
                    fr.println("implement the solution by upper --> lower ranking method:");
                    x = Upper_Ranking_Method();
                    break;
                case 2:
                    fr.println("implement the solution by lower --> upper ranking method:");
                    x = Lower_Ranking_Method();
                    break;
                case 3:
                    fr.println("implement the solution by parallel ranking method:");
                    x = Parallel_Ranking_Method();
                    break;
                case 4:
                    fr.println("implement the solution by elementary ranking method:");
                    x = Elementary_Matrices_Method();
                    break;
                default:
                    throw new Exception("you entered an invalid value for an option number to solution");
            }
            Write_Solution();
        }
    }

    ///////////////////////////////////////////////// R1 Space ////////////////////////////////////////////////
    private void Single_Variable_Equation() {
        if (A[0][0] == 0 && b[0][0] == 0) {
            fr.println("exists an infinite number of solutions in R1 space for the equation that is:");
            fr.println("x = λ when λ it's a free value that belongs to the R set");
        } else if (A[0][0] == 0 && b[0][0] != 0) {
            fr.println("does not an exists solutions for this equation");
        } else {
            fr.println("exist a single solution in R1 space for the equation which is:");
            float c = b[0][0] / A[0][0];
            if (c % 1 == 0) {
                fr.println("x = " + (int) c);
            } else if (fn.equals("decimal")) {
                fr.println("x = " + c);
            } else if (fn.equals("rational")) {
                fr.println("x = " + convertDecimalToFraction(c));
            }
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Check_User_Input() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = A.length, n = A[0].length, k = b.length;
            LocalDateTime cur = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File file = new File("Results/System_Linear_Equations/" + Name_File(m,n) + " " + cur.format(formatter) + ".txt");
            fr = new PrintWriter(new FileWriter(file, true));
            Write_Exercise();
            if (m <= n && m == k) {
                if (n > 1) { // R2 space or higher
                    Many_Variables_System();
                } else { // R1 space
                    Single_Variable_Equation();
                }
            } else {
                fr.println("this is an input does not meet the conditions for system of linear equations");
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
