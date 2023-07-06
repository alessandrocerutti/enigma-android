package it.ac.enigma.rest;

public class QrCodeStepRequestBody {

    String qrcode;
    Long idSquadra;

    public QrCodeStepRequestBody() {
    }

    public QrCodeStepRequestBody(String qrcode, Long idSquadra) {
        setQrcode(qrcode);
        setIdSquadra(idSquadra);
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Long getIdSquadra() {
        return idSquadra;
    }

    public void setIdSquadra(Long idSquadra) {
        this.idSquadra = idSquadra;
    }
}
