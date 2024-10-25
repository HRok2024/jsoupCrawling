package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class Project2 {
    public static void main(String[] args) {
        //네이버 스포츠 크롤링
        String url = "http://www.cgv.co.kr/movies/";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element = doc.select("div.sect-movie-chart");
        System.out.println("=======================================");

        //Iterator을 사용하여 하나씩 값 가져오기
        // 순차적으로 rank,title에 대한 내용들을 변수에 담아준다
        // 해당하는 리스트들이 하나씩 담긴다
        // 그래서 while문에서 다음내용을 출력하게 하면 1번랭크-1번이름, 2번랭크-2번이름, 3번랭크-3번이름,... 이렇게 나온다

        Iterator<Element> el1=element.select("strong.rank").iterator();
        Iterator<Element> el2=element.select("strong.title").iterator();
        while(el1.hasNext()&&el2.hasNext()) {
            System.out.println(el1.next().text()+"\t"+el2.next().text());
        }
        System.out.println("=======================================");
    }
}
