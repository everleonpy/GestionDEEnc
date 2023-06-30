package business;

import dao.ProblemaDatosException;
import gui.ListOfValuesModel;
import pojo.ListOfValuesParameters;

public interface LOVFactory {
    public ListOfValuesModel getListOfValues (ListOfValuesParameters p) throws ProblemaDatosException, Exception;
}
