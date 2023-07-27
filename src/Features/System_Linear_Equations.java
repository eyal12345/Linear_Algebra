package Features;

import Tools.ShareTools;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class System_Linear_Equations extends ShareTools {
    private float[][] A;
    private float[] b;
    private float[] x;
    private final String fn;
    private PrintWriter fr;

    public System_Linear_Equations(float[][] nA, float[] nb, String rep) {
        A = nA;
        b = nb;
        x = null;
        fn = rep;
        fr = null;
    }

    /////////////////////////////////////////////// Print Methods /////////////////////////////////////////////////
    // display the system Ax = b in the linear equations format
    private void Write_Exercise() {
        int n = A.length;
        fr.println(Executive_Order(n));
        if (n == 1) {
            if (A[0][0] % 1 == 0) {
                if (A[0][0] == 1) {
                    if (b[0] % 1 == 0) {
                        fr.println("x = " + (int) b[0]);
                    } else {
                        fr.println("x = " + b[0]);
                    }
                } else if (A[0][0] == -1) {
                    if (b[0] % 1 == 0) {
                        fr.println("-x = " + (int) b[0]);
                    } else {
                        fr.println("-x = " + b[0]);
                    }
                } else {
                    if (b[0] % 1 == 0) {
                        fr.println((int) A[0][0] + "x = " + (int) b[0]);
                    } else {
                        fr.println((int) A[0][0] + "x = " + b[0]);
                    }
                }
            } else {
                if (b[0] % 1 == 0) {
                    fr.println(A[0][0] + "x = " + (int) b[0]);
                } else {
                    fr.println(A[0][0] + "x = " + b[0]);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
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
                if (b[i] % 1 == 0) {
                    fr.println(" = " + (int) (Math.round(b[i] * 1000.0) / 1000.0));
                } else {
                    fr.println(" = " + (Math.round(b[i] * 1000.0) / 1000.0));
                }
            }
        }
        fr.println();
    }

    // display current status of the system Ax = b each time of iteration on an element
    private void Write_Status_System() {
        int n = A.length;
        for (int i = 0; i < n; i++) {
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
            if ((Math.round(b[i] * 1000.0) / 1000.0) % 1 == 0) {
                fr.print((int) (Math.round(b[i] * 1000.0) / 1000.0));
            } else if (fn.equals("decimal")) {
                fr.print(Math.round(b[i] * 1000.0) / 1000.0);
            } else if (fn.equals("rational")) {
                fr.print(convertDecimalToFraction(b[i]));
            }
            fr.println();
        }
        fr.println();
    }

    // display a matrix each current status
    private void Write_Matrix(float[][] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
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
            fr.println();
        }
        fr.println();
    }

    // determine what kind of matrix
    private String Which_Type_Triangular(float[][] A, boolean flag) {
        if (Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
            return "A is already parallel triangular so now will be change directly to I:";
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A) && flag) {
            return "A is already upper triangular so now we'll go directly to the lower ranking:";
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A) && !flag) {
            return "A is already lower triangular so now we'll go directly to the upper ranking:";
        } else if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A) && flag) {
            return "transform L matrix to I by an upper ranking:";
        } else if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A) && !flag) {
            return "transform U matrix to I by a lower ranking:";
        } else if (flag) {
            return "transform A matrix to U by an upper ranking:";
        } else {
            return "transform A matrix to L by a lower ranking:";
        }
    }

    // show the resulting solution as a vector representation
    private void Write_Solution() {
        int n = x.length;
        String s = "(";
        for (int i = 0; i < n; i++) {
            if ((Math.round(x[i] * 1000.0) / 1000.0) % 1 == 0) {
                s += (int) (Math.round(x[i] * 1000.0) / 1000.0);
            } else if (fn.equals("decimal")) {
                s += Math.round(x[i] * 1000.0) / 1000.0;
            } else if (fn.equals("rational")) {
                s += convertDecimalToFraction(x[i]);
            } if (i != n - 1) {
                s += " ,";
            }
        }
        s += ")";
        fr.println(s);
    }

    ///////////////////////////////////////////////// User Menus /////////////////////////////////////////////////
    // display user interface by selection method for solution
    private void User_Menu_System() {
        System.out.println("choose number method to solution:");
        System.out.println("1. invertible method");
        System.out.println("2. cramer method (first method)");
        System.out.println("3. cramer method (second method)");
        System.out.println("4. forward backward method");
        System.out.println("5. upper --> lower ranking method");
        System.out.println("6. lower --> upper ranking method");
        System.out.println("7. parallel ranking method");
        System.out.println("8. elementary matrices method");
    }

    ////////////////////////////////////////////////// Quantity //////////////////////////////////////////////////
    // invoke name of file by quantity of equations and unknowns
    private String Name_File(int n) {
        if (n == 1) {
            return "Linear_Equation_(1 Equation)(1 Unknown)";
        } else {
            return "System_Linear_Equations_(" + n + "_Equations)(" + n + "_Unknowns)";
        }
    }

    // invoke executive order by quantity of equations and unknowns
    private String Executive_Order(int n) {
        if (n == 1) {
            return "solve the next equation in R1 space:";
        } else {
            return "solve the next system in R" + n + " space:";
        }
    }

    ////////////////////////////////////////////////// Locations /////////////////////////////////////////////////
    // get the index starting from the specific column in the matrix which are him value not equal to 0
    private int Index_UnZero_Value(float[][] A, int k) {
        int n = A.length;
        for (int i = k + 1; i < n + k; i++) {
            if (A[i % n][k] != 0) {
                return i % n;
            }
        }
        return -1;
    }

    ////////////////////////////////////////////// Matrix Operations /////////////////////////////////////////////
    // replace between two rows in a system Ax = b
    public void Retreat_Rows_System(float[][] A, float[] b, int r1, int r2) {
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

    //////////////////////////////////////////// Elementary Actions //////////////////////////////////////////////
    // show elementary actions for replace between rows in the system
    private void Retreat_Elementary_Action(int i, int j) {
        int r1 = i + 1, r2 = j + 1;
        if (r1 <= r2) {
            fr.println("R" + r1 + " <--> R" + r2);
        } else {
            fr.println("R" + r2 + " <--> R" + r1);
        }
        fr.println();
    }

    // show elementary actions for sum between rows in the system
    private void Sum_Elementary_Action(float k, int j, int i) {
        if (k != 0) {
            int r = j + 1, c = i + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
            if (k > 0) {
                if (k % 1 == 0) {
                    if (k == 1) {
                        fr.println("R" + r + " --> R" + r + " - R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " - " + (int) k + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    fr.println("R" + r + " --> R" + r + " - " + k + "*R" + c);
                } else if (fn.equals("rational")) {
                    fr.println("R" + r + " --> R" + r + " - " + convertDecimalToFraction(k) + "*R" + c);
                }
            } else {
                if (k % 1 == 0) {
                    if (k == -1) {
                        fr.println("R" + r + " --> R" + r + " + R" + c);
                    } else {
                        fr.println("R" + r + " --> R" + r + " + " + (int) (-k) + "*R" + c);
                    }
                } else if (fn.equals("decimal")) {
                    fr.println("R" + r + " --> R" + r + " + " + (-k) + "*R" + c);
                } else if (fn.equals("rational")) {
                    fr.println("R" + r + " --> R" + r + " + " + convertDecimalToFraction(-k) + "*R" + c);
                }
            }
            fr.println();
        }
    }

    // show elementary actions for multiplication of a row in the system
    private void Mul_Elementary_Action(float k, int j) {
        if (k != 1) {
            int r = j + 1;
            k = (float) (Math.round(k * 1000.0) / 1000.0);
            if (k % 1 == 0) {
                if (k == -1) {
                    fr.println("R" + r + " --> - R" + r);
                } else {
                    fr.println("R" + r + " --> " + (int) k + "*R" + r);
                }
            } else if (fn.equals("decimal")) {
                fr.println("R" + r + " --> " + k + "*R" + r);
            } else if (fn.equals("rational")) {
                fr.println("R" + r + " --> " + convertDecimalToFraction(k) + "*R" + r);
            }
            fr.println();
        }
    }

    /////////////////////////////////////////// Methods to Solution /////////////////////////////////////////////
    // solve system of linear equations Ax = b by invertible multiplication method: x = Inv(A)*b
    private float[] Invertible_Method() {
        fr.println("implement the solution by multiplication of b in invertible A: x = b*Inv(A)");
        int n = A.length;
        float[] x = new float[n];
        float[][] invA = Invertible(A);
        fr.println("Inv(A) = ");
        Write_Matrix(invA);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x[i] += b[j] * invA[i][j];
            }
            x[i] = (float)(Math.round(x[i] * 1000.0) / 1000.0);
        }
        return x;
    }

    // solve system of linear equations Ax = b by cramer method (first algorithm)
    private float[] Cramer_Method_V1() {
        fr.println("x(i) = |A(i)|/|A| for each 1<=i<=n");
        int n = A.length;
        float det = Determinant(A);
        fr.println("det = " + det);
        float[] x = new float[n];
        float[] h = new float[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                h[i] = A[i][j];
                A[i][j] = b[i];
            }
            float detj = Determinant(A);
            fr.println("det" + (j + 1) + " = " + detj);
            fr.println("A" + (j + 1) + " = ");
            Write_Matrix(A);
            for (int i = 0; i < n; i++) {
                A[i][j] = h[i];
            }
            x[j] = detj / det;
        }
        return x;
    }

    // solve system of linear equations Ax = b by cramer method (second algorithm)
    private float[] Cramer_Method_V2() {
        fr.println("x(i) = |A(i)|/|A| for each 1<=i<=n");
        int n = A.length;
        float[] x = new float[n];
        float det = Determinant(A);
        fr.println("det = " + det);
        for (int i = 0; i < n; i++) {
            float sum = 0;
            for (int j = 0; j < n; j++) {
                sum += Math.pow(-1,i + j) * b[j] * Determinant(Sub_Matrix(A,j,i));
            }
            fr.println("det" + (i + 1) + " = " + sum);
            x[i] = sum / det;
        }
        return x;
    }

    // solve system of linear equations Ax = b by forward backward method: Ly = b and then Ux = y
    private float[] Forward_Backward_Method() {
        int n = b.length;
        fr.println("first, we will calculate upper ranking of A:");
        float[][] U = Ranking_Matrix(A);
        Write_Matrix(U);
        fr.println("second, we will calculate lower ranking of A:");
        float[][] L = Mul_Mats(A,Invertible(U));
        Write_Matrix(L);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (L[i][i] == 0) {
                    Retreat_Rows_System(L,b,i,j);
                    break;
                }
            }
        }
        fr.println("third, we will solve forward system Ly = b:");
        float[] y = new float[n];
        A = L;
        Write_Status_System();
        for (int i = 0; i < n; i++) {
            y[i] = b[i];
            for (int j = 0; j < i; j++) {
                y[i] -= L[i][j] * y[j];
            }
            y[i] /= L[i][i];
        }
        fr.println("finally, we will solve backward system Ux = y:");
        float[] x = new float[n];
        A = U;
        b = y;
        Write_Status_System();
        for (int i = n - 1; i >= 0; i--) {
            x[i] = y[i];
            for (int j = i + 1; j < n; j++) {
                x[i] -= U[i][j] * x[j];
            }
            x[i] /= U[i][i];
            x[i] = (float)(Math.round(x[i] * 1000.0) / 1000.0);
        }
        return x;
    }

    // solve system of linear equations Ax = b by an upper ranking and then a lower ranking
    private float[] Upper_Ranking_Method() {
        fr.println(Which_Type_Triangular(A,true));
        int n = A.length;
        for (int i = 0; i < n; i++) {
            if (A[i][i] == 0) {
                int r = Index_UnZero_Value(A,i);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_System(A,b,i,r);
                Write_Status_System();
            }
            for (int j = i + 1; j < n; j++) {
                if (A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = 0; k < n; k++) {
                        A[j][k] -= A[i][k] * c;
                    }
                    A[j][i] = 0;
                    b[j] -= b[i] * c;
                    Write_Status_System();
                } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                    float c = 1 / A[j][j];
                    Mul_Elementary_Action(c,j);
                    b[j] /= A[j][j];
                    A[j][j] = 1;
                    Write_Status_System();
                }
            }
            if (Is_Upper_Triangular(A) && !Is_Lower_Triangular(A)) {
                fr.print("and now ");
                return Lower_Ranking_Method();
            }
        }
        if (!Is_Unit_Matrix(A)) {
            fr.println("still not yet received an unit matrix");
            return Lower_Ranking_Method();
        }
        return b;
    }

    // solve system of linear equations Ax = b by a lower ranking and then an upper ranking
    private float[] Lower_Ranking_Method() {
        fr.println(Which_Type_Triangular(A,false));
        int n = A.length;
        for (int i = n - 1; i >= 0; i--) {
            if (A[i][i] == 0) {
                int r = n - 1 - Index_UnZero_Value(A,i);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_System(A,b,i,r);
                Write_Status_System();
            }
            for (int j = i - 1; j >= 0; j--) {
                if (A[j][i] != 0) {
                    float c = A[j][i] / A[i][i];
                    Sum_Elementary_Action(c,j,i);
                    for (int k = n - 1; k >= 0; k--) {
                        A[j][k] -= A[i][k] * c;
                    }
                    A[j][i] = 0;
                    b[j] -= b[i] * c;
                    Write_Status_System();
                } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                    float c = 1 / A[j][j];
                    Mul_Elementary_Action(c,j);
                    b[j] /= A[j][j];
                    A[j][j] = 1;
                    Write_Status_System();
                }
            }
            if (!Is_Upper_Triangular(A) && Is_Lower_Triangular(A)) {
                fr.print("and now ");
                return Upper_Ranking_Method();
            }
        }
        if (!Is_Unit_Matrix(A)) {
            fr.println("still not yet received an unit matrix");
            return Upper_Ranking_Method();
        }
        return b;
    }

    // solve system of linear equations Ax = b by parallel ranking
    private float[] Parallel_Ranking_Method() {
        fr.println("transform A matrix to I by a parallel ranking:");
        int n = A.length;
        while (!Is_Unit_Matrix(A)) {
            for (int i = 0; i < n; i++) {
                if (A[i][i] == 0) {
                    int r = Index_UnZero_Value(A,i);
                    Retreat_Elementary_Action(i,r);
                    Retreat_Rows_System(A,b,i,r);
                    Write_Status_System();
                }
                for (int j = 0; j < n; j++) {
                    if (i != j && A[j][i] != 0) {
                        float c = A[j][i] / A[i][i];
                        Sum_Elementary_Action(c,j,i);
                        for (int k = 0; k < n; k++) {
                            A[j][k] -= A[i][k] * c;
                        }
                        A[j][i] = 0;
                        b[j] -= b[i] * c;
                        Write_Status_System();
                    } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                        float c = 1 / A[j][j];
                        Mul_Elementary_Action(c,j);
                        b[j] /= A[j][j];
                        A[j][j] = 1;
                        Write_Status_System();
                    }
                }
            }
        }
        return b;
    }

    // solve system of linear equations Ax = b by parallel elementary matrices
    private float[] Elementary_Matrices_Method() {
        fr.println("transform A matrix to I by an elementary matrices:");
        int n = A.length, i = 0, j = 0;
        float[][] E = Unit_Matrix(n);
        while (!Is_Unit_Matrix(A)) {
            if (A[i][i] == 0) {
                int r = Index_UnZero_Value(A,i);
                Retreat_Elementary_Action(i,r);
                Retreat_Rows_System(E,b,i,r);
                A = Mul_Mats(E,A);
                E = Unit_Matrix(n);
                Write_Status_System();
            } if (i != j && A[j][i] != 0) {
                E[j][i] -= (A[j][i] / A[i][i]);
                Sum_Elementary_Action(-E[j][i],j,i);
                A = Mul_Mats(E,A);
                b[j] += b[i] * E[j][i];
                E = Unit_Matrix(n);
                A[j][i] = 0;
                Write_Status_System();
            } if (Is_Unit_Vector(A,j) && A[j][j] != 1) {
                E[j][j] = 1 / A[j][j];
                Mul_Elementary_Action(E[j][j],j);
                A = Mul_Mats(E,A);
                b[j] *= E[j][j];
                E = Unit_Matrix(n);
                A[j][j] = 1;
                Write_Status_System();
            } if (j == n - 1) {
                i = (i + 1) % n;
            }
            j = (j + 1) % n;
        }
        return b;
    }

    ///////////////////////////////////////////// User Interface ///////////////////////////////////////////////
    // choose action in order to solve a system Ax = b
    private void Many_Variables_System() throws Exception {
        int n = A.length;
        float det = Determinant(A);
        if (Is_Zero_Matrix(A) && Is_Zero_Vector(b)) {
            fr.println("exists an infinite number of solutions to the system in the space R" + (n));
        } else if ((Is_Zero_Matrix(A) && !Is_Zero_Vector(b)) || det == 0) {
            fr.println("does not an exists solutions for this system");
        } else {
            Scanner sc = new Scanner(System.in);
            User_Menu_System();
            int op = sc.nextInt();
            Write_Status_System();
            switch (op) {
                case 1:
                    fr.println("implement the solution by invertible method:");
                    x = Invertible_Method();
                    break;
                case 2:
                    fr.println("implement the solution by cramer method:");
                    x = Cramer_Method_V1();
                    break;
                case 3:
                    fr.println("implement the solution by cramer method:");
                    x = Cramer_Method_V2();
                    break;
                case 4:
                    fr.println("implement the solution by forward backward method:");
                    x = Forward_Backward_Method();
                    break;
                case 5:
                    fr.println("implement the solution by upper --> lower ranking method:");
                    x = Upper_Ranking_Method();
                    break;
                case 6:
                    fr.println("implement the solution by lower --> upper ranking method:");
                    x = Lower_Ranking_Method();
                    break;
                case 7:
                    fr.println("implement the solution by parallel ranking method:");
                    x = Parallel_Ranking_Method();
                    break;
                case 8:
                    fr.println("implement the solution by elementary ranking method:");
                    x = Elementary_Matrices_Method();
                    break;
                default:
                    throw new Exception("you entered an invalid number");
            }
            fr.println("exist a single solution in R" + n + " space for the system which is:");
            fr.print("x = ");
            Write_Solution();
        }
    }

    ///////////////////////////////////////////////// R1 Space ////////////////////////////////////////////////
    // solve an equation ax = b
    private void Single_Variable_Equation() {
        if (A[0][0] == 0 && b[0] == 0) {
            fr.println("exists an infinite number of solutions to the equation in R1 space:");
            fr.println("x = λ when λ it's a free value that belongs to the R set");
        } else if (A[0][0] == 0 && b[0] != 0) {
            fr.println("does not an exists solutions for this equation");
        } else {
            fr.println("exist a single solution in R1 space for the equation which is:");
            float c = b[0] / A[0][0];
            if (c % 1 == 0) {
                fr.println("x = " + (int) c);
            } else if (fn.equals("decimal")) {
                fr.println("x = " + c);
            } else if (fn.equals("rational")) {
                fr.println("x = " + convertDecimalToFraction(c));
            }
        }
    }

    /////////////////////////////////////////////// Check Input ///////////////////////////////////////////////
    // check if user input is valid
    public void Progress_Run() throws Exception {
        if (fn.equals("decimal") || fn.equals("rational")) {
            int m = A.length, n = A[0].length, k = b.length;
            LocalDateTime cur = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            File file = new File("Results/System_Linear_Equations/" + Name_File(n) + " " + cur.format(formatter) + ".txt");
            fr = new PrintWriter(new FileWriter(file, true));
            Write_Exercise();
            if (m == n && m == k) {
                if (n > 1) { // R2 space or higher
                    Many_Variables_System();
                } else { // R1 space
                    Single_Variable_Equation();
                }
            } else {
                fr.println("this is an input does not meet the conditions for system of linear equations");
            }
            fr.close();
        } else {
            throw new Exception("you entered invalid value for a representation elementary actions and solution");
        }
    }
}
