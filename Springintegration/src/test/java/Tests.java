import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    @Ignore
    @Test
    public void test() {
        List<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>> classes =
                new ArrayList<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>>();
        for (String className : StringUtils.tokenizeToStringArray("test,test2;test3;test4,", ",;\t\n")) {
            classes.add(loadInitializerClass(className));
        }

        System.out.println(classes);
    }

    private Class<ApplicationContextInitializer<ConfigurableApplicationContext>> loadInitializerClass(String className) {
        try {
            Class<?> clazz = ClassUtils.forName(className, ClassUtils.getDefaultClassLoader());
            Assert.isAssignable(ApplicationContextInitializer.class, clazz);
            return (Class<ApplicationContextInitializer<ConfigurableApplicationContext>>) clazz;
        } catch (ClassNotFoundException ex) {
            throw new ApplicationContextException("Failed to load context initializer class [" + className + "]", ex);
        }
    }
}
