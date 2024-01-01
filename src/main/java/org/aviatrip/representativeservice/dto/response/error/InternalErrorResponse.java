package org.aviatrip.representativeservice.dto.response.error;

public class InternalErrorResponse extends ErrorResponse {

    private static final String DEFAULT_MESSAGE = "internal server error occurred";
    private static final String DEFAULT_DETAILS = "please try later";

    public static final InternalErrorResponse DEFAULT = new InternalErrorResponse();

    public InternalErrorResponse() {
        super(DEFAULT_MESSAGE, DEFAULT_DETAILS);
    }

    public InternalErrorResponse(String message) {
        super(message, null);
    }

    public InternalErrorResponse(String message, String details) {
        super(message, details);
    }
}
