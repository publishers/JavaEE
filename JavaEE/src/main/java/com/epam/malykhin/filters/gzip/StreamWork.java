package com.epam.malykhin.filters.gzip;

import java.io.IOException;

interface StreamWork {
    void doChoose() throws IOException;
}