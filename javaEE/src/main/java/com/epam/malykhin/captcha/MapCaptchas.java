package com.epam.malykhin.captcha;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Serhii_Malykhin on 04.12.16.
 */
public class MapCaptchas extends Thread {

    private static final Logger LOG = Logger.getLogger(MapCaptchas.class);
    /**
     * slept for 5 min
     */
    private final long sleepTime = 1000 * 60 * 5;
    private Map<Integer, EpamCaptcha> map;
    private int setId = 0;

    public MapCaptchas() {
        map = new HashMap<>();
    }

    public synchronized int addCaptcha(EpamCaptcha epamCaptcha) {
        cleanCaptchaMap();

        map.put(++setId, epamCaptcha);

        return setId;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (map.size() > 0)
                    cleanCaptchaMap();
                sleep(sleepTime / 2);
            } catch (InterruptedException e) {
                LOG.warn(e);
            }
        }
    }

    public synchronized void cleanCaptchaMap() {
        synchronized (map) {
            Iterator<Map.Entry<Integer, EpamCaptcha>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, EpamCaptcha> tmpCaptcha = iter.next();
                if (isNeedRemove(tmpCaptcha.getValue())) {
                    iter.remove();
                }
            }
        }
    }

    public EpamCaptcha getCaptchaById(int id) {
        return map.get(id);
    }

    private boolean isNeedRemove(EpamCaptcha value) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - value.getTime()) < sleepTime ? false : true;
    }

    public boolean containsValue(EpamCaptcha epamCaptcha) {
        return map.containsValue(epamCaptcha);
    }
}
