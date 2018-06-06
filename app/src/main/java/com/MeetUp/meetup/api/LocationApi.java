package com.MeetUp.meetup.api;

import com.MeetUp.meetup.api.models.LocationModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationApi {

    String FIND_LOCATIONS_END_POINT = "/find/locations?&sign=true";
    String API_KEY = "key";
    String QUERY = "query";
    @GET(FIND_LOCATIONS_END_POINT)
    Observable<List<LocationModel>> getLocations(@Query(API_KEY) String apiKey,
                                                 @Query(QUERY) String location);

}
