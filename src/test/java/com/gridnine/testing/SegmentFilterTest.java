package com.gridnine.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SegmentFilterTest {
    private final SegmentFilter segmentFilter = new SegmentFilter();

    @Test
    public void processingUntilCurrentTimeTest() {
        Segment segment = new Segment(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(6));
        List<Flight> flightWithDateBeforeCurrent = new ArrayList<>(List.of(
                new Flight(List.of(new Segment(LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(5)))),
                new Flight(List.of(new Segment(LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(6))))
        ));
        Assertions.assertThat(segmentFilter.processingUntilCurrentTime(flightWithDateBeforeCurrent).toString())
                .isEqualTo(new ArrayList<>(List.of(new Flight(List.of()), new Flight(List.of(segment)))).toString());
    }

    @Test
    public void departureTimeBeforeArrivalTime() {
        Segment segment = new Segment(LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(6));
        List<Flight> incorrectSchedule = new ArrayList<>(List.of(
                new Flight(List.of(new Segment(LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(6)))),
                new Flight(List.of(new Segment(LocalDateTime.now().plusDays(8), LocalDateTime.now().plusDays(6))))
        ));
        Assertions.assertThat(segmentFilter.departureTimeBeforeArrivalTime(incorrectSchedule).toString())
                .isEqualTo(new ArrayList<>(List.of(new Flight(List.of(segment)), new Flight(List.of()))).toString());
    }

    @Test
    public void expectationTimeMoreTwoHours() {
        List<Segment> segment = new ArrayList<>(List.of(
                new Segment(LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(6)),
                new Segment(LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4))));
        List<Flight> departureTimeMoreTwoHours = new ArrayList<>(List.of(
                new Flight(List.of(
                        new Segment(LocalDateTime.now().minusHours(12), LocalDateTime.now().minusHours(10)),
                        new Segment(LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(5)))),
                new Flight(List.of(
                        new Segment(LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(6)),
                        new Segment(LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4))))
        ));
        Assertions.assertThat(segmentFilter.expectationTimeMoreTwoHours(departureTimeMoreTwoHours).toString())
                .isEqualTo(List.of(new Flight(segment)).toString());
    }
}
