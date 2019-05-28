package pkgUT;

import static org.junit.Assert.assertEquals;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.junit.Test;

public class TestCalculation {

	@Test
	public void testPMT() {
		double PMT;
		double r = 0.07 / 12;
		double n = 15 * 12;
		double p = 200000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));

		double PMTExpected = 1797.66;

		assertEquals(PMTExpected, PMT, 0.01);

	}

	@Test
	public void testTotalPay1() {
		double PMT;
		double r = 0.07 / 12;
		double n = 15 * 12;
		double p = 200000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));

		double PMTExpected = 1797.66;
		assertEquals(PMTExpected, PMT, 0.01);

		double dLoanAmount = p;
		PMT=PMTExpected;
		double TotalPayExpected = 323577.84;

		for (int i = 0; i < n; i++) {

			double d = dLoanAmount * r;
			double principle = PMT - Double.parseDouble(String.format("%.2f", d));
			principle = Double.parseDouble(String.format("%.2f", principle));//format
			if (i==n-1) {
				principle = dLoanAmount;
				dLoanAmount -= principle;
				double totalPay = PMT * i + d + principle;
				assertEquals(TotalPayExpected, totalPay, 0.01);

			} else {
				dLoanAmount -= principle;
			}
			dLoanAmount = Double.parseDouble(String.format("%.2f", dLoanAmount));//format
		}

	}
	@Test
	public void testTotalPay2() {
		double PMT;
		double r = 0.07 / 12;
		double n = 10 * 12;
		double p = 200000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));

		double PMTExpected = 2322.17;
		assertEquals(PMTExpected, PMT, 0.01);

		double dLoanAmount = p;
		PMT = PMTExpected;
		double TotalPayExpected = 278660.38;
		
		for (int i = 0; i < n; i++) {
			
			double d = dLoanAmount * r;
			double principle = PMT - Double.parseDouble(String.format("%.2f", d));
			principle = Double.parseDouble(String.format("%.2f", principle));//format
			if (i==n-1) {
				principle = dLoanAmount;
				dLoanAmount -= principle;
				double totalPay = PMT * i + d + principle;
				assertEquals(TotalPayExpected, totalPay, 0.01);
				
			} else {
				dLoanAmount -= principle;
			}
			dLoanAmount = Double.parseDouble(String.format("%.2f", dLoanAmount));//format
		}
		
	}
	@Test
	public void testTotalPay3() {
		double PMT;
		double r = 0.07 / 12;
		double n = 5 * 12;
		double p = 100000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));

		double PMTExpected = 1980.12;
		assertEquals(PMTExpected, PMT, 0.01);

		double dLoanAmount = p;
		PMT = PMTExpected;
		double TotalPayExpected = 118807.22;

		for (int i = 0; i < n; i++) {
			
			double d = dLoanAmount * r;
			double principle = PMT - Double.parseDouble(String.format("%.2f", d));
			principle = Double.parseDouble(String.format("%.2f", principle));//format
			if (i==n-1) {
				principle = dLoanAmount;
				dLoanAmount -= principle;
				double totalPay = PMT * i + d + principle;
				assertEquals(TotalPayExpected, totalPay, 0.01);
				
			} else {
				dLoanAmount -= principle;
			}
			dLoanAmount = Double.parseDouble(String.format("%.2f", dLoanAmount));//format
		}
		
	}

}
