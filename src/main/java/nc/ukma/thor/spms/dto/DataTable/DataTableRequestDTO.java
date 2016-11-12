package nc.ukma.thor.spms.dto.DataTable;

import java.util.List;

public class DataTableRequestDTO {
	
	private int draw;
	private int start;
	private int length;
	private List<DataTableColumnDTO> columns;
	private List<DataTableOrderDTO> order;
	private DataTableSearchDTO search;
	
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public DataTableSearchDTO getSearch() {
		return search;
	}
	public void setSearch(DataTableSearchDTO search) {
		this.search = search;
	}
	public List<DataTableColumnDTO> getColumns() {
		return columns;
	}
	public void setColumns(List<DataTableColumnDTO> columns) {
		this.columns = columns;
	}
	public List<DataTableOrderDTO> getOrder() {
		return order;
	}
	public void setOrder(List<DataTableOrderDTO> order) {
		this.order = order;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + draw;
		result = prime * result + length;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + start;
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
		DataTableRequestDTO other = (DataTableRequestDTO) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (draw != other.draw)
			return false;
		if (length != other.length)
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (start != other.start)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DataTableRequestDTO [draw=" + draw + ", start=" + start + ", length=" + length + ", columns=" + columns
				+ ", order=" + order + ", search=" + search + "]";
	}

}
