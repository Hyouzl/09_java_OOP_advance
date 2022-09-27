package step9_02.atm_v2;

import java.util.Random;
import java.util.Scanner;

public class UserManager { 
	
	/*
	 * 유저 매니저 클래스에는
	 * 
	 *  모든 유저의 정보를 출력하는 메서드 printAllUser()
	 *  중복되는 계좌가 있는지 확인하는 메서드 boolean getCheckAcc(String account)
	 *  중복되는 아이디가 있는지 확인하는 메서드 boolean checkId(String id)
	 *	로그인 메서드 int logUser() 
	 *	회원가입 메서드 void joinMember()
	 *	회원삭제 메서드 int deleteMember(int identifier)
	 *	
	 * 
	 */
	
	// singleTon (객체를 1번만 생성하기 하기 위해서)
	private UserManager() {} // private 생성자를 만든다.
	static private UserManager instance = new UserManager(); // 내부에서 static으로 자기자신의 인스턴스를 생성한다.
	static public UserManager getInstance() { //인스턴스를 반환 해줄 메서드
		return instance; 
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	
	final int ACC_MAX_CNT = 3;			// 최대 개설 가능한 계좌 수
	User[] userList = null;				// 전체 회원정보
	int userCnt = 0;					// 전체 회원 수
	
	void printAllUser() { 
		
		for (int i = 0; i < userCnt; i++) {
			System.out.print((i+1) + ".ID(" + userList[i].id + ")\tPW(" + userList[i].pw + ")\t");
			if (userList[i].accCnt != 0) {
				for (int j = 0; j < userList[i].accCnt; j++) {
					System.out.print("(" + userList[i].acc[j].accNumber + ":" + userList[i].acc[j].money + ")");
				}
			}
			System.out.println();
		}
	}
	
	
	boolean getCheckAcc(String account) { //중복되는 계좌 번호가 있는지 
		
		boolean isDuple = false;
		for (int i=0; i<userCnt; i++) {
			for (int j = 0; j < userList[i].accCnt; j++) { // 모든 유저의 모든 계좌 번호를 확인
				if (account.equals(userList[i].acc[j].accNumber)) {
					isDuple = true; // 중복되는 계좌가 있으면 true 반환
				}
			}
		}
		return isDuple; // 아니면 false
		
	}
	
	
	int logUser() {
		
		int identifier = -1;
		
		System.out.print("[로그인]아이디를 입력하세요 : ");
		String id = scan.next();
		System.out.print("[로그인]패스워드를 입력하세요 : ");
		String pw = scan.next();
		
		for (int i = 0; i < UserManager.instance.userCnt; i++) {  // 왜 instance 생성 ..?
			if (userList[i].id.equals(id) && userList[i].pw.equals(pw)) {
				identifier = i;
			}
		}
		
		return identifier;

	}
	
	
	boolean checkId(String id) { // 중복되는 아이디 체크
		
		boolean isDuple = false;
		for (int i = 0; i < userCnt; i++) {
			if (userList[i].id.equals(id)) {
				isDuple = true;
			}
		}
		return isDuple;
		
	}
	
	
	void joinMember() { 
		
		System.out.print("[회원가입]아이디를 입력하세요 : ");
		String id = scan.next();
		System.out.print("[회원가입]패스워드를 입력하세요 : ");
		String pw = scan.next();
		
		boolean isResult = checkId(id);
		
		if (isResult) {
			System.out.println("[메세지]아이디가 중복됩니다.");
			return;
		}
		
		if (userCnt == 0) {
			userList = new User[userCnt + 1];
			userList[userCnt] = new User(); 
		}
		else {
			User[] tmp = userList;
			userList = new User[userCnt + 1];
			userList[userCnt] = new User();
			
			for (int i = 0; i < userCnt; i++) {
				userList[i] = tmp[i];
			}
			tmp = null;
		}
		userList[userCnt].id = id;
		userList[userCnt].pw = pw;
		
		userCnt++;
		System.out.println("[메세지]회원가입을 축하합니다.");
		
		FileManager.getInstance().save();

	}

	
	int deleteMember(int identifier) {
		
		User[] tmp = userList;
		userList = new User[userCnt - 1];
		
		int j = 0;
		for (int i = 0; i < userCnt; i++) {
			if (i != identifier) {
				userList[j++] = tmp[i];
			}
		}
		
		userCnt--;
		tmp = null;
		identifier = -1;
		
		System.out.println("[메세지]탈퇴되었습니다.");

		FileManager.getInstance().save();
		
		return identifier;
		
	}
	
	// (테스트생성용 메서드)  : 테스트 데이타 > 더미
	void setDummy() {
		
		String[] ids  = {"user1"  ,  "user2",     "user3",    "user4",    "user5"};
		String[] pws  = {"1111"   ,   "2222",      "3333",     "4444",    "5555"};
		String[] accs = {"1234567",  "2345692",  "1078912",   "2489123",  "7391234"};
		int[] moneys  = { 87000   ,     12000,    49000,        34000,     128000};
		
		userCnt = 5;
		userList = new User[userCnt];
		
		for (int i = 0; i < userCnt; i++) {
			userList[i] = new User();
			userList[i].id = ids[i];
			userList[i].pw = pws[i];
			userList[i].acc[0] = new Account();
			userList[i].acc[0].accNumber = accs[i];
			userList[i].acc[0].money = moneys[i];
			userList[i].accCnt++;
		}
		
	}
	
}
