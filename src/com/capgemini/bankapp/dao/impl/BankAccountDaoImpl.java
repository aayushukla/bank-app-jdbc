package com.capgemini.bankapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.capgemini.bankapp.dao.BankAppDao;
import com.capgemini.bankapp.util.DbUtil;


public class BankAccountDaoImpl implements BankAppDao{

	@Override
	public double getBalance(long accountId) {
		String query= "SELECT account_balance FROM bankaccounts WHERE account_id"+accountId;
		double balance=0;
		
		try (Connection connection=DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result=statement.executeQuery())
		{
			balance=result.getDouble(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public void updateBalance(long accountId, double newBalance) {
		
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		String query="DELETE FROM bankaccounts WHERE accountid="+accountId;
		int result;
		try(Connection connection= DbUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			result =statement.executeUpdate();
			if(result==1)
				return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
}
