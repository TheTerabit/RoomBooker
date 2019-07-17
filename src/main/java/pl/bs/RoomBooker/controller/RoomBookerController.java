package pl.bs.RoomBooker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.bs.RoomBooker.entity.Reservation;
import pl.bs.RoomBooker.entity.request.AddReservationRequest;
import pl.bs.RoomBooker.service.RoomBookerService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomBookerController {

    private final RoomBookerService roomBookerService;

    @Autowired
    public RoomBookerController(RoomBookerService roomBookerService) {
        this.roomBookerService = roomBookerService;
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public List<Reservation> getReservations() throws IOException {
        return this.roomBookerService.getReservations();
    }

    @RequestMapping(value = "/reservations/add", method = RequestMethod.POST)
    public String addReservation(@RequestBody AddReservationRequest addReservationRequest) {
        return this.roomBookerService.addReservation(addReservationRequest);
    }

    @RequestMapping(value = "/reservations/delete/{id}", method = RequestMethod.DELETE)
    public String deleteReservation(@PathVariable("id") Long id) {
        return this.roomBookerService.deleteReservation(id);
    }
}