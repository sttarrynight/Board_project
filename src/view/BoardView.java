package view;

import java.util.List;
import model.dto.BoardDTO;
import model.dto.CommentDTO;
import util.CommonUtil;

public class BoardView {

    // 전체 게시글 출력
    public void showAllBoards(List<BoardDTO> boardList) {
        if (boardList == null || boardList.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }

        System.out.println("================= 전체 게시글 =================");
        for (BoardDTO b : boardList) {
            // 수정된 적이 있으면 updated_at 사용
            String date = (b.getUpdated_at() != null) ? b.getUpdated_at().toString() : b.getCreated_at().toString();

            System.out.printf("ID: %d | 제목: %s | 작성자: %s | 날짜: %s\n",
                    b.getBoard_id(), b.getTitle(), b.getUsername(), date);
        }
    }

    // 게시글 상세 출력
    public void showBoardDetail(BoardDTO board) {
        if (board == null) {
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }

        System.out.println("================= 게시글 상세 =================");
        System.out.println("ID: " + board.getBoard_id());
        System.out.println("제목: " + board.getTitle());
        System.out.println("내용: " + board.getContent());
        System.out.println("작성자: " + board.getUsername());
        System.out.println("작성일: " + board.getUpdated_at());
        System.out.println("수정일: " + board.getUpdated_at());

    }

    // 특정 사용자 게시글 출력
    public void showBoardsByUser(List<BoardDTO> boardList, String username) {
        if (boardList == null || boardList.isEmpty()) {
            System.out.println(username + "님이 작성한 게시글이 없습니다.");
            return;
        }

        System.out.println("================= " + username + "님의 게시글 =================");
        for (BoardDTO b : boardList) {
            String date = (b.getUpdated_at() != null) ? b.getUpdated_at().toString() : b.getCreated_at().toString();

            System.out.printf("ID: %d | 제목: %s | 날짜: %s\n",
                    b.getBoard_id(), b.getTitle(), date);
        }
    }

    // 게시글 작성/수정/삭제 결과 출력
    public void showMessage(String msg) {
        System.out.println("[알림]" + msg);
    }
    
    // 게시글 상세 + 댓글 출력
    public void showBoardDetailWithComments(BoardDTO board, List<CommentDTO> comments) {
        if (board == null) {
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }

        System.out.println("================= 게시글 상세 =================");
        System.out.println("ID: " + board.getBoard_id());
        System.out.println("제목: " + board.getTitle());
        System.out.println("내용: " + board.getContent());
        System.out.println("작성자: " + board.getUsername());
        System.out.println("작성일: " + CommonUtil.formatDate(board.getCreated_at()));
        System.out.println("수정일: " + (board.getUpdated_at() != null ?
                CommonUtil.formatDate(board.getUpdated_at()) : "수정 없음"));

        System.out.println("----------------- 댓글 -----------------");
        if (comments == null || comments.isEmpty()) {
            System.out.println("댓글이 없습니다.");
        } else {
            for (CommentDTO c : comments) {
                System.out.printf("댓글 ID: %d | 작성자: %s | 내용: %s | 작성일: %s\n",
                        c.getComment_id(), c.getUsername(), c.getContent(), c.getCreated_at());
            }
        }
        System.out.println("----------------------------------------");
    }
}
