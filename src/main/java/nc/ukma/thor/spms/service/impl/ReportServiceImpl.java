package nc.ukma.thor.spms.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.report.HrFeedbackInfo;
import nc.ukma.thor.spms.entity.report.MeetingTraitFeedbackInfo;
import nc.ukma.thor.spms.entity.report.PersonInfo;
import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.entity.report.StudentReport;
import nc.ukma.thor.spms.entity.report.TraitCategoryInfo;
import nc.ukma.thor.spms.entity.report.TraitInfo;
import nc.ukma.thor.spms.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	
	@Override
	public Workbook studentReportToWorkbook(StudentReport studentReport) {
		Workbook wb = new HSSFWorkbook();
		CreationHelper helper = wb.getCreationHelper();
		// create sheet
		Sheet sheet = wb.createSheet();
		CellStyle borders = wb.createCellStyle();
		borders.setBorderBottom(CellStyle.BORDER_THIN);
		borders.setBorderTop(CellStyle.BORDER_THIN);
		borders.setBorderRight(CellStyle.BORDER_THIN);
		borders.setBorderLeft(CellStyle.BORDER_THIN);
		
		CellStyle categoryHeaderStyle = wb.createCellStyle();
		categoryHeaderStyle.setBorderBottom(CellStyle.BORDER_THICK);
		categoryHeaderStyle.setBorderTop(CellStyle.BORDER_THICK);
		categoryHeaderStyle.setBorderRight(CellStyle.BORDER_THICK);
		categoryHeaderStyle.setBorderLeft(CellStyle.BORDER_THICK);

		CellStyle traitHeaderStyle = wb.createCellStyle();
		traitHeaderStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
		traitHeaderStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		traitHeaderStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
		traitHeaderStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
		
		CellStyle traitHeaderScoreStyle = wb.createCellStyle();
		traitHeaderScoreStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
		traitHeaderScoreStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
		traitHeaderScoreStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
		traitHeaderScoreStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
		traitHeaderScoreStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		dateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		dateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		dateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		dateCellStyle.setDataFormat(helper.createDataFormat().getFormat(nc.ukma.thor.spms.util.DateUtil.DEFAULT_DATE_FORMAT));
		
		
		InputStream is = getClass().getResourceAsStream("/photos/" + studentReport.getLinkToPhoto());
		byte[] bytes;
		try {
			bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			Drawing drawing = sheet.createDrawingPatriarch();//
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(0);
			anchor.setRow1(0);
			Picture pict = drawing.createPicture(anchor, pictureIdx);//
			pict.resize(0.25);//
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return wb;
		}

		studentReportHeaderHelp((short) 0,"First Name", studentReport.getPersonInfo().getFirstName(), sheet, borders);
		studentReportHeaderHelp((short) 1,"Second Name", studentReport.getPersonInfo().getSecondName(), sheet, borders);
		studentReportHeaderHelp((short) 2,"Last Name", studentReport.getPersonInfo().getLastName(), sheet, borders);
		studentReportHeaderHelp((short) 3,"Project Name", studentReport.getProjectName(), sheet, borders);
		studentReportHeaderHelp((short) 4,"Team Name", studentReport.getTeamName(), sheet, borders);
		studentReportHeaderHelp((short) 5,"Status", studentReport.getStatus(), sheet, borders);
		studentReportHeaderHelp((short) 6,"Status comment", studentReport.getStatusComment(), sheet, borders);
		Row row = sheet.createRow(7);
		row = sheet.createRow(8);
		row = sheet.createRow(9);
		row = sheet.createRow(10);
		row = sheet.createRow(11);
		Cell cell = row.createCell(0);
		cell.setCellValue("Trait Scores during project");
		row = sheet.createRow(12);
		cell = row.createCell(0);
		
		for(TraitCategoryInfo traitCategoryInfo: studentReport.getTraitCategoriesInfo()){
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell = row.createCell(0);
			cell.setCellValue(traitCategoryInfo.getName());
			cell.setCellStyle(categoryHeaderStyle);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 9));
			for(TraitInfo traitInfo: traitCategoryInfo.getTraitInfo()){
				row = sheet.createRow(sheet.getLastRowNum()+1);
				cell = row.createCell(0);
				cell.setCellValue(traitInfo.getName());
				cell.setCellStyle(traitHeaderStyle);
				cell = row.createCell(9);
				cell.setCellValue(String.format("%.1f",  traitInfo.getAverageScore()));
				cell.setCellStyle(traitHeaderScoreStyle);
				sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 8));
				if(!traitInfo.getMeetingsTraitFeedbackInfo().isEmpty()){
					row = sheet.createRow(sheet.getLastRowNum()+1);
					cell = row.createCell(0);
					cell.setCellValue("Meeting topic");
					cell.setCellStyle(borders);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 1));
					cell = row.createCell(2);
					cell.setCellValue("Date");
					cell.setCellStyle(borders);
					cell.setCellStyle(dateCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 2, 3));
					cell = row.createCell(4);
					cell.setCellValue("Mentor Name");
					cell.setCellStyle(borders);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 4, 5));
					cell = row.createCell(6);
					cell.setCellValue("Comment");
					cell.setCellStyle(borders);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 6, 8));
					cell = row.createCell(9);
					cell.setCellValue("Score");
					cell.setCellStyle(borders);
				}
				for(MeetingTraitFeedbackInfo meetingTraitFeedbackInfo: traitInfo.getMeetingsTraitFeedbackInfo()){
					row = sheet.createRow(sheet.getLastRowNum()+1);
					cell = row.createCell(0);
					cell.setCellValue(meetingTraitFeedbackInfo.getMeetingTopic());
					cell.setCellStyle(borders);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 1));
					cell = row.createCell(2);
					cell.setCellValue(meetingTraitFeedbackInfo.getStartDate());
					cell.setCellStyle(borders);
					cell.setCellStyle(dateCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 2, 3));
					cell = row.createCell(4);
					cell.setCellValue(meetingTraitFeedbackInfo.getMentor().getFirstName());
					cell.setCellStyle(borders);
					cell = row.createCell(5);
					cell.setCellValue(meetingTraitFeedbackInfo.getMentor().getLastName());
					cell.setCellStyle(borders);
					cell = row.createCell(6);
					cell.setCellValue(meetingTraitFeedbackInfo.getComment());
					cell.setCellStyle(borders);
					sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 6, 8));
					cell = row.createCell(9);
					cell.setCellValue(meetingTraitFeedbackInfo.getScore());
					cell.setCellStyle(borders);
				}
			}
		}
		
		row = sheet.createRow(sheet.getLastRowNum()+2);
		cell = row.createCell(0);
		cell.setCellValue("Hr feedbacks");
		row = sheet.createRow(sheet.getLastRowNum()+1);
		cell = row.createCell(0);
		for(HrFeedbackInfo hrFeedbackInfo: studentReport.getHrFeedbacksInfo()){
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell = row.createCell(0);
			cell.setCellValue(hrFeedbackInfo.getTopic());
			cell.setCellStyle(categoryHeaderStyle);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 9));
			
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell = row.createCell(0);
			cell.setCellValue(hrFeedbackInfo.getSummary());
			cell.setCellStyle(borders);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 9));
			row.setHeight((short) 0);
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell = row.createCell(0);
			cell.setCellValue("Author");
			cell.setCellStyle(borders);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 4));
			cell = row.createCell(5);
			cell.setCellValue("Added By");
			cell.setCellStyle(borders);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 5, 9));
			
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell = row.createCell(0);
			cell.setCellValue(hrFeedbackInfo.getAuthor().toString());
			cell.setCellStyle(borders);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 0, 4));
			cell = row.createCell(5);
			cell.setCellValue(hrFeedbackInfo.getAdded_by().toString());
			cell.setCellStyle(borders);
			sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(),sheet.getLastRowNum(), 5, 9));
			row = sheet.createRow(sheet.getLastRowNum()+1);
			cell = row.createCell(0);
		}
		return wb;
	}

	private void studentReportHeaderHelp(short index, String name, String value, Sheet sheet, CellStyle style) {
		Row row = sheet.createRow(index);
		Cell cell = row.createCell(3);
		cell.setCellValue(name);
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue(value);
		cell.setCellStyle(style);
		sheet.addMergedRegion(new CellRangeAddress(index, index, 3, 5));
		sheet.addMergedRegion(new CellRangeAddress(index, index, 6, 9));
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
		currentCell = row.createCell(0);
		currentCell.setCellValue("Name");
		row.createCell(1).setCellValue(projectReport.getProjectInfo().getName());

		row = sheet.createRow(2);
		row.setHeight((short) 0);
		row.createCell(0).setCellValue("Description");

		currentCell = row.createCell(1);
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
		row.createCell(0).setCellValue("Completed");
		if (projectReport.getProjectInfo().isCompleted()) {
			row.createCell(1).setCellValue("yes");
		} else {
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
		for (int i = 0; i < studentsWhoLeft.size(); i++) {
			row = sheet.createRow(15 + i);
			row.createCell(0).setCellValue(studentsWhoLeft.get(0).toString());
			row.createCell(1).setCellValue(reasons.get(0));
		}
		/*
		 * sheet.addMergedRegion(new CellRangeAddress(1,2,0,0));
		 * sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
		 * sheet.addMergedRegion(new CellRangeAddress(1,2,2,2));
		 * sheet.addMergedRegion(new CellRangeAddress(1,2,3,3));
		 * sheet.addMergedRegion(new CellRangeAddress(1,2,4,4));
		 * sheet.addMergedRegion(new CellRangeAddress(1,1,5,9));
		 */

		sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
				0, // last row (0-based)
				0, // first column (0-based)
				1 // last column (0-based)
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
