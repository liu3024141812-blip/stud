-- -------------------- stud_class --------------------
CREATE TABLE IF NOT EXISTS stud_class (
    id        INT          NOT NULL AUTO_INCREMENT,
    myclass   VARCHAR(50)  NOT NULL,
    grade     INT          NOT NULL,
    mymajor   VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------- student --------------------
CREATE TABLE IF NOT EXISTS student (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50)  NOT NULL,
    student_no  VARCHAR(20)  NOT NULL UNIQUE,
    class_id    INT          NULL,
    credits     INT          NOT NULL DEFAULT 0
);

-- -------------------- course --------------------
CREATE TABLE IF NOT EXISTS course (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100) NOT NULL,
    teacher  VARCHAR(50)  NOT NULL,
    image    VARCHAR(500) NULL,
    credits  INT          NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------- course 测试数据 --------------------
INSERT INTO course (name, teacher, image, credits) VALUES
('数据结构与算法', '张伟', 'https://images.unsplash.com/photo-1518770660439-4636190af475?w=400', 4),
('高等数学', '李娜', 'https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=400', 5),
('大学英语', '王芳', 'https://images.unsplash.com/photo-1546410531-bb4caa6b424d?w=400', 3),
('计算机网络', '刘洋', 'https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=400', 3),
('操作系统', '陈明', 'https://images.unsplash.com/photo-1629654297299-c8506221ca97?w=400', 4),
('数据库原理', '赵敏', 'https://images.unsplash.com/photo-1544383835-bda2bc66a55d?w=400', 3),
('软件工程', '孙丽', 'https://images.unsplash.com/photo-1504639725590-34d0984388bd?w=400', 3),
('人工智能导论', '周杰', 'https://images.unsplash.com/photo-1677442136019-21780ecad995?w=400', 3),
('线性代数', '吴静', 'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=400', 3),
('离散数学', '郑强', 'https://images.unsplash.com/photo-1509228627152-72ae9ae6848d?w=400', 4);

-- -------------------- score --------------------
CREATE TABLE IF NOT EXISTS score (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    student_id    INT          NOT NULL,
    student_name  VARCHAR(50)  NOT NULL,
    course_id     INT          NOT NULL,
    course_name   VARCHAR(100) NOT NULL,
    score         DECIMAL(5,2) NOT NULL,
    CONSTRAINT chk_score_range CHECK (score >= 0 AND score <= 100),
    CONSTRAINT fk_score_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_score_course  FOREIGN KEY (course_id)  REFERENCES course(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO score (student_id, student_name, course_id, course_name, score) VALUES
(1, '张三', 1, '数据结构与算法', 85.50),
(2, '李四', 2, '高等数学', 92.00),
(3, '王五', 3, '大学英语', 78.00),
(4, '赵六', 4, '计算机网络', 88.50),
(5, '钱七', 5, '操作系统', 95.00),
(6, '孙八', 6, '数据库原理', 81.00),
(7, '周九', 7, '软件工程', 76.50),
(8, '吴十', 8, '人工智能导论', 90.00),
(9, '郑一', 9, '线性代数', 84.00),
(10, '冯二', 10, '离散数学', 89.50);

-- -------------------- 通用 CRUD 示例 --------------------
-- 注：以下为参考语句，实际执行请根据业务需要调整条件与字段

-- ========== stud_class ==========

-- 新增
INSERT INTO stud_class (myclass, grade, mymajor)
VALUES ('示例班级', 2024, '示例专业');

-- 查询
SELECT * FROM stud_class;
SELECT * FROM stud_class WHERE id = ?;
SELECT * FROM stud_class WHERE myclass LIKE '%关键字%';
SELECT * FROM stud_class ORDER BY grade DESC, id ASC;

-- 修改
UPDATE stud_class
SET myclass = ?, grade = ?, mymajor = ?
WHERE id = ?;

-- 删除
DELETE FROM stud_class WHERE id = ?;

-- ========== student ==========

-- 新增
INSERT INTO student (name, student_no, class_id, credits)
VALUES ('示例姓名', '2024010199', 1, 0);

-- 查询
SELECT * FROM student;
SELECT * FROM student WHERE id = ?;
SELECT * FROM student WHERE student_no = ?;
SELECT * FROM student WHERE name LIKE '%关键字%';
SELECT * FROM student WHERE class_id = ?;
SELECT s.*, c.myclass, c.mymajor
FROM student s
LEFT JOIN stud_class c ON s.class_id = c.id
ORDER BY s.id ASC;

-- 修改
UPDATE student
SET name = ?, student_no = ?, class_id = ?, credits = ?
WHERE id = ?;

-- 删除
DELETE FROM student WHERE id = ?;
