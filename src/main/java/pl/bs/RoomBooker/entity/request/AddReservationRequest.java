package pl.bs.RoomBooker.entity.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddReservationRequest {

    private String personName;
    private String eventName;
    private Timestamp start;
    private Timestamp end;
}
