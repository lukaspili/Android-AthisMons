package com.siu.android.athismons.daogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DaoGenerator {

    public static void main(String... args) throws Exception {

        Schema schema = new Schema(1, "com.siu.android.athismons.dao.model");
        schema.enableKeepSectionsByDefault();

        Entity entry = schema.addEntity("News");
        entry.implementsSerializable();

        entry.addIdProperty();
        entry.addStringProperty("title");
        entry.addStringProperty("category");
        entry.addStringProperty("image");
        entry.addStringProperty("pubDate");
        entry.addStringProperty("description");

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "./src-gen");
    }
}
