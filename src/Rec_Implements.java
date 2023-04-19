import java.util.Vector;

public class Rec_Implements {

    ////////////////////////////////////////////////// Questions /////////////////////////////////////////////////
    // check if the vector is a zero vector
    public static boolean Is_Zero_Vector(float[] b) {
        int n = b.length;
        for (int i = 0; i < n; i++) {
            if (b[i] != 0) {
                return false;
            }
        }
        return true;
    }

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

    // check if exist in the matrix a zeros row
    public static boolean Is_Zero_Row(float[][] A, int r) {
        int n = A[0].length;
        for (int j = 0; j < n; j++) {
            if (A[r][j] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if exist in the vector a zeros column
    public static boolean Is_Zero_Col(float[][] b, int c) {
        int m = b.length, n = b[0].length;
        for (int i = 0; i < m && c < n; i++) {
            if (b[i][c] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if all vector values is equal to each other
    public static boolean Is_Equals_Values(Vector<Float> R) {
        int n = R.size();
        for (int i = 0; i < n - 1; i++) {
            if (!R.get(i).equals(R.get(i + 1))) {
                return false;
            }
        }
        return true;
    }

    // check if the specific row in the matrix is a unit vector
    public static boolean Is_Exist_Vector(float[][] A, int r) {
        int n = A[0].length;
        float[] v = Get_Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Get_Index_for_Unit_Vector(v);
        for (int i = 0; i < n; i++) {
            if (i != r && Is_Unit_Vector(A,r) && Is_Unit_Vector(A,i)) {
                int c2 = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,i));
                if (c1 == c2 && c1 != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if exists two vectors in the matrix which are linearly dependent
    public static boolean Is_Linear_Dependent_Rows(float[][] A) {
        int m = A.length, n = A[0].length;
        for (int r1 = 0; r1 < m - 1; r1++) {
            for (int r2 = r1 + 1; r2 < m; r2++) {
                if (!Is_Unit_Vector(A,r1) && !Is_Unit_Vector(A,r2)) {
                    Vector<Float> R  = new Vector<Float>();
                    for (int j = 0; j < n; j++) {
                        if (A[r1][j] != 0 || A[r2][j] != 0) {
                            R.add(A[r1][j] / A[r2][j]);
                            if (r2 == r1 + 1 && Is_Equals_Values(R) && R.size() >= 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
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

    // find two columns in the matrix which are linearly dependent
    public static int Get_Linear_Dependent_Columns(float[][] A) {
        int m = A.length, n = A[0].length;
        for (int c1 = 0; c1 < n - 1; c1++) {
            for (int c2 = c1 + 1; c2 < n; c2++) {
                Vector<Float> C  = new Vector<Float>();
                for (int r = 0; r < m; r++) {
                    if (A[r][c1] != 0 || A[r][c2] != 0) {
                        C.add(A[r][c1] / A[r][c2]);
                    }
                }
                if (Is_Equals_Values(C) && C.size() > 1) {
                    return c1;
                }
            }
        }
        return -1;
    }

    // get the specific column index of the row requested from the matrix which are indicating a unit vector
    public static int Get_Index_Row_from_Matrix(float[][] A, int r) {
        int n = A[0].length;
        float[] v = Get_Row_from_Matrix(A,r);
        v[r] = (Is_Zero_Vector(v)) ? 1 : v[r];
        int c1 = Get_Index_for_Unit_Vector(v);
        for (int i = 0; i < n; i++) {
            int c2 = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,i));
            if (c1 == c2 && c1 != -1) {
                return i;
            }
        }
        return -1;
    }

    // get the index from the vector that is indicating a unit vector
    public static int Get_Index_for_Unit_Vector(float[] v) {
        int n = v.length;
        for (int c = 0; c < n; c++) {
            if (v[c] != 0) {
                return c;
            }
        }
        return -1;
    }

    // get the index starting from the specific column in the matrix which are him value not equal to 0 with or in the negative direction
    public static int Get_Index_UnZero_Value_Extended(float[][] A, int k, boolean flag) {
        int n = A.length;
        if (flag) {
            for (int i = k + 1; i < n + k; i++) {
                if (A[i % n][k] != 0) {
                    return i % n;
                }
            }
        } else {
            for (int i = n + k - 1; i > k - 1; i--) {
                if (A[i % n][k] != 0) {
                    return i % n;
                }
            }
        }
        return -1;
    }

    // get the index column from the specific row in the matrix which are indicating intersection between zero rows and zero columns
    public static int Get_Intersection_Zero_Row_Col(float[][] A, int r) {
        int n = A[0].length;
        for (int c = 0; c < n; c++) {
            if (Is_Zero_Col(A,c) && Is_Zero_Row(A,r)) {
                return c;
            }
        }
        return -1;
    }

    ///////////////////////////////////////////////// Matrix Rows ////////////////////////////////////////////////
    // replace between two rows in a system Ax = b
    public static void Retreat_Rows_System(float[][] A, float[][] b, int r1, int r2) {
        int n = A[0].length, m = b[0].length;
        for (int j = 0; j < n; j++) {
            float k = A[r1][j];
            A[r1][j] = A[r2][j];
            A[r2][j] = k;
        }
        for (int j = 0; j < m; j++) {
            if (!Is_Zero_Col(b,j)) {
                float k = b[r1][j];
                b[r1][j] = b[r2][j];
                b[r2][j] = k;
            }
        }
    }

    // replace between two rows in a matrix
    public static void Retreat_Rows_Matrix(float[][] M, int r1, int r2) {
        int n = M.length;
        for (int j = 0; j < n; j++) {
            float t = M[r1][j];
            M[r1][j] = M[r2][j];
            M[r2][j] = t;
        }
    }

    // import the specific row from the matrix
    public static float[] Get_Row_from_Matrix(float[][] A, int r) {
        int n = A[0].length;
        float[] v = new float[n];
        for (int j = 0; j < n; j++) {
            v[j] = A[r][j];
        }
        return v;
    }

    ///////////////////////////////////////////// Change Dimensions //////////////////////////////////////////////
    // add a new column to the vector
    public static float[][] Increase_Cols_in_Vector(float[][] b) {
        int m = b.length, n = b[0].length;
        float[][] nb = new float[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nb[i][j] = b[i][j];
            }
        }
        return nb;
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

    /////////////////////////////////////////.. Invertible Matrices /////////////////////////////////////////////
    // invert the M matrix in parallel ranking by a multiplication of M in elementary matrix each iteration (recursive)
    public static float[][] Invertible_Ranking_Method_V2_Rec(float[][] M, float[][] InvM, int i, int j) {
        int n = M.length;
        if (Is_Unit_Matrix(M)) {
            return InvM;
        } else {
            float[][] E = Unit_Matrix(n);
            if (M[i][i] == 0) {
                int r = Get_Index_UnZero_Value(M,i);
                System.out.println("R" + (i + 1) + " <--> R" + (r + 1) + "\n");
                Retreat_Rows_Matrix(E,i,r);
                M = Mul_Mats(E,M);
                InvM = Mul_Mats(E,InvM);
            } else {
                if (i != j && M[j][i] != 0) {
                    E[j][i] -= (M[j][i] / M[i][i]);
                    M = Mul_Mats(E,M);
                    InvM = Mul_Mats(E,InvM);
                    M[j][i] = 0;
                } else if (Is_Unit_Vector(M,j) && M[j][j] != 1) {
                    E[j][j] = 1 / M[j][j];
                    M = Mul_Mats(E,M);
                    InvM = Mul_Mats(E,InvM);
                    M[j][j] = 1;
                } if (j == n - 1)
                    i = (i + 1) % n;
                j = (j + 1) % n;
            }
            return Invertible_Ranking_Method_V2_Rec(M,InvM,i,j);
        }
    }

    ///////////////////////////////////////// System Linear Equations ///////////////////////////////////////////
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

    //////////////////////////////////// System Linear Equations Extended ///////////////////////////////////////
    // solve system of linear equations Ax = b by a parallel ranking (recursive)
    public static float[][] Parallel_Ranking_Method_Extended_Rec(float[][] A, float[][] b, int i, int j) {
        if (Is_Unit_Matrix(A)) {
            return b;
        } else {
            int n = A.length, t = b[0].length - 1;
            A[i][i] = (A[i][i] >= -0.0001 && A[i][i] <= 0.0001) ? 0 : A[i][i];
            if (Is_Zero_Row(A,i) && !Is_Linear_Dependent_Rows(A)) {
                int d1 = Get_Intersection_Zero_Row_Col(A,i);
                int d2 = Get_Linear_Dependent_Columns(A);
                if (d1 != -1) {
                    b = Increase_Cols_in_Vector(b);
                    A[i][d1] = 1;
                    b[i][++t] = 1;
                } else if (d2 != -1) {
                    b = Increase_Cols_in_Vector(b);
                    A[i][d2] = 1;
                    b[i][++t] = 1;
                } else if (!Is_Exist_Vector(A,i)) {
                    b = Increase_Cols_in_Vector(b);
                    A[i][i] = 1;
                    b[i][++t] = 1;
                }
            } if (A[i][i] == 0) {
                int r = Get_Index_UnZero_Value_Extended(A,i,true);
                if (r >= 0 && r < n && r != i) {
                    A[r][i] = (A[r][i] >= -0.0001 && A[r][i] <= 0.0001) ? 0 : A[r][i];
                    Retreat_Rows_System(A,b,i,r);
                }
            }
            if (i != j && A[i][i] != 0 && A[j][i] != 0) {
                float c = A[j][i] / A[i][i];
                for (int k = 0; k < n; k++) {
                    A[j][k] -= A[i][k] * c;
                    if (k <= t) {
                        b[j][k] -= b[i][k] * c;
                    }
                }
                A[j][i] = (A[j][i] >= -0.0001 && A[j][i] <= 0.0001) ? 0 : A[j][i];
            }
            A[j][j] = (A[j][j] >= -0.0001 && A[j][j] <= 0.0001) ? 0 : A[j][j];
            if (Is_Unit_Vector(A,j)) {
                int d = Get_Index_for_Unit_Vector(Get_Row_from_Matrix(A,j));
                if (d != -1 && A[j][d] != 0 && A[j][d] != 1) {
                    float c = 1 / A[j][d];
                    for (int k = 0; k <= t; k++) {
                        b[j][k] /= A[j][d];
                    }
                    b[j][0] = (float) (Math.round(b[j][0] * 1000.0) / 1000.0);
                    A[j][d] = 1;
                }
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
            return Parallel_Ranking_Method_Extended_Rec(A,b,i,j);
        }
    }


    /////////////////////////////////////////////// Run Progress ////////////////////////////////////////////////
    public static void main(String[] args) {

    }
}
