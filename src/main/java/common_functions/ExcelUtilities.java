package common_functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.Desktop;

public class ExcelUtilities {
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	public static void WriteEntietiestoExcel(String ScenarioName, String entitynumber) throws IOException {
		String outputfile = "src/test/resources/Entities.xlsx";
		File file = new File(outputfile);
		System.out.println("Excel Path: " + file.getAbsolutePath());
		if (file.exists() && file.length() > 0) {
		    FileInputStream fis = new FileInputStream(file);
		    workbook = new XSSFWorkbook(fis);
		    fis.close();
		} else {
		    workbook = new XSSFWorkbook();
		}
		
		 sheet = workbook.getSheet("Entities");
		if(sheet==null) {
			sheet = workbook.createSheet("Entities");
			sheet.createRow(0).createCell(0).setCellValue("Sl No");
			sheet.getRow(0).createCell(1).setCellValue("Scenario name");
			sheet.getRow(0).createCell(2).setCellValue("Entities");
		}
		int lastRowNum = sheet.getLastRowNum();
		int scenarioCol = -1;
		int entityCol = -1;
		XSSFRow headerRow = sheet.getRow(0);
		for(Cell cell : headerRow) {
			if(cell.getStringCellValue().equalsIgnoreCase("Scenario name")) {
				scenarioCol = cell.getColumnIndex();
			}
			if(cell.getStringCellValue().equalsIgnoreCase("Entities")) {
				entityCol = cell.getColumnIndex();
			}
		}
		if (scenarioCol == -1 || entityCol == -1) {
			throw new RuntimeException("Required columns not found!");
		}
		
		Findwritexl(ScenarioName, scenarioCol, entityCol, entitynumber);
		CellStyle rowstyle = createRowStyle();
		
		for (int i = 0; i < lastRowNum; i++) {
			Row r = sheet.getRow(i);
			if(r!=null)
				applyStyleToRow(r,rowstyle);
		}
		
		
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		workbook.close();
		

		// After closing workbook
		if (Desktop.isDesktopSupported()) {
		    Desktop.getDesktop().open(file);
		}
	}
	
	public static void Findwritexl(String ScenarioName, int scenarioCol,int entityCol,String entitynumber) throws IOException {
		boolean found = false;
		workbook.getSheet("Entities");
		int lastRowNum = sheet.getLastRowNum();
		for(int i=1;i<=lastRowNum;i++) {
		    Row row= sheet.getRow(i);
		    if (row == null) {
		        continue;
		    }
		    Cell scenariocell = row.getCell(scenarioCol);
		    if (scenariocell == null)
		        continue;
		    String scenarioValue = scenariocell.toString().trim();

		    if(scenarioValue.equalsIgnoreCase(ScenarioName)) {
		        Cell entitycell= row.getCell(entityCol);
		        if(entitycell==null) {
		            entitycell = row.createCell(entityCol);
		            entitycell.setCellValue(entitynumber);
		        }else {
		        	entitycell.setCellValue(entitynumber);
		        }
		        found = true;
		        System.out.println("Updated row " + i + " for scenario: " + ScenarioName + " with entity: " + entitynumber);
		        break;
		    }
		}
		if(!found) {
		    Row newRow = sheet.createRow(lastRowNum + 1);
		    newRow.createCell(0).setCellValue(lastRowNum + 1);
		    newRow.createCell(scenarioCol).setCellValue(ScenarioName);
		    newRow.createCell(entityCol).setCellValue(entitynumber);
		    System.out.println("Added new row " + (lastRowNum + 1) + " for scenario: " + ScenarioName);
		}
		 System.out.println("Excel file updated successfully.");
	}
	
	public static XSSFCellStyle SetCellstyle() {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont font = workbook.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBold(true);
		
		style.setFont(font);
		return style;
	}
	public static XSSFCellStyle createRowStyle() {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);
		return style;
	}
	
	public static void applyStyleToRow(Row row, CellStyle rowstyle) {
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if(cell!=null) {
				cell.setCellStyle(rowstyle);
			}
			
		}
	}
}