package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Project3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String jusick=sc.nextLine();

        String url = "https://finance.naver.com/item/main.naver?code="+jusick;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements todaylist = doc.select("div.new_totalinfo dl>dd");

        String time = todaylist.get(0).text();
        String name = todaylist.get(1).text().split(" ")[1];
        String juga = todaylist.get(3).text().split(" ")[1];
        String dungRakrate = todaylist.get(3).text().split(" ")[6];
        String siga = todaylist.get(5).text().split(" ")[1];
        String goga = todaylist.get(6).text().split(" ")[1];
        String zeoga = todaylist.get(8).text().split(" ")[1];
        String georaeryang = todaylist.get(10).text().split(" ")[1];

        String stype = todaylist.get(3).text().split(" ")[3];
        String vsYesterday = todaylist.get(3).text().split(" ")[4];

        System.out.printf("======================%s======================\r\n",name); //종목명
        System.out.println("주가:"+juga);
        System.out.println("등락률:"+dungRakrate+"%");
        System.out.println("시가:"+siga);
        System.out.println("고가:"+goga);
        System.out.println("저가:"+zeoga);
        System.out.println("거래량:"+georaeryang);
        System.out.println("타입:"+stype);
        System.out.println("전일대비:"+vsYesterday);
        System.out.println("가져오는 시간:"+time);

    }
}
