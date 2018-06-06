package com.MeetUp.meetup.api;

import com.MeetUp.meetup.data.db.room.entities.Group;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GroupsApi {

    String API_KEY = "key";
    String LONGITUDE = "lon";
    String LATITUDE = "lat";
    String TOPIC_ID = "topic_id";
    String SELF_GROUPS_END_POINT = "/self/groups?sign=true";
    String FIND_GROUPS_END_POINT = "/find/groups?sign=true";

    @GET(SELF_GROUPS_END_POINT)
    Observable<List<Group>> getUserGroups(@Query(API_KEY) String apiKey);

    @GET(FIND_GROUPS_END_POINT)
    Observable<List<Group>> getGroupsByTopicId(@Query(API_KEY) String apiKey,
                                               @Query(LONGITUDE) String longitude,
                                               @Query(LATITUDE) String latitude,
                                               @Query(TOPIC_ID) String topicId,
                                               @Query("page") int pageCount);
}
