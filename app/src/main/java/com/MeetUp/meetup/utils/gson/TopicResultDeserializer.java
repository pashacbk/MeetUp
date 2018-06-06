package com.MeetUp.meetup.utils.gson;

import com.MeetUp.meetup.data.db.room.entities.Topic;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;


public class TopicResultDeserializer implements JsonDeserializer<List<Topic>> {

    private static final String RESULTS_KEY = "results";

    @Override
    public List<Topic> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject errorJson = json.getAsJsonObject();
        JsonArray topicArray = errorJson.getAsJsonArray(RESULTS_KEY);
        Gson gson = new Gson();
        return gson.fromJson(topicArray, typeOfT);
    }
}
