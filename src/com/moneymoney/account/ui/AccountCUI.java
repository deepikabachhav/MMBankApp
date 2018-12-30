package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	private static SavingsAccountService savingsAccountService=new SavingsAccountServiceImpl();
	public static void start() {
		
		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");
			
			int choice = scanner.nextInt();
			
			performOperation(choice);
			
		} while(true);
	}

	private static void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			updateAccount();
			break;
		case 3:
			closeAccount();
			break;
		case 4:
			searchAccount();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			checkCurrentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			//sortMenu();
			break;
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
		
	}

	
	private static void searchAccount() {
		System.out.println("Search Account By: ");
		System.out.println("1. Account Number: ");
		System.out.println("2. Account Holder Name: ");
		System.out.println("3. Account Balance: ");
		System.out.println("Make your choice: ");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter Account Number you want to search: ");
			int accountNumber = scanner.nextInt();
			try {
				SavingsAccount searchByAccountNumber = savingsAccountService.getAccountById(accountNumber);
				System.out.println(searchByAccountNumber);
			} catch (ClassNotFoundException | SQLException| AccountNotFoundException e) {
				e.printStackTrace();
			}catch(Exception e1){
				e1.printStackTrace();
			}
		case 2:
			System.out.println("Enter Account Holder Name you want to search: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			try {
				SavingsAccount	searchByAccountHolderName = savingsAccountService.searchAccountByAccountHoldername(accountHolderName);
				System.out.println(searchByAccountHolderName);
			} catch (ClassNotFoundException | AccountNotFoundException
					| SQLException e) {
				e.printStackTrace();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter the Range of Account Balance: ");
			System.out.println("Enter Minimum Balance: ");
			double minimumBalance=scanner.nextDouble();
			System.out.println("Enter Maximum Balance: ");
			double maximumBalance=scanner.nextDouble();
			try {
				List<SavingsAccount> savingsAccounts = savingsAccountService.searchAccountByAccountBalance(minimumBalance, maximumBalance);
				for (SavingsAccount savingsAccount : savingsAccounts) {
					System.out.println(savingsAccount);
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			catch(Exception e1){
				e1.printStackTrace();
				
			}
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
			}
		}
	
	private static void updateAccount() {
		System.out.println("Enter Account Number whose details you want to update: ");
		int accountNumber = scanner.nextInt();
		SavingsAccount account= null;
		try {
			 account=savingsAccountService.getAccountById(accountNumber);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Which Details you want to update: ");
		System.out.println("1. Account Holder Name: ");
		System.out.println("2. Is salaried or Non-salaried account: ");
		System.out.println("3. update both Account Holder Name and salary: ");
		System.out.println("Make your choice: ");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			System.out.println("Enter new Account Holder Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			account.getBankAccount().setAccountHolderName(accountHolderName);
			try {
				SavingsAccount updateAccountByName=savingsAccountService.updateAccount(account);
				System.out.println(updateAccountByName);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n")?false:true;
			account.setSalary(salary);
			try {
				SavingsAccount updateAccountByisSalary = savingsAccountService.updateAccount(account);
				System.out.println(updateAccountByisSalary);
			} catch (ClassNotFoundException | SQLException| AccountNotFoundException e) {
				e.printStackTrace();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			break;	
		case 3:
			System.out.println("Enter new Account Holder Name: ");
			String newaccountHolderName = scanner.nextLine();
			newaccountHolderName = scanner.nextLine();
			System.out.println("Salaried?(y/n): ");
			boolean newsalary = scanner.next().equalsIgnoreCase("n")?false:true;
			account.getBankAccount().setAccountHolderName(newaccountHolderName);
			account.setSalary(newsalary);
			try {
				SavingsAccount updateAccountByBoth=savingsAccountService.updateAccount(account);
				System.out.println(updateAccountByBoth);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}catch(Exception e1){
				e1.printStackTrace();
			}
			break;	
		default:
			System.err.println("Invalid Choice!");
			break;
		}
	}
	
	private static void checkCurrentBalance() {
		System.out.println("Enter Account Number whose account balance to be checked: ");
		int accountNumber = scanner.nextInt();
		try {
			double checkAccount = savingsAccountService.checkCurrentBalance(accountNumber);
			System.out.println(" Your SavingsAccount current balance is :"+checkAccount);
		} catch (ClassNotFoundException | AccountNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}

	private static void closeAccount() {
		System.out.println("Enter Account Number to be closed: ");
		int accountNumber = scanner.nextInt();
		try {
			SavingsAccount closeAccount = savingsAccountService.deleteAccount(accountNumber);
			DBUtil.commit();
		} catch (ClassNotFoundException | AccountNotFoundException
				| SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*private static void sortMenu() {
		int sortBy=0;
		System.out.println("+++++Ways of Sorting+++++++");
		System.out.println("1. Account Number");
		System.out.println("2. Account Holder Name");
		System.out.println("3. Account Balance");
		int choice = scanner.nextInt();
		if(choice <=3){
			System.out.println("Sort Account Number By: ");
			System.out.println("1.Ascending order: ");
			System.out.println("2.Descending order: ");
			System.out.println("Make your choice: ");
			sortBy = scanner.nextInt();
		}
		switch (sortBy) {
		case 1:
			List<SavingsAccount> savingsAccount= savingsAccountService.sortByAscendingOrder(savingsAccounts);
			break;
		case 2:
			
			break;
		default:
			System.err.println("Invalid choice!");
			break;
		}
	}
	
*/
	private static void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void acceptInput(String type) {
		if(type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance=0.0;
			if(!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n")?false:true;
			createSavingsAccount(accountHolderName,accountBalance, salary);
		}
	}

	private static void createSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}



