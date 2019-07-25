package pl.bs.RoomBooker.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue
    private long id;
    private String personName;
    private String eventName;
    private String room;
    private ZonedDateTime start;
    private ZonedDateTime end;

}
