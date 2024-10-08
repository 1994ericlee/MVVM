package com.eric.mvvm.base;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {}

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {}

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e);
}
