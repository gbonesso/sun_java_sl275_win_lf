package banking.domain;

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

  private static final int MAX_CUSTOMERS = 10;
  private static final double SAVINGS_RATE = 3.5;

  private Customer[] customers;
  private int        numberOfCustomers;

  private Bank() {
    customers = new Customer[MAX_CUSTOMERS];
    numberOfCustomers = 0;
  }

  public void addCustomer(String f, String l) {
    int i = numberOfCustomers++;
    customers[i] = new Customer(f, l);
  }
  public Customer getCustomer(int customer_index) {
    return customers[customer_index];
  }
  public int getNumOfCustomers() {
    return numberOfCustomers;
  }
}

