package app.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import org.apache.poi.ss.formula.functions.FinanceLib;

import app.StudentCalc;
import data.PayData;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	@FXML
	private TextField LoanAmount;
	@FXML
	private TextField InterestRate;
	@FXML
	private TextField NbrOfYears;

	
	@FXML
	private Label lblAdditionalPayemnts;
	@FXML
	private Label lblTotalPayemnts;
	@FXML
	private Label lblTotalinterest;
	
	@FXML
	private DatePicker PaymentStartDate;
	
	@FXML
	private TableView<PayData> PayTableView;
	
	private ObservableList <PayData> datas = FXCollections.observableArrayList();
	private Map<String,Double> additionPay = new HashMap<String, Double>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PayTableView.setItems(datas);
		ObservableList<TableColumn<PayData, ?>> columns = PayTableView.getColumns();
		
		columns.get(0).setCellValueFactory(new PropertyValueFactory<>("paymentID"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<>("payment"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<>("additonalPayment"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<>("interest"));
		columns.get(5).setCellValueFactory(new PropertyValueFactory<>("principle"));
		columns.get(6).setCellValueFactory(new PropertyValueFactory<>("balance"));
		
		TableColumn<PayData,String> tableColumn = (TableColumn<PayData, String>) columns.get(3);
		
		tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PayData,String>>() {
			
			@Override
			public void handle(CellEditEvent<PayData, String> paramT) {
				String additonalPayment = paramT.getNewValue();
				String dueDate = paramT.getRowValue().getDueDate();
				if(additonalPayment.length()>0){
					additionPay.put(dueDate, Double.valueOf(additonalPayment));
					btnCalcLoan(null);
				}else {
					additionPay.remove(dueDate);
				}
			}
		});
		PayTableView.setEditable(true);
		PaymentStartDate.setEditable(false);
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {

		try {
			System.out.println("Amount: " + LoanAmount.getText());
			double dLoanAmount = Double.parseDouble(LoanAmount.getText());
			double rate = Double.parseDouble(InterestRate.getText());
			double year = Integer.parseInt(NbrOfYears.getText());
			System.out.println("Amount: " + dLoanAmount);	
			
			lblTotalPayemnts.setText("123");
			
			LocalDate localDate = PaymentStartDate.getValue();
 
			System.out.println(localDate);
			
			
			double r = rate / 12;
			double n = year * 12;
			double p = dLoanAmount;
			double f = 0;
			boolean t = false;
			double PMT = Double.parseDouble(String.format("%.2f",Math.abs(FinanceLib.pmt(r, n, p, f, t))));
			datas.clear();
			
			LocalDate nextlocalDate = localDate;
			datas.add(new PayData(LoanAmount.getText()));

			for (int i = 0; i < n; i++) {
				
				try {
					nextlocalDate=nextlocalDate.withDayOfMonth(localDate.getDayOfMonth());
				} catch (Exception e) {
					nextlocalDate = nextlocalDate.with(TemporalAdjusters.lastDayOfMonth());
				}

				
				double d= dLoanAmount * r;
				double principle= PMT-Double.parseDouble(String.format("%.2f",d));
				double additionpay=0;
				if(additionPay.get (nextlocalDate.toString())!=null){
					additionpay = additionPay.get (nextlocalDate.toString());
				}
				principle = Double.parseDouble(String.format("%.2f",principle));
				if (i==n-1 || dLoanAmount < PMT) {
					principle=dLoanAmount;
					dLoanAmount-=(principle+additionpay);
					datas.add(new PayData(String.valueOf(i+1), nextlocalDate.toString(), String.format("%.2f",d+principle),
							additionpay==0?"":String.format("%.2f",additionpay), String.format("%.2f",d),
									String.format("%.2f",principle+additionpay), String.format("%.2f",dLoanAmount)));
				}else {
					dLoanAmount-=(principle+additionpay);
					datas.add(new PayData(String.valueOf(i+1), nextlocalDate.toString(), String.format("%.2f",d+principle), 
							additionpay==0?"":String.format("%.2f",additionpay), String.format("%.2f",d), 
									String.format("%.2f",principle+additionpay), String.format("%.2f",dLoanAmount)));
				}
				dLoanAmount=Double.parseDouble(String.format("%.2f",dLoanAmount));
				if(dLoanAmount==0){
					break;
				}
				nextlocalDate = nextlocalDate.plusMonths(1);
			}
			
			
			double allAdditionPay = 0,totalPay=0;
			for (int i = 1; i < datas.size(); i++) {
				String payment = datas.get(i).getAdditonalPayment();
				if (payment!=null && payment.length()>0) {
					allAdditionPay+=Double.parseDouble(payment);
				}
				totalPay+=Double.parseDouble(datas.get(i).getInterest())+Double.parseDouble(datas.get(i).getPrinciple());
			}
			
			lblAdditionalPayemnts.setText(allAdditionPay+"");
			lblTotalPayemnts.setText(String.format("%.2f", totalPay));
			lblTotalinterest.setText(String.format("%.2f", totalPay-Double.parseDouble(LoanAmount.getText())));
			
		} catch (Exception e) {
			new Alert(AlertType.ERROR,e.toString()).showAndWait();
		}

	}
}
