package com.unisinos.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.unisinos.domain.AppInfoDto;

public class ExcelUtil {

	public List<HSSFWorkbook> createXls(List<AppInfoDto> result) {
		
		List<List<AppInfoDto>> resultSplited = splitResult(result, 65000);
		List<HSSFWorkbook> resultWb = new ArrayList<>();
		
		for (List<AppInfoDto> appsInfo : resultSplited) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			
			createHeaderCells(sheet.createRow(0));
			
			AtomicInteger rowCounter = new AtomicInteger(1);
			appsInfo.forEach(app -> {
				HSSFRow row = sheet.createRow(rowCounter.getAndIncrement());
				createCells(row, app);
			});
			
			resultWb.add(wb);
		}
		return resultWb;
	}

	private List<List<AppInfoDto>> splitResult(List<AppInfoDto> result, int limit) {
		List<List<AppInfoDto>> splitedResult = new ArrayList<>();
		int currentIndex = 0; 
		while( currentIndex < result.size() ) {
			int endIndex = result.size() > (currentIndex + limit) ? (currentIndex + limit) : result.size(); 
			splitedResult.add(result.subList(currentIndex, endIndex));
			currentIndex += limit;
		}
		return splitedResult;
	}

	private void createHeaderCells(HSSFRow row) {
		row.createCell(0).setCellValue(new HSSFRichTextString("userName"));
		row.createCell(1).setCellValue(new HSSFRichTextString("processName"));
		row.createCell(2).setCellValue(new HSSFRichTextString("dayOfWeek"));
		row.createCell(3).setCellValue(new HSSFRichTextString("hour"));
		row.createCell(4).setCellValue(new HSSFRichTextString("minute"));
		row.createCell(5).setCellValue(new HSSFRichTextString("second"));
		row.createCell(6).setCellValue(new HSSFRichTextString("openedTime"));
		row.createCell(7).setCellValue(new HSSFRichTextString("idScreen"));
		row.createCell(8).setCellValue(new HSSFRichTextString("rx"));
		row.createCell(9).setCellValue(new HSSFRichTextString("tx"));
	}

	private void createCells(HSSFRow row, AppInfoDto app) {
		row.createCell(0).setCellValue(new HSSFRichTextString(app.getUserName()));
		row.createCell(1).setCellValue(new HSSFRichTextString(app.getProcessName()));
		row.createCell(2).setCellValue(app.getDayOfWeek());
		row.createCell(3).setCellValue(app.getHour());
		row.createCell(4).setCellValue(app.getMinutes());
		row.createCell(5).setCellValue(app.getSeconds());
		row.createCell(6).setCellValue(app.getOpenedTime());
		row.createCell(7).setCellValue(app.getIdScreen());
		row.createCell(8).setCellValue(app.getRx());
		row.createCell(9).setCellValue(app.getTx());
	}

}
