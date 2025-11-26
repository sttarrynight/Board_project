
import java.util.Scanner;

import model.dto.MemberDTO;

public class Main {
	
	private static Scanner sc = new Scanner(System.in);
	private static MemberDTO currentUser = null; // 현재 로그인한 사용자
	
	public static void main(String[] args) {
		start();
	}
	
	// 애플리케이션 시작
	private static void start() {
		System.out.println("========================================");
		System.out.println("      게시판 프로그램에 오신 것을 환영합니다!");
		System.out.println("========================================");
		
		// 로그인/회원가입 화면
		while(currentUser == null) {
			int choice = showLoginMenu();
			
			switch(choice) {
				//로그인
				case 1 -> { }
				// 회원가입
				case 2 -> { }
				// 종료
				case 0 -> {
					System.out.println("\n프로그램을 종료합니다.");
					return;
				}
				default -> {
					System.out.println("\n[오류] 잘못된 선택입니다.");
				}
			}
		}
		
		// 로그인 성공 후 메인 메뉴
		mainMenu();
	}
	
	// 로그인/회원가입 메뉴
	private static int showLoginMenu() {
		System.out.println("\n========== 시작 메뉴 ==========");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("0. 종료");
		System.out.print("선택: ");
		
		try {
			return Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	
	// 메인 메뉴
	private static void mainMenu() {
		while(currentUser != null) {
			int choice = showMainMenu();
			
			switch(choice) {
			 	// 게시글 조회
				case 1 -> {
					System.out.println("\n[알림] 게시글 조회 기능은 팀원이 구현 중입니다.");
					// TODO: BoardController 연동
					break;
				}
				// 게시글 작성
				case 2 -> {
					System.out.println("\n[알림] 게시글 작성 기능은 팀원이 구현 중입니다.");
					// TODO: BoardController 연동
					break;
				}
				// 나의 게시글
				case 3 -> {
					System.out.println("\n[알림] 나의 게시글 기능은 팀원이 구현 중입니다.");
					// TODO: BoardController 연동
					break;
				}
				// 나의 댓글
				case 4 -> {
					System.out.println("\n[알림] 나의 댓글 기능은 팀원이 구현 중입니다.");
					// TODO: CommentController 연동
					break;
				}
				// 회원정보
				case 5 -> {
					
					break;
				}
				// 로그아웃
				case 6 -> {
					logout();
					start(); // 다시 시작 화면으로
					return;
				}
					
				default -> {
					System.out.println("\n[오류] 잘못된 선택입니다.");
				}
			}
		}
	}
	
	// 메인 메뉴 출력
	private static int showMainMenu() {
		System.out.println("\n========== 메인 메뉴 ==========");
		System.out.println("1. 게시글 조회");
		System.out.println("2. 게시글 작성");
		System.out.println("3. 나의 게시글");
		System.out.println("4. 나의 댓글");
		System.out.println("5. 회원정보");
		System.out.println("6. 로그아웃");
		System.out.print("선택: ");
		
		try {
			return Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	
	// 로그아웃
	private static void logout() {
		if(currentUser != null) {
			System.out.println("\n" + currentUser.getUsername() + "님, 로그아웃 되었습니다.");
			currentUser = null;
		}
	}
}