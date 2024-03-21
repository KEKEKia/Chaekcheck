package com.cc.business.domain.repository;

import com.cc.business.domain.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByMemberId(int memberId);

    @Query("SELECT b FROM book b WHERE b.memberId = :memberId AND b.title LIKE %:keyword%")
    List<BookEntity> searchAllByMemberId(@Param("memberId") int memberId, @Param("keyword") String keyword);
}