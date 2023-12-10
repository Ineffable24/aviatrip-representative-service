package org.aviatrip.representativeservice.dto.response.error;

public class ResourceNotFoundResponse extends ErrorResponse {
    private static final String MESSAGE_POSTFIX = " not found";
    private static final String DEFAULT_MESSAGE = "resource" + MESSAGE_POSTFIX;
    public static final ResourceNotFoundResponse DEFAULT = new ResourceNotFoundResponse();

    public ResourceNotFoundResponse() {
        super(DEFAULT_MESSAGE, null);
    }

    private ResourceNotFoundResponse(String resourceName) {
        super(resourceName + MESSAGE_POSTFIX, null);
    }

    public static ResourceNotFoundResponse of(String resourceName) {
        return new ResourceNotFoundResponse(resourceName);
    }
}
