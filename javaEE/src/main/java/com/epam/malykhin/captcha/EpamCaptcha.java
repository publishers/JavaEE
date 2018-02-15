package com.epam.malykhin.captcha;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode(exclude = {"createdTime"})
public class EpamCaptcha implements Serializable {
    @Getter
    private int captcha;
    @Getter
    private long createdTime;
}
