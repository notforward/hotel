import com.epam.project.hotel.dao.CheckDAO;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.AppException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Check;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

public class CheckDAOTest{
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
    public void tearDown(){
        ds.close();
    }
    @Test
    public void findCheckByIdTest() throws AppException, SQLException {
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(CheckDAO.SELECT_CHECK_BY_ID)).thenReturn(ps);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getInt("user_id")).thenReturn(1);
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        Check check = checkDAO.findCheckByID(con, 1);
        Assert.assertEquals(1, check.getUser_id());
        System.out.println(check);
    }
    @Test(expected = NullPointerException.class)
    public void updateCheckStatusTest() throws SQLException, AppException {
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(CheckDAO.UPDATE_CHECK_STATUS)).thenReturn(ps);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("check_status")).thenReturn("NOT PAYED");

        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        Check mockCheck = new Check();
        mockCheck.setCheck_id(1);
        Check check = checkDAO.updateCheckStatus(con, mockCheck, "NOT PAYED");
        Assert.assertEquals("NOT PAYED", check.getCheck_status());
        System.out.println(check);
    }
    @Test
    public void findAllChecksTest() throws SQLException, AppException {
        Mockito.when(con.createStatement()).thenReturn(st);
        Mockito.when(st.executeQuery(CheckDAO.SELECT_ALL_CHECKS)).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        List<Check> checks = checkDAO.findAllChecks(con);
        Assert.assertEquals(1, checks.size());
        System.out.println(checks);
    }
    @Test
    public void checkCreationTest() throws SQLException, AppException {
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(con.prepareStatement(CheckDAO.INSPECT_CHECK)).thenReturn(ps);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getDate("room_in")).thenReturn(Date.valueOf("2021-06-10"));
        Mockito.when(rs.getDate("room_out")).thenReturn(Date.valueOf("2021-06-23"));

        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
        boolean available = checkDAO.checkCreation(con, Date.valueOf("2021-06-01"
        ), Date.valueOf("2021-06-27"), 1);
        Assert.assertFalse(available);
        System.out.println(available);
    }
}
