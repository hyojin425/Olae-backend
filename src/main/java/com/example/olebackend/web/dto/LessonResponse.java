package com.example.olebackend.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class LessonResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getTeacherNameDTO {
        String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getLessonDetailDTO {

        String title; // 수업명
        String description; // 수업 설명
        String lessonType; // 수업 방식
        String supplies; // 준비물
        Integer price; // 가격
        String refund; // 환불 규정
        String place; // 수업 장소

        LocalDateTime lessonStartDate; // 강의 시작일
        LocalDateTime lessonEndDate; // 강의 종료일

        String lectureWeekDay; // 강의 진행 요일
        LocalDateTime lessonStartTime; // 강의 시작 시간
        LocalDateTime lessonEndTime; // 강의 종료 시간

        String approved; // 승인 여부

        LocalDateTime gatherStartDate; // 모집 시작일
        LocalDateTime gatherEndDate; // 모집 마감일

        Integer limitCount; // 신청 제한 인원
        Integer currentCount; // 현재 신청 인원

        List<LessonResponse.getTeacherNameDTO> lessonTeacherList; // 강사
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getLessonByCategoryAndSearchDTO {

        Long lessonId; // 교육 id
        String title; // 수업명

        LocalDateTime gatherStartDate; // 모집 시작일
        LocalDateTime gatherEndDate; // 모집 마감일

        LocalDateTime lessonStartDate; // 강의 시작일
        LocalDateTime lessonEndDate; // 강의 종료일

        String lectureWeekDay; // 강의 진행 요일
        LocalDateTime lessonStartTime; // 강의 시작 시간
        LocalDateTime lessonEndTime; // 강의 종료 시간

        Integer price; // 가격
        String place; // 수업 장소

        Integer limitCount; // 신청 제한 인원
        Integer currentCount; // 현재 신청 인원
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getLessonListByCategoryAndSearchDTO {
        List<LessonResponse.getLessonByCategoryAndSearchDTO> lessonList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getLessonOrderByCriteriaDTO {
        String title;
        Integer currentCount;
        String place;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getLessonListOrderByCriteriaDTO {
        List<LessonResponse.getLessonOrderByCriteriaDTO> lessonList;
    }


}
