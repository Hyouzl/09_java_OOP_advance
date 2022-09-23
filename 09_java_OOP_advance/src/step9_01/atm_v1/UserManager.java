package step9_01.atm_v1;

import java.util.Scanner;

public class UserManager {
	//유저들을 관리하는 클래스
	Scanner scan = new Scanner(System.in);
	User[] user = null; // 유저 객체를 담을 배열
	int userCount = 0;
	
	void printAllUser() { // 모든 유저들의 정보 출력
		for(int i = 0; i < userCount; i++) {
			user[i].printAccount();
		}
	}
	
	
	
	void addUser() { // 유저 추가 기능
		
		if(userCount == 0) { // 가입된 유저가 아무도 없을 때
			user = new User[1]; // 첫 가입 유저를 담을 배열 생성
		}
		else { // 가입된 유저가 존재할 때 
			User[] temp = user; // 이전의 가입한 유저 객체들은 담은 배열을 임시저장
			user = new User[userCount + 1]; // 새로 가입한 유저를 포함할 수 있는 배열 생성
			for(int i = 0; i < userCount; i++) {
				user[i] = temp[i]; // 이전 가입 유저들 담기
			}
			temp = null; //임시저장소 비우기
		}
		
		
		System.out.print("[가입] 아이디를 입력하세요 : ");
		String id = scan.next();
		
		boolean isDuple = false;
		for (int i = 0; i < userCount; i++) {
			if (user[i].id.equals(id)) {
				isDuple = true; // 유저 아이디 중복이 있는지 확인
			}
		}
		if (!isDuple) {// 중복이 없으면
			user[userCount] = new User(); // 유저 배열에 새로운 유저 객체 생성
			user[userCount].id = id; // 입력한 아이디 넣어주기
			System.out.println("[메시지] ID : '" + id+ "' 가입 되었습니다.\n");
			userCount++; // 유저 수 늘려주기
		}
		else {
			System.out.println("[메시지] '"+ id + "'은 이미 가입된 아이디 입니다.\n");
		}
		
	}
	
	
	
	User getUser(int idx) { 
		
		return user[idx]; 
	}
	
	
	
	
	int logUser() { // 유저 로그인 기능
		
		int identifier = -1;
		System.out.print("[입력] 아이디를 입력하세요 : ");
		String name = scan.next();
		
		for (int i = 0; i < userCount; i++) {
			if (name.equals(user[i].id)) { //아이디를 입력하고 유저 배열 중에 일치하는 아이디가 존재하면 
				identifier = i;
				System.out.println("[" + user[identifier].id + "] 님 로그인.\n");
			} //로그인 성공
		}
		
		return identifier; //로그인 한 유저의 인덱스 리턴
		
	}
	
	
	
	void leave() { // 유저 탈퇴 기능
		
		System.out.print("[입력] 탈퇴할 아이디를 입력하세요 : ");
		String name = scan.next();
		int identifier = -1;
		for (int i = 0; i < userCount; i++) {
			if (name.equals(user[i].id)) {
				identifier = i;  //탈퇴할 아이디가 유저 배열안에 존재하면 인덱스 넘겨주기			
			}
		}
		
		if(identifier == -1) {
			System.out.println("[메시지] 아이디를 다시 확인하세요.");
			return;
		}
		
		System.out.println("ID : '" +user[identifier].id + "' 가 탈퇴되었습니다.");
		
		User[] temp = user; 
		user = new User[userCount - 1]; 
		
		for(int i = 0; i < identifier; i++) {
			user[i] = temp[i]; //  
		}
		for(int i =identifier; i < userCount-1; i++) {
			user[i] =temp[i + 1];
		}
		
		userCount--;
		
	}
	
}
