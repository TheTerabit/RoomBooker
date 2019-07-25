package pl.bs.RoomBooker.entity.request;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AddReservationRequest {

    private String personName;
    private String eventName;
    private String room;
    private ZonedDateTime start;
    private ZonedDateTime end;
}
