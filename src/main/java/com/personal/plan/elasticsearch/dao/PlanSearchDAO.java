package com.personal.plan.elasticsearch.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.plan.elasticsearch.model.Plan;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of @ElasticsearchRepository to find records based on different criteria
 * This can be used for simple queries, but in case of anything more complicated a query object should be built
 */
@Repository
public class PlanSearchDAO {
    private final ElasticsearchOperations operations;

    public PlanSearchDAO(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    /**
     * Search for a plan by a given criteria and pageable option
     * @param searchCriteria
     * @param page
     * @return
     */
    public Map<String, Object> searchForPlan(Map<String, String> searchCriteria, Pageable page){

        NativeSearchQuery query = buildSearchQuery(searchCriteria,page);

        SearchHits<Plan> result = operations.search(query, Plan.class);
        SearchPage<Plan> responsePage = SearchHitSupport.searchPageFor(result, query.getPageable());

        Map<String, Object> response = buildPageResponseMap(responsePage);

        return response;
    }

    /**
     * Helper method to build the build search query based on a search criteria
     * @param searchCriteria
     * @param page
     * @return
     */
    private NativeSearchQuery buildSearchQuery(Map<String, String> searchCriteria, Pageable page) {
        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        for (Map.Entry<String, String> entry : searchCriteria.entrySet()){
            if(entry.getKey() !=null && entry.getValue() !=null)
                boolQuery.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()).operator(Operator.AND));
        }

        return new NativeSearchQueryBuilder().withQuery(boolQuery).withPageable(page).build();

    }

    /**
     * Helper method to build up the map response to make it easier to know how many pages are available,
     * current page, and the total # of items per page
     * @param planList
     * @return
     */
    private Map<String, Object> buildPageResponseMap(SearchPage<Plan> planList) {
        List<SearchHit<Plan>> plans = planList.getContent();

        List<Plan> result = new ArrayList<>();
        for(SearchHit<Plan> p : plans){
            result.add(p.getContent());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("plans", result);
        response.put("currentPage", planList.getNumber());
        response.put("totalItems", planList.getTotalElements());
        response.put("totalPages", planList.getTotalPages());

        return response;
    }

}
