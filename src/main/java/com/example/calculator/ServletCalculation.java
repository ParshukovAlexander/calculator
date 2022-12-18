package com.example.calculator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calculation")
public class ServletCalculation extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        StringBuffer jb = new StringBuffer();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw =response.getWriter();

        String line;

        try {

            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {

                jb.append(line);

            }
        }catch ( Exception e){
            System.out.println("Error");
        }

        JsonObject jobj=gson.fromJson(String.valueOf(jb),JsonObject.class);

        request.setCharacterEncoding("UTF-8");

        try {

            double a = Double.parseDouble(jobj.get("a").getAsString());
            double b = Double.parseDouble(jobj.get("b").getAsString());
            String math = jobj.get("math").getAsString();

            pw.println(result(a, b, math));
        }catch (NumberFormatException e){
            pw.println("Некорректные данные");
        }

        JSONObject obj = new JSONObject();
        try {
            obj.put("result", "foo");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String result(double a, double b, String math) {
        if (math.contains("+")) return String.valueOf(a+b);
        if (math.contains("-")) return String.valueOf(a-b);
        if (math.contains("*")) return String.valueOf(a*b);
        if (math.contains("/")) return String.valueOf(a/b);
        return "Некорректные данные";
    }
}
