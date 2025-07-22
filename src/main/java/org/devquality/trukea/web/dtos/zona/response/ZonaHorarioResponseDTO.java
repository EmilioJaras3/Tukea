package org.devquality.trukea.web.dtos.zona.response;

public class ZonaHorarioResponseDTO {
    public String diaDeSemana;
    public String horaApertura;
    public String horaCierre;

    public ZonaHorarioResponseDTO() {}

    public ZonaHorarioResponseDTO(String diaDeSemana, String horaApertura, String horaCierre) {
        this.diaDeSemana = diaDeSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // Getters y setters
    public String getDiaDeSemana() {
        return diaDeSemana;
    }

    public void setDiaDeSemana(String diaDeSemana) {
        this.diaDeSemana = diaDeSemana;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }
}