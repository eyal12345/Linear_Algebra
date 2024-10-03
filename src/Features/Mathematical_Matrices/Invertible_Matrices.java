package Features.Mathematical_Matrices;

import Features.MenuActions;
import Features.ShareTools;
import java.io.PrintWriter;

public class Invertible_Matrices extends ShareTools implements MenuActions {
    public float[][] M;
    public float[][] InvM;

    public Invertible_Matrices(float[][] nM, String method, String format, PrintWriter writer) {
        super(method, format, writer);
        this.M = nM;
        this.InvM = Unit_Matrix(nM.length);
    }

    /////////////////////////////////////////////// Write Methods /////////////////////////////////////////////////
    // display current status of the matrices M and InvM each time of iteration on an element
    private void Write_Status_Matrices(float[][] M, float[][] InvM) {
        int n = M.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(M[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    writer.print((int) (Math.round(M[i][j] * 1000.0) / 1000.0));
                } else if (format.equals("decimal")) {
                    writer.print(Math.round(M[i][j] * 1000.0) / 1000.0);
                } else if (format.equals("rational")) {
                    writer.print(convertDecimalToFraction(M[i][j]));
                } if (j != n - 1) {
                    writer.print(" ,");
                }
            }
            writer.print(" | ");
            for (int j = 0; j < n; j++) {
                if ((Math.round(InvM[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    writer.print((int) (Math.round(InvM[i][j] * 1000.0) / 1000.0));
                } else if (format.equals("decimal")) {
                    writer.print(Math.round(InvM[i][j] * 1000.0) / 1000.0);
                } else if (format.equals("rational")) {
                    writer.print(convertDecimalToFraction(InvM[i][j]));
                } if (j != n - 1) {
                    writer.print(" ,");
                }
            }
            writer.println();
        }
        writer.println();
        this.M = M; this.InvM = InvM;
    }

    // determine what kind of matrix
    public String Which_Type_Triangular(float[][] M, boolean flag) {
        if (Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
            return "M is already parallel triangular so now will be change directly to I:";
        } else if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M) && flag) {
            return "M is already upper triangular so now we'll go directly to the lower ranking:";
        } else if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M) && !flag) {
            return "M is already lower triangular so now we'll go directly to the upper ranking:";
        } else if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M) && flag) {
            return "transform L matrix to I by upper ranking:";
        } else if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M) && !flag) {
            return "transform U matrix to I by lower ranking:";
        } else if (flag) {
            return "transform M matrix to U by upper ranking:";
        } else {
            return "transform M matrix to L by lower ranking:";
        }
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    // invert the M matrix by the formula: Inv(M) = 1/|M| * Adj(M)
    private float[][] Invertible_Direct(float[][] M) {
        int n = M.length;
        float det = Determinant(M);
        if (det == 0) {
            writer.println("this is singular matrix");
            return null;
        } else {
            float[][] InvM = new float[n][n];
            float[][] adj = Adjoint(M);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    InvM[i][j] = (1 / det) * adj[i][j];
                }
            }
            return InvM;
        }
    }

    // invert the M matrix by upper ranking and then lower ranking
    private float[][] Upper_Ranking_Rows_Action(float[][] M) {
        writer.println(Which_Type_Triangular(M,true));
        int n = M.length;
        float[][] InvM = this.InvM;
        for (int i = 0; i < n; i++) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i,true);
                Retreat_Elementary_Description(i,r);
                Retreat_Rows_Matrix(M,i,r);
                Retreat_Rows_Matrix(InvM,i,r);
                Write_Status_Matrices(M,InvM);
            }
            for (int j = i + 1; j < n; j++) {
                if (M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Description(c,j,i);
                    for (int k = 0; k < n; k++) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Write_Status_Matrices(M,InvM);
                } if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                    writer.println("this is singular matrix");
                    return null;
                } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Description(c,j);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Write_Status_Matrices(M,InvM);
                }
            }
            if (Is_Upper_Triangular(M) && !Is_Lower_Triangular(M)) {
                writer.print("and now ");
                return Lower_Ranking_Rows_Action(M);
            }
        }
        if (!Is_Unit_Matrix(M)) {
            writer.println("still not yet received unit matrix");
            return Lower_Ranking_Rows_Action(M);
        }
        return InvM;
    }

    // invert the M matrix by lower ranking and then upper ranking
    private float[][] Lower_Ranking_Rows_Action(float[][] M) {
        writer.println(Which_Type_Triangular(M,false));
        int n = M.length;
        float[][] InvM = this.InvM;
        for (int i = n - 1; i >= 0; i--) {
            if (M[i][i] == 0) {
                int r = Index_UnZero_Value(M,i,false);
                Retreat_Elementary_Description(i,r);
                Retreat_Rows_Matrix(M,i,r);
                Retreat_Rows_Matrix(InvM,i,r);
                Write_Status_Matrices(M,InvM);
            }
            for (int j = i - 1; j >= 0; j--) {
                if (M[j][i] != 0) {
                    float c = M[j][i] / M[i][i];
                    Sum_Elementary_Description(c,j,i);
                    for (int k = n - 1; k >= 0; k--) {
                        M[j][k] -= M[i][k] * c;
                        InvM[j][k] -= InvM[i][k] * c;
                    }
                    M[j][i] = 0;
                    Write_Status_Matrices(M,InvM);
                } if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                    writer.println("this is singular matrix");
                    return null;
                } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                    float c = 1 / M[j][j];
                    Mul_Elementary_Description(c,j);
                    for (int k = 0; k < n; k++) {
                        InvM[j][k] /= M[j][j];
                    }
                    M[j][j] = 1;
                    Write_Status_Matrices(M,InvM);
                }
            }
            if (!Is_Upper_Triangular(M) && Is_Lower_Triangular(M)) {
                writer.print("and now ");
                return Upper_Ranking_Rows_Action(M);
            }
        }
        if (!Is_Unit_Matrix(M)) {
            writer.println("still not yet received unit matrix");
            return Upper_Ranking_Rows_Action(M);
        }
        return InvM;
    }

    // invert the M matrix by parallel ranking
    private float[][] Ranking_Rows_Action(float[][] M) {
        writer.println("transform M matrix to I by a parallel ranking:");
        int n = M.length;
        float[][] InvM = this.InvM;
        while (!Is_Unit_Matrix(M)) {
            for (int i = 0; i < n; i++) {
                if (M[i][i] == 0 && !Is_Zero_Col(M,i)) {
                    int r = Index_UnZero_Value(M,i,true);
                    Retreat_Elementary_Description(i,r);
                    Retreat_Rows_Matrix(M,i,r);
                    Retreat_Rows_Matrix(InvM,i,r);
                    Write_Status_Matrices(M,InvM);
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && M[j][i] != 0) {
                        float c = M[j][i] / M[i][i];
                        Sum_Elementary_Description(c,j,i);
                        for (int k = 0; k < n; k++) {
                            M[j][k] -= M[i][k] * c;
                            InvM[j][k] -= InvM[i][k] * c;
                        }
                        M[j][i] = 0;
                        Write_Status_Matrices(M,InvM);
                    }
                    if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                        writer.println("this is singular matrix");
                        return null;
                    } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                        float c = 1 / M[j][j];
                        Mul_Elementary_Description(c,j);
                        for (int k = 0; k < n; k++) {
                            InvM[j][k] /= M[j][j];
                        }
                        M[j][j] = 1;
                        Write_Status_Matrices(M,InvM);
                    }
                }
            }
        }
        return InvM;
    }

    // invert the M matrix by parallel elementary matrices
    private float[][] Elementary_Matrices_Action(float[][] M) {
        writer.println("transform M matrix to I by elementary matrices:");
        int n = M.length, i = 0, j = 0;
        float[][] InvM = this.InvM;
        float[][] E = Unit_Matrix(n);
        while (!Is_Unit_Matrix(M)) {
            if (M[i][i] == 0 && !Is_Zero_Col(M,i)) {
                int r = Index_UnZero_Value(M,i,true);
                Retreat_Elementary_Description(i,r);
                Retreat_Rows_Matrix(E,i,r);
                M = Mul_Mats(E,M); InvM = Mul_Mats(E,InvM); E = Unit_Matrix(n);
                Write_Status_Matrices(M,InvM);
            }
            if (i != j && M[j][i] != 0) {
                E[j][i] -= (M[j][i] / M[i][i]);
                Sum_Elementary_Description(-E[j][i],j,i);
                M = Mul_Mats(E,M); InvM = Mul_Mats(E,InvM); E = Unit_Matrix(n);
                M[j][i] = 0;
                Write_Status_Matrices(M,InvM);
            }
            if (Is_Zero_Row(M,j) || Is_Zero_Col(M,i)) {
                writer.println("this is singular matrix");
                return null;
            } else if (Is_Unit_Vector(M,j) && M[j][j] != 0 && M[j][j] != 1) {
                E[j][j] = 1 / M[j][j];
                Mul_Elementary_Description(E[j][j],j);
                M = Mul_Mats(E,M); InvM = Mul_Mats(E,InvM); E = Unit_Matrix(n);
                M[j][j] = 1;
                Write_Status_Matrices(M,InvM);
            }
            if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return InvM;
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    @Override
    // choose option in order to correctness check for M matrix
    public void User_Interface(float[][] M) throws Exception {
        InvM = Unit_Matrix(M.length);
        switch (method) {
            case "Formula_Method" -> {
                writer.println("implement the solution by formula: Inv(M) = (1/|M|) * Adj(M)");
                InvM = Invertible_Direct(M);
            }
            case "Upper_Ranking_Method" -> {
                Write_Status_Matrices(M,InvM);
                writer.println("implement the solution by upper ranking method and then lower ranking method:");
                InvM = Upper_Ranking_Rows_Action(M);
            }
            case "Lower_Ranking_Method" -> {
                Write_Status_Matrices(M,InvM);
                writer.println("implement the solution by lower ranking method and then upper ranking method:");
                InvM = Lower_Ranking_Rows_Action(M);
            }
            case "Ranking_Rows_Method" -> {
                Write_Status_Matrices(M,InvM);
                writer.println("implement the solution by parallel ranking rows method:");
                InvM = Ranking_Rows_Action(M);
            }
            case "Elementary_Matrices_Method" -> {
                Write_Status_Matrices(M,InvM);
                writer.println("implement the solution by elementary matrices method:");
                InvM = Elementary_Matrices_Action(M);
            }
            default -> throw new Exception("you entered invalid number");
        }
        if (InvM != null) {
            writer.println("the invertible of this matrix is:");
            writer.println(Display_Status_Matrix(InvM,format));
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    @Override
    // check if user input is valid
    public void Run_Progress() throws Exception {
        if (format.equals("decimal") || format.equals("rational")) {
            int m = M.length, n = M[0].length;
            writer.println("invert the next matrix (" + m + "*" + n + " size):");
            writer.println(Display_Status_Matrix(M,format));
            if (m != n) {
                writer.println("this is a matrix which is not square matrix");
            } else {
                User_Interface(M);
            }
            writer.close();
        } else {
            throw new Exception("you entered invalid value for representation elementary actions and solution");
        }
    }
}
