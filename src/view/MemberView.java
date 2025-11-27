package view;

import model.dto.MemberDTO;
import util.CommonUtil;

public class MemberView {
	
		//로그인, 회원가입 메뉴
		public static void showAuthMenu() {
			System.out.println("\n========== 로그인/회원가입 ==========");
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("0. 종료");
			System.out.println("====================================");
		}
		
		//내정보 메뉴
		public static void showMemberInfoMenu() {
			System.out.println("\n========== 내정보 관리 ==========");
			System.out.println("1. 정보 조회");
			System.out.println("2. 비밀번호 수정");
			System.out.println("3. 회원 탈퇴");
			System.out.println("0. 이전 메뉴");
			System.out.println("================================");
		}
		
		//회원 정보 출력
		public static void printMemberInfo(MemberDTO member) {
			
			if(member == null) {
				System.out.println("[알림] 회원 정보를 찾을 수 없습니다.");
				return;
			}
			
			System.out.println("\n========== 회원 정보 ==========");
			System.out.println("아이디: " + member.getUsername());
			System.out.println("전화번호: " + member.getPhone());
			System.out.println("가입일: " + CommonUtil.formatDate(member.getCreated_at()));
			
			System.out.println("===============================");
		}
}