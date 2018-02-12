package com.example.munia.nearbyplacesapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by munia on 1/24/2018.
 */

public interface NearbyService {

    @GET
    Call<NearbyResponse> getNearby(@Url String urlString);

    @GET
    Call<DistanceClass> getDirection(@Url String urlString);
}
