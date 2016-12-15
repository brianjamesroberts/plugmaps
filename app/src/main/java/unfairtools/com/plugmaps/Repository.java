package unfairtools.com.plugmaps;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by brianroberts on 12/13/16.
 */
public class Repository {


    Context context;
    public Repository(Context ctx){

        this.context = ctx;
    }

    public ArrayList<ChooseCarActivity.MakeModelData> getModelData(ChooseCarActivity.CarPickerType carPickerType) {
        ArrayList<ChooseCarActivity.MakeModelData> data = new ArrayList<ChooseCarActivity.MakeModelData>();
        switch (carPickerType) {
            case MANUFACTURER:
                for (int i = 0; i < 50; i++) {
                    ChooseCarActivity.MakeModelData mData = new ChooseCarActivity.MakeModelData();
                    mData.img = context.getResources().getDrawable(R.drawable.ic_airplane);
                    if (i%5== 0) {
                        mData.stringInfo = "Tesla";
                        mData.img = context.getDrawable(R.mipmap.ic_tesla2);
                    }


                    if (i%5 == 1) mData.stringInfo = "Toyota";

                    if (i%5 == 2) mData.stringInfo = "Chevy";

                    if (i%5 == 3) {
                        mData.stringInfo = "Ford";
                        mData.img = context.getDrawable(R.mipmap.ic_ford);
                    }

                    if (i%5 == 4) mData.stringInfo = "Kandi";
                    data.add(mData);
                }
                break;
            case MODEL:
                break;
        }
        return data;

    }
}
