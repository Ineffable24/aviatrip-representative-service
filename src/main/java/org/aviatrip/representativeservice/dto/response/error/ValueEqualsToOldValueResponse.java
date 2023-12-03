package org.aviatrip.representativeservice.dto.response.error;

public class ValueEqualsToOldValueResponse extends ErrorResponse {
    private static final String DEFAULT_MESSAGE_PATTERN = "? must not equal to old ?";
    private static final String DEFAULT_MESSAGE = DEFAULT_MESSAGE_PATTERN.replaceAll("\\?", "value");

    public ValueEqualsToOldValueResponse() {
        super(DEFAULT_MESSAGE, null);
    }

    public ValueEqualsToOldValueResponse(String valueName) {
        super(DEFAULT_MESSAGE_PATTERN.replaceAll("\\?", valueName), null);
    }
}
