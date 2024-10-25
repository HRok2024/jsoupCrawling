package org.example.model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.util.*;

public class Project6 {
    public static void main(String[] args) {
        String fileName="zusic.xls";
        Map<String,String> list=new HashMap<String,String>(); //주식이름, 코드번호
        try(FileInputStream fis=new FileInputStream(fileName);
            HSSFWorkbook workbook=new HSSFWorkbook(fis);) {
            HSSFSheet sheet = workbook.getSheetAt(0); //첫번째 시트
            
            Iterator<Row> rows = sheet.iterator(); //줄(row) 이터레이터 만들기
            rows.next(); // 첫줄의 제목은 넘긴다

            String[] temp=new String[5];
            while(rows.hasNext()) {
                HSSFRow row=(HSSFRow) rows.next();
                Iterator<Cell> cells = row.cellIterator(); //한줄안에 여러개의 셀들
                int i=0;
                while(cells.hasNext()) {
                    HSSFCell cell=(HSSFCell) cells.next();
                    temp[i] = cell.toString();
                    i++;
                    if(i==4) break;
                }
                list.put(temp[3],temp[1]);
            }
            showExcelData(list);
            showZusicCode(list);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showZusicCode(Map<String,String> list) {
        //이름을 넣으면 종목코드를 출력해준다
        Scanner scanner=new Scanner(System.in);
        while(true) {
        System.out.println("주식명을 입력: ");
        String name=scanner.nextLine();
        System.out.println(list.get(name));}
    }

    public static void showExcelData(Map<String,String> list) {
        for(Map.Entry<String,String> entry:list.entrySet()) {
            System.out.println(entry.getKey()+"\t"+entry.getValue());
        }
    }
}
