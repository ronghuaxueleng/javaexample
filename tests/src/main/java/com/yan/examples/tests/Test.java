package com.yan.examples.tests;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.yan.utils.html.HtmlUtils;

/**
 * 导入招标ES表数据处理后导回ES表
 */
public class Test {
	private static final int defaultBatchSize = 100;

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	private void importDataOnlineToTest(String startTime, String endTime) throws IOException {
		Client client = ESClient.getTestClient();
		SearchRequestBuilder query = client.prepareSearch("item_zhaobiao6").setTypes("item_info");

		// bool.must(QueryBuilders.matchQuery("item_id","307339004"));
		// SearchResponse scrollResp = query.setQuery(bool)
		SearchResponse scrollResp = query.setQuery(QueryBuilders.matchAllQuery()).setScroll(new TimeValue(600000))
				.setSize(defaultBatchSize).execute().actionGet();
		long total = scrollResp.getHits().getTotalHits();
		System.out.println("need : total " + total + " rows ...");
		int fail = 0;
		int num = 0;
		int i = 0;
		String filePath = "D:\\segment\\table.xlsx";
		// 创建工作薄
		XSSFWorkbook resultBook = new XSSFWorkbook();
		// 在工作薄中创建一工作表
		XSSFSheet newSheet = resultBook.createSheet();
		while (total > 0) {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				try {
					Map<String, Object> item = hit.getSource();
					String content = String.valueOf(item.get("content"));
					String contentTrim = HtmlUtils.getTextFromTHML(content);
					// System.out.println(contentTest);
					String url = String.valueOf(item.get("url"));
					String title = String.valueOf(item.get("title"));
					TableUtils tableUtils = new TableUtils();
					tableUtils.setTitle(title);
					tableUtils.getDataByHtml(content);

					String txt = tableUtils.hasTable() ? tableUtils.toJSONString() : "";

					XSSFRow newResultRow = newSheet.createRow(i);
					newResultRow.createCell(0).setCellValue(title);
					newResultRow.createCell(1).setCellValue(contentTrim);
					newResultRow.createCell(2).setCellValue(txt);
					newResultRow.createCell(3).setCellValue(url);
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			num += 100;
			if (num == 600) {
				break;
			}
			if (scrollResp.getHits().getHits().length == 0) {
				break;
			}
		}
		// 新建一输出流并把相应的excel文件存盘
		FileOutputStream fos = new FileOutputStream(filePath);
		resultBook.write(fos);
		fos.flush();
		// 操作结束，关闭流
		fos.close();
		System.out.println("文件生成");
		System.out.println("fail count is " + fail);
	}

	public static void main(String[] args) throws Exception {
		Test name = new Test();
		// long startTime = 1527350400000L;
		// long endTime = 1527436800000L;
		// long startTime = 1524672000000L;
		// long endTime = 1524758400000L;
		long startTime = 1528646400000L;
		// long endTime = 1527696000000L;
		long killTime = 1525881600000L;
		long oneDay = 86400000L;
		long endTime = startTime + oneDay;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while (startTime >= killTime) {
			try {
				System.out.println("start time 开始处理" + sdf.format(new Date(startTime)) + "的数据");
				name.importDataOnlineToTest(startTime + "", endTime + "");
				System.out.println("end time " + sdf.format(new Date(startTime)) + "处理完成");
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error get message startTime = " + startTime + ",endTime = " + endTime);
			}
			startTime -= oneDay;
			endTime -= oneDay;
		}
	}
}
