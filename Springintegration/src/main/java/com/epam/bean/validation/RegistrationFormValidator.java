package com.epam.bean.validation;

import com.epam.bean.BeanForm;
import org.apache.log4j.Logger;
import org.apache.solr.common.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.util.StaticTransformVariable.FORM_FIELD_PASSWORD2;

@Component
public class RegistrationFormValidator implements Validator {
    private static final Logger LOG = Logger.getLogger(RegistrationFormValidator.class);
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

    private Map<String, Boolean> beanFormValidation = new LinkedHashMap<>();

    @Override
    public boolean supports(Class<?> clazz) {
        return BeanForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        this.beanForm = (BeanForm) target;
        startValidation();
    }

    private void startValidation() {

//        Map<String, String> beans = beanForm.getBeans();
//        Iterator<Map.Entry<String, String>> iter = beans.entrySet().iterator();
//        for (int nextElement = 0; iter.hasNext(); nextElement++) {
//            Map.Entry<String, String> tmp = iter.next();
//            if (tmp.getValue() != null) {
//                beanFormValidation.put(
//                        tmp.getKey(),
//                        tmp.getValue().matches(REGEX_FORM_VALIDATION[nextElement]) || (isValidForm = false)
//                );
//            } else {
//                beanFormValidation.put(tmp.getKey(), (isValidForm = false));
//            }
//        }
//        validateName(beanForm.getFirstName());
//        validateName(beanForm.getSecondName()));
        validatePasswords();
    }

//    private void validateName(String name, String fieldName,int indexRegex) {
//        if (!StringUtils.isEmpty(name)) {
//            beanFormValidation.put(
//                    tmp.getKey(),
//                    name.matches(REGEX_FORM_VALIDATION[indexRegex]) || (isValidForm = false)
//            );
//        } else {
//            beanFormValidation.put(tmp.getKey(), (isValidForm = false));
//        }
//    }

    private void validatePasswords() {
        String pass = beanForm.getPassword();
        String pass2 = beanForm.getPassword2();
        if (pass2 != null && pass != null) {
            if (pass.equals(pass2)) {
                beanFormValidation.put(FORM_FIELD_PASSWORD2, true);
            }
        } else {
            beanFormValidation.put(FORM_FIELD_PASSWORD2, (isValidForm = false));
        }
    }

}
