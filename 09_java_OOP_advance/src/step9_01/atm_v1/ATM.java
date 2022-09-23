package step9_01.atm_v1;
import java.util.Random;
import java.util.Scanner;

public class ATM {
	
	Scanner scan = new Scanner(System.in);
	Random ran   = new Random();
	UserManager userManager = new UserManager(); //유저 관리자 객체
	int identifier = -1;
	
	void printMainMenu() {
				
		while (true) {
			
			System.out.println("\n[ MEGA ATM ]");
			System.out.print("[1.로그인] [2.로그아웃] [3.회원가입] [4.회원탈퇴] [0.종료] : ");
			int sel = scan.nextInt();
			
			if      (sel == 1) 	    login();
			else if (sel == 2) 		logout();
			else if (sel == 3) 		join();
			else if (sel == 4) 		leave();
			else if (sel == 0) 		break;
			
		}
		
		System.out.println("[메시지] 프로그램을 종료합니다.");
		
	}
	
	
	void login() { //로그인 기능 
		
		identifier = userManager.logUser(); 
		
		if (identifier != -1) {
			printAccountMenu(); // 로그인이 완료되면 계좌 메뉴 기능으로 
		}
		else {
			System.out.println("[메세지] 로그인실패.");
		}
		
	}
	
	
	void join() {	// 가입 기능
		userManager.addUser();
	}
	
	
	void logout() {
		
		if (identifier == -1) {
			System.out.println("[메시지] 로그인을 하신 후 이용하실 수 있습니다.");
		}
		else {
			identifier = -1;
			System.out.println("[메시지] 로그아웃 되었습니다.");
		}
		
	}
	
	
	void leave() { // 탈퇴 기능
		userManager.leave();
	}
	
	
	void printAccountMenu() { 
		
		while (true) {
			
			System.out.print("[1.계좌생성] [2.계좌삭제] [3.조회] [0.로그아웃] : ");
			int sel = scan.nextInt();
			
			String makeAccount = Integer.toString(ran.nextInt(90001) + 10000);
			 
			
			if (sel == 1) { 
				if (userManager.user[identifier].accCount == 0) { // 로그인한 유저의 계좌가 아무것도 없으면
					userManager.user[identifier].acc = new Account[1]; // 계좌배열 생성
					
					userManager.user[identifier].acc[0] = new Account(); // 계좌
					userManager.user[identifier].acc[0].number = makeAccount; // 생성된 계좌번호 넣어주기	
				
				}
				else { // 로그인한 유저의 계좌가 이미 존재하면
					Account[] temp = userManager.getUser(identifier).acc; // 이전 계좌들의 정보를 임시저장 
					int tempAccCount = userManager.getUser(identifier).accCount; // 이전 계좌들의 개수
					userManager.user[identifier].acc = new Account[tempAccCount+1]; // 로그인한 유저의 추가할 계좌를 포함한 배열 생성
					for (int i = 0; i < tempAccCount; i++) {
						userManager.user[identifier].acc[i] = temp[i];
					}
					userManager.user[identifier].acc[tempAccCount] = new Account();
					userManager.user[identifier].acc[tempAccCount].number = makeAccount;
					
				}
				userManager.user[identifier].accCount++;
				System.out.println("[메시지]'"+makeAccount +"'계좌가 생성되었습니다.\n");
			} 	
			else if (sel == 2) {
				
				if (userManager.user[identifier].accCount == 0) {
					System.out.println("[메시지] 더 이상 삭제할 수 없습니다.\n");
					continue;
				}
				
				if (userManager.user[identifier].accCount == 1) { // 계좌의 개수가 한개밖에 없으면 널처리하면 됨
					System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[0].number+"' 삭제 되었습니다.\n");
					userManager.user[identifier].acc = null;
				}
				else {
					
					System.out.print("삭제 하고 싶은 계좌 번호를 입력하세요 : ");
					String deleteAccount = scan.next();
					int tempAccCount = userManager.user[identifier].accCount;
					int delIdx = -1;
					for (int i = 0; i <tempAccCount; i++) {
						if (deleteAccount.equals(userManager.user[identifier].acc[i].number)) {
							delIdx = i;
						}
					}
					
					if (delIdx == -1) {
						System.out.println("[메시지] 계좌번호를 확인하세요.\n");
						continue;
					}
					else {
						System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[delIdx].number+"' 삭제 되었습니다.\n");
						
						Account[] temp = userManager.user[identifier].acc;
						userManager.user[identifier].acc = new Account[tempAccCount-1];
						
						
						for (int i = 0; i < delIdx; i++) {
							userManager.user[identifier].acc[i] = temp[i];
						}
						for (int i = delIdx; i < tempAccCount - 1; i++) {
							userManager.user[identifier].acc[i] = temp[i+1];
						}
					}
					
				}
				userManager.user[identifier].accCount--;
				
			}
			
			else if (sel == 3) {
				if (userManager.user[identifier].accCount == 0) {
					System.out.println("[메시지] 생성된 계좌가 없습니다.\n");
				}
				else {
					userManager.user[identifier].printAccount();
				}
			}   
			else if (sel == 0) {
				logout();
				break;
			}
			
		}
		
	}	
}
