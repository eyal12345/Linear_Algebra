package Features.Decompose_Matrices;

import java.io.PrintWriter;

public class LDLT_Decomposition extends Decompose_Matrices {

    public LDLT_Decomposition(float[][] M, String fn, String ne, PrintWriter fr) {
        super(M, fn, ne, fr);
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
