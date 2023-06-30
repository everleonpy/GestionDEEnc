package tools;

import static com.roshka.sifen.internal.Constants.SIFEN_CURRENT_VERSION;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;

import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;

import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.internal.util.SifenExceptionUtil;
import com.roshka.sifen.internal.util.SifenUtil;

import pojo.EdIdBuildingParams;
import pojo.QrBuildingParams;

public class DEUtils {
	
    public String obtenerCDC ( EdIdBuildingParams txData ) throws SifenException {
        // Se intenta la generación del CDC
    	    String CDC;
        String controlCode;
        String cd;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            controlCode = SifenUtil.leftPad(String.valueOf(txData.getiTiDE()), '0', 2) +
                    txData.getdRucEm() +
                    txData.getdDVEmi() +
                    txData.getdEst() +
                    txData.getdPunExp() +
                    txData.getdNumDoc() +
                    txData.getiTipCont() +
                    txData.getdFeEmiDE().format(formatter) +
                    txData.getiTipEmi() +
                    txData.getdCodSeg();
        } catch (Exception e) {
            throw SifenExceptionUtil.fieldNotFound("Se produjo un error al generar el CDC. Verificar si todos los campos necesarios están presentes.");
        }

        // Se setean los valores generados en sus lugares correspondientes dentro de la clase
        cd = SifenUtil.generateDv(controlCode);
        CDC = controlCode + cd;

        return CDC;
    }
	
	
    public String generateQRLink ( SignedInfo signedInfo, 
    		                           SifenConfig sifenConfig, 
    		                           QrBuildingParams txData ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();

        queryParams.put("nVersion", SIFEN_CURRENT_VERSION);
        queryParams.put("Id", txData.getId());
        queryParams.put("dFeEmiDE", SifenUtil.bytesToHex(txData.getdFeEmiDE().format(formatter).getBytes(StandardCharsets.UTF_8)));

        if (txData.getiNatRec() == 1) {
            queryParams.put("dRucRec", txData.getdRucRec());
        } else if (txData.getiTiOpe() != 4 && txData.getdNumIDRec() != null) {
            queryParams.put("dNumIDRec", txData.getdNumIDRec());
        } else {
            queryParams.put("dNumIDRec", "0");
        }

        if (txData.getiTiDE() != 7) {
            queryParams.put("dTotGralOpe", String.valueOf(txData.getdTotGralOpe()));
            queryParams.put("dTotIVA",
                    txData.getiTImp() == 1 || txData.getiTImp() == 5
                            ? String.valueOf(txData.getdTotIVA())
                            : "0"
            );
        } else {
            queryParams.put("dTotGralOpe", "0");
            queryParams.put("dTotIVA", "0");
        }

        queryParams.put("cItems", String.valueOf(txData.getCantidadItems()));

        byte[] digestValue = Base64.getEncoder().encode(((Reference) signedInfo.getReferences().get(0)).getDigestValue());
        queryParams.put("DigestValue", SifenUtil.bytesToHex(digestValue));
        queryParams.put("IdCSC", sifenConfig.getIdCSC());

        String urlParamsString = SifenUtil.buildUrlParams(queryParams);
        String hashedParams = SifenUtil.sha256Hex(urlParamsString + sifenConfig.getCSC());

        return sifenConfig.getUrlConsultaQr() + urlParamsString + "&cHashQR=" + hashedParams;
    }

}
