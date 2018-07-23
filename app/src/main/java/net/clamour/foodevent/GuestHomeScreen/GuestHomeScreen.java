package net.clamour.foodevent.GuestHomeScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.MarkerOptions;

import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.GuestProfile.GuestProfileScreen;
import net.clamour.foodevent.HostProfile.HostSplash;
import net.clamour.foodevent.R;
import net.clamour.foodevent.booking.GuestBooking;
import net.clamour.foodevent.guestsearch.EventsNearMe;
import net.clamour.foodevent.guestsearch.SearchActivity;
import net.clamour.foodevent.sidebar.AboutUs;
import net.clamour.foodevent.sidebar.ContactUs;
import net.clamour.foodevent.sidebar.HowitWorks;
import net.clamour.foodevent.sidebar.InboxGuest;
import net.clamour.foodevent.sidebar.MyHealthyExperience;
import net.clamour.foodevent.sidebar.WishlistGuest;

import java.util.Arrays;

public class GuestHomeScreen extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private TabLayout mTabLayout;

    AlertDialog alertDialog;
    RatingBar ratingBar;
    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    SharedPreferences profile_prefrences;

    String auth_id,first_name,last_name,complete_name;
    ImageView search_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home_screen);

        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);
        profile_prefrences= this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);


        auth_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_idregistration",auth_id);

        auth_id=login_prefrence.getString("outh_id","");
        Log.i("auth_idlogin",auth_id);

        first_name=Registration_preferences.getString("first_name","");
        last_name=Registration_preferences.getString("last_name","");

        complete_name=first_name+" "+last_name;
        Log.i("complete_name",complete_name);

        first_name=login_prefrence.getString("first_name","");
        last_name=login_prefrence.getString("last_name","");



        complete_name=first_name+" "+last_name;
        Log.i("complete_name",complete_name);



        search_icon=(ImageView)findViewById(R.id.search_icon);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GuestHomeScreen.this,SearchActivity.class);
                startActivity(intent);
//                try {
//                    Intent intent =
//                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
//                                    .build(GuestHomeScreen.this);
//                    startActivityForResult(intent, 1);
//                } catch (GooglePlayServicesRepairableException e) {
//                    // TODO: Handle the error.
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    // TODO: Handle the error.
//                }
            }


        });


//        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(GuestHomeScreen.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });

        ListView lt = (ListView) findViewById(R.id.left_drawer);
        String St[] = {"","","Home","My Profile","My Orders","Favourites","Contact Us","About MHH","How it Works","Refer to Friend","Logout"};
        Integer imgs[] = {0,0,R.drawable.ic_home_black_24dp,R.drawable.ic_person_black_24dp,R.drawable.ic_event_black_24dp,R.drawable.ic_favorite_black_24dp,R.drawable.ic_contact_phone_black_24dp,R.drawable.ic_info_black_24dp,R.drawable.ic_lightbulb_outline_black_24dp,R.drawable.ic_share_black_24dp,R.drawable.ic_exit_to_app_black_24dp};


        CustomAdapterDrawer CAD = new CustomAdapterDrawer(this, St, imgs);
        lt.setAdapter(CAD);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar1.setTitleTextColor(Color.WHITE);
        toolbar1.setTitle("My Healthy Host");

        lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){

                    Intent intent=new Intent(GuestHomeScreen.this,HostSplash.class);
                    startActivity(intent);
                }

                if(position==2){
                    Intent intent=new Intent(GuestHomeScreen.this,GuestHomeScreen.class);
                    startActivity(intent);
                }
                if(position==3){

                    Intent intent=new Intent(GuestHomeScreen.this, GuestProfileScreen.class);
                    startActivity(intent);
                }

                if(position==4){
                   Intent intent=new Intent(GuestHomeScreen.this,MyHealthyExperience.class);
                    startActivity(intent);

                }
//                if(position==5){
//                   // Intent intent=new Intent(GuestHomeScreen.this,GuestRiview.class);
//                    //startActivity(intent);
//                    rateus();
//                }
//                if(position==6){
//                    Intent intent=new Intent(GuestHomeScreen.this,InboxGuest.class);
//                    startActivity(intent);
//
//                }
                if(position==5){
                    Intent intent=new Intent(GuestHomeScreen.this,WishlistGuest.class);
                    startActivity(intent);
                }
                if(position==6){
                    Intent intent=new Intent(GuestHomeScreen.this,ContactUs.class);
                    startActivity(intent);
                }
                if(position==7){
                    Intent intent=new Intent(GuestHomeScreen.this,AboutUs.class);
                    startActivity(intent);
                }

                if(position==8){
                    Intent intent=new Intent(GuestHomeScreen.this,HowitWorks.class);
                    startActivity(intent);
                }
                if(position==9){
                    shareApp();
                }
                if(position==10){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            GuestHomeScreen.this);

                    // set title
                    alertDialogBuilder.setTitle("                    Alert!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("         Are you really want to logout")
                            .setCancelable(false)
                            .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    Registration_preferences.edit().remove("outh_id").apply();
                                    login_prefrence.edit().remove("outh_id").apply();
                                    profile_prefrences.edit().remove("outh_id").apply();


                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();


//                                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                                    intent.addCategory(Intent.CATEGORY_HOME);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                    finish();

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                }

            }
        });



        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar1,
                R.string.app_name,
                R.string.app_name
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //Set the custom toolbar
        if (toolbar1 != null) {
            setSupportActionBar(toolbar1);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle.syncState();

        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;

        }
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void rateus(){
        Intent intent1 = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id="
                        + GuestHomeScreen.this.getPackageName()));
        startActivity(intent1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                Intent intent=new Intent(GuestHomeScreen.this, EventsNearMe.class);
                startActivity(intent);

//                ((TextView) findViewById(R.id.searched_address)).setText(place.getName()+",\n"+
//                        place.getAddress() +"\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
public void shareApp(){
    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    String shareBody = "Here is the share content body";
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
    startActivity(Intent.createChooser(sharingIntent, "Share via"));
}
}

