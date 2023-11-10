package com.dottree.nonogrammers.service;

import com.dottree.nonogrammers.domain.NonoResponseDTO;
import com.dottree.nonogrammers.domain.UserDotDTO;
import com.dottree.nonogrammers.domain.UserNonoDTO;
import com.dottree.nonogrammers.domain.UserSolvingRowDTO;
import com.dottree.nonogrammers.entity.*;
import com.dottree.nonogrammers.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class MainService {

    private final DotRepository dRepository;
    private final NonoRepository nRepository;
    private final UserDotRepository udRepository;
    private final UserNonoRepository unRepository;
    private final UserSolvedCountRepository uscRepository;
    private final UserSolvingRowRepository usrRepository;
    private final UserRepository uRepository;

    public MainService(DotRepository dRepository,
                       NonoRepository nRepository,
                       UserDotRepository udRepository,
                       UserNonoRepository unRepository,
                       UserSolvedCountRepository uscRepository,
                       UserSolvingRowRepository usrRepository,
                       UserRepository uRepository){
        this.dRepository = dRepository;
        this.nRepository = nRepository;
        this.udRepository = udRepository;
        this.unRepository = unRepository;
        this.uscRepository = uscRepository;
        this.usrRepository = usrRepository;
        this.uRepository = uRepository;
    }

    /**
     *
     * @param unDTO
     * @return
     */
    public int selectUserFromUserNono(UserNonoDTO unDTO){
        log.info("selectUserFromUserNono start !!!!!");
        Integer num = unRepository.countByUser_UserIdAndNono_NonoId(unDTO.getUserId(),unDTO.getNonoId());
        return num;
    }
    /**
     * user_solved_count에서 solvedCount조회
     * @param udDTO
     * @return
     */
    public Integer selectUserSolvedCount(UserDotDTO udDTO){
        log.info("selectUserSolvedCount start !!!!!");
        UserSolvedCount userSolvedCount = uscRepository.findById(udDTO.getUserId()).get();
        return userSolvedCount.getSolvedCount();
    }

    /**
     * dot에서 모든 도트정보 조회
     * @param nonoId
     * @return
     */
    public List<Dot> selectAllDot(int nonoId){
        log.info("selectAllDot start !!!!!");
        return dRepository.findAllByNono_NonoId(nonoId);
    }

    /**
     * nono에서 행마다 Url 뒤에 넣어줄 문제의 번호들 조회
     *
     * @param nonoId
     * @return
     */
    public String selectallProblemToStr(int nonoId){
        log.info("selectallProblemToStr start !!!!!");
        Nono nono = nRepository.findById(nonoId).get();
        return nono.getAllProblemToStr();
    }

    public Long selectAllDotCount(int nonoId){
        log.info("selectAllDotCount start !!!!!");
        return dRepository.countByNono_NonoId(nonoId);
    }

    public Long selectSolvedNumber(UserDotDTO udDTO){
        log.info("selectSolvedNumber start !!!!!");
        return udRepository.countUserDotByUser_UserIdAndNono_NonoId(udDTO.getUserId(),udDTO.getNonoId());
    }

    public List<Long> selectSolvedDotId(UserDotDTO udDTO){
        log.info("selectSolvedDotId start !!!!!");
        return udRepository.selectSolvedDotId(udDTO.getUserId(),udDTO.getNonoId());
    }

    public Integer selectUserSolvingRow(UserDotDTO udDTO){
        log.info("selectUserSolvingRow start !!!!!");
        return usrRepository.selectUserSolvingRow(udDTO);
    }

    public Integer selectIsDotsSolved(UserDotDTO udDTO){
        return udRepository.selectIsDotsSolved(udDTO);
    }

    public List<NonoResponseDTO> selectAllNoNo(){
        List<Nono> nonoList = nRepository.findAll();
        List<NonoResponseDTO> nrd = new ArrayList<>();
        for(Nono elem : nonoList){
            NonoResponseDTO nonoResponseDTO = new NonoResponseDTO(elem);
            nrd.add(nonoResponseDTO);
        }
        return nrd;
    }

    public List<NonoResponseDTO> selectNonoByLevel(int levelType){
        List<Nono> nonoList = nRepository.findByLevelType(levelType);
        List<NonoResponseDTO> nrd = new ArrayList<>();
        for(Nono elem : nonoList){
            NonoResponseDTO nonoResponseDTO = new NonoResponseDTO(elem);
            nrd.add(nonoResponseDTO);
        }
        return nrd;
    }


    @Transactional
    public void updateUserSolvedCount(int userId, int baekjoonSolvedCnt){
        log.info("updateUserSolvedCount start !!!!!");
        UserSolvedCount userSolvedCount = uscRepository.findById(userId).get();
        log.info(userSolvedCount.toString());
        userSolvedCount.update(baekjoonSolvedCnt);
    }

    @Transactional
    public void resetUserSolvingRow(UserDotDTO udDTO){
        log.info("resetUserSolvingRow start !!!!!");
        UserSolvingRow userSolvingRow = usrRepository.findByUser_UserIdAndNono_NonoId(udDTO.getUserId(), udDTO.getNonoId());
        userSolvingRow.update(0);
    }

    @Transactional
    public void updateUserSolvingRow(UserSolvingRowDTO usrDTO){
        log.info("updateUserSolvingRow start !!!!!");
        UserSolvingRow userSolvingRow = usrRepository.findByUser_UserIdAndNono_NonoId(usrDTO.getUserId(),usrDTO.getNonoId());
        userSolvingRow.update(usrDTO.getSolvingRow());
    }

    @Transactional
    public void updateUserNonoIsSolved(UserNonoDTO unDTO){
        log.info("updateUserNonoIsSolved start !!!!!");
        UserNono userNono = unRepository.findByUser_UserIdAndNono_NonoId(unDTO.getUserId(), unDTO.getNonoId());
        userNono.update(2);
    }
    @Transactional
    public void insertUserSolvedCount(int userId, int baekjoonSolvedCnt){
        log.info("insertUserSolvedCount start !!!!!!");
        User user = uRepository.findById(userId).get();

        UserSolvedCount userSolvedCount = UserSolvedCount.builder().
                userId(userId).
                user(user).
                solvedCount(baekjoonSolvedCnt).
                build();
        uscRepository.save(userSolvedCount);
    }

    @Transactional
    public void insertUserSolvingRow(UserSolvingRowDTO usrDTO){
        log.info("insertUserSolvingRow start !!!!!");
        User user = uRepository.findById(usrDTO.getUserId()).get();
        Nono nono = nRepository.findById(usrDTO.getNonoId()).get();
        UserSolvingRow userSolvingRow = UserSolvingRow.builder()
                .user(user)
                .nono(nono)
                .solvingRow(usrDTO.getSolvingRow())
        .build();
        usrRepository.save(userSolvingRow);
    }

    @Transactional
    public void insertUserNono(UserNonoDTO unDTO){
        log.info("insertUserNono start !!!!!");
        User user = uRepository.findById(unDTO.getUserId()).get();
        Nono nono = nRepository.findById(unDTO.getNonoId()).get();

        UserNono userNono = UserNono.builder()
                .user(user)
                .nono(nono)
                .isSolved(1)
                .build();
        unRepository.save(userNono);
    }

    @Transactional
    public void insertUserDot(UserDotDTO udDTO, String jsonString) throws JsonProcessingException {
        log.info("insertUserDot start !!!!!");
        ObjectMapper getNumMapper = new ObjectMapper();
        JsonNode getNumJsonNode = getNumMapper.readTree(jsonString);
        User user = uRepository.findById(udDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException());
        Nono nono = nRepository.findById(udDTO.getNonoId()).orElseThrow(() -> new IllegalArgumentException());
        log.info(String.valueOf(getNumJsonNode.get(0).get("dotId")));
        for (int i = 0; i < getNumJsonNode.size(); i++) {
            Dot dot = dRepository.findById(getNumJsonNode.get(i).get("dotId").asInt()).orElseThrow(() -> new IllegalArgumentException());
            log.info(String.valueOf(user.toBuilder().build().getUserId()));
            UserDot userDot = UserDot.builder()
                    .userId(user.toBuilder().build().getUserId())
                    .nonoId(nono.toBuilder().build().getNonoId())
                    .dotId(dot.toBuilder().build().getDotId())
                    .build();

            udRepository.save(userDot);
        }
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


    /**
     * 백준 사용자 푼 문제 수 가져오기.
     * @param baekjoonId
     * @return
     */
    public int getUserBaekData(String baekjoonId) {
        log.info(getClass().getName() + "getUserBackData start!!!!!!!");
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
    public String isUserIdNullthenRedirect(HttpSession session) {
        if(session.getAttribute("value") == null) {
            System.out.println("************ userId is NULL ************");
            return "redirect:/login";
        } else {
            return "";
        }
    }



}
