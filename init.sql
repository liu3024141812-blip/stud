-- -------------------- stud_class --------------------
CREATE TABLE IF NOT EXISTS stud_class (
    id        INT          NOT NULL AUTO_INCREMENT,
    myclass   VARCHAR(50)  NOT NULL,
    grade     INT          NOT NULL,
    mymajor   VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    -- 防止同一年级、同一专业下重复创建相同班级。
    UNIQUE KEY uk_class_grade_major_name (grade, mymajor, myclass)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------- student --------------------
CREATE TABLE IF NOT EXISTS student (
    id          INT         NOT NULL AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL,
    student_no  VARCHAR(20) NOT NULL,
    class_id    INT         NOT NULL,
    credits     INT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    -- 保证每个学号唯一。
    UNIQUE KEY uk_student_no (student_no),
    KEY idx_student_class_id (class_id),
    -- 保证学生关联到已存在的班级；班级删除后自动清空学生班级。
    CONSTRAINT fk_student_class FOREIGN KEY (class_id) REFERENCES stud_class(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------- course --------------------
CREATE TABLE IF NOT EXISTS course (
    id       INT          NOT NULL AUTO_INCREMENT,
    name     VARCHAR(100) NOT NULL,
    teacher  VARCHAR(50)  NOT NULL,
    image    VARCHAR(500) NULL,
    credits  INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    -- 防止同一教师重复创建同名课程。
    UNIQUE KEY uk_course_name_teacher (name, teacher)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------- score --------------------
CREATE TABLE IF NOT EXISTS score (
    id          INT           NOT NULL AUTO_INCREMENT,
    student_id  INT           NOT NULL,
    course_id   INT           NOT NULL,
    score       DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (id),
    -- 保证同一学生同一课程只有一条成绩记录。
    UNIQUE KEY uk_score_student_course (student_id, course_id),
    KEY idx_score_student_id (student_id),
    KEY idx_score_course_id (course_id),
    -- 限制考试成绩必须在 0 到 100 之间。
    CONSTRAINT chk_score_range CHECK (score >= 0 AND score <= 100),
    -- 保证成绩记录关联到已存在的学生和课程。
    CONSTRAINT fk_score_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_score_course FOREIGN KEY (course_id) REFERENCES course(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -------------------- seed data --------------------
INSERT INTO stud_class (myclass, grade, mymajor) VALUES
('1班', 2024, '计算机科学与技术'),
('2班', 2024, '软件工程')
ON DUPLICATE KEY UPDATE
    myclass = VALUES(myclass),
    grade = VALUES(grade),
    mymajor = VALUES(mymajor);

INSERT INTO student (name, student_no, class_id, credits) VALUES
('张三', '2024010101', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '计算机科学与技术' AND myclass = '1班'), 0),
('李四', '2024010102', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '计算机科学与技术' AND myclass = '1班'), 0),
('王五', '2024010103', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '计算机科学与技术' AND myclass = '1班'), 0),
('赵六', '2024010104', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '计算机科学与技术' AND myclass = '1班'), 0),
('钱七', '2024010105', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '计算机科学与技术' AND myclass = '1班'), 0),
('孙八', '2024010201', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '软件工程' AND myclass = '2班'), 0),
('周九', '2024010202', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '软件工程' AND myclass = '2班'), 0),
('吴十', '2024010203', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '软件工程' AND myclass = '2班'), 0),
('郑一', '2024010204', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '软件工程' AND myclass = '2班'), 0),
('冯二', '2024010205', (SELECT id FROM stud_class WHERE grade = 2024 AND mymajor = '软件工程' AND myclass = '2班'), 0)
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    class_id = VALUES(class_id),
    credits = VALUES(credits);

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
('离散数学', '郑强', 'https://images.unsplash.com/photo-1509228627152-72ae9ae6848d?w=400', 4)
ON DUPLICATE KEY UPDATE
    image = VALUES(image),
    credits = VALUES(credits);

INSERT INTO score (student_id, course_id, score)
SELECT s.id, c.id, seed.score
FROM (
    SELECT '2024010101' AS student_no, '数据结构与算法' AS course_name, CAST(85.50 AS DECIMAL(5, 2)) AS score
    UNION ALL SELECT '2024010102', '高等数学', 92.00
    UNION ALL SELECT '2024010103', '大学英语', 78.00
    UNION ALL SELECT '2024010104', '计算机网络', 88.50
    UNION ALL SELECT '2024010105', '操作系统', 95.00
    UNION ALL SELECT '2024010201', '数据库原理', 81.00
    UNION ALL SELECT '2024010202', '软件工程', 76.50
    UNION ALL SELECT '2024010203', '人工智能导论', 90.00
    UNION ALL SELECT '2024010204', '线性代数', 84.00
    UNION ALL SELECT '2024010205', '离散数学', 89.50
) seed
JOIN student s ON s.student_no = seed.student_no
JOIN course c ON c.name = seed.course_name
ON DUPLICATE KEY UPDATE
    score = VALUES(score);
