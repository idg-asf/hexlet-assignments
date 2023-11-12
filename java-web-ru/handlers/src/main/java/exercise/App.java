package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.util.List;

public final class App {

    public static Javalin getApp() {

        // BEGIN
        var app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });

        List<String> phones = Data.getPhones();
        List<String> domains = Data.getDomains();

        ObjectMapper objectMapper = new ObjectMapper();
        String phonesJson = "";
        String domainsJson = "";
        try {
            phonesJson = objectMapper.writeValueAsString(phones);
            domainsJson = objectMapper.writeValueAsString(domains);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        String finalPhonesJson = phonesJson;
        app.get("/phones", ctx -> ctx.json(finalPhonesJson));
        String finalDomainsJson = domainsJson;
        app.get("/domains", ctx -> ctx.json(finalDomainsJson));

        return app;
        // END
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
