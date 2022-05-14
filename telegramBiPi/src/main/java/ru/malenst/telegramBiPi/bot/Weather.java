package ru.malenst.telegramBiPi.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class Weather {
    private static Document getPage(String ssilka) throws IOException {
        String url = ssilka;
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String weather(String city) throws IOException {
        Document document = getPage("https://yandex.ru/search/?text=погода+в+" + city);
        Element tempEl = document.select("div[class=weather-forecast__current]").first();
        String[] a =  tempEl.select("div[class=weather-forecast__current-details]").text().split(" ");
        String[] wind =  tempEl.select("div[class=weather-forecast__desc-value]").text().split(" ");
        String weat = a[0] + "\n" + tempEl.select("div[class=weather-forecast__current-temp]").text() + "\n Ветер " + wind[0] + wind[1];

        return weat;
    }
}
