package bank;

import java.util.ArrayList;

/**
 * Class that represents a customer in the bank.
 */
public class Customer {
    private int id;
    private String name;
    private String password;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<CreditCardApplication> pendingApplications;
    private ArrayList<Bank> banks;
    private int ifscCode;

    /**
     * Constructs a Customer object with the specified ID, name, and password.
     *
     * @param id       The ID of the customer.
     * @param name     The name of the customer.
     * @param password The password of the customer.
     */
    public Customer(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.creditCards = new ArrayList<>();
        this.pendingApplications = new ArrayList<>();
        this.banks = new ArrayList<>();
    }

    public int getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(int ifscCode) {
        this.ifscCode = ifscCode;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public ArrayList<CreditCardApplication> getPendingApplications() {
        return pendingApplications;
    }

    public void addCreditCard(CreditCard card) {
        creditCards.add(card);
    }

    public void addPendingApplication(CreditCardApplication application) {
        pendingApplications.add(application);
    }

    public CreditCard findCreditCard(long cardNumber, int cvv) {
        for (CreditCard card : creditCards) {
            if (card.getCardNumber() == cardNumber && card.getPin() == cvv) {
                return card;
            }
        }
        return null;
    }

    public boolean hasPendingCreditCard() {
        return !pendingApplications.isEmpty();
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + " password " + password;
    }

    public int getNumOfCards() {
        return creditCards.size();
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public void addBank(Bank bank) {
        banks.add(bank);
    }
}
