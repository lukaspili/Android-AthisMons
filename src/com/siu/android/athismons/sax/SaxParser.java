package com.siu.android.athismons.sax;

import android.util.Log;
import com.siu.android.athismons.dao.model.Agenda;
import com.siu.android.athismons.dao.model.News;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class SaxParser {

    private SAXParserFactory parserFactory = SAXParserFactory.newInstance();

    public List<News> parseNews(String content) {
        return parse(new NewsHandler(), content);
    }

    public List<Agenda> parseAgenda(String content) {
        return parse(new AgendaHandler(), content);
    }

    private <T extends AbstractHandler> List parse(T handler, String content) {
        try {
            XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(content)));
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error in parsing", e);
            return null;
        }

        return handler.getList();
    }

//    public List<Entry> parse(String content) {
//
//        if (null == content || content.trim().equals("")) {
//            Log.w(getClass().getName(), "Content to parse is empty");
//            return new ArrayList<Entry>();
//        }
//
//        return parse(new InputSource(new StringReader(content)));
//    }
//
//    public List<Entry> parse(InputStream is) {
//
//        if (null == is) {
//            return new ArrayList<Entry>();
//        }
//
//        return parse(new InputSource(is));
//    }
//
//    public List<Entry> parse(InputSource inputSource) {
//
//        long time = System.currentTimeMillis();
//
//        try {
//            xmlReader.parse(inputSource);
//        } catch (Exception e) {
//            Log.e(getClass().getName(), "Parsing failed", e);
//            return new ArrayList<Entry>();
//        }
//
//        Log.d(getClass().getName(), "Content parsed in " + (System.currentTimeMillis() - time) + " ms");
//
//        return entryHandler.getEntries();
//    }
}
