package bank;

/**
 * Class that represents a credit card application.
 */
class CreditCardApplication {
    private Customer customer;
    private boolean approved;

    /**
     * Constructs a CreditCardApplication object with the specified customer.
     *
     * @param customer The customer associated with the application.
     */
    public CreditCardApplication(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the customer associated with the application.
     *
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Checks if the application is approved.
     *
     * @return true if the application is approved, false otherwise.
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the approval status of the application.
     *
     * @param approved true to approve the application, false to reject it.
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "CreditCardApplication{" +
                "customer=" + customer +
                ", approved=" + approved +
                '}';
    }
}
