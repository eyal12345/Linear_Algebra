package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class Parallel_Ranking_Method extends System_Linear_Equations_Extended implements Elementary_Method_Actions {

    public Parallel_Ranking_Method(float[][] nA, float[][] nb, String method, String format, PrintWriter writer) {
        super(nA, nb, method, format, writer);
    }

    @Override
    public void Retreat_Elementary_Action(float[][] A, float[][] b, int c) {
        int m = A.length;
        if (A[c][c] == 0 && !Is_Zero_Col(A,c)) {
            int r = Index_UnZero_Value(A,c,true);
            if (r >= 0 && r < m && r != c) {
                A[r][c] = (A[r][c] >= -0.0001 && A[r][c] <= 0.0001) ? 0 : A[r][c];
                Retreat_Elementary_Description(c,r);
                Retreat_Rows_Matrix(A,c,r);
                Retreat_Rows_Matrix(b,c,r);
                Write_Status_System(A,b);
            }
        }
    }

    @Override
    public void Sum_Elementary_Action(float[][] A, float[][] b, int r, int c) {
        int t = b[0].length - 1, n = A[0].length;
        if (c != r && A[c][c] != 0 && A[r][c] != 0) {
            float p = A[r][c] / A[c][c];
            Sum_Elementary_Description(p,r,c);
            for (int k = 0; k < n; k++) {
                A[r][k] -= A[c][k] * p;
                if (k <= t) {
                    b[r][k] -= b[c][k] * p;
                }
            }
            A[r][c] = (A[r][c] >= -0.0001 && A[r][c] <= 0.0001) ? 0 : A[r][c];
            if (r < n) {
                A[r][r] = (A[r][r] >= -0.0001 && A[r][r] <= 0.0001) ? 0 : A[r][r];
            }
            Write_Status_System(A,b);
        }
    }

    @Override
    public void Mul_Elementary_Action(float[][] A, float[][] b, int r) {
        int t = b[0].length - 1;
        if (Is_Unit_Vector(A,r)) {
            int d = Index_for_Unit_Vector(Row_from_Matrix(A,r));
            if (d != -1 && A[r][d] != 0 && A[r][d] != 1) {
                float p = 1 / A[r][d];
                Mul_Elementary_Description(p,r);
                for (int k = 0; k <= t; k++) {
                    b[r][k] /= A[r][d];
                }
                A[r][d] = 1;
                Write_Status_System(A,b);
            }
        }
    }

    @Override
    // solve system of linear equations Ax = b by ranking rows
    public float[][] Elementary_Method_Action(float[][] A, float[][] b) {
        writer.println("transform A matrix to I by ranking rows:");
        int m = A.length, n = A[0].length;
        while (!Is_Unit_Matrix(A)) {
            for (int i = 0; i < n; i++) {
                A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
                Define_Free_Variable(A,b,i);
                A = this.A; b = this.b;
                Retreat_Elementary_Action(A,b,i);
                for (int j = 0; j < m; j++) {
                    Sum_Elementary_Action(A,b,j,i);
                    Mul_Elementary_Action(A,b,j);
                    boolean changed = Is_Reduced_Rows(A,b,j);
                    A = this.A; b = this.b;
                    m = A.length; n = A[0].length;
                    if (!changed && Is_Zero_Row(A,j) && !Is_Zero_Row(b,j)) {
                        writer.println("does not exists solutions");
                        return null;
                    }
                }
            }
        }
        return b;
    }
}
