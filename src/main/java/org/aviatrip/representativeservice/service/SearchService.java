package org.aviatrip.representativeservice.service;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.TicketSearchFilterRequest;
import org.aviatrip.representativeservice.dto.response.TicketView;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.repository.TicketSearchRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TicketSearchRepository searchRepository;

    public List<TicketView> getTicketsByFilter(TicketSearchFilterRequest filter, Pageable pageable, boolean orderByPriceAsc) {
        if(filter.getSource().equals(filter.getDestination()))
            throw new BadRequestException(ErrorResponse.of("Source city must not be equal to the destination city"));

        return searchRepository.findAllByFilter(filter, pageable, orderByPriceAsc);
    }
}
