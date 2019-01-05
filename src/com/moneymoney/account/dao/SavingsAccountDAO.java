package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	double checkCurrentBalance(int accountNumber) throws AccountNotFoundException, ClassNotFoundException, SQLException;
	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount deleteAccount(int accountNumber) throws  ClassNotFoundException, SQLException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	void commit() throws SQLException;
	SavingsAccount searchAccountByAccountHoldername(String accountHolderName) throws AccountNotFoundException, ClassNotFoundException, SQLException;
	List<SavingsAccount> searchAccountByAccountBalance(double minimumBalance,double maximumBalance) throws ClassNotFoundException, SQLException;
	SavingsAccount updateAccount(SavingsAccount account)throws ClassNotFoundException, SQLException,AccountNotFoundException;
	List<SavingsAccount> sort(int choice, int sortBy)throws ClassNotFoundException, SQLException;
}
