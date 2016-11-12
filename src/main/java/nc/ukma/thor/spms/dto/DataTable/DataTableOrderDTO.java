package nc.ukma.thor.spms.dto.DataTable;

public class DataTableOrderDTO {
	
	private int column;
	private String dir;
	
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
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
		DataTableOrderDTO other = (DataTableOrderDTO) obj;
		if (column != other.column)
			return false;
		if (dir == null) {
			if (other.dir != null)
				return false;
		} else if (!dir.equals(other.dir))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DataTableOrderDTO [column=" + column + ", dir=" + dir + "]";
	}

}
