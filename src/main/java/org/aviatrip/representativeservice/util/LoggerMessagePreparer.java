package org.aviatrip.representativeservice.util;

import org.aviatrip.representativeservice.dto.response.error.ErrorsResponse;
import org.aviatrip.representativeservice.exception.DetailedException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.text.MessageFormat;

public class LoggerMessagePreparer {

    public static String prepareErrorMessage(Throwable throwable) {
        Assert.notNull(throwable, "Throwable not present");
        Throwable mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(throwable);

        return mostSpecificCause.getClass().getSimpleName() +
                " thrown: " +
                mostSpecificCause.getMessage();
    }

    public static String prepareErrorMessage(Throwable throwable, HttpStatus httpStatus) {
        Assert.notNull(throwable, "Throwable not present");
        Throwable mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(throwable);

        return MessageFormat.format("Returning HTTP {0} {1}: [{2}] thrown with message [{3}]", httpStatus.value(),
                httpStatus.getReasonPhrase(),
                mostSpecificCause.getClass().getSimpleName(),
                throwable.getMessage()
        );
    }

    public static String prepareErrorMessage(Throwable throwable, HttpStatus httpStatus, ErrorsResponse errorsResponse) {
        Assert.notNull(throwable, "Throwable not present");
        Throwable mostSpecificCause = NestedExceptionUtils.getMostSpecificCause(throwable);

        return MessageFormat.format("Returning HTTP {0} {1}: [{2}] thrown with message [{3}]", httpStatus.value(),
                httpStatus.getReasonPhrase(),
                mostSpecificCause.getClass().getSimpleName(),
                errorsResponse
        );
    }

    public static String prepareDetailedErrorMessage(DetailedException exception) {
        Assert.notNull(exception, "Detailed exception not present");

        return exception.getClass().getSimpleName() +
                " thrown: " +
                exception.getErrorResponse().orElse(null);
    }

    public static String prepareDetailedErrorMessage(DetailedException exception, HttpStatus httpStatus) {
        Assert.notNull(exception, "Detailed exception not present");

        return MessageFormat.format("Returning HTTP {0} {1}: [{2}] thrown with message [{3}]", httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getClass().getSimpleName(),
                exception.getErrorResponse().orElse(null)
        );
    }
}
