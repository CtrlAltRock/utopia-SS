//package com.smoothstack.ua.services;
//
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class MetricService {
//
//    private Map<String, Map<Integer, Integer>> metricMap;
//
//    public void increaseCount(String request, int status) {
//        Map<Integer, Integer> statusMap = metricMap.get(request);
//        if (statusMap == null) {
//            statusMap = new ConcurrentHashMap<>();
//        }
//
//        Integer count = statusMap.get(status);
//        if (count == null) {
//            count = 1;
//        } else {
//            count++;
//        }
//        statusMap.put(status, count);
//        metricMap.put(request, statusMap);
//    }
//
//    public Map getFullMetric() {
//        return metricMap;
//    }
//}
