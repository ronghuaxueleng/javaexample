package com.yan.examples.conf.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class Xml {

	private Document document;
	
	public Xml(String xmlPath) {
		SAXReader reader = new SAXReader();
		try {
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			this.document = reader.read(new File(xmlPath));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public String attrValue(String strXPath) {
		Node n = document.selectSingleNode(strXPath);
		if (n != null) {
			return (n.valueOf("@value"));
		} else {
			return null;
		}
	}
	
}
