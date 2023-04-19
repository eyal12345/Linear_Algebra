import java.util.Scanner;

public class Invertible_Matrices {

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the matrix M in the matrices format
    public static void Display_Exercise(float[][] M) {
        int n = M.length;
        System.out.println("invert the next matrix (" + n + "*" + n + " size):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    System.out.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } if (j != n - 1) {
                    System.out.print(" ,");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // display current status of the matrices M and InvM each time of iteration on an element
    public static void Print_Status_Matrices(float[][] M, float[][] InvM, String fn) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    System.out.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("d")) {
                    System.out.print(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("r")) {
                    System.out.print(convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    System.out.print(" ,");
                }
            }
            System.out.print(" | ");
            for (int j = 0; j < n; j++) {
                if ((Math.round(InvM[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    System.out.print((int) (Math.round(InvM[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("d")) {
                    System.out.print(Math.round(InvM[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("r")) {
                    System.out.print(convertDecimalToFraction(InvM[i][j]));
                } if (j != n - 1) {
                    System.out.print(" ,");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // show the resulting solution as a matrix representation
    public static void Print_Solution(float[][] M, String fn) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    System.out.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("d")) {
                    System.out.print(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("r")) {
                    System.out.print(convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    System.out.print(" ,");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    public static void User_Menu_System() {
        System.out.println("choose number method to solution:");
        System.out.println("1. invert a matrix by lower ranking");
        System.out.println("2. invert a matrix by upper ranking");
        System.out.println("3. invert a matrix by parallel ranking");
        System.out.println("4. invert a matrix by parallel ranking with elementary matrices");
        System.out.println("5. invert a matrix by parallel ranking with elementary matrices (recursive)");
        System.out.println("6. invert a matrix by formula: Inv(M) = (1/|M|) * Adj(M)");
        System.out.println("7. invert a matrix by formula: Inv(M) = (1/|M|) * Adj(M) with fast performance");
    }

    // display user interface by selection format for solution
    public static void User_Menu_Solution() {
        System.out.println("choose the character of format to representation of solution:");
        System.out.println("d. decimal");
        System.out.println("r. rational");
    }

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if a matrix is a unit matrix
    public static boolean Is_Unit_Matrix(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][i] != 1 || (i != j && M[i][j] != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the specific row in the matrix is a unit vector
    public static boolean Is_Unit_Vector(float[][] M, int k) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n && M[k][i] != 0; j++) {
                if (i != j && M[k][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the matrix is an upper triangular
    public static boolean Is_Upper_Triangular(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (M[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the matrix is a lower triangular
    public static boolean Is_Lower_Triangular(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    public static int Get_Index_UnZero_Value(float[][] M, int k) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[k][i] != 0) {
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

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // calculate determinant of a matrix
    public static float Determinant(float[][] M) {
        int n = M.length;
        if (n == 1) {
            return M[0][0];
        } else {
            float sum = 0;
            for (int i = 0; i < n; i++) {
                sum += Math.pow(-1,i) * M[0][i] * Determinant(Sub_Matrix(M,0,i));
            }
            return sum;
        }
    }

    // calculate of sub-matrix from a matrix by cutting row "x" and column "y"
    public static float[][] Sub_Matrix(float[][] M, int x, int y) {
        int n = M.length, p = 0, q = 0;
        float[][] subM = new float[n - 1][n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != x && j != y) {
                    subM[p][q] = M[i][j];
                    q++;
                    if (q == n - 1) {
                        p++;
                        q = 0;
                    }
                }
            }
        }
        return subM;
    }

    // calculate adjoint matrix of a matrix
    public static float[][] Adjoint(float[][] M) {
        int n = M.length;
        float[][] Adj = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Adj[j][i] = (float) Math.pow(-1,i + j) * Determinant(Sub_Matrix(M,i,j));
            }
        }
        return Adj;
    }

    // replace between two rows in a matrix
    public static void Retreat_Rows_Matrix(float[][] M, int r1, int r2) {
        int n = M.length;
        for (int j = 0; j < n; j++) {
            float t = M[r1][j];
            M[r1][j] = M[r2][j];
            M[r2][j] = t;
        }
    }

    // replace between two rows in a matrices
    public static void Retreat_Rows_Matrices(float[][] M, float[][] invM, int r1, int r2) {
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

    // calculate multiplication between two matrices
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

    ////////////////////////////////////////////////// Convertor /////////////////////////////////////////////////
    // convert a value to a format of rational number
    public static String convertDecimalToFraction(float x) {
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
    // show elementary actions for sum between rows in the matrices
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

    // show elementary actions for multiplication of a row in the matrices
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
                System.out.println("R" + r + " --> " + k + "*R" + r + "\n");
            } else if (fn.equals("r")) {
                System.out.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r + "\n");
            }
        }
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // invert the M matrix by an upper ranking and then a lower ranking
    public static float[][] Upper_Ranking_Method(float[][] M, float[][] InvM, String fn) {
        if (Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
            System.out.println("M is already parallel triangular so now will be change directly to I:");
        } else if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M)) {
            System.out.println("M is already upper triangular so now we'll go directly to the lower ranking:");
            return Lower_Ranking_Method(M,InvM,fn);
        } else if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
            System.out.println("transform L matrix to I by an upper ranking:");
        } else {
            System.out.println("transform M matrix to U by an upper ranking:");
        }
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] == 0) {
                int r = Get_Index_UnZero_Value(M,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrices(M,InvM,i,r);
                Print_Status_Matrices(M,InvM,fn);
            }
            for (int j = i + 1; j < n; j++) {
                if (M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Print_Status_Matrices(M,InvM,fn);
                } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j,fn);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Print_Status_Matrices(M,InvM,fn);
                }
            }
            if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M)) {
                System.out.print("and now ");
                return Lower_Ranking_Method(M,InvM,fn);
            }
        }
        if (!Is_Lower_Triangular(M) || !Is_Unit_Matrix(M)) {
            System.out.println("still not yet received an unit matrix");
            return Lower_Ranking_Method(M,InvM,fn);
        }
        return InvM;
    }

    // invert the M matrix by a lower ranking and then an upper ranking
    public static float[][] Lower_Ranking_Method(float[][] M, float[][] InvM, String fn) {
        if (Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
            System.out.println("M is already parallel triangular so now will be change directly to I:");
        } else if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
            System.out.println("M is already lower triangular so now we'll go directly to the upper ranking:");
            return Upper_Ranking_Method(M,InvM,fn);
        } else if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M)) {
            System.out.println("transform U matrix to I by a lower ranking:");
        } else {
            System.out.println("transform M matrix to L by a lower ranking:");
        }
        int n = M.length;
        for (int i = n - 1; i >= 0; i--) {
            if (M[i][i] == 0) {
                int r = n - 1 - Get_Index_UnZero_Value(M,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrices(M,InvM,i,r);
                Print_Status_Matrices(M,InvM,fn);
            }
            for (int j = i - 1; j >= 0; j--) {
                if (M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = n - 1; k >= 0; k--) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Print_Status_Matrices(M,InvM,fn);
                } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j,fn);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Print_Status_Matrices(M,InvM,fn);
                }
            }
            if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
                System.out.print("and now ");
                return Upper_Ranking_Method(M,InvM,fn);
            }
        }
        if (!Is_Upper_Triangular(M) || !Is_Unit_Matrix(M)) {
            System.out.println("still not yet received an unit matrix");
            return Upper_Ranking_Method(M,InvM,fn);
        }
        return InvM;
    }

    // invert the M matrix by parallel ranking
    public static float[][] Invertible(float[][] M, String fn) {
        System.out.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        float[][] InvM = Unit_Matrix(n);
        for (int i = 0; i < n; i++) {
            if (M[i][i] == 0) {
                int r = Get_Index_UnZero_Value(M,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrices(M,InvM,i,r);
                Print_Status_Matrices(M,InvM,fn);
            }
            for (int j = 0; j < n; j++) {
                if (i != j && M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Print_Status_Matrices(M,InvM,fn);
                } if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Action(c,j,fn);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Print_Status_Matrices(M,InvM,fn);
                }
            }
        }
        return InvM;
    }

    // invert the M matrix in parallel ranking by a multiplication of M in elementary matrix each iteration
    public static float[][] Invertible_Elementary(float[][] M, String fn) {
        System.out.println("transform M matrix to I by an elementary matrices:");
        int n = M.length, i = 0 ,j = 0;
        float[][] InvM = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            float[][] E = Unit_Matrix(n);
            if (M[i][i] == 0) {
                int r = Get_Index_UnZero_Value(M,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrix(E,i,r);
                M = Mul_Mats(E,M);
                InvM = Mul_Mats(E,InvM);
                Print_Status_Matrices(M,InvM,fn);
            } else {
                if (i != j && M[j][i] != 0) {
                    E[j][i] -= (M[j][i] / M[i][i]);
                    Sum_Elementary_Action(-E[j][i],j,i,fn);
                    M = Mul_Mats(E,M);
                    InvM = Mul_Mats(E,InvM);
                    M[j][i] = 0;
                    Print_Status_Matrices(M,InvM,fn);
                } else if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    E[j][j] = 1 / M[j][j];
                    Mul_Elementary_Action(E[j][j],j,fn);
                    M = Mul_Mats(E,M);
                    InvM = Mul_Mats(E,InvM);
                    M[j][j] = 1;
                    Print_Status_Matrices(M,InvM,fn);
                } if (j == n - 1) {
                    i = (i + 1) % n;
                }
                j = (j + 1) % n;
            }
        }
        return InvM;
    }

    // invert the M matrix in parallel ranking by a multiplication of M in elementary matrix each iteration (recursive)
    public static float[][] Invertible_Elementary_Rec(float[][] M, float[][] InvM, int i, int j, String fn) {
        int n = M.length;
        if (Is_Unit_Matrix(M)) {
            return InvM;
        } else {
            float[][] E = Unit_Matrix(n);
            if (M[i][i] == 0) {
                int r = Get_Index_UnZero_Value(M,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrix(E,i,r);
                M = Mul_Mats(E,M);
                InvM = Mul_Mats(E,InvM);
                Print_Status_Matrices(M,InvM,fn);
            } else {
                if (i != j && M[j][i] != 0) {
                    E[j][i] -= (M[j][i] / M[i][i]);
                    Sum_Elementary_Action(-E[j][i],j,i,fn);
                    M = Mul_Mats(E,M);
                    InvM = Mul_Mats(E,InvM);
                    M[j][i] = 0;
                    Print_Status_Matrices(M,InvM,fn);
                } else if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    E[j][j] = 1 / M[j][j];
                    Mul_Elementary_Action(E[j][j],j,fn);
                    M = Mul_Mats(E,M);
                    InvM = Mul_Mats(E,InvM);
                    M[j][j] = 1;
                    Print_Status_Matrices(M,InvM,fn);
                } if (j == n - 1)
                    i = (i + 1) % n;
                j = (j + 1) % n;
            }
            return Invertible_Elementary_Rec(M,InvM,i,j,fn);
        }
    }

    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    public static float[][] Invertible_Direct(float[][] M) {
        float det = Determinant(M);
        int n = M.length;
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
    public static void Invert_Matrix(float[][] M, String fn) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_System();
        int op = sc.nextInt();
        Print_Status_Matrices(M,Unit_Matrix(M.length),fn);
        switch (op) {
            case 1:
                M = Lower_Ranking_Method(M,Unit_Matrix(M.length),fn);
                System.out.println("the invertible of this matrix is:");
                Print_Solution(M,fn);
                break;
            case 2:
                M = Upper_Ranking_Method(M,Unit_Matrix(M.length),fn);
                System.out.println("the invertible of this matrix is:");
                Print_Solution(M,fn);
                break;
            case 3:
                M = Invertible(M,fn);
                System.out.println("the invertible of this matrix is:");
                Print_Solution(M,fn);
                break;
            case 4:
                M = Invertible_Elementary(M,fn);
                System.out.println("the invertible of this matrix is:");
                Print_Solution(M,fn);
                break;
            case 5:
                M = Invertible_Elementary_Rec(M,Unit_Matrix(M.length),0,0,fn);
                System.out.println("the invertible of this matrix is:");
                Print_Solution(M,fn);
                break;
            case 6:
                M = Invertible_Direct(M);
                System.out.println("the invertible of this matrix is:");
                Print_Solution(M,fn);
                break;
            default:
                throw new Exception("you entered an invalid number");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public static void Check_User_Input(float[][] M) throws Exception {
        int m = M.length, n = M[0].length;
        if (m == n) {
            Display_Exercise(M);
            float det = Determinant(M);
            if (det == 0) {
                throw new Exception("this is a singular matrix");
            } else {
                Scanner sc = new Scanner(System.in);
                User_Menu_Solution();
                String fn = sc.next();
                if (fn.equals("d") || fn.equals("r")) {
                    if (n > 1) { // 2*2 size or higher
                        Invert_Matrix(M,fn);
                    } else { // 1*1 size
                        float c = 1 / M[0][0];
                        if (c % 1 == 0) {
                            System.out.println("the invertible of this matrix is: " + (int) c);
                        } else if (fn.equals("d")) {
                            System.out.println("the invertible of this matrix is: " + c);
                        } else if (fn.equals("r")) {
                            System.out.println("the invertible of this matrix is: " + convertDecimalToFraction(c));
                        }
                    }
                } else {
                    throw new Exception("you entered invalid value for a representation elementary actions and solution");
                }
            }
        } else {
            throw new Exception("your input does not meet the conditions for invertible matrices");
        }
    }

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        float[][] A1 = {{2,1,-1},{-3,-1,2},{-2,1,2}};
        float[][] A2 = {{1,-1,-2},{2,-3,-5},{-1,3,5}};
        float[][] A3 = {{1,1,5},{1,2,7},{2,-1,4}};
        float[][] A4 = {{1,2,-1},{2,4,-3},{-1,-2,0}};
        float[][] A5 = {{1,1,5},{-2,-2,-10},{6,6,30}};
        float[][] B = {{-2,2,2,-1,1},{-4,4,4,4,3},{2,3,2,3,2},{-3,-1,1,2,2},{5,5,3,5,5}};
        float[][] C = {{-2,3,3,-2},{-1,4,2,-2},{1,3,1,3},{-3,-2,4,-5}};
        float[][] D = {{2,10,7,-5,0,8},{-12,-8,-9,-1,-11,-5},{-1,8,0,-4,-9,11},{12,6,-10,5,-11,-12},{12,11,-10,-2,-4,-12},{5,-5,3,6,8,-10}};
        float[][] E = {{1,1,-2,0,-1,-1},{0,-2,-1,2,-2,2},{0,0,-1,-1,-1,1},{0,0,-2,2,-2,0},{-2,-2,0,0,-2,2},{1,0,-1,0,0,2}};
        float[][] F = {{-2,0,-4,-2,5,-6},{6,-1,-2,0,1,0},{6,0,0,-2,5,2},{-4,0,6,4,4,6},{-4,-2,3,-6,4,-4},{5,1,-5,-1,5,-2}};
        float[][] G = {{-1,0,4,-1,-2,5,-1},{6,-7,7,1,-7,-6,5},{0,2,6,1,-7,6,1},{2,2,0,-2,-6,6,-5},{3,3,-3,1,7,-1,-2},{1,-5,1,-4,3,1,-4},{7,-5,5,0,-4,-4,1}};
        float[][] Z = {{8}};
        try {
            Check_User_Input(C);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
