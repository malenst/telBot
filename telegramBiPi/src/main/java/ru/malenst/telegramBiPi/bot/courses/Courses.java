package ru.malenst.telegramBiPi.bot.courses;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class Courses {

    private static Document getPage(String ssilka) throws IOException {
        String url = ssilka;
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static String course(String cur) throws IOException {
        Document document = getPage("https://ru.investing.com/currencies/" + cur + "-rub");
        Element course = document.select("div[class=instrument-price_instrument-price__3uw25 flex items-end flex-wrap font-bold]").first();
        String ourCourse = course.select("span[class=text-2xl]").text();
        return ourCourse;
    }
}
