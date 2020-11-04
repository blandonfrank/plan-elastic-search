package com.personal.plan.elasticsearch;

import com.personal.plan.elasticsearch.dao.PlanSearchDAO;
import com.personal.plan.elasticsearch.model.Plan;
import com.personal.plan.elasticsearch.service.PlanSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PlanSearchServiceTest {

    @Mock
    PlanSearchDAO planSearchDAO;

    @Mock
    Pageable pageableMock;

    @InjectMocks
    PlanSearchService planSearchService;

    @Test
    void shouldReturnSearchHitMapForPlanName(){
        //given
        Plan plan = new Plan();
        plan.setACK_ID("1234");
        plan.setPLAN_NAME("TEST");

        List<Plan> expectedPlans = Arrays.asList(plan);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("plans", expectedPlans);

        String planName = "TEST";

        //search criteria
        Map<String, String> searchCriteria = new HashMap<>();
        searchCriteria.put("PLAN_NAME", planName);

//        //when
//        Map<String, Object> actualResponse = planSearchService.searchPlan(planName, "","",pageableMock);

        //assert(actualResponse.get("plans")).equals(actualResponse.get("plans"));

    }
}
