package bank;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that implements the functionalities of a bank.
 */
public class Bank {
	private ArrayList<Customer> customers;
	private ArrayList<CreditCard> creditCards;
	private ArrayList<CreditCardApplication> pendingApplications;
	static final int MAX_CARDS = 5;
	private static Bank instance; // Singleton instance

	public Bank() {
		this.customers = new ArrayList<>();
		this.creditCards = new ArrayList<>();
		this.pendingApplications = new ArrayList<>();
	}

	/**
	 * Returns the Singleton instance of the Bank.
	 *
	 * @return The Bank instance.
	 */
	public static Bank getInstance() {
		if (instance == null) {
			instance = new Bank();
		}
		return instance;
	}

	/**
	 * Returns the list of pending credit card applications.
	 *
	 * @return The list of pending credit card applications.
	 */
	public ArrayList<CreditCardApplication> getPendingApplications() {
		return pendingApplications;
	}

	/**
	 * Returns the list of customers.
	 *
	 * @return The list of customers.
	 */
	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	/**
	 * Displays the details of all customers.
	 */
	public void displayCustomers() {
		System.out.println("Bank - Customer details");
		for (Customer customer : customers)
			System.out.println(customer);
	}

	/**
	 * Adds a new customer to the bank.
	 */
	public void addCustomer() {
		Scanner sc = SingletonScanner.getInstance();
		System.out.println("Enter customer name:");
		String name = sc.next();
		System.out.println("Enter customer password:");
		String password = sc.next();
		int customerId = generateId();
		Customer newCustomer = new Customer(customerId, name, password);
		customers.add(newCustomer);

		System.out.println("Customer added successfully.");
	}

	/**
	 * Displays all issued credit cards.
	 */
	public void viewAllCreditCards() {
		System.out.println("Bank - View all issued cards");
		for (CreditCard card : creditCards)
			System.out.println(card);
	}

	/**
	 * Displays all blocked credit cards.
	 */
	public void viewBlockedCreditCards() {
		System.out.println("Bank - View blocked cards");
		for (CreditCard card : creditCards) {
			if (card.isBlocked()) {
				System.out.println(card);
			}
		}
	}

	/**
	 * Issues a new credit card to a customer.
	 */
	public void issueCreditCard() {
		Scanner sc = SingletonScanner.getInstance();
		if (pendingApplications.isEmpty()) {
			System.out.println("No pending applications.");
			return;
		}
		System.out.println("Enter the customer ID:");
		int customerId = sc.nextInt();
		CreditCardApplication applicationToProcess = findPendingApplication(customerId);
		if (applicationToProcess == null) {
			System.out.println("No pending application");
			return;
		}
		Customer customer = applicationToProcess.getCustomer();
		if (customer.getNumOfCards() >= MAX_CARDS) {
			System.out.println("Maximum card limit reached for the customer.");
			return;
		}

		System.out.println("Enter 16-digit card number:");
		long cardNo = sc.nextLong();
		if (!validateCardNumber(cardNo)) {
			System.out.println("Invalid card number. Must be a 16-digit number.");
			return;
		}

		System.out.println("Enter four-digit CVV:");
		int pin = sc.nextInt();
		if (!validateCVV(pin)) {
			System.out.println("Invalid CVV. Must be a 4-digit number.");
			return;
		}

		String cardType = getCardType();
		CreditCard card = new CreditCard(cardNo, pin, cardType);
		customer.addCreditCard(card);
		creditCards.add(card);
		System.out.println("Credit card issued successfully.");
		applicationToProcess.setApproved(true);
		pendingApplications.remove(applicationToProcess);
	}

	/**
	 * Closes or blocks a credit card.
	 */
	public void closeCreditCard() {
		Scanner sc = SingletonScanner.getInstance();
		System.out.println("Bank - Close/Block Credit Card");
		System.out.println("Enter the 16-digit card number:");
		long cardNo = sc.nextLong();
		if (!validateCardNumber(cardNo)) {
			System.out.println("Invalid card number. Must be a 16-digit number.");
			return;
		}

		System.out.println("Enter the four-digit CVV:");
		int pin = sc.nextInt();
		if (!validateCVV(pin)) {
			System.out.println("Invalid CVV. Must be a 4-digit number.");
			return;
		}

		CreditCard cardToClose = findCreditCard(cardNo, pin);
		if (cardToClose == null) {
			System.out.println("Credit card not found.");
		} else {
			cardToClose.setClosed(true);
			System.out.println("Credit card closed successfully.");
		}
	}

	/**
	 * Logs out of the bank.
	 */
	public void logout() {
		System.out.println("Bank - Logged out.");
	}

	/**
	 * Generates a unique ID for a customer.
	 *
	 * @return The unique ID.
	 */
	private int generateId() {
		return customers.size() + 1;
	}

	/**
	 * Finds a pending credit card application for a customer.
	 *
	 * @param customerId The customer ID.
	 * @return The pending credit card application, or null if not found.
	 */
	private CreditCardApplication findPendingApplication(int customerId) {
		for (CreditCardApplication application : pendingApplications) {
			if (application.getCustomer().getId() == customerId) {
				return application;
			}
		}
		return null;
	}

	/**
	 * Finds a credit card by card number and pin.
	 *
	 * @param cardNo The card number.
	 * @param pin    The card pin.
	 * @return The credit card, or null if not found.
	 */
	private CreditCard findCreditCard(long cardNo, int pin) {
		for (CreditCard card : creditCards) {
			if (card.getCardNumber() == cardNo && card.getPin() == pin) {
				return card;
			}
		}
		return null;
	}

	/**
	 * Validates a card number.
	 *
	 * @param cardNo The card number to validate.
	 * @return True if valid, false otherwise.
	 */
	public boolean validateCardNumber(long cardNo) {
		String cardNoString = String.valueOf(cardNo);
		return cardNoString.length() == 16;
	}

	/**
	 * Validates a CVV.
	 *
	 * @param cvv The CVV to validate.
	 * @return True if valid, false otherwise.
	 */
	public boolean validateCVV(int cvv) {
		String cvvString = String.valueOf(cvv);
		return cvvString.length() == 4;
	}

	/**
	 * Gets the card type.
	 * 
	 * @param sc
	 *
	 * @return The card type.
	 */
	protected String getCardType() {
		return "";
	}
}
