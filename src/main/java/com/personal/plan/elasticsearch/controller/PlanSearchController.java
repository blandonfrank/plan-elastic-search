package com.personal.plan.elasticsearch.controller;

import com.personal.plan.elasticsearch.exception.InvalidInputException;
import com.personal.plan.elasticsearch.service.PlanSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller to search for a plan based on a given search criteria.
 */
@RestController
@RequestMapping("/api/v1/plans")
@CrossOrigin(origins = {"*"})
@Tag(name = "Personal Capital Plan Search API", description = "Searching plans on Elastic Search based on different criteria")
@SecurityRequirement(name = "api")
public class PlanSearchController {

    private final PlanSearchService planSearchService;

    PlanSearchController(PlanSearchService planSearchService) {
        this.planSearchService = planSearchService;
    }

    /**
     * End point to search for a plan based on a given criteria. All fields are optional, but at the very least one
     * field is expected to performed a search
     * @param planName
     * @param sponsorName
     * @param sponsorState
     * @param page
     * @param size
     * @return
     */
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPlan(@RequestParam(value = "planName", required = false) String planName,
                                                       @RequestParam(value = "sponsorName", required = false) String sponsorName,
                                                       @RequestParam(value = "sponsorState", required = false) String sponsorState,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "3") int size) {

        if (StringUtils.isBlank(planName) && StringUtils.isBlank(sponsorName) && StringUtils.isBlank(sponsorState)) {
            throw new InvalidInputException("Please enter at least one argument in the search criteria");
        }
        Pageable paging = PageRequest.of(page, size);

        Map<String, Object> response = planSearchService.searchPlan(planName, sponsorName, sponsorState, paging);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

