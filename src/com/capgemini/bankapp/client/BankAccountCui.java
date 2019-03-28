package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountCui {
	
	static final Logger logger= Logger.getLogger(BankAccountCui.class);
	
	public static void main(String[] args) throws LowBalanceException{

	int choice;
	String accountHolderName;
	String accountType;
	double accountBalance;
	double accountAmount;
	int accountId;
	int accountId2;
	String accountUpdatedType;
	String accountUpdatedName;
	BankAccountService bankService = new BankAccountServiceImpl();

	try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
	{
		while (true)
		{
			System.out.println("1. Add New BankAccount\n2. Withdraw\n3. Deposit\n4. Fund Transfer");
			System.out.println("5. Delete BankAccount\n6. Display All BankAccount Details");
			System.out.println("7. Search BankAccount\n8. Check Balance\n9. Exit\n10. Update query");

			System.out.print("Please enter your choice = ");
			choice = Integer.parseInt(reader.readLine());

			switch (choice)
			{

			case 1:
				System.out.println("Enter account holder name: ");
				accountHolderName = reader.readLine();
				System.out.println("Enter account type: ");
				accountType = reader.readLine();
				System.out.println("Enter account balance: ");
				accountBalance = Double.parseDouble(reader.readLine());
				BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);
				if (bankService.addNewBankAccount(account))
					System.out.println("Account created successfully...\n");
				else
					System.out.println("failed to create new account...\n");
				break;
			case 2:
				System.out.println("Enter account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter the amount you want to withdraw");
				accountAmount = Double.parseDouble(reader.readLine());
				try {
					System.out.println(bankService.withdraw(accountId, accountAmount));
				} catch(LowBalanceException e) {
					logger.error("excepetion",e);
				} catch (BankAccountNotFoundException e1) {
					logger.error("excepetion",e1);
				}
				break;
				
			case 3:
				System.out.println("enter account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter the amount you want to deposit");
				accountAmount = Double.parseDouble(reader.readLine());
				try {
					System.out.println(bankService.deposit(accountId, accountAmount));
				} catch (BankAccountNotFoundException e1) {
					logger.error("excepetion",e1);
				}
				break;
			case 4:
				System.out.println("Enter your account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter id where you want to send the money");
				accountId2 = Integer.parseInt(reader.readLine());
				System.out.println("enter the amount you want to transfer");
				accountAmount = Double.parseDouble(reader.readLine());
				try {
					System.out.println(bankService.fundTransfer(accountId, accountId2, accountAmount));
				} catch (BankAccountNotFoundException e2) {
					logger.error("excepetion",e2);
				}
				break;
			case 5:
				System.out.println("Enter account id you wan to delete");
				accountId = Integer.parseInt(reader.readLine());
				try {
					System.out.println(bankService.deleteBankAccount(accountId));
				} catch (BankAccountNotFoundException e1) {
					// TODO Auto-generated catch block
					logger.error("excepetion",e1);
				}
				break;
			case 6:
				bankService.findAllBankAccounts();
				break;
			case 7:
				System.out.println("Enter the accountID for account you want to search:");
				accountId = Integer.parseInt(reader.readLine());
				try {
					System.out.println(bankService.searchAccount(accountId));
				} catch (BankAccountNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 8:
				System.out.println("enter account id");
				accountId = Integer.parseInt(reader.readLine());
				try {
					System.out.println(bankService.checkBalance(accountId));
				} catch (BankAccountNotFoundException e) {
					logger.error("excepetion",e);
				}
				break;

			case 9:
				System.out.println("Thanks for banking with us.");
				System.exit(0);
				break;
			case 10:
				System.out.println("Enter the account you want to update");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter updated name");
				accountUpdatedName = reader.readLine();
				System.out.println("Enter update account type");
				accountUpdatedType = reader.readLine();
					if(bankService.updateBankAccount(accountId, accountUpdatedName, accountUpdatedType))
						System.out.println("Succesfully changed");
					else
						System.out.println("Failed to update");
					break;
			}
		}
	}
	catch(IOException e)
	{
		
		//e.printStackTrace();
	}
}
}
