package pl.bs.RoomBooker.controller;

import org.springframework.web.bind.annotation.*;
import pl.bs.RoomBooker.entity.Reservation;
import pl.bs.RoomBooker.entity.request.AddReservationRequest;
import pl.bs.RoomBooker.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationEndpoint {

    private final ReservationService reservationService;

    public ReservationEndpoint(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    @GetMapping("/future")
    public List<Reservation> getFutureReservations() {
        return this.reservationService.getFutureReservations();
    }

    @GetMapping("room/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable("roomId") String roomId){
        return this.reservationService.getReservationsByRoom(roomId);
    }

    @PostMapping
    public void create(@RequestBody AddReservationRequest addReservationRequest) throws Exception {
        this.reservationService.addReservation(addReservationRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.reservationService.deleteReservation(id);
    }

}
