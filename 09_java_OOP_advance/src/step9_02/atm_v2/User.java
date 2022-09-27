package step9_02.atm_v2;

public class User {
	
	//유저 정보에는 가지고 있는 계좌들 계좌 개수 아이디,비밀번호
	Account[] acc = new Account[UserManager.getInstance().ACC_MAX_CNT];	
	int accCnt;	
	String id;											
	String pw;											
	
}


