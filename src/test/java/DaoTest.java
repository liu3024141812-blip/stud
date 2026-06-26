import com.example.stud.dao.StudClassDao;
import com.example.stud.entity.StudClass;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DaoTest {
    @Test
    public void test01() throws SQLException {
        StudClassDao studClassDao = new StudClassDao();
        studClassDao.delete(6);

    }


    @Test
    public void test02() throws SQLException {
        StudClassDao studClassDao=new StudClassDao();
        StudClass studClass=new StudClass();
        studClass.setId(2);
        studClass.setMajor("物理");
        studClass.setName("1班");
        studClass.setGrade(24);
        studClassDao.update(studClass);
    }
}
