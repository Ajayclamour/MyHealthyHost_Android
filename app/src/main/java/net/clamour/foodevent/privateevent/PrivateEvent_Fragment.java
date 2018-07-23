package net.clamour.foodevent.privateevent;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.clamour.foodevent.GuestHomeScreen.RecentActivity;
import net.clamour.foodevent.GuestHomeScreen.RecentActivityAdapter;
import net.clamour.foodevent.GuestHomeScreen.RecentActivityStorage;
import net.clamour.foodevent.R;
import net.clamour.foodevent.eventlist.EventListStorage;
import net.clamour.foodevent.eventlist.GuestEventList;
import net.clamour.foodevent.hostdetails.HostDetails;
import net.clamour.foodevent.webservice.ApiClient;
import net.clamour.foodevent.webservice.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by clamour_5 on 12/20/2017.
 */

public class PrivateEvent_Fragment extends android.support.v4.app.Fragment {

    RecentActivityAdapter recentActivityAdapter;
    ArrayList<RecentActivityStorage>privateevent_array;
    ListView listView;
    ApiInterface apiInterface;
    String outh_id_host,service_id;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.privateevent_fragment,container,false);
        listView=(ListView)v.findViewById(R.id.list);
        privateevent_array=new ArrayList<>();

        recentActivityAdapter=new RecentActivityAdapter(getActivity(),privateevent_array);
        listView.setAdapter(recentActivityAdapter);

        getPrivateEventHost();
        listClick();



        // addData();






        return v;

    }

    public void getPrivateEventHost(){
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<RecentActivityStorage>> call=apiInterface.getDetails1();

        call.enqueue(new Callback<ArrayList<RecentActivityStorage>>() {
            @Override
            public void onResponse(Call<ArrayList<RecentActivityStorage>> call, Response<ArrayList<RecentActivityStorage>> response) {

                privateevent_array=response.body();
                recentActivityAdapter=new RecentActivityAdapter(getActivity(),privateevent_array);
                listView.setAdapter(recentActivityAdapter);

                Log.i("socialdinning",privateevent_array.toString());

            //    RecentActivityStorage recentActivityStorage=response.body();


               // ArrayList<RecentActivityStorage>arrayList=response.body();

//

//                try {
//
//                    JSONArray jsonArray = new JSONArray(response.body());
//                    for (int i=0;i<=jsonArray.length();i++){
//
//                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                        String first=jsonObject.getString("first_name");
//
//                    }
//                }
//                catch (Exception e){
//
//                }

                    for (int i=0;i<privateevent_array.size();i++){

                        RecentActivityStorage recentActivityStorage=privateevent_array.get(i);
                        String first_name=recentActivityStorage.getFirst_name();
                        Log.i("Privateevent", first_name);
                         outh_id_host=recentActivityStorage.getOauth_uid();
                        Log.i("Privateevent_outhid", outh_id_host);
                        service_id=recentActivityStorage.getServiceid();
                        Log.i("service_id",service_id);


                }

//
//                String jsonString = response.body().toString();
//                Gson gson = new Gson();
//                RecentActivityStorage recentActivityStorage = gson.fromJson(jsonString, RecentActivityStorage.class);
//                Log.i("Privateevent", String.valueOf(recentActivityStorage.getFirst_name()));






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

            outh_id_host=privateevent_array.get(position).getOauth_uid();
            service_id=privateevent_array.get(position).getServiceid();
            Intent intent=new Intent(getActivity(), Privateevent_list.class);
            intent.putExtra("outh_id_host",outh_id_host);
            intent.putExtra("service_id",service_id);
            startActivity(intent);

        }
    });
}
}