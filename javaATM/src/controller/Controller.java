package controller;

import account.AccountDAO;
import client.ClientDAO;
import utils.Utils;

public class Controller {
	private AccountDAO accDAO;
	private ClientDAO cDAO;
	
	
	public void run() {
		accDAO = new AccountDAO();
		cDAO = new ClientDAO();
		accDAO.init();
		cDAO.init();
		
		while(true) {
			printMainMenu();
			int sel = Utils.getInstance().getIntValue("메뉴 입력[0-2] >> ", 0, 2);
			if(sel == 0) {
				System.out.println("프로그램 종료");
				break;
			}else if(sel == 1) { // 관리자
				 managerMenu();
			}else if(sel == 2) { // 사용자
				userMenu();
			}
		}
		
	}
	
	public void userMenu() {
		while (true) {
			printUserMenu();
			int sel = Utils.getInstance().getIntValue("메뉴 입력[0-2] >> ", 0, 2);
			if (sel == 0) { // 뒤로가기
				break;
			} else if (sel == 1) { // 회원가입
				cDAO.join();
			} else if (sel == 2) { // 로그인
				String id = cDAO.logIn();
				if(id == null)continue;
				logMenu(id);
			}
			cDAO.printClient();
		}
	}

	
	
	private void logMenu(String id) {
		while(true) {
			printLogMenu(id);
			int sel = Utils.getInstance().getIntValue("메뉴 입력[0-5] >> ", 0, 7);
			if(sel == 0) {
				System.out.println("로그아웃 성공!");
				return;
			}else if(sel == 1) {
				accDAO.addAcc(id,cDAO);
			}else if(sel == 2) {
				accDAO.delAcc(id);
			}else if(sel == 3) {
				accDAO.depositMoney();
			}else if(sel == 4) {
				accDAO.withdrawalAccount(id);
			}else if(sel == 5) {
				accDAO.accountTransfer(id);
			}else if(sel == 6) {
				if(cDAO.delClient(id,accDAO)) {
					return;
				}
			}else if(sel == 7) {
				cDAO.printMyPage(id,accDAO);
			}
		}
	}

	private void printLogMenu(String id) {
		System.out.printf("[ %s ]님 페이지\n",id);
		System.out.println("[1]계좌추가\n[2]계좌삭제\n[3]입금\n[4]출금\n[5]이체\n[6]탈퇴\n[7]마이페이지\n[0]로그아웃");
		
	}

	public void printUserMenu() {
		System.out.println("[1]회원가입\n[2]로그인\n[0]뒤로가기");
	}
	
	public void managerMenu() {
		while(true) {
			printManagerMenu();
			int sel = Utils.getInstance().getIntValue("메뉴 입력[0-5] >> ", 0, 5);
			if(sel == 0) {
				return;
			}else if(sel == 1) {
				cDAO.printClientList();
			}else if(sel == 2) {
				cDAO.updateClient();
			}else if(sel == 3) {
				cDAO.delUser(accDAO);
			}else if(sel == 4) {
				Utils.getInstance().saveDataToFile(accDAO,cDAO);
			}else if(sel == 5) {
			accDAO.loadAccData(Utils.getInstance().loadAccDataToFile());
			cDAO.loadClientData(Utils.getInstance().loadClientDataToFile());
			
			}
		}
	}
	
	public void printManagerMenu() {
		System.out.println("[1]회원목록\n[2]회원수정\n[3]회원삭제\n[4]데이터 저장\n[5]데이터불러오기\n[0]뒤로가기");
	}
	
	public void printMainMenu() {
		System.out.println("[1]관리자\n[2]사용자\n[0]종료");
	}
	
}
