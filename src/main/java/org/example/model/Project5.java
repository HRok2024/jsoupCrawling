package org.example.model;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Project5 {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName="bookList.xls"; //프로젝트 폴더에 있는 엑셀파일
        List<ExcelVO> data=new ArrayList<ExcelVO>(); //엑셀시트 내용을 1줄씩 읽어서 저장
        //파일입력스트림으로 가상의 엑셀파일(workbook)을 만들기
        // try는 파일을 다 쓰고 닫아줄 필요가 없어서 만들었다???
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
                }
                ExcelVO vo=new ExcelVO(temp[0],temp[1],temp[2],temp[3],temp[4]);
                data.add(vo);
            }
            showExcelData(data);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

     public static void showExcelData(List<ExcelVO> data) {
        for(ExcelVO vo:data) {
            System.out.println(vo);
        }
    }
}
