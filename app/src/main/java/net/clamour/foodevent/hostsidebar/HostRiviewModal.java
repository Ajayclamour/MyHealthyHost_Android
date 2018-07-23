package net.clamour.foodevent.hostsidebar;

/**
 * Created by clamour_5 on 1/31/2018.
 */

public class HostRiviewModal {
    private String gusetname;
    private int rating_count;
    private String comments;
    private String guest_image;
    private String rating_date;
    private String event_name;

//    public HostRiviewModal(String gusetname,int rating_count,String comments,String event_name,String rating_date,int guest_image){
//        this.gusetname=gusetname;
//        this.rating_count=rating_count;
//        this.comments=comments;
//        this.event_name=event_name;
//        this.rating_date=rating_date;
//        this.guest_image=guest_image;
//    }


    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public String getGuest_image() {
        return guest_image;
    }

    public void setGuest_image(String guest_image) {
        this.guest_image = guest_image;
    }

    public String getRating_date() {
        return rating_date;
    }

    public void setRating_date(String rating_date) {
        this.rating_date = rating_date;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getGusetname() {
        return gusetname;
    }

    public void setGusetname(String gusetname) {
        this.gusetname = gusetname;
    }




    public String getComments() {
        return comments;

    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
