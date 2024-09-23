package Features.Receive_Matrices;

import java.io.PrintWriter;

public class Receive_Matrices_Methods extends Receive_Matrices {

    public Receive_Matrices_Methods(float[][] L, String fn, String ne, PrintWriter fr) {
        super(L, fn, ne, fr);
    }

    // get the LU decomposition by multiplication of L and U (first algorithm)
    public void From_LU_To_M_V1(float[][] L, float[][] U) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(L,U);
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (first algorithm)
    public void From_LLT_To_M_V1(float[][] L, float[][] LT) {
        float[][] M = Mul_Mats(L,LT);
        fr.println("M = ");
        fr.println(Display_Status_Matrix(M,fn));
    }

    // get the LDL' decomposition by multiplication of L, D and L' (first algorithm)
    public void From_LDLT_To_M_V1(float[][] L, float[][] D, float[][] LT) {
        if (Is_One_Slant(L)) {
            float[][] M = Mul_Mats(Mul_Mats(L,D),LT);
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LU decomposition by multiplication of L and U (second algorithm)
    public void From_LU_To_M_V2(float[][] L, float[][] U) {
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
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }

    // get the LL' decomposition by multiplication of L and L' (second algorithm)
    public void From_LLT_To_M_V2(float[][] L, float[][] LT) {
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
        fr.println("M = ");
        fr.println(Display_Status_Matrix(M,fn));
    }

    // get the LDL' decomposition by multiplication of L, D and L' (second algorithm)
    public void From_LDLT_To_M_V2(float[][] L, float[][] D, float[][] LT) {
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
            fr.println("M = ");
            fr.println(Display_Status_Matrix(M,fn));
        } else {
            fr.println("these are values other than 1 on the main diagonal of L matrix");
        }
    }
}
