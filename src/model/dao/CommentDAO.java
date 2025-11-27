package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dto.CommentDTO;
import util.DBUtil;
public class CommentDAO {
	public int insertCommentReturn(CommentDTO dto) {
	    String sql = "INSERT INTO COMMENTS (content, created_at, board_id, user_id) "
	               + "VALUES (?, SYSDATE, ?, ?) ";
	    int result = 0;
	    try (Connection conn = DBUtil.dbConnect();
	    	 PreparedStatement pst = conn.prepareStatement(sql)){
	    	pst.setString(1, dto.getContent());
	    	pst.setInt(2, dto.getBoard_id());
	    	pst.setInt(3, dto.getUser_id());
	    	result = pst.executeUpdate();
	    } catch (SQLException e) {
	    	System.err.println("댓글 생성 오류: " + e.getMessage());
	    }
	   
	    return result;
	}
    public List<CommentDTO> findByUserId(int user_id) {
        String sql = "SELECT c.comment_id, c.content, c.created_at, c.board_id, c.user_id, u.username "
                + "FROM COMMENTS c "
                + "JOIN MEMBER u ON c.user_id = u.user_id "
                + "WHERE c.user_id = ? "
                + "ORDER BY c.created_at DESC"; // 최신 댓글이 먼저 나오도록
        List<CommentDTO> list = new ArrayList<>();
        try (Connection conn = DBUtil.dbConnect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, user_id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    CommentDTO dto = new CommentDTO(
                        rs.getInt("comment_id"),
                        rs.getString("content"),
                        rs.getDate("created_at"),
                        rs.getInt("board_id"),
                        rs.getInt("user_id"),
                        rs.getString("username")
                    );
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<CommentDTO> findByBoardId(int board_id) {
        String sql = "SELECT c.comment_id, c.content, c.created_at, c.board_id, c.user_id, u.username "
                   + "FROM COMMENTS c "
                   + "JOIN MEMBER u ON c.user_id = u.user_id "
                   + "WHERE c.board_id = ? "
                   + "ORDER BY c.created_at DESC";
        List<CommentDTO> list = new ArrayList<>();
        try (Connection conn = DBUtil.dbConnect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, board_id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    CommentDTO dto = new CommentDTO(
                        rs.getInt("comment_id"),
                        rs.getString("content"),
                        rs.getDate("created_at"),
                        rs.getInt("board_id"),
                        rs.getInt("user_id"),
                        rs.getString("username")
                    );
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean deleteComment(int comment_id,int user_id) {
        String sql = "DELETE FROM COMMENTS WHERE comment_id = ? AND user_id = ?";
        try (Connection conn = DBUtil.dbConnect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, comment_id);
            pst.setInt(2, user_id);
            int rows = pst.executeUpdate(); // 쿼리 실행 후 영향을 받은 행의 수
            return rows>0;
        } catch (SQLException e) {
            System.out.println("댓글 삭제 실패: "+e.getMessage());
            return false;
        }
    }
}