package service;

import java.util.List;

import model.dao.BoardDAO;
import model.dto.BoardDTO;
import model.dto.CommentDTO;
import model.dto.MemberDTO;

public class BoardService {

    private BoardDAO dao;

    public BoardService() {
        this.dao = new BoardDAO();
    }

    // 1. 전체 게시글 조회
    public List<BoardDTO> getAllBoards() {
        return dao.selectAll();
    }

    // 2. 특정 게시글 상세 조회
    public BoardDTO getBoardById(int boardId) {
        return dao.selectById(boardId);
    }
    // 댓글도 같이
    public List<CommentDTO> selectComments(int boardId) {
    	return dao.selectComments(boardId);
    }

    // 3. 특정 사용자가 작성한 게시글 조회
    public List<BoardDTO> getBoardsByUser(MemberDTO user) {
    	Integer userId = user.getUser_id();
        return dao.selectByUser(userId);
    }
    
    //  username으로 게시글 조회
    public List<BoardDTO> getBoardsByUsername(String username) {
        return dao.selectByUsername(username);
    }
    
    

    // 4. 게시글 작성
    public void createBoard(BoardDTO board) {
    	try {
            dao.insert(board);
            System.out.println("[알림] 게시글 작성 완료");
        } catch (Exception e) {
            System.out.println("[오류] 게시글 작성 실패: " + e.getMessage());
        }
    }

    // 5. 게시글 수정
    public boolean updateBoard(BoardDTO board) {
        boolean result = dao.update(board);
        if (!result) {
            System.out.println("게시글 수정 실패 (본인 게시글인지 확인하세요)");
        }
        return result;
    }

    // 6. 게시글 삭제
    public boolean deleteBoard(int boardId, int userId) {
        boolean result = dao.delete(boardId, userId);
        if (!result) {
            System.out.println("게시글 삭제 실패 (본인 게시글인지 확인하세요)");
        }
        return result;
    }

}
