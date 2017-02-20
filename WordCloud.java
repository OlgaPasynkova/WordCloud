package de.pasynkova.wordcloud;

import java.io.*;
//import javax.activation;

/**
 * Created by Olga Pasynkova on 17.02.2017.
 */
public class WordCloud {
    public static void main(String[] args) {

        System.out.println("Word Cloud Generator v1.0");
        System.out.println( "Please, write in console some word(s) with whitespaces and press Enter"  );

        String wordsForCloud = null;
        BufferedReader queryResult = null;
        StringBuilder queryResultFiltered = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            wordsForCloud = reader.readLine().replaceAll(" * ","+");
            System.out.println(wordsForCloud);
            if (!wordsForCloud.equals(null)) {
                queryResult = GoogleQuery.search (args[0], args[1],wordsForCloud);
            }

            queryResultFiltered = JsonFilter.snippetFiltern(queryResult);
            queryResult.close();
            WordCloudGenerator.generateWordMap(queryResultFiltered);



        }
        catch (Exception e){
            System.out.println( e.getMessage() + e.getStackTrace());
        }





    }
}
