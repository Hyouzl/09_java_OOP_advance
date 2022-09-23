package step9_01.atm_v1;

public class Account { // 계좌 정보를 담는 클래스
	
	String number = "";// 계좌번호 
	int money = 0;// 계좌 안에 돈
	
	void printOwnAccount() {
		System.out.println(this.number +  " : " + this.money);
	}
	
}
