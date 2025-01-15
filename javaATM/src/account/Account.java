package account;
// 한 계정마다 계좌 3개까지 만들수 있음
public class Account {
	private int clientNo;
	private String clientId;
	private String accNumber;
	private int money;
	public int getClientNo() {
		return clientNo;
	}
	public void setClientNo(int clientNo) {
		this.clientNo = clientNo;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money += money;
	}
	Account(int clientNo, String clientId, String accNumber) {
		this.clientNo = clientNo;
		this.clientId = clientId;
		this.accNumber = accNumber;
	}
	Account(int clientNo, String clientId, String accNumber,int money) {
		this.clientNo = clientNo;
		this.clientId = clientId;
		this.accNumber = accNumber;
		this.money = money;
	}
	@Override
	public String toString() {
		return "Account [clientNo=" + clientNo + ", clientId=" + clientId + ", accNumber=" + accNumber + ", money="
				+ money + "]";
	}
	
}
