package step9_02.atm_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	
	private FileManager() {} // 파일 매니저 생성자 private로 생성
	private static FileManager instance = new FileManager(); // static 으로 파일 매니저의 인스턴스 생성
	public static FileManager getInstance() {
		return instance; // 파일 매니저의 인스턴스를 반환 해주는 메서드
	}
	
	String fileName = "ATM.txt";
	String data = "";
	UserManager um = UserManager.getInstance();
	
	void setData() {
		
		// 데이터 넣기
		data = "";
		int userCount = um.userCnt;
		data += userCount;
		data += "\n";
		
		for (int i = 0; i < userCount; i++) {
			data += um.userList[i].id;
			data += "\n";
			data += um.userList[i].pw;
			data += "\n";
			data += um.userList[i].accCnt;
			data += "\n";
			
			if (um.userList[i].accCnt == 0) {
				data += "0\n";
			}
			else {
				for (int j = 0; j < um.userList[i].accCnt; j++) {
					data += um.userList[i].acc[j].accNumber;
					data += "/";
					data += um.userList[i].acc[j].money;
					if (j != um.userList[i].accCnt - 1) {
						data += ",";
					}
				}
				data += "\n";
			}
		}
		
	}
	
	
	void save() {
		
		setData();
		
		FileWriter fw = null; // 파일 만드는 객체
		
		try {
			fw = new FileWriter(fileName); //  file이름을 가지는 파일 만들기 
			fw.write(data); // 파일에 데이터 입력
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {try {fw.close();} catch (IOException e) {}}
		}
		
	}
	
	
	void load() { // 파일 로드 (불러오기)
		
		File file = new File(fileName); // 파일 객체 생성
		FileReader fr = null; 
		BufferedReader br = null; 
		
		try {
			
			// 왜 다시 넣지..???/
			if (file.exists()) { // 파일이 존재하면 
				 
				fr = new FileReader(file); // 파일 리더 객체 생성
				br = new BufferedReader(fr); // 파일의 텍스트 리더 객체 생성
				while (true) {
					String line = br.readLine(); // 한줄씩 읽기
					if (line == null) { // 읽을 줄이 없으면 멈추기
						break;
					}
					data += line; // 한 줄씩 데이터 저장
					data += "\n"; 
				}
				
				String[] tmp = data.split("\n"); // 한줄마다 spilt 해주기
				//temp[0] == um.userCnt
				//temp[1] == um.userlist[i].id
				//temp[2] == um.userlist[i].pw
				//temp[3] == um.userlist[].accCnt
				um.userCnt = Integer.parseInt(tmp[0]); // 
				um.userList = new User[um.userCnt];
				for (int i = 0; i < um.userCnt; i++) {
					um.userList[i] = new User();
				}
				
				int j = 0;
				for(int i = 1; i < tmp.length; i += 4) {
					String id = tmp[i];
					String pw = tmp[i+1]; 
					int accCnt = Integer.parseInt(tmp[i+2]);
					um.userList[j].id = id;
					um.userList[j].pw = pw;
					um.userList[j].accCnt = accCnt;
					String accInfo = tmp[i+3];
					if (accCnt == 1) {
						String[] temp = accInfo.split("/");
						String acc = temp[0];
						int money = Integer.parseInt(temp[1]);
						um.userList[j].acc[0] = new Account();
						um.userList[j].acc[0].accNumber = acc;
						um.userList[j].acc[0].money = money;
					}
					if (accCnt > 1){
						String[] temp = accInfo.split(",");
						for (int k = 0; k < temp.length; k++) {
							String[] parse = temp[k].split("/");
							String acc = parse[0];
							int money = Integer.parseInt(parse[1]);
							um.userList[j].acc[k] = new Account();
							um.userList[j].acc[k].accNumber = acc;
							um.userList[j].acc[k].money = money;
						}
					}
					j++;
				}
			}
			else {
				//um.setDummy();
				setData();
				save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {try {br.close();} catch (IOException e) {}}
			if (fr != null) {try {fr.close();} catch (IOException e) {}}
		}
	}
	
}





