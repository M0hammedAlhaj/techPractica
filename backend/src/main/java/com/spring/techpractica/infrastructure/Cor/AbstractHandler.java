package com.spring.techpractica.infrastructure.Cor;

public abstract class AbstractHandler<T> implements Handler<T> {
    private Handler<T> nextHandler;

    @Override
    public void setNext(Handler<T> nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(T request) {
        process(request);
        if (nextHandler != null) {
            nextHandler.handle(request);
        }
    }

    public abstract void process(T request);
}
