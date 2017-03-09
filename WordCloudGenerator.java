package de.pasynkova.wordcloud;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import org.apache.commons.io.input.ReaderInputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * Created by Olga Pasynkova on 17.02.2017.
 */
public class WordCloudGenerator {

    public static void generateWordMap(InputStream imageTemplate, StringBuilder queryResultFiltered, OutputStream outputStream ) {


        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(400);
        frequencyAnalyzer.setMinWordLength(4);
        //frequencyAnalyzer.setStopWords(loadStopWords());

        try {
            InputStream wordsStream = new ReaderInputStream(new StringReader(queryResultFiltered.toString())) ;
            final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(wordsStream );
            final Dimension dimension = new Dimension(600, 386);
            final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
            wordCloud.setPadding(1);
            wordCloud.setBackgroundColor(Color.WHITE);
            wordCloud.setBackground(new PixelBoundryBackground(imageTemplate));
            wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0x000000)));
            wordCloud.setFontScalar(new LinearFontScalar(10, 40));
            wordCloud.build(wordFrequencies);
            wordCloud.writeToStreamAsPNG(outputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }


    }
}
