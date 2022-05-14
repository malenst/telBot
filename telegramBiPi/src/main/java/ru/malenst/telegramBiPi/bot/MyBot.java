package ru.malenst.telegramBiPi.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.malenst.telegramBiPi.bot.courses.Courses;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "project_bipi_bot";
    }

    @Override
    public String getBotToken() {
        return "5188180449:AAFGhIuRWROYHTZmxIs-R3_pDLunUWcXuuo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().equalsIgnoreCase("Привет")) {

            sendMessage((update.getMessage().getChatId().toString()), "Привет, " + update.getMessage().getFrom().getFirstName());
        }


        if (update.getMessage().getText().substring(0,4).equalsIgnoreCase("Курс")) {

            try {
                execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                int wid = update.getMessage().getText().length();
                sendMessage((update.getMessage().getChatId().toString()), Courses.course(update.getMessage().getText().substring(wid - 4,wid - 1)) + "\n \n *По данным investing.com (Форекс)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] inpMes = update.getMessage().getText().split(" ");
        if (update.getMessage().getText().substring(0,6).equalsIgnoreCase("Погода") && inpMes.length == 3) {


            try {
                sendMessage((update.getMessage().getChatId().toString()), Weather.weather(inpMes[inpMes.length - 1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (update.getMessage().getText().substring(0,6).equalsIgnoreCase("Погода") && Objects.equals(inpMes[inpMes.length - 1], "завтра")) {
            try {
                sendMessage((update.getMessage().getChatId().toString()), Weather.weather(inpMes[inpMes.length - 2] + "+" + inpMes[inpMes.length - 1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (update.getMessage().getText().substring(0,11).equalsIgnoreCase("Коронавирус")) {
            String place = inpMes[0];

            for(int i = 1; i<inpMes.length; i++) {
                place += "+" + inpMes[i];
            }

            try {
                sendMessage((update.getMessage().getChatId().toString()), Covid.covid(place));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            setButton(message);
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static Document getPage(String ssilka) throws IOException {
        String url = ssilka;
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static String cov_dat() throws IOException{
        Document document = getPage("https://yandex.ru/covid19/stat");
        Element element = document.select("div[class=today__categories").first();
        String covDate = element.select("div[class=subheader_s]").text();
        return covDate;
    }

    private static String cov_zar_world() throws IOException{
        Document document = getPage("https://coronavirus-control.ru/");
        Element element = document.select("div[id=block_2-number]").first();
        //Element element2 = document.select("span[class=block_span]").first();
        //String covAllWorld = "В мире зафиксировано: " + element.text();
        Element covAllWorld1 = element.select("span[class=block_span]").first();
        String a = "В мире зафиксировано: " + element.text() + "(" + covAllWorld1.text() + ")";
        //covZarWorld = "Всего заражено в мире: " + covAllWorld.text() + "("+ element.text() + ")";
        return a;
    }

    //keys

   public void setButton(SendMessage sendMassage){
        ReplyKeyboardMarkup replykeyboardmarkup = new ReplyKeyboardMarkup(); //создание клавы самой по себе
        sendMassage.setReplyMarkup(replykeyboardmarkup); //установка кнопок
        replykeyboardmarkup.setSelective(true); // клавиатура видна всем а не конкретному пользователю
        replykeyboardmarkup.setResizeKeyboard(true);// диномическая настройка размера
        replykeyboardmarkup.setOneTimeKeyboard(false); //клава остаётся после нажатия

       List<KeyboardRow> keyRaw = new ArrayList<>();

       KeyboardRow keyFirstRow = new KeyboardRow();
       keyFirstRow.add(new KeyboardButton("Привет"));
       KeyboardRow keyFirstRow1 = new KeyboardRow();
       keyFirstRow1.add(new KeyboardButton("Привет"));
       keyFirstRow1.add(new KeyboardButton("Курс"));
       keyRaw.add(keyFirstRow1);

       replykeyboardmarkup.setKeyboard(keyRaw);
    }

    public static SendMessage sendInlineKeyBoardMessage(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Доллар");
        inlineKeyboardButton1.setCallbackData("usd");
        inlineKeyboardButton2.setText("Евро");
        inlineKeyboardButton2.setCallbackData("eur");
        inlineKeyboardButton1.setText("Дирхам");
        inlineKeyboardButton1.setCallbackData("aed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow1.add(inlineKeyboardButton3);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return SendMessage.builder().chatId(chatId).replyMarkup(inlineKeyboardMarkup).build();
    }
}
