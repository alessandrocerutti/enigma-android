package it.ac.enigma.rest;

import java.util.List;

import it.ac.enigma.model.ProvaDto;

public class ResponseSquadre {
    List<ProvaDto> squadraList;

    public List<ProvaDto> getSquadraList() {
        return squadraList;
    }

    public void setSquadraList(List<ProvaDto> squadraList) {
        this.squadraList = squadraList;
    }
}
