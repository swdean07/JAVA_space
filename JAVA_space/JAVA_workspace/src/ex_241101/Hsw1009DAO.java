package ex_241101;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Hsw1009DAO {
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String userid = "scott";
    String passwd = "tiger";
	private Object connection;

    public Hsw1009DAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean insertMember(Hsw1009DTO member) {
        String insertQuery = "INSERT INTO member501(name, email, password) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            
            connection.setAutoCommit(false);
            
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            
            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // 롤백을 시도하려면 커넥션을 먼저 열어야 하므로
            try {
                if (connection != null) {
                    ((Connection) connection).rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        }
    }

    public List<Hsw1009DTO> fetchAllMembers() {
        List<Hsw1009DTO> members = new ArrayList<>();
        String query = "SELECT name, email, password FROM members";

        try (Connection connection = DriverManager.getConnection(url, userid, passwd);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Hsw1009DTO member = new Hsw1009DTO(
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

	public boolean isEmailExists(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	public Hsw1009DTO getMemberByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}