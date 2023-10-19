package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.MainMapper;
import com.dottree.nonogrammers.domain.DotDTO;
import com.dottree.nonogrammers.domain.NonoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class MainController {
    final MainMapper mdao;
    public MainController(MainMapper mdao) {
        this.mdao = mdao;
    }

    @RequestMapping("/nono")
    public ModelAndView showNono(int userId, int nonoId){
        ModelAndView mav = new ModelAndView();
        if(mdao.selectUserFromUserNono(userId,nonoId) == 0){
            mdao.insertUserNono(userId,nonoId);
        }
        return mav;
    }
    @GetMapping("/dot")
    public String dotView() throws IOException {
        StringBuilder sb = new StringBuilder();
        int nonoId = 1;
        DotDTO dDTO = new DotDTO();

        // Load the Excel file into Workbook to be converted to array
        FileInputStream fis = new FileInputStream("/Users/COM/Desktop/Looney Tunes.xlsx");
        Workbook excelWorkbookToArray = new XSSFWorkbook(fis);
        // Get the reference to the first sheet of the workbook for conversion to array
        Sheet worksheet = excelWorkbookToArray.getSheetAt(0);
        int rowCnt = worksheet.getLastRowNum() + 1; // getLastRowNum returns 0-based index, so we add 1.
        int colCnt = worksheet.getRow(0).getLastCellNum(); // Assuming all rows have same number of columns.
        System.out.println("rowCnt: " + rowCnt);
        System.out.println("colCnt: " + colCnt);
        for (int i = 0; i < rowCnt; i++) {
            Row row = worksheet.getRow(i);
            for (int j = 0; j < colCnt; j++) {
                Cell cell = row.getCell(j); //i행의 j열 셀
                Color color = cell.getCellStyle().getFillForegroundColorColor(); // 셀의
                if (color instanceof XSSFColor) {
                    XSSFColor xssfColor = (XSSFColor) color;
                    String d = xssfColor.getARGBHex().substring(2);
//                    dDTO.setNonoId(nonoId);
//                    dDTO.setColor(d);
//                    mdao.insertDotsInDot(dDTO);
                    System.out.print(d + ",");
                }
            }
            System.out.println();
        }
        System.out.println("No. Of Rows Exported in array: " + rowCnt);
        fis.close();
        return null;
    }

//
//    @RequestMapping("/printdots")
//    @ResponseBody
//    public String


    @RequestMapping("/nonodots")
    public ModelAndView nonodots(@RequestParam("nonoId")int nonoId){
            log.info("nonoId : " + nonoId);
            int cnt = 0;
            String aaad = "212352";
            List<List<DotDTO>> totalRowList = new ArrayList<>(); // 모든 도트의 정보를 담을 이중ArrayList. 행,열로 나뉘어 있음.
            ModelAndView mav = new ModelAndView();
            mav.setViewName("nonodots");
            List<DotDTO> nList = mdao.selectAllDot(nonoId); //
//            NonoDTO allUrls = mdao.selectAllRowUrl(nonoId);
//            String [] urlAry = allUrls.getRowUrl().split(",");


            int row = nList.size()/32;
            log.info("row : "+row);

            List<DotDTO> singleRowList = null;
            for(int i=0; i<row; i++){
                singleRowList = new ArrayList<>();
                for(int j=0; j<32; j++){
                    singleRowList.add(nList.get(cnt));
//                    log.info(i + "행 " + j + "열");
                    //System.out.print(nList.get(cnt).getColor()+",");
                    cnt++;
                }
                //System.out.println();
                totalRowList.add(singleRowList);
            }
            mav.addObject("dotList", totalRowList);
//            mav.addObject("urlList", urlList);
            mav.addObject("testcolor", aaad);
            mav.addObject("testcode", "와웅우우아아");

        System.out.println(totalRowList.get(1).size());
            for(int i=0; i<48; i++){

                for( int j = 0; j < totalRowList.get(0).size(); j++) {
                    System.out.print(totalRowList.get(i).get(j).getColor()+" ,");
                }
            }
        return mav;
    }





    // 문제링크 가져오기.
    @RequestMapping("/geturls")
    @ResponseBody
    public void geturls(@RequestParam("nonoId")int nonoId){
        NonoDTO allUrls = mdao.selectAllRowUrl(nonoId);
        String [] urlAry = allUrls.getRowUrl().split(",");

        for (int i=0; i< urlAry.length; i++){
            System.out.println(urlAry[i]);
        }
    }
}
