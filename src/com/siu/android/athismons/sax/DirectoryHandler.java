package com.siu.android.athismons.sax;

import android.util.Log;
import com.siu.android.athismons.dao.model.Card;
import com.siu.android.athismons.dao.model.Directory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DirectoryHandler extends AbstractHandler<Directory> {

    private static final String DIRECTORY = "theme";
    private static final String CARD = "fiche";

    private boolean inDirectory, inCard;
    private Card card;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase(DIRECTORY)) {
            element = new Directory();
            inDirectory = true;
        } else if (localName.equalsIgnoreCase(CARD)) {
            card = new Card();
            inCard = true;
        }

        value = null; // reset value each time
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // in card tags
        if (inCard) {
            parseCard(localName);
        }

        // in directory tags
        else {
            parseDirectory(localName);
        }
    }

    private void parseDirectory(String localName) {
        if (localName.equalsIgnoreCase("intitule")) {
            element.setTitle(value);
        } else if (localName.equalsIgnoreCase("visuel")) {
            element.setListPicture(value);
        }

        // end dirctory tag
        else if (localName.equalsIgnoreCase(DIRECTORY)) {
            list.add(element);
            inDirectory = false;
        }
    }

    private void parseCard(String localName) {
        if (localName.equalsIgnoreCase("intitule")) {
            card.setTitle(value);
        } else if (localName.equalsIgnoreCase("heberge")) {
            card.setBuilding(value);
        } else if (localName.equalsIgnoreCase("voie")) {
            card.setStreet(value);
        } else if (localName.equalsIgnoreCase("codePostal")) {
            card.setPostalCode(value);
        } else if (localName.equalsIgnoreCase("ville")) {
            card.setCity(value);
        } else if (localName.equalsIgnoreCase("precision")) {
            card.setAddressComplement(value);
        } else if (localName.equalsIgnoreCase("contact")) {
            card.setContact(value);
        } else if (localName.equalsIgnoreCase("telephone_1")) {
            card.setPhone1(value);
        } else if (localName.equalsIgnoreCase("telephone_2")) {
            card.setPhone2(value);
        } else if (localName.equalsIgnoreCase("email")) {
            card.setEmail(value);
        } else if (localName.equalsIgnoreCase("site")) {
            card.setWebsite(value);
        } else if (localName.equalsIgnoreCase("petit")) {
            card.setPicture(value);
        } else if (localName.equalsIgnoreCase("visuel")) {
            card.setListPicture(value);
        }

        // lat / long
        else if (localName.equalsIgnoreCase("lat")) {
            try {
                card.setLatitude(Double.valueOf(value));
            } catch (Exception e) {
                Log.e(getClass().getName(), "Invalid latitude", e);
                card.setLatitude(0.0);
            }
        } else if (localName.equalsIgnoreCase("lng")) {
            try {
                card.setLongitude(Double.valueOf(value));
            } catch (Exception e) {
                Log.e(getClass().getName(), "Invalid longitude", e);
                card.setLongitude(0.0);
            }
        }

        // end card tag
        else if (localName.equalsIgnoreCase(CARD)) {
            element.getTransientCards().add(card);
            inCard = false;
        }
    }
}
