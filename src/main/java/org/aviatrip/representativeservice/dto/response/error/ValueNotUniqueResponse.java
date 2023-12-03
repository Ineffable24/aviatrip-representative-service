package org.aviatrip.representativeservice.dto.response.error;

public class ValueNotUniqueResponse extends ErrorResponse {

    private static final String MESSAGE_POSTFIX = " already taken";
    private static final String DEFAULT_MESSAGE = "value" + MESSAGE_POSTFIX;

    public ValueNotUniqueResponse() {
        super(DEFAULT_MESSAGE, null);
    }

    public ValueNotUniqueResponse(String valueName) {
        super(valueName + MESSAGE_POSTFIX, null);
    }
}
