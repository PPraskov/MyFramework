package core.execution.http.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

public class ThymeleafResourceHandler {


    public String findResource(String resourceString, Context context) {
        if (true){
            StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
            resourceString = setMode(stringTemplateResolver,resourceString);
            TemplateFinder finder = new TemplateManager();
            String template = finder.findTemplate(resourceString);
            if (template == null){
                throw new RuntimeException("Template not found!");
            }
            TemplateEngine engine = new TemplateEngine();
            engine.setTemplateResolver(stringTemplateResolver);
            String result = engine.process(template, context);
            return result;
        }
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        setResolver(resolver, resourceString);
        resolver.setCacheable(true);
        resolver.setCacheTTLMs(36000000L);
        TemplateEngine engine = new TemplateEngine();
        String template;
        if (resourceString.contains(".")){
            template = resourceString.substring(resourceString.indexOf("/"), resourceString.lastIndexOf("."));
        }
        else {
            template = resourceString;
        }
        engine.setTemplateResolver(resolver);
        String result = engine.process(template, context);
        return result;
    }

    private String setMode(StringTemplateResolver stringTemplateResolver, String resourceString) {
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
        if (resourceString.contains(".")) {
            String afterDot = resourceString.substring(resourceString.lastIndexOf("."));
            if (afterDot.equals(".css")) {
                stringTemplateResolver.setTemplateMode(TemplateMode.CSS);
                return resourceString;
            } else if (afterDot.equals(".js")) {
                stringTemplateResolver.setTemplateMode(TemplateMode.JAVASCRIPT);
                return resourceString;
            }
        }
        else {
            return resourceString+".html";
        }
        return null;
    }

    private void setResolver(ClassLoaderTemplateResolver resolver, String resourceString) {
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        if (resourceString.contains(".")) {
            String afterDot = resourceString.substring(resourceString.lastIndexOf("."));
            if (afterDot.equals(".css")) {
                resolver.setTemplateMode(TemplateMode.CSS);
                resolver.setSuffix(afterDot);
            } else if (afterDot.equals(".js")) {
                resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
                resolver.setSuffix(afterDot);
            }
        }
    }

    void utl() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        Context context = new Context();
        context.setVariable("var", "value");
        String html = engine.process("index", context);

    }
}
