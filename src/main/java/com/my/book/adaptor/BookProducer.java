package com.my.book.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.book.domain.event.BookChanged;
import java.util.concurrent.ExecutionException;

public interface BookProducer {
    void sendBookCreateEvent(BookChanged bookChanged) throws ExecutionException, InterruptedException, JsonProcessingException;
    void sendBookDeleteEvent(BookChanged bookDeleteEvent) throws ExecutionException, InterruptedException, JsonProcessingException;
}
