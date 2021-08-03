package concurs.services;

public class ConcursException extends Exception {

    public ConcursException(){
        super();
    }

    public ConcursException(String mesaj){
        super(mesaj);
    }
}
