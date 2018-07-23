package net.clamour.foodevent.GuestHomeScreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.clamour.foodevent.catering.CateringFragment;
import net.clamour.foodevent.privateevent.PrivateEvent_Fragment;
import net.clamour.foodevent.tiffindelivery.TiffinDeliveryFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 1:
                TiffinDeliveryFragment tiffinDeliveryFragment = new TiffinDeliveryFragment();
                return tiffinDeliveryFragment;

            case 2:
                CateringFragment cateringFragment = new CateringFragment();
                return cateringFragment;

            case 3:
                PrivateEvent_Fragment privateEvent_fragment = new PrivateEvent_Fragment();
                return privateEvent_fragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Social Dining";

            case 1:
                return "Tiffin Delivery";

            case 2:
                return "Catering";

            case 3:
                return "Paying Guest";
            default:
                return null;
        }

    }

}
