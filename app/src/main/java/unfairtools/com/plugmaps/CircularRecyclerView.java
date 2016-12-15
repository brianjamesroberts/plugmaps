package unfairtools.com.plugmaps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;

/**
 * Created by brianroberts on 12/14/16.
 */

public class CircularRecyclerView extends RecyclerView {

    //Matrix matrix = new Matrix();
    private CircularRecyclerView(Context context) {
        super(context);
    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        final float DELTAX = w * 0.15f;
//        float[] src = {
//                0, 0, w, 0, w, h, 0, h
//        };
//        float[] dst = {
//                0, 0, w, 0, w - DELTAX, h, DELTAX, h
//        };
//
//        matrix.setPolyToPoly(src, 0, dst, 0, 4);
//    }
//
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        canvas.save();
//        canvas.concat(matrix);
//        super.dispatchDraw(canvas);
//        canvas.restore();
//    }
}
