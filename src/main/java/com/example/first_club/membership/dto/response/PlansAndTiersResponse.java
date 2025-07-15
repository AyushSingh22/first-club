package com.example.first_club.membership.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlansAndTiersResponse {

    private List<PlanResponse> plans;
    private List<TierResponse> tiers;
}
