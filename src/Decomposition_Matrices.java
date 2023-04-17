import java.util.Scanner;

public class Decomposition_Matrices {

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the matrix M in the matrices format
    public static void Display_Exercise(float[][] M) {
        int n = M.length;
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

    // display a matrix each current status
    public static void Print_Matrix(float[][] M, String fn) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0)
                    System.out.print((int)(Math.round(M[i][j] * 1000.0) / 1000.0));
                else if (fn.equals("d")) {
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

    // display user interface by selection method for decompose matrices
    public static void User_Menu_System_Decompose() {
        System.out.println("choose number method to solution:");
        System.out.println("1. get L and U matrices by decomposition of M (first method)");
        System.out.println("2. get L and L' matrices by decomposition of M (first method)");
        System.out.println("3. get L, D and L' matrices by decomposition of M (first method)");
        System.out.println("4. get L and U matrices by decomposition of M (second method)");
        System.out.println("5. get L and L' matrices by decomposition of M (second method)");
        System.out.println("6. get L, D and L' matrices by decomposition of M (second method)");
    }

    // display user interface by selection method for receive matrices
    public static void User_Menu_System_Receive() {
        System.out.println("choose number method to solution:");
        System.out.println("1. LU decomposition by L and U multiplication (first method)");
        System.out.println("2. LL' decomposition by L and L' multiplication (first method)");
        System.out.println("3. LDL' decomposition by L, D and L' multiplication (first method)");
        System.out.println("4. LU decomposition by L and U multiplication (second method)");
        System.out.println("5. LL' decomposition by L and L' multiplication (second method)");
        System.out.println("6. LDL' decomposition by L, D and L' multiplication (second method)");
    }

    // display user interface by selection format for solution
    public static void User_Menu_Solution() {
        System.out.println("choose a character of format to representation of solution:");
        System.out.println("d. decimal");
        System.out.println("r. rational");
    }

    // display user interface by direction of the solution
    public static void User_Menu_Direction() {
        System.out.println("choose a character for direction of the solution:");
        System.out.println("d. get the components by M decomposition");
        System.out.println("r. get M by multiplication of the components");
    }

    /////////////////////////////////////////// Auxiliary Operations /////////////////////////////////////////////
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

    // check if the matrix is a symmetrical matrix
    public static boolean Is_Symmetrical_Matrix(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (M[i][j] != M[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if all the values in the main diagonal which are positive
    public static boolean Is_Values_Positives(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] < 0) {
                return false;
            }
        }
        return true;
    }

    // check if all the values in the main diagonal which are equals to 1
    public static boolean Is_One_Slant(float[][] M) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            if (M[i][i] != 1) {
                return false;
            }
        }
        return true;
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

    // calculate transpose matrix of a matrix
    public static float[][] Transpose(float[][] M) {
        int n = M.length;
        float[][] MT = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                MT[j][i] = M[i][j];
            }
        }
        return MT;
    }

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

    /////////////////////////////////////////////// Input Matrices ///////////////////////////////////////////////
    // create diagonal matrix
    public static float[][] Create_Diagonal_Matrix(int n) {
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
    public static float[][] Create_Upper_Matrix(int n) {
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
                        System.out.println("R" + r + " --> R" + r + " + " + (int) ((-1) * k) + "*R" + c + "\n");
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

    ////////////////////////////////////// Methods to Solution (Decompose M) ////////////////////////////////////////
    // get the LU decomposition of M (first algorithm)
    public static float[][] From_M_To_LU_V1(float[][] M, String fn) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        System.out.println("transform M matrix to U by an upper ranking:");
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                L[j][i] = M[j][i] / M[i][i];
                Sum_Elementary_Action(L[j][i],j,i,fn);
                for (int k = 0; k < n; k++) {
                    M[j][k] -= M[i][k] * L[j][i];
                }
                M[j][i] = 0;
                if (L[j][i] != 0) {
                    Print_Matrix(M,fn);
                }
            }
            for (int k = i; k < n; k++) {
                U[i][k] = M[i][k];
            }
        }
        System.out.println("L = ");
        Print_Matrix(L,fn);
        return U;
    }

    // get the LL' decomposition of M (first algorithm)
    public static float[][] From_M_To_LLT_V1(float[][] M, String fn) throws Exception {
        if (Is_Symmetrical_Matrix(M) && Is_Values_Positives(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] LT = new float[n][n];
            System.out.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n && M[i][i] != 0; i++) {
                for (int j = i + 1; j < n; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        Print_Matrix(M,fn);
                    }
                }
                float c = (float) Math.sqrt(M[i][i]);
                Mul_Elementary_Action(1 / c,i,fn);
                for (int k = i; k < n; k++) {
                    M[i][k] /= c;
                    L[k][i] = M[i][k];
                    LT[i][k] = L[k][i];
                }
                if (c != 1) {
                    Print_Matrix(M,fn);
                }
            }
            System.out.println("L = ");
            Print_Matrix(L,fn);
            return LT;
        } else {
            throw new Exception("you entered matrix which is not a symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (first algorithm)
    public static float[][] From_M_To_LDLT_V1(float[][] M, String fn) throws Exception {
        if (Is_Symmetrical_Matrix(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] D = new float[n][n];
            float[][] LT = new float[n][n];
            System.out.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n; i++) {
                L[i][i] = 1;
                for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Action(c,j,i,fn);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        Print_Matrix(M,fn);
                    }
                }
                D[i][i] = M[i][i];
                if (D[i][i] != 0) {
                    Mul_Elementary_Action(1 / D[i][i],i,fn);
                    for (int k = i; k < n; k++) {
                        M[i][k] /= D[i][i];
                        L[k][i] = M[i][k];
                        LT[i][k] = L[k][i];
                    }
                    if (D[i][i] != 1) {
                        Print_Matrix(M,fn);
                    }
                } else {
                    LT[i][i] = 1;
                }
            }
            System.out.println("L = ");
            Print_Matrix(L,fn);
            System.out.println("D = ");
            Print_Matrix(D,fn);
            return LT;
        } else {
            throw new Exception("you entered matrix which is not a symmetrical matrix");
        }
    }

    // get the LU decomposition of M (second algorithm)
    public static float[][] From_M_To_LU_V2(float[][] M, String fn) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = 0; j < n; j++) {
                int m = Math.min(i,j);
                for (int k = 0 ;k < m ;k++) {
                    M[i][j] -= L[i][k] * U[k][j];
                }
                if (i <= j || U[j][j] == 0) {
                    U[i][j] = M[i][j];
                } else {
                    L[i][j] = M[i][j] / U[j][j];
                }
            }
        }
        System.out.println("L = ");
        Print_Matrix(L,fn);
        return U;
    }

    // get the LL' decomposition of M (second algorithm)
    public static float[][] From_M_To_LLT_V2(float[][] M, String fn) throws Exception {
        if (Is_Symmetrical_Matrix(M) && Is_Values_Positives(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] LT = new float[n][n];
            for (int j = 0; j < n; j++) {
                for (int i = j; i < n; i++) {
                    for (int k = 0; k < j; k++) {
                        M[i][j] -= L[i][k] * LT[k][j];
                    }
                    L[i][j] = M[i][j] / (float) Math.sqrt(M[j][j]);
                    LT[j][i] = L[i][j];
                }
            }
            System.out.println("L = ");
            Print_Matrix(L,fn);
            return LT;
        } else {
            throw new Exception("you entered matrix which is not a symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (second algorithm)
    public static float[][] From_M_To_LDLT_V2(float[][] M, String fn) throws Exception {
        if (Is_Symmetrical_Matrix(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] D = new float[n][n];
            float[][] LT = new float[n][n];
            for (int j = 0; j < n; j++) {
                L[j][j] = 1;
                for (int i = j; i < n; i++) {
                    for (int k = 0; k < j; k++) {
                        M[i][j] -= L[i][k] * D[k][k] * LT[k][j];
                    }
                    if (M[j][j] != 0) {
                        L[i][j] = M[i][j] / M[j][j];
                        LT[j][i] = L[i][j];
                    } else {
                        LT[i][i] = 1;
                    }
                }
                D[j][j] = M[j][j];
            }
            System.out.println("L = ");
            Print_Matrix(L,fn);
            System.out.println("D = ");
            Print_Matrix(D,fn);
            return LT;
        } else {
            throw new Exception("you entered matrix which is not a symmetrical matrix");
        }
    }

    ////////////////////////////////////// User Interface (Decompose M) //////////////////////////////////////
    // get the matrix components
    public static void Decompose_Matrix(float[][] M) throws Exception {
        int n = M.length;
        System.out.println("decompose the next matrix (" + n + "*" + n + " size):");
        Display_Exercise(M);
        Scanner sc = new Scanner(System.in);
        User_Menu_Solution();
        String fn = sc.next();
        if (fn.equals("d") || fn.equals("r")) {
            User_Menu_System_Decompose();
            int op = sc.nextInt();
            System.out.println("M = ");
            Print_Matrix(M,fn);
            float[][] M2;
            switch (op) {
                case 1:
                    M2 = From_M_To_LU_V1(M,fn);
                    System.out.println("U = ");
                    Print_Matrix(M2,fn);
                    break;
                case 2:
                    M2 = From_M_To_LLT_V1(M,fn);
                    System.out.println("L' = ");
                    Print_Matrix(M2,fn);
                    break;
                case 3:
                    M2 = From_M_To_LDLT_V1(M,fn);
                    System.out.println("L' = ");
                    Print_Matrix(M2,fn);
                    break;
                case 4:
                    M2 = From_M_To_LU_V2(M,fn);
                    System.out.println("U = ");
                    Print_Matrix(M2,fn);
                    break;
                case 5:
                    M2 = From_M_To_LLT_V2(M,fn);
                    System.out.println("L' = ");
                    Print_Matrix(M2,fn);
                    break;
                case 6:
                    M2 = From_M_To_LDLT_V2(M,fn);
                    System.out.println("L' = ");
                    Print_Matrix(M2,fn);
                    break;
                default:
                    throw new Exception("you entered an invalid number");
            }
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }

    /////////////////////////////////////// Methods to Solution (Receive M) /////////////////////////////////////////
    // get the LU decomposition by multiplication of L and U (first algorithm)
    public static float[][] From_LU_To_M_V1(float[][] L, float[][] U, String fn) throws Exception {
        System.out.println("U = ");
        Print_Matrix(U,fn);
        if (Is_One_Slant(L)) {
            return Mul_Mats(L,U);
        } else {
            throw new Exception("you entered values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (first algorithm)
    public static float[][] From_LLT_To_M_V1(float[][] L, float[][] LT, String fn) {
        System.out.println("L' = ");
        Print_Matrix(LT,fn);
        return Mul_Mats(L,LT);
    }

    // get the LDL' decomposition by multiplication of L, D and L' (first algorithm)
    public static float[][] From_LDLT_To_M_V1(float[][] L, float[][] D, float[][] LT, String fn) throws Exception {
        System.out.println("D = ");
        Print_Matrix(D,fn);
        System.out.println("L' = ");
        Print_Matrix(LT,fn);
        if (Is_One_Slant(L)) {
            return Mul_Mats(Mul_Mats(L,D),LT);
        } else {
            throw new Exception("you entered values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LU decomposition by multiplication of L and U (second algorithm)
    public static float[][] From_LU_To_M_V2(float[][] L, float[][] U, String fn) throws Exception {
        System.out.println("U = ");
        Print_Matrix(U,fn);
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
            return M;
        } else {
            throw new Exception("you entered values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (second algorithm)
    public static float[][] From_LLT_To_M_V2(float[][] L, float[][] LT, String fn) {
        System.out.println("L' = ");
        Print_Matrix(LT,fn);
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
        return M;
    }

    // get the LDL' decomposition by multiplication of L, D and L' (second algorithm)
    public static float[][] From_LDLT_To_M_V2(float[][] L, float[][] D, float[][] LT, String fn) throws Exception {
        System.out.println("D = ");
        Print_Matrix(D,fn);
        System.out.println("L' = ");
        Print_Matrix(LT,fn);
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
            return M;
        } else {
            throw new Exception("you entered values other than 1 on the main diagonal of L matrix");
        }
    }

    /////////////////////////////////////// User Interface (Receive M) ///////////////////////////////////////
    // get the matrix by a multiplication components
    public static void Receive_Matrix(float[][] L) throws Exception {
        int n = L.length;
        if (Is_Lower_Triangular(L)) {
            System.out.println("receive the M matrix from the L and another matrices (" + n + "*" + n + " size):");
            Display_Exercise(L);
            Scanner sc = new Scanner(System.in);
            User_Menu_Solution();
            String fn = sc.next();
            if (fn.equals("d") || fn.equals("r")) {
                User_Menu_System_Receive();
                int op = sc.nextInt();
                System.out.println("L = ");
                Print_Matrix(L,fn);
                float[][] M, M2, M3;
                switch (op) {
                    case 1:
                        M2 = Create_Upper_Matrix(n);
                        M = From_LU_To_M_V1(L,M2,fn);
                        System.out.println("M = ");
                        Print_Matrix(M,fn);
                        break;
                    case 2:
                        M2 = Transpose(L);
                        M = From_LLT_To_M_V1(L,M2,fn);
                        System.out.println("M = ");
                        Print_Matrix(M,fn);
                        break;
                    case 3:
                        M2 = Create_Diagonal_Matrix(n);
                        M3 = Transpose(L);
                        M = From_LDLT_To_M_V1(L,M2,M3,fn);
                        System.out.println("M = ");
                        Print_Matrix(M,fn);
                        break;
                    case 4:
                        M2 = Create_Upper_Matrix(n);
                        M = From_LU_To_M_V2(L,M2,fn);
                        System.out.println("M = ");
                        Print_Matrix(M,fn);
                        break;
                    case 5:
                        M2 = Transpose(L);
                        M = From_LLT_To_M_V2(L,M2,fn);
                        System.out.println("M = ");
                        Print_Matrix(M,fn);
                        break;
                    case 6:
                        M2 = Create_Diagonal_Matrix(n);
                        M3 = Transpose(L);
                        M = From_LDLT_To_M_V2(L,M2,M3,fn);
                        System.out.println("M = ");
                        Print_Matrix(M,fn);
                        break;
                    default:
                        throw new Exception("you entered an invalid number");
                }
            } else {
                throw new Exception("you entered invalid values for the L matrix");
            }
        } else {
            throw new Exception("you entered matrix which is not a lower triangular");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public static void Check_User_Input(float[][] M) throws Exception {
        int m = M.length, n = M[0].length;
        if (m == n) {
            Scanner sc = new Scanner(System.in);
            User_Menu_Direction();
            String de = sc.next();
            if (de.equals("d")) {
                Decompose_Matrix(M);
            } else if (de.equals("r")) {
                Receive_Matrix(M);
            } else {
                throw new Exception("you entered invalid value for a direction of the solution");
            }
        } else {
            throw new Exception("you entered matrix which is not a square matrix");
        }
    }

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {
        ///////////////////////// Decompose M /////////////////////////
        float[][] LU = {{2,1,-1},{-3,-1,2},{-2,1,2}};
        float[][] LU2 = {{2,1,0},{6,0,4},{-8,5,0}};
        float[][] LU3 = {{2,0,0},{6,4,0},{-8,5,0}};
        float[][] LU4 = {{2,0,0},{6,0,0},{-8,5,0}};
        float[][] LLT = {{4,12,-16},{12,37,-43},{-16,-43,98}};
        float[][] LLT2 = {{4,-2,2},{-2,17,11},{2,11,35}};
        float[][] LLT3 = {{1,3,-4},{3,10,-7},{-4,-7,42}};
        float[][] LDLT = {{-1,-3,4},{-3,-5,32},{4,32,75}};
        float[][] LDLT2 = {{4,12,-16},{12,52,-28},{-16,-28,89}};
        float[][] LDLT3 = {{4,24,-32},{24,144,-192},{-32,-192,256}};
        ////////////////////////// Receive M //////////////////////////
        float[][] L = {{1,0,0},{-2,1,0},{-1,4,1}};
        float[][] L2 = {{1,0,0},{3,1,0},{-4,5,1}};
        float[][] L3 = {{2,0,0},{6,1,0},{-8,5,3}};
        float[][] L4 = {{1,0,0},{6,1,0},{-8,5,1}};
        try {
            Check_User_Input(LLT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
