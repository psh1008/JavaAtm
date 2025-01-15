package account;

import java.util.ArrayList;

import client.Client;
import client.ClientDAO;
import utils.Utils;

public class AccountDAO {
	private ArrayList<Account> accList;
	private Utils utils;
	public void init(){
		accList = new ArrayList<>();
		utils = Utils.getInstance();
	}
	
	public void printAcc() {
		for(int i=0;i<accList.size();i++) {
			System.out.println(accList.get(i));
		}
	}
	
	public void addAcc(String id,ClientDAO cDAO) {
		String accNum = utils.getAccNo("계좌번호 입력 >> ");
		int cNum = cDAO.getClientNo(id);
		if(!checkAccNoDup(accNum)) {
			System.out.println("계좌 중복");return;
		}
		if(checkAccCnt(id)) {
			System.out.println("계좌는 3개 까지만 만들수 있습니다.");return;
		}
		accList.add(new Account(cNum,id,accNum));
	}

	private boolean checkAccCnt(String id) {
		int cnt = 0;
		for(int i=0;i<accList.size();i++) {
			if(accList.get(i).getClientId().equals(id))cnt++;
		}
		if(cnt == 3) {
			return true;
		}
		return false;
	}

	private boolean checkAccNoDup(String accNum) {
		for(int i=0;i<accList.size();i++) {
			if(accNum.equals(accList.get(i).getAccNumber()))return false;
		}
		return true;
	}
	
	public void delAcc(String id) {
		String accNo = utils.getAccNo("삭제할 계좌번호 입력 >>");
		int idx = checkMyAccount(accNo,id);
		if(idx == -1) {
			System.out.println("본인 계좌만 삭제 가능합니다.");
		}else if(idx == -2) {
			System.out.println("일치하는 계좌가 존재하지 않습니다.");
		}else {
			accList.remove(idx);
			System.out.println("삭제 성공!");
		}
	}

	
	public void withdrawalAccount(String id) {
		if(accList.size() == 0) {
			System.out.println("현재 계좌가 존재하지 않습니다.");
			return;
		}
		String accNo = utils.getAccNo("삭제할 계좌번호 입력 >>");
		int idx = checkMyAccount(accNo,id);
		if(idx == -1) {
			System.out.println("본인 계좌만 출금 가능합니다.");
		}else if(idx == -2) {
			System.out.println("일치하는 계좌가 존재하지 않습니다.");
		}else {
			int money = utils.getIntValue("출금할 금액 입력 >>", 100, 100000000);
			if(accList.get(idx).getMoney()-money<0) {
				System.out.println("계좌 잔고 부족");
			}else {
				accList.get(idx).setMoney(-money);
				System.out.println("출금 성공!");
			}
		}
		
		
	}
	
	public void accountTransfer(String id) {
		String accNo = utils.getAccNo("본인 계좌 입력 >> ");
		int idx = checkMyAccount(accNo,id);
		if(idx == -1) {
			System.out.println("본인 계좌만 선택 가능합니다.");
		}else if(idx == -2) {
			System.out.println("일치하는 계좌가 존재하지 않습니다.");
		}else {
			String yourAcc = utils.getAccNo("이체할 계좌 입력");
			if(yourAcc.equals(accNo)) {
				System.out.println("동일계좌 이체 불가능");
				return;
			}
			int index = -1;
			for(int i=0;i<accList.size();i++) {
				if(yourAcc.equals(accList.get(i).getAccNumber())) {
					index = i;
				}
			}
			if(index == -1) {
				System.out.println("일치하는 계좌가 존재하지 않습니다.");return;
			}
			int money = utils.getIntValue("이체할 금액 입력", 100, 100000000);
			if(accList.get(idx).getMoney()-money < 0) {
				System.out.println("잔액 부족");return;
			}
			accList.get(idx).setMoney(-money);
			accList.get(index).setMoney(money);
			System.out.println("이체 성공!");
		}
	}
	
	public void delClientAcc(String id) {
		
		for(int i=0;i<accList.size();i++) {
			if(accList.get(i).getClientId().equals(id)) {
				accList.remove(i);
				i--;
			}
		}
		
	}
	
	public void depositMoney() {
		if(accList.size() == 0) {
			System.out.println("현재 계좌가 존재하지 않습니다.");
			return;
		}
		String accNo = utils.getAccNo("입금할 계좌 입력 >>");
		if(checkAccNoDup(accNo)) {
			System.out.println("일치하는 계좌가 존재하지 않습니다.");
			return;
		}
		int money = utils.getIntValue("입금할 금액 입력 >>", 100, 100000000);
		
		for(int i=0;i<accList.size();i++) {
			if(accList.get(i).getAccNumber().equals(accNo)) {
				accList.get(i).setMoney(money);
			}
		}
		
	}
	
	public String getAccList() {
		String data = "";
		for(int i=0;i<accList.size();i++) {
			data += accList.get(i).getClientNo()+"/";
			data += accList.get(i).getClientId()+"/";
			data += accList.get(i).getAccNumber()+"/";
			data += accList.get(i).getMoney()+"\n";
		}
		return data;
	}
	
	public void printMyAccount(String id) {
		
		for(int i=0;i<accList.size();i++) {
			if(accList.get(i).getClientId().equals(id)) {
				System.out.println(accList.get(i));
			}
		}
		
	}
	
	public void loadAccData(String accData) {
		
		if(accData == null) {
			System.out.println("불러올 데이터가 없습니다.");return;
		}
		
		accList.clear();
		
		String[] temp = accData.split("\n");
		for(int i=0;i<temp.length;i++) {
			String[] info = temp[i].split("/");
			accList.add(new Account(Integer.parseInt(info[0]),info[1],info[2],Integer.parseInt(info[3])));
		}
		
	}
	
	private int checkMyAccount(String accNo,String id) {
		int idx = -1;
		boolean isCheck = false;
		for(int i=0;i<accList.size();i++) {
			if(accNo.equals(accList.get(i).getAccNumber())) {
				isCheck = true;
			}
		}
		if(isCheck) {
			for(int i=0;i<accList.size();i++) {
				if(accList.get(i).getClientId().equals(id) && accList.get(i).getAccNumber().equals(accNo)) {
					idx = i;
					return idx;
				}
			}
		}else {
			return -2;
		}
		return idx;
	}
	
}
