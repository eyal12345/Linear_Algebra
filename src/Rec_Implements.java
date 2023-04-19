
public class Rec_Implements {

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if a matrix is a unit matrix
    public static boolean Is_Unit_Matrix(float[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][i] != 1 || (i != j && A[i][j] != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if the specific row in the matrix is a unit vector
    public static boolean Is_Unit_Vector(float[][] A, int k) {
        int n = A.length;
        boolean flag = true;
        for (int i = 0; i < n && flag; i++) {
            for (int j = 0; j < n && A[k][i] != 0; j++) {
                if (j != i && A[k][j] != 0) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    public static int Get_Index_UnZero_Value(float[][] A, int k) {
        int n = A.length;
        for (int i = k + 1; i < n + k; i++) {
            if (A[i % n][k] != 0) {
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
    public static float Determinant(float[][] A) {
        int n = A.length;
        if (n == 1) {
            return A[0][0];
        }
        float sum = 0;
        for (int i = 0; i < n; i++) {
            sum += A[0][i] * Determinant(Sub_Matrix(A,0,i)) * Math.pow(-1,i);
        }
        return sum;
    }

    // calculate of sub-matrix from a matrix by cutting row "x" and column "y"
    public static float[][] Sub_Matrix(float[][] A, int x, int y) {
        int n = A.length ,p = 0 ,q = 0;
        float[][] subA = new float[n - 1][n - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != x && j != y) {
                    subA[p][q] = A[i][j];
                    q++;
                    if (q == n - 1) {
                        p++;
                        q = 0;
                    }
                }
            }
        }
        return subA;
    }

    // replace between two rows in a system Ax = b
    public static void Retreat_Rows_System(float[][] A, float[] b, int r1, int r2) {
        int n = A.length;
        for (int j = 0; j < n; j++) {
            float t = A[r1][j];
            A[r1][j] = A[r2][j];
            A[r2][j] = t;
        }
        float t = b[r1];
        b[r1] = b[r2];
        b[r2] = t;
    }

    // calculate multiplication between two matrices provided that M1's length column is equal to M2's length row
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

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // solve system of linear equations Ax = b by cramer method (recursive)
    public static float[] Cramer_Method_V2_Rec(float[][] A, float[] b, float[] x, int i, int j) {
        int n = A.length;
        if (i == n) {
            return x;
        } else {
            x[i] += Math.pow(-1,i + j) * b[j] * Determinant(Sub_Matrix(A,j,i));
            if (j == n - 1) {
                float det = Determinant(A);
                x[i] = x[i] / det;
                return Cramer_Method_V2_Rec(A,b,x,i + 1,0);
            }
            return Cramer_Method_V2_Rec(A,b,x,i,j + 1);
        }
    }


    // solve system of linear equations Ax = b by a parallel ranking (recursive)
    public static float[] Parallel_Ranking_Method_V1_Rec(float[][] A, float[] b, int i, int j) {
        int n = A.length;
        if (Is_Unit_Matrix(A)) {
            return b;
        } else {
            if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i);
                Retreat_Rows_System(A,b,i,r);
            } if (i != j && A[j][i] != 0) {
                float c = A[j][i] / A[i][i];
                for (int k = 0; k < n; k++) {
                    A[j][k] -= A[i][k] * c;
                }
                A[j][i] = 0;
                b[j] -= b[i] * c;
            } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                float c = 1 / A[j][j];
                b[j] /= A[j][j];
                A[j][j] = 1;
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
            return Parallel_Ranking_Method_V1_Rec(A,b,i,j);
        }
    }

    // solve system of linear equations Ax = b by multiplication elementary matrix each iteration (recursive)
    public static float[] Parallel_Ranking_Method_V2_Rec(float[][] A, float[] b, int i, int j) {
        int n = A.length;
        if (Is_Unit_Matrix(A)) {
            return b;
        } else {
            float[][] E = Unit_Matrix(n);
            if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value(A,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_System(E,b,i,r);
                A = Mul_Mats(E,A);
            } else {
                if (i != j && A[j][i] != 0) {
                    E[j][i] -= (A[j][i] / A[i][i]);
                    b[j] += b[i] * E[j][i];
                    A = Mul_Mats(E,A);
                    A[j][i] = 0;
                } else if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                    E[j][j] = 1 / A[j][j];
                    b[j] *= E[j][j];
                    A = Mul_Mats(E,A);
                    A[j][j] = 1;
                } if (j == n - 1) {
                    i = (i + 1) % n;
                }
                j = (j + 1) % n;
            }
            return Parallel_Ranking_Method_V2_Rec(A,b,i,j);
        }
    }

    ////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {

    }
}
