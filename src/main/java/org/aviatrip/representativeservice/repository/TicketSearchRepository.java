package org.aviatrip.representativeservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.aviatrip.representativeservice.dto.request.TicketSearchFilterRequest;
import org.aviatrip.representativeservice.dto.response.TicketView;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.entity.AviaCompany;
import org.aviatrip.representativeservice.entity.Flight;
import org.aviatrip.representativeservice.entity.FlightSeat;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketSearchRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TicketView> findAllByFilter(TicketSearchFilterRequest filter, Pageable pageable, boolean orderByPriceAsc) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TicketView> query = cb.createQuery(TicketView.class);

        Root<FlightSeat> seat = query.from(FlightSeat.class);
        Join<Flight, FlightSeat> flight = seat.join("flight", JoinType.INNER);
        Join<AviaCompany, Flight> company = flight.join("company", JoinType.INNER);
        Join<Airplane, Flight> airplane = flight.join("airplane", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(flight.get("source"), filter.getSource()));
        predicates.add(cb.equal(flight.get("destination"), filter.getDestination()));
        predicates.add(groupFlightSeatByFlightIdPredicate(cb, query, seat, filter.getSeatClass()));
        predicates.add(getDepartureDatePredicate(cb, flight, filter.getDepartureDate()));

        if(!filter.getCompanies().isEmpty())
            predicates.add(company.get("name").in(filter.getCompanies()));

        query.select(cb.construct(TicketView.class, getSelectionsForTicketClass(seat, flight, company, airplane)))
                .where(predicates.toArray(new Predicate[]{}));

        if(orderByPriceAsc)
            query.orderBy(cb.asc(seat.get("price")));

        return em.createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults((pageable.getPageSize()))
                .getResultList();
    }

    private Predicate getDepartureDatePredicate(CriteriaBuilder cb, Join<Flight, FlightSeat> flight, ZonedDateTime date) {
        ZonedDateTime currentTimestamp = ZonedDateTime.now(ZoneId.of("UTC"));

        ZonedDateTime dateAfter = ZonedDateTime.of(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), 0, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime dateBefore = dateAfter.plusDays(1);

        if(currentTimestamp.getYear() == date.getYear() && currentTimestamp.getDayOfYear() == date.getDayOfYear())
            dateAfter = currentTimestamp.plusHours(1);

        return cb.between(flight.get("departureTimestamp"), dateAfter, dateBefore);
    }

    private Predicate groupFlightSeatByFlightIdPredicate(CriteriaBuilder cb, CriteriaQuery<TicketView> query,
                                                                            Root<FlightSeat> seat, FlightSeatClass seatClass) {
        Subquery<Number> subQuery = query.subquery(Number.class);
        Root<FlightSeat> subRoot = subQuery.from(FlightSeat.class);

        subQuery.select(cb.min(subRoot.get("id")))
                .where(cb.equal(subRoot.get("seatClass"), seatClass))
                .where(cb.isFalse(seat.get("isReserved")))
                .groupBy(subRoot.get("flight"));
        return seat.get("id").in(List.of(subQuery));
    }

    private Selection<?>[] getSelectionsForTicketClass(Root<FlightSeat> seat, Join<Flight,
            FlightSeat> flight, Join<AviaCompany, Flight> company, Join<Airplane, Flight> airplane) {
        return new Selection[]{
                seat.get("id"),
                seat.get("isWindowSeat"),
                seat.get("position"),
                seat.get("price"),
                flight.get("id"),
                flight.get("source"),
                flight.get("destination"),
                flight.get("departureTimestamp"),
                flight.get("arrivalTimestamp"),
                flight.get("reservationDuration"),
                company.get("name"),
                airplane.get("model")
        };
    }
}
