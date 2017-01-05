package unfairtools.com.plugmaps;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import javax.inject.Inject;

import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Contracts.MapsContract;
import unfairtools.com.plugmaps.UI.ChooseCarFragment;

/**
 * Created by brianroberts on 12/13/16.
 */
public class Repository {


    private WeakHashMap<Integer, BasePresenter> presenterMap;
    //TODO: inject this class into each presenter w/ dagger 12/26/16


    BaseApplication base;

    @Inject
    SQLiteDatabase db;

    public enum PresenterType{
        MAPS_PRESENTER, MAIN_ACTIVITY_PRESENTER
    }

    private Repository(BaseApplication baseApp){
        this.base = baseApp;
        presenterMap = new WeakHashMap<Integer, BasePresenter>();
        this.base.getServicesComponent().inject(this);
        initDB();

    }



    public void getPoints(LatLngBounds latLngBounds){
        Log.e("Get points received", "get pts received");


        MarkerInfo inf = new MarkerInfo();
        inf.name = "Tesla Supercharger";
        inf.latLng = new LatLng(48.0356029d,-123.42074d);
        inf.outletTypes = new ArrayList<MarkerInfo.OutletType>();
        inf.adapterTypes = new ArrayList<MarkerInfo.AdapterType>();
        inf.id = 0;
        MarkerOptions mm = new MarkerOptions().position(inf.latLng).title(inf.name);

        HashMap<MarkerInfo,MarkerOptions> markerOptionsMarkerInfoHashMap = new HashMap<>();
        markerOptionsMarkerInfoHashMap.put(inf,mm);

        sendPoints(markerOptionsMarkerInfoHashMap);
    }


    public void sendPoints(HashMap<MarkerInfo,MarkerOptions> hashMap){
        ((MapsContract.Presenter)presenterMap.get(0)).receivePoints(hashMap);
    }

    public static Repository newInstance(BaseApplication base){
        return new Repository(base);
    }

    public void registerPresenter(PresenterType mCode, BasePresenter mFrag){
        Log.e("Registering", "Registered: Presenter code " + mCode);
        presenterMap.put(mCode.ordinal(), mFrag);
    }

    public void deregisterPresenter(PresenterType mCode){
        Log.e("Deregistering", "Deregistered: Presenter code " + mCode);
        presenterMap.remove(mCode);
    }


    public enum ManufacturerCode{
        TESLA,FORD,FARADAY,CHEVROLET,NISSAN,TOYOTA,VW
    }

    public enum PlugType{
        SAE_COMBO, CHADEMO, NEMA15, NEMA20, NEMA50, TESLA, SAE
    }

    static class CarInfoComplete{
        String manufacturer;
        String model;
        int year;
        ArrayList<PlugType> capablePlugs;
        ArrayList<PlugType> standardPlugs;
        ManufacturerCode manufacturerCode;
    }

    static class LocalAppPrefs{
        static final String table_name = "LOCAL_PREFS";
        static final String id = "ID"; //set to 0
        static final String car_make = "CAR_MAKE";
        static final String car_id = "CAR_ID";
        static final String enabled_plugs = "ENABLED_PLUGS";
        static final String disabled_plugs = "DISABLED_PLUGS";

        public static final String createTableString = "CREATE TABLE " + table_name + "(" +
                id + " INT" + " PRIMARY KEY NOT NULL, " +
                car_make +" TEXT" + ", " +
                car_id + " INT" + ", " +
                enabled_plugs + " TEXT" + ", " +
                disabled_plugs + " TEXT" +  ");";


        static void insertInitialAppPrefs(SQLiteDatabase db){
            Log.e("Repo", "inserting intial app preferences");
            ContentValues cv = new ContentValues();
            cv.put(id, "0");
            cv.put(car_make, "Select your vehicle");
            cv.put(car_id, 0);
            cv.put(enabled_plugs,"");
            cv.put(disabled_plugs,"");
            db.insert(table_name,null,cv);
        }

    }

    void initDB(){

        if(!SQLMethods.doesTableExist(db,LocalAppPrefs.table_name)){
            db.beginTransaction();
            try{
                Log.e("Repo", "Creating " + LocalAppPrefs.table_name + " table");
                db.execSQL(LocalAppPrefs.createTableString);
                LocalAppPrefs.insertInitialAppPrefs(db);
                db.setTransactionSuccessful();
            }catch(Exception e){}finally{
                db.endTransaction();
            }
        }
        if(!SQLMethods.doesTableExist(db,CarSQLStrings.table_name)){
            db.beginTransaction();
            try{
                Log.e("Repo", "Creating " + CarSQLStrings.table_name + " table");
                db.execSQL(CarSQLStrings.createTableString);
                CarSQLStrings.insertInitialCarInfos(db);
                db.setTransactionSuccessful();
            }catch(Exception e){

            }finally{
                db.endTransaction();
            }
        }



    }

    static class CarSQLStrings{


        public static String table_name = "CAR_INFO_TABLE";
        public static String id = "ID";
        public static final String manufacturer = "MANUFACTURER";
        public static final String model = "MODEL";
        public static final String years = "YEAR";
        public static final String capable_plugs = "CAPABLE_PLUGS";
        public static final String standard_plugs = "STANDARD_PLUGS";
        public static final String manufacturer_code = "MANUFACTURER_CODE";

        public static void insertInitialCarInfos(SQLiteDatabase db){
            Log.e("Repo", "Inserting initial Car Infos");
            ContentValues cv = new ContentValues();
            for(int i = 0; i < 4; i ++){
                switch(i){
                    case 0:
                        cv.put(CarSQLStrings.id, 0);
                        cv.put(CarSQLStrings.manufacturer, "");
                        cv.put(model, "");
                        cv. put(CarSQLStrings.years,"");
                        cv. put(CarSQLStrings.capable_plugs, "");
                        cv. put(CarSQLStrings.standard_plugs,
                                PlugType.CHADEMO.ordinal() + ","+ PlugType.NEMA15.ordinal() +PlugType.NEMA20.ordinal() + "," +
                                        PlugType.NEMA50.ordinal() + "," + PlugType.SAE.ordinal() + "," +
                                        PlugType.SAE_COMBO.ordinal()+ "," + PlugType.TESLA);
                        cv. put(CarSQLStrings.manufacturer_code,"");
                        db.insert(CarSQLStrings.table_name,null,cv);
                        break;
                    case 1:
                        cv.put(CarSQLStrings.id, 1);
                        cv.put(CarSQLStrings.manufacturer, "Tesla");
                        cv.put(model, "Model S");
                        cv. put(CarSQLStrings.years,"2012, 2013, 2014, 2015, 2016");
                        cv. put(CarSQLStrings.capable_plugs, PlugType.CHADEMO.ordinal() + "");
                        cv. put(CarSQLStrings.standard_plugs, PlugType.TESLA.ordinal() + "");
                        cv. put(CarSQLStrings.manufacturer_code,ManufacturerCode.TESLA.ordinal() + "");
                        db.insert(CarSQLStrings.table_name,null,cv);
                        break;
                    case 2:
                        cv.put(CarSQLStrings.id, 2);
                        cv.put(CarSQLStrings.manufacturer, "Tesla");
                        cv.put(model, "Model X");
                        cv. put(CarSQLStrings.years,"2014, 2015, 2016");
                        cv. put(CarSQLStrings.capable_plugs, PlugType.CHADEMO.ordinal() + "");
                        cv. put(CarSQLStrings.standard_plugs, PlugType.TESLA.ordinal() + "");
                        cv. put(CarSQLStrings.manufacturer_code,ManufacturerCode.TESLA.ordinal() + "");
                        db.insert(CarSQLStrings.table_name,null,cv);
                        break;
                    case 3:
                        cv.put(CarSQLStrings.id, 3);
                        cv.put(CarSQLStrings.manufacturer, "Tesla");
                        cv.put(model, "Model 3");
                        cv. put(CarSQLStrings.years,"2017");
                        cv. put(CarSQLStrings.capable_plugs, PlugType.CHADEMO.ordinal() + "");
                        cv. put(CarSQLStrings.standard_plugs, PlugType.TESLA.ordinal() + "");
                        cv. put(CarSQLStrings.manufacturer_code,ManufacturerCode.TESLA.ordinal() + "");
                        db.insert(CarSQLStrings.table_name,null,cv);
                        break;
                    default:
                        break;
                }
            }
        }

        public static final String createTableString = "CREATE TABLE " + table_name + "(" + id +
                 " INT" +  " PRIMARY KEY NOT NULL, " +
                manufacturer +  " TEXT " + ", " +
                model +" TEXT" +  ", " +
                years +" TEXT" +  ", " +
                capable_plugs +" TEXT" +  ", " +
                standard_plugs +" TEXT" +  ", " +
                manufacturer_code +" TEXT" +  ");";
    }

    public ArrayList<ChooseCarFragment.ModelDetail> getModelDetailData(String manufacturer){
        ArrayList<ChooseCarFragment.ModelDetail> list = new ArrayList<>();
        switch(manufacturer){
            case "Tesla":
                ChooseCarFragment.ModelDetail md = new ChooseCarFragment.ModelDetail();
                md.brandText = "";
                md.carMakeText = "spacer";
                md.imageResource = 0;
                md.setAnimation = false;
                list.add(md);
                md = new ChooseCarFragment.ModelDetail();
                md.brandText = "Tesla";
                md.carMakeText = "Model S";
                md.imageResource = R.drawable.ic_tesla_model_s;
                md.setAnimation = true;
                list.add(md);
                md = new ChooseCarFragment.ModelDetail();
                md.brandText = "Tesla";
                md.carMakeText = "Model X";
                md.imageResource = R.drawable.ic_tesla_model_s;
                md.setAnimation = false;
                list.add(md);
                md = new ChooseCarFragment.ModelDetail();
                md.brandText = "Tesla";
                md.carMakeText = "Model 3";
                md.imageResource = R.drawable.ic_tesla_model_s;
                md.setAnimation = false;
                list.add(md);
                md = new ChooseCarFragment.ModelDetail();
                md.brandText = "";
                md.carMakeText = "spacer";
                md.imageResource = 0;
                md.setAnimation = false;
                list.add(md);
                break;
            default:
                break;
        }
        return list;
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
                        mData.manufacturerCode = ManufacturerCode.TESLA;
                        mData.img = base.getDrawable(R.mipmap.ic_tesla2);
                    }
                    if (i%5 == 1) {
                        mData.stringInfo = "Toyota ";
                        mData.manufacturerCode = ManufacturerCode.TOYOTA;
                        mData.img = base.getDrawable(R.mipmap.ic_toyota);
                    }

                    if (i%5 == 2){
                        mData.stringInfo = "Chevy ";
                        mData.manufacturerCode = ManufacturerCode.CHEVROLET;
                        mData.img = base.getDrawable(R.mipmap.ic_chevy);
                    }

                    if (i%5 == 4) {
                        mData.stringInfo = "Ford";
                        mData.manufacturerCode = ManufacturerCode.FORD;
                        mData.img = base.getDrawable(R.mipmap.ic_ford);
                    }

                    if (i%5 == 3){
                        mData.stringInfo = "Faraday ";
                        mData.manufacturerCode = ManufacturerCode.FARADAY;
                        mData.img = base.getDrawable(R.mipmap.ic_faraday);
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
