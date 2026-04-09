package sn.ec2lt.banque.exception;

public class CompteInexistant extends RuntimeException{
    public CompteInexistant(String message){
        super(message);
    }
}
