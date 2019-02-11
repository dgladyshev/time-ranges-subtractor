package pro.gladyshev.timerangessubtractor.util;

import static java.util.Comparator.comparing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import pro.gladyshev.timerangessubtractor.model.Range;

@UtilityClass
public class RangeSubtraction {

    public List<Range> subtract(List<Range> listA, List<Range> listB) {
        List<Range> results = new ArrayList<>();
        var stackA = toMergedAndSortedDeque(listA);
        var stackB = toMergedAndSortedDeque(listB);
        while (!stackA.isEmpty()) {
            if (stackB.isEmpty()) {
                results.addAll(stackA);
                break;
            }
            Range a = stackA.pop();
            Range b = stackB.pop();
            long startA = a.startMillis();
            long endA = a.endMillis();
            long startB = b.startMillis();
            long endB = b.endMillis();
            if (startA >= endB) {
                // A is after or next to B, discard B, put A back to check future intersections
                stackA.addFirst(a);
            } else if (endA <= startB) {
                // B is after A or next to A, save A, put B back to check future intersections
                results.add(a);
                stackB.addFirst(b);
            } else if (startB <= startA && endB >= endA) {
                // A is a subset of B, discard A, put B back to check future intersections
                stackB.addFirst(b);
            } else if (startB > startA && endB < endA) {
                // B is a subset of A, discard B, save "left" part of (B - A)
                // Put back "right" part to check future intersections
                results.add(new Range(a.getStart(), b.getStart()));
                stackA.addFirst(new Range(b.getEnd(), a.getEnd()));
            } else if (startA >= startB) {
                // Important: additional case checks omitted for speed, do not alter the order of "if" statement
                // A intersects B, A is ahead or B is a subset of A and start positions are the same
                // Discard B, put back (B - A) to check future intersections
                stackA.addFirst(new Range(b.getEnd(), a.getEnd()));
            } else {
                // Important: additional case checks omitted for speed, do not alter the order of "if" statement
                // A intersects B, A is behind or B is a subset of A and end positions are the same
                // Save (B-A), put back B to check future intersections
                results.add(new Range(a.getStart(), b.getStart()));
                stackB.addFirst(b);
            }
        }
        return results;
    }

    Deque<Range> toMergedAndSortedDeque(List<Range> unsortedList) {
        var sortedDeque = unsortedList
                .stream()
                .filter(Range::isNotEmptyOrNegative)
                .sorted(comparing(Range::getStart))
                .collect(Collectors.toCollection(LinkedList::new));
        var mergedDeque = new LinkedList<Range>();
        if (!sortedDeque.isEmpty()) {
            Range merged = sortedDeque.pop();
            while (!sortedDeque.isEmpty()) {
                Range current = sortedDeque.pop();
                if (current.getStart().isBefore(merged.getEnd())) {
                    merged = new Range(merged.getStart(), getMaxEnd(merged, current));
                } else {
                    mergedDeque.add(merged);
                    merged = current;
                }
            }
            mergedDeque.add(merged);
        }
        return mergedDeque;
    }

    Instant getMaxEnd(Range first, Range second) {
        return second.getEnd().isAfter(first.getEnd()) ? second.getEnd() : first.getEnd();
    }

}
