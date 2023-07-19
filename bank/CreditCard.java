package bank;

/**
 * Class that implements credit card operations to spend/deposit money.
 */
class CreditCard {
    private long cardNumber;
    private int pin;
    private boolean blocked;
    protected double spendingLimit;
    private String type;

    /**
     * Constructs a CreditCard object with the specified card number, PIN, and card type.
     *
     * @param cardNumber The card number.
     * @param pin        The PIN.
     * @param cardType   The card type.
     */
    public CreditCard(long cardNumber, int pin, String cardType) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.blocked = false;
        if (cardType.equals("Platinum")) {
            this.spendingLimit = 1000000.0;
        } else if (cardType.equals("Diamond")) {
            this.spendingLimit = 500000.0;
        } else if (cardType.equals("Gold")) {
            this.spendingLimit = 250000.0;
        }
        this.type = cardType;
    }

    /**
     * Returns the card number.
     *
     * @return The card number.
     */
    public long getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the PIN.
     *
     * @return The PIN.
     */
    public int getPin() {
        return pin;
    }

    /**
     * Returns the current balance (spending limit) of the credit card.
     *
     * @return The current balance.
     */
    public double getBalance() {
        return spendingLimit;
    }

    /**
     * Checks if the credit card is blocked.
     *
     * @return true if the credit card is blocked, false otherwise.
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Sets the block status of the credit card.
     *
     * @param blocked true to block the credit card, false to unblock it.
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Deposits the specified amount to the credit card.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        if (blocked) {
            System.out.println("Cannot deposit. Credit card is blocked.");
            return;
        }

        spendingLimit += amount;
        System.out.println("Deposit successful. New balance: " + spendingLimit);
    }

    /**
     * Spends the specified amount using the credit card.
     *
     * @param  amount The amount to spend.
     */
    public void spend(double amount) {
        if (blocked) {
            System.out.println("Credit card is blocked.");
            return;
        }

        if (amount > spendingLimit) {
            System.out.println("Transaction declined. Insufficient balance.");
            return;
        }

        spendingLimit -= amount;
        System.out.println("Transaction successful. Remaining balance: " + spendingLimit);
    }

    /**
     * Sets the closed status of the credit card.
     *
     * @param  closed true to close the credit card, false to keep it open.
     */
    public void setClosed(boolean closed) {
        blocked = closed;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber=" + cardNumber +
                ", pin=" + pin +
                ", balance=" + spendingLimit +
                ", blocked=" + blocked +
                ", cardType=" + type +
                '}';
    }
}
