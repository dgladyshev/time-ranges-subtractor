package pro.gladyshev.timerangessubtractor.dto;

import java.util.List;
import lombok.Data;
import pro.gladyshev.timerangessubtractor.model.Range;

@Data
public class InputRanges {

    private List<Range> a;
    private List<Range> b;

}
