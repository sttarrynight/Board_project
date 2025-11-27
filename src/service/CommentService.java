package service;
import java.util.List;
import model.dao.CommentDAO;
import model.dto.CommentDTO;
public class CommentService {
	 private CommentDAO commentDAO;
	    public CommentService() {
	        this.commentDAO = new CommentDAO();
	    }
	    public int writeComment(CommentDTO dto) {
	        return commentDAO.insertCommentReturn(dto);
	    }
	    public List<CommentDTO> getCommentsByBoard(int board_id) {
	        return commentDAO.findByBoardId(board_id);
	    }
	    public List<CommentDTO> getCommentsByUser(int user_id) {
	        return commentDAO.findByUserId(user_id);
	    }
	    public boolean removeComment(int comment_id,int user_id) {
	        return commentDAO.deleteComment(comment_id,user_id);
	    }
}