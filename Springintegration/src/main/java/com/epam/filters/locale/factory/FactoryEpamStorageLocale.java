package com.epam.filters.locale.factory;

import com.epam.filters.locale.CookieEpamStorageLocale;
import com.epam.filters.locale.EpamStorageLocale;
import com.epam.filters.locale.SessionEpamStorageLocale;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

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
