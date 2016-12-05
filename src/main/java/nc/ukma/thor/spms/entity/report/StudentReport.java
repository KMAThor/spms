package nc.ukma.thor.spms.entity.report;

import java.util.ArrayList;
import java.util.List;

public class StudentReport {

	private PersonInfo personInfo;
	private String linkToPhoto;
	private String projectName;
	private String teamName;
	private String status;
	private String statusComment;
	
	private List<TraitCategoryInfo> traitCategoriesInfo = new ArrayList<>();
	private List<HrFeedbackInfo> hrFeedbacksInfo = new ArrayList<>();
	
	public StudentReport(){}
	
	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public String getLinkToPhoto() {
		return linkToPhoto;
	}

	public void setLinkToPhoto(String linkToPhoto) {
		this.linkToPhoto = linkToPhoto;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusComment() {
		return statusComment;
	}

	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
	}

	public List<TraitCategoryInfo> getTraitCategoriesInfo() {
		return traitCategoriesInfo;
	}

	public void setTraitCategoriesInfo(List<TraitCategoryInfo> traitCategoriesInfo) {
		this.traitCategoriesInfo = traitCategoriesInfo;
	}

	public List<HrFeedbackInfo> getHrFeedbacksInfo() {
		return hrFeedbacksInfo;
	}

	public void setHrFeedbacksInfo(List<HrFeedbackInfo> hrFeedbacksInfo) {
		this.hrFeedbacksInfo = hrFeedbacksInfo;
	}
	
}
