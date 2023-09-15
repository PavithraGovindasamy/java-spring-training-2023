package com.sirius.springenablement.ticket_booking.entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import java.util.List;
@Entity(name="tickets")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Tickets {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "availableCount")
    private int availableCount;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Shows show;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public com.sirius.springenablement.ticket_booking.entity.Shows getShow() {
        return show;
    }

    public void setShow(com.sirius.springenablement.ticket_booking.entity.Shows show) {
        this.show = show;
    }
}
