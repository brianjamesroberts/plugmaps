package unfairtools.com.plugmaps;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
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

    public void getPoints(LatLngBounds latLngBounds){
        Log.e("Get points received", "get pts receigved");


        MarkerInfo inf = new MarkerInfo();
        inf.name = "Tesla Supercharger";
        inf.latLng = new LatLng(48.0356029d,-123.42074d);
        inf.outletTypes = new ArrayList<MarkerInfo.OutletType>();
        inf.adapterTypes = new ArrayList<MarkerInfo.AdapterType>();
        inf.id = 0;
        MarkerOptions mm = new MarkerOptions().position(inf.latLng).title(inf.name);

        HashMap<MarkerOptions,MarkerInfo> markerOptionsMarkerInfoHashMap = new HashMap<>();
        markerOptionsMarkerInfoHashMap.put(mm,inf);

        sendPoints(markerOptionsMarkerInfoHashMap);
    }


    public void sendPoints(HashMap<MarkerOptions,MarkerInfo> hashMap){
        ((MapsContract.Presenter)presenterMap.get(0)).receivePoints(hashMap);
    }

    public static Repository newInstance(Context c){
        return new Repository(c);
    }

    public void registerPresenter(int mCode, BasePresenter mFrag){
        Log.e("Registering", "Registered: Presenter code " + mCode);
        presenterMap.put(mCode, mFrag);
    }

    public void deregisterPresenter(int mCode){
        Log.e("Deregistering", "Deregistered: Presenter code " + mCode);
        presenterMap.remove(mCode);
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
