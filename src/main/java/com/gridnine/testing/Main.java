package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SegmentFilter segmentFilter = new SegmentFilter();
        List<Flight> flights = FlightBuilder.createFlights();
        flights.forEach(System.out::println);
        System.out.println("\n-----------------------------------------------------\n");
        segmentFilter.processingUntilCurrentTime(flights).forEach(System.out::println);
        System.out.println("\n-----------------------------------------------------\n");
        segmentFilter.departureTimeBeforeArrivalTime(flights).forEach(System.out::println);
        System.out.println("\n-----------------------------------------------------\n");
        segmentFilter.expectationTimeMoreTwoHours(flights).forEach(System.out::println);
    }
}