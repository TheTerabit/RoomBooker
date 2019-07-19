package pl.bs.RoomBooker.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue
    private long id;
    private String personName;
    private String eventName;
    private String room;
    private Timestamp start;
    private Timestamp end;

}
