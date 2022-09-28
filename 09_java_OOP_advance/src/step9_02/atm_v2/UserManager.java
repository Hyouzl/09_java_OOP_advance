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
		
		for (int i = 0; i < userCnt; i++) { // 아이디 비번, (계좌있으면) 계좌 정보 보여주기
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
		
		for (int i = 0; i < UserManager.instance.userCnt; i++) {  // 생성한 유저 객체를 통해서 정보 얻기
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
		
		// 회원가입 할 아이디와 패스워드 를 입력받은 뒤 중복아이디체크 메서드를 이용해 중복아이디 체크
		// 중복 되는게 없으면 멤버 추가
		
		
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
			userList[userCnt] = new User(); // 가입된 유저가 없을 때는 그냥 유저 배열 추가 후 유저 배열에 유저 객체 넣기.
		}
		else { // 가입된 유저가 있을 때는 원래 있던 유저 배열 임시저장후 사이즈추가한 유저 배열 생성 후 객체 넣어주기
			User[] tmp = userList;
			userList = new User[userCnt + 1];
			userList[userCnt] = new User();
			
			for (int i = 0; i < userCnt; i++) {
				userList[i] = tmp[i]; // 그 전 배열들 다 넣어주기
			}
			tmp = null; 
		}
		userList[userCnt].id = id;
		userList[userCnt].pw = pw;
		
		userCnt++;
		System.out.println("[메세지]회원가입을 축하합니다.");
		
		FileManager.getInstance().save(); // 파일에 저장

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
