package edu.escuelaing.arep;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static spark.Spark.*;

public class App {
    public static void main(String[] args){
        port(getPort());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        secure("keystores/ecikeystore.p12", "123456", "keystores/myTrustStore","123456");
        staticFileLocation("/static");
        get("/consumir", (req, res) -> "{\"Date\": \""+dtf.format(LocalDateTime.now())+"\"}");
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
