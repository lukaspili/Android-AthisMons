package com.siu.android.athismons.daogenerator;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

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

        Entity directory = schema.addEntity("Directory");
        directory.implementsSerializable();
        directory.addIdProperty();
        directory.addStringProperty("title");

        Entity card = schema.addEntity("Card");
        card.implementsSerializable();
        card.addIdProperty();
        card.addStringProperty("title");
        card.addStringProperty("building");
        card.addStringProperty("street");
        card.addStringProperty("city");
        card.addStringProperty("postalCode");
        card.addStringProperty("addressComplement");
        card.addStringProperty("picture");
        card.addStringProperty("contact");
        card.addStringProperty("website");
        card.addStringProperty("email");
        card.addStringProperty("phone1");
        card.addStringProperty("phone2");
        card.addDoubleProperty("latitude");
        card.addDoubleProperty("longitude");

        Property directoryId = card.addLongProperty("directoryId").notNull().getProperty();
        ToMany customerToOrders = directory.addToMany(card, directoryId);

//        Property directoryIdProperty = card.addLongProperty("directoryId").getProperty();
//        card.addToOne(directory, directoryIdProperty);

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "./src-gen");
    }
}
