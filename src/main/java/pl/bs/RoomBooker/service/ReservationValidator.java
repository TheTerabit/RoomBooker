package pl.bs.RoomBooker.service;

import org.springframework.stereotype.Service;
import pl.bs.RoomBooker.entity.Reservation;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationValidator {

    public ReservationValidator() { }

    public void checkIfStartBeforeEnd(Reservation reservation) throws Exception {
        if(reservation.getStart().isAfter(reservation.getEnd()))
            throw new Exception("End of the meeting must be after its start.");
    }

    public void checkReservationLength(Reservation reservation) throws Exception {
        if(!this.checkEventLength(reservation.getStart(), reservation.getEnd()))
            throw new Exception("Duration of the meeting must be 15 - 120 minutes.");
    }

    public void checkIfRoomIsFree(Reservation reservation, List<Reservation> allReservations) throws Exception {
        if(!checkIfFree(reservation.getStart(), reservation.getEnd(), allReservations))
            throw new Exception("The conference room is already occupied.");
    }

    private boolean checkIfFree(ZonedDateTime start, ZonedDateTime end, List<Reservation> allReservations){//stream
        for(Reservation reservation : allReservations){
            if(!((!start.isBefore(reservation.getEnd()))||(!end.isAfter(reservation.getStart()))))
                return false;
        }
        return true;
    }

    private boolean checkEventLength(ZonedDateTime start, ZonedDateTime end){
        long eventLength=end.toInstant().toEpochMilli()-start.toInstant().toEpochMilli();
        if((eventLength >= TimeUnit.MINUTES.toMillis(15))&&(eventLength <= TimeUnit.MINUTES.toMillis(120)))
            return true;
        else
            return false;
    }


}
