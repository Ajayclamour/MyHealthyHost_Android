package net.clamour.foodevent.webservice;



import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.GuestProfile.ForgotPasswordResponse;
import net.clamour.foodevent.GuestProfile.RegistrationResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Clamour on 11/29/2017.
 */

public interface ApiInterface {

    @POST("social_hosts.php")
    Call<ArrayList<RecentActivityStorage>> getDetails();

    @POST("paying_host.php")
    Call<ArrayList<RecentActivityStorage>> getDetails1();


    @POST("catering_host.php")
    Call<ArrayList<RecentActivityStorage>> getDetails2();

    @POST("tiffin_host.php")
    Call<ArrayList<RecentActivityStorage>> getDetails3();

    @POST("registration.php")
    @FormUrlEncoded
    Call<ArrayList<RegistrationResponse>>User_Registration(@Field("firstname") String firstname, @Field("lastname") String lastname, @Field("email") String email, @Field("usertype") String usertype, @Field("password") String password);


    @POST("forgot_pass_check.php")
    @FormUrlEncoded
    Call<ArrayList<ForgotPasswordResponse>>forgotPassword(@Field("email")String email);



//    @Headers("Cache-Control: max-age=640000")
//    @POST("catering_details.php")
//    Call<ArrayList<RecentActivityStorage>> getDetails4();
}

