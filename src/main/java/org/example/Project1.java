package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Project1 {
    public static void main(String[] args) {
        //네이버 스포츠 크롤링
        String url = "https://sports.news.naver.com/wfootball/index";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element = doc.select("div.home_news");
        //제목글자만 가져와서 적기
        String title = element.select("h2").text().substring(0, 4);
        System.out.println("=============================================");
        System.out.println(title);
        System.out.println("=============================================");
        //모든 li리스트의 모든 글자들을 출력한다.
        for(Element el:element.select("li")){
            if (el.text().length()>=45){
                System.out.println(el.text().substring(0,30)+"...");
            }
            System.out.println(el.text());
        }
        System.out.println("=============================================");
    }
}