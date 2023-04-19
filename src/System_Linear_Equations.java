import java.util.Scanner;

public class System_Linear_Equations {

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the system Ax = b in the linear equations format
    public static void Display_Exercise(float[][] A, float[] b) {
        int n = A.length;
        if (n == 1) {
            System.out.println("solve the next equation (" + 1 + " variable):");
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
                if (b[0] % 1 == 0) {
                    System.out.println(A[0][0] + "x = " + (int) b[0]);
                } else {
                    System.out.println(A[0][0] + "x = " + b[0]);
                }
            }
        } else {
            System.out.println("solve the next system (" + n + " variables):");
            for (int i = 0; i < n; i++) {
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
                    System.out.println(" = " + (int) (Math.round(b[i] * 1000.0) / 1000.0));
                } else {
                    System.out.println(" = " + (Math.round(b[i] * 1000.0) / 1000.0));
                }
            }
        }
        System.out.println();
    }

    // display current status of the system Ax = b each time of iteration on an element
    public static void Print_Status_System(float[][] A, float[] b, String fn) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
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
            if ((Math.round(b[i] * 1000.0) / 1000.0) % 1 == 0) {
                System.out.print((int) (Math.round(b[i] * 1000.0) / 1000.0));
            } else if (fn.equals("d")) {
                System.out.print(Math.round(b[i] * 1000.0) / 1000.0);
            } else if (fn.equals("r")) {
                System.out.print(convertDecimalToFraction(b[i]));
            }
            System.out.println();
        }
        System.out.println();
    }

    // display a matrix each current status
    public static void Print_Matrix(float[][] A, String fn) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
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
            System.out.println();
        }
        System.out.println();
    }

    // show the resulting solution as a vector representation
    public static void Print_Solution(float[] x, String fn) {
        int n = x.length;
        String s = "(";
        for (int i = 0; i < n; i++) {
            if ((Math.round(x[i] * 1000.0) / 1000.0) % 1 == 0) {
                s += (int) (Math.round(x[i] * 1000.0) / 1000.0);
            } else if (fn.equals("d")) {
                s += Math.round(x[i] * 1000.0) / 1000.0;
            } else if (fn.equals("r")) {
                s += convertDecimalToFraction(x[i]);
            } if (i != n - 1) {
                s += " ,";
            }
        }
        s += ")";
        System.out.println(s);
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    public static void User_Menu_System() {
        System.out.println("choose number method to solution:");
        System.out.println("1. invertible method");
        System.out.println("2. cramer method (first method)");
        System.out.println("3. cramer method (second method)");
        System.out.println("4. forward backward method");
        System.out.println("5. upper --> lower ranking method");
        System.out.println("6. lower --> upper ranking method");
        System.out.println("7. parallel ranking method (first method)");
        System.out.println("8. parallel ranking method (second method)");
    }

    // display user interface by selection format for solution
    public static void User_Menu_Solution() {
        System.out.println("choose the character of format to representation of solution:");
        System.out.println("d. decimal");
        System.out.println("r. rational");
    }

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if a matrix is zero matrix
    public static boolean Is_Zero_Matrix(float[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if a vector is zero vector
    public static boolean Is_Zero_Vector(float[] b) {
        int n = b.length;
        for (int i = 0; i < n; i++) {
            if (b[i] != 0) {
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
    public static boolean Is_Unit_Vector(float[][] A, int k) {
        int n = A.length;
        boolean flag = true;
        for (int i = 0; i < n && flag; i++) {
            for (int j = 0; j < n && A[k][i] != 0; j++) {
                if (j != i && A[k][j] != 0) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
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

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    public static int Get_Index_UnZero_Value(float[][] A, int k) {
        int n = A.length;
        for (int i = k + 1; i < n + k; i++) {
            if (A[i % n][k] != 0) {
                return i % n;
            }
        }
        return -1;
    }

    ////////////////////////////////////////////// Matrices Creation /////////////////////////////////////////////
    // create a unit matrix with "n*n" size
    public static float[][] Unit_Matrix(int n) {
        float[][] I = new float[n][n];
        for (int i = 0; i < n; i++) {
            I[i][i] = 1;
        }
        return I;
    }

    // duplicate the matrix values into a new matrix
    public static float[][] Copy_Matrix(float[][] A) {
        int n = A.length;
        float[][] cA = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cA[i][j] = A[i][j];
            }
        }
        return cA;
    }

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // calculate determinant of a matrix
    public static float Determinant(float[][] A) {
        int n = A.length;
        if (n == 1) {
            return A[0][0];
        }
        float sum = 0;
        for (int i = 0; i < n; i++) {
            sum += A[0][i] * Determinant(Sub_Matrix(A,0,i)) * Math.pow(-1,i);
        }
        return sum;
    }

    // calculate of sub-matrix from a matrix by cutting row "x" and column "y"
    public static float[][] Sub_Matrix(float[][] A, int x, int y) {
        int n = A.length ,p = 0 ,q = 0;
        float[][] subA = new float[n - 1][n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != x && j != y) {
                    subA[p][q] = A[i][j];
                    q++;
                    if (q == n - 1) {
                        p++;
                        q = 0;
                    }
                }
            }
        }
        return subA;
    }

    // calculate adjoint matrix of a matrix
    public static float[][] Adjoint(float[][] A) {
        int n = A.length;
        float[][] Adj = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Adj[j][i] = (float) (Math.pow(-1,i + j) * Determinant(Sub_Matrix(A,i,j)));
            }
        }
        return Adj;
    }

    // calculate invertible matrix of a matrix
    public static float[][] Invertible(float[][] A) {
        int n = A.length;
        float det = Determinant(A);
        float[][] invA = new float[n][n];
        float[][] adj = Adjoint(A);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invA[i][j] = (1 / det) * adj[i][j];
            }
        }
        return invA;
    }

    // replace between two rows in a system Ax = b
    public static void Retreat_Rows_System(float[][] A, float[] b, int r1, int r2) {
        int n = A.length;
        for (int j = 0; j < n; j++) {
            float t = A[r1][j];
            A[r1][j] = A[r2][j];
            A[r2][j] = t;
        }
        float t = b[r1];
        b[r1] = b[r2];
        b[r2] = t;
    }

    // calculate multiplication between two matrices provided that M1's length column is equal to M2's length row
    public static float[][] Mul_Mats(float[][] M1, float[][] M2) {
        int n = M1.length;
        float[][] M = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    M[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return M;
    }

    // calculate ranking of a matrix by upper triangular
    public static float[][] Ranking_Matrix(float[][] A) {
        int n = A.length;
        float[][] rA = Copy_Matrix(A);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (rA[i][i] == 0) {
                    int r = (i + 1) % n;
                    for (int k = 0; k < n; k++) {
                        float t = rA[i][k];
                        rA[i][k] = rA[r][k];
                        rA[r][k] = t;
                    }
                } if (rA[j][i] != 0) {
                    float c = rA[j][i] / rA[i][i];
                    for (int k = 0; k < n; k++) {
                        rA[j][k] = rA[j][k] - rA[i][k] * c;
                    }
                }
            }
        }
        return rA;
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
    // show elementary actions for replace between rows in the system
    public static void Retreat_Elementary_Action(int i, int j) {
        int r1 = i + 1, r2 = j + 1;
        if (r1 <= r2) {
            System.out.println("R" + r1 + " <--> R" + r2 + "\n");
        } else {
            System.out.println("R" + r2 + " <--> R" + r1 + "\n");
        }
    }

    // show elementary actions for sum between rows in the system
    public static void Sum_Elementary_Action(float k, int j, int i, String fn) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
            if (k > 0) {
                if (k % 1 == 0) {
                    if (k == 1) {
                        System.out.println("R" + r + " --> R" + r + " - R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c + "\n");
                    }
                } else if (fn.equals("d")) {
                    System.out.println("R" + r + " --> R" + r + " - " + k + "*R" + c + "\n");
                } else if (fn.equals("r")) {
                    System.out.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c + "\n");
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        System.out.println("R" + r + " --> R" + r + " + R" + c + "\n");
                    } else {
                        System.out.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c + "\n");
                    }
                } else if (fn.equals("d")) {
                    System.out.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c + "\n");
                } else if (fn.equals("r")) {
                    System.out.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c + "\n");
                }
            }
        }
    }

    // show elementary actions for multiplication of a row in the system
    public static void Mul_Elementary_Action(float k, int j, String fn) {
        if (k != 1) {
            int r = j + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
            if (k % 1 == 0) {
                if (k == -1) {
                    System.out.println("R" + r + " --> - R" + r + "\n");
                } else {
                    System.out.println("R" + r + " --> " + (int) k + "*R" + r + "\n");
                }
            } else if (fn.equals("d")) {
                System.out.println("R" + r + " --> " + k + "*R" + r + "\n");
            } else if (fn.equals("r")) {
                System.out.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r + "\n");
            }
        }
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // solve system of linear equations Ax = b by invertible multiplication method: x = Inv(A)*b
    public static float[] Invertible_Method(float[][] A, float[] b) {
        int n = A.length;
        float[] x = new float[n];
        float[][] invA = Invertible(A);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x[i] += b[j] * invA[i][j];
            }
            x[i] = (float)(Math.round(x[i] * 1000.0) / 1000.0);
        }
        return x;
    }

    // solve system of linear equations Ax = b by cramer method (first algorithm)
    public static float[] Cramer_Method_V1(float[][] A, float[] b) {
        int n = A.length;
        float det = Determinant(A);
        float[] x = new float[n];
        float[] h = new float[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                h[i] = A[i][j];
                A[i][j] = b[i];
            }
            float detj = Determinant(A);
            for (int i = 0; i < n; i++) {
                A[i][j] = h[i];
            }
            x[j] = detj / det;
        }
        return x;
    }

    // solve system of linear equations Ax = b by cramer method (second algorithm)
    public static float[] Cramer_Method_V2(float[][] A, float[] b) {
        int n = A.length;
        float[] x = new float[n];
        float det = Determinant(A);
        for (int i = 0; i < n; i++) {
            float sum = 0;
            for (int j = 0; j < n; j++) {
                sum += Math.pow(-1,i + j) * b[j] * Determinant(Sub_Matrix(A,j,i));
            }
            x[i] = sum / det;
        }
        return x;
    }

    // solve system of linear equations Ax = b by forward backward method: Ly = b and then Ux = y
    public static float[] Forward_Backward_Method(float[][] A, float[] b, String fn) {
        int n = b.length;
        System.out.println("first, we will calculate upper ranking of A:");
        float[][] U = Ranking_Matrix(A);
        Print_Matrix(U,fn);
        System.out.println("second, we will calculate lower ranking of A:");
        float[][] L = Mul_Mats(A,Invertible(U));
        Print_Matrix(L,fn);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (L[i][i] == 0) {
                    Retreat_Rows_System(L,b,i,j);
                    break;
                }
            }
        }
        System.out.println("third, we will solve forward system Ly = b:");
        float[] y = new float[n];
        Print_Status_System(L,b,fn);
        for (int i = 0; i < n; i++) {
            y[i] = b[i];
            for (int j = 0; j < i; j++) {
                y[i] -= L[i][j] * y[j];
            }
            y[i] /= L[i][i];
        }
        System.out.println("finally, we will solve backward system Ux = y:");
        float[] x = new float[n];
        Print_Status_System(U,y,fn);
        for (int i = n - 1; i >= 0; i--) {
            x[i] = y[i];
            for (int j = i + 1; j < n; j++) {
                x[i] -= U[i][j] * x[j];
            }
            x[i] /= U[i][i];
            x[i] = (float)(Math.round(x[i] * 1000.0) / 1000.0);
        }
        return x;
    }

    // solve system of linear equations Ax = b by an upper ranking and then a lower ranking
    public static float[] Upper_Ranking_Method(float[][] A, float[] b, String fn) {
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
        int n = A.length;
        for (int i = 0; i < n; i++) {
            if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_System(A,b,i,r);
                Print_Status_System(A,b,fn);
            }
            for (int j = i + 1; j < n; j++) {
                if (A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = 0; k < n; k++) {
                        A[j][k] -= A[i][k] * c;
                    }
                    A[j][i] = 0;
                    b[j] -= b[i] * c;
                    Print_Status_System(A,b,fn);
                } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                    float c = 1 / A[j][j];
                    Mul_Elementary_Action(c,j,fn);
                    b[j] /= A[j][j];
                    A[j][j] = 1;
                    Print_Status_System(A,b,fn);
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
    public static float[] Lower_Ranking_Method(float[][] A, float[] b, String fn) {
        if (Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            System.out.println("A is already parallel triangular so now will be change directly to I:");
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            System.out.println("A is already lower triangular so now we'll go directly to the upper ranking:");
            return Upper_Ranking_Method(A,b,fn);
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A)) {
            System.out.println("transform U matrix to I by a lower ranking:");
        } else {
            System.out.println("transform A matrix to L by a lower ranking:");
        }
        int n = A.length;
        for (int i = n - 1; i >= 0; i--) {
            if (A[i][i] == 0) {
                int r = n - 1 - Get_Index_UnZero_Value(A,i);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_System(A,b,i,r);
                Print_Status_System(A,b,fn);
            }
            for (int j = i - 1; j >= 0; j--) {
                if (A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = n - 1; k >= 0; k--) {
                        A[j][k] -= A[i][k] * c;
                    }
                    A[j][i] = 0;
                    b[j] -= b[i] * c;
                    Print_Status_System(A,b,fn);
                } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                    float c = 1 / A[j][j];
                    Mul_Elementary_Action(c,j,fn);
                    b[j] /= A[j][j];
                    A[j][j] = 1;
                    Print_Status_System(A,b,fn);
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
    public static float[] Parallel_Ranking_Method_V1(float[][] A, float[] b, String fn) {
        System.out.println("transform A matrix to I by a parallel ranking:");
        int n = A.length;
        while (!Is_Unit_Matrix(A)) {
            for (int i = 0; i < n; i++) {
                if (A[i][i] == 0) {
                    int r = Get_Index_UnZero_Value(A,i);
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(A,b,i,r);
                    Print_Status_System(A,b,fn);
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && A[j][i] != 0) {
                        float c = A[j][i] / A[i][i];
                        Sum_Elementary_Action(c,j,i,fn);
                        for (int k = 0; k < n; k++) {
                            A[j][k] -= A[i][k] * c;
                        }
                        A[j][i] = 0;
                        b[j] -= b[i] * c;
                        Print_Status_System(A,b,fn);
                    } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                        float c = 1 / A[j][j];
                        Mul_Elementary_Action(c,j,fn);
                        b[j] /= A[j][j];
                        A[j][j] = 1;
                        Print_Status_System(A,b,fn);
                    }
                }
            }
        }
        return b;
    }

    // solve system of linear equations Ax = b by multiplication elementary matrix each iteration
    public static float[] Parallel_Ranking_Method_V2(float[][] A, float[] b, String fn) {
        System.out.println("transform A matrix to I by an elementary matrices:");
        int n = A.length, i = 0, j = 0;
        while (!Is_Unit_Matrix(A)) {
            float[][] E = Unit_Matrix(n);
            if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_System(E,b,i,r);
                A = Mul_Mats(E,A);
                Print_Status_System(A,b,fn);
            } else {
                if (i != j && A[j][i] != 0) {
                    E[j][i] -= (A[j][i] / A[i][i]);
                    Sum_Elementary_Action(-E[j][i],j,i,fn);
                    b[j] += b[i] * E[j][i];
                    A = Mul_Mats(E,A);
                    A[j][i] = 0;
                    Print_Status_System(A,b,fn);
                } else if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                    E[j][j] = 1 / A[j][j];
                    Mul_Elementary_Action(E[j][j],j,fn);
                    b[j] *= E[j][j];
                    A = Mul_Mats(E,A);
                    A[j][j] = 1;
                    Print_Status_System(A,b,fn);
                } if (j == n - 1) {
                    i = (i + 1) % n;
                }
                j = (j + 1) % n;
            }
        }
        return b;
    }

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose action in order to solve a system Ax = b
    public static void Solve_System(float[][] A, float[] b, String fn) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_System();
        int op = sc.nextInt();
        Print_Status_System(A,b,fn);
        float[] x;
        switch (op) {
            case 1:
                x = Invertible_Method(A,b);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 2:
                x = Cramer_Method_V1(A,b);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 3:
                x = Cramer_Method_V2(A,b);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 4:
                x = Forward_Backward_Method(A,b,fn);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 5:
                x = Upper_Ranking_Method(A,b,fn);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 6:
                x = Lower_Ranking_Method(A,b,fn);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 7:
                x = Parallel_Ranking_Method_V1(A,b,fn);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            case 8:
                x = Parallel_Ranking_Method_V2(A,b,fn);
                System.out.print("exist a single solution for the system which is: x = ");
                Print_Solution(x,fn);
                break;
            default:
                throw new Exception("you entered an invalid number");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public static void Check_User_Input(float[][] A, float[] b) throws Exception {
        int m = A.length, n = A[0].length, k = b.length;
        if (m == n && m == k) {
            Display_Exercise(A,b);
            float det = Determinant(A);
            if (Is_Zero_Matrix(A) && Is_Zero_Vector(b)) {
                String ex = "exists an infinite number of solutions to the";
                ex = (n == 1) ? ex + " equation in the space R" + (n) : ex + " system in the space R" + (n);
                throw new Exception(ex);
            } else if ((Is_Zero_Matrix(A) && !Is_Zero_Vector(b)) || det == 0) {
                String ex = "does not an exists solutions for this";
                ex = (n == 1) ? ex + " equation" : ex + " system";
                throw new Exception(ex);
            } else {
                Scanner sc = new Scanner(System.in);
                User_Menu_Solution();
                String fn = sc.next();
                if (fn.equals("d") || fn.equals("r")) {
                    if (n > 1) { // R2 space or higher
                        Solve_System(A,b,fn);
                    } else { // R1 space
                        float c = b[0] / A[0][0];
                        if (c % 1 == 0) {
                            System.out.println("exist a single solution for the equation which is: x = " + (int) c);
                        } else if (fn.equals("d")) {
                            System.out.println("exist a single solution for the equation which is: x = " + c);
                        } else if (fn.equals("r")) {
                            System.out.println("exist a single solution for the equation which is: x = " + convertDecimalToFraction(c));
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

    /////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        float[][] A1 = {{3}};
        float[] a1 = {4};
        float[][] B1 = {{0}};
        float[] b1 = {0};
        float[][] A2 = {{1,-4},{-2,8}};
        float[] a2 = {5,-10};
        float[][] B2 = {{0,1},{1,1}};
        float[] b2 = {1,1};
        float[][] A3 = {{2,1,-1},{-3,-1,2},{-2,1,2}};
        float[] a3 = {8,-11,-3};
        float[][] B3 = {{1,1,2},{1,2,3},{2,2,4}};
        float[] b3 = {0,0,0};
        float[][] C3 = {{1,1,5},{1,2,7},{2,-1,4}};
        float[] c3 = {0,0,0};
        float[][] D3 = {{2,1,1},{-3,-2,-1},{4,-1,5}};
        float[] d3 = {1,-1,5};
        float[][] E3 = {{2,1,3},{10,-1,1},{4,-1,-1}};
        float[] e3 = {10,26,8};
        float[][] F3 = {{0,0,0},{0,0,0},{0,0,0}};
        float[] f3 = {0,0,0};
        float[][] G3 = {{2,1,-1},{-3,-1,2},{-2,1,2}};
        float[] g3 = {0,0,0};
        float[][] H3 = {{0,0,0},{0,0,0},{0,0,0}};
        float[] h3 = {0,0,0};
        float[][] A4 = {{-2,3,3,-2},{-1,4,2,-2},{1,3,1,3},{-3,-2,4,-5}};
        float[] a4 = {8,5,19,-19};
        float[][] A5 = {{-2,2,2,-1,1},{-4,4,4,4,3},{2,3,2,3,2},{-3,-1,1,2,2},{5,5,3,5,5}};
        float[] a5 = {1,31,15,8,52};
        float[][] A6 = {{6,1,3,3,-11,1},{11,-6,5,11,0,-4},{-2,-2,-4,2,-3,0},{2,12,-1,-7,3,0},{5,-11,-11,8,-8,-2},{3,2,2,-1,-1,1}};
        float[] a6 = {-1,41,-30,-8,-27,18};
        float[][] B6 = {{-2,0,-4,-2,5,-6},{6,-1,-2,0,1,0},{6,0,0,-2,5,2},{-2,0,3,2,2,3},{-4,-2,3,-6,4,-4},{5,1,-5,-1,5,-2}};
        float[] b6 = {0,0,0,0,0,0};
        float[][] A7 = {{-1,0,4,-1,-2,5,-1},{6,-7,7,1,-7,-6,5},{0,2,6,1,-7,6,1},{2,2,0,-2,-6,6,-5},{3,3,-3,1,7,-1,-2},{1,-5,1,-4,3,1,-4},{7,-5,5,0,-4,-4,1}};
        float[] a7 = {6,-12,8,20,-3,-4,-6};
        float[][] B7 = {{2,3,1,-4,0,-3,0},{-3,1,1,1,0,-4,-1},{0,1,0,-2,1,-1,1},{-4,1,-3,1,0,-2,1},{1,-3,0,-2,-4,1,0},{1,-2,3,0,-4,-2,-4},{0,4,-4,-2,-3,-2,3}};
        float[] b7 = {0,0,0,0,0,0,0};
        try {
            Check_User_Input(A6,a6);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
