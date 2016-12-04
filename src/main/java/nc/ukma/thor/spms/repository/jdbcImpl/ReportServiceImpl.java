package nc.ukma.thor.spms.repository.jdbcImpl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.entity.report.StudentReport;
import nc.ukma.thor.spms.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{

	@Override
	public Workbook studentReportToWorkbook(StudentReport studentReport) {
		Workbook wb = new HSSFWorkbook();
		// TODO Auto-generated method stub
		return wb;
	}

	@Override
	public Workbook projectReportToWorkbook(ProjectReport projectReport) {
		Workbook wb = new HSSFWorkbook();
		// TODO Auto-generated method stub
		return wb;
	}
}
