package step9_01.atm_v1;

public class User {
	
	String id;
	int accCount; //계좌 개수
	Account[] acc; // 계좌의 정보를 담은 배열
	
	void printAccount() {
		
		for (int i = 0; i < accCount; i++) {
			acc[i].printOwnAccount();
		}	
		
	}
	
}
