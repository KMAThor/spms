package nc.ukma.thor.spms.dto.DataTable;

import java.util.List;

public class DataTableResponseDTO<DataType> {
	
	private int draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<DataType> data;
	
	public DataTableResponseDTO(int draw, long recordsTotal, long recordsFiltered, List<DataType> data) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<DataType> getData() {
		return data;
	}
	public void setData(List<DataType> data) {
		this.data = data;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + draw;
		result = prime * result + (int) (recordsFiltered ^ (recordsFiltered >>> 32));
		result = prime * result + (int) (recordsTotal ^ (recordsTotal >>> 32));
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
		DataTableResponseDTO<?> other = (DataTableResponseDTO<?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (draw != other.draw)
			return false;
		if (recordsFiltered != other.recordsFiltered)
			return false;
		if (recordsTotal != other.recordsTotal)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DataTableResponseDTO [draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered="
				+ recordsFiltered + ", data=" + data + "]";
	}
	
}
