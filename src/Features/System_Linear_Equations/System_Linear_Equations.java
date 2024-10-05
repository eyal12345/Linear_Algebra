package Features.System_Linear_Equations;

import Features.MenuActionsSLE;
import Features.ShareTools;
import java.io.PrintWriter;

public class System_Linear_Equations extends ShareTools implements MenuActionsSLE {
    public float[][] A;
    public float[][] b;
    public float[][] x;

    public System_Linear_Equations(float[][] nA, float[][] nb, String method, String format, PrintWriter writer) {
        super(method, format, writer);
        this.A = nA;
        this.b = nb;
        this.x = null;
    }

    /////////////////////////////////////////////// Write Methods /////////////////////////////////////////////////
    // display the system Ax = b in the linear equations format
    private void Write_Exercise(float[][] A, float[][] b) {
        int m = A.length, n = A[0].length;
        writer.println(Executive_Order(m,n));
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                writer.print("eq" + (i + 1) + ": ");
                if (A[i][0] % 1 == 0) {
                    if (A[i][0] == 1) {
                        if (b[i][0] % 1 == 0) {
                            writer.println("x = " + (int) b[i][0]);
                        } else {
                            writer.println("x = " + b[i][0]);
                        }
                    } else if (A[i][0] == -1) {
                        if (b[i][0] % 1 == 0) {
                            writer.println("-x = " + (int) b[i][0]);
                        } else {
                            writer.println("-x = " + b[i][0]);
                        }
                    } else {
                        if (b[i][0] % 1 == 0) {
                            writer.println((int) A[i][0] + "x = " + (int) b[i][0]);
                        } else {
                            writer.println((int) A[i][0] + "x = " + b[i][0]);
                        }
                    }
                } else {
                    if (b[i][0] % 1 == 0)
                        writer.println(A[i][0] + "x = " + (int) b[i][0]);
                    else
                        writer.println(A[i][0] + "x = " + b[i][0]);
                }
            }
        } else {
            for (int i = 0; i < m; i++) {
                writer.print("eq" + (i + 1) + ": ");
                for (int j = 0; j < n; j++) {
                    if (A[i][j] > 0) {
                        writer.print("+ ");
                        if (Math.abs(A[i][j]) == 1) {
                            writer.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            writer.print((int) A[i][j] + "*x" + (j + 1) + " ");
                        } else {
                            writer.print(A[i][j] + "*x" + (j + 1) + " ");
                        }
                    } else if (A[i][j] < 0) {
                        writer.print("- ");
                        if (Math.abs(A[i][j]) == 1) {
                            writer.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            writer.print((int) Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        } else {
                            writer.print(Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        }
                    } else {
                        writer.print(" ".repeat(7));
                    }
                }
                if (b[i][0] % 1 == 0) {
                    writer.println(" = " + (int) b[i][0]);
                } else {
                    writer.println(" = " + b[i][0]);
                }
            }
        }
        writer.println();
    }

    // display current status of the system Ax = b each time of iteration on an element
    public void Write_Status_System(float[][] A, float[][] b) {
        int m = A.length, n = A[0].length, k = b[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(A[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    writer.print((int) (Math.round(A[i][j] * 1000.0) / 1000.0));
                } else if (format.equals("decimal")) {
                    writer.print(Math.round(A[i][j] * 1000.0) / 1000.0);
                } else if (format.equals("rational")) {
                    writer.print(convertDecimalToFraction(A[i][j]));
                } if (j != n - 1) {
                    writer.print(" ,");
                }
            }
            writer.print(" | ");
            for (int j = 0; j < k; j++) {
                if ((Math.round(b[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    writer.print((int) (Math.round(b[i][j] * 1000.0) / 1000.0));
                } else if (format.equals("decimal")) {
                    writer.print(Math.round(b[i][j] * 1000.0) / 1000.0);
                } else if (format.equals("rational")) {
                    writer.print(convertDecimalToFraction(b[i][j]));
                } if (j != k - 1) {
                    writer.print(" ,");
                }
            }
            writer.println();
        }
        writer.println();
        this.A = A; this.b = b;
    }

    // show the resulting solution as a vector representation
    private void Write_Solution(float[][] x) {
        int m = x.length, n = x[0].length;
        StringBuilder sol = new StringBuilder();
        StringBuilder suf = new StringBuilder();
        if (Is_Zero_Col(x,1)) {
            writer.println("exist a single solution in R" + m + " space for the system which is:");
            if (m == 1 && n == 1) {
                if (x[0][0] % 1 == 0) {
                    sol.append((int) x[0][0]);
                } else if (format.equals("decimal")) {
                    sol.append(x[0][0]);
                } else if (format.equals("rational")) {
                    sol.append(convertDecimalToFraction(x[0][0]));
                }
            } else {
                sol.append(Display_Vector(x,0));
            }
        } else {
            if (m == 1 && n == 2) {
                writer.println("exists an infinite number of solutions which are belongs to the R set:");
                suf.append("λ");
            } else {
                writer.println("the solution is an infinite set of vectors in R" + m + " space which are linearly dependents in the vector space:");
                suf.append(" when ");
                if (!Is_Zero_Col(x,0)) {
                    sol.append(Display_Vector(x,0));
                }
                for (int t = 1; t < n && !Is_Zero_Col(x,t); t++) {
                    if (!sol.isEmpty()) {
                        sol.append(" + ");
                    }
                    sol = new StringBuilder((Is_Zero_Col(x, 2)) ? sol + "λ*" : sol + "λ" + t + "*");
                    if (n == 2) {
                        suf.append("λ it's a free scalar");
                    } else if (t == n - 1) {
                        suf.append("λ").append(t).append(" it's a free scalars");
                    } else {
                        suf.append("λ").append(t).append(",");
                    }
                    sol.append(Display_Vector(x,t));
                }
                suf.append(" that belongs to the R set");
            }
        }
        writer.println("x = " + sol.append(suf));
    }

    // determine what kind of matrix
    public String Which_Type_Triangular(float[][] A, boolean flag) {
        if (Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            return "A is already parallel triangular so now will be change directly to I:";
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A) && flag) {
            return "A is already upper triangular so now we'll go directly to the lower ranking:";
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A) && !flag) {
            return "A is already lower triangular so now we'll go directly to the upper ranking:";
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A) && flag) {
            return "transform L matrix to I by upper ranking:";
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A) && !flag) {
            return "transform U matrix to I by lower ranking:";
        } else if (flag) {
            return "transform A matrix to U by upper ranking:";
        } else {
            return "transform A matrix to L by lower ranking:";
        }
    }

    @Override
    // invoke which method match by method input user
    public String Fix_Method_Value(String method) {
        switch (method) {
            case "I", "IM", "Inv", "InvM", "Invertible" -> method = "Invertible_Method";
            case "C", "CM", "Cra", "CraM", "Cramer" -> method = "Cramer_Method";
            case "F", "FM", "FB", "FBM", "Forward", "Backward" -> method = "Forward_Backward_Method";
            case "U", "UR",  "URM", "Upp", "Upper" -> method = "Upper_Ranking_Method";
            case "L", "LR",  "LRM", "Low", "Lower" -> method = "Lower_Ranking_Method";
            case "P", "PR",  "PRM", "Pra", "Parallel" -> method = "Parallel_Ranking_Method";
            case "E", "EM",  "EMM", "Elem", "Elementary" -> method = "Elementary_Matrices_Method";
        }
        return method;
    }

    /////////////////////////////////////////////// Display Format ///////////////////////////////////////////////
    // display the coordinates as a vector representation
    private String Display_Vector(float[][] x, int c) {
        int m = x.length;
        StringBuilder s = new StringBuilder("(");
        for (int i = 0; i < m; i++) {
            if ((Math.round(x[i][c] * 1000.0) / 1000.0) % 1 == 0) {
                s.append((int) (Math.round(x[i][c] * 1000.0) / 1000.0));
            } else if (format.equals("decimal")) {
                s.append(Math.round(x[i][c] * 1000.0) / 1000.0);
            } else if (format.equals("rational")) {
                s.append(convertDecimalToFraction(x[i][c]));
            } if (i != m - 1) {
                s.append(" ,");
            }
        }
        s.append(")");
        return s.toString();
    }

    ////////////////////////////////////////////////// Quantity //////////////////////////////////////////////////
    // invoke executive order by quantity of equations and unknowns
    private String Executive_Order(int m, int n) {
        if (m == 1 && n == 1) {
            return "solve the next equation in R1 space (1 equation)(1 unknown):";
        } else if (m > 1 && n == 1) {
            return "solve the next equation in R1 space (" + m + " equations)(1 unknown):";
        } else if (m == 1 && n > 1) {
            return "solve the next equation in R" + n + " space (1 equation)(" + n + " unknowns):";
        } else {
            return "solve the next system in R" + n + " space (" + m + " equations)(" + n + " unknowns):";
        }
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    // solve system of linear equations Ax = b
    public float[][] Invertible_Action(float[][] A, float[][] b) {
        int n = A.length;
        float det = Determinant(A);
        if (det != 0) {
            writer.println("implement the solution by multiplication of b in invertible A: x = b*Inv(A)");
            float[][] invA = Invertible(A);
            writer.println("Inv(A) = ");
            writer.println(Display_Status_Matrix(invA,format));
            x = new float[n][1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    x[i][0] += b[j][0] * invA[i][j];
                }
                x[i][0] = (float)(Math.round(x[i][0] * 1000.0) / 1000.0);
            }
            return x;
        }
        writer.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }

    // solve system of linear equations Ax = b by cramer method (first algorithm)
    public float[][] Cramer_Action(float[][] A, float[][] b) {
        int n = A.length;
        float det = Determinant(A);
        if (det != 0) {
            writer.println("x(i) = |A(i)|/|A| for each 1 <= i <= " + n);
            writer.println("det = " + det + "\n");
            x = new float[n][1];
            for (int j = 0; j < n; j++) {
                float[] h = new float[n];
                for (int i = 0; i < n; i++) {
                    h[i] = A[i][j];
                    A[i][j] = b[i][0];
                }
                float detj = Determinant(A);
                writer.println("A" + (j + 1) + " = ");
                writer.println(Display_Status_Matrix(A,format));
                for (int i = 0; i < n; i++) {
                    A[i][j] = h[i];
                }
                x[j][0] = detj / det;
                writer.println("det" + (j + 1) + " = " + detj);
                writer.println("x" + (j + 1) + " = " + x[j][0] + "\n");
            }
            return x;
        }
        writer.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }

    // solve system of linear equations Ax = b by forward backward method: Ly = b and then Ux = y
    public float[][] Forward_Backward_Action(float[][] A, float[][] b) {
        int n = b.length;
        float det = Determinant(A);
        if (det != 0) {
            writer.println("first, we will calculate upper ranking of A:");
            float[][] U = Ranking_Matrix(A);
            writer.println(Display_Status_Matrix(U,format));
            writer.println("second, we will calculate lower ranking of A:");
            float[][] L = Mul_Mats(A,Invertible(U));
            writer.println(Display_Status_Matrix(L,format));
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (L[i][i] == 0) {
                        writer.println("retreat rows which are zeros in main diagonal:");
                        Retreat_Elementary_Description(i,j);
                        Retreat_Rows_Matrix(L,i,j);
                        Retreat_Rows_Matrix(b,i,j);
                        writer.println(Display_Status_Matrix(L,format));
                        break;
                    }
                }
            }
            writer.println("third, we will solve forward system Ly = b:");
            float[][] y = new float[n][1];
            Write_Status_System(L,b);
            for (int i = 0; i < n; i++) {
                y[i][0] = b[i][0];
                for (int j = 0; j < i; j++) {
                    y[i][0] -= L[i][j] * y[j][0];
                }
                y[i][0] /= L[i][i];
            }
            writer.println("finally, we will solve backward system Ux = y:");
            x = new float[n][1];
            Write_Status_System(U,y);
            for (int i = n - 1; i >= 0; i--) {
                x[i][0] = y[i][0];
                for (int j = i + 1; j < n; j++) {
                    x[i][0] -= U[i][j] * x[j][0];
                }
                x[i][0] /= U[i][i];
                x[i][0] = (float)(Math.round(x[i][0] * 1000.0) / 1000.0);
            }
            return x;
        }
        writer.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    @Override
    // choose action in order to solve a system Ax = b
    public void User_Interface(float[][] A, float[][] b) throws Exception {
        Write_Status_System(A,b);
        if (Is_Invalid_System(A,b)) {
            writer.println("does not an exists solutions");
            x = null;
        } else {
            method = Fix_Method_Value(method);
            switch (method) {
                case "Invertible_Method" -> {
                    writer.println("implement the solution by invertible method:");
                    x = Invertible_Action(A,b);
                }
                case "Cramer_Method" -> {
                    writer.println("implement the solution by cramer method:");
                    x = Cramer_Action(A,b);
                }
                case "Forward_Backward_Method" -> {
                    writer.println("implement the solution by forward backward method:");
                    x = Forward_Backward_Action(A,b);
                }
                case "Upper_Ranking_Method" -> {
                    writer.println("implement the solution by upper ranking method and then lower ranking method:");
                    Elementary_Method_Actions met = new Upper_Ranking_Method(A,b,method,format,writer);
                    x = met.Elementary_Method_Action(A,b);
                }
                case "Lower_Ranking_Method" -> {
                    writer.println("implement the solution by lower ranking method and then upper ranking method:");
                    Elementary_Method_Actions met = new Lower_Ranking_Method(A,b,method,format,writer);
                    x = met.Elementary_Method_Action(A,b);
                }
                case "Parallel_Ranking_Method" -> {
                    writer.println("implement the solution by parallel ranking method:");
                    Elementary_Method_Actions met = new Parallel_Ranking_Method(A,b,method,format,writer);
                    x = met.Elementary_Method_Action(A,b);
                }
                case "Elementary_Matrices_Method" -> {
                    writer.println("implement the solution by elementary matrices method:");
                    Elementary_Method_Actions met = new Elementary_Matrices_Method(A,b,method,format,writer);
                    x = met.Elementary_Method_Action(A,b);
                }
                default -> throw new Exception("you entered invalid value of method solution");
            }
        }
        if (x != null) {
            Write_Solution(x);
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    @Override
    // check if user input is valid
    public void Run_Progress() throws Exception {
        if (format.equals("decimal") || format.equals("rational")) {
            int m = A.length, k = b.length, l = b[0].length;
            Write_Exercise(A,b);
            if (m == k && l == 1) {
                User_Interface(A,b);
            } else {
                writer.println("this is input does not meet the conditions for system of linear equations");
            }
            writer.close();
        } else {
            throw new Exception("you entered invalid value for representation elementary actions and solution");
        }
    }
}
