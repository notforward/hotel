import com.epam.project.hotel.dao.entities.mysql.UserDAO;
import com.epam.project.hotel.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

public class RequestDAOTest {
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

}