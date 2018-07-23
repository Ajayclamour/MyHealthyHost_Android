package net.clamour.foodevent.sidebar;

/**
 * Created by clamour_5 on 2/2/2018.
 */

public class wishListStorage {
    private int eventImage;
    private String eventName;
    private String HostName;
    private String serviceName;
    private String hostIcon;
    private String price;
   // private int rating;
    private String address;
    private String wishId;
    private String hostid;
    private String cateogryname;
    private String serviceid;

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public String getCateogryname() {
        return cateogryname;
    }

    public void setCateogryname(String cateogryname) {
        this.cateogryname = cateogryname;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getWishId() {
        return wishId;
    }

    public void setWishId(String wishId) {
        this.wishId = wishId;
    }
//    public wishListStorage(String eventName, String HostName, String serviceName, int hostIcon, String price, String address){
//
//        //this.eventImage=eventImage;
//        this.eventName=eventName;
//        this.HostName=HostName;
//        this.serviceName=serviceName;
//        this.hostIcon=hostIcon;
//        this.price=price;
//      //  this.rating=rating;
//        this.address=address;
//
//    }

    public int getEventImage() {
        return eventImage;
    }

    public void setEventImage(int eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public int getRating() {

    public String getHostIcon() {
        return hostIcon;
    }

    public void setHostIcon(String hostIcon) {
        this.hostIcon = hostIcon;
    }

//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
