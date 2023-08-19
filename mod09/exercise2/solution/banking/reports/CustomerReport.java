package banking.reports;

import banking.domain.*;
import java.text.NumberFormat;
import java.util.Iterator;

public class CustomerReport {

  public void generateReport() {
    NumberFormat currency_format = NumberFormat.getCurrencyInstance();
    Bank         bank = Bank.getBank();
    Iterator     customers = bank.getCustomers();

    System.out.println("\t\t\tCUSTOMERS REPORT");
    System.out.println("\t\t\t================");

    while ( customers.hasNext() ) {
      Customer customer = (Customer) customers.next();
      Iterator accounts = customer.getAccounts();

      System.out.println();
      System.out.println("Customer: "
			 + customer.getLastName() + ", "
			 + customer.getFirstName());

      while ( accounts.hasNext() ) {
	Account account = (Account) accounts.next();
	String  account_type = "";

	// Determine the account type
	if ( account instanceof SavingsAccount ) {
	  account_type = "Savings Account";
	} else if ( account instanceof CheckingAccount ) {
	  account_type = "Checking Account";
	} else {
	  account_type = "Unknown Account Type";
	}

	// Print the current balance of the account
	System.out.println("    " + account_type + ": current balance is "
			 + currency_format.format(account.getBalance()));
      }
    }
  }

}
