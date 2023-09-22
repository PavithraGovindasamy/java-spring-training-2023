package com.sirius.springenablement.ticket_booking.dto;


    @lombok.Data
    public class UpdateTicketCountRequestDto {
        private Long userId;
        private Long bookingId;
        private Integer newTicketCount;
        private String action;
    }

