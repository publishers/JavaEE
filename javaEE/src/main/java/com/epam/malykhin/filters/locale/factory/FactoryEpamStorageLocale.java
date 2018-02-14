package com.epam.malykhin.filters.locale.factory;

import com.epam.malykhin.filters.locale.CookieEpamStorageLocale;
import com.epam.malykhin.filters.locale.EpamStorageLocale;
import com.epam.malykhin.filters.locale.SessionEpamStorageLocale;

import static java.util.Objects.isNull;

/**
 * Created by Serhii_Malykhin on 12/23/2016.
 */
public class FactoryEpamStorageLocale {
    private EpamStorageLocale epamStorageLocale;

    public EpamStorageLocale getEpamStorageLocale() {
        return epamStorageLocale;
    }

    public void setEpamStorageLocale(String typeStorage) {
        switch (typeStorage) {
            case "session":
                epamStorageLocale = new SessionEpamStorageLocale();
                break;
            case "cookie":
                epamStorageLocale = new CookieEpamStorageLocale();
                break;
        }
        if (isNull(epamStorageLocale))
            throw new IllegalArgumentException("This application doesn't support the type " + typeStorage + "\n Make correct your type");
    }
}
