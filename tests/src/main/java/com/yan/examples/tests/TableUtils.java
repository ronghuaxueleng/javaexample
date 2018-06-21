package com.yan.examples.tests;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class TableUtils {
	private final static String DOCUMENT_TYPEID = "DOCUMENT";
	private final static String DOC_TITLE_TYPEID = "DOC.TITLE";
	private final static String DOC_PARAGRAPH_TYPEID = "DOC.PARAGRAPH";
	private final static String DOC_TABLE_TYPEID = "DOC.TABLE";
	
	private final static String SPECIAL_CHARACTER_REGEX = "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×■]";
	
	private String title = "";
	private boolean hasTable = false;

	private JSONArray tableArray = new JSONArray();

	public TableUtils() {
	}

	public TableUtils(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements htmls = doc.select("body");
		Element body = htmls.first();
		if (!body.getElementsByTag("table").isEmpty()) {
			hasTable = true;
			getTree(body);
		}

	}

	public boolean hasTable() {
		return hasTable;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void getDataByHtml(String html) throws IOException {
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
		if (!body.getElementsByTag("table").isEmpty()) {
			hasTable = true;
			getTree(body);
		}
	}

	@SuppressWarnings("serial")
	public JSONObject toJSON() throws JSONException {
		return new JSONObject() {
			{
				put("title", title);
				put("type", DOCUMENT_TYPEID);
				put("elements", tableArray);
			}
		};
	}

	public String toJSONString() throws JSONException {
		return toJSON().toString();
	}

	public void getTree(Element root) {
		Elements elList = root.children();
		if (elList != null && elList.size() != 0) {
			if (elList != null && elList.size() != 0) {
				for (Element el : elList) {
					if (!"table".equals(el.tagName().toLowerCase())) {
						getTree(el);
					} else {
						tableArray.add(getOneTableData(el));
					}
				}
			}
		}
	}

	@SuppressWarnings("serial")
	public JSONArray getRowData(Element table) throws JSONException {
		Elements trs = table.select("tr");
		JSONArray rows = new JSONArray();
		for (int i = 0; i < trs.size(); i++) {
			final JSONArray datas = getTdData(trs.get(i));
			rows.add(new JSONObject() {
				{
					put("datas", datas);
				}
			});
		}
		return rows;
	}

	@SuppressWarnings("serial")
	private JSONObject setTdText(final Object txt, final String type) {
		return new JSONObject() {
			{
				put("content", new JSONObject() {
					{
						put("text", txt);
						put("type", type);
					}
				});
			}
		};
	}

	@SuppressWarnings("serial")
	public JSONArray getTdData(Element tr) throws JSONException {
		Elements tds = tr.select("td");
		JSONArray datas = new JSONArray();
		for (int j = 0; j < tds.size(); j++) {
			Element td = tds.get(j);
			final int rowspan = td.hasAttr("rowspan") ? Integer.parseInt(td.attr("rowspan")) : 0;
			final int colspan = td.hasAttr("colspan") ? Integer.parseInt(td.attr("colspan")) : 0;
			Elements children = td.children();
			if (children.size() > 0) {
				Set<String> txtSet = new HashSet<String>();
				final JSONArray docElements = new JSONArray();
				for (Element el : children) {
					if ("table".equals(el.tagName().toLowerCase())) {
						final JSONObject tableData = getOneTableData(el);
						docElements.add(new JSONObject() {
							{
								put("content", new JSONObject() {
									{
										put("text", tableData);
										put("type", DOC_TABLE_TYPEID);
									}
								});
							}
						});
					} else {
						txtSet.add(td.text());
					}
				}
				if (docElements.size() > 0) {
					datas.add(new JSONObject() {
						{
							put("title", title);
							put("type", DOCUMENT_TYPEID);
							put("elements", docElements);
							put("colspan", colspan);
							put("rowspan", rowspan);
						}
					});
				}

				String txt = StringUtils.join(txtSet.toArray(), "");
				if (txt != null && !txt.isEmpty()) {
					txt = StringUtils.removePattern(txt, SPECIAL_CHARACTER_REGEX);
					JSONObject txtData = setTdText(txt, DOC_PARAGRAPH_TYPEID);
					txtData.put("colspan", colspan);
					txtData.put("rowspan", rowspan);
					datas.add(txtData);
				}

			} else {
				final String txt = td.text();
				JSONObject txtData = setTdText(StringUtils.removePattern(txt, SPECIAL_CHARACTER_REGEX), DOC_PARAGRAPH_TYPEID);
				txtData.put("colspan", colspan);
				txtData.put("rowspan", rowspan);
				datas.add(txtData);
			}
		}
		return datas;
	}

	public JSONObject getOneTableData(Element table) {
		JSONObject json = new JSONObject();
		try {
			json.put("type", DOC_TABLE_TYPEID);
			json.put("rows", getRowData(table));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
