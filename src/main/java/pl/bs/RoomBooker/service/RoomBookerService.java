package pl.bs.RoomBooker.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bs.RoomBooker.entity.Reservation;
import pl.bs.RoomBooker.entity.request.AddReservationRequest;
import pl.bs.RoomBooker.repository.RoomBookerRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RoomBookerService {

    private final RoomBookerRepository roomBookerRepository;

    @Autowired
    public RoomBookerService(RoomBookerRepository roomBookerRepository){ this.roomBookerRepository=roomBookerRepository; }

    public List<Reservation> getAllReservations() {
        return this.roomBookerRepository.findAll();
    }

    public List<Reservation> getFutureReservations() {
        List<Reservation> futureReservations = this.roomBookerRepository.findAll();
        for(Reservation reservation : this.roomBookerRepository.findAll())
            if(reservation.getEnd().before(new Timestamp(System.currentTimeMillis())))
                futureReservations.remove(reservation);

        return futureReservations;
    }

    public List<Reservation> getReservationsByRoom(String room) {
        return this.roomBookerRepository.findByRoom(room);
    }

    public String addReservation(AddReservationRequest addReservationRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Reservation reservation = modelMapper.map(addReservationRequest, Reservation.class);
        if(reservation.getStart().after(reservation.getEnd()))
            return "End of the meeting must be after its start.";
        if(!this.checkEventLength(reservation.getStart(), reservation.getEnd()))
            return "Duration of the meeting must be 15 - 120 minutes.";
        if(checkIfFree(reservation.getStart(), reservation.getEnd(), reservation.getRoom())){
            roomBookerRepository.save(reservation);
            return "The conference room has been booked.";
        }
        else
            return "The conference room is already occupied.";


    }

    public String deleteReservation(Long id) {
        roomBookerRepository.deleteById(id);
        return "The reservation has been deleted.";
    }


    private boolean checkIfFree(Timestamp start, Timestamp end, String room){
        for(Reservation reservation : this.roomBookerRepository.findByRoom(room)){
            if(!((!start.before(reservation.getEnd()))||(!end.after(reservation.getStart()))))
                return false;
        }
        return true;
    }

    private boolean checkEventLength(Timestamp start, Timestamp end){
        long eventLength=end.getTime()-start.getTime();
        if((eventLength >= TimeUnit.MINUTES.toMillis(15))&&(eventLength <= TimeUnit.MINUTES.toMillis(120)))
            return true;
        else
            return false;
    }

}
