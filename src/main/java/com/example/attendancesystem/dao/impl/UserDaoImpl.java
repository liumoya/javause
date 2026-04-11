package com.example.attendancesystem.dao.impl;
import com.example.attendancesystem.dao.UserDao;
import com.example.attendancesystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 自定义 RowMapper 处理字段映射（因为 SQL Server 字段名和 Java 属性名不完全一致）
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRealName(rs.getString("real_name"));
            user.setRole(rs.getString("role"));
            user.setStudentNo(rs.getString("student_no"));
            user.setTeacherNo(rs.getString("teacher_no"));
            user.setClassName(rs.getString("class_name"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setStatus(rs.getInt("status"));

            // 处理 DateTime2 类型
            if (rs.getTimestamp("create_time") != null) {
                user.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            }
            if (rs.getTimestamp("update_time") != null) {
                user.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            }
            return user;
        }
    };

    // ========== 增删改实现 ==========

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO [user] (username, password, real_name, role, " +
                "student_no, teacher_no, class_name, phone, email, status, " +
                "create_time, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getRealName(),
                user.getRole(),
                user.getStudentNo(),
                user.getTeacherNo(),
                user.getClassName(),
                user.getPhone(),
                user.getEmail(),
                user.getStatus() != null ? user.getStatus() : 1  // 默认状态为1
        );
    }

    @Override
    public int deleteUser(Integer id) {
        // 软删除：将状态改为0
        String sql = "UPDATE [user] SET status = 0, update_time = GETDATE() WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int deleteUserPhysical(Integer id) {
        // 物理删除：彻底从数据库删除
        String sql = "DELETE FROM [user] WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateUser(User user) {
        String sql = "UPDATE [user] SET " +
                "username = ?, " +
                "password = ?, " +
                "real_name = ?, " +
                "role = ?, " +
                "student_no = ?, " +
                "teacher_no = ?, " +
                "class_name = ?, " +
                "phone = ?, " +
                "email = ?, " +
                "status = ?, " +
                "update_time = GETDATE() " +
                "WHERE id = ?";

        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getRealName(),
                user.getRole(),
                user.getStudentNo(),
                user.getTeacherNo(),
                user.getClassName(),
                user.getPhone(),
                user.getEmail(),
                user.getStatus(),
                user.getId()
        );
    }

    // ========== 查询实现 ==========

    @Override
    public User getUserById(Integer id) {
        String sql = "SELECT * FROM [user] WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, id);
        } catch (Exception e) {
            System.out.println("根据ID查询用户失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM [user] WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, username);
        } catch (Exception e) {
            System.out.println("根据用户名查询用户失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByStudentNo(String studentNo) {
        String sql = "SELECT * FROM [user] WHERE student_no = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, studentNo);
        } catch (Exception e) {
            System.out.println("根据学号查询用户失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByTeacherNo(String teacherNo) {
        String sql = "SELECT * FROM [user] WHERE teacher_no = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, teacherNo);
        } catch (Exception e) {
            System.out.println("根据工号查询用户失败: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getUsersByRole(String role) {
        String sql = "SELECT * FROM [user] WHERE role = ? ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper, role);
    }

    @Override
    public List<User> getUsersByStatus(Integer status) {
        String sql = "SELECT * FROM [user] WHERE status = ? ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper, status);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM [user] ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper);
    }
    @Override
    public List<User> getUsersByPage(int offset, int pageSize) {
        // 👇 使用 ROW_NUMBER() 分页，兼容所有 SQL Server 版本
        String sql = "SELECT * FROM (" +
                "    SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS row_num " +
                "    FROM [user]" +
                ") AS temp " +
                "WHERE row_num > ? AND row_num <= ?";

        // 计算结束行数
        int end = offset + pageSize;

        // 执行查询
        return jdbcTemplate.query(sql, new Object[]{offset, end}, new BeanPropertyRowMapper<>(User.class));
    }


    // ========== 统计实现 ==========

    @Override
    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM [user]";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int countUsersByRole(String role) {
        String sql = "SELECT COUNT(*) FROM [user] WHERE role = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, role);
    }

    // ========== 批量操作实现 ==========

    @Override
    public int[] batchAddUsers(List<User> users) {
        String sql = "INSERT INTO [user] (username, password, real_name, role, " +
                "student_no, teacher_no, class_name, phone, email, status, " +
                "create_time, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRealName());
                ps.setString(4, user.getRole());
                ps.setString(5, user.getStudentNo());
                ps.setString(6, user.getTeacherNo());
                ps.setString(7, user.getClassName());
                ps.setString(8, user.getPhone());
                ps.setString(9, user.getEmail());
                ps.setInt(10, user.getStatus() != null ? user.getStatus() : 1);
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

    @Override
    public int batchUpdateStatus(List<Integer> ids, Integer status) {
        // 使用 IN 语句批量更新
        StringBuilder sql = new StringBuilder("UPDATE [user] SET status = ?, update_time = GETDATE() WHERE id IN (");
        for (int i = 0; i < ids.size(); i++) {
            sql.append("?");
            if (i < ids.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        // 构建参数数组
        Object[] params = new Object[ids.size() + 1];
        params[0] = status;
        for (int i = 0; i < ids.size(); i++) {
            params[i + 1] = ids.get(i);
        }

        return jdbcTemplate.update(sql.toString(), params);
    }
}