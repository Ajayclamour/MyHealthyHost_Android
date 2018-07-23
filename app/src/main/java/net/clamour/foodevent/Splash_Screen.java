package net.clamour.foodevent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.clamour.foodevent.GuestProfile.GuestLogin;
import net.clamour.foodevent.GuestProfile.GuestRegistration;

public class Splash_Screen extends AppCompatActivity {
     ProgressBar spinner;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash__screen);

        spinner = (ProgressBar)findViewById(R.id.progressBar);

        Typeface face1 = Typeface.createFromAsset(getAssets(), "Lato-Bold.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf");

//        textView.setTypeface(face1);

//hi
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(Splash_Screen.this,GuestLogin.class);
                Splash_Screen.this.startActivity(mainIntent);
                Splash_Screen.this.finish();
            }
        }, 1000);
    }
}