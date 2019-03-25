package com.capgemini.bankapp.service;

import com.capgemini.bankapp.exception.LowBalanceException;

public interface BankAccountService {
	
	
	public double checkBalance(long accountId);
	public boolean withdraw(long accountId, double amount)throws LowBalanceException;
	public boolean deposit(long accountId, double amount);
	public boolean deleteBankAccount(long accountId);
	
}
