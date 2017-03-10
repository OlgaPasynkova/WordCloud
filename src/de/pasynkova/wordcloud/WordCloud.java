package de.pasynkova.wordcloud;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Olga Pasynkova on 17.02.2017.
 */
public class WordCloud extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Words Cloud";

        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " +
                "transitional//en\">\n";
        BufferedReader queryResult = null;
        StringBuilder queryResultFiltered = null;

        String wordsForCloud = null;
        String errorMessage = "No error";

        String imageBase64 = "";


        try {
            wordsForCloud = request.getParameter("words_for_map").replaceAll(" * ", "+");

            if (!wordsForCloud.equals(null)) {
                queryResult = GoogleQuery.search(Constants.APP_KEY, Constants.GOOGLE_ENGINE_KEY, wordsForCloud);
            }

            queryResultFiltered = JsonFilter.snippetFiltern(queryResult);
            queryResult.close();

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            String relativeWebPath = "WEB-INF/classes/backgrounds/cloud_fg.bmp";
            InputStream imageTemplate = getServletContext().getResourceAsStream(relativeWebPath);

            WordCloudGenerator.generateWordMap(imageTemplate,queryResultFiltered, os );
            imageBase64 = Base64.getEncoder().encodeToString(os.toByteArray());

        } catch (Exception e) {
            errorMessage = e.getMessage() + e.getStackTrace();
        }
        out.println(docType +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width\">\n" +
                "\t<title>Word cloud</title>\n" +
                "\t<link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\">\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"print.css\">\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" media=\"screen and (max-width: 630px), screen and (max-device-width: 630px)\" href=\"mobil.css\"> \n" +
                "\t<!--[if lt IE 9]>\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"ie-alt.css\">\n" +
                "\t<![endif]-->\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div id=\"container\">\n" +
                "\t<div id=\"kopfleiste\">\n" +
                "\t\t<h1>Word cloud</h1>\n" +
                "\t</div>\n" +
                "\t\n" +
                "\t<div id=\"menu\">\n" +
                "\t\t<table>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td><a href=\"index.html\">Word cloud</td>\n" +
                "\t\t\t\t<td><a href=\"about.html\">About this project</a></td>\n" +
                "\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t</div>\n" +
                "\t\n" +
                "\t<div id=\"inhalt\">\n" +
                "<p><b>You words cloud for: " + wordsForCloud + "</b></p>\n" +
                "<img src=\"data:image/png;base64," + imageBase64 +
                " \"alt=\"Word cloud\" />" +
                "\t\t<p></p>\n" +
                "\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n");
    }
}
