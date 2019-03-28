package com.capgemini.bankapp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.client.BankAccountCui;
import com.capgemini.bankapp.dao.BankAppDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountServiceImpl implements BankAccountService {

	BankAppDao bankappdao;
	
	static final Logger logger= Logger.getLogger(BankAccountCui.class);

	public BankAccountServiceImpl() {
		bankappdao = new BankAccountDaoImpl();
	}

	@Override
	public double checkBalance(long accountId) throws BankAccountNotFoundException {
		double balance = bankappdao.getBalance(accountId);
		if (balance >= 0)
			return balance;
		throw new BankAccountNotFoundException("Bankaccount does not exist");

	}

	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankappdao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Account does not exist");
		if (balance >= amount) {
			balance -= amount;
			bankappdao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else
			throw new LowBalanceException("insufficient balance");

	}
	
	public double withdrawForFundTransfer(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		double balance = bankappdao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("Account does not exist");
		if (balance >= amount) {
			balance -= amount;
			bankappdao.updateBalance(accountId, balance);
			return balance;
		} else
			throw new LowBalanceException("insufficient balance");

	}


	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		double balance = bankappdao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("account does not exit");
		balance += amount;
		bankappdao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException {
		boolean result=bankappdao.deleteBankAccount(accountId);
		if(result) {
			DbUtil.commit();
		return result;
		}
		throw new BankAccountNotFoundException("bANK ACCOUNT DOES NOT EXIST");
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount) throws BankAccountNotFoundException,LowBalanceException {
		try {
		double balance=withdrawForFundTransfer(fromAccount, amount);
		deposit(toAccount, amount);
		DbUtil.commit();
		return balance;
		}
		catch(LowBalanceException | BankAccountNotFoundException e) {
			logger.error("exception",e);
			DbUtil.rollback();
			throw e;
		}
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		boolean result= bankappdao.addNewBankAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankappdao.findAllBankAccounts();
	}

	@Override
	public BankAccount searchAccount(int accountId) throws BankAccountNotFoundException {
		BankAccount result= bankappdao.findBankAccount(accountId);
		if(result==null)
			throw new BankAccountNotFoundException("account not found");
		else 
			return result;
	}

	@Override
	public boolean updateBankAccount(int accountId, String updatedName, String updatedType){
		boolean result= bankappdao.updateBankAccountDetails(accountId, updatedName, updatedType);
		if(result)
			DbUtil.commit();
		return result;
	}

}
