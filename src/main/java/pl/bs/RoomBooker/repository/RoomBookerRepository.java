package pl.bs.RoomBooker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bs.RoomBooker.entity.Reservation;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface RoomBookerRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRoom(String roomId);

    @Query("SELECT r FROM Reservation r WHERE r.end > ?1")
    List<Reservation> findFutureReservations(ZonedDateTime now);
}
