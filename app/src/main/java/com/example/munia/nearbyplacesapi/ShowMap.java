package com.example.munia.nearbyplacesapi;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowMap extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap map;
    private GoogleMapOptions options;
    NearbyService service;
    private static final String BASE_URL="https://maps.googleapis.com/maps/api/";
    String dsetinationLatlng;
    String presentLatlng="23.7637103,90.3557239";
    LatLng deslatLng;
    String distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        Bundle extras = getIntent().getExtras();

        dsetinationLatlng  = extras.getString("latlng");
        String string = dsetinationLatlng;
        String[] parts = string.split(",");
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        deslatLng = new LatLng(Double.parseDouble(part1),Double.parseDouble(part2));
        // Toast.makeText(this, dsetinationLatlng,Toast.LENGTH_LONG).show();


        options=new GoogleMapOptions();
        options.zoomControlsEnabled(true);
        SupportMapFragment mapFragment=SupportMapFragment.newInstance(options);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapContainer,mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
        getData();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.addMarker(new MarkerOptions().position(deslatLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(deslatLng,18));
    }

    public void getData() {

        String apikey = getString(R.string.distance_api_key);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create()).build();
        service = retrofit.create(NearbyService.class);
        String endUrl;
        endUrl = String.format("distancematrix/json?units=imperial&origins=%s&destinations=%s&key=%s", presentLatlng, dsetinationLatlng, apikey);
        Call<DistanceClass> call = service.getDirection(endUrl);
        call.enqueue(new Callback<DistanceClass>() {
            @Override
            public void onResponse(Call<DistanceClass> call, Response<DistanceClass> response) {
                if (response.code() == 200) {
                    DistanceClass distanceClass = response.body();
                    distance = distanceClass.getRows().get(0).getElements().get(0).getDistance().getText().toString();
                    showDialogue();
                    //Toast.makeText(ShowMap.this, distance, Toast.LENGTH_SHORT).show();


                } else
                    System.out.println("NO DATAAAAA");
            }

            @Override
            public void onFailure(Call<DistanceClass> call, Throwable t) {
                Toast.makeText(ShowMap.this, "No Record founded!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showDialogue(){

        AlertDialog alertDialog = new AlertDialog.Builder(ShowMap.this).create();
        alertDialog.setTitle("Distance");
        alertDialog.setMessage("This place is: "+distance+ "le far from you.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }


}