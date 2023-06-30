package util;

import java.util.Arrays;


public enum TransmissionLog {
    ENVIO_TRANSACCION((short) 1, "Envio Transaccion a Sifen"),
    GENERACION_KUDE((short) 2, "Envio KuDE al receptor"),
    GENERACION_XML((short) 3, "Envio KuDE al receptor"),
    ENVIO_KUDE((short) 4, "Envio KuDE al receptor"),
    ENVIO_XML((short) 5, "Envio XML al receptor"),
    ANULACION_TRANSACCION((short) 6, "Anulacion de transaccion");

    private short val;
    private String descripcion;

    TransmissionLog(short val, String descripcion) {
        this.val = val;
        this.descripcion = descripcion;
    }

    public static TransmissionLog getByVal(short val) {
        return Arrays.stream(TransmissionLog.values()).filter(e -> e.val == val).findFirst().orElse(null);
    }

    public short getVal() {
        return val;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "{\"val\": " + val + ", \"descripcion\": \"" + descripcion + "\"}";
    }

}
