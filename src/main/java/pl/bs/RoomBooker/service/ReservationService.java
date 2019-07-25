package pl.bs.RoomBooker.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.bs.RoomBooker.entity.Reservation;
import pl.bs.RoomBooker.entity.request.AddReservationRequest;
import pl.bs.RoomBooker.repository.RoomBookerRepository;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final RoomBookerRepository roomBookerRepository;
    private final ReservationValidator reservationValidator;

    public ReservationService(RoomBookerRepository roomBookerRepository, ReservationValidator reservationValidator) {
        this.roomBookerRepository = roomBookerRepository;
        this.reservationValidator = reservationValidator;
    }

    public List<Reservation> getAllReservations() {
        System.out.println(ZonedDateTime.now(Clock.systemDefaultZone()));
        return this.roomBookerRepository.findAll();
    }

    public List<Reservation> getFutureReservations() {
        return this.roomBookerRepository.findFutureReservations(ZonedDateTime.now(Clock.systemDefaultZone()));
    }

    public List<Reservation> getReservationsByRoom(String roomId) {
        return this.roomBookerRepository.findByRoom(roomId);
    }

    public void addReservation(AddReservationRequest addReservationRequest) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Reservation reservation = modelMapper.map(addReservationRequest, Reservation.class);
        this.reservationValidator.checkIfStartBeforeEnd(reservation);
        this.reservationValidator.checkReservationLength(reservation);
        this.reservationValidator.checkIfRoomIsFree(reservation, this.roomBookerRepository.findByRoom(reservation.getRoom()));

        roomBookerRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        roomBookerRepository.deleteById(id);
    }

}
