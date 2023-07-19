package bank;

import java.util.Scanner;

/**
 * Class that implements the functionalities of the ABC bank.
 */
public class AbcBank extends Bank {
	@Override
	protected String getCardType() {
		Scanner sc = SingletonScanner.getInstance();
		System.out.println("Enter card type (Platinum, Gold):");
		String cardType = sc.next();
		return cardType;
	}
}
