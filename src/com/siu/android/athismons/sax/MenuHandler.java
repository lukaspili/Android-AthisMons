package com.siu.android.athismons.sax;

import com.siu.android.athismons.dao.model.Menu;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class MenuHandler extends AbstractHandler<Menu> {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase("item")) {
            element = new Menu();
        } else if (localName.equalsIgnoreCase("enclosure")) {
            element.setPicture(attributes.getValue("url"));
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
            element.setTitle(value);

        } else if (localName.equalsIgnoreCase("description")) {
            element.setDescription(value);

        } else if (localName.equalsIgnoreCase("link")) {
            element.setLink(value);

        } else if (localName.equalsIgnoreCase("item")) {
            list.add(element);
            element = null;
        }
    }
}
