package concurs.model;

import com.sun.tools.javac.comp.Resolve;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "Concurs")
public class Concurs  implements Serializable {

    private Integer id;
    private String nume;
    private Double distanta;
    private Integer capacitate;
    private Integer numar;

    public Concurs(){}

    public Concurs(Integer idCursa, String nume, Double dist, Integer cap, Integer nr) {
        this.id = idCursa;
        this.nume = nume;
        this.distanta = dist;
        this.capacitate = cap;
        this.numar = nr;
    }

    @Id
    @Column(name = "IDCursa")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Nume")
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Column(name = "Distanta")
    public Double getDistanta() {
        return distanta;
    }

    public void setDistanta(Double distanta) {
        this.distanta = distanta;
    }

    @Column(name = "Capacitate")
    public Integer getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(Integer capacitate) {
        this.capacitate = capacitate;
    }

    @Column(name = "Numar")
    public Integer getNumar() {
        return numar;
    }

    public void setNumar(Integer numar) {
        this.numar = numar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concurs)) return false;
        Concurs concurs = (Concurs) o;
        return Objects.equals(id, concurs.id) &&
                Objects.equals(nume, concurs.nume) &&
                Objects.equals(distanta, concurs.distanta) &&
                Objects.equals(capacitate, concurs.capacitate) &&
                Objects.equals(numar, concurs.numar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, distanta, capacitate, numar);
    }

    @Override
    public String toString() {
        return capacitate.toString();
    }
}
