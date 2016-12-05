package com.company;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;


import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static User user;

    public static void main(String[] args) {
        ArrayList<Message> messages = new ArrayList<>();

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if(user == null) {
                        return new ModelAndView(m, "index.html");
                    } else {
                        m.put("name", user.name);
                        m.put("messages", messages);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    user = new User(name, password);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(

                "/create-message",
                ((request, response) -> {
                    String text = request.queryParams("userMessage");
                    Message p = new Message (text);
                    messages.add(p);
                    response.redirect("/");
                    return "";
                })
        );
    }
}
