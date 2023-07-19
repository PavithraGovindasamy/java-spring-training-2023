package bank;

import java.util.Scanner;

/**
 * Class that implements the functionalities of the Xyz bank.
 */
public class XyzBank extends Bank {
	@Override
	protected String getCardType() {
		Scanner sc = SingletonScanner.getInstance();
		System.out.println("Enter card type (Diamond, Gold):");
		String cardType = sc.next();
		return cardType;
	}
}
