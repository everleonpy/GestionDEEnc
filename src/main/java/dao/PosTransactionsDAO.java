package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import pojo.PosEbTransmissionLog;
import pojo.PosInvoice;
import pojo.PosInvoiceData;
import pojo.PosTransaction;
import pojo.RcvInvoice;
import util.UtilPOS;

public class PosTransactionsDAO {
	
    public static PosTransaction getRow ( long transactionId, long cashControlId, long cashId, Connection conn ) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        java.util.Date d = null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        PosTransaction o = null;
        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("select ADDRESS, AMOUNT, ATTRIBUTE1, ATTRIBUTE2,");
            buffer.append(" ATTRIBUTE3, ATTRIBUTE4, ATTRIBUTE5, BUILDING_NUMBER,");
            buffer.append(" CANCELLED_BY, to_char(CANCELLED_ON, 'ddmmyyyy hh24miss') CANCELLED_ON, CANCEL_HEADTLLR_ID, CASH_CONTROL_ID,");
            buffer.append(" CASH_ID, CITY_ID, COLLECTION_ID, COMMENTS,");
            buffer.append(" COUNTRY_ID, COUNTY_ID, COUPONS_DOC_NO, COUPONS_NAME,");
            buffer.append(" COUPONS_PHONE, COUPONS_QTY, CREATED_BY, to_char(CREATED_ON, 'ddmmyyyy hh24miss') CREATED_ON,");
            buffer.append(" CURRENCY_ID, CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_NUMBER,");
            buffer.append(" CUSTOMER_SITE_ID, CUST_POINTS_QTY, DISCOUNT10_AMOUNT, DISCOUNT5_AMOUNT,");
            buffer.append(" DISCOUNT_AMOUNT, EB_CDM_REASON_ID, EB_CONTROL_CODE, EB_OPER_TYPE_ID,");
            buffer.append(" EB_SALE_MODE_ID, EB_SECURITY_CODE, to_char(EB_SEND_DATE, 'ddmmyyyy hh24miss') EB_SEND_DATE, to_char(EB_SIGN_DATE, 'ddmmyyyy hh24miss') EB_SIGN_DATE,");
            buffer.append(" EB_TAX_TYPE_ID, EB_TX_TYPE_ID, EXCEMPT_AMOUNT, EXCHANGE_RATE,");
            buffer.append(" EXPORTED_FLAG, E_MAIL, FOREIGN_TRX_ID, GLOBAL_DISC_PCT,");
            buffer.append(" IDENTIFIER, IDENTITY_NUMBER, IDENTITY_TYPE, INITIAL_PAYMENT,");
            buffer.append(" INSTALLMENTS_QTY, INST_DAYS_INTERVAL, INVOICE_TYPE_ID, INV_JOURNAL_ID,");
            buffer.append(" ISSUE_POINT_ID, MODIFIED_BY, to_char(MODIFIED_ON, 'ddmmyyyy hh24miss') MODIFIED_ON, ORG_ID,");
            buffer.append(" ORG_TYPE, PAID_AMOUNT, PHONE, PMT_TERM_ID,");
            buffer.append(" PRINTED_BY, PRINTING_DATE, PROGRAM, RCV_TRANS_ID,");
            buffer.append(" READY_TO_SEND, REAL_AMOUNT, REF_NUMBER, REMISSION_DATE,");
            buffer.append(" RESIDUAL_AMOUNT, ROUNDING_AMOUNT, SALESMAN_ID, SENT_TO_SERVER,");
            buffer.append(" SITE_ID, SLE_CANCEL_ID, SLE_COST_CANC_ID, SLE_COST_ID,");
            buffer.append(" SLE_SALES_ID, STAMP_ID, STAMP_NUMBER, STATE_ID,");
            buffer.append(" TAX10_AMOUNT, TAX5_AMOUNT, TAXABLE10_AMOUNT, TAXABLE5_AMOUNT,");
            buffer.append(" TAXABLE_AMOUNT, TAX_AMOUNT, TAX_PAYER_NAME, TAX_PAYER_NUMBER,");
            buffer.append(" TRANS_TYPE_ID, TRN_BASE_NUMBER, to_char(TRN_DATE, 'ddmmyyyy hh24miss') TRN_DATE, TRN_NUMBER,");
            buffer.append(" TRX_CONDITION, UNIT_ID, XPERT_VERSION ");
            buffer.append(" from POS_TRANSACTIONS");
            buffer.append(" where IDENTIFIER = ?");
            buffer.append(" and CASH_CONTROL_ID = ?");
            buffer.append(" and CASH_ID = ?");

            ps = conn.prepareStatement(buffer.toString());
            ps.setLong(1, transactionId);
            ps.setLong(2, cashControlId);
            ps.setLong(3, cashId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dataFound = true;
                o = new PosTransaction();
                o.setAddress(rs.getString("ADDRESS"));
                o.setAmount(rs.getDouble("AMOUNT"));
                o.setAttribute1(rs.getString("ATTRIBUTE1"));
                o.setAttribute2(rs.getString("ATTRIBUTE2"));
                o.setAttribute3(rs.getString("ATTRIBUTE3"));
                o.setAttribute4(rs.getString("ATTRIBUTE4"));
                o.setAttribute5(rs.getString("ATTRIBUTE5"));
                o.setBuildingNumber(rs.getString("BUILDING_NUMBER"));
                o.setCancelledBy(rs.getString("CANCELLED_BY"));
                if (rs.getString("CANCELLED_ON") != null) {
                    d = sdf.parse(rs.getString("CANCELLED_ON"));
                    o.setCancelledOn(d);
                }
                o.setCancelHeadtllrId(rs.getLong("CANCEL_HEADTLLR_ID"));
                o.setCashControlId(rs.getLong("CASH_CONTROL_ID"));
                o.setCashId(rs.getLong("CASH_ID"));
                o.setCityId(rs.getLong("CITY_ID"));
                o.setCollectionId(rs.getLong("COLLECTION_ID"));
                o.setComments(rs.getString("COMMENTS"));
                o.setCountryId(rs.getLong("COUNTRY_ID"));
                o.setCountyId(rs.getLong("COUNTY_ID"));
                o.setCouponsDocNo(rs.getString("COUPONS_DOC_NO"));
                o.setCouponsName(rs.getString("COUPONS_NAME"));
                o.setCouponsPhone(rs.getString("COUPONS_PHONE"));
                o.setCouponsQty(rs.getDouble("COUPONS_QTY"));
                o.setCreatedBy(rs.getString("CREATED_BY"));
                d = sdf.parse(rs.getString("CREATED_ON"));
                o.setCreatedOn(d);
                o.setCurrencyId(rs.getLong("CURRENCY_ID"));
                o.setCustomerId(rs.getLong("CUSTOMER_ID"));
                o.setCustomerName(rs.getString("CUSTOMER_NAME"));
                o.setCustomerNumber(rs.getString("CUSTOMER_NUMBER"));
                o.setCustomerSiteId(rs.getLong("CUSTOMER_SITE_ID"));
                o.setCustPointsQty(rs.getDouble("CUST_POINTS_QTY"));
                o.setDiscount10Amount(rs.getDouble("DISCOUNT10_AMOUNT"));
                o.setDiscount5Amount(rs.getDouble("DISCOUNT5_AMOUNT"));
                o.setDiscountAmount(rs.getDouble("DISCOUNT_AMOUNT"));
                o.setEbCdmReasonId(rs.getLong("EB_CDM_REASON_ID"));
                o.setEbControlCode(rs.getString("EB_CONTROL_CODE"));
                o.setEbOperTypeId(rs.getLong("EB_OPER_TYPE_ID"));
                o.setEbSaleModeId(rs.getLong("EB_SALE_MODE_ID"));
                o.setEbSecurityCode(rs.getString("EB_SECURITY_CODE"));
                if (rs.getString("EB_SEND_DATE") != null) {
                    d = sdf.parse(rs.getString("EB_SEND_DATE"));
                    o.setEbSendDate(d);
                }
                if (rs.getString("EB_SIGN_DATE") != null) {
                    d = sdf.parse(rs.getString("EB_SIGN_DATE"));
                    o.setEbSignDate(d);
                }
                o.setEbTaxTypeId(rs.getLong("EB_TAX_TYPE_ID"));
                o.setEbTxTypeId(rs.getLong("EB_TX_TYPE_ID"));
                o.setExcemptAmount(rs.getDouble("EXCEMPT_AMOUNT"));
                o.setExchangeRate(rs.getDouble("EXCHANGE_RATE"));
                o.setExportedFlag(rs.getString("EXPORTED_FLAG"));
                o.setEMail(rs.getString("E_MAIL"));
                o.setForeignTrxId(rs.getLong("FOREIGN_TRX_ID"));
                o.setGlobalDiscPct(rs.getDouble("GLOBAL_DISC_PCT"));
                o.setIdentifier(rs.getLong("IDENTIFIER"));
                o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
                o.setIdentityType(rs.getString("IDENTITY_TYPE"));
                o.setInitialPayment(rs.getDouble("INITIAL_PAYMENT"));
                o.setInstallmentsQty(rs.getInt("INSTALLMENTS_QTY"));
                o.setInstDaysInterval(rs.getInt("INST_DAYS_INTERVAL"));
                o.setInvoiceTypeId(rs.getLong("INVOICE_TYPE_ID"));
                o.setInvJournalId(rs.getLong("INV_JOURNAL_ID"));
                o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));
                o.setModifiedBy(rs.getString("MODIFIED_BY"));
                if (rs.getString("MODIFIED_ON") != null) {
                    d = sdf.parse(rs.getString("MODIFIED_ON"));
                    o.setModifiedOn(d);
                }
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setOrgType(rs.getString("ORG_TYPE"));
                o.setPaidAmount(rs.getDouble("PAID_AMOUNT"));
                o.setPhone(rs.getString("PHONE"));
                o.setPmtTermId(rs.getLong("PMT_TERM_ID"));
                o.setPrintedBy(rs.getString("PRINTED_BY"));
                if (rs.getString("PRINTING_DATE") != null) {
                    d = sdf.parse(rs.getString("PRINTING_DATE"));
                    o.setPrintingDate(d);
                }
                o.setProgram(rs.getString("PROGRAM"));
                o.setRcvTransId(rs.getLong("RCV_TRANS_ID"));
                o.setReadyToSend(rs.getString("READY_TO_SEND"));
                o.setRealAmount(rs.getDouble("REAL_AMOUNT"));
                o.setRefNumber(rs.getString("REF_NUMBER"));
                if (rs.getString("REMISSION_DATE") != null) {
                    d = sdf.parse(rs.getString("REMISSION_DATE"));
                    o.setRemissionDate(d);
                }
                o.setResidualAmount(rs.getDouble("RESIDUAL_AMOUNT"));
                o.setRoundingAmount(rs.getDouble("ROUNDING_AMOUNT"));
                o.setSalesmanId(rs.getLong("SALESMAN_ID"));
                o.setSentToServer(rs.getString("SENT_TO_SERVER"));
                o.setSiteId(rs.getLong("SITE_ID"));
                o.setSleCancelId(rs.getLong("SLE_CANCEL_ID"));
                o.setSleCostCancId(rs.getLong("SLE_COST_CANC_ID"));
                o.setSleCostId(rs.getLong("SLE_COST_ID"));
                o.setSleSalesId(rs.getLong("SLE_SALES_ID"));
                o.setStampId(rs.getLong("STAMP_ID"));
                o.setStampNumber(rs.getString("STAMP_NUMBER"));
                o.setStateId(rs.getLong("STATE_ID"));
                o.setTax10Amount(rs.getDouble("TAX10_AMOUNT"));
                o.setTax5Amount(rs.getDouble("TAX5_AMOUNT"));
                o.setTaxable10Amount(rs.getDouble("TAXABLE10_AMOUNT"));
                o.setTaxable5Amount(rs.getDouble("TAXABLE5_AMOUNT"));
                o.setTaxableAmount(rs.getDouble("TAXABLE_AMOUNT"));
                o.setTaxAmount(rs.getDouble("TAX_AMOUNT"));
                o.setTaxPayerName(rs.getString("TAX_PAYER_NAME"));
                o.setTaxPayerNumber(rs.getString("TAX_PAYER_NUMBER"));
                o.setTransTypeId(rs.getLong("TRANS_TYPE_ID"));
                o.setTrnBaseNumber(rs.getLong("TRN_BASE_NUMBER"));
                d = sdf.parse(rs.getString("TRN_DATE"));
                o.setTrnDate(d);
                o.setTrnNumber(rs.getString("TRN_NUMBER"));
                o.setTrxCondition(rs.getString("TRX_CONDITION"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.setXpertVersion(rs.getString("XPERT_VERSION"));
            }
            if (dataFound == true) {
                return o;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(ps);
        }
    }

	public static int notValidInvoicesQty ( java.util.Date trxDate ) {
		
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
		    buffer.append(" POS_TRANSACTIONS x");

		    buffer.append(" where t.TRX_TYPE = 'FACTURA'");
		    buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
		    buffer.append(" and x.STAMP_ID not in (60035, 60037)");
		    buffer.append(" and x.TRN_DATE < ?");
		    buffer.append(" and x.TRN_DATE >= ?");

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
		    buffer.append(" POS_TRANSACTIONS x");

		    buffer.append(" where not exists ( select 1");
		    buffer.append(" from POS_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.CASH_REGISTER_ID = x.CASH_ID");
		    buffer.append(" and b.CASH_CONTROL_ID = x.CASH_CONTROL_ID");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
		    
		    buffer.append(" and t.TRX_TYPE = 'FACTURA'");
		    buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
		    buffer.append(" and x.TRN_DATE < ?");
		    buffer.append(" and x.TRN_DATE >= ?");

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
    
    public static PosInvoiceData getFormatList ( long transactionId, long cashControlId, long cashId, Connection conn ) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean dataFound = false;

        java.util.Date d = null;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        PosInvoiceData o = null;
        try {
            StringBuffer buffer = new StringBuffer();

            buffer.append("select csh.FULL_NAME CASHIER_NAME, cre.DESCRIPTION CR_NAME, cre.CR_NUMBER, 'CONTADO' INV_CONDITION,");
            buffer.append(" ipo.CODE IPO_CODE, ipo.DESCRIPTION IPO_NAME, org.ADDRESS1 ORG_ADDRESS, org.NAME ORG_NAME,");
            buffer.append(" org.TAX_NUMBER ORG_TAXNO, org.ALTERNATIVE_NAME ORG_TRADNAME, org.PHONE1 ORG_PHONE, '0' PAYMENT_DAYS,");
            buffer.append(" ste.ADDRESS1 SITE_ADDRESS, ste.NAME SITE_NAME, stm.AUTHORIZATION_DATE STMAUTH_DATE, stm.EXPIRATION_DATE STMEXP_DATE,");
            buffer.append(" stm.FROM_DATE STMFROM_DATE, stm.TO_DATE STMTO_DATE, stm.STAMP_NO, to_char(inv.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE,");
            buffer.append(" ttp.NAME TX_TYPE_NAME,");
            buffer.append(" inv.EXCEMPT_AMOUNT, inv.TAXABLE_AMOUNT, inv.TAX_AMOUNT, inv.DISCOUNT_AMOUNT,");
            buffer.append(" inv.TAXABLE5_AMOUNT, inv.TAX5_AMOUNT, inv.TAXABLE10_AMOUNT, inv.TAX10_AMOUNT,");
            buffer.append(" inv.DISCOUNT5_AMOUNT, inv.DISCOUNT10_AMOUNT, inv.ROUNDING_AMOUNT, inv.RESIDUAL_AMOUNT,");
            buffer.append(" inv.AMOUNT, inv.ORG_ID, inv.UNIT_ID, inv.CUSTOMER_NUMBER, inv.CUSTOMER_NAME,");
            buffer.append(" inv.TAX_PAYER_NUMBER, inv.TAX_PAYER_NAME, ste.PHONE1 SITE_PHONE, ste.DONATION_BENEF_NAME,");
            buffer.append(" inv.CURRENCY_ID, ccy.CODE CURRENCY_CODE, ccy.DECIMAL_PLACES, inv.TRN_NUMBER,");
            buffer.append(" inv.EB_CONTROL_CODE, inv.EB_QR_CODE");

            buffer.append(" from FND_ORGANIZATIONS org,");
            buffer.append(" RCV_INVOICE_TYPES ttp,");            
            buffer.append(" FND_CURRENCIES ccy,");
            buffer.append(" FND_SITES ste,");
            buffer.append(" POS_CASHIERS csh,");
            buffer.append(" POS_CASH_REGISTERS cre,");
            buffer.append(" POS_ISSUE_POINTS ipo,");
            buffer.append(" POS_STAMP_RECORDS stm,");
            buffer.append(" POS_CR_CONTROLS ctr,");
            buffer.append(" POS_TRANSACTIONS inv");
            
            buffer.append(" where org.IDENTIFIER = inv.ORG_ID");
            buffer.append(" and ttp.IDENTIFIER = inv.INVOICE_TYPE_ID");
            buffer.append(" and ccy.IDENTIFIER = inv.CURRENCY_ID");
            buffer.append(" and ste.IDENTIFIER = inv.SITE_ID");
            buffer.append(" and csh.IDENTIFIER = ctr.CASHIER_ID");
            buffer.append(" and ctr.CASH_REGISTER_ID = inv.CASH_ID");
            buffer.append(" and ctr.IDENTIFIER = inv.CASH_CONTROL_ID");            
            buffer.append(" and ipo.IDENTIFIER = inv.ISSUE_POINT_ID");
            buffer.append(" and stm.IDENTIFIER = inv.STAMP_ID");
            buffer.append(" and cre.IDENTIFIER = inv.CASH_ID");

            buffer.append(" and inv.CASH_ID = ?");
            buffer.append(" and inv.CASH_CONTROL_ID = ?");
            buffer.append(" and inv.IDENTIFIER = ?");

            ps = conn.prepareStatement(buffer.toString());
            ps.setLong(1, cashId);
            ps.setLong(2, cashControlId);
            ps.setLong(3, transactionId);
            rs = ps.executeQuery();            

            if (rs.next()) {
                dataFound = true;
                o = new PosInvoiceData();
                
                o.setActivList(null);
                o.setCashierName(rs.getString("CASHIER_NAME"));
                o.setCashName(rs.getString("CR_NAME"));
                o.setCashNumber(rs.getString("CR_NUMBER"));
                o.setInvCondition(rs.getString("INV_CONDITION"));
                o.setIssuePointCode(rs.getString("IPO_CODE"));
                o.setIssuePointName(rs.getString("IPO_NAME"));
                o.setOrgAddress(rs.getString("ORG_ADDRESS"));
                o.setOrgName(rs.getString("ORG_NAME"));
                o.setOrgPhone(rs.getString("ORG_PHONE"));
                o.setOrgTaxNumber(rs.getString("ORG_TAXNO"));
                o.setPaymentDays(rs.getShort("PAYMENT_DAYS"));
                o.setSiteAddress(rs.getString("SITE_ADDRESS"));
                o.setSiteName(rs.getString("SITE_NAME"));
                o.setSitePhone(rs.getString("SITE_PHONE"));
                o.setSiteBenefName(rs.getString("DONATION_BENEF_NAME"));
                o.setStampAuthDate(rs.getDate("STMAUTH_DATE"));
                o.setStampExpDate(rs.getDate("STMEXP_DATE"));
                o.setStampFromDate(rs.getDate("STMFROM_DATE"));
                o.setStampNo(rs.getString("STAMP_NO"));
                o.setStampToDate(rs.getDate("STMTO_DATE"));
                o.setTradingName(rs.getString("ORG_TRADNAME"));
                d = sdf.parse(rs.getString("TRN_DATE"));
                o.setTxDate(d);
                o.setTxTypeName(rs.getString("TX_TYPE_NAME")); 
                o.setTrnNumber(rs.getString("TRN_NUMBER"));
                //
                System.out.println(rs.getDouble("AMOUNT") + " | " + rs.getDouble("EXCEMPT_AMOUNT") + " | " +
                		rs.getDouble("TAXABLE_AMOUNT") + " | " + rs.getDouble("TAX_AMOUNT") + " | " + rs.getDouble("TAXABLE10_AMOUNT") + " | " + 
                		rs.getDouble("TAXABLE5_AMOUNT") + " | " + rs.getDouble("TAX10_AMOUNT") + " | " + rs.getDouble("TAX5_AMOUNT") + " | " + 
                		rs.getDouble("DISCOUNT_AMOUNT"));
                o.setInvoiceAmount(rs.getDouble("AMOUNT"));
                o.setExemptAmount(rs.getDouble("EXCEMPT_AMOUNT"));
                o.setTaxableAmount(rs.getDouble("TAXABLE_AMOUNT"));
                o.setTaxAmount(rs.getDouble("TAX_AMOUNT"));
                o.setTaxable10Amount(rs.getDouble("TAXABLE10_AMOUNT"));
                o.setTaxable5Amount(rs.getDouble("TAXABLE5_AMOUNT"));
                o.setTax10Amount(rs.getDouble("TAX10_AMOUNT"));
                o.setTax5Amount(rs.getDouble("TAX5_AMOUNT"));
                o.setResidualAmount(rs.getDouble("RESIDUAL_AMOUNT"));
                o.setDiscountAmount(rs.getDouble("DISCOUNT_AMOUNT"));
                //
                o.setInvoiceId(transactionId);
                o.setCashId(cashId);
                o.setControlId(cashControlId);
                o.setOrgId(rs.getLong("ORG_ID"));
                o.setUnitId(rs.getLong("UNIT_ID"));
                o.setCurrencyId(rs.getLong("CURRENCY_ID"));
                o.setCurrencyCode(rs.getString("CURRENCY_CODE"));
                o.setCcyDecimals(rs.getInt("DECIMAL_PLACES"));
                //
                o.setCustomerNumber(rs.getString("CUSTOMER_NUMBER"));
                o.setCustomerName(rs.getString("CUSTOMER_NAME"));
                o.setTaxNumber(rs.getString("TAX_PAYER_NUMBER"));
                o.setTaxName(rs.getString("TAX_PAYER_NAME"));
                //
                o.setEbControlCode(rs.getString("EB_CONTROL_CODE"));
                o.setQrCode(rs.getString("EB_QR_CODE"));
            }
            if (dataFound == true) {
                return o;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(ps);
        }
    }
	
	public static int updateSignatureInfo ( long transactionId, 
			                                long controlId, 
			                                long cashId, 
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

	        buffer.append("update POS_TRANSACTIONS");
	        buffer.append(" set EB_SEND_DATE = ?,");
	        buffer.append(" EB_SIGN_DATE = ?,");
	        buffer.append(" EB_SECURITY_CODE = ?,");
	        buffer.append(" EB_CONTROL_CODE = ?,");
	        buffer.append(" EB_QR_CODE = ?,");
	        buffer.append(" EB_XML_FILE = ?");
	        buffer.append(" where CASH_ID = ?");
	        buffer.append(" and CASH_CONTROL_ID = ?");
	        buffer.append(" and IDENTIFIER = ?");

	        stmtUpdate = conn.prepareStatement(buffer.toString());
	        stmtUpdate.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
	        stmtUpdate.setTimestamp(2, new Timestamp(signDate.getTime()));
	        stmtUpdate.setString(3, securityCode);
	        stmtUpdate.setString(4, controlCode);
	        stmtUpdate.setString(5, qrCode);
	        stmtUpdate.setString(6, xmlFile);
	        stmtUpdate.setLong(7, cashId);
	        stmtUpdate.setLong(8, controlId);
	        stmtUpdate.setLong(9, transactionId);
	        updated = stmtUpdate.executeUpdate();
	        //System.out.println("actualizado datos transmision: " + transactionId);
	        return updated;

	    } catch (Exception e) {
	    	    e.printStackTrace();
	        return 0;
	    } finally {
	        Util.closeStatement(stmtUpdate);
			Util.closeJDBCConnection(conn);
	    }
	}
	
	public static int updateBatchId ( long transactionId, 
			                          long controlId, 
			                          long cashId, 
			                          String controlCode,
			                          long ebBatchId,
			                          String fileName, 
			                          Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			/*
			buffer.append("update POS_TRANSACTIONS");
			buffer.append(" set EB_SEND_DATE = ?,");
			buffer.append(" EB_BATCH_ID = ?,");
			buffer.append(" EB_XML_FILE = ?");
			buffer.append(" where CASH_ID = ?");
			buffer.append(" and CASH_CONTROL_ID = ?");
			buffer.append(" and IDENTIFIER = ?");
			*/
			buffer.append("update POS_EB_INVOICES");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_SEND_DATE = ?,");
			buffer.append(" EB_BATCH_ID = ?,");
			buffer.append(" EB_XML_FILE = ?");
			buffer.append(" where CASHID = ?");
			buffer.append(" and CASHCONTROLID = ?");
			buffer.append(" and IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, controlCode);
			stmtUpdate.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			stmtUpdate.setLong(3, ebBatchId);
			stmtUpdate.setString(4, fileName);
			stmtUpdate.setLong(5, cashId);
			stmtUpdate.setLong(6, controlId);
			stmtUpdate.setLong(7, transactionId);
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

	public static int updateGrFile ( long transactionId, 
			long controlId, 
			long cashId, 
			String fileName ) {
		Connection conn = null;
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}
			StringBuffer buffer = new StringBuffer();

			buffer.append("update POS_TRANSACTIONS");
			buffer.append(" set EB_GR_FILE = ?");
			buffer.append(" where CASH_ID = ?");
			buffer.append(" and CASH_CONTROL_ID = ?");
			buffer.append(" and IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, fileName);
			stmtUpdate.setLong(2, cashId);
			stmtUpdate.setLong(3, controlId);
			stmtUpdate.setLong(4, transactionId);
			updated = stmtUpdate.executeUpdate();
			//System.out.println("actualizado archivo KuDE: " + transactionId);
			return updated;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			Util.closeStatement(stmtUpdate);
			Util.closeJDBCConnection(conn);
		}
	}

	public static int updateControlCode ( long transactionId, 
			                              long controlId, 
			                              long cashId, 
			                              String controlCode, 
			                              String qrCode, 
			                              Connection conn ) {
		PreparedStatement stmtUpdate = null;
		int updated = 0;
		try {
			StringBuffer buffer = new StringBuffer();

			buffer.append("update POS_TRANSACTIONS");
			buffer.append(" set EB_CONTROL_CODE = ?,");
			buffer.append(" EB_QR_CODE = ?");
			buffer.append(" where CASH_ID = ?");
			buffer.append(" and CASH_CONTROL_ID = ?");
			buffer.append(" and IDENTIFIER = ?");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setString(1, controlCode);
			stmtUpdate.setString(2, qrCode);
			stmtUpdate.setLong(3, cashId);
			stmtUpdate.setLong(4, controlId);
			stmtUpdate.setLong(5, transactionId);
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

	public static int updateQrCode ( long transactionId, long controlId, long cashId, long orgId, long unitId, String qrCode ) {
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
			buffer.append("insert into POS_TRX_EB_QR_CODE (");
			buffer.append(" TRANSACTION_ID, CASH_CONTROL_ID, CASH_ID, ORG_ID,");
			buffer.append(" UNIT_ID, QR_CODE, CREATED_ON )");
			buffer.append(" values ( ?, ?, ?, ?, ?, ?, ? )");

			ps = conn.prepareStatement(buffer.toString());
			ps.setLong(1, transactionId);
			ps.setLong(2, controlId);
			ps.setLong(3, cashId);
			ps.setLong(4, orgId);
			ps.setLong(5, unitId);
			ps.setString(6, qrCode);
			ps.setTimestamp(7, new Timestamp (new java.util.Date().getTime()));
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
	
	public static int createTransmissionLog ( PosEbTransmissionLog tLog ) {
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
			buffer.append("insert into POS_EB_TRANSMISSION_LOG (");
			buffer.append(" IDENTIFIER, TRANSACTION_ID, CASH_CONTROL_ID, CASH_ID,");
			buffer.append(" ORG_ID, UNIT_ID, EVENT_ID, ERROR_CODE,");
			buffer.append(" ERROR_MSG, CREATED_ON )");
			buffer.append(" values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

			stmtUpdate = conn.prepareStatement(buffer.toString());
			stmtUpdate.setLong(1, tLog.getIdentifier());
			stmtUpdate.setLong(2, tLog.getTransactionId());
			stmtUpdate.setLong(3, tLog.getCashControlId());
			stmtUpdate.setLong(4, tLog.getCashId());
			stmtUpdate.setLong(5, tLog.getOrgId());
			stmtUpdate.setLong(6, tLog.getUnitId());
			stmtUpdate.setShort(7, tLog.getEventId());
			stmtUpdate.setString(8, tLog.getErrorCode());
			stmtUpdate.setString(9, tLog.getErrorMsg());
			stmtUpdate.setTimestamp(10, new Timestamp (new java.util.Date().getTime()));
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
	
	public static boolean existsObject ( long cashId, long controlId, long transactionId ) {
		
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
		    buffer.append(" from POS_TRANSACTIONS");
		    buffer.append(" where CASH_ID = ?");
		    buffer.append(" and CASH_CONTROL_ID = ?");
		    buffer.append(" and IDENTIFIER = ?");

		    stmtConsulta = conn.prepareStatement(buffer.toString());
		    stmtConsulta.setLong(1, cashId );			
		    stmtConsulta.setLong(2, controlId );			
		    stmtConsulta.setLong(3, transactionId );			

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
		    buffer.append(" from POS_TRANSACTIONS");
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
	 * de impuestos correspondiente a una sucursal y una fecha.
	 * @param siteId - Identificador de sucursal
	 * @param trxDate - Fecha de las transacciones
	 * @return ArrayList<PosInvoice> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<PosInvoice> getNotSentList ( long siteId, java.util.Date trxDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<PosInvoice> l;
		int index = 0;
		int recCounter = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TRN_NUMBER, to_char(x.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE, 'CONTADO' TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUSTOMER_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.INVOICE_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CASH_ID,");
			buffer.append(" x.CASH_CONTROL_ID, x.IDENTITY_TYPE, x.IDENTITY_NUMBER");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" POS_CASH_REGISTERS c,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" POS_TRANSACTIONS x");

		    buffer.append(" where exists ( select 1");
		    buffer.append(" from POS_COLLECTIONS ch,");
		    buffer.append(" POS_COLLECTION_ITEMS ci");
		    buffer.append(" where ch.CASH_REGISTER_ID = ci.CASH_REGISTER_ID");
		    buffer.append(" and ch.CASH_CONTROL_ID = ci.CASH_CONTROL_ID");
		    buffer.append(" and ch.IDENTIFIER = ci.COLLECTION_ID");
		    buffer.append(" and ci.CASH_REGISTER_ID = x.CASH_ID");
		    buffer.append(" and ci.CASH_CONTROL_ID = x.CASH_CONTROL_ID");
		    buffer.append(" and ci.INVOICE_ID = x.IDENTIFIER )");
			
		    buffer.append(" and not exists ( select 1");
		    buffer.append(" from POS_TRX_EB_BATCH_ITEMS b");
		    buffer.append(" where upper(nvl(b.RESULT_STATUS, 'Rechazado')) = 'APROBADO'");
		    buffer.append(" and b.CASH_REGISTER_ID = x.CASH_ID");
		    buffer.append(" and b.CASH_CONTROL_ID = x.CASH_CONTROL_ID");
		    buffer.append(" and b.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
			buffer.append(" and c.IDENTIFIER = x.CASH_ID");
			buffer.append(" and x.CANCELLED_ON is null");
			buffer.append(" and x.STAMP_ID in (60035, 60037, 60038)");
			// variable parameters
			if (siteId != 0) {
				buffer.append(" and x.SITE_ID = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TRN_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRN_DATE >= ?");
			}
			// order by
			buffer.append(" order by x.TRN_DATE desc");

			//System.out.println("**********getNotSentList");
			//System.out.println(buffer.toString());
			//System.out.println("param. en el DAO: " + cashId + " - " + fromDate + " - " + toDate +
			//		" - " + fromNumber + " - " + toNumber);
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

			l = new ArrayList<PosInvoice>();

			while (rs.next()) {
				dataFound = true;
				PosInvoice o = new PosInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setCashId(rs.getLong("CASH_ID"));
				o.setControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setTxNumber(rs.getString("TRN_NUMBER"));
				d = sdf.parse(rs.getString("TRN_DATE"));
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
				o.setIdentityNumber(rs.getString("IDENTITY_NUMBER"));
				o.setIdentityType(rs.getString("IDENTITY_TYPE"));
				o.setIssuePointId(rs.getLong("CUSTOMER_ID"));
				o.setIssuePointId(rs.getLong("CUSTOMER_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("INVOICE_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				//
				if (o.getTaxNumber() != null) {
					o.setIdNumber(o.getTaxNumber());
				} else {
					o.setIdNumber(o.getCustomerNumber());
				}
				//System.out.println("agregando: " + o.getTxNumber());
				l.add(o);
				//
				recCounter++;
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
	 * Obtener la lista de facturas cuya representacion grafica aun no ha sido generada,
	 * correspondiente a una caja dentro de un tango de fechas.
	 * @param cashId - Idetificador de caja de ventas al detalle
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<PosInvoice> - Lista de facturas que aun no tienen representacion grafica
	 */
	public static ArrayList<PosInvoice> getNotRenderedList ( 
			long cashId, 
			java.util.Date fromDate, 
			java.util.Date toDate ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<PosInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TRN_NUMBER, to_char(x.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE, 'CONTADO' TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUSTOMER_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.INVOICE_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CASH_ID,");
			buffer.append(" x.CASH_CONTROL_ID");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" POS_CASH_REGISTERS c,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" POS_TRANSACTIONS x");

			buffer.append(" where s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
			buffer.append(" and c.IDENTIFIER = x.CASH_ID");
			buffer.append(" and x.CANCELLED_ON is null");			
			buffer.append(" and x.EB_QR_CODE is not null");
			buffer.append(" and x.EB_GR_FILE is null");

			if (cashId != 0) {
				buffer.append(" and x.CASH_ID = ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRN_DATE >= ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TRN_DATE < ?");
			}
			//System.out.println("**********getNotRenderedList");
			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (cashId != 0) {
				index++;
				ps.setLong(index, cashId);
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

			l = new ArrayList<PosInvoice>();

			while (rs.next()) {
				dataFound = true;
				PosInvoice o = new PosInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setCashId(rs.getLong("CASH_ID"));
				o.setControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setTxNumber(rs.getString("TRN_NUMBER"));
				d = sdf.parse(rs.getString("TRN_DATE"));
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
				o.setIssuePointId(rs.getLong("CUSTOMER_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("INVOICE_TYPE_ID"));
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
	 * @param cashId - Idetificador de caja de ventas al detalle
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<PosInvoice> - Lista de facturas que aun no tienen representacion grafica
	 */
	public static ArrayList<PosInvoice> getRenderedList ( 
			long cashId, 
			String taxNumber,
			String identityNumber,
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
		ArrayList<PosInvoice> l;
		int index = 0;

		try {
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TRN_NUMBER, to_char(x.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE, 'CONTADO' TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUSTOMER_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.INVOICE_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CASH_ID,");
			buffer.append(" x.CASH_CONTROL_ID, x.E_MAIL, x.EB_GR_FILE");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" POS_CASH_REGISTERS c,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" POS_TRANSACTIONS x");

			buffer.append(" where s.IDENTIFIER = x.SITE_ID");
            buffer.append(" and t.TRX_TYPE = 'FACTURA'");
			buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
			buffer.append(" and c.IDENTIFIER = x.CASH_ID");
			buffer.append(" and ( x.TAX_PAYER_NUMBER is not null or x.IDENTITY_NUMBER is not null )");			
			buffer.append(" and x.EB_CONTROL_CODE is not null");

			if (cashId != 0) {
				buffer.append(" and x.CASH_ID = ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRN_DATE >= ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TRN_DATE < ?");
			}
			if (identityNumber != null) {
				buffer.append(" and x.IDENTITY_NUMBER = ?");
			}
			if (taxNumber != null) {
				buffer.append(" and x.TAX_PAYER_NUMBER = ?");
			}
			buffer.append(" order by x.TAX_PAYER_NAME");

			//System.out.println("**********getRenderedList");
			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if (cashId != 0) {
				index++;
				ps.setLong(index, cashId);
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
			if (identityNumber != null) {
				index++;
				ps.setString(index, identityNumber);
			}
			if (taxNumber != null) {
				index++;
				ps.setString(index, taxNumber);
			}
			rs = ps.executeQuery();

			l = new ArrayList<PosInvoice>();

			while (rs.next()) {
				dataFound = true;
				PosInvoice o = new PosInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setCashId(rs.getLong("CASH_ID"));
				o.setControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setTxNumber(rs.getString("TRN_NUMBER"));
				d = sdf.parse(rs.getString("TRN_DATE"));
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
				o.setIssuePointId(rs.getLong("CUSTOMER_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("INVOICE_TYPE_ID"));
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
				//System.out.println("getRenderedList - agregando: " + o.getTxNumber());
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
	 * Obtener la lista de facturas POS que han sido anuladas de manera a enviar los eventos 
	 * de cancelacion correspondientes.
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<PosInvoice> - Lista de facturas anuladas
	 */
	public static ArrayList<PosInvoice> getCanceledList ( java.util.Date trxDate, String trxType ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<PosInvoice> l;
		int index = 0;

		try {
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TRN_NUMBER, to_char(x.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE, 'CONTADO' TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUSTOMER_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.INVOICE_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CASH_ID,");
			buffer.append(" x.CASH_CONTROL_ID, COMMENTS");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" POS_CASH_REGISTERS c,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" POS_TRANSACTIONS x");

			buffer.append(" where s.IDENTIFIER = x.SITE_ID");
			buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
			buffer.append(" and c.IDENTIFIER = x.CASH_ID");
			buffer.append(" and x.EB_CONTROL_CODE is not null");			

			//buffer.append(" and x.IDENTIFIER in (238414, 238417)");			

			if ( trxType != null) {
	            buffer.append(" and t.TRX_TYPE = ?");				
			}
			if (toDate != null) {
				buffer.append(" and x.CANCELLED_ON < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.CANCELLED_ON >= ?");
			}
			//System.out.println("**********getCancelledList");
			//System.out.println(buffer.toString());
			//
			ps = conn.prepareStatement(buffer.toString());
			//
			if ( trxType != null) {
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

			l = new ArrayList<PosInvoice>();

			while (rs.next()) {
				dataFound = true;
				PosInvoice o = new PosInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setCashId(rs.getLong("CASH_ID"));
				o.setControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setTxNumber(rs.getString("TRN_NUMBER"));
				d = sdf.parse(rs.getString("TRN_DATE"));
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
				o.setIssuePointId(rs.getLong("CUSTOMER_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("INVOICE_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setCancelComments(rs.getString("COMMENTS"));
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
	 * Obtener la lista de transacciones Receivables que fueron anuladas sin haber sido emitidas 
	 * ni informadas a la entidad de impuestos a efectos de informar los eventos de inutilizacion 
	 * correspondientes.
	 * @param trxType - Tipo de transaccion
	 * @param fromDate - Fecha inicial del rango
	 * @param toDate - Fecha final del rango
	 * @return ArrayList<PosInvoice> - Lista de facturas pendientes de transmision
	 */
	public static ArrayList<PosInvoice> getDisabledList ( java.util.Date trxDate, String trxType ) {
		Connection conn =  null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean dataFound = false;
		java.util.Date fromDate = null;    
		java.util.Date toDate = null;    
		java.util.Date d = null;    
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String customerName;
		ArrayList<PosInvoice> l;
		int index = 0;
		int counter = 0;

		try {
			fromDate = trxDate;
			toDate = UtilPOS.addDaysToDate(fromDate, 1);
			
			conn = Util.getConnection();
			if (conn == null) {
				throw new SQLException("No hay conexion con base de datos");
			}

			StringBuffer buffer = new StringBuffer();
			
			buffer.append("select x.IDENTIFIER, x.TRN_NUMBER, to_char(x.TRN_DATE, 'dd/mm/yyyy hh24:mi:ss') TRN_DATE, 'CONTADO' TRX_CONDITION,");
			buffer.append(" x.AMOUNT, x.IDENTITY_NUMBER, x.CUSTOMER_NAME, x.TAX_PAYER_NUMBER,");
			buffer.append(" x.TAX_PAYER_NAME, x.CUSTOMER_ID, x.CUSTOMER_SITE_ID, x.CURRENCY_ID,");
			buffer.append(" x.STAMP_ID, x.UNIT_ID, x.ORG_ID, x.SITE_ID,");
			buffer.append(" s.NAME SITE_NAME, x.ISSUE_POINT_ID, x.INVOICE_TYPE_ID, t.NAME TX_TYPE_NAME,");
			buffer.append(" x.EB_SIGN_DATE, x.EB_SEND_DATE, x.EB_CONTROL_CODE, x.CASH_ID,");
			buffer.append(" x.CASH_CONTROL_ID, COMMENTS, x.TRN_BASE_NUMBER, m.STAMP_NO,");
			buffer.append(" to_char(x.CANCELLED_ON, 'dd/mm/yyyy hh24:mi:ss') CANCELLED_ON");
		
			buffer.append(" from FND_SITES s,");
			buffer.append(" POS_CASH_REGISTERS c,");
			buffer.append(" RCV_INVOICE_TYPES t,");
			buffer.append(" POS_STAMP_RECORDS m,");
			buffer.append(" POS_TRANSACTIONS x");

			buffer.append(" where not exists ( select 1");
			buffer.append(" from POS_COLLECTIONS l");
			buffer.append(" where l.CASH_REGISTER_ID = x.CASH_ID");
			buffer.append(" and l.CASH_CONTROL_ID = x.CASH_CONTROL_ID");
			buffer.append(" and l.INVOICE_ID = x.IDENTIFIER )");
			
			buffer.append(" and not exists ( select 1");
		    buffer.append(" from POS_EB_EVENT_ITEMS_LOG l");
			buffer.append(" where l.EVENT_TYPE_ID = 2");
			buffer.append(" and l.CASH_REGISTER_ID = x.CASH_ID");
			buffer.append(" and l.CASH_CONTROL_ID = x.CASH_CONTROL_ID");
			buffer.append(" and l.TRANSACTION_ID = x.IDENTIFIER )");
			
			buffer.append(" and s.IDENTIFIER = x.SITE_ID");
			buffer.append(" and t.IDENTIFIER = x.INVOICE_TYPE_ID");
			buffer.append(" and m.IDENTIFIER = x.STAMP_ID");
			buffer.append(" and c.IDENTIFIER = x.CASH_ID");
			// por las caracteristicas de las transacciones POS, las anulacione seran
			// procesadas como eventos de inutilizacion
			//buffer.append(" and x.CANCELLED_ON is null");			

			if (trxType != null) {
	            buffer.append(" and t.TRX_TYPE = ?");
			}
			if (toDate != null) {
				buffer.append(" and x.TRN_DATE < ?");
			}
			if (fromDate != null) {
				buffer.append(" and x.TRN_DATE >= ?");
			}
			System.out.println("**********getDisabledList");
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

			l = new ArrayList<PosInvoice>();

			while (rs.next()) {
				dataFound = true;
				PosInvoice o = new PosInvoice();
				//
				o.setInvoiceId(rs.getLong("IDENTIFIER"));
				o.setCashId(rs.getLong("CASH_ID"));
				o.setControlId(rs.getLong("CASH_CONTROL_ID"));
				o.setTxNumber(rs.getString("TRN_NUMBER"));
				d = sdf.parse(rs.getString("TRN_DATE"));
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
				o.setIssuePointId(rs.getLong("CUSTOMER_SITE_ID"));
				o.setCurrencyId(rs.getLong("CURRENCY_ID"));
				o.setStampId(rs.getLong("STAMP_ID"));
				o.setUnitId(rs.getLong("UNIT_ID"));
				o.setOrgId(rs.getLong("ORG_ID"));
				o.setSiteId(rs.getLong("SITE_ID"));
				o.setSiteName(rs.getString("SITE_NAME"));
				o.setIssuePointId(rs.getLong("ISSUE_POINT_ID"));				
				o.setTxTypeId(rs.getLong("INVOICE_TYPE_ID"));
				o.setTxTypeName(rs.getString("TX_TYPE_NAME"));
				o.setSignDate(rs.getDate("EB_SIGN_DATE"));
				o.setSendDate(rs.getDate("EB_SEND_DATE"));
				o.setControlCode(rs.getString("EB_CONTROL_CODE"));
				o.setCancelComments(rs.getString("COMMENTS"));
				o.setBaseNumber(rs.getInt("TRN_BASE_NUMBER"));
			    //001-001-0002345
			    //012345678901234
			    o.setEstabCode(o.getTxNumber().substring(0, 3));
			    o.setIssuePointCode(o.getTxNumber().substring(4, 7));
			    o.setTransactionNo(o.getTxNumber().substring(8));
			    if (o.getBaseNumber() == 0) {
			    	    o.setBaseNumber(Integer.parseInt(o.getTransactionNo()));
			    }
				o.setStampNo(rs.getString("STAMP_NO"));
				if (rs.getString("CANCELLED_ON") != null) {
				    d = sdf.parse(rs.getString("CANCELLED_ON"));
				    o.setCancelledOn(d);
		        }

				System.out.println("agregando: " + o.getTxNumber());
				l.add(o);
				// cargar una sola transaccion a efectos de prueba
				/*
				counter++;
				if (counter == 1) {
					break;
				}
				*/
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
	
}
