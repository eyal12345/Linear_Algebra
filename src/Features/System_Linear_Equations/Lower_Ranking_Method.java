package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class Lower_Ranking_Method extends System_Linear_Equations_Extended implements Elementary_Method_Actions {

    public Lower_Ranking_Method(float[][] nA, float[][] nb, String method, String format, PrintWriter writer) {
        super(nA, nb, method, format, writer);
    }

    @Override
    public void Retreat_Elementary_Action(float[][] A, float[][] b, int c) {
        int m = A.length, n = A[0].length;
        if (A[c][c] == 0 && !(m == 1 && n == 1)) {
            int r = Index_UnZero_Value(A,c,false);
            int l = Index_Row_from_Matrix(A,c);
            if (Is_Exist_Vector(A,c) && l < c) {
                r = Index_UnZero_Value(A,c,true);
            } if (r >= 0 && r < m && r != c) {
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
        int m = A.length, n = A[0].length, t = b[0].length - 1;
        if (A[c][c] != 0 && A[r][c] != 0 && !(m == 1 && n == 1)) {
            float p = A[r][c] / A[c][c];
            Sum_Elementary_Description(p,r,c);
            for (int k = n - 1; k >= 0; k--) {
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
    // solve system of linear equations Ax = b by lower ranking and then upper ranking
    public float[][] Elementary_Method_Action(float[][] A, float[][] b) {
        writer.println(Which_Type_Triangular(A,false));
        int m = A.length, n = A[0].length;
        for (int i = Math.min(m,n) - 1; i >= 0; i--) {
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            Define_Free_Variable(this.A,this.b,i);
            Retreat_Elementary_Action(this.A,this.b,i);
            int s = (m == 1 && n == 1) ? i : i - 1;
            for (int j = s; j >= 0; j--) {
                Sum_Elementary_Action(this.A,this.b,j,i);
                Mul_Elementary_Action(this.A,this.b,j);
                boolean changed = Is_Reduced_Rows(this.A,this.b,j);
                A = this.A; b = this.b;
                m = A.length; n = A[0].length;
                if (!changed && Is_Zero_Row(A,j) && !Is_Zero_Row(b,j)) {
                    writer.println("does not exists solutions");
                    return null;
                }
            }
            if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
                writer.print("and now ");
                Elementary_Method_Actions met = new Upper_Ranking_Method(A,b,method,format,writer);
                return met.Elementary_Method_Action(A,b);
            }
        }
        if (!Is_Unit_Matrix(A)) {
            writer.println("still not yet received unit matrix");
            Elementary_Method_Actions met = new Upper_Ranking_Method(A,b,method,format,writer);
            return met.Elementary_Method_Action(A,b);
        }
        return b;
    }
}
