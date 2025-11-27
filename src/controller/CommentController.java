package controller;
import java.util.List;
import model.dto.CommentDTO;
import service.CommentService;
import view.CommentView;
public class CommentController {
    private static CommentService commentService = new CommentService();
    private static CommentView commentView = new CommentView();
    public static void startCommentManagement(int currentUserId) {
        while (true) {
            CommentView.showCommentManagementMenu();
            int choice = commentView.inputInt("");
            switch (choice) {
                case 1:
                	showCommentsByUser(currentUserId);
                    break;
                case 2:
                    removeComment(currentUserId);
                    break;
                case 3:
                    commentView.showMessage("메인 메뉴로 돌아갑니다.");
                    return;
                default:
                    commentView.showMessage("잘못된 선택입니다.");
            }
        }
    }
    public static void showCommentsByUser(int currentUserId) {
        List<CommentDTO> myComments = commentService.getCommentsByUser(currentUserId);
        commentView.showUserComments(myComments);
    }
    public static void showCommentsByBoard(int boardId) {
        List<CommentDTO> comments = commentService.getCommentsByBoard(boardId);
        commentView.showComments(comments, boardId);
    }
    public static void writeComment(int currentUserId,int boardId) {
        String content = commentView.inputCommentContent();
        // 새 댓글 DTO 생성 (내용, 게시글 ID, 현재 사용자 ID 사용)
        CommentDTO newComment = new CommentDTO();
        newComment.setContent(content);
        newComment.setUser_id(currentUserId);
        newComment.setBoard_id(boardId);
        int result = commentService.writeComment(newComment);
        if (result > 0) {
        	commentView.showMessage("댓글이 작성되었습니다.");
        } else {
            commentView.showMessage("댓글 작성에 실패했습니다.");
        }
	    showCommentsByBoard(boardId);
    }
    public static void removeComment(int currentUserId) {
        commentView.showMessage("댓글 삭제를 선택하셨습니다.");
        int commentId = commentView.inputCommentIdToDelete();
        boolean isDeleted = commentService.removeComment(commentId,currentUserId);
        if (isDeleted) {
            commentView.showMessage("댓글이 삭제되었습니다.");
        } else {
            commentView.showMessage("본인이 작성한 댓글만 삭제 가능합니다.");
        }
    }
}