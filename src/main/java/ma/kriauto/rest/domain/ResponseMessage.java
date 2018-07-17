package ma.kriauto.rest.domain;

/**
 * Used to transport messages back to the client.
 */
public class ResponseMessage {
    public enum Type {
        success, warn, danger, info;
    }

    private final Type type;
    private final String code;
    private final String label;

    public ResponseMessage(Type type, String code, String label) {
        this.type = type;
        this.code = code;
        this.label = label;
    }

	public Type getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
