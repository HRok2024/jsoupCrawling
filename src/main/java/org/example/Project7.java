package org.example;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.example.model.ExcelVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Project7 {

    static Scanner scanner = new Scanner(System.in); //static에서 쓰기 위해서 static으로 선언
    static List<ExcelVO> bookList = new ArrayList<>();
    static HSSFWorkbook workbook=new HSSFWorkbook();

    public static void main(String[] args) {
        /* 계속 실행되게 */
        boolean running = true;
        do {
            menu();
            int key = Integer.parseInt(scanner.nextLine()); //스캐너로 받아온 값은 문자열이므로 숫자형으로 바꿔준다
            switch (key) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listCheck();
                    break;
                case 3:
                    excelInput();
                    break;
                case 4:
                    running = false; //사용자가 4를 선택하면 프로그램이 종료된다
                default:
                    break;
            }
        } while (running);
    }


    private static void searchBook() {
        System.out.print("책 검색 : ");
        String query = scanner.nextLine().trim(); //trim() : 문자열의 앞, 뒤에 있는 스페이스(띄우기)를 없애준다
        ExcelVO vo = getNaverAPI(query);

        if (vo == null) {
            return;
        }

        System.out.println("검색된 책을 저장? (y/n) 선택 : ");
        if (scanner.nextLine().toLowerCase().charAt(0) == 'y') {
            bookList.add(vo);
            System.out.println("책이 저장됩니다.");
        }else{
            System.out.println("책이 저장되지 않습니다.");
        }
    }

    /**
     * 검색어 입력을 받아서 네이버 API로 책을 검색한 결과를 리턴한다
     *
     * @param query 검색어
     * @return 책 객체
     */
    private static ExcelVO getNaverAPI(String query) {

        try {
            String openApi = "ㅁㄹ"
                    + URLEncoder.encode(query, "UTF-8"); //한글은 인코딩을 해줘야한다

            URL url = new URL(openApi.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("X-Naver-Client-Id", "ㅁㄹ");
            conn.setRequestProperty("X-Naver-Client-Secret", "ㅁㄹ");

            BufferedReader br;
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
//            System.out.println(sb.toString());

            br.close();
            conn.disconnect();

            JSONParser jp = new JSONParser();
            JSONObject a = (JSONObject) jp.parse(sb.toString());
            JSONArray books = (JSONArray) a.get("items");

            if (books.isEmpty()) {
                System.out.println("검색된 책이 없습니다.");
                return null;
            }

            JSONObject book = (JSONObject) books.get(0);//제이슨배열에서 첫번째 책을 가져온다
            ExcelVO vo = new ExcelVO();
            System.out.println(book.get("title"));
            System.out.println(book.get("author"));
            System.out.println(book.get("publisher"));
            System.out.println(book.get("isbn"));
            System.out.println(book.get("pubdate"));
            System.out.println("");
            vo.setTitle(book.get("title").toString());
            vo.setAuthor(book.get("author").toString());
            vo.setCompany(book.get("publisher").toString());
            vo.setIsbn(book.get("isbn").toString());
            vo.setImgurl(book.get("image").toString());

            return vo; //try문에서 에러가 나지 않았을 때 보내주는 리턴값, 여기서 getNaverAPI 메소드는 끝난다

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; //에러가 났을 때 해주는 리턴값이다, 일명 에러용 리턴이다
    }

    private static void menu() {
        System.out.println("========= 메뉴 =========");
        System.out.println("1. 책 검색");
        System.out.println("2. 책 리스트 확인");
        System.out.println("3. 책 리스트 엑셀 저장");
        System.out.println("4. 종료");
        System.out.println("번호 선택 : ");
    }

    private static void listCheck() {
        if(bookList.isEmpty()) {
            System.out.println("리스트에 저장된 책이 없습니다.");
        }else{
            for (ExcelVO book : bookList) {
                System.out.println(book);
            }
        }
    }

    private static void excelInput() {
        try{
            HSSFSheet sheet=null;
            if (workbook.getSheet("Book SHEET")!=null){
                sheet=workbook.getSheet("Book SHEET");
            }else{
                sheet=workbook.createSheet("Book SHEET");
            }

            HSSFRow rowA=sheet.createRow(0);
            HSSFCell cellA=rowA.createCell(0);
            cellA.setCellValue(new HSSFRichTextString("책제목"));
            HSSFCell cellB=rowA.createCell(1);
            cellB.setCellValue(new HSSFRichTextString("저자"));
            HSSFCell cellC=rowA.createCell(2);
            cellC.setCellValue(new HSSFRichTextString("출판사"));
            HSSFCell cellD=rowA.createCell(3);
            cellD.setCellValue(new HSSFRichTextString("isbn"));
            HSSFCell cellE=rowA.createCell(4);
            cellE.setCellValue(new HSSFRichTextString("이미지주소"));

            int i=1;
            for(ExcelVO vo:bookList){
                HSSFRow row=sheet.createRow(i++);
                row.createCell(0).setCellType(CellType.STRING);
                row.createCell(0).setCellValue(vo.getTitle());
                row.createCell(1).setCellType(CellType.STRING);
                row.createCell(1).setCellValue(vo.getAuthor());
                row.createCell(2).setCellType(CellType.STRING);
                row.createCell(2).setCellValue(vo.getCompany());
                row.createCell(3).setCellType(CellType.STRING);
                row.createCell(3).setCellValue(vo.getIsbn());
                row.createCell(4).setCellType(CellType.STRING);
                row.createCell(4).setCellValue(vo.getImgurl());
            }

            FileOutputStream fos = new FileOutputStream("BookList.xls");
            workbook.write(fos);
            fos.close();
            System.out.println("엑셀로 저장 성공!");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
