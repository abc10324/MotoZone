package com.motozone.config.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.lowagie.text.Cell;
import com.lowagie.text.Paragraph;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.motozone.general.model.util.JavaBeanUtils;

public class GeneralExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// set response header
		String fileName = (String) model.get("fileName");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\".xls");
		
		// get undefined bean list
		List<Object> beanList = (List<Object>) model.get("beanList");
		
		// translate bean to map
		List<Map<String,Object>> mapList = JavaBeanUtils.bean2Map(beanList);
		
		Sheet sheet = workbook.createSheet(fileName);
		
		// prepare to set table header
		Map<String,Object> header = mapList.get(0);

		// get column number
		int colNum = 0;
		List<String> keyList = new ArrayList<>();
		for(String key : header.keySet()) {
			colNum++;
			keyList.add(key);
		}
		
		// set header row
		Row headerRow = sheet.createRow(0);
		
		int iCount = 0;
		for(String key : header.keySet()) {
			headerRow.createCell(iCount).setCellValue(key);
			iCount++;
		}
		
		
		// set content
		int rowCount = 1;
		for(Map<String,Object> map : mapList) {
			Row row = sheet.createRow(rowCount);
			
			for(int i=0 ; i<colNum ; i++) {
				Object value = map.get(keyList.get(i));
				String content = value != null ? value.toString() : "";
				row.createCell(i).setCellValue(content);
			}
			
			rowCount++;
		}
		
	}

}
