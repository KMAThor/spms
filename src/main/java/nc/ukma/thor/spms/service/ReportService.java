package nc.ukma.thor.spms.service;

import org.apache.poi.ss.usermodel.Workbook;

import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.entity.report.StudentReport;

public interface ReportService {

	public Workbook studentReportToWorkbook(StudentReport studentReport);
	public Workbook projectReportToWorkbook(ProjectReport projectReport);
	
}
