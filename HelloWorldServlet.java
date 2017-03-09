package de.pasynkova.wordcloud;

/**
 * Created by Olga Pasynkova on 20.02.2017.
 */
import java.io.*;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

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

            String relativeWebPath = "WEB-INF/classes/backgrounds/dark-pink-heart.png";
            InputStream imageTemplate = getServletContext().getResourceAsStream(relativeWebPath);

            WordCloudGenerator.generateWordMap(imageTemplate,queryResultFiltered, os );
            imageBase64 = Base64.getEncoder().encodeToString(os.toByteArray());

        } catch (Exception e) {
            errorMessage = e.getMessage() + e.getStackTrace();
        }

        out.println(docType + "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>Words for cloud</b>: "
                + wordsForCloud + "\n" +
                "</ul>\n" +
                "<p>" + errorMessage +"</p>"+

                "<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\n" +
                "AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO\n" +
                "9TXL0Y4OHwAAAABJRU5ErkJggg==\" alt=\"Red dot\" />" +

                "<img src=\"data:image/png;base64," + imageBase64 +
                " \"alt=\"Red dot\" />" +
                "</body></html>");



    }

}
