package pl.bs.RoomBooker.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bs.RoomBooker.entity.Reservation;
import pl.bs.RoomBooker.entity.request.AddReservationRequest;
import pl.bs.RoomBooker.repository.RoomBookerRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RoomBookerService {

    private final RoomBookerRepository roomBookerRepository;
   // private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RoomBookerService(RoomBookerRepository roomBookerRepository){ this.roomBookerRepository=roomBookerRepository; }

    public List<Reservation> getReservations() {

        List<Reservation> futureReservations = this.roomBookerRepository.findAll();
        for(Reservation reservation : this.roomBookerRepository.findAll())
            if(reservation.getEnd().before(new Timestamp(System.currentTimeMillis())))
                futureReservations.remove(reservation);

        return futureReservations;
    }

    public String addReservation(AddReservationRequest addReservationRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Reservation reservation = modelMapper.map(addReservationRequest, Reservation.class);
        if((checkIfFree(reservation.getStart(), reservation.getEnd()))&&(reservation.getStart().before(reservation.getEnd()))){
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

    private boolean checkIfFree(Timestamp start, Timestamp end){
        for(Reservation reservation : this.roomBookerRepository.findAll()){
            if(!((!start.before(reservation.getEnd()))||(!end.after(reservation.getStart()))))
                return false;
        }
        return true;
    }

}
