package com.example.employeeapp.repository;

import com.example.employeeapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser_Id(Long userId);
    @Transactional
    @Modifying
@Query("UPDATE Task t SET t.status = 'COMPLETED' WHERE t.id = :taskId AND t.user.id = :userId")
int updatedTask(@Param("taskId") Long taskId, @Param("userId") Long userId);

}
