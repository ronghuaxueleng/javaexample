package com.yan.examples.tests;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONException;
import com.yan.utils.html.StringUtils;

public class Table {
	public static void main(String[] args) throws Exception {
//		 readXlsx();
		test1();
	}
	
	public static void test1() throws JSONException, IOException {
		String url = "http://www.dlggzy.cn/jyxx/jsgcZbjggsDetail?guid=b8671e7c-9aa1-4415-9a05-b82ad96028bc&isOther=false";
		String json = new TableUtils(url).toJSONString();
		System.out.println(json);
	}
	
	@SuppressWarnings("resource")
	public static void readXlsx() throws IOException {
		XSSFWorkbook xwb = new XSSFWorkbook("D:\\segment\\表格类标讯HTML.xlsx");
		XSSFSheet sheet = xwb.getSheetAt(0);
		for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
			XSSFRow row = sheet.getRow(i);
			String cell = row.getCell(2).toString();
			System.out.println(cell);
			if (StringUtils.isUrl(cell)) {
				try {
					String json = new TableUtils(cell).toJSONString();
					System.out.println(json);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
