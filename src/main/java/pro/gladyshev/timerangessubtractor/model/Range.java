package pro.gladyshev.timerangessubtractor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Range {

    @ApiModelProperty(position = 1)
    private Instant start;
    @ApiModelProperty(position = 2)
    private Instant end;

    public Range(String startIsoString, String endIsoString) {
        start = ZonedDateTime.parse(startIsoString).toInstant();
        end = ZonedDateTime.parse(endIsoString).toInstant();
    }

    public long startMillis() {
        return start.toEpochMilli();
    }

    public long endMillis() {
        return end.toEpochMilli();
    }

    @JsonIgnore
    public boolean isNotEmptyOrNegative() {
        return startMillis() < endMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Range) {
            Range other = (Range) o;
            return this.start.toEpochMilli() == other.start.toEpochMilli()
                    && this.end.toEpochMilli() == other.end.toEpochMilli();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

}