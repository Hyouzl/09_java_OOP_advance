package step9_03.atm_v3;


public class AccountManager {

	private AccountManager() {}
	private static AccountManager instance = new AccountManager();
	public static AccountManager getInstance() {
		return instance;
	}
	
	UserManager userManager = UserManager.getInstance();
	
	void createAccount() {
		
		User loginUser = userManager.userList[userManager.identifier];
		
		if (loginUser.accCount == 3) {
			System.out.println("[메세지]더 이상 계좌를 생성할 수 없습니다.\n");
			return;
		}
		
		if (loginUser.accCount == 0) {
			loginUser.accList = new Account[loginUser.accCount + 1];
		} 
		else if (loginUser.accCount  > 0) {
			Account[] temp = loginUser.accList;
			
			loginUser.accList = new Account[loginUser.accCount + 1];
			for(int i = 0; i < loginUser.accCount; i++) {
				loginUser.accList[i] = temp[i];
			}
			temp = null;
		}
		loginUser.accList[loginUser.accCount] = new Account();
		
		String makeAccount = ATM.ran.nextInt(90000000) + 10000001 +"";
		loginUser.accList[loginUser.accCount].number = makeAccount;
		loginUser.accList[loginUser.accCount].money = 0;
		
		loginUser.accCount++;
		System.out.println("[메세지]계좌가 생성되었습니다.\n");
		
		FileManager.getInstance().saveData();
		
	}
	
	
	int showAccList(String msg) {
		
		int loginAccIndex = -1;

		User loginUser = userManager.userList[userManager.identifier];
		
		if (loginUser.accCount > 0) {
			for (int i = 0; i < loginUser.accCount; i++) {
				System.out.println("[" + (i+1) + "]" + loginUser.accList[i].number);
			}
			
			System.out.print("[" + msg + "]내 계좌를 메뉴에서 선택하세요 : ");
			loginAccIndex = ATM.scan.nextInt();
			loginAccIndex--;
		}
		
		return loginAccIndex;
		
	}
	
	
	void income() {
		
		int loginAccIndex = showAccList("입금");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		System.out.print("[입금]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money += money;
		System.out.println("[메세지]입금을 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
		
	}
	
	
	void outcome() {
		
		int loginAccIndex = showAccList("출금");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		System.out.print("[출금]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		if (userManager.userList[userManager.identifier].accList[loginAccIndex].money < money) {
			System.out.println("[메세지]계좌잔액이 부족합니다.\n");
			return;
		}
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
		System.out.println("[메세지]출금을 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
		
	}
	
	
	int checkAcc(String transAccount) {
		
		int check = -1;
		for (int i = 0; i < userManager.userList.length; i++) {
			if (userManager.userList[i].accList != null) {
				for (int j = 0; j < userManager.userList[i].accCount; j++) {
					if (transAccount.equals(userManager.userList[i].accList[j].number)) {
						check = i;
					}
				}
			}
		}
		return check;
		
	}
	
	
	int getAccIndex(int transUserIndex, String transAccount) {
		
		int accIndex = 0;
		
		for (int i=0; i<userManager.userList[transUserIndex].accCount; i++) {
			if (transAccount.equals(userManager.userList[transUserIndex].accList[i].number)) {
				accIndex = i;
			}
		}
		
		return accIndex;
		
	}
	
	
	void transfer() {
		
		int loginAccIndex = showAccList("이체");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}		
		
		System.out.print("[이체]이체할 '계좌번호'를 입력하세요 : ");
		String transAccount = ATM.scan.next();
		
		int transUserIndex = checkAcc(transAccount);
		if (transUserIndex == -1) {
			System.out.println("[메세지]'계좌번호'를 확인해주세요.\n");
			return;
		}
		
		int transAccIndex = getAccIndex(transUserIndex, transAccount);
		
		System.out.print("[이체]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		if (money > userManager.userList[userManager.identifier].accList[loginAccIndex].money) {
			System.out.println("[메세지]계좌잔액이 부족합니다.\n");
			return;
		}
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
		userManager.userList[transUserIndex].accList[transAccIndex].money += money;
		System.out.println("[메세지]이체를 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
	}
	
	
	void lookupAcc() {
		userManager.userList[userManager.identifier].printOneUserAllAccounts();
	}

	
	void deleteAcc() {
		
		int loginAccIndex = showAccList("삭제");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		User user = userManager.userList[userManager.identifier];
		
		if (user.accCount == 1) {
			user.accList = null;
		}
		else if(user.accCount > 1) {
			Account[] acc = user.accList;
			
			user.accList = new Account[user.accCount - 1];
			int j = 0;
			for (int i = 0; i < user.accCount; i++) {
				if (i != loginAccIndex) {
					user.accList[j] = acc[i];
					j = j + 1;
				}
			}
			acc = null;
		}
		user.accCount--;
		
		System.out.println("[메세지]계좌삭제를 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
	
	}
	
	
}
