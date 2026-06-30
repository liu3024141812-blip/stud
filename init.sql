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
