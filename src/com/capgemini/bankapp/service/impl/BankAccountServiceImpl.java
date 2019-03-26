package com.capgemini.bankapp.service.impl;

import java.util.List;

import com.capgemini.bankapp.dao.BankAppDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

	BankAppDao bankappdao;

	public BankAccountServiceImpl() {
		bankappdao=new BankAccountDaoImpl(); 
	}
			
	

	@Override
	public double checkBalance(long accountId) {
		return bankappdao.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException {
		double balance = bankappdao.getBalance(accountId);
		if (balance >= amount) {
			balance -= amount;
			bankappdao.updateBalance(accountId, balance);
			return balance;
		} else
			throw new LowBalanceException("insufficient balance");

	}

	@Override
	public double deposit(long accountId, double amount) {
		double balance = bankappdao.getBalance(accountId);
		balance +=amount;
		bankappdao.updateBalance(accountId, balance);
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		return bankappdao.deleteBankAccount(accountId);
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws LowBalanceException {
		double newBalance = withdraw(fromAccount, amount);
		deposit(toAccount, amount);
		return newBalance;
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		return bankappdao.addNewBankAccount(account);
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankappdao.findAllBankAccounts();
	}



	@Override
	public BankAccount searchAccount(int accountId) {
		return bankappdao.findBankAccount(accountId);
		
	}

}
