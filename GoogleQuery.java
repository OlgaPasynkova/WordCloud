package de.pasynkova.wordcloud;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Olga Pasynkova on 16.02.2017.
 */
public class GoogleQuery {
    //static File file = new File("d:/downloads/test3.txt");
            public static BufferedReader search(String appKey, String googleCustomEngineKey, String wordsToSearch) throws IOException {

                URL url = new URL(
                        "https://www.googleapis.com/customsearch/v1?key="+appKey+"&cx="+ googleCustomEngineKey+"&q="+ wordsToSearch +"&start=1&alt=json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                System.out.println(url);
                //conn.disconnect();
                return br;


            }



}
