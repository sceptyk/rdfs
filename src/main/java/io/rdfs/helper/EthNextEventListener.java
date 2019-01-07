package io.rdfs.helper;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class EthNextEventListener<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {}

    @Override
    public void onError(Throwable t) {}

    @Override
    public void onComplete() {}
}
