package com.motozone.config.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.motozone.general.model.util.JavaBeanUtils;

public class GeneralPdfView extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// set response header
		String fileName = (String) model.get("fileName");
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\".pdf");
		
		
		// for chinese font setting
		BaseFont bf = BaseFont.createFont( "MHei-Medium", "UniCNS-UCS2-H", BaseFont.EMBEDDED );
		Font font = new Font(bf,12,Font.NORMAL);
		
		// get undefined bean list
		List<Object> beanList = (List<Object>) model.get("beanList");
		
		// translate bean to map
		List<Map<String,Object>> mapList = JavaBeanUtils.bean2Map(beanList);
		
		// prepare to set table header
		Map<String,Object> header = mapList.get(0);

		// get column number
		int colNum = 0;
		List<String> keyList = new ArrayList<>();
		for(String key : header.keySet()) {
			colNum++;
			keyList.add(key);
		}
		
		// set table header
		Table table = new Table(colNum+1);
		table.setPadding(3.0f);
		table.setHorizontalAlignment(HorizontalAlignment.CENTER);
		table.addCell("");
		
		
		for(String key : header.keySet()) {
			Paragraph p = new Paragraph(key,font);
			Cell cell = new Cell();
			cell.add(p);
			cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			
			table.addCell(cell);
		}
		
		// set table content
		Integer id = 1; // row number
		for(Map<String,Object> map : mapList) {
			Cell idCell = new Cell(id.toString());
			idCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.addCell(idCell);
			id++;
			
			for(int i=0 ; i<colNum ; i++) {
				Object value = map.get(keyList.get(i));
				String content = value != null ? value.toString() : "";
				Paragraph p = new Paragraph(content,font);
				Cell cell = new Cell();
				cell.add(p);
				cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
				
				table.addCell(cell);
			}
			
		}
		
		// add table to the document
		document.add(table);
		
	}
	
}
