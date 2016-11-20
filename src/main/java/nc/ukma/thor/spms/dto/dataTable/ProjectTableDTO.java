package nc.ukma.thor.spms.dto.dataTable;

import java.util.ArrayList;
import java.util.List;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.util.DateUtil;

public class ProjectTableDTO {
	
	private Long id;
	private String name;
	private String startDate;
	private String endDate;
	private boolean isCompleted;
	
	public ProjectTableDTO() {}
	
	public ProjectTableDTO(Project project) {
		this.id = project.getId();
		this.name = project.getName();
		this.startDate = DateUtil.getStringRepresentation(project.getStartDate());
		this.endDate = DateUtil.getStringRepresentation(project.getEndDate());
		this.isCompleted = project.isCompleted();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isCompleted ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		ProjectTableDTO other = (ProjectTableDTO) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCompleted != other.isCompleted)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "ProjectTableDTO [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", isCompleted=" + isCompleted + "]";
	}

	public static List<ProjectTableDTO> convertFrom(List<Project> projects){
		List<ProjectTableDTO> projectsDTO = new ArrayList<ProjectTableDTO>();
		for(Project project: projects){
			projectsDTO.add(new ProjectTableDTO(project));
		}
		return projectsDTO;
	}
	
}
