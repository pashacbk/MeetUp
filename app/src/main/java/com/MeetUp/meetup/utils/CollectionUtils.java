package com.MeetUp.meetup.utils;

import java.util.List;

public class CollectionUtils {

    public static boolean isEmpty(List collections) {
        return collections == null || collections.isEmpty();
    }

    public static boolean isNotEmpty(List collections) {
        return !isEmpty(collections);
    }
}
