package concurs.model;

import java.io.Serializable;
import java.util.Objects;

public class Participant extends Entity<Integer> implements Serializable {
    private String nume;
    private String echipa;
    private Integer capacitate;
    private Integer idCursa;


    public Participant(Integer integer, String nume, String echipa, Integer capacitate, Integer idCursa) {
        super(integer);
        this.nume = nume;
        this.echipa = echipa;
        this.capacitate = capacitate;
        this.idCursa = idCursa;
    }

    public String getNume() {
        return nume;
    }

    public Integer getIdCursa() {
        return idCursa;
    }

    public void setIdCursa(Integer idCursa) {
        this.idCursa = idCursa;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public Integer getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(Integer capacitate) {
        this.capacitate = capacitate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;
        if (!super.equals(o)) return false;
        Participant that = (Participant) o;
        return Objects.equals(nume, that.nume) &&
                Objects.equals(echipa, that.echipa) &&
                Objects.equals(capacitate, that.capacitate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nume, echipa, capacitate);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "nume='" + nume + '\'' +
                ", echipa='" + echipa + '\'' +
                ", capacitate=" + capacitate +
                '}';
    }
}
