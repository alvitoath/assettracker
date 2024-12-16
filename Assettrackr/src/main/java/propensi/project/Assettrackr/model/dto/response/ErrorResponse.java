// ErrorResponse.java
package propensi.project.Assettrackr.model.dto.response;

public class ErrorResponse {
    private String field;
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // Getters and Setters
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
