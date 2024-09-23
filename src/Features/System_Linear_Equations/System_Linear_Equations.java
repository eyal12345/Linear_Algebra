package Features.System_Linear_Equations;

import Features.ShareTools;
import Features.System_Linear_Equations.Solution_Methods.*;
import java.util.Scanner;
import java.io.PrintWriter;

public class System_Linear_Equations extends ShareTools {
    public float[][] A;
    public float[][] b;
    public float[][] x;

    public System_Linear_Equations(float[][] nA, float[][] nb, String fn, String ne, PrintWriter fr) {
        super(fn, ne, fr);
        this.A = nA;
        this.b = nb;
        this.x = null;
    }

    /////////////////////////////////////////////// Write Methods /////////////////////////////////////////////////
    // display the system Ax = b in the linear equations format
    private void Write_Exercise(float[][] A, float[][] b) {
        int m = A.length, n = A[0].length;
        fr.println(Executive_Order(m,n));
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                fr.print("eq" + (i + 1) + ": ");
                if (A[i][0] % 1 == 0) {
                    if (A[i][0] == 1) {
                        if (b[i][0] % 1 == 0) {
                            fr.println("x = " + (int) b[i][0]);
                        } else {
                            fr.println("x = " + b[i][0]);
                        }
                    } else if (A[i][0] == -1) {
                        if (b[i][0] % 1 == 0) {
                            fr.println("-x = " + (int) b[i][0]);
                        } else {
                            fr.println("-x = " + b[i][0]);
                        }
                    } else {
                        if (b[i][0] % 1 == 0) {
                            fr.println((int) A[i][0] + "x = " + (int) b[i][0]);
                        } else {
                            fr.println((int) A[i][0] + "x = " + b[i][0]);
                        }
                    }
                } else {
                    if (b[i][0] % 1 == 0)
                        fr.println(A[i][0] + "x = " + (int) b[i][0]);
                    else
                        fr.println(A[i][0] + "x = " + b[i][0]);
                }
            }
        } else {
            for (int i = 0; i < m; i++) {
                fr.print("eq" + (i + 1) + ": ");
                for (int j = 0; j < n; j++) {
                    if (A[i][j] > 0) {
                        fr.print("+ ");
                        if (Math.abs(A[i][j]) == 1) {
                            fr.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            fr.print((int) A[i][j] + "*x" + (j + 1) + " ");
                        } else {
                            fr.print(A[i][j] + "*x" + (j + 1) + " ");
                        }
                    } else if (A[i][j] < 0) {
                        fr.print("- ");
                        if (Math.abs(A[i][j]) == 1) {
                            fr.print("  x" + (j + 1) + " ");
                        } else if (A[i][j] % 1 == 0) {
                            fr.print((int) Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        } else {
                            fr.print(Math.abs(A[i][j]) + "*x" + (j + 1) + " ");
                        }
                    } else {
                        fr.print(" ".repeat(7));
                    }
                }
                if (b[i][0] % 1 == 0) {
                    fr.println(" = " + (int) b[i][0]);
                } else {
                    fr.println(" = " + b[i][0]);
                }
            }
        }
        fr.println();
    }

    // display current status of the system Ax = b each time of iteration on an element
    public void Write_Status_System(float[][] A, float[][] b) {
        int m = A.length, n = A[0].length, k = b[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((Math.round(A[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(A[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(A[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(A[i][j]));
                } if (j != n - 1) {
                    fr.print(" ,");
                }
            }
            fr.print(" | ");
            for (int j = 0; j < k; j++) {
                if ((Math.round(b[i][j] * 1000.0) / 1000.0) % 1 == 0) {
                    fr.print((int) (Math.round(b[i][j] * 1000.0) / 1000.0));
                } else if (fn.equals("decimal")) {
                    fr.print(Math.round(b[i][j] * 1000.0) / 1000.0);
                } else if (fn.equals("rational")) {
                    fr.print(convertDecimalToFraction(b[i][j]));
                } if (j != k - 1) {
                    fr.print(" ,");
                }
            }
            fr.println();
        }
        fr.println();
        this.A = A; this.b = b;
    }

    // show the resulting solution as a vector representation
    private void Write_Solution(float[][] x) {
        int m = x.length, n = x[0].length;
        StringBuilder sol = new StringBuilder();
        StringBuilder suf = new StringBuilder();
        if (Is_Zero_Col(x,1)) {
            fr.println("exist a single solution in R" + m + " space for the system which is:");
            if (m == 1 && n == 1) {
                if (x[0][0] % 1 == 0) {
                    sol.append((int) x[0][0]);
                } else if (fn.equals("decimal")) {
                    sol.append(x[0][0]);
                } else if (fn.equals("rational")) {
                    sol.append(convertDecimalToFraction(x[0][0]));
                }
            } else {
                sol.append(Display_Vector(x,0));
            }
        } else {
            if (m == 1 && n == 2) {
                fr.println("exists an infinite number of solutions which are belongs to the R set:");
                suf.append("λ");
            } else {
                fr.println("the solution is an infinite set of vectors in R" + m + " space which are linearly dependents in the vector space:");
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
        fr.println("x = " + sol.append(suf));
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    private void User_Menu_System() {
        System.out.println("choose the number of method to solution:");
        System.out.println("1. invertible method (not support vector solutions)");
        System.out.println("2. cramer method (not support vector solutions)");
        System.out.println("3. forward backward method (not support vector solutions)");
        System.out.println("4. ranking rows method");
        System.out.println("5. elementary matrices method");
    }

    /////////////////////////////////////////////// Display Format ///////////////////////////////////////////////
    // display the coordinates as a vector representation
    private String Display_Vector(float[][] x, int c) {
        int m = x.length;
        StringBuilder s = new StringBuilder("(");
        for (int i = 0; i < m; i++) {
            if ((Math.round(x[i][c] * 1000.0) / 1000.0) % 1 == 0) {
                s.append((int) (Math.round(x[i][c] * 1000.0) / 1000.0));
            } else if (fn.equals("decimal")) {
                s.append(Math.round(x[i][c] * 1000.0) / 1000.0);
            } else if (fn.equals("rational")) {
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

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // multiplication of matrix and vector in same matrix
    public void Mul_Mats_System(float[][] E, float[][] A, float[][] b) {
        int m = E.length, nA = A[0].length, nb = b[0].length;
        float[][] EA = new float[m][nA];
        float[][] Eb = new float[m][nb];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < nA; j++) {
                for (int k = 0; k < m; k++) {
                    EA[i][j] += E[i][k] * A[k][j];
                    EA[i][j] = (EA[i][j] >= -0.0001 && EA[i][j] <= 0.0001) ? 0 : EA[i][j];
                }
            }
            for (int j = 0; j < nb; j++) {
                for (int k = 0; k < m; k++) {
                    Eb[i][j] += E[i][k] * b[k][j];
                    Eb[i][j] = (Eb[i][j] >= -0.0001 && Eb[i][j] <= 0.0001) ? 0 : Eb[i][j];
                }
            }
        }
        this.A = EA; this.b = Eb;
    }

    ///////////////////////////////////////////// Change Dimensions //////////////////////////////////////////////
    // add zero row from the system by needed
    private void Add_Zero_Row(float[][] A, float[][] b) {
        int m = A.length, n = A[0].length, t = b[0].length;
        float[][] nA = new float[m + 1][n];
        float[][] nb = new float[m + 1][t];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                nA[i][j] = A[i][j];
            }
            for (int k = 0; k < t ; k++) {
                nb[i][k] = b[i][k];
            }
        }
        this.A = nA; this.b = nb;
    }

    // remove zero row from the system by needed
    private void Delete_Zero_Row(float[][] A, float[][] b, int r) {
        int m = A.length, n = A[0].length, t = b[0].length;
        float[][] nA = new float[m - 1][n];
        float[][] nb = new float[m - 1][t];
        int k = 0;
        for (int i = 0; i < m; i++) {
            if (i != r) {
                for (int j = 0; j < n ; j++) {
                    nA[k][j] = A[i][j];
                }
                for (int j = 0; j < t ; j++) {
                    nb[k][j] = b[i][j];
                }
                k++;
            }
        }
        this.A = nA; this.b = nb;
    }

    // add a new column to the vector
    private float[][] Increase_Col_in_Vector(float[][] b) {
        int m = b.length, n = b[0].length;
        float[][] nb = new float[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nb[i][j] = b[i][j];
            }
        }
        return nb;
    }

    // remove the last column from the vector
    private float[][] Decrease_Col_in_Vector(float[][] b, float c) {
        int m = b.length;
        float[][] nb = new float[m][1];
        for (int i = 0; i < m; i++) {
            nb[i][0] = b[i][0] + c * b[i][1];
        }
        return nb;
    }

    ////////////////////////////////////////////// Free Variables ///////////////////////////////////////////////
    // counter free variables in the system
    private int Count_Free_Variables(float[][] A, float[][] b, int r) {
        int t = b[0].length - 1;
        if (t == 1 && !Is_Zero_Row(b,r)) {
            float c = - b[r][0] / b[r][1];
            if (c % 1 == 0) {
                fr.println("exist certainly const for scalar (λ = " + (int) c + ") so we simplify the vector b");
            } else {
                fr.println("exist certainly const for scalar (λ = " + c + ") so we simplify the vector b");
            }
            b = Decrease_Col_in_Vector(b,c);
            t--;
            Write_Status_System(A,b);
            this.b = b;
        }
        return t;
    }

    // define free variable in row "r" where exist zero rows in the matrix
    public void Define_Free_Variable(float[][] A, float[][] b, int r) {
        int m = A.length, n = A[0].length;
        if (m < n && (Is_Unit_Vector(A,r) || m == 1)) {
            fr.println("adding a new row to the system");
            Add_Zero_Row(A,b);
            A = this.A; b = this.b;
            m = A.length;
            Write_Status_System(A,b);
        }
        if (m <= n && Is_Zero_Row(A,r) && !Is_Linear_Dependent_Rows(A)) {
            int t = Count_Free_Variables(A,b,r); b = this.b;
            int d = m, d1 = Intersection_Zero_Row_Col(A,r), d2 = Linear_Dependent_Columns(A);
            if (d1 != -1) {
                d = d1;
            } else if (d2 != -1) {
                d = d2;
            } else if (!Is_Exist_Vector(A,r)) {
                d = r;
            } if (d < m && Is_Zero_Row(b,r)) {
                A[r][d] = 1;
                fr.println("define a new column in the vector b when x" + (d + 1) + " is a free variable in R" + m + " space:");
                b = Increase_Col_in_Vector(b);
                b[r][++t] = 1;
                Write_Status_System(A,b);
            }
            this.A = A; this.b = b;
        }
    }

    // check if number of rows in the matrix has reduced
    public boolean Is_Reduced_Rows(float[][] A, float[][] b, int r) {
        boolean changed = false;
        int m = A.length, n = A[0].length;
        if (m > n && Is_Zero_Row(A,r) && Is_Zero_Row(b,r)) {
            fr.println("delete the zero row from the system:");
            Delete_Zero_Row(A,b,r);
            A = this.A; b = this.b;
            changed = true;
            Write_Status_System(A,b);
        }
        return changed;
    }

    //////////////////////////////////////////// Methods to Solution ////////////////////////////////////////////
    // solve system of linear equations Ax = b
    public float[][] Invertible_Action(float[][] A, float[][] b) {
        int n = A.length;
        float det = Determinant(A);
        if (det != 0) {
            fr.println("implement the solution by multiplication of b in invertible A: x = b*Inv(A)");
            float[][] invA = Invertible(A);
            fr.println("Inv(A) = ");
            fr.println(Display_Status_Matrix(invA,fn));
            x = new float[n][1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    x[i][0] += b[j][0] * invA[i][j];
                }
                x[i][0] = (float)(Math.round(x[i][0] * 1000.0) / 1000.0);
            }
            return x;
        }
        fr.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }

    // solve system of linear equations Ax = b by cramer method (first algorithm)
    public float[][] Cramer_Action(float[][] A, float[][] b) {
        int n = A.length;
        float det = Determinant(A);
        if (det != 0) {
            fr.println("x(i) = |A(i)|/|A| for each 1 <= i <= " + n);
            fr.println("det = " + det + "\n");
            x = new float[n][1];
            for (int j = 0; j < n; j++) {
                float[] h = new float[n];
                for (int i = 0; i < n; i++) {
                    h[i] = A[i][j];
                    A[i][j] = b[i][0];
                }
                float detj = Determinant(A);
                fr.println("A" + (j + 1) + " = ");
                fr.println(Display_Status_Matrix(A,fn));
                for (int i = 0; i < n; i++) {
                    A[i][j] = h[i];
                }
                x[j][0] = detj / det;
                fr.println("det" + (j + 1) + " = " + detj);
                fr.println("x" + (j + 1) + " = " + x[j][0] + "\n");
            }
            return x;
        }
        fr.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }

    // solve system of linear equations Ax = b by forward backward method: Ly = b and then Ux = y
    public float[][] Forward_Backward_Action(float[][] A, float[][] b) {
        int n = b.length;
        float det = Determinant(A);
        if (det != 0) {
            fr.println("first, we will calculate upper ranking of A:");
            float[][] U = Ranking_Matrix(A);
            fr.println(Display_Status_Matrix(U,fn));
            fr.println("second, we will calculate lower ranking of A:");
            float[][] L = Mul_Mats(A,Invertible(U));
            fr.println(Display_Status_Matrix(L,fn));
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (L[i][i] == 0) {
                        fr.println("retreat rows which are zeros in main diagonal:");
                        Retreat_Elementary_Description(i,j);
                        Retreat_Rows_Matrix(L,i,j);
                        Retreat_Rows_Matrix(b,i,j);
                        fr.println(Display_Status_Matrix(L,fn));
                        break;
                    }
                }
            }
            fr.println("third, we will solve forward system Ly = b:");
            float[][] y = new float[n][1];
            Write_Status_System(L,b);
            for (int i = 0; i < n; i++) {
                y[i][0] = b[i][0];
                for (int j = 0; j < i; j++) {
                    y[i][0] -= L[i][j] * y[j][0];
                }
                y[i][0] /= L[i][i];
            }
            fr.println("finally, we will solve backward system Ux = y:");
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
        fr.println("has no solution because it's impossible to reach vector solution in this method when determinant is 0");
        return null;
    }

    /////////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose action in order to solve a system Ax = b
    private void Solve_System(float[][] A, float[][] b) throws Exception {
        Scanner sc = new Scanner(System.in);
        User_Menu_System();
        int op = sc.nextInt();
        Write_Status_System(A,b);
        if (Is_Invalid_System(A,b)) {
            fr.println("does not an exists solutions");
            x = null;
        } else switch (op) {
            case 1 -> {
                fr.println("implement the solution by invertible method:");
                x = Invertible_Action(A,b);
            }
            case 2 -> {
                fr.println("implement the solution by cramer method:");
                x = Cramer_Action(A,b);
            }
            case 3 -> {
                fr.println("implement the solution by forward backward method:");
                x = Forward_Backward_Action(A,b);
            }
            case 4 -> {
                fr.println("implement the solution by ranking rows method:");
                Ranking_Rows_Method met = new Ranking_Rows_Method(A,b,fn,ne,fr);
                x = met.Ranking_Rows_Action(A,b);
            }
            case 5 -> {
                fr.println("implement the solution by elementary matrices method:");
                Elementary_Matrices_Method met = new Elementary_Matrices_Method(A,b,fn,ne,fr);
                x = met.Elementary_Matrices_Action(A,b);
            }
            default -> throw new Exception("you entered an invalid value for an option number to solution");
        }
        if (x != null) {
            Write_Solution(x);
        }
    }

    //////////////////////////////////////////////// Check Input ////////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = A.length, k = b.length, l = b[0].length;
            Write_Exercise(A,b);
            if (m == k && l == 1) {
                Solve_System(A,b);
            } else {
                fr.println("this is an input does not meet the conditions for system of linear equations");
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
