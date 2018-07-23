package net.clamour.foodevent.eventlist;

/**
 * Created by clamour_5 on 12/21/2017.
 */

public class EventListStorage {
    private String id;
    private String oauth_uid;
    private String cat_coverimg;
    private String picture;
    private String cat_title;
    private String address;
    private String cat_price;
    private String firstName;
    private String lastName;
    private String Experience;
    private String Description;
    private String Cuisine;
    private String tiffin_date;
    private String product_id;
    private String availability_timing;
    private String duration;
    private String recieving_timing;
    private String special_diets;
    private String pay_place;
    private String payfacilities;
    private String pay_date;

    public String getPay_place() {
        return pay_place;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public void setPay_place(String pay_place) {
        this.pay_place = pay_place;
    }

    public String getPayfacilities() {
        return payfacilities;
    }

    public void setPayfacilities(String payfacilities) {
        this.payfacilities = payfacilities;
    }



    public String getSpecial_diets() {
        return special_diets;
    }

    public void setSpecial_diets(String special_diets) {
        this.special_diets = special_diets;
    }

    public String getAvailability_timing() {
        return availability_timing;

    }

    public void setAvailability_timing(String availability_timing) {
        this.availability_timing = availability_timing;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRecieving_timing() {
        return recieving_timing;
    }

    public void setRecieving_timing(String recieving_timing) {
        this.recieving_timing = recieving_timing;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getMinimum_guest() {
        return minimum_guest;
    }

    public void setMinimum_guest(String minimum_guest) {
        this.minimum_guest = minimum_guest;
    }

    public String getMaximum_guest() {
        return maximum_guest;
    }

    public void setMaximum_guest(String maximum_guest) {
        this.maximum_guest = maximum_guest;
    }

    private String menu;
    private String instruction;
    private String minimum_guest;
    private String maximum_guest;
//    private String rating_count;

    //searcheventlist
    private String search_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    private String search_outhid;
    private String search_catcoverimage;
    private String search_picture;
    private String searchevent_address;

    public String getServiceid_serach() {
        return serviceid_serach;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSearchevent_address() {
        return searchevent_address;
    }

    public void setSearchevent_address(String searchevent_address) {
        this.searchevent_address = searchevent_address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public String getTiffin_date() {
        return tiffin_date;
    }

    public void setTiffin_date(String tiffin_date) {
        this.tiffin_date = tiffin_date;
    }

    public void setServiceid_serach(String serviceid_serach) {
        this.serviceid_serach = serviceid_serach;

    }

    public String getSearch_cateogry() {
        return search_cateogry;
    }

    public void setSearch_cateogry(String search_cateogry) {
        this.search_cateogry = search_cateogry;
    }

    private String search_cat_title;
    private String search_address;
    private String search_cat_price;
    private String serviceid_serach;
    private String search_cateogry;
    private String search_hostId;


    public String getSearch_hostId() {
        return search_hostId;
    }

    public void setSearch_hostId(String search_hostId) {
        this.search_hostId = search_hostId;
    }

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    public String getSearch_outhid() {
        return search_outhid;
    }

    public void setSearch_outhid(String search_outhid) {
        this.search_outhid = search_outhid;
    }

    public String getSearch_catcoverimage() {
        return search_catcoverimage;
    }

    public void setSearch_catcoverimage(String search_catcoverimage) {
        this.search_catcoverimage = search_catcoverimage;
    }

    public String getSearch_picture() {
        return search_picture;
    }

    public void setSearch_picture(String search_picture) {
        this.search_picture = search_picture;
    }

    public String getSearch_cat_title() {
        return search_cat_title;
    }

    public void setSearch_cat_title(String search_cat_title) {
        this.search_cat_title = search_cat_title;
    }

    public String getSearch_address() {
        return search_address;
    }

    public void setSearch_address(String search_address) {
        this.search_address = search_address;
    }

    public String getSearch_cat_price() {
        return search_cat_price;
    }

    public void setSearch_cat_price(String search_cat_price) {
        this.search_cat_price = search_cat_price;
    }


    //MyHealthyExperienceStorage
    private String eventImageHistory;
    private String hostImageHistory;
    private String eventNameHistory;
    private String HostNameHistory;
    private String eventPriceHistory;

    public String getHostIdHistory() {
        return HostIdHistory;
    }

    public void setHostIdHistory(String hostIdHistory) {
        HostIdHistory = hostIdHistory;
    }

    private String HostIdHistory;

    public String getEventImageHistory() {
        return eventImageHistory;
    }

    public void setEventImageHistory(String eventImageHistory) {
        this.eventImageHistory = eventImageHistory;
    }

    public String getHostImageHistory() {
        return hostImageHistory;
    }

    public void setHostImageHistory(String hostImageHistory) {
        this.hostImageHistory = hostImageHistory;
    }

    public String getEventNameHistory() {
        return eventNameHistory;
    }

    public void setEventNameHistory(String eventNameHistory) {
        this.eventNameHistory = eventNameHistory;
    }

    public String getHostNameHistory() {
        return HostNameHistory;
    }

    public void setHostNameHistory(String hostNameHistory) {
        HostNameHistory = hostNameHistory;
    }

    public String getEventPriceHistory() {
        return eventPriceHistory;
    }

    public void setEventPriceHistory(String eventPriceHistory) {
        this.eventPriceHistory = eventPriceHistory;
    }

    public String getCat_coverimg() {
        return cat_coverimg;
    }

    public void setCat_coverimg(String cat_coverimg) {
        this.cat_coverimg = cat_coverimg;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOauth_uid() {
        return oauth_uid;
    }

    public void setOauth_uid(String oauth_uid) {
        this.oauth_uid = oauth_uid;
    }



    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCat_price() {
        return cat_price;
    }

    public void setCat_price(String cat_price) {
        this.cat_price = cat_price;
    }

//guestRiviewDescription
    private String guestname_riview;
    private String riview_date;
    private String riview_message;
    private String guest_image_riview;

    public String getGuestname_riview() {
        return guestname_riview;
    }

    public void setGuestname_riview(String guestname_riview) {
        this.guestname_riview = guestname_riview;
    }

    public String getRiview_date() {
        return riview_date;
    }

    public String getGuest_image_riview() {
        return guest_image_riview;
    }

    public void setGuest_image_riview(String guest_image_riview) {
        this.guest_image_riview = guest_image_riview;
    }

    public void setRiview_date(String riview_date) {

        this.riview_date = riview_date;
    }

    public String getRiview_message() {
        return riview_message;
    }

    public void setRiview_message(String riview_message) {
        this.riview_message = riview_message;
    }
}
