import com.epam.project.hotel.dao.CheckDAO;
import com.epam.project.hotel.dao.Factory;
import com.epam.project.hotel.dao.entities.mysql.MySQLFactory;
import com.epam.project.hotel.sql.DBException;
import com.epam.project.hotel.sql.DataSource;
import com.epam.project.hotel.sql.entities.Check;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDAOTest{
    private Connection con;

    private Statement stmt;
    private PreparedStatement pstmt;

    private ResultSet rs;

    private MockedStatic<DataSource> dataSource;

    @Before
    public void setUp() throws DBException {
        con = Mockito.mock(Connection.class);
        stmt = Mockito.mock(Statement.class);
        pstmt = Mockito.mock(PreparedStatement.class);
        rs = Mockito.mock(ResultSet.class);

        dataSource = Mockito.mockStatic(DataSource.class);
        dataSource.when(dataSource::getClass).thenReturn(con);
    }

    @BeforeAll
    public static void flagUp(){
        Factory factory = MySQLFactory.getInstance();
        CheckDAO checkDAO = (CheckDAO) factory.getDAO("CheckDAO");
    }
    @Test
    public void findCheckByIdTest() throws DBException {
        Check check = null;

    }
}
