package edu.escuelaing.arep;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
public class App {
    public static void main(String[] args) {
        port(getPort());
        URLReader.main();
        secure("keystores/ecikeystore.p12", "123456" ,null,null);

        Gson gson = new Gson();
        Map<String,String> users = new HashMap<>();
        users.put("MatiwsxD", Hashing.sha256().hashString("1234", StandardCharsets.UTF_8).toString());
        staticFileLocation("/static");

        get("/hello", (req, res) -> "Hello World");
        get("/", (req,res) -> {
            res.redirect("login.html");

            return "";
        });
        get("/loged", (req,res) -> {
            System.out.println("sssss");
            res.redirect("loged.html");
            return "";
        });
        post("/login", (req,res) -> {
            req.session(true);
            User user = gson.fromJson(req.body(), User.class);
            if(Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString().equals(users.get(user.getUserName()))){
                req.session().attribute("User",user.getUserName());
                req.session().attribute("Loged",true);
                res.status(200);
            }
            else {
                res.status(400);
            }
            return "";
        });
        get("/successful", (req,res) ->{
            System.out.println("https://"+((req.url().split("/")[2]).split(":")[0])+":4568/consumir");
            String resp = URLReader.readURL("https://"+((req.url().split("/")[2]).split(":")[0])+":4568/consumir");
            System.out.println(resp);
            JSONObject jsonObject = new JSONObject(resp);
            return jsonObject;
        });
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}