package ae.qmatic.tacme.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by nikunjkumar on 8/18/16.
 */
public class MyMarkers {
    String branchId;
    String branchName;
    LatLng latLong;
    String branchAddress;
    boolean isBranchOpen;
    String branchWorkingDays;
    String branchTiming;


    public MyMarkers(String bId, String bName, LatLng ll, String bAddress,  boolean isOpen, String bWorkingDays, String bTiming){
        this.branchId = bId;
        this.branchName = bName;
        this.latLong = ll;
        this.branchAddress = bAddress;
        this.isBranchOpen = isOpen;
        this.branchWorkingDays = bWorkingDays;
        this.branchTiming = bTiming;

    }

    /// BRANCH ID
    public String getBranchId(){
        return branchId;
    }
    public void setBranchId(String bName){
        this.branchId = branchId;
    }

    /// BRANCH NAME
    public String getBranchName(){
        return branchName;
    }
    public void setBranchName(String bName){
        this.branchName = bName;
    }


    /// LAT - LONG
    public LatLng getLatLong(){
        return latLong;
    }
    public void setLat(LatLng latlong){
        this.latLong = latlong;
    }


    ////// BRANCH ADDRESS
    public void setBranchAddress(String bAddress){
        this.branchAddress = bAddress;
    }
    public String getBranchAddress(){
        return branchAddress;
    }


    /// IS BRANCH OPEN - STATUS
    public boolean getIsBranchOpen(){
        return isBranchOpen;
    }
    public void setIsBranchOpen(boolean isOpen){
        this.isBranchOpen = isOpen;
    }


    ////// BRANCH WORKING DAYS
    public void setBranchWorkingDays(String branchWorkingDays){
        this.branchWorkingDays = branchWorkingDays;
    }
    public String getBranchWorkingDays(){
        return branchWorkingDays;
    }

    ////// BRANCH TIMING
    public void setBranchTiming(String bTiming){
        this.branchTiming = bTiming;
    }
    public String getBranchTiming(){
        return branchTiming;
    }



}
