package de.pasynkova.wordcloud;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Olga Pasynkova on 17.02.2017.
 */
public class WordCloud extends HttpServlet {
   /* public static void main(String[] args) {

        System.out.println("Word Cloud Generator v1.0");
        System.out.println("Please, write in console some word(s) with whitespaces and press Enter");

        String wordsForCloud = null;
        BufferedReader queryResult = null;
        StringBuilder queryResultFiltered = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            wordsForCloud = reader.readLine().replaceAll(" * ", "+");
            System.out.println(wordsForCloud);
            if (!wordsForCloud.equals(null)) {
                queryResult = GoogleQuery.search(args[0], args[1], wordsForCloud);
            }

            queryResultFiltered = JsonFilter.snippetFiltern(queryResult);
            queryResult.close();
            WordCloudGenerator.generateWordMap(queryResultFiltered);


        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace());
        }


    } */

    public void doGet(String[] args,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        BufferedReader queryResult = null;
        StringBuilder queryResultFiltered = null;

        PrintWriter out = response.getWriter();
        String title = "Words Cloud";
        String wordsForCloud = null;
        String errorMessage = "No error";
        try {
            wordsForCloud = request.getParameter("words_for_map").replaceAll(" * ", "+");
           //System.out.println(wordsForCloud);
            if (!wordsForCloud.equals(null)) {
                queryResult = GoogleQuery.search(args[0], args[1], wordsForCloud);
            }

            queryResultFiltered = JsonFilter.snippetFiltern(queryResult);
            queryResult.close();
            WordCloudGenerator.generateWordMap(queryResultFiltered);


        } catch (Exception e) {
            errorMessage = e.getMessage() + e.getStackTrace();
        }

        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " +
                "transitional//en\">\n";

        out.println(docType + "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "  <b>Words</b>: "
                + request.getParameter("words_for_map") + "\n" +
                "<p>" + errorMessage +"</p>" +
                "<img src=\"d:/downloads/whale_wordcloud1.png\"" +
                "</body></html>");



    }

}
