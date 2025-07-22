package org.devquality.trukea.persistance.entities;

public class ZonaSeguraHorario {
    private Long idHorario;
    private String diaDeSemana;
    private String horaApertura;
    private String horaCierre;
    private Long idZona;

    public ZonaSeguraHorario() {}

    public ZonaSeguraHorario(Long idHorario, String diaDeSemana, String horaApertura, String horaCierre, Long idZona) {
        this.idHorario = idHorario;
        this.diaDeSemana = diaDeSemana;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.idZona = idZona;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

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

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }
}
