package pro.gladyshev.timerangessubtractor.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.gladyshev.timerangessubtractor.model.Range;

class RangeSubtractionTest {

    @Test
    @DisplayName("(9:00-10:00) “minus” (9:00-9:30) = (9:30-10:00)")
    void testIntersectionCase01() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T10:00:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:30:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T09:30:00Z", "2019-01-01T10:00:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("(9:00-10:00) “minus” (9:00-10:00) = ()")
    void testIntersectionCase02() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T10:00:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T10:00:00Z")
        );
        var expected = List.of();
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("(9:00-9:30) “minus” (9:30-15:00) = (9:00-9:30)")
    void testIntersectionCase03() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T09:30:00Z", "2019-01-01T15:00:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:30:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("(9:00-9:30, 10:00-10:30) “minus” (9:15-10:15) = (9:00-9:15, 10:15-10:30)")
    void testIntersectionCase04() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:30:00Z"),
                new Range("2019-01-01T10:00:00Z", "2019-01-01T10:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T09:15:00Z", "2019-01-01T10:15:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:15:00Z"),
                new Range("2019-01-01T10:15:00Z", "2019-01-01T10:30:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("(9:00-11:00, 13:00-15:00) “minus” (9:00-9:15, 10:00-10:15, 12:30-16:00) = (9:15-10:00, 10:15-11:00)")
    void testIntersectionCase05() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T11:00:00Z"),
                new Range("2019-01-01T13:00:00Z", "2019-01-01T15:00:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:15:00Z"),
                new Range("2019-01-01T10:00:00Z", "2019-01-01T10:15:00Z"),
                new Range("2019-01-01T12:30:00Z", "2019-01-01T16:00:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T09:15:00Z", "2019-01-01T10:00:00Z"),
                new Range("2019-01-01T10:15:00Z", "2019-01-01T11:00:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("B is just before A: (9:00-9:30) “minus” (8:30-09:00) = (9:00-9:30)")
    void testIntersectionCase06() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T08:30:00Z", "2019-01-01T09:00:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:30:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Empty range: (9:00-9:00) “minus” (8:30-8:30) = ()")
    void testIntersectionCase07() {
        var listA = List.of(
                new Range("2019-01-01T09:00:00Z", "2019-01-01T09:00:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T08:30:00Z", "2019-01-01T08:30:00Z")
        );
        var expected = List.of();
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sorting: (11:00-12:30, 10:00-10:30) “minus” (9:15-10:15) = (10:15-10:30, 11:00-12:30)")
    void testIntersectionCase08() {
        var listA = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T12:30:00Z"),
                new Range("2019-01-01T10:00:00Z", "2019-01-01T10:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T09:15:00Z", "2019-01-01T10:15:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T10:15:00Z", "2019-01-01T10:30:00Z"),
                new Range("2019-01-01T11:00:00Z", "2019-01-01T12:30:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Empty B: (11:00-12:30) “minus” (11:15-11:15) = (11:00-12:30)")
    void testIntersectionCase09() {
        var listA = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T12:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T11:15:00Z", "2019-01-01T11:15:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T12:30:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Different dates: (11:00 today - 12:00 today) “minus” (11:15 today - 11:15 tomorrow) = (11:00-11:15)")
    void testIntersectionCase10() {
        var listA = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T12:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T11:15:00Z", "2019-01-02T11:15:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T11:15:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Different timezones: (11:00 UTC - 12:00 UTC) “minus” (11:15 UTC - 11:15 UTC+7) = (11:00-11:15)")
    void testIntersectionCase11() {
        var listA = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T12:30:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T11:15:00Z", "2019-01-02T11:15:00+07:00")
        );
        var expected = List.of(
                new Range("2019-01-01T11:00:00Z", "2019-01-01T11:15:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Internal overlap in A: (10:00-11:20, 11:00-11:30, 10:20-10:40) “minus” (11:25-11:30) = (10:00-11:25)")
    void testIntersectionCase12() {
        var listA = List.of(
                new Range("2019-01-01T10:00:00Z", "2019-01-01T11:20:00Z"),
                new Range("2019-01-01T11:00:00Z", "2019-01-01T11:30:00Z"),
                new Range("2019-01-01T10:20:00Z", "2019-01-01T10:40:00Z")
        );
        var listB = List.of(
                new Range("2019-01-01T11:25:00Z", "2019-01-01T11:30:00Z")
        );
        var expected = List.of(
                new Range("2019-01-01T10:00:00Z", "2019-01-01T11:25:00Z")
        );
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Negative A: (12:30-12:00) “minus” () = ()")
    void testIntersectionCase13() {
        var listA = List.of(
                new Range("2019-01-01T12:30:00Z", "2019-01-01T12:00:00Z")
        );
        var listB = new ArrayList<Range>();
        var expected = List.of();
        var actual = RangeSubtraction.subtract(listA, listB);
        assertEquals(expected, actual);
    }

}

