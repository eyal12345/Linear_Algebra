package Features.Mathematical_Matrices;

import Features.MenuActions;
import Features.ShareTools;
import java.io.PrintWriter;

public class Decompose_Matrices extends ShareTools implements MenuActions {
    private final float[][] M;

    public Decompose_Matrices(float[][] nM, String method, String format, PrintWriter writer) {
        super(method, format, writer);
        this.M = nM;
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    // get the LU decomposition of M (first algorithm)
    private void from_M_To_LU_V1(float[][] M) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        writer.println("transform M matrix to U by an upper ranking:");
        for (int i = 0; i < n; i++) {
            L[i][i] = 1;
            for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                L[j][i] = M[j][i] / M[i][i];
                Sum_Elementary_Description(L[j][i],j,i);
                for (int k = 0; k < n; k++) {
                    M[j][k] -= M[i][k] * L[j][i];
                }
                M[j][i] = 0;
                if (L[j][i] != 0) {
                    writer.println(Display_Status_Matrix(M,format));
                }
            }
            for (int k = i; k < n; k++) {
                U[i][k] = M[i][k];
            }
        }
        writer.println("L = ");
        writer.println(Display_Status_Matrix(L,format));
        writer.println("U = ");
        writer.println(Display_Status_Matrix(U,format));
    }

    // get the LL' decomposition of M (first algorithm)
    private void from_M_To_LLT_V1(float[][] M) {
        if (Is_Symmetrical_Matrix(M) && Is_Values_Positives(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] LT = new float[n][n];
            writer.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n && M[i][i] != 0; i++) {
                for (int j = i + 1; j < n; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Description(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        writer.println(Display_Status_Matrix(M,format));
                    }
                }
                float c = (float) Math.sqrt(M[i][i]);
                Mul_Elementary_Description(1 / c,i);
                for (int k = i; k < n; k++) {
                    M[i][k] /= c;
                    L[k][i] = M[i][k];
                    LT[i][k] = L[k][i];
                }
                if (c != 1) {
                    writer.println(Display_Status_Matrix(M,format));
                }
            }
            writer.println("L = ");
            writer.println(Display_Status_Matrix(L,format));
            writer.println("L' = ");
            writer.println(Display_Status_Matrix(LT,format));
        } else {
            writer.println("this is a matrix which is not symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (first algorithm)
    private void from_M_To_LDLT_V1(float[][] M) {
        if (Is_Symmetrical_Matrix(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] D = new float[n][n];
            float[][] LT = new float[n][n];
            writer.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n; i++) {
                L[i][i] = 1;
                for (int j = i + 1; j < n && M[i][i] != 0; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Description(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        writer.println(Display_Status_Matrix(M,format));
                    }
                }
                D[i][i] = M[i][i];
                if (D[i][i] != 0) {
                    Mul_Elementary_Description(1 / D[i][i],i);
                    for (int k = i; k < n; k++) {
                        M[i][k] /= D[i][i];
                        L[k][i] = M[i][k];
                        LT[i][k] = L[k][i];
                    }
                    if (D[i][i] != 1) {
                        writer.println(Display_Status_Matrix(M,format));
                    }
                } else {
                    LT[i][i] = 1;
                }
            }
            writer.println("L = ");
            writer.println(Display_Status_Matrix(L,format));
            writer.println("D = ");
            writer.println(Display_Status_Matrix(D,format));
            writer.println("L' = ");
            writer.println(Display_Status_Matrix(LT,format));
        } else {
            writer.println("this is a matrix which is not symmetrical matrix");
        }
    }

    // get the LU decomposition of M (second algorithm)
    private void from_M_To_LU_V2(float[][] M) {
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
        writer.println("L = ");
        writer.println(Display_Status_Matrix(L,format));
        writer.println("U = ");
        writer.println(Display_Status_Matrix(U,format));
    }

    // get the LL' decomposition of M (second algorithm)
    private void from_M_To_LLT_V2(float[][] M) {
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
            writer.println("L = ");
            writer.println(Display_Status_Matrix(L,format));
            writer.println("L' = ");
            writer.println(Display_Status_Matrix(LT,format));
        } else {
            writer.println("this is a matrix which is not symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (second algorithm)
    private void from_M_To_LDLT_V2(float[][] M) {
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
            writer.println("L = ");
            writer.println(Display_Status_Matrix(L,format));
            writer.println("D = ");
            writer.println(Display_Status_Matrix(D,format));
            writer.println("L' = ");
            writer.println(Display_Status_Matrix(LT,format));
        } else {
            writer.println("this is a matrix which is not symmetrical matrix");
        }
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    @Override
    // get the matrix components
    public void User_Interface(float[][] M) throws Exception {
        switch (method) {
            case "LU_Decomposition" -> {
                writer.println("find L and U by decomposition of M:");
                from_M_To_LU_V1(M);
            }
            case "LLT_Decomposition" -> {
                writer.println("find L and L' by decomposition of M:");
                from_M_To_LLT_V1(M);
            }
            case "LDLT_Decomposition" -> {
                writer.println("find L, D and L' by decomposition of M:");
                from_M_To_LDLT_V1(M);
            }
            case "LU_Decomposition_2" -> {
                writer.println("find L and U by decomposition of M:");
                from_M_To_LU_V2(M);
            }
            case "LLT_Decomposition_2" -> {
                writer.println("find L and L' by decomposition of M:");
                from_M_To_LLT_V2(M);
            }
            case "LDLT_Decomposition_2" -> {
                writer.println("find L, D and L' by decomposition of M:");
                from_M_To_LDLT_V2(M);
            }
            default -> throw new Exception("you entered invalid value of method solution");
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    @Override
    // check if user input is valid
    public void Run_Progress() throws Exception {
        if (format.equals("decimal") || format.equals("rational")) {
            int m = M.length, n = M[0].length;
            writer.println("decompose the next matrix (" + m + "*" + n + " size):");
            writer.println(Display_Status_Matrix(M,format));
            if (m != n) {
                writer.println("this is a matrix which is not square matrix");
            } else {
                writer.println("M = ");
                writer.println(Display_Status_Matrix(M,format));
                User_Interface(M);
            }
            writer.close();
        } else {
            throw new Exception("you entered invalid value for representation elementary actions and solution");
        }
    }
}
