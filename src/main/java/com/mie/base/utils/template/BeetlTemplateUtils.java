package com.mie.base.utils.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ReThrowConsoleErrorHandler;
import org.beetl.core.Template;
import org.beetl.core.resource.AllowAllMatcher;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.CompositeResourceLoader;
import org.beetl.core.resource.StartsWithMatcher;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeetlTemplateUtils {
    public static GroupTemplate gt = null;
    private static Logger logger = LoggerFactory.getLogger(BeetlTemplateUtils.class);

    public BeetlTemplateUtils() {
    }

    public static String render(String template, Map<String, Object> params) {
        Template t = gt.getTemplate(template);
        t.binding(params);
        return t.render();
    }

    public static String render(String template, String varName, Object data) {
        Template t = gt.getTemplate(template);
        t.binding(varName, data);
        return t.render();
    }

    public static void renderTo(String template, Writer writer, Map<String, Object> params) {
        Template t = gt.getTemplate(template);
        t.binding(params);
        t.renderTo(writer);
    }

    static {
        StringTemplateResourceLoader stringResourceLoader = new StringTemplateResourceLoader();
        ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader("template/");
        CompositeResourceLoader loader = new CompositeResourceLoader();
        loader.addResourceLoader(new StartsWithMatcher("str:"), stringResourceLoader);
        loader.addResourceLoader(new AllowAllMatcher(), classpathResourceLoader);

        Configuration cfg;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }

        gt = new GroupTemplate(loader, cfg);
        gt.setErrorHandler(new ReThrowConsoleErrorHandler());
    }
}
