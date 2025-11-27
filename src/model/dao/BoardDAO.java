package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dto.BoardDTO;
import model.dto.CommentDTO;
import util.DBUtil;

public class BoardDAO {
	
	// 특정 사용자가 쓴 게시글 조회
	// 게시글 id로 상세 조회  -> 댓글까지 보이게 
	// 게시글 전체 조회 
	// 게시글 작성
	// 게시글 수정
	// 게시글 삭제 

	
	// username으로 게시글 조회
	public List<BoardDTO> selectByUsername(String username) {
	    List<BoardDTO> list = new ArrayList<>();
	    String sql = """
	        SELECT b.board_id, b.title, b.content, b.created_at, b.updated_at,
	               b.user_id, u.username
	        FROM board b
	        JOIN member u ON b.user_id = u.user_id
	        WHERE u.username = ?
	        ORDER BY nvl(b.updated_at, b.created_at) DESC
	    """;

	    try (Connection conn = DBUtil.dbConnect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            BoardDTO dto = new BoardDTO(
	                rs.getInt("board_id"),
	                rs.getString("title"),
	                rs.getString("content"),
	                rs.getDate("created_at"),
	                rs.getDate("updated_at"),
	                rs.getInt("user_id"),
	                rs.getString("username")
	            );
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	
    // 1. 특정 사용자가 작성한 게시글 조회
    public List<BoardDTO> selectByUser(int userId) {
        List<BoardDTO> list = new ArrayList<>();

        String sql = """
            SELECT b.board_id, b.title, b.content,b.created_at , b.updated_at,b.user_id, u.username
            FROM board b
            JOIN member u ON b.user_id = u.user_id
            WHERE b.user_id = ?
            ORDER BY nvl(b.updated_at,b.created_at) DESC
        """;

        try (
            Connection conn = DBUtil.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                BoardDTO dto = new BoardDTO(
                    rs.getInt("board_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_at"),
                    rs.getDate("updated_at"),
                    rs.getInt("user_id"),
                    rs.getString("username")
                );

                
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // 2. 게시글 상세 조회 (+ 댓글)
    public BoardDTO selectById(int boardId) {
        BoardDTO board = null;

        String sql = """
            SELECT b.board_id, b.title, b.content, b.created_at,
                   b.updated_at, b.user_id, u.username
            FROM board b
            JOIN member u ON b.user_id = u.user_id
            WHERE b.board_id = ?
        """;

        try (
            Connection conn = DBUtil.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, boardId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new BoardDTO(
                    rs.getInt("board_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("created_at"),
                    rs.getDate("updated_at"),
                    rs.getInt("user_id"),
                    rs.getString("username")
                );
              
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return board;
    }

    // 댓글 리스트 반환
    public List<CommentDTO> selectComments(int boardId) {
        List<CommentDTO> list = new ArrayList<>();

        String sql = """
                SELECT c.comment_id,
                       c.board_id,
                       c.user_id,
                       u.username,
                       c.content,
                       c.created_at
                FROM comments c
                JOIN member u ON c.user_id = u.user_id
                WHERE c.board_id = ?
                ORDER BY c.created_at DESC
            """;

        try (
            Connection conn =  DBUtil.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, boardId);
            ResultSet rs = pstmt.executeQuery();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // 3. 전체 게시글 조회
    public List<BoardDTO> selectAll() {
        List<BoardDTO> list = new ArrayList<>();

        String sql = """
            SELECT b.board_id,
                   b.title,
                   b.content,
                   b.created_at,
                   b.updated_at,
                   b.user_id,
                   u.username
            FROM board b
            JOIN member u ON b.user_id = u.user_id
            ORDER BY NVL(b.updated_at, b.created_at) DESC
        """;

        try (
            Connection conn = DBUtil.dbConnect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
            	BoardDTO dto = new BoardDTO();
            	dto.setBoard_id(rs.getInt("board_id"));
            	dto.setTitle(rs.getString("title"));
            	dto.setContent(rs.getString("content"));
            	dto.setCreated_at(rs.getDate("created_at"));
            	dto.setUpdated_at(rs.getDate("updated_at"));
            	dto.setUser_id(rs.getInt("user_id"));
            	dto.setUsername(rs.getString("username"));

                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // 4. 게시글 작성
    public void insert(BoardDTO dto) {
        String sql = """
            INSERT INTO board (board_id, title, content, created_at, user_id)
            VALUES (seq_board.nextval, ?, ?, SYSDATE, ?)
        """;

        try (
            Connection conn =  DBUtil.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getUser_id());

            String result =  pstmt.executeUpdate()+"건 작성되었습니다!";
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // 5. 게시글 수정
    public boolean update(BoardDTO dto) {
        String sql = """
            UPDATE board
            SET title = ?, content = ?, updated_at = SYSDATE
            WHERE board_id = ? AND user_id = ?
        """;

        try (
            Connection conn = DBUtil.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getBoard_id());
            pstmt.setInt(4, dto.getUser_id()); // 본인 확인

            int result = pstmt.executeUpdate();
            return result > 0; // 수정 성공 여부
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. 게시글 삭제
    public boolean delete(int boardId, int userId) {
        String sql = "DELETE FROM board WHERE board_id = ? AND user_id = ?";

        try (
            Connection conn = DBUtil.dbConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, boardId);
            pstmt.setInt(2, userId); // 본인 확인

            int result = pstmt.executeUpdate();
            return result > 0; // 삭제 성공 여부
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 
    
	
	
	
}
