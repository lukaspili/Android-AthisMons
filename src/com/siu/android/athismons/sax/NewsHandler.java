package com.siu.android.athismons.sax;

import android.util.Log;
import com.siu.android.andutils.util.DateUtils;
import com.siu.android.athismons.dao.model.News;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsHandler extends AbstractHandler<News> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss Z", Locale.ENGLISH);

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
            try {
                element.setPubDate(DateUtils.formatAsFull(dateFormat.parse(value)));
            } catch (Exception e) {
                element.setPubDate(value);
                Log.e(getClass().getName(), "Invalid date format : " + value, e);
            }

        } else if (localName.equalsIgnoreCase("description")) {
            element.setDescription(value);

        } else if (localName.equalsIgnoreCase("item")) {
            list.add(element);
            element = null;
        }
    }
}
