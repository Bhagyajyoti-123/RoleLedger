package com.roleledger.dto;

import java.util.Map;

public class DashboardStatsDTO {

    private Long totalApplications;
    private Map<String, Long> statusCounts;

    public DashboardStatsDTO(Long totalApplications, Map<String, Long> statusCounts) {
        this.totalApplications = totalApplications;
        this.statusCounts = statusCounts;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public Map<String, Long> getStatusCounts() {
        return statusCounts;
    }
}
