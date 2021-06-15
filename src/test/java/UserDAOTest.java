import com.epam.project.hotel.dao.CheckDAO;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.dao.entities.mysql.UserDAO;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

public class UserDAOTest {
    private Connection con;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    private MockedStatic<DataSource> ds;

    @Before
    public void setUp() throws SQLException {
        con = Mockito.mock(Connection.class);
        st = Mockito.mock(Statement.class);
        ps = Mockito.mock(PreparedStatement.class);
        rs = Mockito.mock(ResultSet.class);

        ds = Mockito.mockStatic(DataSource.class);
        ds.when(DataSource::getConnection).thenReturn(con);
    }

    @After
    public void tearDown() {
        ds.close();
    }
    @Test
    public void hashPassTest() throws AppException {
        Factory factory = MySQLFactory.getInstance();
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        String hash = userDAO.hashPass("12345");
        Assert.assertEquals("173447602773428053556316684567667297915", hash);
    }
    @Test
    public void createUserTest() throws SQLException, AppException {
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(UserDAO.INSERT_USER)).thenReturn(ps);
        Mockito.when(con.prepareStatement(UserDAO.FIND_USER_BY_LOGIN)).thenReturn(ps);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getInt("user_id")).thenReturn(1);
        Factory factory = MySQLFactory.getInstance();
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        User user = userDAO.createUser("artem", "1", "artem@mail.ru");
        Assert.assertEquals(user.getId(), 1);
    }
    @Test
    public void findUserIDTest() throws SQLException, AppException {
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(UserDAO.FIND_USER_BY_ID)).thenReturn(ps);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getInt("user_id")).thenReturn(1);
        Factory factory = MySQLFactory.getInstance();
        UserDAO userDAO = (UserDAO) factory.getDAO("UserDAO");
        User user = userDAO.findUserID(con, 1);
        Assert.assertEquals(user.getId(), 1);
    }
}