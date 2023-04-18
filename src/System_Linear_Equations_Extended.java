import java.util.Scanner;
import java.util.Vector;

public class System_Linear_Equations_Extended {

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the system Ax = b in the linear equations format
    public static void Display_Exercise(float[][] A, float[] b) {
        int m = A.length, n = A[0].length;
        if (n == 1) {
            System.out.println("solve the next equation in R1 space:");
            if (A[0][0] % 1 == 0) {
                if (A[0][0] == 1) {
                    if (b[0] % 1 == 0) {
                        System.out.println("x = " + (int) b[0]);
                    } else {
                        System.out.println("x = " + b[0]);
                    }
                } else if (A[0][0] == -1) {
                    if (b[0] % 1 == 0) {
                        System.out.println("-x = " + (int) b[0]);
                    } else {
                        System.out.println("-x = " + b[0]);
                    }
                } else {
                    if (b[0] % 1 == 0) {
                        System.out.println((int) A[0][0] + "x = " + (int) b[0]);
                    } else {
                        System.out.println((int) A[0][0] + "x = " + b[0]);
                    }
                }
            } else {
                if (b[0] % 1 == 0)
                    System.out.println(A[0][0] + "x = " + (int) b[0]);
                else
                    System.out.println(A[0][0] + "x = " + b[0]);
            }
        } else {
            if (m == 1) {
                System.out.println("solve the next equation in R" + n + " space:");
            } else {
                System.out.println("solve the next system in R" + n + " space:");
            }
            for (int i = 0; i < m; i++) {
                System.out.print("eq" + (i + 1) + ": ");
                for (int j = 0; j < n; j++) {
                    if (A[i][j] > 0) {
                        System.out.print("+ ");
                        if (Math.abs(A[i][j]) == 1) {
                            System.out.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            System.out.print((int) A[i][j] + "*x" + (j + 1) + " ");
                        } else {
                            System.out.print(A[i][j] + "*x" + (j + 1) + " ");
                        }
                    } else if (A[i][j] < 0) {
                        System.out.print("- ");
                        if (Math.abs(A[i][j]) == 1) {
                            System.out.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            System.out.print((int) Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        } else {
                            System.out.print(Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        }
                    } else {
                        System.out.print(" ".repeat(7));
                    }
                }
                if (b[i] % 1 == 0) {
                    System.out.println(" = " + (int) b[i]);
                } else {
                    System.out.println(" = " + b[i]);
                }
            }
        }
        System.out.println();
    }

    // display current status of the system Ax = b each time of iteration on an element
    public static void Print_Status_System(float[][] A, float[][] b, String fn) {
        int m = A.length, n = A[0].length, k = b[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(A[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    System.out.print((int) (Math.round(A[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("d")) {
                    System.out.print(Math.round(A[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("r")) {
                    System.out.print(convertDecimalToFraction(A[i][j]));
                } if (j != n - 1) {
                    System.out.print(" ,");
                }
            }
            System.out.print(" | ");
            for (int j = 0; j < k; j++) {
                if ((Math.round(b[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    System.out.print((int) (Math.round(b[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("d")) {
                    System.out.print(Math.round(b[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("r")) {
                    System.out.print(convertDecimalToFraction(b[i][j]));
                } if (j != k - 1) {
                    System.out.print(" ,");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // show the resulting solution as a vector representation
    public static void Print_Solution(float[][] x, String fn) {
        int m = x.length, n = x[0].length;
        String s = "", st = "";
        if (Is_Zero_Col(x,0) && !Is_Zero_Col(x,1)) { // λu
            System.out.print("the solution is a infinite set of vectors in R" + m + " space which are linearly dependents in the vector space: x = ");
            st += " when ";
            for (int t = 1; t < n && !Is_Zero_Col(x,t); t++) {
                s = (Is_Zero_Col(x,2)) ? s + "s*" : s + "s" + t + "*";
                if (n == 2) {
                    st += "s its a free scalar";
                } else if (t == n - 1) {
                    st += "s" + t + " its a free scalars";
                } else {
                    st += "s" + t + ",";
                }
                s += Display_Vector(x,t,fn);
                if (!Is_Zero_Col(x,t + 1)) {
                    s += " + ";
                }
            }
            st += " that belongs to the R set";
        } else if (!Is_Zero_Col(x,0) && !Is_Zero_Col(x,1)) { // x0 + λu
            System.out.print("the solution is a infinite set of vectors in R" + m + " space which are linearly dependents in the vector space: x = ");
            s += Display_Vector(x,0,fn);
            st += " when ";
            for (int t = 1; t < m; t++) {
                if (!Is_Zero_Col(x,t)) {
                    s = (Is_Zero_Col(x,2)) ? s + " + s*" : s + " + s" + t + "*";
                    if (n == 2) {
                        st += "s its a free scalar";
                    } else if (t == n - 1) {
                        st += "s" + t + " its a free scalars";
                    } else {
                        st += "s" + t + ",";
                    }
                    s += Display_Vector(x,t,fn);
                }
            }
            st += " that belongs to the R set";
        } else { // x0
            System.out.print("exist a single solution in R" + m + " space for the system which is: x = ");
            s += Display_Vector(x,0,fn);
        }
        System.out.println(s + st);
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    public static void User_Menu_System() {
        System.out.println("choose the number of method to solution:");
        System.out.println("1. upper --> lower ranking method");
        System.out.println("2. lower --> upper ranking method");
        System.out.println("3. parallel ranking method");
        System.out.println("4. parallel ranking method (recursive)");
    }

    // display user interface by selection format for solution
    public static void User_Menu_Solution() {
        System.out.println("choose the character of format to representation of solution:");
        System.out.println("d. decimal");
        System.out.println("r. rational");
    }

    /////////////////////////////////////////////// Display Format ///////////////////////////////////////////////
    // display the coordinates as a vector representation
    public static String Display_Vector(float[][] x, int c, String fn) {
        int m = x.length;
        String s = "(";
        for (int i = 0; i < m; i++) {
            if ((Math.round(x[i][c] * 1000.0) / 1000.0) % 1 == 0) {
                s += (int) (Math.round(x[i][c] * 1000.0) / 1000.0);
            } else if (fn.equals("d")) {
                s += Math.round(x[i][c] * 1000.0) / 1000.0;
            } else if (fn.equals("r")) {
                s += convertDecimalToFraction(x[i][c]);
            } if (i != m - 1) {
                s += " ,";
            }
        }
        s += ")";
        return s;
    }

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if the vector is a zero vector
    public static boolean Is_Zero_Vector(float[] b) {
        int n = b.length;
        for (int i = 0; i < n; i++) {
            if (b[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if exist in the matrix a zeros row
    public static boolean Is_Zero_Row(float[][] A, int r) {
        int n = A[0].length;
        for (int j = 0; j < n; j++) {
            if (A[r][j] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if exist in the vector a zeros column
    public static boolean Is_Zero_Col(float[][] b, int c) {
        int m = b.length, n = b[0].length;
        for (int i = 0; i < m && c < n; i++) {
            if (b[i][c] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if the matrix is an upper triangular
    public static boolean Is_Upper_Triangular(float[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (A[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the matrix is a lower triangular
    public static boolean Is_Lower_Triangular(float[][] A) {
        int n = A.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (A[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if all vector values is equal to each other
    public static boolean Is_Equals_Values(Vector<Float> R) {
        int n = R.size();
        for (int i = 0; i < n - 1; i++) {
            if (!R.get(i).equals(R.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    // check if a matrix is a unit matrix
    public static boolean Is_Unit_Matrix(float[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][i] != 1 || (i != j && A[i][j] != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the specific row in the matrix is a unit vector
    public static boolean Is_Unit_Vector(float[][] A, int i) {
        int n = A[0].length, c = 0;
        for (int j = 0; j < n; j++) {
            if (A[i][j] != 0) {
                c++;
            }
        }
        return (c <= 1);
    }

    // check if the specific row in the matrix is a unit vector
    public static boolean Is_Exist_Vector(float[][] A, int r) {
        int n = A[0].length;
        float[] v = Get_Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Get_Index_for_Unit_Vector(v);
        for (int i = 0; i < n; i++) {
            if (i != r && Is_Unit_Vector(A,r) && Is_Unit_Vector(A,i)) {
                int c2 = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,i));
                if (c1 == c2 && c1 != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if exists two vectors in the matrix which are linearly dependent
    public static boolean Is_Linear_Dependent_Rows(float[][] A) {
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
    public static boolean Is_Linear_Independent_System(float[][] A, float[] b) {
        int m = A.length, n = A[0].length;
        for (int r = 0; r < m; r++) {
            if (Is_Zero_Row(A,r) && b[r] != 0) {
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
                if (Is_Equals_Values(R) && R.size() > 1 && (b[r1] != 0 || b[r2] != 0) && (b[r1] / b[r2] != R.get(0))) {
                    return true;
                }
            }
        }
        return false;
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // find two columns in the matrix which are linearly dependent
    public static int Get_Linear_Dependent_Columns(float[][] A) {
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
    public static int Get_Index_Row_from_Matrix(float[][] A, int r) {
        int n = A[0].length;
        float[] v = Get_Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Get_Index_for_Unit_Vector(v);
        for (int i = 0; i < n; i++) {
            int c2 = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,i));
            if (c1 == c2 && c1 != -1) {
                return i;
            }
        }
        return -1;
    }

    // get the index from the vector that is indicating a unit vector
    public static int Get_Index_for_Unit_Vector(float[] v) {
        int n = v.length;
        for (int c = 0; c < n; c++) {
            if (v[c] != 0) {
                return c;
            }
        }
        return -1;
    }

    // get the index starting from the specific column in the matrix which are him value not equal to 0 with or in the negative direction
    public static int Get_Index_UnZero_Value(float[][] A, int k, boolean flag) {
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
    public static int Get_Intersection_Zero_Row_Col(float[][] A, int r) {
        int n = A[0].length;
        for (int c = 0; c < n; c++) {
            if (Is_Zero_Col(A,c) && Is_Zero_Row(A,r)) {
                return c;
            }
        }
        return -1;
    }

    //////////////////////////////////////////////// Retreat Rows ////////////////////////////////////////////////
    // replace between two rows in a system Ax = b
    public static void Retreat_Rows_System(float[][] A, float[][] b, int r1, int r2) {
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
    public static float[][] Increase_Rows_in_Matrix(float[][] A, int m) {
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
    public static float[][] Increase_Rows_in_Vector(float[] b, int m) {
        int n = b.length;
        float[][] nb = new float[m][1];
        for (int i = 0; i < n; i++) {
            nb[i][0] = (i < m) ? b[i] : 0;
        }
        return nb;
    }

    // add a new column to the vector
    public static float[][] Increase_Cols_in_Vector(float[][] b) {
        int m = b.length, n = b[0].length;
        float[][] nb = new float[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nb[i][j] = b[i][j];
            }
        }
        return nb;
    }

    /////////////////////////////////////////////// Derivative Rows //////////////////////////////////////////////
    // import the specific row from the matrix
    public static float[] Get_Row_from_Matrix(float[][] A, int r) {
        int n = A[0].length;
        float[] v = new float[n];
        for (int j = 0; j < n; j++) {
            v[j] = A[r][j];
        }
        return v;
    }

    ////////////////////////////////////////////////// Convertor /////////////////////////////////////////////////
    // convert a value to a format of rational number
    public static String convertDecimalToFraction(float x){
        if (x < 0) {
            return "-" + convertDecimalToFraction(-x);
        } else {
            float tolerance = (float) 1.0E-5, h1 = 1, h2 = 0, k1 = 0, k2 = 1, b = x;
            do {
                float a = (float) Math.floor(b);
                float aux = h1;
                h1 = a*h1 + h2;
                h2 = aux;
                aux = k1;
                k1 = a*k1 + k2;
                k2 = aux;
                b = 1 / (b - a);
            } while (Math.abs(x - h1/k1) > x*tolerance);
            if (k1 != 1) {
                return String.valueOf((int) h1 + "/" + (int) k1);
            } else {
                return String.valueOf((int) h1);
            }
        }
    }

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
    // show elementary actions for sum between rows in the system
    public static void Sum_Elementary_Action(float k, int j, int i, String fn) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            if (k > 0) {
                if (k % 1 == 0) {
                    if (k == 1) {
                        System.out.println("R" + r + " --> R" + r + " - R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c + "\n");
                    }
                } else if (fn.equals("d")) {
                    k = (float) (Math.round(k * 1000.0) / 1000.0);
                    if (k % 1 == 0) {
                        System.out.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " - " + k + "*R" + c + "\n");
                    }
                } else if (fn.equals("r")) {
                    String v = convertDecimalToFraction(k);
                    if (!v.equals("1")) {
                        System.out.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " - R" + c + "\n");
                    }
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        System.out.println("R" + r + " --> R" + r + " + R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c + "\n");
                    }
                } else if (fn.equals("d")) {
                    k = (float) (Math.round(k * 1000.0) / 1000.0);
                    if (k % 1 == 0) {
                        System.out.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c + "\n");
                    }
                } else if (fn.equals("r")) {
                    String v = convertDecimalToFraction(-k);
                    if (!v.equals("-1")) {
                        System.out.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " + R" + c + "\n");
                    }
                }
            }
        }
    }

    // show elementary actions for multiplication of a row in the system
    public static void Mul_Elementary_Action(float k, int j, String fn) {
        if (k != 1) {
            int r = j + 1;
            if (k % 1 == 0) {
                if (k == -1) {
                    System.out.println("R" + r + " --> - R" + r + "\n");
                } else {
                    System.out.println("R" + r + " --> " + (int) k + "*R" + r + "\n");
                }
            } else if (fn.equals("d")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k % 1 == 0) {
                    System.out.println("R" + r + " --> " + (int) k + "*R" + r + "\n");
                } else {
                    System.out.println("R" + r + " --> " + k + "*R" + r + "\n");
                }
            } else if (fn.equals("r")) {
                k = (float) (Math.round(k * 1000.0) / 1000.0);
                if (k == -1) {
                    System.out.println("R" + r + " --> - R" + r + "\n");
                } else {
                    System.out.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r + "\n");
                }
            }
        }
    }

    // show elementary actions for replace between rows in the system
    public static void Retreat_Elementary_Action(int i, int j) {
        int r1 = i + 1, r2 = j + 1;
        if (r1 <= r2) {
            System.out.println("R" + r1 + " <--> R" + r2 + "\n");
        } else {
            System.out.println("R" + r2 + " <--> R" + r1 + "\n");
        }
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // solve system of linear equations Ax = b by an upper ranking and then a lower ranking
    public static float[][] Upper_Ranking_Method(float[][] A, float[][] b, String fn) {
        if (Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            System.out.println("A is already parallel triangular so now will be change directly to I:");
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A)) {
            System.out.println("A is already upper triangular so now we'll go directly to the lower ranking:");
            return Lower_Ranking_Method(A,b,fn);
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            System.out.println("transform L matrix to I by an upper ranking:");
        } else {
            System.out.println("transform A matrix to U by an upper ranking:");
        }
        int n = A.length, t = b[0].length - 1;
        for (int i = 0; i < n; i++) {
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            if (Is_Zero_Row(A,i) && !Is_Linear_Dependent_Rows(A)) {
                int d1 = Get_Intersection_Zero_Row_Col(A,i);
                int d2 = Get_Linear_Dependent_Columns(A);
                if (d1 != -1) {
                    System.out.println("define a new column in the vector b when x" + (d1 + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][d1] = 1;
                    b[i][++t] = 1;
                } else if (d2 != -1) {
                    System.out.println("define a new column in the vector b when x" + (d2 + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][d2] = 1;
                    b[i][++t] = 1;
                } else if (!Is_Exist_Vector(A,i)) {
                    System.out.println("define a new column in the vector b when x" + (i + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][i] = 1;
                    b[i][++t] = 1;
                }
                Print_Status_System(A,b,fn);
            } if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i,true);
                int l = Get_Index_Row_from_Matrix(A,i);
                if (Is_Exist_Vector(A,i) && l < i) {
                    r = Get_Index_UnZero_Value(A,i,false);
                } if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(A,b,i,r);
                    Print_Status_System(A,b,fn);
                }
            }
            for (int j = i + 1; j < n; j++) {
                if (A[i][i] != 0 && A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = 0; k < n; k++) {
                        A[j][k] -= A[i][k] * c;
                        if (k <= t) {
                            b[j][k] -= b[i][k] * c;
                            b[j][k] = (b[j][k] >= -0.0001 && b[j][k] <= 0.0001) ? 0 : b[j][k];
                        }
                    }
                    A[j][i] = (A[j][i] >= -0.0001 && A[j][i] <= 0.0001) ? 0 : A[j][i];
                    Print_Status_System(A,b,fn);
                }
                A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
                if (Is_Unit_Vector(A,j)) {
                    int d = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,j));
                    if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                        float c = 1 / A[j][d];
                        Mul_Elementary_Action(c,j,fn);
                        for (int k = 0; k <= t; k++) {
                            b[j][k] /= A[j][d];
                        }
                        b[j][0] = (float) (Math.round(b[j][0] * 1000.0) / 1000.0);
                        A[j][d] = 1;
                        Print_Status_System(A,b,fn);
                    }
                }
            }
            if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A)) {
                System.out.print("and now ");
                return Lower_Ranking_Method(A,b,fn);
            }
        }
        if (!Is_Unit_Matrix(A)) {
            System.out.println("still not yet received an unit matrix");
            return Lower_Ranking_Method(A,b,fn);
        }
        return b;
    }

    // solve system of linear equations Ax = b by a lower ranking and then an upper ranking
    public static float[][] Lower_Ranking_Method(float[][] A, float[][] b, String fn) {
        if (Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            System.out.println("A is already parallel triangular so now will be change directly to I:");
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            System.out.println("A is already lower triangular so now we'll go directly to the upper ranking:");
            return Upper_Ranking_Method(A,b,fn);
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A)) {
            System.out.println("transform U matrix to I by an lower ranking:");
        } else {
            System.out.println("transform A matrix to L by an lower ranking:");
        }
        int n = A.length, t = b[0].length - 1;
        for (int i = n - 1; i >= 0; i--) {
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            if (Is_Zero_Row(A,i) && !Is_Linear_Dependent_Rows(A)) {
                int d1 = Get_Intersection_Zero_Row_Col(A,i);
                int d2 = Get_Linear_Dependent_Columns(A);
                if (d1 != -1) {
                    System.out.println("define a new column in the vector b when x" + (d1 + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][d1] = 1;
                    b[i][++t] = 1;
                } else if (d2 != -1) {
                    System.out.println("define a new column in the vector b when x" + (d2 + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][d2] = 1;
                    b[i][++t] = 1;
                } else if (!Is_Exist_Vector(A,i)) {
                    System.out.println("define a new column in the vector b when x" + (i + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][i] = 1;
                    b[i][++t] = 1;
                }
                Print_Status_System(A,b,fn);
            } if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i,false);
                int l = Get_Index_Row_from_Matrix(A,i);
                if (Is_Exist_Vector(A,i) && l < i) {
                    r = Get_Index_UnZero_Value(A,i,true);
                } if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(A,b,i,r);
                    Print_Status_System(A,b,fn);
                }
            }
            for (int j = i - 1; j >= 0; j--) {
                if (A[i][i] != 0 && A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = n - 1; k >= 0; k--) {
                        A[j][k] -= A[i][k] * c;
                        if (k <= t) {
                            b[j][k] -= b[i][k] * c;
                            b[j][k] = (b[j][k] >= -0.0001 && b[j][k] <= 0.0001) ? 0 : b[j][k];
                        }
                    }
                    A[j][i] = (A[j][i] >= -0.0001 && A[j][i] <= 0.0001) ? 0 : A[j][i];
                    Print_Status_System(A,b,fn);
                }
                A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
                if (Is_Unit_Vector(A,j)) {
                    int d = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,j));
                    if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                        float c = 1 / A[j][d];
                        Mul_Elementary_Action(c,j,fn);
                        for (int k = 0; k <= t; k++) {
                            b[j][k] /= A[j][d];
                        }
                        b[j][0] = (float) (Math.round(b[j][0] * 1000.0) / 1000.0);
                        A[j][d] = 1;
                        Print_Status_System(A,b,fn);
                    }
                }
            }
            if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
                System.out.print("and now ");
                return Upper_Ranking_Method(A,b,fn);
            }
        }
        if (!Is_Unit_Matrix(A)) {
            System.out.println("still not yet received an unit matrix");
            return Upper_Ranking_Method(A,b,fn);
        }
        return b;
    }

    // solve system of linear equations Ax = b by parallel ranking
    public static float[][] Parallel_Ranking_Method(float[][] A, float[][] b, String fn) {
        System.out.println("transform A matrix to I by a parallel ranking:");
        while (!Is_Unit_Matrix(A)) {
            int n = A.length, t = b[0].length - 1;
            for (int i = 0; i < n; i++) {
                A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
                if (Is_Zero_Row(A,i) && !Is_Linear_Dependent_Rows(A)) {
                    int d1 = Get_Intersection_Zero_Row_Col(A,i);
                    int d2 = Get_Linear_Dependent_Columns(A);
                    if (d1 != -1) {
                        System.out.println("define a new column in the vector b when x" + (d1 + 1) + " is a free variable in R" + n + " space:");
                        b = Increase_Cols_in_Vector(b);
                        A[i][d1] = 1;
                        b[i][++t] = 1;
                    } else if (d2 != -1) {
                        System.out.println("define a new column in the vector b when x" + (d2 + 1) + " is a free variable in R" + n + " space:");
                        b = Increase_Cols_in_Vector(b);
                        A[i][d2] = 1;
                        b[i][++t] = 1;
                    } else if (!Is_Exist_Vector(A,i)) {
                        System.out.println("define a new column in the vector b when x" + (i + 1) + " is a free variable in R" + n + " space:");
                        b = Increase_Cols_in_Vector(b);
                        A[i][i] = 1;
                        b[i][++t] = 1;
                    }
                    Print_Status_System(A,b,fn);
                } if (A[i][i] == 0) {
                    int r = Get_Index_UnZero_Value(A,i,true);
                    if (r >= 0 && r < n && r != i) {
                        A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                        Retreat_Elementary_Action(i,r);
                        Retreat_Rows_System(A,b,i,r);
                        Print_Status_System(A,b,fn);
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && A[i][i] != 0 && A[j][i] != 0) {
                        float c = A[j][i] / A[i][i];
                        Sum_Elementary_Action(c,j,i,fn);
                        for (int k = 0; k < n; k++) {
                            A[j][k] -= A[i][k] * c;
                            if (k <= t) {
                                b[j][k] -= b[i][k] * c;
                            }
                        }
                        A[j][i] = (A[j][i] >= -0.0001 && A[j][i] <= 0.0001) ? 0 : A[j][i];
                        Print_Status_System(A,b,fn);
                    }
                    A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
                    if (Is_Unit_Vector(A,j)) {
                        int d = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,j));
                        if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                            float c = 1 / A[j][d];
                            Mul_Elementary_Action(c,j,fn);
                            for (int k = 0; k <= t; k++) {
                                b[j][k] /= A[j][d];
                            }
                            b[j][0] = (float) (Math.round(b[j][0] * 1000.0) / 1000.0);
                            A[j][d] = 1;
                            Print_Status_System(A,b,fn);
                        }
                    }
                }
            }
        }
        return b;
    }

    // solve system of linear equations Ax = b by a parallel ranking (recursive)
    public static float[][] Parallel_Ranking_Method_Rec(float[][] A, float[][] b, int i, int j, String fn) {
        if (Is_Unit_Matrix(A)) {
            return b;
        } else {
            int n = A.length, t = b[0].length - 1;
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            if (Is_Zero_Row(A,i) && !Is_Linear_Dependent_Rows(A)) {
                int d1 = Get_Intersection_Zero_Row_Col(A,i);
                int d2 = Get_Linear_Dependent_Columns(A);
                if (d1 != -1) {
                    System.out.println("define a new column in the vector b when x" + (d1 + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][d1] = 1;
                    b[i][++t] = 1;
                } else if (d2 != -1) {
                    System.out.println("define a new column in the vector b when x" + (d2 + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][d2] = 1;
                    b[i][++t] = 1;
                } else if (!Is_Exist_Vector(A,i)) {
                    System.out.println("define a new column in the vector b when x" + (i + 1) + " is a free variable in R" + n + " space:");
                    b = Increase_Cols_in_Vector(b);
                    A[i][i] = 1;
                    b[i][++t] = 1;
                }
                Print_Status_System(A,b,fn);
            } if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i,true);
                if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(A,b,i,r);
                    Print_Status_System(A,b,fn);
                }
            }
            if (i != j && A[i][i] != 0 && A[j][i] != 0) {
                float c = A[j][i] / A[i][i];
                Sum_Elementary_Action(c,j,i,fn);
                for (int k = 0; k < n; k++) {
                    A[j][k] -= A[i][k] * c;
                    if (k <= t) {
                        b[j][k] -= b[i][k] * c;
                    }
                }
                A[j][i] = (A[j][i] >= -0.0001 && A[j][i] <= 0.0001) ? 0 : A[j][i];
                Print_Status_System(A,b,fn);
            }
            A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
            if (Is_Unit_Vector(A,j)) {
                int d = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,j));
                if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                    float c = 1 / A[j][d];
                    Mul_Elementary_Action(c,j,fn);
                    for (int k = 0; k <= t; k++) {
                        b[j][k] /= A[j][d];
                    }
                    b[j][0] = (float) (Math.round(b[j][0] * 1000.0) / 1000.0);
                    A[j][d] = 1;
                    Print_Status_System(A,b,fn);
                }
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
            return Parallel_Ranking_Method_Rec(A,b,i,j,fn);
        }
    }

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose action in order to solve a system Ax = b
    public static void Solve_System(float[][] A, float[][] b, String fn) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_System();
        int op = sc.nextInt();
        Print_Status_System(A,b,fn);
        float[][] x;
        switch (op) {
            case 1:
                x = Upper_Ranking_Method(A,b,fn);
                Print_Solution(x,fn);
                break;
            case 2:
                x = Lower_Ranking_Method(A,b,fn);
                Print_Solution(x,fn);
                break;
            case 3:
                x = Parallel_Ranking_Method(A,b,fn);
                Print_Solution(x,fn);
                break;
            case 4:
                x = Parallel_Ranking_Method_Rec(A,b,0,0,fn);
                Print_Solution(x,fn);
                break;
            default:
                throw new Exception("you entered an invalid value for an option number to solution");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public static void Check_User_Input(float[][] A, float[] b) throws Exception {
        int m = A.length, n = A[0].length, k = b.length;
        if (m <= n && m == k) {
            Display_Exercise(A,b);
            if (Is_Linear_Independent_System(A,b)) {
                String ex = "does not an exists solutions for this";
                ex = (n == 1) ? ex + " equation" : ex + " system";
                throw new Exception(ex);
            } else if (n == 1 && A[0][0] == 0 && b[0] == 0) {
                System.out.println("exists an infinite number of solutions in R1 space for the equation that is: x = s when s its a free value that belongs to the R set");
            } else {
                Scanner sc = new Scanner(System.in);
                User_Menu_Solution();
                String fn = sc.next();
                if (fn.equals("d") || fn.equals("r")) {
                    if (n > 1) { // R2 space or higher
                        if (m < n) {
                            if (n - m == 1) {
                                System.out.println("added one more row of zeros in order to get a square completion");
                            } else {
                                System.out.println("added " + (n - m) + " more rows of zeros in order to get a square completion");
                            }
                            A = Increase_Rows_in_Matrix(A,m);
                        }
                        float[][] bt = Increase_Rows_in_Vector(b,n);
                        Solve_System(A,bt,fn);
                    } else { // R1 space
                        float c = b[0] / A[0][0];
                        if (c % 1 == 0) {
                            System.out.println("exist a single solution in R1 space for the equation which is: x = " + (int) c);
                        } else if (fn.equals("d")) {
                            System.out.println("exist a single solution in R1 space for the equation which is: x = " + c);
                        } else if (fn.equals("r")) {
                            System.out.println("exist a single solution in R1 space for the equation which is: x = " + convertDecimalToFraction(c));
                        }
                    }
                } else {
                    throw new Exception("you entered invalid value for a representation elementary actions and solution");
                }
            }
        } else {
            throw new Exception("your input does not meet the conditions for system of linear equations");
        }
    }

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        float[][] A11 = {{3}};
        float[] b11 = {4};
        // x = 1.3333334 = 4/3
        float[][] A12 = {{0}};
        float[] b12 = {3};
        // does not an exists any solution to the equation
        float[][] A13 = {{0}};
        float[] b13 = {0};
        // x = λ (free variable) when λ find in R
        float[][] A41 = {{-2,3,3,-2},{-1,4,2,-2},{1,3,1,3},{-3,-2,4,-5}};
        float[] b41 = {8,5,19,-19};
        // x = (-7 ,3 ,-1 ,6)
        float[][] A42 = {{2,-1,3,3},{4,-2,6,5},{6,-3,-1,2},{8,-4,-4,-5}};
        float[] b42 = {4,4,14,-4};
        // x = (0 ,-1 ,-3 ,4) + λ*(1 ,2 ,0 ,0)
        float[][] A43 = {{2,-1,3,3},{4,-2,6,5}};
        float[] b43 = {4,4};
        // x = (0 ,8 ,0 ,4) + λ1*(0 ,3 ,1 ,0) + λ2*(1 ,2 ,0 ,0)
        float[][] A44 = {{0,0,0,0},{0,0,0,0}};
        float[] b44 = {0,0};
        // x = λ1*(1 ,0 ,0 ,0) + λ2*(0 ,1 ,0 ,0) + λ3*(0 ,0 ,1 ,0) + λ4*(0 ,0 ,0 ,1)
        float[][] A51 = {{-2,2,2,-1,1},{-4,4,4,4,3},{2,3,2,3,2},{-3,-1,1,2,2},{5,5,3,5,5}};
        float[] b51 = {1,31,15,8,52};
        // x = (-7.5 ,19 ,-33.5 ,2 ,17)
        float[][] A52 = {{-5,-6,-5,-5,-3},{0,-1,0,4,5},{-6,-4,-6,0,-3},{-5,2,-5,-1,-2},{-6,-1,-6,5,1}};
        float[] b52 = {0,0,0,0,0};
        // x = λ*(1 ,0 ,-1 ,0 ,0)
        float[][] A53 = {{1,5,7,3,-5},{2,-4,7,3,-2},{-8,-3,-3,-4,1},{1,-8,-3,0,-7},{8,-5,3,4,-2}};
        float[] b53 = {0,0,0,0,0};
        // upper & parallel: x = λ*(-4.125 ,-0.125 ,-3.375 ,11.125 ,1) = λ*(-33/8 ,-1/8 ,-27/8 ,89/8 ,1)
        // lower: x = λ*(1 ,0.03 ,0.818 ,-2.697 ,-0.242) = λ*(1 ,1/33 ,9/11 ,-89/33 ,-8/33)
        float[][] A54 = {{-2,2,2,-1,1},{-4,4,4,4,3}};
        float[] b54 = {1,31};
        // x = (0 ,-14 ,0 ,0 ,29) + s1*(0 ,-1 ,1 ,0 ,0) + s2*(0 ,3.5 ,0 ,1 ,-6) + s3*(1 ,1 ,0 ,0 ,0)
        float[][] A61 = {{6,1,3,3,-11,1},{11,-6,5,11,0,-4},{-2,-2,-4,2,-3,0},{2,12,-1,-7,3,0},{5,-11,-11,8,-8,-2},{3,2,2,-1,-1,1}};
        float[] b61 = {-1,41,-30,-8,-27,18};
        // x = (4.8 ,-3.7 ,3.3 ,-3.1 ,2.8 ,4.1)
        float[][] A62 = {{-2,0,-4,-2,5,-6},{6,-1,-2,0,1,0},{6,0,0,-2,5,2},{-2,0,3,2,2,3},{-4,-2,3,-6,4,-4},{5,1,-5,-1,5,-2}};
        float[] b62 = {0,0,0,0,0,0};
        // upper & parallel: x = λ*(-0.5 ,-1 ,-1 ,-0.5 ,0 ,1)
        // lower: x = λ*(1 ,2 ,2 ,1 ,0 ,-2)
        float[][] A63 = {{5,5,-1,4,-1,-3},{-5,6,-1,6,1,-6},{1,1,-5,4,-5,1},{-1,1,0,0,-2,6},{1,6,2,5,-4,-4},{-5,0,-1,0,-1,6}};
        float[] b63 = {0,0,0,0,0,0};
        // upper & parallel: x = λ*(0.556 ,-2.778 ,1.889 ,4.333 ,1.333 ,1) = λ*(5/9 ,-25/9 ,17/9 ,13/3 ,4/3 ,1)
        // lower: x = λ*(1 ,-5 ,3.4 ,7.8 ,2.4 ,1.8) = λ*(1 ,-5 ,17/5 ,39/5 ,12/5 ,9/5)
        float[][] A71 = {{-1,0,4,-1,-2,5,-1},{6,-7,7,1,-7,-6,5},{0,2,6,1,-7,6,1},{2,2,0,-2,-6,6,-5},{3,3,-3,1,7,-1,-2},{1,-5,1,-4,3,1,-4},{7,-5,5,0,-4,-4,1}};
        float[] b71 = {6,-12,8,20,-3,-4,-6};
        // x = (2 ,17 ,5 ,-29 ,-1 ,-7 ,8)
        float[][] A72 = {{0,-4,-2,7,7,1,0},{0,2,3,1,2,3,0},{0,-1,-1,-5,2,-3,-1},{2,1,3,0,-5,1,-6},{2,4,0,4,-3,2,4},{4,2,6,3,0,-4,-2},{6,2,5,-3,-3,-4,-2}};
        float[] b72 = {3,8,9,11,14,18,18};
        // x = (4.3 ,9 ,-5.2 ,0.4 ,3.8 ,-0.8 ,-4.8)
        float[][] A73 = {{2,3,1,-4,0,-3,0},{-3,1,1,1,0,-4,-1},{0,1,0,-2,1,-1,1},{-4,1,-3,1,0,-2,1},{1,-3,0,-2,-4,1,0},{1,-2,3,0,-4,-2,-4},{0,4,-4,-2,-3,-2,3}};
        float[] b73 = {0,0,0,0,0,0,0};
        // upper & parallel: x = λ*(2.5 ,-2.25 ,-1.5 ,1.25 ,1 ,-2.75 ,1)
        // lower: x = λ*(1 ,-0.9 ,-0.6 ,0.5 ,0.4 ,-1.1 ,0.4)
        float[][] A74 = {{7,6,4,2,-2,-7,5},{0,-3,7,-7,4,0,-7},{5,-3,4,-5,0,4,0},{4,3,-5,0,4,-2,-3},{6,-3,6,-3,-4,2,5},{-7,1,-7,6,-2,3,2},{6,-2,-5,-1,-3,4,-2}};
        float[] b74 = {-8,-8,10,14,1,8,2};
        // x = (13.6 ,-16.4 ,13.8 ,40 ,18.6 ,9.4 ,-7.4)
        float[][] A75 = {{-3,6,-3,3,-4,6,6},{4,0,2,-4,-6,-6,-3},{-2,-2,-7,5,3,3,2},{-5,0,2,0,3,2,4},{-6,0,0,7,-3,4,6},{5,-5,2,1,7,-2,-5},{-3,-2,6,5,0,-1,7}};
        float[] b75 = {14,-6,20,6,16,-10,-15};
        // x = (12.5 ,-31.5 ,-1 ,-19.5 ,-12.5 ,28 ,13)
        float[][] A76 = {{6,-3,7,-6,-5,-2,3},{5,4,7,6,6,2,-5},{-4,-2,-1,-5,-2,3,-5},{-7,1,2,-1,-5,0,6},{7,-2,0,7,-2,4,-2},{-1,-3,-4,-4,0,0,2},{7,-6,-6,-4,6,-1,2}};
        float[] b76 = {-2,-9,15,0,18,-13,-15};
        // x = (-12.4 ,-30 ,9.7 ,14.6 ,1.4 ,-18.2 ,-9.1)
        float[][] A77 = {{0,-3,-1,5,7,0,0},{6,-5,1,6,0,5,-7},{-1,-5,7,-5,-7,4,0},{3,-5,4,7,6,0,3},{-4,-1,4,-6,-4,-6,-3},{2,6,0,4,-1,-1,-1},{-7,0,7,-5,0,-2,-3}};
        float[] b77 = {-5,7,18,-9,-12,11,-6};
        // x = (-6.7 ,-0.85 ,-1.65 ,6.7 ,-6.1 ,2.35 ,1.05)
        float[][] A78 = {{0,0,3,4,-1,6,-3},{7,-4,0,-7,1,-7,-4},{-7,3,-3,5,1,3,4},{-6,5,3,0,6,5,-5},{2,-5,1,-6,-7,7,-6},{6,7,-3,2,-6,3,3},{2,2,-7,-3,3,-6,0}};
        float[] b78 = {3,5,2,-13,20,2,5};
        // x = (-20.65 ,11.8 ,2.2 ,13.55 ,-33.25 ,-29.75 ,-29.15)
        float[][] A79 = {{0,-5,3,3,-4,7,-6},{-2,1,3,-7,6,7,-6},{0,1,0,-2,5,5,2},{-6,6,6,-3,-4,1,-5},{-3,1,1,-3,-5,6,0},{4,2,-4,0,-1,0,0},{1,0,-1,-1,4,-4,5}};
        float[] b79 = {-15,-2,17,1,-10,15,5};
        // x = (3.11 ,4.87 ,1.4 ,2.58 ,1.58 ,1.45 ,1.07)
        float[][] A710 = {{4,-5,1,1,-1,-6,7},{-5,0,1,-7,-2,-5,-5},{0,-1,5,0,7,6,0},{6,-3,1,0,-3,-3,6},{-5,-5,4,-4,4,-4,-5},{0,1,-2,6,4,-7,6},{-1,-6,2,-2,-4,1,7}};
        float[] b710 = {-13,19,-8,-19,16,11,-20};
        // x = (-2.1 ,1.68 ,1.46 ,1.58 ,-0.3 ,-1.92 ,-1.58)
        float[][] A711 = {{-3,-6,0,-1,-5,-2,-4},{3,-2,-2,3,5,5,0},{5,7,4,4,7,-1,-2},{3,0,0,-7,3,7,-6},{-6,0,0,-2,-7,-4,-6},{0,1,7,-3,4,5,3},{0,7,2,0,2,-1,-3}};
        float[] b711 = {2,-3,-10,5,-7,7,-11};
        // x = (3.19 ,-0.21 ,1.33 ,-0.21 ,-3.58 ,1.64 ,1.13)
        float[][] A712 = {{7,4,4,-1,-3,-1,-3},{-5,-3,4,2,-4,-1,7},{-5,7,-7,-1,-4,0,0},{6,3,5,3,-5,0,0},{3,1,5,-1,7,-5,-1},{1,0,7,-5,-2,-2,-7},{-1,0,3,3,7,-2,-5}};
        float[] b712 = {0,1,5,6,-18,7,14};
        // x = (-22 ,57 ,53 ,-31 ,41 ,113 ,27)
        float[][] A81 = {{4,4,-4,-1,-4,-1,4,-1},{0,4,-4,4,0,3,1,4},{-4,3,-3,0,0,1,-2,-2},{0,3,-2,2,4,0,0,0},{1,-1,2,0,-3,2,2,0},{3,-2,0,-1,0,4,-4,1},{-2,3,-1,3,-1,1,4,4},{-4,-1,0,-4,-4,-3,-1,-4}};
        float[] b81 = {0,0,0,0,0,0,0,0};
        // upper & parallel: x = λ*(1.5 ,6 ,5.5 ,1.5 ,-2.5 ,-2.5 ,-4.5 ,1)
        // lower: x = λ*(1 ,4 ,3.667 ,1 ,-1.667 ,-1.667 ,-3 ,0.667) = λ*(1 ,4 ,11/3 ,1 ,-5/3 ,-5/3 ,-3 ,2/3)
        float[][] A82 = {{2,0,2,0,-1,1,-3,2},{-1,-3,-1,-3,-4,0,-2,-1},{4,3,-1,2,4,0,0,0},{0,-3,1,0,0,2,-1,4},{2,-3,-2,4,2,-1,2,-4},{-3,-2,1,2,3,1,4,-2},{0,0,-4,-4,2,-3,-2,3},{0,-3,-1,3,0,-3,1,-3}};
        float[] b82 = {0,0,0,0,0,0,0,0};
        // upper & parallel: x = λ*(9, -3, 7, -8, -1, -5, 10, 1)
        // lower: x = λ*(1 ,-0.333 ,0.778 ,-0.889 ,-0.111 ,-0.556 ,1.111 ,0.111) = λ*(1 ,-1/3 ,7/9 ,-8/9 ,-1/9 ,-5/9 ,10/9 ,1/9)
        float[][] A83 = {{0,-1,-1,0,0,0,1,0},{1,3,0,0,-2,-1,0,0},{1,2,-2,1,0,0,-1,-1},{-3,-1,3,3,-3,0,2,2},{-2,3,-1,-2,1,2,-3,-3},{-1,0,-2,-2,1,-3,1,1},{-2,0,-3,0,3,0,-1,-2},{-1,-2,-1,-3,3,-2,-2,3}};
        float[] b83 = {0,0,0,0,0,0,0,0};
        // x = λ*(0 ,1 ,0 ,0 ,1 ,1 ,1 ,1)
        try {
            Check_User_Input(A52,b52);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
