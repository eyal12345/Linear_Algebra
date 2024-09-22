package Features.Decompose_Matrices.Solution_Methods;

import Features.Decompose_Matrices.Decompose_Matrices;

import java.io.PrintWriter;

public class LLT_Decomposition extends Decompose_Matrices {

    public LLT_Decomposition(float[][] M, String fn, String ne, PrintWriter fr) {
        super(M, fn, ne, fr);
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
}
