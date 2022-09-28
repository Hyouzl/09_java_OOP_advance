package step9_02.atm_v2;

import java.util.Random;
import java.util.Scanner;

public class AccountManager {
	
	/*
	 *  어카운트(게좌) 매니저 클래스에는 
	 *  
	 *  계좌 생성 메서드
	 *  계좌 삭제 메서드
	 *  유저의 모든 계좌를 보여주는 메서드
	 * 
	 */

	//sigleton >> 객체 한개만 생성하기위해 private 생성자 생성 / private static  객체 생성/ 생성한 객체 반환 메서드
	private AccountManager() {}
	private static AccountManager instance = new AccountManager();
	public static AccountManager getInstance() {
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	UserManager um = UserManager.getInstance();

	void createAcc(int identifier) { // 계좌 생성
		
		int accCntByUser = um.userList[identifier].accCnt;
		
		if (accCntByUser == 3) {
			System.out.println("[메세지]계좌개설은 3개까지만 가능합니다.");
			return;
		}
		
		um.userList[identifier].acc[accCntByUser] = new Account(); // 새로운 계좌 객체 생성
		
		String makeAccount = "";
		while (true) {
			makeAccount = ran.nextInt(9000000) + 1000001 + "";		
			if (!um.getCheckAcc(makeAccount)){ // 중복되는게 없으면
				break; //계좌 랜덤 생성을 멈춘다..
			}
		}
		um.userList[identifier].acc[accCntByUser].accNumber = makeAccount; // 생성한 계좌 넣어주기
		um.userList[identifier].accCnt++; //계좌 개수 업
		System.out.println("[메세지]'" + makeAccount + "'계좌가 생성되었습니다.\n");
	}
	
	void deleteAcc (int identifier) {
		
		int accCntByUser = um.userList[identifier].accCnt;
		
		if (accCntByUser == 0) {
			System.out.println("생성 된 게좌가 없습니다.");
		}
		else if (accCntByUser == 1) {
			System.out.println("[메세지] '" + um.userList[identifier].acc[0].accNumber + "' 계좌가 삭제 되었습니다.");
			um.userList[identifier].acc = null; // 계좌 한개밖에 없으면 걍널처리
			um.userList[identifier].accCnt--;
		}
		else {
			
			System.out.print("삭제할 계좌의 번호를 입력하세요 : ");
			String deleteName = scan.next();
			int deleteIdx = -1;
			
			for (int i = 0; i < accCntByUser; i++) {
				if (um.userList[identifier].acc[i].accNumber.equals(deleteName)) {
					deleteIdx = i;
				}
			}
			
			if (deleteIdx == -1) {
				System.out.println("[메세지] 계좌 번호를 확인하세요.");
			}
			else {
			
				Account[] temp = um.userList[identifier].acc;
				um.userList[identifier].acc = new Account[accCntByUser - 1];
				
				for (int i = 0; i< deleteIdx; i++)  {
					um.userList[identifier].acc[i] = temp[i];
				}
				for (int i = deleteIdx; i < accCntByUser; i++) {
					um.userList[identifier].acc[i] = temp[i + 1];
				}
				
				um.userList[identifier].accCnt--;
			}
		}
		
	}
	
	
	void printAcc(int identifier) {
		
		User temp = um.userList[identifier];
		System.out.println("====================");
		System.out.println("ID : " + temp.id);
		System.out.println("====================");
		for (int i = 0; i < temp.accCnt; i++) {
			System.out.println("accNumber:" +temp.acc[i].accNumber + " / money: " + temp.acc[i].money);
		}
		System.out.println("=============================\n");
		
	}
	
}
