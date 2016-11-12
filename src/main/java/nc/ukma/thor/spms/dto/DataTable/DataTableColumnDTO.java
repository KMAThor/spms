package nc.ukma.thor.spms.dto.DataTable;

public class DataTableColumnDTO {

	private String data;
	private String name;
	private boolean searchable;
	private boolean orderable;
	private DataTableSearchDTO search;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public boolean isOrderable() {
		return orderable;
	}
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}
	public DataTableSearchDTO getSearch() {
		return search;
	}
	public void setSearch(DataTableSearchDTO search) {
		this.search = search;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (orderable ? 1231 : 1237);
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + (searchable ? 1231 : 1237);
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
		DataTableColumnDTO other = (DataTableColumnDTO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderable != other.orderable)
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (searchable != other.searchable)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DataTableColumnDTO [data=" + data + ", searchable=" + searchable + ", orderable=" + orderable
				+ ", search=" + search + "]";
	}
}
