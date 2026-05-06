package com.example.attendancesystem.repository;
//import com.example.attendancesystem.entity.Student;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

//import java.util.List;
//import java.util.Optional;

//@Repository
//public interface StudentRepository extends JpaRepository<Student, String>,
    //    JpaSpecificationExecutor<Student> {//继承
    // ========== 1. 分页查询方法 ==========

    /**
     * 分页查询所有学生
     */
 //   Page<Student> findAll(Pageable pageable);

    /**
     * 根据班级分页查询
     */
//    Page<Student> findByClassname(String classname, Pageable pageable);

    /**
     * 根据状态分页查询
     */
  //  Page<Student> findByStatus(Integer status, Pageable pageable);

    /**
     * 根据性别分页查询
     */
  //  Page<Student> findByGender(String gender, Pageable pageable);

    /**
     * 根据班级和状态分页查询
     */
 //   Page<Student> findByClassnameAndStatus(String classname, Integer status, Pageable pageable);

    /**
     * 根据班级和性别分页查询
     */
 //   Page<Student> findByClassnameAndGender(String classname, String gender, Pageable pageable);

    /**
     * 根据姓名模糊分页查询
     */
  //  Page<Student> findByNameContaining(String name, Pageable pageable);

    // ========== 2. 基础查询方法 ==========

  //  Optional<Student> findByStudentNo(String studentNo)
    //  List<Student> findByName(String name);
    //List<Student> findByClassname(String classname);
    //List<Student> findByGender(String gender);
    //List<Student> findByStatus(Integer status);

    // ========== 3. 统计方法 ==========

   // long countByClassname(String classname);
   // long countByGender(String gender);
    //long countByStatus(Integer status);

    // ========== 4. JPQL 查询 ==========

   // @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.studentNo LIKE %:keyword% OR s.classname LIKE %:keyword%")
   // List<Student> searchStudents(@Param("keyword") String keyword);

   // @Query("SELECT s.classname, COUNT(s) FROM Student s GROUP BY s.classname")
   // List<Object[]> countStudentsByClass();
//}
