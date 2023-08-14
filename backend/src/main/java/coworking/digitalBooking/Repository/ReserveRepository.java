package coworking.digitalBooking.Repository;

import coworking.digitalBooking.Entities.Coworking;
import coworking.digitalBooking.Entities.Reserve;
import coworking.digitalBooking.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository <Reserve, Long>{
    @Query("SELECT r FROM Reserve r WHERE r.coworking = :coworking AND r.end_date > :startDate AND r.start_date < :endDate")
    List<Reserve> findByCoworkingAndDates(@Param("coworking") Coworking coworking, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Reserve r WHERE r.coworking = :coworking")
    List<Reserve> findAllByAttribute(Coworking coworking);

    @Query("SELECT r FROM Reserve r WHERE r.user = :user")
    List<Reserve> findAllByAttribute(User user);
}
