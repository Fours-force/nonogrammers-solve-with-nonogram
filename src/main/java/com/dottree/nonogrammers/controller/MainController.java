package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.MainMapper;
import com.dottree.nonogrammers.domain.DotDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;

@Controller
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
        StringBuffer sb = new StringBuffer();
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
                Cell cell = row.getCell(j);
                Color color = cell.getCellStyle().getFillForegroundColorColor();
                if (color instanceof XSSFColor) {
                    XSSFColor xssfColor = (XSSFColor) color;
                    String d = xssfColor.getARGBHex().substring(2);
                    dDTO.setNonoId(nonoId);
                    dDTO.setColor(d);

                    System.out.print(d + ",");
                }
            }
            System.out.println();
        }
        System.out.println("No. Of Rows Exported in array: " + rowCnt);
        fis.close();
        return null;
    }
}
