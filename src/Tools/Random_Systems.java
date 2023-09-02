package Tools;

import Features.System_Linear_Equations;
public class Random_Systems {

    public void Rand_Exercises() {
        try {
            boolean flag = true;
            while (flag) {
                float[][] A = new float[8][8];
                float[][] b = new float[8][1];
                for (int i = 0; i < A.length; i++) {
                    for (int j = 0; j < A[0].length; j++) {
                        A[i][j] = (int)(Math.random()*12 - 6);
                    }
                    b[i][0] = (int)(Math.random()*42 - 21);
                }
                if (ShareTools.Determinant(A) % 50000 == 0) {
                    System.out.println(ShareTools.Determinant(A));
                    System_Linear_Equations sle = new System_Linear_Equations(A,b,"rational","test");
                    sle.Progress_Run();
                    flag = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
