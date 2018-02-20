package com.epam.captcha;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

@Component
public class MapCaptchas extends Thread {

    private static final Logger LOG = Logger.getLogger(MapCaptchas.class);
    private final long sleepTime = 1000 * 60 * 5;
    private Map<Integer, EpamCaptcha> map;
    private int setId = 0;

    public MapCaptchas() {
        map = new Hashtable<>();
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

    private synchronized void cleanCaptchaMap() {
        map.entrySet().removeIf(tmpCaptcha -> isNeedRemove(tmpCaptcha.getValue()));
    }

    public EpamCaptcha getCaptchaById(int id) {
        return map.get(id);
    }

    private boolean isNeedRemove(EpamCaptcha value) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - value.getTime()) < sleepTime;
    }

    public boolean containsValue(EpamCaptcha epamCaptcha) {
        return map.containsValue(epamCaptcha);
    }
}
