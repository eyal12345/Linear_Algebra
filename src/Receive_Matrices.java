import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Receive_Matrices extends ShareTools {
    private float[][] L;
    private final PrintWriter fr;

    public Receive_Matrices(float[][] nL) {
        L = nL;
        try {
            int n = L[0].length;
            LocalDateTime cur = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File file = new File("Receive Matrices (" + n + "x" + n + " size) " + cur.format(formatter) + ".txt");
            fr = new PrintWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the matrix M in the matrices format
    private void Write_Exercise(float[][] M) {
        int n = M.length;
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

    // display a matrix each current status
    private void Write_Matrix(float[][] M, String fn) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("d")) {
                    fr.print(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("r")) {
                    fr.print(convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for receive matrices
    private static void User_Menu_System_Receive() {
        System.out.println("choose number method to solution:");
        System.out.println("1. LU decomposition by L and U multiplication (first method)");
        System.out.println("2. LL' decomposition by L and L' multiplication (first method)");
        System.out.println("3. LDL' decomposition by L, D and L' multiplication (first method)");
        System.out.println("4. LU decomposition by L and U multiplication (second method)");
        System.out.println("5. LL' decomposition by L and L' multiplication (second method)");
        System.out.println("6. LDL' decomposition by L, D and L' multiplication (second method)");
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

    /////////////////////////////////////// Methods to Solution (Receive M) /////////////////////////////////////////
    // get the LU decomposition by multiplication of L and U (first algorithm)
    private float[][] From_LU_To_M_V1(float[][] L, float[][] U, String fn) throws Exception {
        fr.println("U = ");
        Write_Matrix(U,fn);
        if (Is_One_Slant(L)) {
            return Mul_Mats(L,U);
        } else {
            throw new Exception("you entered values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (first algorithm)
    private float[][] From_LLT_To_M_V1(float[][] L, String fn) {
        float[][] LT = Transpose(L);
        fr.println("L' = ");
        Write_Matrix(LT,fn);
        return Mul_Mats(L,LT);
    }

    // get the LDL' decomposition by multiplication of L, D and L' (first algorithm)
    private float[][] From_LDLT_To_M_V1(float[][] L, float[][] D, String fn) throws Exception {
        fr.println("D = ");
        Write_Matrix(D,fn);
        float[][] LT = Transpose(L);
        fr.println("L' = ");
        Write_Matrix(LT,fn);
        if (Is_One_Slant(L)) {
            return Mul_Mats(Mul_Mats(L,D),LT);
        } else {
            throw new Exception("you entered values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LU decomposition by multiplication of L and U (second algorithm)
    private float[][] From_LU_To_M_V2(float[][] L, float[][] U, String fn) throws Exception {
        fr.println("U = ");
        Write_Matrix(U,fn);
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
    private float[][] From_LLT_To_M_V2(float[][] L, String fn) {
        float[][] LT = Transpose(L);
        fr.println("L' = ");
        Write_Matrix(LT,fn);
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
    private float[][] From_LDLT_To_M_V2(float[][] L, float[][] D, String fn) throws Exception {
        fr.println("D = ");
        Write_Matrix(D,fn);
        float[][] LT = Transpose(L);
        fr.println("L' = ");
        Write_Matrix(LT,fn);
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
    public void Receive_Matrix(float[][] L) throws Exception {
        int n = L.length;
        if (Is_Lower_Triangular(L)) {
            fr.println("receive the M matrix from the L and another matrices (" + n + "*" + n + " size):");
            Write_Exercise(L);
            Scanner sc = new Scanner(System.in);
            User_Menu_Solution();
            String fn = sc.next();
            if (fn.equals("d") || fn.equals("r")) {
                User_Menu_System_Receive();
                int op = sc.nextInt();
                fr.println("L = ");
                Write_Matrix(L,fn);
                float[][] M;
                switch (op) {
                    case 1:
                        M = From_LU_To_M_V1(L,Create_Upper_Matrix(n),fn);
                        break;
                    case 2:
                        M = From_LLT_To_M_V1(L,fn);
                        break;
                    case 3:
                        M = From_LDLT_To_M_V1(L,Create_Diagonal_Matrix(n),fn);
                        break;
                    case 4:
                        M = From_LU_To_M_V2(L,Create_Upper_Matrix(n),fn);
                        break;
                    case 5:
                        M = From_LLT_To_M_V2(L,fn);
                        break;
                    case 6:
                        M = From_LDLT_To_M_V2(L,Create_Diagonal_Matrix(n),fn);
                        break;
                    default:
                        throw new Exception("you entered an invalid number");
                }
                fr.println("M = ");
                Write_Matrix(M,fn);
                fr.close();
            } else {
                throw new Exception("you entered invalid values for the L matrix");
            }
        } else {
            throw new Exception("you entered matrix which is not a lower triangular");
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Check_User_Input() throws Exception {
        int m = L.length, n = L[0].length;
        if (m == n) {
            Receive_Matrix(L);
        } else {
            throw new Exception("you entered matrix which is not a square matrix");
        }
    }
}
