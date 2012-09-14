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
        news.implementsInterface("com.siu.android.athismons.model.Detail");
        news.addIdProperty();
        news.addStringProperty("title");
        news.addStringProperty("category");
        news.addStringProperty("image");
        news.addStringProperty("pubDate");
        news.addStringProperty("description");
        news.addStringProperty("url");

        Entity agenda = schema.addEntity("Agenda");
        agenda.implementsSerializable();
        agenda.implementsInterface("com.siu.android.athismons.model.Detail");
        agenda.addIdProperty();
        agenda.addStringProperty("title");
        agenda.addStringProperty("category");
        agenda.addStringProperty("image");
        agenda.addStringProperty("description");
        agenda.addStringProperty("url");

        Entity directory = schema.addEntity("Directory");
        directory.implementsSerializable();
        directory.addIdProperty();
        directory.addStringProperty("title");
        directory.addStringProperty("listPicture");
        directory.addStringProperty("url");

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
        card.addStringProperty("listPicture");
        card.addDoubleProperty("latitude");
        card.addDoubleProperty("longitude");
        card.addStringProperty("url");

        Property directoryId = card.addLongProperty("directoryId").notNull().getProperty();
        ToMany customerToOrders = directory.addToMany(card, directoryId);

        Entity menu = schema.addEntity("Menu");
        menu.implementsSerializable();
        menu.implementsInterface("com.siu.android.athismons.model.Detail");
        menu.addIdProperty();
        menu.addStringProperty("title");
        menu.addStringProperty("description");
        menu.addStringProperty("link");
        menu.addStringProperty("picture");

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "./src-gen");
    }
}
