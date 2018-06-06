package com.MeetUp.meetup.utils.rx;


import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestRxSchedulers extends AppRxSchedulers {

    private TestScheduler testScheduler;

    public TestRxSchedulers() {
        super();
        testScheduler = new TestScheduler();
    }

    @Override
    public Scheduler androidUI() {
        return testScheduler;
    }

    @Override
    public Scheduler io() {
        return testScheduler;
    }

    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    @Override
    public Scheduler background() {
        return testScheduler;
    }

    @Override
    public Scheduler network() {
        return testScheduler;
    }

    public TestScheduler getTestScheduler() {
        return testScheduler;
    }
}