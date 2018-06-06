package com.MeetUp.meetup.utils;

import android.util.Log;
import android.util.SparseArray;

import com.MeetUp.meetup.api.response.ErrorResponse;
import com.MeetUp.meetup.utils.exceptions.NoNetworkException;
import com.MeetUp.meetup.utils.gson.ApiErrorDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ApiErrorHandler {

    private static final String TAG = ApiErrorHandler.class.getSimpleName();
    private static final int GENERAL_ERROR_CODE = -1;
    private static final int NO_NETWORK_ERROR_CODE = -2;
    private static final String LINE_FEED = "\n";
    private final SparseArray<String> errorMap;
    private final Gson gson;

    public ApiErrorHandler(SparseArray<String> errorMap, Gson gson) {
        this.errorMap = errorMap;
        this.gson = gson;
    }

    public String handleError(Throwable t) {
        String errorMessage;
        if (t instanceof NoNetworkException) {
            errorMessage = handleErrorByErrorCode(NO_NETWORK_ERROR_CODE);
        } else if (t instanceof HttpException) {
            errorMessage = handleHttpException(((HttpException) t));
        } else {
            errorMessage = handleErrorByErrorCode(GENERAL_ERROR_CODE);
        }
        return errorMessage;
    }

    private String handleHttpException(HttpException httpException) {
        String errorMessage;
        ResponseBody errorBody = httpException.response().errorBody();
        if (errorBody != null) {
            errorMessage = tryToParseErrorBody(errorBody, httpException.code());
        } else {
            errorMessage = handleErrorByErrorCode(httpException.code());
        }
        return errorMessage;
    }

    private String tryToParseErrorBody(ResponseBody errorBody, int errorCode) {
        StringBuilder errorMessage = new StringBuilder();
        List<ErrorResponse> errors = new ArrayList<>();
            try {
                String error = errorBody.string();
                Type errorListType = new TypeToken<List<ErrorResponse>>() {
                }.getType();
                errors = gson.fromJson(error, errorListType);
            } catch (IOException | JsonSyntaxException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        if (CollectionUtils.isNotEmpty(errors)) {
            for (ErrorResponse error : errors) {
                errorMessage.append(error.getMessage()).append(LINE_FEED);
            }
        } else {
            errorMessage.append(handleErrorByErrorCode(errorCode));
        }
        return errorMessage.toString();
    }

    private String handleErrorByErrorCode(int errorCode) {
        String errorMessage = errorMap.get(errorCode);
        if (errorMessage == null) {
            errorMessage = errorMap.get(GENERAL_ERROR_CODE);
        }
        return errorMessage;
    }
}
