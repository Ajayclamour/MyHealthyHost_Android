package net.clamour.foodevent.catering;

/**
 * Created by clamour_5 on 2/26/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.clamour.foodevent.GuestHomeScreen.RecentActivityAdapter;
import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.eventlist.GuestEventList;
import net.clamour.foodevent.hostdetails.HostDetails;
import net.clamour.foodevent.webservice.ApiClient;
import net.clamour.foodevent.webservice.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clamour_5 on 12/20/2017.
 */

public class CateringFragment extends android.support.v4.app.Fragment {

    RecentActivityAdapter recentActivityAdapter;
    ArrayList<RecentActivityStorage>catering_array;
    ListView listView;
    ApiInterface apiInterface;
    String outh_id_host,service_id;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.catering_fragment,container,false);
        listView=(ListView)v.findViewById(R.id.list);
        catering_array=new ArrayList<>();

        recentActivityAdapter=new RecentActivityAdapter(getActivity(),catering_array);
        listView.setAdapter(recentActivityAdapter);

        getCAteringEventHost();
        listClick();


        return v;

    }

    public void getCAteringEventHost(){
        Log.i("getinserteddd","getinserteddd");
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<RecentActivityStorage>> call=apiInterface.getDetails2();

        call.enqueue(new Callback<ArrayList<RecentActivityStorage>>() {
            @Override
            public void onResponse(Call<ArrayList<RecentActivityStorage>> call, Response<ArrayList<RecentActivityStorage>> response) {

                catering_array=response.body();
                recentActivityAdapter=new RecentActivityAdapter(getActivity(),catering_array);
                listView.setAdapter(recentActivityAdapter);



                Log.i("socialdinning",catering_array.toString());


                for (int i=0;i<catering_array.size();i++){

                    RecentActivityStorage recentActivityStorage=catering_array.get(i);
                    String first_name=recentActivityStorage.getFirst_name();
                    Log.i("catering", first_name);
                    outh_id_host=recentActivityStorage.getOauth_uid();
                    Log.i("catering_outhid", outh_id_host);
                    service_id=recentActivityStorage.getServiceid();
                    Log.i("service_id",service_id);

                }


            }

            @Override
            public void onFailure(Call<ArrayList<RecentActivityStorage>> call, Throwable t) {

            }
        });
    }
    public void listClick(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                outh_id_host=catering_array.get(position).getOauth_uid();
                service_id=catering_array.get(position).getServiceid();
                Intent intent=new Intent(getActivity(),Catering_eventlist.class);
                intent.putExtra("outh_id_host",outh_id_host);
                intent.putExtra("service_id",service_id);
                startActivity(intent);

            }
        });
    }
}