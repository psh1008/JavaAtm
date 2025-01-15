package client;

import java.util.ArrayList;

import account.AccountDAO;
import utils.Utils;

public class ClientDAO {
	private ArrayList<Client> cList;
	private Utils utils;
	private int num = 1001;
	public void init(){
		cList = new ArrayList<>();
		utils = Utils.getInstance();
	}
	
	public void join() {
		String id = utils.getStringValue("ID 입력 >>");
		if(checkIdDup(id)) {
			System.out.println("아이디 중복");
			return;
		}
		String pw = utils.getStringValue("PW 입력 >> ");
		String name = utils.getStringValue("이름 입력 >> ");
		cList.add(new Client((num),id,pw,name));
		num++;
	}
	
	public int getClientNo(String id) {
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getId().equals(id))return cList.get(i).getClientNo();
		}
		return 0;
	}
	
	public void delUser(AccountDAO accDAO) {
		
		if(cList.size() == 0) {
			System.out.println("불러올 데이터가 없습니다.");return;
		}
		
		String id = utils.getStringValue("삭제할 아이디 입력 >>");
		int idx = checkIdDupIdx(id);
		if(idx == -1) {
			System.out.println("일치하는 아이디가 존재하지 않습니다");return;
		}
		cList.remove(idx);
		accDAO.delClientAcc(id);
	}
	
	public int checkIdDupIdx(String id) {
		int idx = -1;
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getId().equals(id))idx = i;
		}
		return idx;
	}
	
	public void updateClient() {
		if(cList.size() == 0) {
			System.out.println("데이터가 없습니다");return;
		}
		
		String id = utils.getStringValue("수정할 회원 아이디를 입력 하세요>>");
		if(checkIdDup(id)) {
			int idx = -1;
			for(int i=0;i<cList.size();i++) {
				if(cList.get(i).getId().equals(id)) {
					idx = i;
				}
			}
			System.out.println("수정할 회원 정보를 입력하세요");
			String pw = utils.getStringValue("비밀번호 입력 >> ");
			if(pw.equals(cList.get(idx).getPw())) {
				System.out.println("기존과 다른 비밀번호를 입력하세요");return;
			}
			String name = utils.getStringValue("이름 입력 >>");
			if(name.equals(cList.get(idx).getName())) {
				System.out.println("기존과 다른 이름을 입력하세요");return;
			}
			cList.get(idx).setPw(pw);
			cList.get(idx).setName(name);
			System.out.println("회원정보 수정 성공!");
		}else {
			System.out.println("일치하는 아이디가 존재하지 않습니다.");return;
		}
	}
	
	public boolean delClient(String id,AccountDAO accDAO) {
		
		String pw = utils.getStringValue("비밀번호 입력 >>");
		boolean isCheck = false;
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getId().equals(id) && cList.get(i).getPw().equals(pw)) {
				isCheck = true;
			}
		}
		if(!isCheck) {
			System.out.println("비밀번호가 일치하지 않습니다.");return false;
		}
		if(cList.size() == 0) {
			System.out.println("삭제할 회원이 없습니다.");return false;
		}
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getId().equals(id)) {
				cList.remove(i);
			}
		}
		accDAO.delClientAcc(id);
		System.out.println("탈퇴 완료!");
		return true;
	}
	
	public void printMyPage(String id,AccountDAO accDAO) {
		
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getId().equals(id)) {
				System.out.printf("아이디 : %s\n비밀번호 : %s\n이름 : %s\n",cList.get(i).getId(),cList.get(i).getPw(),cList.get(i).getName());
			}
		}
		accDAO.printMyAccount(id);
	}
	
	public String logIn() {
		String id = utils.getStringValue("ID 입력 >> ");
		if(checkIdDup(id)) {
			String pw = utils.getStringValue("PW 입력 >> ");
			if(checkPwDup(pw)) {
				System.out.println("로그인 성공!");
				return id;
			}else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		}else {
			System.out.println("ID가 틀렸습니다.");
		}
		return null;
	}
	
	public void printClientList() {
		if(cList.size() == 0) {
			System.out.println("데이터가 없습니다.");return;
		}
		
		for(int i=0;i<cList.size();i++) {
			System.out.println(cList.get(i));
		}
	}
	
	private boolean checkPwDup(String pw) {
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getPw().equals(pw)) {
				return true;
			}
		}
		return false;
	}

	public void loadClientData(String ClientData) {
		
		if(ClientData == null) {
			System.out.println("불러올 데이터가 없습니다.");return;
		}
		
		cList.clear();
		
		String[] temp = ClientData.split("\n");
		for(int i=0;i<temp.length;i++) {
			String[] info = temp[i].split("/");
			cList.add(new Client(Integer.parseInt(info[0]),info[1],info[2],info[3]));
		}
	}
	
	public String getCList() {
		String data = "";
		for(int i=0;i<cList.size();i++) {
			data += cList.get(i).getClientNo()+"/";
			data += cList.get(i).getId()+"/";
			data += cList.get(i).getPw()+"/";
			data += cList.get(i).getName()+"\n";
		}
		return data;
	}
	
	public void printClient(){
		for(int i=0;i<cList.size();i++) {
			System.out.println(cList.get(i));
		}
	}

	private boolean checkIdDup(String id) {
		
		for(int i=0;i<cList.size();i++) {
			if(cList.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}
