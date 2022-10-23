package com.string.generator.model.request;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestTest {

    private final Request request;

    public RequestTest()
    {
        this.request = new Request();
    }

    @Test
    void getIsValid() {
        boolean valid = true;
        boolean notValid = false;

        this.request.setIsValid(valid);

        assertEquals(
                valid,
                this.request.getIsValid()
        );

        this.request.setIsValid(notValid);

        assertEquals(
                notValid,
                this.request.getIsValid()
        );

    }

    @Test
    void getErrorMessages() {
        List<String> errorMsgs = new ArrayList<>();
        errorMsgs.add("some error");
        errorMsgs.add("other error");

        this.request.setErrorMessages(errorMsgs);

        assertEquals(
                2,
                this.request.getErrorMessages().size()
                );
    }

    @Test
    void getId() {
        long id = 532532;
        this.request.setId(id);

        assertEquals(
                id,
                this.request.getId()
        );
    }

    @Test
    void getCreatedAt() {
        Date date = Date.from(Instant.now());
        this.request.setCreatedAt(date);

        assertEquals(
                date,
                this.request.getCreatedAt()
        );
    }
}