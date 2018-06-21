package com.yan.examples.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestMain {

	public static void main(String[] args) throws IOException {
		readXlsx();
	}

	/**
	 * 读取xlsx文件
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void readXlsx() throws IOException {
		String filePath = "D:\\segment\\结果.xlsx";
		XSSFWorkbook xwb = new XSSFWorkbook("D:\\segment\\招标600篇.xlsx");
		XSSFSheet sheet = xwb.getSheetAt(0);

		// 创建工作薄
		XSSFWorkbook resultBook = new XSSFWorkbook();
		// 在工作薄中创建一工作表
		XSSFSheet newSheet = resultBook.createSheet("分词结果");
		// 在指定的索引处创建一行
		XSSFRow newRow = newSheet.createRow(0);
		// 在指定的索引处创建一列（单元格）
		XSSFCell title = newRow.createCell(0);
		title.setCellValue(new XSSFRichTextString("标题"));
		XSSFCell result = newRow.createCell(1);
		result.setCellValue(new XSSFRichTextString("结果"));
		XSSFCell content = newRow.createCell(2);
		content.setCellValue(new XSSFRichTextString("原文"));

		for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFRow newResultRow = newSheet.createRow(i + 1);
			for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
				String cell = row.getCell(j).toString();
				XSSFCell newResultCell = newResultRow.createCell(j);
				newResultCell.setCellValue(cell);
			}
		}
		// 新建一输出流并把相应的excel文件存盘
		FileOutputStream fos = new FileOutputStream(filePath);
		resultBook.write(fos);
		fos.flush();
		// 操作结束，关闭流
		fos.close();
		System.out.println("文件生成");
	}
}
