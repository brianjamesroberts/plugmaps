package unfairtools.com.plugmaps;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by brianroberts on 12/27/16.
 */

public class MarkerInfo {

    public enum OutletType{
        JC22
    }

    public enum AdapterType{
        JC22_to_BP49
    }

    public String name;
    public LatLng latLng;
    public ArrayList<OutletType> outletTypes;
    public ArrayList<AdapterType> adapterTypes;
    public int id;

}
