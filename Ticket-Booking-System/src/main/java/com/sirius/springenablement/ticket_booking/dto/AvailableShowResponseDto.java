package com.sirius.springenablement.ticket_booking.dto;
public class AvailableShowResponseDto {
    private String showName;
    private int availableTicketCount;

    private boolean success=true;
    private String message="Success";
    private java.util.List<com.sirius.springenablement.ticket_booking.dto.AvailableShowResponseDto> availableShowsWithTickets;
    private String name;
    private String TheatreName;
    private String locationName;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTheatreName() {
        return TheatreName;
    }

    public void setTheatreName(String theatreName) {
        TheatreName = theatreName;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    public AvailableShowResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AvailableShowResponseDto() {

    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getAvailableTicketCount() {
        return availableTicketCount;
    }

    public void setAvailableTicketCount(int availableTicketCount) {
        this.availableTicketCount = availableTicketCount;
    }
    private com.sirius.springenablement.ticket_booking.entity.Shows show;
    public void setShow(com.sirius.springenablement.ticket_booking.entity.Shows show) {
        this.show=show;
    }
//
//



}
