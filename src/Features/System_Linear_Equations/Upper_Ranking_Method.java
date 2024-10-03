package Features.System_Linear_Equations;

import java.io.PrintWriter;

public class Upper_Ranking_Method extends System_Linear_Equations_Extended implements Elementary_Method_Actions {

    public Upper_Ranking_Method(float[][] nA, float[][] nb, String method, String format, PrintWriter writer) {
        super(nA, nb, method, format, writer);
    }

    @Override
    public void Retreat_Elementary_Action(float[][] A, float[][] b, int c) {

    }

    @Override
    public void Sum_Elementary_Action(float[][] A, float[][] b, int r, int c) {

    }

    @Override
    public void Mul_Elementary_Action(float[][] A, float[][] b, int r) {

    }

    @Override
    // solve system of linear equations Ax = b by upper ranking and then lower ranking
    public float[][] Elementary_Method_Action(float[][] A, float[][] b) {
        return null;
    }
}
