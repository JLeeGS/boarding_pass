package domain;

import java.io.Serializable;

public class BoardingPassTicket implements Serializable {
    private static final long serialVersionUID=1L;
    private BoardingPass bp;
    private Person person;

    public BoardingPass getBp() {
        return bp;
    }

    public void setBp(BoardingPass bp) {
        this.bp = bp;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BoardingPassTicket(){
        super();
    }
    public BoardingPassTicket(BoardingPass bp, Person person){
        this.bp=bp;
        this.person=person;
    }


}
