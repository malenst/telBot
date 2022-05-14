package ru.malenst.telegramBiPi.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class Covid {
    private static Document getPage(String ssilka) throws IOException {
        String url = ssilka;
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String covid(String place) throws IOException {
        Document document = getPage("https://yandex.ru/search/?text=" + place);
        Element element = document.select("div[class=sport-table__cell sport-table__cell_align_right sport-table__cell_overflow_nowrap]").first();
        Element element2 = document.select("div[class=se-chart__bars]").first();
        String numbers = "Подтверждено: " + element.text();// + "(" + element2.select("div[style=right: 0px; display: block; top: auto; bottom: 26px;]").text() + ")";// + "\n" + "Смертей: " + element.select("div[class=se-chart__value-current]").text();
        return numbers;
    }
}
