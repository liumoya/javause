package com.example.attendancesystem.dao;
import com.example.attendancesystem.entity.Student;
import java.util.List;

/**
 * 数据访问层接口
 * 定义数据库操作的方法
 */

public interface StudentDao {
    /**
      添加学生
      @param student 学生对象
      @return 添加成功返回true，失败返回false
     */
    boolean insert(Student student);

    /**
     * 根据ID删除学生
     * @param id 学生ID
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteById(String id);

    /**
     * 根据ID查询学生
     * @param id 学生ID
     * @return 学生对象，如果不存在返回null
     */
    Student findById(String id);

    /**
     * 根据学号查询学生
     * @param studentNo 学号
     * @return 学生对象，如果不存在返回null
     */
    Student findByStudentNo(String studentNo);

    /**
     * 查询所有学生
     * @return 所有学生列表
     */
    List<Student> findAll();

    /**
     * 更新学生信息
     * @param student 学生对象
     * @return 更新成功返回true，失败返回false
     */
    boolean update(Student student);

    /**
     * 检查学号是否存在
     * @param studentNo 学号
     * @return 存在返回true，不存在返回false
     */
    boolean existsByStudentNo(String studentNo);
}
