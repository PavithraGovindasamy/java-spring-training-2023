package bank;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that performs bank operations.
 */
public class Banking {
    /**
     * Main method that has the basic implementation of banking application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
    	  Bank abcBank = new AbcBank();
          Bank xyzBank = new XyzBank();
    
    	  ArrayList<Items> items = new ArrayList<>();
        items.add(new Items("Laptop", 5000.0));
        items.add(new Items("TV", 1000.0));
        items.add(new Items("Jewels", 3000.0));

        Scanner sc = SingletonScanner.getInstance();

        try {
            while (true) {
                System.out.println("Enter the bank:");
                System.out.println("1. ABC-BANK");
                System.out.println("2. XYZ-BANK");
                System.out.println("3. Exit");
                int bankChoice = sc.nextInt();

                if (bankChoice == 1) {
                
                    performBankOperations(abcBank, items);
                } else if (bankChoice == 2) {
                   
                    performBankOperations(xyzBank, items);
                } else if (bankChoice == 3) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    /**
     * Performs bank operations based on the selected bank.
     *
     * @param bank  The selected bank.
     * @param items The list of items.
     * @param sc    The Scanner object for user input.
     */
    public static void performBankOperations(Bank bank, ArrayList<Items> items) {
        int flag = 0;

        try {
        	   Scanner sc = SingletonScanner.getInstance();
            while (true) {
                System.out.println("Enter your choice: ");
                System.out.println("1. Bank Administrator");
                System.out.println("2. Customer");
                System.out.println("3. Logout");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Welcome Bank Administrator:");
                        System.out.println("Enter administrator password:");
                        String adminPassword = sc.next();

                        if (!adminPassword.equals("admin")) {
                            System.out.println("Incorrect administrator password.");
                            break;
                        }

                        while (flag == 0) {
                            System.out.println("Enter your option: ");
                            System.out.println("1. Display all customers");
                            System.out.println("2. Add new customer");
                            System.out.println("3. View All Issued Cards");
                            System.out.println("4. View Blocked Cards");
                            System.out.println("5. Issue Credit Card");
                            System.out.println("6. Close/Block Credit Card");
                            System.out.println("7. Logout");

                            int ch = sc.nextInt();

                            switch (ch) {
                                case 1:
                                    bank.displayCustomers();
                                    break;
                                case 2:
                                    bank.addCustomer();
                                    break;
                                case 3:
                                    bank.viewAllCreditCards();
                                    break;
                                case 4:
                                    bank.viewBlockedCreditCards();
                                    break;
                                case 5:
                                    bank.issueCreditCard();
                                    break;
                                case 6:
                                    bank.closeCreditCard();
                                    break;
                                case 7:
                                    bank.logout();
                                    flag = 1;
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                        }
                        flag = 0;
                        break;

                    case 2:
                        System.out.println("Welcome Customer:");
                        System.out.println("Enter your customer ID:");
                        int customerId = sc.nextInt();
                        System.out.println("Enter your password:");
                        String cusPassword = sc.next();

                        Customer existingCustomer = null;
                        for (Customer customer : bank.getCustomers()) {
                            if (customer.getId() == customerId && customer.getPassword().equals(cusPassword)) {
                                existingCustomer = customer;
                                break;
                            }
                        }

                        if (existingCustomer == null) {
                            System.out.println("Invalid credentials.");
                            break;
                        }

                        System.out.println("1. Apply for new Credit Card");
                        System.out.println("2. View Balance");
                        System.out.println("3. Close/Block Credit Card");
                        System.out.println("4. Spend");
                        System.out.println("5. Deposit");
                        System.out.println("6. Exit");

                        int customerChoice = sc.nextInt();
                        long cardNumber;
                        int cvv;
                        CreditCard creditCard;
                        switch (customerChoice) {
                            case 1:
                                if (existingCustomer.hasPendingCreditCard()) {
                                    System.out.println("You already have a pending credit card application.");
                                    break;
                                }
                                if (existingCustomer.getNumOfCards() >= Bank.MAX_CARDS) {
                                    System.out.println("Maximum card limit reached. You cannot apply for more credit cards.");
                                    break;
                                }
                                System.out.println("Apply for new Credit Card");
                                CreditCardApplication application = new CreditCardApplication(existingCustomer);
                                bank.getPendingApplications().add(application);
                                System.out.println("Credit card application submitted successfully.");
                                break;

                            case 2:
                                System.out.println("View Balance");
                                System.out.println("Enter the 16-digit card number:");
                                cardNumber = sc.nextLong();
                                System.out.println("Enter the four-digit CVV:");
                                cvv = sc.nextInt();
                                creditCard = existingCustomer.findCreditCard(cardNumber, cvv);
                                if (creditCard != null) {
                                    System.out.println("Balance: " + creditCard.getBalance());
                                } else {
                                    System.out.println("Credit card not found.");
                                }
                                break;

                            case 3:
                                bank.closeCreditCard();
                                break;

                            case 4:
                                System.out.println("Spend");
                                System.out.println("Enter the 16-digit card number:");
                                cardNumber = sc.nextLong();
                                System.out.println("Enter the four-digit CVV:");
                                cvv = sc.nextInt();
                                creditCard = existingCustomer.findCreditCard(cardNumber, cvv);
                                if (creditCard != null) {
                                    System.out.println("Select an item:");
                                    for (int i = 0; i < items.size(); i++) {
                                        System.out.println(i + 1 + ". " + items.get(i).getName());
                                    }
                                    int itemIndex = sc.nextInt();
                                    if (itemIndex > 0 && itemIndex <= items.size()) {
                                        Items selectedItem = items.get(itemIndex - 1);
                                        creditCard.spend(selectedItem.getPrice());
                                        System.out.println("Purchase successful. Updated balance: " + creditCard.getBalance());
                                    } else {
                                        System.out.println("Invalid item selection.");
                                    }
                                } else {
                                    System.out.println("Credit card not found.");
                                }
                                break;

                            case 5:
                                System.out.println("Deposit");
                                System.out.println("Enter the 16-digit card number:");
                                cardNumber = sc.nextLong();
                                System.out.println("Enter the four-digit CVV:");
                                cvv = sc.nextInt();
                                creditCard = existingCustomer.findCreditCard(cardNumber, cvv);
                                if (creditCard != null) {
                                    System.out.println("Enter the amount to deposit:");
                                    double amount = sc.nextDouble();
                                    creditCard.deposit(amount);
                                } else {
                                    System.out.println("Credit card not found.");
                                }
                                break;

                            case 6:
                                System.out.println("Exiting...");
                                return;

                            default:
                                System.out.println("Invalid option.");
                                break;
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
