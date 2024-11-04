package ex_241101;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Hsw1009DAO {
    // 데이터베이스 연결을 위한 Connection 객체
    private Connection con = null;

    // 회원 정보를 추가하는 메서드
    public boolean insertMember(Hsw1009DTO member) {
        boolean ok = false;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "INSERT INTO member501(id, name, email, password) VALUES (member501_seq.NEXTVAL, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());

            int r = pstmt.executeUpdate(); // 실행 -> 저장
            if (r > 0) {
                System.out.println("Insert successful");
                ok = true;
            } else {
                System.out.println("Insert failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(pstmt, con);
        }
        return ok;
    }

    // 모든 회원 정보를 조회하는 메서드
    public List<Hsw1009DTO> selectAllMembers() {
        List<Hsw1009DTO> resultList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "SELECT * FROM member501 ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Hsw1009DTO dto = new Hsw1009DTO(id, name, email, password);
                resultList.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(pstmt, con);
        }
        return resultList;
    }

    // 이메일로 회원 조회
    public Hsw1009DTO selectMemberByEmail(String inputEmail) {
        Hsw1009DTO dto = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "SELECT * FROM member501 WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, inputEmail);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                dto = new Hsw1009DTO(id, name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(pstmt, con);
        }
        return dto;
    }

    // 회원 삭제
    public void deleteMember(int userId) {
        PreparedStatement pstmt = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "DELETE FROM member501 WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            int r = pstmt.executeUpdate();
            if (r > 0) {
                System.out.println("Deletion successful");
                JOptionPane.showMessageDialog(null, userId + " deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(pstmt, con);
        }
    }

    // 회원 수정
    public void updateMember(Hsw1009DTO member) {
        PreparedStatement pstmt = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "UPDATE member501 SET name = ?, email = ?, password = ? WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setInt(4, member.getId());

            int r = pstmt.executeUpdate();
            if (r > 0) {
                System.out.println("Update successful");
                JOptionPane.showMessageDialog(null, member.getId() + " updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(pstmt, con);
        }
    }

    // 이메일 존재 여부 확인
    public boolean isEmailExists(String email) {
        boolean exists = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "SELECT COUNT(*) FROM member501 WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(pstmt, con);
        }
        return exists;
    }

    // 자원 해제 메서드
    private void closeResources(PreparedStatement pstmt, Connection con) {
        try {
            Connection rs = null;
			if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // 이메일로 회원 삭제
    public boolean deleteMember(String email) {
        boolean isDeleted = false;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionDB.getConn();
            String sql = "DELETE FROM member501 WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);

            int r = pstmt.executeUpdate(); // 실행 -> 삭제
            if (r > 0) {
                System.out.println("삭제 성공");
                JOptionPane.showMessageDialog(null, email + " 삭제 성공", "Message", JOptionPane.INFORMATION_MESSAGE);
                isDeleted = true;
            } else {
                System.out.println("삭제 실패: 해당 이메일의 회원이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(pstmt, con);
        }
        
        return isDeleted;
    }
}







