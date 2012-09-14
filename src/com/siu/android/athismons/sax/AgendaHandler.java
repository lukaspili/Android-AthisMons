package com.siu.android.athismons.sax;

import com.siu.android.athismons.dao.model.Agenda;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AgendaHandler extends AbstractHandler<Agenda> {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase("item")) {
            element = new Agenda();
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
            String[] s = value.split("--", 2);
            if (s.length == 2) {
                element.setCategory(s[0].trim());
                element.setTitle(s[1].trim());
            } else {
                element.setTitle(value);
            }

        } else if (localName.equalsIgnoreCase("description")) {
            element.setDescription(value);

        } else if (localName.equalsIgnoreCase("link")) {
            element.setUrl(value);

        } else if (localName.equalsIgnoreCase("item")) {
            list.add(element);
            element = null;
        }
    }
}
