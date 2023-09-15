package com.sirius.springenablement.ticket_booking.dto;
public class AvailableShowResponseDto {
    private String showName;
    private int availableTicketCount;

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

    private com.sirius.springenablement.ticket_booking.entity.Tickets tickets;
    public void setTickets(java.util.List<com.sirius.springenablement.ticket_booking.entity.Tickets> showTickets) {
    }
}
