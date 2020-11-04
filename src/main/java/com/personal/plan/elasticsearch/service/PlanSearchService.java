package com.personal.plan.elasticsearch.service;

import com.personal.plan.elasticsearch.dao.PlanSearchDAO;
import com.personal.plan.elasticsearch.exception.PlanSearchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class used to find plans based on plan name, sponsor name or sponsor state
 */
@Service
@Slf4j
public class PlanSearchService {

    private final PlanSearchDAO planSearchDAO;
    private static final String PLAN_NAME_KEY = "PLAN_NAME";
    private static final String SPONSOR_NAME_KEY = "SPONS_SIGNED_NAME";
    private static final String SPONSOR_STATE_KEY = "SPONS_DFE_MAIL_US_STATE";

    public PlanSearchService(PlanSearchDAO planSearchDAO) {
        this.planSearchDAO = planSearchDAO;
    }

    /**
     * Finds plans by a given plan name, and/or sponsor name, and/or sponsor state
     * In case something goes wrong and a null response comes back from the Elastic search repository, an empty page will be
     * @param planName
     * @param sponsorName
     * @param sponsorState
     * @param page
     * @return
     * @throws PlanSearchException
     */
    public Map<String, Object> searchPlan(String planName, String sponsorName, String sponsorState, Pageable page) throws PlanSearchException{
        log.info("Finding plan by Plan name: {} | Sponsor Name: {} | Sponsor State: {}", planName, sponsorName, sponsorState);
        log.info("# of pages {}, of size {} requested", page.getPageNumber(), page.getPageSize());

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put(PLAN_NAME_KEY, planName);
        requestMap.put(SPONSOR_NAME_KEY, sponsorName);
        requestMap.put(SPONSOR_STATE_KEY, sponsorState);

        Map<String, Object> response;
        try {
            response = planSearchDAO.searchForPlan(requestMap, page);
        } catch (Exception ex) {
            throw new PlanSearchException("Unable to complete request");
        }
        return response;
    }


}
