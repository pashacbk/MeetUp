package com.MeetUp.meetup.utils.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BasePresenter<V extends BaseView> {

    private V view;
    private CompositeDisposable disposable;


    public V getView() {
        return view;
    }

    public final void attachView(V view) {
        this.view = view;
        disposable = new CompositeDisposable();
        onViewAttached();
    }

    protected void onViewAttached() {
    }

    protected void addDisposable(Disposable disposable) {
        this.disposable.add(disposable);
    }

    public void detachView() {
        if (disposable != null) {
            disposable.clear();
        }
        view = null;
    }
}
