package net.clamour.foodevent.hostsidebar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.clamour.foodevent.GuestHomeScreen.CustomAdapterDrawer;
import net.clamour.foodevent.HostProfile.HostHomeScreen;
import net.clamour.foodevent.R;
import net.clamour.foodevent.sidebar.AboutUs;
import net.clamour.foodevent.sidebar.ContactUs;
import net.clamour.foodevent.sidebar.GuestSwitchsplash;
import net.clamour.foodevent.sidebar.HowitWorks;

public class DrawerHostBaseActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences login_prefrence;
    SharedPreferences Registration_preferences;
    SharedPreferences profile_prefrences;
    SharedPreferences profile_prefrences_host;

    String auth_id,first_name,last_name,complete_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_host_base);

        setDrawer();
        Registration_preferences = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.Submit_Code_Screen", MODE_PRIVATE);
        login_prefrence = this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestLogin", MODE_PRIVATE);
        profile_prefrences= this.getSharedPreferences("net.clamour.foodevent.GuestProfile.GuestProfileScreen", MODE_PRIVATE);

        auth_id=Registration_preferences.getString("outh_id","");
        Log.i("auth_idregistration",auth_id);

        auth_id=login_prefrence.getString("outh_id","");
        Log.i("auth_idlogin",auth_id);



    }

    public void setDrawer(){
        ListView lt = (ListView) findViewById(R.id.left_drawer);
        String St[] = {"","","My Profile","Review & Comments","Orders","My Services","Contact Us","About MHH","How it Works","Refer to Friend","Logout"};
        Integer imgs[] = {0,0,R.drawable.host_profile,R.drawable.host_rating,R.drawable.order,R.drawable.myservices,R.drawable.ic_contact_phone_black_24dp,R.drawable.ic_info_black_24dp,R.drawable.ic_lightbulb_outline_black_24dp,R.drawable.ic_share_black_24dp,R.drawable.logout_host,};


        CustomAdapterDrawerHost CAD = new CustomAdapterDrawerHost(this, St, imgs);
        lt.setAdapter(CAD);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
       // toolbar1.setTitleTextColor(Color.WHITE);
        //toolbar1.setTitle("My Healthy Host");

        lt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){

                    Intent intent=new Intent(DrawerHostBaseActivity.this,GuestSwitchsplash.class);
                    startActivity(intent);
                }

//                if(position==2){
//                    Intent intent=new Intent(DrawerHostBaseActivity.this,HostHomeScreen.class);
//                    startActivity(intent);
//                }
                if(position==2){

                    Intent intent=new Intent(DrawerHostBaseActivity.this, HostProfile.class);
                    startActivity(intent);
                }

                if(position==3){
                    Intent intent=new Intent(DrawerHostBaseActivity.this,RiviewRatingsHostList.class);
                    startActivity(intent);

                }
                if(position==4){
                    Intent intent=new Intent(DrawerHostBaseActivity.this,Ordermanagement.class);
                    startActivity(intent);

                }

                if(position==5){
//                    Intent intent=new Intent(DrawerHostBaseActivity.this,Ordermanagement.class);
//                    startActivity(intent);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse("http://myhealthyhost.com/login.php"));
                    startActivity(browserIntent);


                }

                if(position==6){
                    Intent intent=new Intent(DrawerHostBaseActivity.this,ContactUs.class);
                    startActivity(intent);

                }
                if(position==7){
                    Intent intent=new Intent(DrawerHostBaseActivity.this,AboutUs.class);
                    startActivity(intent);

                }
                if(position==8){
                    Intent intent=new Intent(DrawerHostBaseActivity.this,HowitWorks.class);
                    startActivity(intent);

                }

                if(position==9){
                    shareApp();

                }


                if(position==10){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            DrawerHostBaseActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Are you really want to logut")
                            .setCancelable(false)
                            .setPositiveButton("YES",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                   // login_prefrence.edit().remove("outh_id").apply();
                                    Registration_preferences.edit().remove("outh_id").apply();
                                    login_prefrence.edit().remove("outh_id").apply();
                                    profile_prefrences.edit().remove("outh_id").apply();

                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

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

    public void shareApp(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}