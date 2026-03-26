 学生考勤管理系统

 项目简介
这是一个基于 Spring Boot 的学生考勤管理系统，用于管理学生信息。项目采用标准的三层架构设计，不使用数据库，使用内存模拟数据存储。

## 技术栈

- **Java 17**：编程语言
- **Spring Boot 2.7.x**：框架
- **Maven**：项目管理工具
- **Lombok**：简化代码（可选）
- **Postman**：接口测试工具

## 项目结构
## 三层架构说明
### 1. Controller 层（控制层）
- **职责**：接收HTTP请求、参数校验、调用Service层、返回响应
- **注解**：`@RestController`、`@RequestMapping`
- **位置**：`controller/StudentController.java`

### 2. Service 层（业务层）
- **职责**：处理业务逻辑、业务规则校验、调用Dao层
- **注解**：`@Service`
- **位置**：`service/StudentService.java` 和 `service/impl/StudentServiceImpl.java`

### 3. Dao 层（数据访问层）
- **职责**：数据的增删改查（CRUD）
- **注解**：`@Repository`
- **位置**：`dao/StudentDao.java` 和 `dao/impl/StudentDaoImpl.java`

## API 接口文档

### 1. 添加学生

```json
{
    "studentNo": "2024001",
    "name": "刘默雅",
    "classname": "计算机科学与技术3班",
    "age": 20,
    "gender": "女",
    "phone": "18206743156"
}
GET /student/list
GET /student/{id}
GET /student/1