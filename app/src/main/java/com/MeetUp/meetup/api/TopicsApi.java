package com.MeetUp.meetup.api;

import com.MeetUp.meetup.data.db.room.entities.Topic;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopicsApi {

    String API_KEY = "key";
    String MEMBER_ID = "member_id";
    String TOPICS_END_POINT = "/topics?sign=true";

    @GET(TOPICS_END_POINT)
    Observable<List<Topic>> getUserTopics(@Query(MEMBER_ID) String memberId,
                                          @Query(API_KEY) String apiKey);

}
