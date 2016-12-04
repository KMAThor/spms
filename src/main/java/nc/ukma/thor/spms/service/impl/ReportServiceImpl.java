package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.report.PersonInfo;
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
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet();
			
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Project Report");
		
		CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        cellStyle.setWrapText(true);
	    
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		dateCellStyle.setDataFormat(
	        createHelper.createDataFormat().getFormat(nc.ukma.thor.spms.util.DateUtil.DEFAULT_DATE_FORMAT));
		
		
		Cell currentCell;
		row = sheet.createRow(1);
		currentCell= row.createCell(0);
		currentCell.setCellValue("Name");
		row.createCell(1).setCellValue(projectReport.getProjectInfo().getName());
		
		row = sheet.createRow(2);
		row.setHeight((short) 0);
		row.createCell(0).setCellValue("Description");

		currentCell= row.createCell(1);
		currentCell.setCellValue(projectReport.getProjectInfo().getDescription());
		currentCell.setCellStyle(cellStyle);
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("Start Date");
		currentCell = row.createCell(1);
		currentCell.setCellValue(projectReport.getProjectInfo().getStartDate());
		currentCell.setCellStyle(dateCellStyle);
		
		row = sheet.createRow(4);
		row.createCell(0).setCellValue("End Date");
		currentCell = row.createCell(1);
		currentCell.setCellValue(projectReport.getProjectInfo().getEndDate());
		currentCell.setCellStyle(dateCellStyle);
		row = sheet.createRow(5);
		row.createCell(0).setCellValue("Finished");
		if(projectReport.getProjectInfo().isCompleted()){
			row.createCell(1).setCellValue("yes");
		}else{
			row.createCell(1).setCellValue("no");
		}
		row = sheet.createRow(6);
		row = sheet.createRow(7);
		currentCell = row.createCell(0);
		currentCell.setCellValue("Number of students who started participation");
		row.createCell(1).setCellValue(projectReport.getNumberOfParticipants());
		row = sheet.createRow(8);
		row.createCell(0).setCellValue("Number of students who completed project");
		row.createCell(1).setCellValue(projectReport.getNumberOfParticipantsWhoCompletedSuccessfully());
		row = sheet.createRow(9);
		row.createCell(0).setCellValue("Number of students who left");
		row.createCell(1).setCellValue(projectReport.getNumberOfParticipantsWhoLeft());
		row = sheet.createRow(10);
		row.createCell(0).setCellValue("Number of students who was invited for interview");
		row.createCell(1).setCellValue(projectReport.getNumberOfParticipantsWhomInterviewWasScheduled());
		row = sheet.createRow(11);
		row.createCell(0).setCellValue("Number of students who got job offer");
		row.createCell(1).setCellValue(projectReport.getNumberOfParticipantsWhoGotJobOffer());
		
		row = sheet.createRow(12);
		row = sheet.createRow(13);
		row.createCell(0).setCellValue("Students who left project");
		row = sheet.createRow(14);
		row.createCell(0).setCellValue("Full name");
		row.createCell(1).setCellValue("Reason why left");
		
		List<PersonInfo> studentsWhoLeft = projectReport.getParticipantsWhoLeft();
		List<String> reasons = projectReport.getReasonsWhy(); 
		for(int i=0; i< studentsWhoLeft.size(); i++){
			row = sheet.createRow(15+i);
			row.createCell(0).setCellValue(studentsWhoLeft.get(0).toString());
			row.createCell(1).setCellValue(reasons.get(0));
		}
		/*
		sheet.addMergedRegion(new CellRangeAddress(1,2,0,0));
		sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
		sheet.addMergedRegion(new CellRangeAddress(1,2,2,2));
		sheet.addMergedRegion(new CellRangeAddress(1,2,3,3));
		sheet.addMergedRegion(new CellRangeAddress(1,2,4,4));
		sheet.addMergedRegion(new CellRangeAddress(1,1,5,9));*/
		
		sheet.addMergedRegion(new CellRangeAddress(
	            0, //first row (0-based)
	            0, //last row  (0-based)
	            0, //first column (0-based)
	            1  //last column  (0-based)
	    ));
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return wb;
	}
	
	  private static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
	        Cell cell = row.createCell(column);
	        CellStyle cellStyle = wb.createCellStyle();
	        cellStyle.setAlignment(halign);
	        cellStyle.setVerticalAlignment(valign);
	        cell.setCellStyle(cellStyle);
	    }
}
