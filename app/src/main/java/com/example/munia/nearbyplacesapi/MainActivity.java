package com.example.munia.nearbyplacesapi;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL="https://maps.googleapis.com/maps/api/";
    NearbyService service;
    AllPlaceList allPlace;
    private List<AllPlaceList> allPlaceList = new ArrayList<>();


    private RecyclerView recyclerView;
    private AdapterResturentList adapterResturentList;
    TextView warrningTV;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        warrningTV=findViewById(R.id.warrning);

        getData();

    }

    public void getData() {
        String apikey="AIzaSyC60pH6UH4q2SRXBkljpa75kERlSQl8cl4";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create()).build();
        service = retrofit.create(NearbyService.class);
        String endUrl;
        endUrl=String.format("place/nearbysearch/json?location=23.7637103,90.3557239&radius=500&type=restaurant&key=%s",apikey);

        Call<NearbyResponse> call =service.getNearby(endUrl);
        call.enqueue(new Callback<NearbyResponse>()
        {
            @Override
            public void onResponse(Call<NearbyResponse> call, Response<NearbyResponse> response) {
                if(response.code()==200){
                    NearbyResponse nearbyResponse=response.body();

                    int size=nearbyResponse.getResults().size();
                    for(int index=0;index<size;index++) {
                        String name= nearbyResponse.getResults().get(index).getName().toString();
                        String address=nearbyResponse.getResults().get(index).getVicinity().toString();
                        String LatLng=nearbyResponse.getResults().get(index).getGeometry().getLocation().getLat().toString()+","+
                                nearbyResponse.getResults().get(index).getGeometry().getLocation().getLng().toString();

                        allPlace=new AllPlaceList(name,LatLng,address);

                        allPlaceList.add(allPlace);
                        //Toast.makeText(MainActivity.this, name+"    "+" "+address+LatLng+" "+index, Toast.LENGTH_SHORT).show();

                    }
                    setRecyclerView();

                }
                else
                    System.out.println("no data founded");
            }


            @Override
            public void onFailure(Call<NearbyResponse> call, Throwable t) {
                warrningTV.setText("No data Found!!!");
                Toast.makeText(MainActivity.this, "No Record founded!", Toast.LENGTH_SHORT).show();

            }
        }
    );
}
    public void setRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapterResturentList = new AdapterResturentList(allPlaceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapterResturentList);

    }



}