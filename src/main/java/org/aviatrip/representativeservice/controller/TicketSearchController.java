package org.aviatrip.representativeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.TicketSearchFilterRequest;
import org.aviatrip.representativeservice.dto.response.TicketView;
import org.aviatrip.representativeservice.service.SearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class TicketSearchController {

    private final SearchService searchService;

    @PostMapping
    public List<TicketView> searchTickets(@RequestBody @Valid TicketSearchFilterRequest request, @RequestParam(value = "page", defaultValue = "0") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);

        return searchService.getTicketsByFilter(request, pageable, true);
    }
}
