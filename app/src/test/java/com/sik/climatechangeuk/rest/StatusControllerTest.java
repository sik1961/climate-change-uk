package com.sik.climatechangeuk.rest;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import com.sik.climatechangeuk.status.AdapterStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class StatusControllerTest {

    @Test
    public void shouldReturnStatus() {
        StatusController statusController = new StatusController(new AdapterStatus());
        ResponseEntity<AdapterStatus> result = statusController.getStatus();
        assertThat(result, is(notNullValue()));
    }
}