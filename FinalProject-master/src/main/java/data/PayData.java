package data;

import javafx.beans.property.SimpleStringProperty;

public class PayData {
	private SimpleStringProperty paymentID;
	private SimpleStringProperty dueDate;
	private SimpleStringProperty payment;
	private SimpleStringProperty additonalPayment;
	private SimpleStringProperty interest;
	private SimpleStringProperty principle;
	private SimpleStringProperty balance;

	public PayData() {
		super();
		paymentID = new SimpleStringProperty("");
		dueDate = new SimpleStringProperty("");
		payment = new SimpleStringProperty("");
		additonalPayment = new SimpleStringProperty("");
		interest = new SimpleStringProperty("");
		principle = new SimpleStringProperty("");
		balance = new SimpleStringProperty("");
	}

	public PayData(String paymentID, String dueDate, String payment,
			String additonalPayment, String interest, String principle,
			String balance) {
		this();
		this.paymentID.set(paymentID);
		this.dueDate.set(dueDate);
		this.payment.set(payment);
		this.additonalPayment.set(additonalPayment);
		this.interest.set(interest);
		this.principle.set(principle);
		this.balance.set(balance);
	}

	public PayData(String balance) {
		this();
		this.balance.set(balance);
	}

	public String getPaymentID() {
		return paymentID.get();
	}

	public void setPaymentID(String paymentID) {
		this.paymentID.set(paymentID);
	}

	public String getDueDate() {
		return dueDate.get();
	}

	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}

	public String getPayment() {
		return payment.get();
	}

	public void setPayment(String payment) {
		this.payment.set(payment);
	}

	public String getAdditonalPayment() {
		return additonalPayment.get();
	}

	public void setAdditonalPayment(String additonalPayment) {
		this.additonalPayment.set(additonalPayment);
	}

	public String getInterest() {
		return interest.get();
	}

	public void setInterest(String interest) {
		this.interest.set(interest);
	}

	public String getPrinciple() {
		return principle.get();
	}

	public void setPrinciple(String principle) {
		this.principle.set(principle);
	}

	public String getBalance() {
		return balance.get();
	}

	public void setBalance(String balance) {
		this.balance.set(balance);
	}

	@Override
	public String toString() {
		return "PayData [paymentID=" + paymentID + ", dueDate=" + dueDate
				+ ", payment=" + payment + ", additonalPayment="
				+ additonalPayment + ", interest=" + interest + ", principle="
				+ principle + ", balance=" + balance + "]";
	}

}
