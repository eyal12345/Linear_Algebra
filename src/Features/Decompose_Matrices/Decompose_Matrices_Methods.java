package Features.Decompose_Matrices;

import java.io.PrintWriter;

public class Decompose_Matrices_Methods extends Decompose_Matrices {

    public Decompose_Matrices_Methods(float[][] M, String fn, String ne, PrintWriter fr) {
        super(M, fn, ne, fr);
    }

    // get the LU decomposition of M (first algorithm)
    public void From_M_To_LU_V1(float[][] M) {
        int n = M.length;
        float[][] L = new float[n][n];
        float[][] U = new float[n][n];
        fr.println("transform M matrix to U by an upper ranking:");
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
                    fr.println(Display_Status_Matrix(M,fn));
                }
            }
            for (int k = i; k < n; k++) {
                U[i][k] = M[i][k];
            }
        }
        fr.println("L = ");
        fr.println(Display_Status_Matrix(L,fn));
        fr.println("U = ");
        fr.println(Display_Status_Matrix(U,fn));
    }

    // get the LL' decomposition of M (first algorithm)
    public void From_M_To_LLT_V1(float[][] M) {
        if (Is_Symmetrical_Matrix(M) && Is_Values_Positives(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] LT = new float[n][n];
            fr.println("transform M matrix to L' by an upper ranking:");
            for (int i = 0; i < n && M[i][i] != 0; i++) {
                for (int j = i + 1; j < n; j++) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Description(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                    }
                    M[j][i] = 0;
                    if (c != 0) {
                        fr.println(Display_Status_Matrix(M,fn));
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
                    fr.println(Display_Status_Matrix(M,fn));
                }
            }
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (first algorithm)
    public void From_M_To_LDLT_V1(float[][] M) {
        if (Is_Symmetrical_Matrix(M)) {
            int n = M.length;
            float[][] L = new float[n][n];
            float[][] D = new float[n][n];
            float[][] LT = new float[n][n];
            fr.println("transform M matrix to L' by an upper ranking:");
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
                        fr.println(Display_Status_Matrix(M,fn));
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
                        fr.println(Display_Status_Matrix(M,fn));
                    }
                } else {
                    LT[i][i] = 1;
                }
            }
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("D = ");
            fr.println(Display_Status_Matrix(D,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix");
        }
    }

    // get the LU decomposition of M (second algorithm)
    public void From_M_To_LU_V2(float[][] M) {
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
        fr.println("L = ");
        fr.println(Display_Status_Matrix(L,fn));
        fr.println("U = ");
        fr.println(Display_Status_Matrix(U,fn));
    }

    // get the LL' decomposition of M (second algorithm)
    public void From_M_To_LLT_V2(float[][] M) {
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
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix or positive values on the main diagonal");
        }
    }

    // get the LDL' decomposition of M (second algorithm)
    public void From_M_To_LDLT_V2(float[][] M) {
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
            fr.println("L = ");
            fr.println(Display_Status_Matrix(L,fn));
            fr.println("D = ");
            fr.println(Display_Status_Matrix(D,fn));
            fr.println("L' = ");
            fr.println(Display_Status_Matrix(LT,fn));
        } else {
            fr.println("this is a matrix which is not a symmetrical matrix");
        }
    }
}
