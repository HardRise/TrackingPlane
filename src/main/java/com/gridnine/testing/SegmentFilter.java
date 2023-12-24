package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SegmentFilter {
    private List<Flight> flightsUntilTime = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();
    private final LocalDateTime currentTime = LocalDateTime.now();

    /**
     * Returns list of some flights till current moment
     */
    public List<Flight> processingUntilCurrentTime(List<Flight> flights) {
        flightsUntilTime = new ArrayList<>();
        for (Flight flight : flights) {
            segments.addAll(flight.getSegments());

            flightsUntilTime.add(new Flight(
                            segments.stream()
                                    .filter(segment -> segment.getDepartureDate().isAfter(currentTime))
                                    .toList()
                    )
            );

            segments.clear();
        }
        return flightsUntilTime;
    }

    /**
     * Returns list of some flights with incorrect input information
     */
    public List<Flight> departureTimeBeforeArrivalTime(List<Flight> flights) {
        flightsUntilTime = new ArrayList<>();
        for (Flight flight : flights) {
            segments.addAll(flight.getSegments());

            flightsUntilTime.add(new Flight(
                    segments.stream()
                            .filter(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()))
                            .toList()));

            segments.clear();
        }
        return flightsUntilTime;
    }

    /**
     * Returns list of some flights with expectation time more than 2 hours
     */
    public List<Flight> expectationTimeMoreTwoHours(List<Flight> flights) {
        flightsUntilTime = new ArrayList<>();
        for (Flight flight : flights) {
            segments.addAll(flight.getSegments());

            for (int i = 0; i < segments.size() - 1; i++) {
                if (Duration.between(segments.get(i).getArrivalDate().toLocalTime(), segments.get(i + 1).getDepartureDate().toLocalTime()).toHours() >= 2) {
                    segments = null;
                    break;
                }
            }
            if (segments != null) {
                flightsUntilTime.add(new Flight(segments));
                segments = new ArrayList<>();
            } else {
                segments = new ArrayList<>();
            }
        }
        return flightsUntilTime;
    }
}
