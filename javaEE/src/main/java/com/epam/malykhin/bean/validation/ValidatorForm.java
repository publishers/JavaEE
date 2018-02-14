package com.epam.malykhin.bean.validation;

import com.epam.malykhin.bean.BeanForm;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.malykhin.util.StaticTransformVariable.FORM_FIELD_PASSWORD;
import static com.epam.malykhin.util.StaticTransformVariable.FORM_FIELD_PASSWORD2;

/**
 * Created by Serhii_Malykhin on 02.12.16.
 */
public class ValidatorForm {
    private static final Logger LOG = Logger.getLogger(ValidatorForm.class);
    private static final String REGEX_PASSWORD = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*{8,}";
    private static final String REGEX_EMAIL = "^[\\.a-zA-Z0-9_-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private static final String REGEX_NAME = "^[A-Z]{1}[a-zA-Z-]*$";
    private static final String REGEX_CAPTCHA = "^\\d+$";
    private static final String REGEX_NEWSLETTER = "^(|.+)$";
    private static final String[] REGEX_FORM_VALIDATION = {
            REGEX_NAME, REGEX_NAME, REGEX_EMAIL, REGEX_PASSWORD,
            REGEX_PASSWORD, REGEX_CAPTCHA, REGEX_NEWSLETTER
    };
    private BeanForm beanForm;
    private boolean isValidForm = true;

    private Map<String, Boolean> beanFormValidation;

    public ValidatorForm(BeanForm beanForm) {
        this.beanForm = beanForm;
        beanFormValidation = new LinkedHashMap<>();
    }

    public void startValidation() {
        Map<String, String> beans = beanForm.getBeans();
        Iterator<Map.Entry<String, String>> iter = beans.entrySet().iterator();
        for (int nextElement = 0; iter.hasNext(); nextElement++) {
            Map.Entry<String, String> tmp = iter.next();
            if (tmp.getValue() != null) {
                beanFormValidation.put(
                        tmp.getKey(),
                        checkValidationFieldByRegex(
                                tmp.getValue(),
                                REGEX_FORM_VALIDATION[nextElement]) ? true : (isValidForm = false)
                );
            } else {
                beanFormValidation.put(tmp.getKey(), (isValidForm = false));
            }
        }
        validatePasswords();
    }

    private void validatePasswords() {
        Map<String, String> beans = beanForm.getBeans();
        String pass = beans.get(FORM_FIELD_PASSWORD);
        String pass2 = beans.get(FORM_FIELD_PASSWORD2);
        if (pass2 != null && pass != null) {
            if (pass.equals(pass2)) {
                beanFormValidation.put(FORM_FIELD_PASSWORD2, true);
            }
        } else {
            beanFormValidation.put(FORM_FIELD_PASSWORD2, (isValidForm = false));
        }
    }

    public boolean isValidForm() {
        return isValidForm;
    }

    public Map<String, Boolean> getBeanFormValidation() {
        return beanFormValidation;
    }

    private boolean checkValidationFieldByRegex(String val, String regex) {
        return val.matches(regex);
    }
}
