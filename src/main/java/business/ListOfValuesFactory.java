package business;

import dao.CashRegisterDAO;
import dao.FndSitesDAO;
import dao.ProblemaDatosException;
import gui.ListOfValuesModel;
import pojo.ListOfValuesParameters;

public class ListOfValuesFactory implements LOVFactory {

	@Override
	public ListOfValuesModel getListOfValues(ListOfValuesParameters p) throws ProblemaDatosException, Exception {
		// Conjuntos de datos Avanza
	    if (p.getEntity().equals("SUCURSALES")) {
			return new ListOfValuesModel(FndSitesDAO.getList(p.getSearchString(), p.getUnitId()));
	    }
	    if (p.getEntity().equals("CAJAS")) {
			return new ListOfValuesModel(CashRegisterDAO.getList(p.getSearchString(), p.getUnitId()));
	    }
	    /*
		// Conjuntos de datos x-pertPOS
	    if (p.getEntity().equals("CAJEROS")) {
			return new ListOfValuesModel(CashierDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("SUPERVISORES")) {
			return new ListOfValuesModel(HeadTellerDAO.getList(p.getFilters()[0]));
	    }
		if (p.getEntity().equals("CLIENTES")) {
			return new ListOfValuesModel(CustomerDAO.getList(p.getFilters()[0]));
		}
	    if (p.getEntity().equals("CONTADORES")) {
			return new ListOfValuesModel(PosPhysInvWorkerDAO.getList(p.getFilters()[0]));	
	    }
	    if (p.getEntity().equals("CONTROLES DE CAJA")) {
			return new ListOfValuesModel(CashControlDAO.getList(p.getFilters()[0], p.getFilters()[1], p.getUnitId()));
	    }
	    if (p.getEntity().equals("EMPLEADOS")) {
			return new ListOfValuesModel(EmployeeDAO.getList(p.getFilters()[0]));	
	    }
	    if (p.getEntity().equals("ENTIDADES BANCARIAS")) {
			return new ListOfValuesModel(FinancialInstitutionDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("ENTIDADES EMISORAS OC")) {
			return new ListOfValuesModel(PosPurchOrdersEntitiesDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("FORMAS DE PAGO")) {
			return new ListOfValuesModel(PaymentTermDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("MARCAS DE TARJETA")) {
			return new ListOfValuesModel(CreditCardMarkDAO.getList(p.getFilters()[0]));
	    }
		if (p.getEntity().equals("MONEDAS")) {
			return new ListOfValuesModel(CurrencyDAO.getList(p.getFilters()[0]));
		}
	    if (p.getEntity().equals("PROCESADORAS DE TARJETA")) {
			return new ListOfValuesModel(CardProcessorDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("PRODUCTOS")) {
			return new ListOfValuesModel(PosProductDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("TIPO COMPROBANTE VENTAS")) {
			return new ListOfValuesModel(InvoiceTypeDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("USUARIOS")) {
			return new ListOfValuesModel(UserCredentialsDAO.getList(p.getFilters()[0]));
	    }
	    if (p.getEntity().equals("VENDEDORES")) {
			return new ListOfValuesModel(PosSellersDAO.getList(p.getFilters()[0]));
	    }
	    */
	    return null;
	}

}
