package sn.ec2lt.banque.exception;

public class SoldeInsuffisantException extends RuntimeException{
    public SoldeInsuffisantException(String message){
        super(message);
    }
}
