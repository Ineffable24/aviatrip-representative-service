package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.dto.response.FlightSeatReservationView;
import org.aviatrip.representativeservice.dto.response.FlightSeatView;
import org.aviatrip.representativeservice.entity.FlightSeat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, UUID> {
    List<FlightSeatView> findViewsByFlightId(UUID flightId, Pageable pageable);

    @EntityGraph(attributePaths = "flight")
    Optional<FlightSeatReservationView> findFlightSeatReservationViewByIdAndIsReserved(UUID id, boolean isReserved);

    @Modifying
    @Query("update FlightSeat s set s.isReserved = true where s.id = :flightSeatId and s.isReserved = false")
    int reserveFlightseatIfNotReserved(UUID flightSeatId);

    <S> Optional<S> findFlightSeatForTicketViewById(UUID id, Class<S> type);
}
