package pro.gladyshev.timerangessubtractor.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pro.gladyshev.timerangessubtractor.dto.InputRanges;
import pro.gladyshev.timerangessubtractor.model.Range;
import pro.gladyshev.timerangessubtractor.util.RangeSubtraction;

@RestController
public class RangeController {

    @PostMapping("/subtract")
    @ApiOperation(
            value = "Subtract time ranges B from A",
            response = Range.class,
            responseContainer = "List"
    )
    public List<Range> subtract(@ApiParam(required = true) @RequestBody InputRanges ranges) {
        return RangeSubtraction.subtract(ranges.getA(), ranges.getB());
    }

}