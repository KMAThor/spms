package nc.ukma.thor.spms.dto.DataTable;

public class DataTableSearchDTO {

	private String value;
	private boolean regex;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isRegex() {
		return regex;
	}
	public void setRegex(boolean regex) {
		this.regex = regex;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (regex ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataTableSearchDTO other = (DataTableSearchDTO) obj;
		if (regex != other.regex)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DataTableSearchDTO [value=" + value + ", regex=" + regex + "]";
	}
	
}
