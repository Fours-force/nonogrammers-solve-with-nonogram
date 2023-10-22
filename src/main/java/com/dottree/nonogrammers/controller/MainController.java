package com.dottree.nonogrammers.controller;

import com.dottree.nonogrammers.dao.MainMapper;
import com.dottree.nonogrammers.domain.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.binding.BindingException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Controller
@Slf4j
public class MainController {
    final MainMapper mdao;
    public MainController(MainMapper mdao) {
        this.mdao = mdao;
    }

    //사용자 아이디, 노노아이디로 노노 개방
  /*  @RequestMapping("/api/nono/{userId}/{nonoId}")
    public ModelAndView showNono(UserNonoDTO unDTO){
        log.info("showNono start !!!!!!!");
        ModelAndView mav = new ModelAndView();
        if(mdao.selectUserFromUserNono(unDTO) == 0){
            mdao.insertUserNono(unDTO,1);
            mdao.insertUserSolvedCount(unDTO);
        }
        return mav;
    }*/

    //엑셀파일 픽셀마다 데이터 뽑기
    @GetMapping("/api/dot")
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

    //화면에 노노 출력.
    @RequestMapping("/nonodots/{userId}/{nonoId}")
    public ModelAndView nonodots(UserNonoDTO unDTO){
        log.info("nonodots start!!!!!!!!");
        //노노개방
        if(mdao.selectUserFromUserNono(unDTO) == 0){
            log.info("insert UserNono start!!!!!!!!!");
            mdao.insertUserNono(unDTO,1);
            mdao.insertUserSolvedCount(unDTO);
        }

        log.info("nonoId : " + unDTO.getNonoId());
        int cnt = 0;
        String aaad = "212352";
        List<List<DotDTO>> totalRowList = new ArrayList<>(); // 모든 도트의 정보를 담을 이중ArrayList. 행,열로 나뉘어 있음.

        ModelAndView mav = new ModelAndView(); // 모델 생성,nonodot으로 이동
        mav.setViewName("nonodots");

        List<DotDTO> nList = mdao.selectAllDot(unDTO.getNonoId()); // 모든 도트 dot테이블에서 가져옴

        NonoDTO allUrls = mdao.selectAllallProblemToStr(unDTO.getNonoId()); // 모든 문제Url nono테이블에서 가져옴
        String [] urlAry = allUrls.getAllProblemToStr().split(","); // 쉼표 떼고 배열에 저장

        int row = nList.size()/32; // totalRowList에 모든 도트 정보 담음
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

        //푼 문제가 0이라 생긴 문제. 지우지 말 것
       /* UserDotDTO udDTO = new UserDotDTO();
        udDTO.setNonoId(nonoId);
        udDTO.setUserId(userId);
        int userSolvedCnt = mdao.selectSolvedNumber(udDTO);
        mav.addObject("progress",cnt/userSolvedCnt);*/

        mav.addObject("dotList", totalRowList);
        mav.addObject("urlAry", urlAry);
        mav.addObject("testcolor", aaad);
        mav.addObject("testcode", "와웅우우아아");

//        System.out.println(totalRowList.get(1).size());
//            for(int i=0; i<48; i++){
//                for( int j = 0; j < totalRowList.get(0).size(); j++) {
//                    System.out.print(totalRowList.get(i).get(j).getColor()+" ,");
//                }
//            }
        return mav;
    }

    // 문제번호 가져오기.
    @RequestMapping("/api/geturls/{nonoId}")
    @ResponseBody
    public void geturls(@PathVariable("nonoId")int nonoId){
        NonoDTO allUrls = mdao.selectAllallProblemToStr(nonoId);
        String [] urlAry = allUrls.getAllProblemToStr().split(",");

        for (int i=0; i< urlAry.length; i++){
            System.out.println(urlAry[i]);
        }
    }

    //사용자 검색해서 푼 문제수 출력
    @RequestMapping("/api/solvednum")
    @ResponseBody
    public void solvednum() throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String[] responseBody = new String[1];
        client.prepare("GET", "https://solved.ac/api/v3/user/show?handle=tjdtndlwkd")
                .setHeader("Accept", "application/json")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    responseBody[0] = response.getResponseBody();
                    //System.out.println(responseBody[0]);
                })
                .join();
        client.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody[0]);

        System.out.println(jsonNode.get("solvedCount"));  // 출력: John
        System.out.println(jsonNode.get("handle"));

    }

    //문제 레벨 별 문제 번호들 저장 성공
    @RequestMapping("/api/missionlevel/{level}")
    @ResponseBody
    public void solvednum(@PathVariable("level")int level) throws IOException {
        //레벨 별 문제수 가져오기, 리스트에 저장
        int cntPerLevel = getNumPerLevel(level);
        List<String> totalProblemList = new ArrayList<>();
        System.out.println(cntPerLevel);
        for (int i=1; i<cntPerLevel/50; i++){
            totalProblemList.addAll(insertproblems(i, level));

        }
        System.out.println(totalProblemList);
    }

    //사용자 문 문제들 가져오기
//    @RequestMapping("/reloadinfo")
//    @ResponseBody



    //백준 아이디로 전적갱신  / 이전에 푼 문제 수 저장 필요함.
    @RequestMapping("/api/updateCheck/{baekjoonId}/{userId}/{nonoId}")
    @ResponseBody
        public int updateSolved(@PathVariable("baekjoonId")String baekjoonId, @PathVariable("userId")int userId, @PathVariable("nonoId")int nonoId){
        log.info(getClass().getName() + "updateSolved 시작!!!!!!!!!!!!");
        int result = 0;

        UserDotDTO udDTO = new UserDotDTO();
        udDTO.setNonoId(nonoId);
        udDTO.setUserId(userId);

        int userSolvedCnt = mdao.selectUserSolvedCount(udDTO);

        log.info(getClass().getName()+" 해결해온 문제의 수 "+userSolvedCnt);
        log.info(getClass().getName()+" 지금 해결한 문제의 수 "+getUserBaekData(baekjoonId));
        if(userSolvedCnt < getUserBaekData(baekjoonId)){
            result = 1;
            UserSolvedCountDTO uscDTO = new UserSolvedCountDTO();
            uscDTO.setUserId(userId);
            uscDTO.setSolvedCount(userSolvedCnt+1);
            mdao.updateUserSolvedCount(uscDTO);
            mdao.selectUserSolvingRow(udDTO);
        }
        log.info("해결한 문제의 수 " + userSolvedCnt);

        return result;
    }

    //userdot에 해결한 dots들 삽입.
    @RequestMapping(value =("/api/updateUserDot/{userId}/{nonoId}"),method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String updateUserDot(@RequestBody String jsonString, @PathVariable("userId") int userId, @PathVariable("nonoId")int nonoId) throws JsonProcessingException {
        log.info("updateUserDot 시작!!!!!!!");
        UserDotDTO udDTO = new UserDotDTO();

        System.out.println("받아온 JSON 형식의 String : "+jsonString);

        ObjectMapper getNumMapper = new ObjectMapper();
        JsonNode getNumJsonNode = getNumMapper.readTree(jsonString);

        udDTO.setDotId(getNumJsonNode.get(0).get("dotId").asInt());
        udDTO.setUserId(userId);
        udDTO.setNonoId(nonoId);
        log.info(String.valueOf(udDTO.getDotId()));
        try {
            if(mdao.selectIsDotsSolved(udDTO).getDotId() == udDTO.getDotId())
                log.info("중복된 userDotInsert 처리.");//뇌정지..
        }catch (NullPointerException e){
            log.info("에러메세지 : " + e.getMessage());
            for (int i = 0; i < getNumJsonNode.size(); i++) {
                udDTO.setDotId(getNumJsonNode.get(i).get("dotId").asInt());
                udDTO.setUserId(userId);
                udDTO.setNonoId(nonoId);
                mdao.insertUserDot(udDTO);
            }
            mdao.resetUserSolvingRow(udDTO);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:/nonodots/").append(userId).append("/").append(nonoId);
        return sb.toString();
    }
    //사용자가 해결중인 문제의 행 설정
    @RequestMapping("/api/updateSolvingRow/{userId}/{nonoId}/{solvingRow}")
    @ResponseBody
    public void updateSolvingRow(UserSolvingRowDTO usrDTO){
        log.info("updateSolvingRow start !!!");
        log.info("userSolvingDTO solvingRow : " + usrDTO.getSolvingRow());
        UserDotDTO udDTO = new UserDotDTO();
        udDTO.setUserId(usrDTO.getUserId());
        udDTO.setNonoId(udDTO.getNonoId());

        try {
            mdao.selectUserSolvingRow(udDTO);
        }catch (BindingException e){
            log.info(e.getMessage());
            mdao.insertUserSolvingRow(usrDTO);
        }

        mdao.updateUserSolvingRow(usrDTO);

    }
    //사용자가 현재 풀고있는 행 조회. 나중에 /nonodots랑 합쳐야 될 듯.
    @RequestMapping("/api/selectSolvingRow/{userId}/{nonoId}")
    @ResponseBody
    public int selectSolvingRow(UserDotDTO udDTO){
        log.info(getClass().getName()+"selectSolvingRow start!!");
        int result = 0;

        try {
            String rowNullCheck = String.valueOf(mdao.selectUserSolvingRow(udDTO));
            if(!(rowNullCheck.isEmpty())){
                result = mdao.selectUserSolvingRow(udDTO);
            }
        }catch (BindingException e){
            log.info(e.getMessage());
        }

        return result;
    }

    //사용자가 푼 dot들의 List 반환. / 색칠해주려고 사용.
    @RequestMapping(value = ("/api/selectSolvedDotId/{userId}/{nonoId}"), produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<UserDotDTO> selectSolvedDotId(UserDotDTO udDTO){
        List<UserDotDTO> udDTOList = new ArrayList<>();
        udDTOList = mdao.selectSolvedDotId(udDTO);

        return udDTOList;
    }


    ////////////////////////////////////////////내가 선언한 메서드/////////////////////////////////////////////////////////////

    //레벨 의 문제 수
    public int getNumPerLevel(int level) throws IOException {
        AsyncHttpClient getNumPerLevelClient = new DefaultAsyncHttpClient();
        String[] getNumResponseBody = new String[1];
            getNumPerLevelClient.prepare("GET", "https://solved.ac/api/v3/problem/level")
                .setHeader("Accept", "application/json")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    getNumResponseBody[0] = response.getResponseBody();
                    //System.out.println(getNumResponseBody[0]);
                })
                .join();
            getNumPerLevelClient.close();

        ObjectMapper getNumMapper = new ObjectMapper();
        JsonNode getNumJsonNode = getNumMapper.readTree(getNumResponseBody[0]);
        System.out.println(getNumJsonNode.get(level).get("count"));

        return getNumJsonNode.get(level).get("count").asInt();
    }

    //문제 레벨과 페이지 번호로 해당 레벨의 문제 번호,이름 뽑기
    public List<String> insertproblems(int num,int level) throws IOException {
        AsyncHttpClient getNumPerLevelClient = new DefaultAsyncHttpClient();
        String[] getNumResponseBody = new String[1];
        List<String> problemList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String url = "https://solved.ac/api/v3/search/problem?query=tier%3A"+level+"&page="+num;
        getNumPerLevelClient.prepare("GET", url)
                .setHeader("Accept", "application/json")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    getNumResponseBody[0] = response.getResponseBody();
                    //System.out.println(getNumResponseBody[0]);
                })
                .join();
        getNumPerLevelClient.close();

        ObjectMapper getNumMapper = new ObjectMapper();
        JsonNode getNumJsonNode = getNumMapper.readTree(getNumResponseBody[0]);

        for (int i=0; i<getNumJsonNode.get("items").size(); i++){
//            System.out.println("문제 이름 : " + getNumJsonNode.get("items").get(i).get("titleKo"));
//            System.out.println("문제 번호 : " + getNumJsonNode.get("items").get(i).get("problemId"));
            problemList.add(getNumJsonNode.get("items").get(i).get("problemId").asText());
            sb.append(getNumJsonNode.get("items").get(i).get("problemId")).append(",");
        }
        System.out.println(sb);

        //System.out.println(problemList);
        return problemList;
    }

    //백준 사용자 푼 문제 수 가져오기.
    public int getUserBaekData(String baekjoonId) {
        log.info(getClass().getName() + "getUserBackData start!!!!!!!");
//        String userId = "";
        //Scanner sc = new Scanner(System.in);
        //System.out.println("백준 아이디를 입력해주세요.");
        //System.out.print("아이디 : ");

        //userId = sc.next();
        //System.out.println("3초정도 소요됩니다.");

//        userId = "tmdgus5611";
        StringBuilder url = new StringBuilder();

        url.append("https://www.acmicpc.net/user/");
        url.append(baekjoonId);

        //Document에는 페이지의 전체 소스가 저장된다
        Document doc = null;

        try {
            doc = Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
        Element element = doc.select("div.problem-list").first(); // 맞은 문제
        //Element element = doc.select("div.problem-list").last(); // 시도했지만 맞지 못한 문제

        // System.out.println(element);

        System.out.println("========" + baekjoonId + "님이 맞힌 문제========");

        //Iterator을 사용하여 하나씩 값 가져오기
        Iterator<Element> ie1 = element.select("a").iterator();
        System.out.println("---맞힌 개수 : "+element.select("a").size());
        while (ie1.hasNext()) {
            System.out.println(ie1.next().text());
        }
        return element.select("a").size();
    }
}


