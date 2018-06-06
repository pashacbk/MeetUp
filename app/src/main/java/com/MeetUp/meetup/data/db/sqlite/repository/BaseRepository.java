package com.MeetUp.meetup.data.db.sqlite.repository;

import com.MeetUp.meetup.data.db.sqlite.specification.SqlSpecification;

import java.util.List;

public interface BaseRepository<T> {

    void add(T item);

    void add(List<T> item);

    void update(T item);

    void remove(T item);

    List<T> query(SqlSpecification specification);

}
