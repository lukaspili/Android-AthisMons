package com.siu.android.athismons.daogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DaoGenerator {

    public static void main(String... args) throws Exception {

        Schema schema = new Schema(3, "com.siu.android.athismons.dao.model");
        schema.enableKeepSectionsByDefault();

        Entity news = schema.addEntity("News");
        news.implementsSerializable();
        news.addIdProperty();
        news.addStringProperty("title");
        news.addStringProperty("category");
        news.addStringProperty("image");
        news.addStringProperty("pubDate");
        news.addStringProperty("description");

        Entity agenda = schema.addEntity("Agenda");
        agenda.implementsSerializable();
        agenda.addIdProperty();
        agenda.addStringProperty("title");
        agenda.addStringProperty("category");
        agenda.addStringProperty("image");
        agenda.addStringProperty("description");

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "./src-gen");
    }
}
