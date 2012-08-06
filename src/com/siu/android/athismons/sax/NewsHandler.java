package com.siu.android.athismons.sax;

import android.util.Log;
import com.siu.android.athismons.dao.model.News;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsHandler extends DefaultHandler {

    private List<News> list = new ArrayList<News>();
    private News element;
    private String value;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase("item")) {
            element = new News();
        } else if (localName.equalsIgnoreCase("enclosure")) {
            element.setImage(attributes.getValue("url"));
        }

        value = null; // reset value each time
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();

        if (null != value) {
            value += content;
        } else {
            value = content;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // not in item tags
        if (null == element) {
            return;
        }

        if (localName.equalsIgnoreCase("title")) {
            String[] s = value.split("-", 2);
            if (s.length == 2) {
                element.setCategory(s[0].trim());
                element.setTitle(s[1].trim());
            } else {
                element.setTitle(value);
            }

        } else if (localName.equalsIgnoreCase("pubDate")) {
            element.setPubDate(value);

        } else if (localName.equalsIgnoreCase("description")) {
            element.setDescription(value);

        } else if (localName.equalsIgnoreCase("item")) {
            list.add(element);
            element = null;
        }
    }

    public List<News> getList() {
        return list;
    }
}
