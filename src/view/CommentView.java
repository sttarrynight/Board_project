package view;
import java.util.List;
import java.util.Scanner;
import model.dto.CommentDTO;
public class CommentView {
    private Scanner sc = new Scanner(System.in);
    public int inputInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                showMessage("유효하지 않은 입력입니다. 숫자를 입력해 주세요.");
            }
        }
    }
    public String inputString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
    public void showMessage(String msg) {
        System.out.println(">> " + msg);
    }
    public static void showCommentManagementMenu() {
        System.out.println("\n===== 댓글 관리 메뉴 =====");
        System.out.println("1. 내가 쓴 댓글 보기");
        System.out.println("2. 댓글 삭제");
        System.out.println("3. 뒤로가기");
        System.out.print("선택: ");
    }
    public void showComments(List<CommentDTO> comments, int boardId) {
        System.out.println("\n===== 게시글 ID " + boardId + " 댓글 목록 =====");
        if (comments == null || comments.isEmpty()) {
            System.out.println("댓글이 없습니다.");
            return;
        }
        for (CommentDTO comment : comments) {
            String formattedComment = String.format("%s: %s (작성일: %s, ID: %d)",
                comment.getUsername(),
                comment.getContent(),
                comment.getCreated_at().toString(),
                comment.getComment_id()
            );
            System.out.println(formattedComment);
        }
        System.out.println("------------------------------------");
    }
    public void showUserComments(List<CommentDTO> comments) {
        System.out.println("\n===== 내가 쓴 댓글 목록 =====");
        if (comments == null || comments.isEmpty()) {
            System.out.println("작성한 댓글이 없습니다.");
            return;
        }
        for (CommentDTO comment : comments) {
            String formattedComment = String.format("[게시글 ID %d] %s: %s (작성일: %s, 댓글 ID: %d)",
                comment.getBoard_id(),
                comment.getUsername(),
                comment.getContent(),
                comment.getCreated_at().toString(),
                comment.getComment_id()
            );
            System.out.println(formattedComment);
        }
        System.out.println("------------------------------------");
    }
    public int inputCommentIdToDelete() {
        return inputInt("삭제할 댓글 ID: ");
    }
    public String inputCommentContent() {
        return inputString("댓글 내용: ");
    }
}