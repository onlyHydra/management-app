package concurs.model;

import java.io.Serializable;
import java.util.Objects;

public class Inscris implements Serializable {
    private Participant participant;
    private Concurs concurs;

    public Inscris(Participant participant, Concurs concurs) {
        this.participant = participant;
        this.concurs = concurs;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Concurs getConcurs() {
        return concurs;
    }

    public void setConcurs(Concurs concurs) {
        this.concurs = concurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscris)) return false;
        Inscris inscris = (Inscris) o;
        return Objects.equals(participant, inscris.participant) &&
                Objects.equals(concurs, inscris.concurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participant, concurs);
    }

    @Override
    public String toString() {
        return "Inscris{" +
                "participant=" + participant +
                ", concurs=" + concurs +
                '}';
    }
}
