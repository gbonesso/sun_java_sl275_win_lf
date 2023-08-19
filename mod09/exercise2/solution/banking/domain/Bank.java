package banking.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Bank class implements the Singleton design pattern, because
 * there should be only one bank object.
 */
public class Bank {
  /**
   * The class variable that holds the single Bank instance.
   */
  private static final Bank bankInstance = new Bank();
  public static Bank getBank() {
    return bankInstance;
  }

  private static final double SAVINGS_RATE = 3.5;

  private List customers;

  private Bank() {
    customers = new ArrayList();
  }

  public void addCustomer(String f, String l) {
    customers.add(new Customer(f, l));
  }
  public Customer getCustomer(int customer_index) {
    return (Customer) customers.get(customer_index);
  }
  public int getNumOfCustomers() {
    return customers.size();
  }
  public Iterator getCustomers() {
    return customers.iterator();
  }
}

