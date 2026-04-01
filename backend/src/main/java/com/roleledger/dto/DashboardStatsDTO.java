package com.roleledger.dto;

import java.util.Map;

public class DashboardStatsDTO {

    private Long totalApplications;
    private Map<String, Long> statusBreakdown;

    public DashboardStatsDTO(Long totalApplications, Map<String, Long> statusBreakdown) {
        this.totalApplications = totalApplications;
        this.statusBreakdown = statusBreakdown;
    }

    public Long getTotalApplications() { return totalApplications; }
    public Map<String, Long> getStatusBreakdown() { return statusBreakdown; }
}
