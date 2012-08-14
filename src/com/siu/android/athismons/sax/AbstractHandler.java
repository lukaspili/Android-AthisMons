package com.siu.android.athismons.sax;

import com.siu.android.athismons.dao.model.News;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public abstract class AbstractHandler<T> extends DefaultHandler {

    protected List<T> list = new ArrayList<T>();
    protected T element;
    protected String value;

    public List<T> getList() {
        return list;
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
}
