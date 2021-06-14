import com.epam.project.hotel.dao.CheckDAO;
import com.epam.project.hotel.sql.AppException;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

public class CheckDAOTest{
    private Connection con;

    private Statement stmt;
    private PreparedStatement ps;

    private ResultSet rs;

    private MockedStatic<CheckDAO> checkDAO;

    /* @Before
    public void setUp() throws DBException {
        con = Mockito.mock(Connection.class);
        stmt = Mockito.mock(Statement.class);
        pstmt = Mockito.mock(PreparedStatement.class);
        rs = Mockito.mock(ResultSet.class);

        checkDAO = Mockito.mockStatic(CheckDAO.class);
        checkDAO.when(DataSource::getConnection).thenReturn(con);
    }
*/
    @Test
    public void findCheckByIdTest() throws AppException, SQLException {
        con = Mockito.mock(Connection.class);
        rs = Mockito.mock(ResultSet.class);
        ps = Mockito.mock(PreparedStatement.class);
        Mockito.when(rs.next()).thenReturn(true);
        Mockito.when(rs.getString("payment_check.check_id"));
    }
}
