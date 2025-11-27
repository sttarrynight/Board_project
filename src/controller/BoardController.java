package controller;

import java.util.List;
import java.util.Scanner;

import model.dto.BoardDTO;
import model.dto.CommentDTO;
import model.dto.MemberDTO;
import service.BoardService;
import view.BoardView;

public class BoardController {

    private Scanner sc;
    private BoardService service;
    private BoardView view;

    public BoardController(Scanner sc) {
        this.sc = sc;
        this.service = new BoardService();
        this.view = new BoardView();
    }

    // ===================== 게시글 조회 서브 메뉴 =====================
    public void boardViewMenu(MemberDTO user) {
        while (true) {
            System.out.println("\n--- 게시글 조회 ---");
            System.out.println("1. 전체 조회");
            System.out.println("2. 게시글 상세 조회");
            System.out.println("3. 특정 사용자 게시글 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택 >> ");
            int sel = Integer.parseInt(sc.nextLine());

            switch (sel) {
                case 1 -> viewAllBoards();
                case 2 -> viewBoardDetail(user);
                case 3 -> viewBoardsByUser();
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // --------------------- 전체 게시글 조회 ---------------------
    private void viewAllBoards() {
        List<BoardDTO> list = service.getAllBoards();
        view.showAllBoards(list);
    }

    // --------------------- 게시글 상세 조회 + 댓글 ---------------------
    private void viewBoardDetail(MemberDTO user) {
        System.out.print("게시글 ID >> ");
        int id = Integer.parseInt(sc.nextLine());

        BoardDTO board = service.getBoardById(id);
        if (board == null) {
            view.showMessage("게시글이 존재하지 않습니다.");
            return;
        }

        // 댓글 가져오기
        List<CommentDTO> comments = service.selectComments(id);

        // 게시글 상세 + 댓글 출력
        view.showBoardDetailWithComments(board, comments);

        // 댓글 작성 선택
        System.out.println("1. 댓글 달기 0. 돌아가기");
        int sel = Integer.parseInt(sc.nextLine());
        if (sel == 1) {
            // TODO: CommentController 연동
            System.out.println("[알림] 댓글 작성 기능은 CommentController와 연동 필요");
        }
    }

    // --------------------- 특정 사용자 게시글 조회 ---------------------
    private void viewBoardsByUser() {
        System.out.print("사용자 이름 >> ");
        String username = sc.nextLine();

        List<BoardDTO> list = service.getBoardsByUsername(username);
        view.showBoardsByUser(list, username);
    }

    // ===================== 게시글 작성 =====================
    public void createBoard(MemberDTO user) {
        System.out.print("제목 >> ");
        String title = sc.nextLine();
        System.out.print("내용 >> ");
        String content = sc.nextLine();

        BoardDTO board = new BoardDTO();
        board.setTitle(title);
        board.setContent(content);
        board.setUser_id(user.getUser_id());
        board.setUsername(user.getUsername());

        service.createBoard(board);
    }

    // ===================== 나의 게시글 메뉴 =====================
    public void myBoardMenu(MemberDTO user) {
        while (true) {
            System.out.println("\n--- 나의 게시글 ---");
            System.out.println("1. 내 게시글 조회");
            System.out.println("2. 내 게시글 수정");
            System.out.println("3. 내 게시글 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택 >> ");
            int sel = Integer.parseInt(sc.nextLine());

            switch (sel) {
                case 1 -> {
                    List<BoardDTO> list = service.getBoardsByUser(user);
                    view.showBoardsByUser(list, user.getUsername());
                }
                case 2 -> updateMyBoard(user);
                case 3 -> deleteMyBoard(user);
                case 0 -> { return; }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // --------------------- 나의 게시글 수정 ---------------------
    private void updateMyBoard(MemberDTO user) {
        System.out.print("수정할 게시글 ID >> ");
        int id = Integer.parseInt(sc.nextLine());

        BoardDTO board = service.getBoardById(id);
        if (board == null || !board.getUser_id().equals(user.getUser_id())) {
            view.showMessage("본인 게시글이 아니거나 존재하지 않습니다.");
            return;
        }

        System.out.println("<수정 전>");
        System.out.println("제목: " + board.getTitle());
        System.out.println("내용: " + board.getContent());

        System.out.print("제목(" + board.getTitle() + ") >> ");
        String newTitle = sc.nextLine();
        if (newTitle.isEmpty()) newTitle = board.getTitle();

        System.out.print("내용(" + board.getContent() + ") >> ");
        String newContent = sc.nextLine();
        if (newContent.isEmpty()) newContent = board.getContent();

        BoardDTO temp = new BoardDTO();
        temp.setBoard_id(id);
        temp.setTitle(newTitle);
        temp.setContent(newContent);
        temp.setUser_id(user.getUser_id());

        boolean result = service.updateBoard(temp);
        if (result) view.showMessage("게시글 수정 완료");
        else view.showMessage("게시글 수정 실패");
    }

    // --------------------- 나의 게시글 삭제 ---------------------
    private void deleteMyBoard(MemberDTO user) {
        System.out.print("삭제할 게시글 ID >> ");
        int id = Integer.parseInt(sc.nextLine());

        boolean result = service.deleteBoard(id, user.getUser_id());
        if (result) view.showMessage("게시글 삭제 완료");
        else view.showMessage("삭제 실패");
    }
}
