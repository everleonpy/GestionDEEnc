package sifen;

import java.util.logging.Logger;

import com.roshka.sifen.Sifen;
import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.beans.response.RespuestaConsultaDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaLoteDE;
import com.roshka.sifen.core.beans.response.RespuestaConsultaRUC;
import com.roshka.sifen.core.exceptions.SifenException;


public class QueryDocums {
    private final static Logger logger = Logger.getLogger(QueryDocums.class.toString());

    public static void setupSifenConfig() throws SifenException {
        SifenConfig sifenConfig = SifenConfig.cargarConfiguracion("test.properties");
        logger.info("Using CONFIG: " + sifenConfig);
        Sifen.setSifenConfig(sifenConfig);
    }

    public void taxNumber( String taxNumber ) throws SifenException {
        RespuestaConsultaRUC ret = Sifen.consultaRUC ( taxNumber );
        logger.info(ret.toString());
    }

    public void electronicDocument( String id ) throws SifenException {
        RespuestaConsultaDE ret = Sifen.consultaDE(id);
        logger.info(ret.toString());
    }

    public void edBatch( String id ) throws SifenException {
        RespuestaConsultaLoteDE ret = Sifen.consultaLoteDE(id);
        logger.info(ret.toString());
    }

}
