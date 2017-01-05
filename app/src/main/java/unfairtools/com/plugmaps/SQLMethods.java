package unfairtools.com.plugmaps;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class SQLMethods {


    public static class MarkerOptionsTuple {
        public MarkerOptions marker;
        public Integer id;

    }


//    public static MarkerInfoObject getMarkerInfoLocal(int id,SQLiteDatabase db){
//
//
//
//        MarkerInfoObject returnObj = null;
//
//
//        Cursor resultSet = null;
//        db.beginTransaction();
//        try{
//
//            resultSet = db.rawQuery("Select * from " + Constants.LOCATIONS_INFO_TABLE_NAME + " WHERE " +
//                    Constants.LocationsInfoTable.id_primary_key + " = " + id +";", null);
//            if(resultSet==null)
//                return null;
//            if(resultSet.getCount()<1)
//                return null;
//            resultSet.moveToFirst();
//            returnObj = new MarkerInfoObject();
//
//            returnObj.id_primary_key = resultSet.getInt(0);
//            returnObj.description = resultSet.getString(1);
//            returnObj.website = resultSet.getString(2);
//            returnObj.phone = resultSet.getString(3);
//            returnObj.google_url = resultSet.getString(4);
//            returnObj.season = resultSet.getString(5);
//            returnObj.facilities = resultSet.getString(6);
//
//
//
//            resultSet.close();
//            db.setTransactionSuccessful();
//        }catch(Exception e){
//            e.printStackTrace();
//
//        }finally{
//            db.endTransaction();
//        }
//        return returnObj;
//    }
//
//
//    public static void setMapPrefs(SQLiteDatabase db, LatLng target, float zoom){
//        db.beginTransaction();
//        try{
//
//            //if(!SQLMethods.existsRecord(Constants.LOCATIONS_INFO_TABLE_NAME,Constants.LocationsInfoTable.id_primary_key,id +"",db)) {
//                ContentValues args = new ContentValues();
//                args.put(Constants.MapPreferencesTable.id_primary_key, 0);
//                args.put(Constants.MapPreferencesTable.latitude, target.latitude);
//                args.put(Constants.MapPreferencesTable.longitude, target.longitude);
//                args.put(Constants.MapPreferencesTable.zoom, zoom);
//                db.update(Constants.MAP_PREFERENCES_TABLE_NAME, args, Constants.MapPreferencesTable.id_primary_key +" = 0",null);
//
//
//            db.setTransactionSuccessful();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            db.endTransaction();
//        }
//    }
//
//    public static float getMapLocationZoom(SQLiteDatabase db){
//        float retFloat = 0.0f;
//
//        Cursor resultSet = null;
//
//        db.beginTransaction();
//        try{
//
//            resultSet = db.rawQuery("Select * from " + Constants.MAP_PREFERENCES_TABLE_NAME+ " WHERE " +
//                    Constants.MapPreferencesTable.id_primary_key + " = '0';", null);
//            if(resultSet==null || resultSet.getCount()<1) {
//                Log.e("SQLMethods", "zoom was empty");
//            return retFloat;
//            }
//
//            resultSet.moveToFirst();
//
//            retFloat = resultSet.getFloat(3);
//            resultSet.close();
//            db.setTransactionSuccessful();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            db.endTransaction();
//            return retFloat;
//        }
//
//    }

    public static LatLng getMapLocationLatLng(SQLiteDatabase db){
        LatLng retLatLng = new LatLng(0.0f,0.0f);
        Cursor resultSet = null;
        db.beginTransaction();
        try{

            resultSet = db.rawQuery("Select * from " + Constants.MAP_PREFERENCES_TABLE_NAME + " WHERE " +
                    Constants.MapPreferencesTable.id_primary_key + " = 0;", null);
            if(resultSet==null)
                return retLatLng;
            if(resultSet.getCount()<1)
                return retLatLng;
            resultSet.moveToFirst();
            retLatLng = new LatLng(resultSet.getDouble(1),resultSet.getDouble(2));
            resultSet.close();
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();

        }finally{
            db.endTransaction();
            return retLatLng;
        }

    }
//
//    public static void putLastAuthKey(SQLiteDatabase db, String key){
//        try {
//            db.beginTransaction();
//                ContentValues cv = new ContentValues();
//                cv.put(Constants.LoginTable.id_primary_key,0);
//                cv.put(Constants.LoginTable.key,key);
//                db.update(Constants.LOGIN_TABLE_NAME,cv, Constants.LoginTable.id_primary_key + " = '0'",null);
//            db.setTransactionSuccessful();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            db.endTransaction();
//        }
//
//    }
//    public static String getLastAuthKey(SQLiteDatabase db){
//        Cursor resultSet = null;
//        try {
//            String query = "SELECT * FROM " + Constants.LOGIN_TABLE_NAME
//                    + " WHERE " + Constants.LoginTable.id_primary_key + " = '0';";
//            resultSet = db.rawQuery(query, null);
//            if (resultSet == null)
//                return null;
//            if (resultSet.getCount() < 1) {
//                return null;
//            }
//            resultSet.moveToFirst();
//            return resultSet.getString(1);
//        }finally{
//            if(resultSet!=null)
//                resultSet.close();
//        }
//    }





    public class Constants{
        public final static String LOCATIONS_TABLE_NAME = "LOCATIONS_TABLE";
        public class LocationsTable{
            public final static String id_primary_key = "id";
            public final static String latitude = "latitude";
            public final static String longitude = "longitude";
            public final static String name = "name";
            public final static String type = "type";

        }

        public final static String MAP_PREFERENCES_TABLE_NAME = "MAP_PREFERENCES";
        public class MapPreferencesTable{
            public final static String id_primary_key = "id";
            public final static String longitude = "longitude";
            public final static String latitude = "latitude";
            public final static String zoom = "zoom";
        }

        public final static String LOCATIONS_INFO_TABLE_NAME = "LOCATIONS_INFO";
        public class LocationsInfoTable{
            public final static String id_primary_key = "id";
            public final static String description = "description";
            public final static String website = "website";
            public final static String phone = "phone";
            public final static String google_url = "google_url";
            public final static String season = "season";
            public final static String facilities = "facilities";
        }

        public final static String LOGIN_TABLE_NAME = "LOGIN_TABLE";
        public class LoginTable{
            public final static String id_primary_key = "id";
            public final static String key = "key";
        }

    }


    //working
    public static boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = null;
        try {
            db.beginTransaction();

            cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.close();
                    db.setTransactionSuccessful();
                    return true;
                }
            }
            db.setTransactionSuccessful();
            return false;
        }finally{
            if(cursor != null)
                cursor.close();
            db.endTransaction();
        }
    }

    //working
    public static Boolean existsRecord(String TableName,
                                       String dbfield, String fieldValue, SQLiteDatabase db) {

        String Query = "Select * from " + TableName + " where " + dbfield + "="
                + fieldValue;

        Cursor cursor = db.rawQuery(Query, null);
        if(cursor==null)
            return false;
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public static void initDatabaseCheck(SQLiteDatabase db){

        if(!SQLMethods.doesTableExist(db, Constants.LOGIN_TABLE_NAME)) {
            db.beginTransaction();
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS " +
                            Constants.LOGIN_TABLE_NAME +
                            "("
                            + Constants.LoginTable.id_primary_key + " INTEGER PRIMARY KEY,"
                            + Constants.LoginTable.key + " TEXT"
                            + ");");

            db.setTransactionSuccessful();
            db.endTransaction();

            db.beginTransaction();
            ContentValues args = new ContentValues();
            args.put(SQLMethods.Constants.LoginTable.id_primary_key, 0);
            args.put(Constants.LoginTable.key, "n/a");
            db.insert(Constants.LOGIN_TABLE_NAME, null, args);
            db.setTransactionSuccessful();
            db.endTransaction();
        }

        if(!SQLMethods.doesTableExist(db,SQLMethods.Constants.LOCATIONS_TABLE_NAME)){
            Log.e("MainActivity","Creating table " + SQLMethods.Constants.LOCATIONS_TABLE_NAME);
            db.beginTransaction();
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS " +
                            SQLMethods.Constants.LOCATIONS_TABLE_NAME +
                            "("
                            +  SQLMethods.Constants.LocationsTable.id_primary_key + " INTEGER PRIMARY KEY,"
                            +  SQLMethods.Constants.LocationsTable.latitude +" REAL,"
                            +  SQLMethods.Constants.LocationsTable.longitude +" REAL,"
                            +  SQLMethods.Constants.LocationsTable.name + " VARCHAR,"
                            +  SQLMethods.Constants.LocationsTable.type+" INTEGER"
                            +");");
            db.setTransactionSuccessful();
            db.endTransaction();


        }
        if(!SQLMethods.doesTableExist(db,SQLMethods.Constants.MAP_PREFERENCES_TABLE_NAME)){
            Log.e("MainActivity", "Creating table " + SQLMethods.Constants.MAP_PREFERENCES_TABLE_NAME);
            db.beginTransaction();
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS " +
                            SQLMethods.Constants.MAP_PREFERENCES_TABLE_NAME +
                            "("
                            + SQLMethods.Constants.MapPreferencesTable.id_primary_key + " INTEGER PRIMARY KEY,"
                            + SQLMethods.Constants.MapPreferencesTable.latitude + " REAL,"
                            + SQLMethods.Constants.MapPreferencesTable.longitude + " REAL,"
                            +SQLMethods.Constants.MapPreferencesTable.zoom + " REAL"
                            + ");");
            db.setTransactionSuccessful();
            db.endTransaction();
            db.beginTransaction();
            ContentValues args = new ContentValues();
            args.put(SQLMethods.Constants.MapPreferencesTable.id_primary_key, 0);
            args.put(SQLMethods.Constants.MapPreferencesTable.latitude, 47.51f);
            args.put(SQLMethods.Constants.MapPreferencesTable.longitude, -122.35f);
            args.put(SQLMethods.Constants.MapPreferencesTable.zoom, 6.833f);
            db.insert(SQLMethods.Constants.MAP_PREFERENCES_TABLE_NAME, null, args);//, Constants.LocationsTable.id_primary_key + " = '" + id + "'", null);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        if(!SQLMethods.doesTableExist(db,SQLMethods.Constants.LOCATIONS_INFO_TABLE_NAME)){
            Log.e("MainActivity", "Creating table " + SQLMethods.Constants.LOCATIONS_INFO_TABLE_NAME);
            db.beginTransaction();
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS " +
                            SQLMethods.Constants.LOCATIONS_INFO_TABLE_NAME +
                            "("
                            + SQLMethods.Constants.LocationsInfoTable.id_primary_key + " INTEGER PRIMARY KEY,"
                            + SQLMethods.Constants.LocationsInfoTable.description + " VARCHAR,"
                            + SQLMethods.Constants.LocationsInfoTable.website + " VARCHAR,"
                            + SQLMethods.Constants.LocationsInfoTable.phone + " VARCHAR,"
                            + SQLMethods.Constants.LocationsInfoTable.google_url + " VARCHAR,"
                            + SQLMethods.Constants.LocationsInfoTable.season + " VARCHAR,"
                            + SQLMethods.Constants.LocationsInfoTable.facilities + " VARCHAR"

                            +");"
            );
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }


}
