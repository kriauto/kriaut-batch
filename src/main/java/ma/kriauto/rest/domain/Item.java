package ma.kriauto.rest.domain;

public class Item {
	private String code;
	private String label;
	
	public Item() {
	}

	public Item(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Item [code=" + code + ", label=" + label + "]";
	}
}

