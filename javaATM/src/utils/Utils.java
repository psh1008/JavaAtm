package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import account.AccountDAO;
import client.ClientDAO;

public class Utils {
	private static final String CUR_PATH = System.getProperty("user.dir") + "\\src\\"+Utils.class.getPackageName()+"\\";
	private static final String filePathAcc = CUR_PATH + "account.txt";
	private static final String filePathClient = CUR_PATH + "client.txt";
	private static File fileAcc = new File(filePathAcc);
	private static File fileClient = new File(filePathClient);
	private static Utils utils;
	Scanner sc = new Scanner(System.in);
	public static Utils getInstance() {
		if(utils == null) utils = new Utils();
		return utils;
	}
	
	public String loadAccDataToFile() {
		
		String dataReader = "";

		try (FileReader fr = new FileReader(filePathAcc); BufferedReader br = new BufferedReader(fr);) {
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				dataReader += line + "\n";
			}
			dataReader = dataReader.substring(0, dataReader.length() - 1);
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다");
		} catch (IOException e1) {
			System.out.println("파일 읽기 실패");
		} catch(Exception e) {
			return null;
		}
		
		return dataReader;
	}
	public String loadClientDataToFile() {
		String dataReader = "";

		try (FileReader fr = new FileReader(filePathClient); BufferedReader br = new BufferedReader(fr);) {
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				dataReader += line + "\n";
			}
			dataReader = dataReader.substring(0, dataReader.length() - 1);
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다");
		} catch (IOException e1) {
			System.out.println("파일 읽기 실패");
		} catch (Exception e) {
			return null;
		}
		
		return dataReader;
	}
	
	public void saveDataToFile(AccountDAO accDAO,ClientDAO cDAO) {
		
		String accList = accDAO.getAccList();
		String cList = cDAO.getCList();
		try(FileWriter fw = new FileWriter(filePathAcc)) {
			accList = accList.substring(0,accList.length()-1);
			fw.write(accList);
			System.out.println("파일 저장 성공");
		}catch(Exception e) {
			System.out.println("파일 저장 실패");
		}
		try(FileWriter fw = new FileWriter(filePathClient)) {
			cList = cList.substring(0,cList.length()-1);
			fw.write(cList);
			System.out.println("파일 저장 성공");
		}catch(Exception e) {
			System.out.println("파일 저장 실패");
		}
		
		
	}
	
	public String getAccNo(String msg) {
		String data = "";
		while(true) {
			System.out.println(msg);
			data = sc.next(); 
			
			String accPattern = "^\\d{4}-\\d{4}-\\d{4}";
			
			Pattern pattern = Pattern.compile(accPattern);
			Matcher matcher = pattern.matcher(data); 
			if(!matcher.matches()) {
				System.out.println("숫자4개-숫자4개-숫자4개 로 입력해주세요");
				continue;
			}
			return data;
		}
	}
	
	public String getStringValue(String msg) {
		System.out.println(msg);
		String val = sc.next();
		return val;
	}
	
	public int getIntValue(String msg,int start,int end) {
		int value  = 0;
		while (true) {
			System.out.println(msg);
			try {
				int sel = sc.nextInt();

				if (sel < start || sel > end) {
					System.out.printf("%d - %d 사이의 값 입력 \n", start, end);
					continue;
				}
				value = sel;
			} catch (InputMismatchException e) {
				System.out.println("정수를 입력해 주세요");
				continue;
			} finally {
				sc.nextLine();
			}
			break;
		}
		return value;
	}
}
