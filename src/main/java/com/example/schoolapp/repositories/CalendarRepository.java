package com.example.schoolapp.repositories;

import com.example.schoolapp.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findAllByDeletedAtIsNull();
}
