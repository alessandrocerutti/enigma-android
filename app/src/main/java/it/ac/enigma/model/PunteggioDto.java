package it.ac.enigma.model;

import java.io.Serializable;

public class PunteggioDto implements Serializable {

    String descrizione;
    String tipologia;
    Long squadraId;
    Long cacciaId;
    Integer punteggio;

    public PunteggioDto() {
    }

    public PunteggioDto(String descrizione, Integer punteggio,String tipologia, Long idSquadra, Long idCaccia) {
        this.descrizione = descrizione;
        this.punteggio = punteggio;
        this.tipologia = tipologia;
        this.squadraId = idSquadra;
        this.cacciaId = idCaccia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getSquadraId() {
        return squadraId;
    }

    public void setSquadraId(Long squadraId) {
        this.squadraId = squadraId;
    }

    public Long getCacciaId() {
        return cacciaId;
    }

    public void setCacciaId(Long cacciaId) {
        this.cacciaId = cacciaId;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        this.punteggio = punteggio;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}
