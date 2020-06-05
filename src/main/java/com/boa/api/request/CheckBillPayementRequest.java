package com.boa.api.request;

import java.time.LocalDate;

/**
 * CheckBillPayementRequest
 */
public class CheckBillPayementRequest {

    private String billerCode, billRef, cashingRef, billNum, requierNumber, langue;
    private LocalDate startDate, endDate;
}