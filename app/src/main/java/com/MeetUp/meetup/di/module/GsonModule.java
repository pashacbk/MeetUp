package com.MeetUp.meetup.di.module;

import com.MeetUp.meetup.api.response.ErrorResponse;
import com.MeetUp.meetup.data.db.room.entities.Topic;
import com.MeetUp.meetup.di.ApplicationScope;
import com.MeetUp.meetup.utils.gson.ApiErrorDeserializer;
import com.MeetUp.meetup.utils.gson.TopicResultDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

    private static final String TOPIC_TYPE = "topic_type";
    private static final String ERROR_TYPE = "error_type";
    private static final String TOPIC_DESERIALIZER = "topic_deserializer";
    private static final String ERROR_DESERIALIZER = "error_deserializer";

    @Provides
    @ApplicationScope
    Gson providesGsonInstance(@Named(TOPIC_TYPE) Type topicType,
                              @Named(TOPIC_DESERIALIZER) TopicResultDeserializer topicResultDeserializer,
                              @Named(ERROR_TYPE) Type errorType,
                              @Named(ERROR_DESERIALIZER) ApiErrorDeserializer errorDeserializer) {
        return new GsonBuilder()
                .registerTypeAdapter(topicType, topicResultDeserializer)
                .registerTypeAdapter(errorType, errorDeserializer)
                .create();
    }

    @Provides
    @ApplicationScope
    @Named(TOPIC_TYPE)
    Type providesTopicType() {
        return new TypeToken<List<Topic>>() {
        }.getType();
    }

    @Provides
    @ApplicationScope
    @Named(TOPIC_DESERIALIZER)
    TopicResultDeserializer providesTopicDeserializer() {
        return new TopicResultDeserializer();
    }

    @Provides
    @ApplicationScope
    @Named(ERROR_TYPE)
    Type providesErrorType() {
        return new TypeToken<List<ErrorResponse>>() {
        }.getType();
    }

    @Provides
    @ApplicationScope
    @Named(ERROR_DESERIALIZER)
    ApiErrorDeserializer providesErrorDeserializer() {
        return new ApiErrorDeserializer();
    }

}
