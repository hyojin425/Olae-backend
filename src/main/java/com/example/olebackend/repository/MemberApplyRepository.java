package com.example.olebackend.repository;

import com.example.olebackend.domain.mapping.MemberApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberApplyRepository extends JpaRepository<MemberApply, Long> {
    Boolean existsByLessonIdAndMemberId(Long lessonId, Long memberId);
}
