package com.oes.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XlsReader {
	
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	String filePath;
	String sheetName;
	

	public XlsReader(String filePath, String sheetName) {
		this.filePath = filePath;
		this.sheetName = sheetName;
		loadUpdatedData();
	}
	
	private void loadUpdatedData() {
		try {
			workbook = new XSSFWorkbook(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sheet = workbook.getSheet(sheetName);
	}
	
	
	public Map<Integer, Map<Integer, String>> getAllData() {
		loadUpdatedData();
		Map<Integer, Map<Integer, String>> data = new HashedMap<Integer, Map<Integer,String>>();
		int lastRow = sheet.getLastRowNum();
		int lastCol = sheet.getRow(0).getLastCellNum();
		
		for(int row = 0; row <= lastRow; row++) {
			Map<Integer, String> rowData = new HashedMap<Integer, String>();
			for(int col = 0; col < lastCol; col++) {
				Cell currentCell = sheet.getRow(row).getCell(col);
				String value = getCellValue(currentCell);
				rowData.put(col, value);
			}
			data.put(row, rowData);
		}
		return data;
	}
	
	private String getCellValue(Cell cell) {
		CellType type = cell.getCellType();
		
		switch (type) {
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case STRING:
			return cell.getStringCellValue();
		default:
			return "Unsupported Cell Type";
		}
	}
	
	public String getData(int row, int col) {
		Cell cell = sheet.getRow(row).getCell(col);
		return cell.getStringCellValue();
	}

}
