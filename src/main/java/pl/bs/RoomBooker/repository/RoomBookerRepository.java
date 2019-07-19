package pl.bs.RoomBooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pl.bs.RoomBooker.entity.Reservation;

import java.util.List;

@Repository
public interface RoomBookerRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoom(String room);
}
