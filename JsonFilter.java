package de.pasynkova.wordcloud;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Olga Pasynkova on 20.02.2017.
 */
public class JsonFilter {
    public static StringBuilder snippetFiltern(BufferedReader queryResult) throws IOException{
        String output;
        String sb = "";
        StringBuilder filteredSnippets = new StringBuilder("");
        System.out.println("Output from Server .... \n");
        while ((output = queryResult.readLine()) != null) {

            if(output.contains("\"snippet\": \"")) {
                String snippet = output.substring(output.indexOf("\"snippet\": \"") +
                        ("\"snippet\": \"").length(), output.indexOf("\","));
                String snippetWithoutEscapeSequences = snippet.replace("\\n","");
                System.out.println(snippetWithoutEscapeSequences);
                filteredSnippets.append(snippetWithoutEscapeSequences);
            }


        }
        return filteredSnippets;
    }
}
