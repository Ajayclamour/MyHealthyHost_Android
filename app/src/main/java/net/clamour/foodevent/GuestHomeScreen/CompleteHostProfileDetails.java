package net.clamour.foodevent.GuestHomeScreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.clamour.foodevent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteHostProfileDetails extends AppCompatActivity {

    String host_firstname,host_lastname,cusine_name,service_name,proffesion_name,address,email,phone,image_url,auth_provider,imageurl1,replaced_servicename,
    city,country,gender_st,currency_st,language_st,description_st,rating_st;


  //  @BindView(R.id.cusine_host)TextView host_cusine;
    @BindView(R.id.ser_host)TextView service_host;
    @BindView(R.id.pro_host)TextView proffesion_host;
    @BindView(R.id.address_host)TextView address_host;
  //  @BindView(R.id.email_host)TextView email_host;
  //  @BindView(R.id.phone_no_host)TextView phone_no_host;
    @BindView(R.id.host_name)TextView host_nmae_host;
    @BindView(R.id.imageView)ImageView imageView;
  //  @BindView(R.id.currency_host)TextView currency;
    @BindView(R.id.lan_host)TextView language;
    @BindView(R.id.gen_host)TextView gender;
    @BindView(R.id.descripton_host)TextView description_host;
    @BindView(R.id.rating_host)RatingBar rating_host;
    @BindView(R.id.viewmore)TextView viewmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_host_profile_details);

        ButterKnife.bind(this);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        toolbar1.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar1.setTitle("HOST PROFILE");


        setSupportActionBar(toolbar1);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Intent intent=getIntent();
        host_firstname=intent.getStringExtra("host_firstname");
        Log.i("host_name",host_firstname);

        host_lastname=intent.getStringExtra("host_lastname");

        address=intent.getStringExtra("address");
        city=intent.getStringExtra("city");
        country=intent.getStringExtra("country");

        cusine_name=intent.getStringExtra("cusine_name");
        Log.i("cusine_name",cusine_name);
        service_name=intent.getStringExtra("service_name");
        Log.i("service_name",service_name);

        gender_st=intent.getStringExtra("gender");
        language_st=intent.getStringExtra("language");
        currency_st=intent.getStringExtra("currency");
        rating_st=intent.getStringExtra("rating");
        rating_host.setRating(Integer.valueOf(rating_st));

       // Log.i("service_name",service_name);

        replaced_servicename=service_name.replaceAll("=>",",");
        Log.i("replacedd",replaced_servicename);

        proffesion_name=intent.getStringExtra("proffession");

        email=intent.getStringExtra("email");
        phone=intent.getStringExtra("mobile_no");
        auth_provider=intent.getStringExtra("auth_provider");
      //  Log.i("auth_provider",auth_provider);
        description_st=intent.getStringExtra("description");


        image_url=intent.getStringExtra("picture");
        Log.i("image_urlll",image_url);

        imageurl1="http://myhealthyhost.com/"+image_url;
        Log.i("image_urlllother",imageurl1);

        Glide.with(CompleteHostProfileDetails.this).load(imageurl1)
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(0)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(CompleteHostProfileDetails.this,ViewMoreHostProfile.class);
                intent1.putExtra("abouthost",description_st);
                startActivity(intent1);
            }
        });

//        if(auth_provider.equals("google")){
//
//
//        }
//
//        else {
//
//            Glide.with(CompleteHostProfileDetails.this).load(image_url)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .placeholder(0)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageView);
//        }



        //host_cusine.setText(cusine_name);
        service_host.setText(replaced_servicename);
        proffesion_host.setText(proffesion_name);
        address_host.setText(address);
      //  email_host.setText(email);
      //  phone_no_host.setText(phone);
        host_nmae_host.setText(host_firstname+" "+host_lastname);
        gender.setText(gender_st);
     //   currency.setText(currency_st);
        language.setText(language_st);
        description_host.setText(description_st);
        description_host.setMovementMethod(new ScrollingMovementMethod());

    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:
                // Respond to the action bar's Up/Home button
                // adapter.notifyDataSetChanged();

                finish();
                // NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
