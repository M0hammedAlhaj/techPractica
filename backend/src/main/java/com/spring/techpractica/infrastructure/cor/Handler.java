package com.spring.techpractica.infrastructure.cor;

public interface Handler<T> {
     void setNext(Handler<T> nextHandler);
     void handle(T request);
}
