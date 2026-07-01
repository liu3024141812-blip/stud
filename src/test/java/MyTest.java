import com.example.stud.entity.StudClass;
import javafx.fxml.FXML;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyTest {
    @Test
    public void test01() throws Exception {
        StudClass studClass=new StudClass();
        studClass.setName("2班");
        studClass.setMajor("计科");
        studClass.setId(0);
        studClass.setGrade(24);
        // System.out.println(studClass);
        String sql="insert into stud_class (myclass,grade,mymajor) values (?,?,?)";

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username="root";
        String password="302414";
        String url="jdbc:mysql://localhost:3306/stud";
        Connection connection= DriverManager.getConnection(url,username,password);

        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1, studClass.getName());
        statement.setInt(2, studClass.getGrade());
        statement.setString(3, studClass.getMajor());

        int i=statement.executeUpdate();
        System.out.println(i);

    }



}
