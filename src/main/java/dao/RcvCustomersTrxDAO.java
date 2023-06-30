package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.RcvCustomerTrx;
import pojo.RcvEbTransmissionLog;
import pojo.RcvInvoice;
import pojo.RcvMemo;
import util.UtilPOS;

public class RcvCustomersTrxDAO {
	
	public static RcvCustomerTrx getRow ( long transactionId ) {
	    Connection conn =  null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean dataFound = false;
	    RcvCustomerTrx o;
	    java.util.Date d = null;    
	    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	    try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }

	        StringBuffer buffer = new StringBuffer();

            buffer.append("select x.ADDRESS1, x.ADDRESS2, x.ADDRESS3, x.AMOUNT,");
            buffer.append(" x.APPLIED_AMOUNT, x.APPLYING_AMOUNT, x.ARCHING_SHEET_ID, x.AREA_CODE,");
            buffer.append(" x.ATTRIBUTE1, x.ATTRIBUTE2, x.ATTRIBUTE3, x.ATTRIBUTE4,");
            buffer.append(" x.ATTRIBUTE5, x.CANCELLATED_BY, x.CANCELLATED_ON, x.CANCEL_COMMENTS,");
            buffer.append(" x.CANCEL_JRNL_ID, x.CANCEL_REASON_ID, x.CASH_CONTROL_ID, x.CASH_REGISTER_ID,");
            buffer.append(" x.COLLECTIONS_AMOUNT, x.COLLECT_GROUP, x.COLLECT_METHOD, x.COMMENTS,");
            buffer.append(" x.COST_CENTER, x.COST_CENTER_ID, x.CREATED_BY, to_char(x.CREATED_ON,'dd/mm/yyyy hh24:mi:ss') CREATED_ON");
            buffer.append(" x.CURRENCY_ID, x.CUSTOMER_CCID, x.CUSTOMER_ID, x.CUST_SITE_ID,");
            buffer.append(" x.DISCOUNT_AMT, x.DISTRIBUTION_AMT, x.DUE_DATE, x.DUE_DAY_OF_MONTH,");
            buffer.append(" x.EXCEMPT_AMOUNT, x.EXCHANGE_RATE, x.EXPENSES_CONCEPT_ID, x.FISCAL_PERIOD_ID,");
            buffer.append(" x.FISCAL_YEAR_ID, x.GAINED_COUPONS_QTY, x.GAINED_POINTS_QTY, x.GLE_JOURNAL_ID,");
            buffer.append(" x.IDENTIFIER, x.INSTALLMENTS_QTY, x.INST_DAYS_INTERVAL, x.INVOICE_ID,");
            buffer.append(" x.INVOICE_STAMP_ID, x.INV_INTFACE_ID, x.ISSUE_JRNL_ID, x.ISSUE_POINT_ID,");
            buffer.append(" x.LAST_PAYMENT_DATE, x.LC_AMOUNT, x.LC_APPLIED_AMOUNT, x.LC_APPLYING_AMOUNT,");
            buffer.append(" x.LC_COLLECT_AMOUNT, x.MODIFIED_BY, to_char(x.MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON, x.ORG_ID,");
            buffer.append(" x.PHONE1, x.PHONE2, x.PHONE3, x.POS_TRANSACTION_ID,");
            buffer.append(" x.PRICES_LIST_ID, x.PRINTED_BY, to_char(x.PRINTING_DATE, 'dd/mm/yyyy hh24:mi:ss') PRINTING_DATE, x.PROGRAM,");
            buffer.append(" x.PROJECT_ID, x.RA_CANCEL_ID, x.RA_COLLECT_ID, x.RA_JOURNAL_ID,");
            buffer.append(" x.RECEIVABLES_TYPE, x.REF_NUMBER, x.RESIDUAL_AMOUNT, x.SALESMAN_ID,");
            buffer.append(" x.SALES_ORDER_ID, x.SECTION_ID, x.SITE_ID, x.SLE_CANCEL_ID,");
            buffer.append(" x.SLE_COST_CANC_ID, x.SLE_COST_ID, x.SLE_SALES_ID, x.STAMP_ID,");
            buffer.append(" x.STATUS, x.TASK_ID, x.TAX10_AMT, x.TAX5_AMT,");
            buffer.append(" x.TAXABLE10_AMT, x.TAXABLE5_AMT, x.TAXABLE_AMOUNT, x.TAX_AMOUNT,");
            buffer.append(" x.TAX_PAYER_NAME, x.TAX_PAYER_NUMBER, x.TRX_CONDITION, x.TRX_VERSION,");
            buffer.append(" x.TX_BASE_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TX_NUMBER, x.TX_TYPE_ID,");
            buffer.append(" x.UNIT_ID, x.WAREHOUSE_ID, ");
            buffer.append(" t.NAME TX_TYPE_NAME, s.NAME SITE_NAME");
            buffer.append(" from FND_SITES s,");
            buffer.append(" RCV_INCOICE_TYPES t,");
            buffer.append(" RCV_CUSTOMERS_TRX");
	        
            buffer.append(" from FND_SITES s,");
            buffer.append(" RCV_INVOICE_TYPES t,");
            buffer.append(" RCV_CUSTOMERS_TRX x");
            buffer.append(" where s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
            buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
            buffer.append(" and x.IDENTIFIER = ?");

            ps = conn.prepareStatement(buffer.toString());
	        ps.setLong(1, transactionId);
	        rs = ps.executeQuery();

	        o = new RcvCustomerTrx();

	        if (rs.next()) {
                dataFound = true;
                o.setAddress1(rs.getString("ADDRESS1"));
                o.setAddress2(rs.getString("ADDRESS2"));
                o.setAddress3(rs.getString("ADDRESS3"));
                o.setAmount(rs.getDouble("AMOUNT"));
                o.setAppliedAmount(rs.getDouble("APPLIED_AMOUNT"));
                o.setApplyingAmount(rs.getDouble("APPLYING_AMOUNT"));
                o.setArchingSheetId(rs.getLong("ARCHING_SHEET_ID"));
                o.setAreaCode(rs.getString("AREA_CODE"));
                o.setAttribute1(rs.getString("ATTRIBUTE1"));
                o.setAttribute2(rs.getString("ATTRIBUTE2"));
                o.setAttribute3(rs.getString("ATTRIBUTE3"));
                o.setAttribute4(rs.getString("ATTRIBUTE4"));
                o.setAttribute5(rs.getString("ATTRIBUTE5"));
                o.setCancellatedBy(rs.getString("CANCELLATED_BY"));
                if (rs.getString("CANCELLATED_ON") != null) {
                    d = sdf.parse(rs.getString("CANCELLATED_ON"));
                    o.setCancellatedOn(d);
                }
                o.setCancelComments(rs.getString("CANCEL_COMMENTS"));
                o.setCancelJrnlId(rs.getLong("CANCEL_JRNL_ID"));
                o.setCancelReasonId(rs.getLong("CANCEL_REASON_ID"));
                o.setCashControlId(rs.getLong("CASH_CONTROL_ID"));
                o.setCashRegisterId(rs.getLong("CASH_REGISTER_ID"));
                o.setCollectionsAmount(rs.getDouble("COLLECTIONS_AMOUNT"));
                o.setCollectGroup(rs.getString("COLLECT_GROUP"));
                o.setCollectMethod(rs.getString("COLLECT_METHOD"));
                o.setComments(rs.getString("COMMENTS"));
                o.setCostCenter(rs.getString("COST_CENTER"));
                o.setCostCenterId(rs.getLong("COST_CENTER_ID"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                d = sdf.parse(rs.getString("CREATED_ON"));
                o.setCreatedOn(d);
                o.setCurrencyId(rs.getLong("CURRENCY_ID"));
                o.setCustomerCcid(rs.getLong("CUSTOMER_CCID"));
                o.setCustomerId(rs.getLong("CUSTOMER_ID"));
                o.setCustSiteId(rs.getLong("CUST_SITE_ID"));
                o.setDiscountAmt(rs.getDouble("DISCOUNT_AMT"));
                o.setDistributionAmt(rs.getDouble("DISTRIBUTION_AMT"));
                if (rs.getString("DUE_DATE") != null) {
                    d = sdf.parse(rs.getString("DUE_DATE"));
                    o.setDueDate(d);
                }
                o.setDueDayOfMonth(rs.getInt("DUE_DAY_OF_MONTH"));
                o.setExcemptAmount(rs.getDouble("EXCEMPT_AMOUNT"));
                o.setExchangeRate(rs.getDouble("EXCHANGE_RATE"));
                o.setExpensesConceptId(rs.getLong("EXPENSES_CONCEPT_ID"));
                o.setFiscalPeriodId(rs.getLong("FISCAL_PERIOD_ID"));
                o.setFiscalYearId(rs.getLong("FISCAL_YEAR_ID"));
                o.setGainedCouponsQty(rs.getInt("GAINED_COUPONS_QTY"));
                o.setGainedPointsQty(rs.getLong("GAINED_POINTS_QTY"));
                o.setGleJournalId(rs.getLong("GLE_JOURNAL_ID"));
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setInstallmentsQty(rs.getInt("INSTALLMENTS_QTY"));
                o.setInstDaysInterval(rs.getInt("INST_DAYS_INTERVAL"));
                o.setInvoiceId(rs.getLong("INVOICE_ID"));
                o.setInvoiceStampId(rs.getLong("INVOICE_STAMP_ID"));
                o.setInvIntfaceId(rs.getLong("INV_INTFACE_ID"));
                o.setIssueJrnlId(rs.getLong("ISSUE_JRNL_ID"));
                o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));
                if (rs.getString("LAST_PAYMENT_DATE") != null) {
                    d = sdf.parse(rs.getString("LAST_PAYMENT_DATE"));
                    o.setLastPaymentDate(d);
                }
                o.setLcAmount(rs.getDouble("LC_AMOUNT"));
                o.setLcAppliedAmount(rs.getDouble("LC_APPLIED_AMOUNT"));
                o.setLcApplyingAmount(rs.getDouble("LC_APPLYING_AMOUNT"));
                o.setLcCollectAmount(rs.getDouble("LC_COLLECT_AMOUNT"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                if (rs.getString("MODIFIED_ON") != null) {
                    d = sdf.parse(rs.getString("MODIFIED_ON"));
                    o.setModifiedOn(d);
                }
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setPhone1(rs.getString("PHONE1"));
                o.setPhone2(rs.getString("PHONE2"));
                o.setPhone3(rs.getString("PHONE3"));
                o.setPosTransactionId(rs.getLong("POS_TRANSACTION_ID"));
                o.setPricesListId(rs.getLong("PRICES_LIST_ID"));
                o.setPrintedBy(rs.getString("PRINTED_BY"));
                if (rs.getString("PRINTING_DATE") != null) {
                    d = sdf.parse(rs.getString("PRINTING_DATE"));
                    o.setPrintingDate(d);
                }
                o.setProgram(rs.getString("PROGRAM"));
                o.setProjectId(rs.getLong("PROJECT_ID"));
                o.setRaCancelId(rs.getLong("RA_CANCEL_ID"));
                o.setRaCollectId(rs.getLong("RA_COLLECT_ID"));
                o.setRaJournalId(rs.getLong("RA_JOURNAL_ID"));
                o.setReceivablesType(rs.getString("RECEIVABLES_TYPE"));
                o.setRefNumber(rs.getString("REF_NUMBER"));
                o.setResidualAmount(rs.getDouble("RESIDUAL_AMOUNT"));
                o.setSalesmanId(rs.getLong("SALESMAN_ID"));
                o.setSalesOrderId(rs.getLong("SALES_ORDER_ID"));
                o.setSectionId(rs.getLong("SECTION_ID"));
                o.setSiteId(rs.getLong("SITE_ID"));
                o.setSleCancelId(rs.getLong("SLE_CANCEL_ID"));
                o.setSleCostCancId(rs.getLong("SLE_COST_CANC_ID"));
                o.setSleCostId(rs.getLong("SLE_COST_ID"));
                o.setSleSalesId(rs.getLong("SLE_SALES_ID"));
                o.setStampId(rs.getLong("STAMP_ID"));
                o.setStatus(rs.getString("STATUS"));
                o.setTaskId(rs.getLong("TASK_ID"));
                o.setTax10Amt(rs.getDouble("TAX10_AMT"));
                o.setTax5Amt(rs.getDouble("TAX5_AMT"));
                o.setTaxable10Amt(rs.getDouble("TAXABLE10_AMT"));
                o.setTaxable5Amt(rs.getDouble("TAXABLE5_AMT"));
                o.setTaxableAmount(rs.getDouble("TAXABLE_AMOUNT"));
                o.setTaxAmount(rs.getDouble("TAX_AMOUNT"));
                o.setTaxPayerName(rs.getString("TAX_PAYER_NAME"));
                o.setTaxPayerNumber(rs.getString("TAX_PAYER_NUMBER"));
                o.setTrxCondition(rs.getString("TRX_CONDITION"));
                o.setTrxVersion(rs.getInt("TRX_VERSION"));
                o.setTxBaseNumber(rs.getLong("TX_BASE_NUMBER"));
                d = sdf.parse(rs.getString("TX_DATE"));
                o.setTxDate(d);
                o.setTxNumber(rs.getString("TX_NUMBER"));
                o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.setWarehouseId(rs.getLong("WAREHOUSE_ID"));
	        }
	        if (dataFound == true) {
	            return o;
	        } else {
	            return null;
	        }

	    } catch (Exception e) {
	    	e.printStackTrace();
	        e.printStackTrace();
	        return null;
	    } finally {
	        Util.closeResultSet(rs);
	        Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
	    }
	}

	public static ArrayList<RcvCustomerTrx> getList ( long siteId, 
			long txTypeId, 
			long customerId, 
			java.util.Date fromDate, 
			java.util.Date toDate, 
			String transactionNo ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ArrayList<RcvCustomerTrx> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();

			buffer.append("select x.ADDRESS1, x.ADDRESS2, x.ADDRESS3, x.AMOUNT,");
			buffer.append(" x.APPLIED_AMOUNT, x.APPLYING_AMOUNT, x.ARCHING_SHEET_ID, x.AREA_CODE,");
			buffer.append(" x.ATTRIBUTE1, x.ATTRIBUTE2, x.ATTRIBUTE3, x.ATTRIBUTE4,");
			buffer.append(" x.ATTRIBUTE5, x.CANCELLATED_BY, to_char(x.CANCELLATED_ON, 'dd/mm/yyyy hh24:mi:ss') CANCELLATED_ON, x.CANCEL_COMMENTS,");
			buffer.append(" x.CANCEL_JRNL_ID, x.CANCEL_REASON_ID, x.CASH_CONTROL_ID, x.CASH_REGISTER_ID,");
			buffer.append(" x.COLLECTIONS_AMOUNT, x.COLLECT_GROUP, x.COLLECT_METHOD, x.COMMENTS,");
			buffer.append(" x.COST_CENTER, x.COST_CENTER_ID, x.CREATED_BY, to_char(x.CREATED_ON,'dd/mm/yyyy hh24:mi:ss') CREATED_ON,");
			buffer.append(" x.CURRENCY_ID, x.CUSTOMER_CCID, x.CUSTOMER_ID, x.CUST_SITE_ID,");
			buffer.append(" x.DISCOUNT_AMT, x.DISTRIBUTION_AMT, x.DUE_DATE, x.DUE_DAY_OF_MONTH,");
			buffer.append(" x.EXCEMPT_AMOUNT, x.EXCHANGE_RATE, x.EXPENSES_CONCEPT_ID, x.FISCAL_PERIOD_ID,");
			buffer.append(" x.FISCAL_YEAR_ID, x.GAINED_COUPONS_QTY, x.GAINED_POINTS_QTY, x.GLE_JOURNAL_ID,");
			buffer.append(" x.IDENTIFIER, x.INSTALLMENTS_QTY, x.INST_DAYS_INTERVAL, x.INVOICE_ID,");
			buffer.append(" x.INVOICE_STAMP_ID, x.INV_INTFACE_ID, x.ISSUE_JRNL_ID, x.ISSUE_POINT_ID,");
			buffer.append(" x.LAST_PAYMENT_DATE, x.LC_AMOUNT, x.LC_APPLIED_AMOUNT, x.LC_APPLYING_AMOUNT,");
			buffer.append(" x.LC_COLLECT_AMOUNT, x.MODIFIED_BY, to_char(x.MODIFIED_ON, 'dd/mm/yyyy hh24:mi:ss') MODIFIED_ON, x.ORG_ID,");
			buffer.append(" x.PHONE1, x.PHONE2, x.PHONE3, x.POS_TRANSACTION_ID,");
			buffer.append(" x.PRICES_LIST_ID, x.PRINTED_BY, to_char(x.PRINTING_DATE, 'dd/mm/yyyy hh24:mi:ss') PRINTING_DATE, x.PROGRAM,");
			buffer.append(" x.PROJECT_ID, x.RA_CANCEL_ID, x.RA_COLLECT_ID, x.RA_JOURNAL_ID,");
			buffer.append(" x.RECEIVABLES_TYPE, x.REF_NUMBER, x.RESIDUAL_AMOUNT, x.SALESMAN_ID,");
			buffer.append(" x.SALES_ORDER_ID, x.SECTION_ID, x.SITE_ID, x.SLE_CANCEL_ID,");
			buffer.append(" x.SLE_COST_CANC_ID, x.SLE_COST_ID, x.SLE_SALES_ID, x.STAMP_ID,");
			buffer.append(" x.STATUS, x.TASK_ID, x.TAX10_AMT, x.TAX5_AMT,");
			buffer.append(" x.TAXABLE10_AMT, x.TAXABLE5_AMT, x.TAXABLE_AMOUNT, x.TAX_AMOUNT,");
			buffer.append(" x.TAX_PAYER_NAME, x.TAX_PAYER_NUMBER, x.TRX_CONDITION, x.TRX_VERSION,");
			buffer.append(" x.TX_BASE_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TX_NUMBER, x.TX_TYPE_ID,");
			buffer.append(" x.UNIT_ID, x.WAREHOUSE_ID, ");
			buffer.append(" t.NAME TX_TYPE_NAME, s.NAME SITE_NAME");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

			buffer.append(" where s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");

			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (txTypeId != 0) {
				buffer.append(" and x.TX_TYPE_ID = ?");
			}
			if (customerId != 0) {
				buffer.append(" and x.CUSTOMER_ID = ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TX_DATE >= ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TX_DATE <= ?");
			}
			if (transactionNo != null) {
				buffer.append(" and x.TX_NUMBER like ?");
			}
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (txTypeId != 0) {
				index++;
				ps.setLong(index, txTypeId);
			}
			if (customerId != 0) {
				index++;
				ps.setLong(index, customerId);
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (transactionNo != null) {
				index++;
				ps.setString(index, transactionNo + "%");
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvCustomerTrx>();

			while (rs.next()) {
				dataFound = true;
				RcvCustomerTrx o = new RcvCustomerTrx();
				//
				o.setAddress1(rs.getString("ADDRESS1"));
				o.setAddress2(rs.getString("ADDRESS2"));
				o.setAddress3(rs.getString("ADDRESS3"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setAppliedAmount(rs.getDouble("APPLIED_AMOUNT"));
				o.setApplyingAmount(rs.getDouble("APPLYING_AMOUNT"));
				o.setArchingSheetId(rs.getLong("ARCHING_SHEET_ID"));
				o.setAreaCode(rs.getString("AREA_CODE"));
				o.setAttribute1(rs.getString("ATTRIBUTE1"));
				o.setAttribute2(rs.getString("ATTRIBUTE2"));
				o.setAttribute3(rs.getString("ATTRIBUTE3"));
				o.setAttribute4(rs.getString("ATTRIBUTE4"));
				o.setAttribute5(rs.getString("ATTRIBUTE5"));
				o.setCancellatedBy(rs.getString("CANCELLATED_BY"));
				if (rs.getString("CANCELLATED_ON") != null) {
					d = sdf.parse(rs.getString("CANCELLATED_ON"));
					o.setCancellatedOn(d);
				}
				o.setCancelComments(rs.getString("CANCEL_COMMENTS"));
				o.setCancelJrnlId(rs.getLong("CANCEL_JRNL_ID"));
				o.setCancelReasonId(rs.getLong("CANCEL_REASON_ID"));
				o.setCashControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setCashRegisterId(rs.getLong("CASH_REGISTER_ID"));
				o.setCollectionsAmount(rs.getDouble("COLLECTIONS_AMOUNT"));
				o.setCollectGroup(rs.getString("COLLECT_GROUP"));
				o.setCollectMethod(rs.getString("COLLECT_METHOD"));
				o.setComments(rs.getString("COMMENTS"));
				o.setCostCenter(rs.getString("COST_CENTER"));
				o.setCostCenterId(rs.getLong("COST_CENTER_ID"));
				o.setCreatedBy(rs.getString("CREATED_BY"));
				d = sdf.parse(rs.getString("CREATED_ON"));
				o.setCreatedOn(d);
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setCustomerCcid(rs.getLong("CUSTOMER_CCID"));
				o.setCustomerId(rs.getLong("CUSTOMER_ID"));
				o.setCustSiteId(rs.getLong("CUST_SITE_ID"));
				o.setDiscountAmt(rs.getDouble("DISCOUNT_AMT"));
				o.setDistributionAmt(rs.getDouble("DISTRIBUTION_AMT"));
				o.setDueDate(rs.getDate("DUE_DATE"));
				o.setDueDayOfMonth(rs.getInt("DUE_DAY_OF_MONTH"));
				o.setExcemptAmount(rs.getDouble("EXCEMPT_AMOUNT"));
				o.setExchangeRate(rs.getDouble("EXCHANGE_RATE"));
				o.setExpensesConceptId(rs.getLong("EXPENSES_CONCEPT_ID"));
				o.setFiscalPeriodId(rs.getLong("FISCAL_PERIOD_ID"));
				o.setFiscalYearId(rs.getLong("FISCAL_YEAR_ID"));
				o.setGainedCouponsQty(rs.getInt("GAINED_COUPONS_QTY"));
				o.setGainedPointsQty(rs.getLong("GAINED_POINTS_QTY"));
				o.setGleJournalId(rs.getLong("GLE_JOURNAL_ID"));
				o.setIdentifier(rs.getLong("IDENTIFIER"));
				o.setInstallmentsQty(rs.getInt("INSTALLMENTS_QTY"));
				o.setInstDaysInterval(rs.getInt("INST_DAYS_INTERVAL"));
				o.setInvoiceId(rs.getLong("INVOICE_ID"));
				o.setInvoiceStampId(rs.getLong("INVOICE_STAMP_ID"));
				o.setInvIntfaceId(rs.getLong("INV_INTFACE_ID"));
				o.setIssueJrnlId(rs.getLong("ISSUE_JRNL_ID"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));
				o.setLastPaymentDate(rs.getDate("LAST_PAYMENT_DATE"));
				o.setLcAmount(rs.getDouble("LC_AMOUNT"));
				o.setLcAppliedAmount(rs.getDouble("LC_APPLIED_AMOUNT"));
				o.setLcApplyingAmount(rs.getDouble("LC_APPLYING_AMOUNT"));
				o.setLcCollectAmount(rs.getDouble("LC_COLLECT_AMOUNT"));
				o.setModifiedBy(rs.getString("MODIFIED_BY"));
				if (rs.getString("MODIFIED_ON") != null) {
					d = sdf.parse(rs.getString("MODIFIED_ON"));
					o.setModifiedOn(d);
				}
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setPhone1(rs.getString("PHONE1"));
				o.setPhone2(rs.getString("PHONE2"));
				o.setPhone3(rs.getString("PHONE3"));
				o.setPosTransactionId(rs.getLong("POS_TRANSACTION_ID"));
				o.setPricesListId(rs.getLong("PRICES_LIST_ID"));
				o.setPrintedBy(rs.getString("PRINTED_BY"));
				if (rs.getString("PRINTING_DATE") != null) {
					d = sdf.parse(rs.getString("PRINTING_DATE"));
					o.setPrintingDate(d);
				}
				o.setProgram(rs.getString("PROGRAM"));
				o.setProjectId(rs.getLong("PROJECT_ID"));
				o.setRaCancelId(rs.getLong("RA_CANCEL_ID"));
				o.setRaCollectId(rs.getLong("RA_COLLECT_ID"));
				o.setRaJournalId(rs.getLong("RA_JOURNAL_ID"));
				o.setReceivablesType(rs.getString("RECEIVABLES_TYPE"));
				o.setRefNumber(rs.getString("REF_NUMBER"));
				o.setResidualAmount(rs.getDouble("RESIDUAL_AMOUNT"));
				o.setSalesmanId(rs.getLong("SALESMAN_ID"));
				o.setSalesOrderId(rs.getLong("SALES_ORDER_ID"));
				o.setSectionId(rs.getLong("SECTION_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSleCancelId(rs.getLong("SLE_CANCEL_ID"));
				o.setSleCostCancId(rs.getLong("SLE_COST_CANC_ID"));
				o.setSleCostId(rs.getLong("SLE_COST_ID"));
				o.setSleSalesId(rs.getLong("SLE_SALES_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setStatus(rs.getString("STATUS"));
				o.setTaskId(rs.getLong("TASK_ID"));
				o.setTax10Amt(rs.getDouble("TAX10_AMT"));
				o.setTax5Amt(rs.getDouble("TAX5_AMT"));
				o.setTaxable10Amt(rs.getDouble("TAXABLE10_AMT"));
				o.setTaxable5Amt(rs.getDouble("TAXABLE5_AMT"));
				o.setTaxableAmount(rs.getDouble("TAXABLE_AMOUNT"));
				o.setTaxAmount(rs.getDouble("TAX_AMOUNT"));
				o.setTaxPayerName(rs.getString("TAX_PAYER_NAME"));
				o.setTaxPayerNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTrxCondition(rs.getString("TRX_CONDITION"));
				o.setTrxVersion(rs.getInt("TRX_VERSION"));
				o.setTxBaseNumber(rs.getLong("TX_BASE_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxNumber(rs.getString("TX_NUMBER"));
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setWarehouseId(rs.getLong("WAREHOUSE_ID"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static int updateSignatureInfo ( long transactionId, 
			                                String securityCode, 
			                                String controlCode, 
			                                String qrCode,
			                                java.util.Date signDate,
			                                String xmlFile ) {
		Connection conn = null;
	    PreparedStatement stmtUpdate = null;
	    int updated = 0;
	    try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}
	        StringBuffer buffer = new StringBuffer();

	        buffer.append("update RCV_CUSTOMERS_TRX");
	        buffer.append(" set EB_SEND_DATE = ?,");
	        buffer.append(" EB_SIGN_DATE = ?,");
	        buffer.append(" EB_SECURITY_CODE = ?,");
	        buffer.append(" EB_CONTROL_CODE = ?,");
	        buffer.append(" EB_QR_CODE = ?,");
	        buffer.append(" EB_XML_FILE = ?");
	        buffer.append(" where IDENTIFIER = ?");

	        stmtUpdate = conn.prepareStatement(buffer.toString());
	        stmtUpdate.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
	        stmtUpdate.setTimestamp(2, new Timestamp(signDate.getTime()));
	        stmtUpdate.setString(3, securityCode);
	        stmtUpdate.setString(4, controlCode);
	        stmtUpdate.setString(5, qrCode);
	        stmtUpdate.setString(6, xmlFile);
	        stmtUpdate.setLong(7, transactionId);
	        updated = stmtUpdate.executeUpdate();
	        System.out.println("actualizado datos transmision: " + transactionId);
	        return updated;

	    } catch (Exception e) {
	    	    e.printStackTrace();
	        return 0;
	    } finally {
	        Util.closeStatement(stmtUpdate);
			Util.closeJDBCConnection(conn);
	    }
	}
	
	public static int updateQrCode ( long transactionId, long orgId, long unitId, String qrCode ) {
		Connection conn = null;
		PreparedStatement ps = null;
		int updated = 0;
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
			// eliminar el control de caja
	        StringBuffer buffer = new StringBuffer();
			buffer.append("insert into RCV_TRX_EB_QR_CODE (");
			buffer.append(" TRANSACTION_ID, ORG_ID, UNIT_ID, QR_CODE,");
			buffer.append(" CREATED_ON )");
			buffer.append(" values ( ?, ?, ?, ?, ? )");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, transactionId);
			ps.setLong(2, orgId);
			ps.setLong(3, unitId);
			ps.setString(4, qrCode);
	        ps.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
			updated = ps.executeUpdate();
			return updated;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(ps);
	        Util.closeJDBCConnection(conn);
		}
	}
	
	public static int createTransmissionLog ( RcvEbTransmissionLog tLog ) {
		Connection conn = null;
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
	        conn = Util.getConnection();
	        if (conn == null) {
	            throw new SQLException("No hay conexion con base de datos");
	        }
			// eliminar el control de caja
	        StringBuffer buffer = new StringBuffer();
			buffer.append("insert into RCV_EB_TRANSMISSION_LOG (");
			buffer.append(" IDENTIFIER, TRANSACTION_ID, ORG_ID, UNIT_ID,");
			buffer.append(" EVENT_ID, ERROR_CODE, ERROR_MSG, CREATED_ON )");
			buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ? )");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setLong(1, tLog.getIdentifier());
			stmtUpdate.setLong(2, tLog.getTransactionId());
			stmtUpdate.setLong(3, tLog.getOrgId());
			stmtUpdate.setLong(4, tLog.getUnitId());
			stmtUpdate.setShort(5, tLog.getEventId());
			stmtUpdate.setString(6, tLog.getErrorCode());
			stmtUpdate.setString(7, tLog.getErrorMsg());
			stmtUpdate.setTimestamp(8, new Timestamp(new java.util.Date().getTime()));
			updated = stmtUpdate.executeUpdate();
			return updated;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(stmtUpdate);
	        Util.closeJDBCConnection(conn);
		}
	}

	public static int updateControlCode ( long transactionId, String ctrlCode, String qrCode, Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("update RCV_CUSTOMERS_TRX");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_QR_CODE = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, ctrlCode);
			stmtUpdate.setString(2, qrCode);
			stmtUpdate.setLong(3, transactionId);
			updated = stmtUpdate.executeUpdate();
			//System.out.println("actualizado archivo KuDE: " + transactionId);
			return updated;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(stmtUpdate);
		}
	}
	
	public static boolean existsObject ( long objectId ) {
		
		Connection conn =  null;
		PreparedStatement stmtConsulta = null;
		ResultSet rsConsulta = null;
		boolean dataFound = false;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select IDENTIFIER");
		    buffer.append(" from RCV_CUSTOMERS_TRX");
		    buffer.append(" where IDENTIFIER = ?");

		    stmtConsulta = conn.prepareStatement(buffer.toString());
		    stmtConsulta.setLong(1, objectId );			

		    rsConsulta = stmtConsulta.executeQuery();
		    if (rsConsulta.next()) { 
		        dataFound = true;
		    }
			return dataFound;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally { 
			Util.closeResultSet(rsConsulta);
			Util.closeStatement(stmtConsulta);
			Util.closeJDBCConnection(conn);
		}	
	}

	public static boolean existsSecurityCode ( long codeValue ) {
		
		Connection conn =  null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean dataFound = false;
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select EB_SECURITY_CODE");
		    buffer.append(" from RCV_CUSTOMERS_TRX");
		    buffer.append(" where IDENTIFIER = ?");

		    stmt = conn.prepareStatement(buffer.toString());
		    stmt.setLong(1, codeValue );			

		    rs = stmt.executeQuery();
		    if (rs.next()) { 
		        dataFound = true;
		    }
			return dataFound;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(stmt);
			Util.closeJDBCConnection(conn);
		}	
	}

	/**
	 * Obtener la lista de facturas que aun no han sido transmitidas a la entidad recaudadora
	 * de impuestos correspondiente a una caja dentro de un tango de fechas.
	 * @param siteId - Identificador de sucursal
	 * @param trxDate - Fecha para la consulta de las transacciones
	 * @return ArrayList<RcvInvoice> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<RcvInvoice> getInvoicesNotSentList ( long siteId, 
			                                                     java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<RcvInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TX_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.STATUS,");
			buffer.append(" x.EB_BATCH_ID, x.CANCELLATED_ON, x.IDENTITY_TYPE, x.IDENTITY_NUMBER,");
			buffer.append(" nvl(rcv_coll_utils.f_get_coll_amount(x.IDENTIFIER, null), 0) COLL_AMOUNT");
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
				
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");

			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TX_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TX_DATE >= ?");
			}
			//
			System.out.println("**********getNotSentList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvInvoice>();

			while (rs.next()) {
				dataFound = true;
				RcvInvoice o = new RcvInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxCondition(rs.getString("TRX_CONDITION"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIdentityType(rs.getString("IDENTITY_TYPE"));
				o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setStatus(rs.getString("STATUS"));
				o.setCollectionsAmt(rs.getDouble("COLL_AMOUNT"));
				o.setEbBatchId(rs.getLong("EB_BATCH_ID"));
				o.setCanceledOn(rs.getDate("CANCELLATED_ON"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				o.setCustDocType(o.getIdentityType());
				o.setCustDocNumber(o.getIdentityNumber());
				if (o.getTaxNumber() != null) {
					o.setCustDocType("RUC");
					o.setCustDocNumber(o.getTaxNumber());					
				}
				//
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	/**
	 * Obtener la lista de facturas que ya han sido transmitidas a la entidad recaudadora
	 * de impuestos correspondiente a una sucursal dentro de un rango de fechas.
	 * @param siteId - Identificador de sucursal
	 * @param trxDate - Fecha para la consulta de las transacciones
	 * @return ArrayList<RcvInvoice> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<RcvInvoice> getSentInvoicesList ( long siteId, 
			                                                  java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<RcvInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TX_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.STATUS,");
			buffer.append(" x.EB_BATCH_ID, x.CANCELLATED_ON, x.IDENTITY_TYPE, x.IDENTITY_NUMBER,");
			buffer.append(" nvl(rcv_coll_utils.f_get_coll_amount(x.IDENTIFIER, null), 0) COLL_AMOUNT");
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

		    buffer.append(" where exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
				
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");

			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TX_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TX_DATE >= ?");
			}
			//
			System.out.println("**********getNotSentList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvInvoice>();

			while (rs.next()) {
				dataFound = true;
				RcvInvoice o = new RcvInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxCondition(rs.getString("TRX_CONDITION"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIdentityType(rs.getString("IDENTITY_TYPE"));
				o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setStatus(rs.getString("STATUS"));
				o.setCollectionsAmt(rs.getDouble("COLL_AMOUNT"));
				o.setEbBatchId(rs.getLong("EB_BATCH_ID"));
				o.setCanceledOn(rs.getDate("CANCELLATED_ON"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				o.setCustDocType(o.getIdentityType());
				o.setCustDocNumber(o.getIdentityNumber());
				if (o.getTaxNumber() != null) {
					o.setCustDocType("RUC");
					o.setCustDocNumber(o.getTaxNumber());					
				}
				//
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	/**
	 * Obtener la lista de numeros de contribuyente que estan registrados en las
	 * transacciones pero no existen en la base de datos de contribuyentes de la
	 * companhia.
	 * @param siteId - Identificador de sucursal
	 * @param trxDate - Fecha para la consulta de las transacciones
	 * @return ArrayList<String> - Lista de contribuyentes
	 */
	public static ArrayList<String> getTaxPayersList ( long siteId, 
			                                           java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		String s = null;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		ArrayList<String> l;
		int index = 0;
		int counter = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.TAX_PAYER_NUMBER TAX_NUMBER");
			buffer.append(" from POS_TRANSACTIONS x");
			buffer.append(" where not exists ( select 1");
			buffer.append(" from RCV_TAX_PAYERS p");
			buffer.append(" where p.TAX_PAYER_NO = x.TAX_PAYER_NUMBER )");
			buffer.append(" and instr(x.TAX_PAYER_NUMBER, '-') > 0");
			buffer.append(" and x.TAX_PAYER_NUMBER is not null");
			buffer.append(" and x.TRN_DATE < ?");
			buffer.append(" and x.TRN_DATE >= ?");
			buffer.append(" group by x.TAX_PAYER_NUMBER");

			//
			System.out.println("**********getTaxPayersList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			//if (siteId != 0) {
			//	index++;
			//	ps.setLong(index, siteId);
			//}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<String>();

			while (rs.next()) {
				dataFound = true;
				s = rs.getString("TAX_NUMBER");
				l.add(s);
				//counter++;
				//if (counter >= 50) {
				//	break;
				//}
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}

	/**
	 * Obtener la lista de notas de credito y notas de debito que aun no han sido transmitidas
	 *  a la entidad recaudadora de impuestos correspondiente a una caja dentro de un rango de 
	 *  fechas.
	 * @param siteId - Identificador de sucursal
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<RcvMemo> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<RcvMemo> getMemosNotSentList ( long siteId, 
			                                               java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<RcvMemo> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TX_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.STATUS,");
			buffer.append(" x.EB_BATCH_ID, x.CANCELLATED_ON, x.IDENTITY_TYPE, x.IDENTITY_NUMBER,");
			buffer.append(" x.INVOICE_ID, x.POS_TRANSACTION_ID, x.CASH_CONTROL_ID, x.CASH_REGISTER_ID");
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE in ( 'NOTA-CREDITO' )");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");

			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TX_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TX_DATE >= ?");
			}
			//
			System.out.println("**********getMemosNotSentList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvMemo>();

			while (rs.next()) {
				dataFound = true;
				RcvMemo o = new RcvMemo();
				//
				o.setMemoId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIdentityType(rs.getString("IDENTITY_TYPE"));
				o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setStatus(rs.getString("STATUS"));
				o.setEbBatchId(rs.getLong("EB_BATCH_ID"));
				o.setCanceledOn(rs.getDate("CANCELLATED_ON"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				o.setCustDocType(o.getIdentityType());
				o.setCustDocNumber(o.getIdentityNumber());
				if (o.getTaxNumber() != null) {
					o.setCustDocType("RUC");
					o.setCustDocNumber(o.getTaxNumber());					
				}
				// notas de credito vinculadas a facturas Receivables
				if (rs.getLong("INVOICE_ID") != 0) {
				    o.setInvoiceId(rs.getLong("INVOICE_ID"));
				    o.setInvoiceNumber(RcvCustomersTrxDAO.getRcvInvoiceNo(o.getInvoiceId(), conn));
				}
				// notas de credito vinculadas a facturas POS
				if (rs.getLong("POS_TRANSACTION_ID") != 0) {
				    o.setInvoiceId(rs.getLong("POS_TRANSACTION_ID"));
				    o.setCashControlId(rs.getLong("CASH_CONTROL_ID"));
				    o.setCashId(rs.getLong("CASH_REGISTER_ID"));
				    o.setInvoiceNumber(RcvCustomersTrxDAO.getPosInvoiceNo(o.getInvoiceId(), o.getCashControlId(), o.getCashId(), conn));
				}
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static String getRcvInvoiceNo ( long invoiceId, Connection conn ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceNo = null;

		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.TX_NUMBER");
			buffer.append(" from RCV_CUSTOMERS_TRX x");
			buffer.append(" where x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, invoiceId );			

			rs = ps.executeQuery();
			if (rs.next()) { 
				invoiceNo = rs.getString(1);
			}
			return invoiceNo;	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	public static String getPosInvoiceNo ( long invoiceId, long controlId, long cashId, Connection conn ) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceNo = null;

		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.TRN_NUMBER");
			buffer.append(" from POS_TRANSACTIONS x");
			buffer.append(" where x.CASH_ID = ?");
			buffer.append(" and x.CASH_CONTROL_ID = ?");
			buffer.append(" and x.IDENTIFIER = ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, cashId );			
			ps.setLong(2, controlId );			
			ps.setLong(3, invoiceId );			

			rs = ps.executeQuery();
			if (rs.next()) { 
				invoiceNo = rs.getString(1);
			}
			return invoiceNo;	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
		}	
	}
	
	/**
	 * Obtener la lista de facturas cuya representacion grafica aun no ha sido generada,
	 * correspondiente a una caja dentro de un tango de fechas.
	 * @param siteId - Idetificador de sucursal de la factura
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<RcvInvoice> - Lista de facturas que aun no tienen representacion grafica
	 */
	public static ArrayList<RcvInvoice> getNotRenderedList ( long siteId, 
			                                                 java.util.Date fromDate, 
			                                                 java.util.Date toDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<RcvInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TX_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

			buffer.append(" where s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.CANCELLATED_ON is null");			
			buffer.append(" and x.EB_QR_CODE is not null");
			buffer.append(" and x.EB_GR_FILE is null");

			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TX_DATE >= ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TX_DATE < ?");
			}
			//
			System.out.println("**********getNotRenderedList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}

			rs = ps.executeQuery();

			l = new ArrayList<RcvInvoice>();

			while (rs.next()) {
				dataFound = true;
				RcvInvoice o = new RcvInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxCondition(rs.getString("TRX_CONDITION"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				//System.out.println("agregando: " + o.getTxNumber());
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}

	/**
	 * Obtener la lista de facturas con representacion grafica generada,
	 * correspondiente a una caja dentro de un tango de fechas. Es utilizado principalmente
	 * en el proceso de envio de los KuDEs y los documentos electronicos a los clientes.
	 * @param siteId - Idetificador de caja de ventas al detalle
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<RcvInvoice> - Lista de facturas que aun no tienen representacion grafica
	 */
	public static ArrayList<RcvInvoice> getRenderedList ( long siteId, 
			                                              java.util.Date fromDate, 
			                                              java.util.Date toDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		String eMail = null;
		ArrayList<RcvInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TRN_NUMBER, to_char(x.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE, x.TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CASH_ID,");
			buffer.append(" x.CASH_CONTROL_ID, x.E_MAIL, x.EB_GR_FILE");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" POS_TRANSACTIONS x");

			buffer.append(" where s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.CANCELLATED_ON is null");			
			buffer.append(" and x.EB_QR_CODE is not null");
			buffer.append(" and x.EB_GR_FILE is not null");

			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TX_DATE >= ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TX_DATE < ?");
			}
			System.out.println("**********getRenderedList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (siteId != 0) {
				index++;
				ps.setLong(index, siteId);
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			if (toDate != null) {
				d = UtilPOS.addDaysToDate(toDate, 1);
				index++;
				ps.setTimestamp(index, new Timestamp(d.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvInvoice>();

			while (rs.next()) {
				dataFound = true;
				RcvInvoice o = new RcvInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxCondition(rs.getString("TRX_CONDITION"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				if (rs.getString("E_MAIL") != null) {
				    o.seteMail(rs.getString("E_MAIL"));
				} else {
					eMail = null;
					if (rs.getString("TAX_PAYER_NUMBER") != null) {
					    eMail = RcvTaxMailingListDAO.getTaxNumberEmail(rs.getString("TAX_PAYER_NUMBER"), conn);
					}
					if (rs.getString("IDENTITY_NUMBER") != null) {
					    eMail = RcvTaxMailingListDAO.getIdentityNoEmail(rs.getString("IDENTITY_NUMBER"), conn);
					}
				    o.seteMail(eMail);
				}
				o.setgRFileName(rs.getString("EB_GR_FILE"));
				System.out.println("getRenderedList - agregando: " + o.getTxNumber());
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	/**
	 * Obtener la lista de transacciones Receivables que fueron anuladas en un rango de fechas 
	 * a efectos de informar los eventos de cancelacion correspondientes.
	 * @param trxType - Tipo de transaccion
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<RcvInvoice> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<RcvInvoice> getCanceledList ( String trxType, 
			                                              java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<RcvInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TX_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CANCEL_COMMENTS,");
			buffer.append(" to_char(x.CANCELLATED_ON, 'dd/mm/yyyy hh24:mi:ss') CANCELLATED_ON");

			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

			// no se deben considerar las transacciones que ya tienen una entrada en el log de
			// eventos de anulacion
            // Eventos del Emisor
            // 1 = Cancelacion
            // 2 = Inutilizacion
            // 3 = Endoso (futuro)
			buffer.append(" where not exists ( select 1");
			buffer.append(" from RCV_EB_EVENT_ITEMS_LOG l");
			buffer.append(" where l.event_type_id in (1, 2)");
			buffer.append(" and l.transaction_id = x.IDENTIFIER )");

			// no se deben considerar las transacciones que no tienen entrada en el log de
			// transacciones aprobadas, si no hay registro de aprobacion, la transaccion
			// nunca llego a la entidad recaudadora
			buffer.append(" and exists ( select 1");
			buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
			buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
			buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.EB_CONTROL_CODE is not null");
			buffer.append(" and x.CANCELLATED_ON is not null");

			if (trxType != null) {
				buffer.append(" and t.TRX_TYPE = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.CANCELLATED_ON < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.CANCELLATED_ON >= ?");
			}
			//
			System.out.println("**********getCanceledList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (trxType != null) {
				index++;
				ps.setString(index, trxType);
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvInvoice>();

			while (rs.next()) {
				dataFound = true;
				RcvInvoice o = new RcvInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxCondition(rs.getString("TRX_CONDITION"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setCancelComments(rs.getString("CANCEL_COMMENTS"));
				d = sdf.parse(rs.getString("CANCELLATED_ON"));
				o.setCanceledOn(d);
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}

	/**
	 * Obtener la lista de transacciones Receivables que fueron anuladas sin haber sido emitidas 
	 * ni informadas a la entidad de impuestos a efectos de informar los eventos de inutilizacion 
	 * correspondientes.
	 * @param trxType - Tipo de transaccion
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<RcvInvoice> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<RcvInvoice> getDisabledList ( String trxType, 
			                                              java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<RcvInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TX_NUMBER, to_char(x.TX_DATE, 'dd/mm/yyyy hh24:mi:ss') TX_DATE, x.TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUST_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.TX_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CANCEL_COMMENTS,");
			buffer.append(" x.TX_BASE_NUMBER, to_char(x.CANCELLATED_ON, 'dd/mm/yyyy hh24:mi:ss') CANCELLATED_ON, g.STAMP_NO, t.TRX_TYPE");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_STAMP_RECORDS g,");
			buffer.append(" RCV_CUSTOMERS_TRX x");

			// no se deben considerar las transacciones que ya tienen una entrada en el log de
			// eventos de anulacion
            // Eventos del Emisor
            // 1 = Cancelacion
            // 2 = Inutilizacion
            // 3 = Endoso (futuro)
			buffer.append(" where not exists ( select 1");
			buffer.append(" from RCV_EB_EVENT_ITEMS_LOG l");
			buffer.append(" where l.event_type_id in (1, 2)");
			buffer.append(" and l.transaction_id = x.IDENTIFIER )");
			
			// no se deben considerar las transacciones que tienen entrada en el log de
			// transacciones aprobadas, si hay registro de aprobacion, el numero de la 
			// transaccion no puede ser inutilizado
		    buffer.append(" and not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and g.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and x.CANCELLATED_ON is not null");
			// disabled_falg = S es la forma de indicar que el numero debe ser inutilizado
			buffer.append(" and x.DISABLED_FLAG = 'S'");

			if (trxType != null) {
				buffer.append(" and t.TRX_TYPE = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.CANCELLATED_ON < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.CANCELLATED_ON >= ?");
			}
			//
			System.out.println("**********getCanceledList");
			System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (trxType != null) {
				index++;
				ps.setString(index, trxType);
			}
			if (toDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(toDate.getTime()));
			}
			if (fromDate != null) {
				index++;
				ps.setTimestamp(index, new Timestamp(fromDate.getTime()));
			}
			rs = ps.executeQuery();

			l = new ArrayList<RcvInvoice>();

			while (rs.next()) {
				dataFound = true;
				RcvInvoice o = new RcvInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setTxNumber(rs.getString("TX_NUMBER"));
				d = sdf.parse(rs.getString("TX_DATE"));
				o.setTxDate(d);
				o.setTxCondition(rs.getString("TRX_CONDITION"));
				o.setAmount(rs.getDouble("AMOUNT"));
				o.setCustomerNumber(rs.getString("IDENTITY_NUMBER"));
				customerName = rs.getString("TAX_PAYER_NAME");
				if (rs.getString("CUSTOMER_NAME") != null) {
					customerName = rs.getString("CUSTOMER_NAME");
				}
				o.setCustomerName(customerName);
				o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
				o.setTaxName(rs.getString("TAX_PAYER_NAME"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUST_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("TX_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setTrxType(rs.getString("TRX_TYPE"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				d = sdf.parse(rs.getString("CANCELLATED_ON"));
				o.setCanceledOn(d);
				o.setCancelComments(rs.getString("CANCEL_COMMENTS"));
			    o.setBaseNumber(rs.getInt("TX_BASE_NUMBER"));
			    //001-001-0002345
			    //012345678901234
			    o.setEstabCode(o.getTxNumber().substring(0, 3));
			    o.setIssuePointCode(o.getTxNumber().substring(4, 7));
			    o.setTransactionNo(o.getTxNumber().substring(8));
			    if (o.getBaseNumber() == 0) {
			    	    o.setBaseNumber(Integer.parseInt(o.getTransactionNo()));
			    }
			    o.setStampNo(rs.getString("STAMP_NO"));
				//System.out.println("agregando: " + o.getRECEIVING_NO());
				l.add(o);
			}
			if (dataFound == true) {
				return l;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}
	}
	
	public static int updateBatchId ( long transactionId, 
			                          String controlCode,
			                          long ebBatchId,
			                          String xmlFile,
			                          String qrCode,
			                          Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("update RCV_CUSTOMERS_TRX");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_SEND_DATE = ?,");
			buffer.append(" EB_BATCH_ID = ?,");
			buffer.append(" EB_XML_FILE = ?,");
			buffer.append(" EB_QR_CODE = ?");
			buffer.append(" where IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, controlCode);
			stmtUpdate.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			stmtUpdate.setLong(3, ebBatchId);
			stmtUpdate.setString(4, xmlFile);
			stmtUpdate.setString(5, qrCode);
			stmtUpdate.setLong(6, transactionId);
			updated = stmtUpdate.executeUpdate();
			//System.out.println("actualizado datos transmision: " + transactionId);
			return updated;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(stmtUpdate);
		}
	}
	
	public static int invoicesQty ( java.util.Date trxDate ) {
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsQty = 0;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select count(*)");
		    buffer.append(" from RCV_INVOICE_TYPES t,");
		    buffer.append(" RCV_CUSTOMERS_TRX x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
		    
		    buffer.append(" and t.TRX_TYPE = 'FACTURA'");
		    // igual seran listadas las facturas que no califican por ser contado y no tener
		    // cobranza registrada, solamente que no podran ser procesadas por el evento de
		    // preparacion
		    //buffer.append(" and ( ( x.TRX_CONDITION = 'CONTADO' ");
		    //buffer.append(" and rcv_coll_utils.f_get_coll_amount(x.IDENTIFIER, null) > 0 )");
		    //buffer.append(" or x.TRX_CONDITION = 'CREDITO' )");
		    buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
		    buffer.append(" and x.STATUS = 'CONFIRMADO'");
		    buffer.append(" and x.TX_DATE < ?");
		    buffer.append(" and x.TX_DATE >= ?");

		    ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));

		    rs = ps.executeQuery();
		    if (rs.next()) { 
		        rowsQty = rs.getInt(1);
		    }
			return rowsQty;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}
	
	public static int memosQty ( java.util.Date trxDate ) {
		
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsQty = 0;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		
		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			StringBuffer buffer = new StringBuffer();
		    buffer.append("select count(*)");
		    buffer.append(" from RCV_INVOICE_TYPES t,");
		    buffer.append(" RCV_CUSTOMERS_TRX x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from RCV_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
		    
		    buffer.append(" and t.TRX_TYPE = 'NOTA-CREDITO'");
		    buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
		    buffer.append(" and x.STATUS = 'CONFIRMADO'");
		    buffer.append(" and x.TX_DATE < ?");
		    buffer.append(" and x.TX_DATE >= ?");

		    ps = conn.prepareStatement(buffer.toString());
			ps.setTimestamp(1, new Timestamp(toDate.getTime()));
			ps.setTimestamp(2, new Timestamp(fromDate.getTime()));

		    rs = ps.executeQuery();
		    if (rs.next()) { 
		        rowsQty = rs.getInt(1);
		    }
			return rowsQty;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}

	public static int existsMemos ( String trxType, 
			                        java.util.Date fromDate, 
			                        java.util.Date toDate ) {

		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsQty = 0;
		java.util.Date d = null;    

		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append("select count(*)");
			buffer.append(" from RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");
			buffer.append(" where t.TRX_TYPE = ?");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.TX_DATE < ?");
			buffer.append(" and x.TX_DATE >= ?");

			ps = conn.prepareStatement(buffer.toString());
			ps.setString(1, trxType );			
			d = UtilPOS.addDaysToDate(toDate, 1);
			ps.setTimestamp(2, new Timestamp(d.getTime()));
			ps.setTimestamp(3, new Timestamp(fromDate.getTime()));

			rs = ps.executeQuery();
			if (rs.next()) { 
				rowsQty = rs.getInt(1);
			}
			return rowsQty;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}

	/**
	 * Este metodo tiene como objetivo determinar si existe uan transaccion con un numero
	 * determinado en una fecha.
	 */
	public static long existsTx ( String trxType, 
			                      java.util.Date txDate, 
			                      String txNumber, 
			                      long siteId ) {

		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		boolean dataFound = false;
		long txId = 0;
		int idx = 0;

		try {
			conn = Util.getConnection();	
			if (conn == null) { 
				throw new SQLException("No hay conexion con base de datos");
			}
			
			fromDate = txDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			buffer.append("select x.IDENTIFIER");
			buffer.append(" from RCV_INVOICE_TYPES t,");
			buffer.append(" RCV_CUSTOMERS_TRX x");
			buffer.append(" where t.TRX_TYPE = ?");
			buffer.append(" and t.IDENTIFIER = x.TX_TYPE_ID");
			buffer.append(" and x.TX_DATE < ?");
			buffer.append(" and x.TX_DATE >= ?");
			buffer.append(" and x.TX_NUMBER = ?");
			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");				
			}

			ps = conn.prepareStatement(buffer.toString());
			idx++;
			ps.setString(idx, trxType );			
			idx++;
			ps.setTimestamp(idx, new Timestamp(toDate.getTime()));
			idx++;
			ps.setTimestamp(idx, new Timestamp(fromDate.getTime()));
			idx++;
			ps.setString(idx, trxType );			
			if (siteId != 0) {
				idx++;
				ps.setLong(idx, siteId);
			}

			rs = ps.executeQuery();
			if (rs.next()) { 
				dataFound = true;
				txId = rs.getLong(1);
			}
			return txId;	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally { 
			Util.closeResultSet(rs);
			Util.closeStatement(ps);
			Util.closeJDBCConnection(conn);
		}	
	}

}
