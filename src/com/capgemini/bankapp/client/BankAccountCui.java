package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountCui {
	
	public static void main(String[] args) throws LowBalanceException {
		
	int choice;
	String accountHolderName;
	String accountType;
	double accountBalance;
	double accountAmount;
	int accountId;
	int accountId2;
	BankAccountService bankService = new BankAccountServiceImpl();

	try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
	{
		while (true)
		{
			System.out.println("1. Add New BankAccount\n2. Withdraw\n3. Deposit\n4. Fund Transfer");
			System.out.println("5. Delete BankAccount\n6. Display All BankAccount Details");
			System.out.println("7. Search BankAccount\n8. Check Balance\n9. Exit\n");

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
				System.exit(0);
			case 2:
				System.out.println("Enter account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter the amount you want to withdraw");
				accountAmount = Double.parseDouble(reader.readLine());
				System.out.println(bankService.withdraw(accountId, accountAmount));
				
			case 3:
				System.out.println("enter account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter the amount you want to deposit");
				accountAmount = Double.parseDouble(reader.readLine());
				System.out.println(bankService.deposit(accountId, accountAmount));
			case 4:
				System.out.println("Enter your account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println("Enter id where you want to send the money");
				accountId2 = Integer.parseInt(reader.readLine());
				System.out.println("enter the amount you want to transfer");
				accountAmount = Double.parseDouble(reader.readLine());
				System.out.println(bankService.fundTransfer(accountId, accountId2, accountAmount));
				
			case 5:
				System.out.println("Enter account id you wan to delete");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println(bankService.deleteBankAccount(accountId));
			case 6:
				bankService.findAllBankAccounts();
				break;
			case 7:
				System.out.println("Enter the accountID for account you want to search:");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println(bankService.searchAccount(accountId));
				break;
			case 8:
				System.out.println("enter account id");
				accountId = Integer.parseInt(reader.readLine());
				System.out.println(bankService.checkBalance(accountId));
				break;

			case 9:
				System.out.println("Thanks for banking with us.");
				System.exit(0);
			}
		}
	}catch(IOException e)
	{
		e.printStackTrace();
	}
}
}
