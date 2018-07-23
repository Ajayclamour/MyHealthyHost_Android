package net.clamour.foodevent.guestsearch;

/**
 * Created by clamour_5 on 1/11/2018.
 */

public class EventsNearMeStorage {

    private int event_image_currentloc;
    private int event_host_icon_currentloc;
    private String event_name_currentloc;
    private String event_price_currentloc;
    private String event_host_name_currentloc;



    public EventsNearMeStorage(int event_image_currentloc, int event_host_icon_currentloc, String event_name_currentloc, String event_price_currentloc, String event_host_name_currentloc){

        this.event_image_currentloc=event_image_currentloc;
        this.event_host_icon_currentloc=event_host_icon_currentloc;
        this.event_name_currentloc=event_name_currentloc;
        this.event_price_currentloc=event_price_currentloc;
        this.event_host_name_currentloc=event_host_name_currentloc;

    }
    public String getEvent_host_name_currentloc() {
        return event_host_name_currentloc;
    }

    public void setEvent_host_name_currentloc(String event_host_name_currentloc) {
        this.event_host_name_currentloc = event_host_name_currentloc;
    }
    public int getEvent_image_currentloc() {
        return event_image_currentloc;
    }

    public void setEvent_image_currentloc(int event_image_currentloc) {
        this.event_image_currentloc = event_image_currentloc;
    }

    public int getEvent_host_icon_currentloc() {
        return event_host_icon_currentloc;
    }

    public void setEvent_host_icon_currentloc(int event_host_icon_currentloc) {
        this.event_host_icon_currentloc = event_host_icon_currentloc;
    }

    public String getEvent_name_currentloc() {
        return event_name_currentloc;
    }

    public void setEvent_name_currentloc(String event_name_currentloc) {
        this.event_name_currentloc = event_name_currentloc;
    }

    public String getEvent_price_currentloc() {
        return event_price_currentloc;
    }

    public void setEvent_price_currentloc(String event_price_currentloc) {
        this.event_price_currentloc = event_price_currentloc;
    }
}
