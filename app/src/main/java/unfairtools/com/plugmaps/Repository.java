package unfairtools.com.plugmaps;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.WeakHashMap;

import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;

/**
 * Created by brianroberts on 12/13/16.
 */
public class Repository {


    private WeakHashMap<Integer, BasePresenter> presenterMap;
    //TODO: inject this class into each presenter w/ dagger 12/26/16


    Context context;

    private Repository(Context ctx){
        this.context = ctx;
        presenterMap = new WeakHashMap<Integer, BasePresenter>();
    }

    public void getPoints(){
        Log.e("Get points received", "get pts receigved");
        sendPoints();
//        System.exit(0);
    }

    public void sendPoints(){
        ((MapsContract.Presenter)presenterMap.get(0)).receivePoints(new HashSet<MarkerOptions>());
    }

    public static Repository newInstance(Context c){
        return new Repository(c);
    }

    public void registerPresenter(int mCode, BasePresenter mFrag){
        presenterMap.put(mCode, mFrag);
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
