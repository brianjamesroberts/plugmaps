package unfairtools.com.plugmaps;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by brianroberts on 12/14/16.
 */
public class Constants {
    public final static int CarHolderWidth = 80;

    public static int dpToInt(int dp, DisplayMetrics displayMetrics){
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
