package com.epam.captcha;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode(exclude = "time")
@AllArgsConstructor
public class EpamCaptcha implements Serializable {
    @Getter
    @Setter
    private int captcha;
    @Getter
    private long time;
}
