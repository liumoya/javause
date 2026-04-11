package com.example.attendancesystem.entity;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
public class User {

        private Integer id;
        private String username;
        private String password;
        private String realName;      // 真实姓名
        private String role;           // 角色：admin/teacher/student
        private String studentNo;      // 学号（学生）
        private String teacherNo;      // 工号（教师）
        private String className;      // 班级名称
        private String phone;          // 电话
        private String email;          // 邮箱
        private Integer status;        // 状态：1-正常，0-禁用
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        // 无参构造方法
        public User() {}

        // 有参构造方法（常用字段）
        public User(String username, String password, String realName, String role,
                    String studentNo, String teacherNo, String className,
                    String phone, String email, Integer status) {
            this.username = username;
            this.password = password;
            this.realName = realName;
            this.role = role;
            this.studentNo = studentNo;
            this.teacherNo = teacherNo;
            this.className = className;
            this.phone = phone;
            this.email = email;
            this.status = status;
        }

        // Getter 和 Setter 方法
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStudentNo() {
            return studentNo;
        }

        public void setStudentNo(String studentNo) {
            this.studentNo = studentNo;
        }

        public String getTeacherNo() {
            return teacherNo;
        }

        public void setTeacherNo(String teacherNo) {
            this.teacherNo = teacherNo;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public LocalDateTime getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", realName='" + realName + '\'' +
                    ", role='" + role + '\'' +
                    ", studentNo='" + studentNo + '\'' +
                    ", teacherNo='" + teacherNo + '\'' +
                    ", className='" + className + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", status=" + status +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }



}
