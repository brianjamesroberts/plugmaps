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

    @Override
    public int hashCode(){
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!MarkerInfo.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final MarkerInfo other = (MarkerInfo) obj;
        if (this.id != other.id) {
            return false;
        }
        if(other.latLng!=null){
            if(this.latLng!=null){
                if(!other.latLng.equals(this.latLng))
                    return false;
            }
        }
        return true;
    }

}
