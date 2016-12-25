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

    public ArrayList<ChooseCarFragment.MakeModelData> getModelData(ChooseCarFragment.CarPickerType carPickerType) {
        ArrayList<ChooseCarFragment.MakeModelData> data = new ArrayList<ChooseCarFragment.MakeModelData>();
        switch (carPickerType) {
            case MANUFACTURER:
                ChooseCarFragment.numBrands=50;
                for (int i = 0; i < 50; i++) {
                    ChooseCarFragment.MakeModelData mData = new ChooseCarFragment.MakeModelData();
                  //  mData.img = context.getResources().getDrawable(R.drawable.ic_airplane);

                    if (i%5== 0) {
                        mData.stringInfo = "Tesla ";
                        mData.img = context.getDrawable(R.mipmap.ic_tesla2);
                    }
                    if (i%5 == 1) {
                        mData.stringInfo = "Toyota ";
                        mData.img = context.getDrawable(R.mipmap.ic_toyota);
                    }

                    if (i%5 == 2){
                        mData.stringInfo = "Chevy ";
                        mData.img = context.getDrawable(R.mipmap.ic_chevy);
                    }

                    if (i%5 == 4) {
                        mData.stringInfo = "Ford";
                        mData.img = context.getDrawable(R.mipmap.ic_ford);
                    }

                    if (i%5 == 3){
                        mData.stringInfo = "Faraday ";
                        mData.img = context.getDrawable(R.mipmap.ic_faraday);
                    }
                    data.add(mData);
                }
                break;
            case MODEL:
                break;
        }
        return data;

    }
}
