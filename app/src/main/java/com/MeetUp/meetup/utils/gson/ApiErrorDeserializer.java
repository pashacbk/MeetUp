package com.MeetUp.meetup.utils.gson;

import com.MeetUp.meetup.api.response.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;
import java.util.List;


public class ApiErrorDeserializer implements JsonDeserializer<List<ErrorResponse>> {

    private static final String ERRORS_KEY = "errors";

    @Override
    public List<ErrorResponse> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject errorJson = json.getAsJsonObject();
        JsonArray errorArray = errorJson.getAsJsonArray(ERRORS_KEY);
        Gson gson = new Gson();
        return gson.fromJson(errorArray, typeOfT);
    }
}
