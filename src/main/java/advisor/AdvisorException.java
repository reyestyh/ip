package advisor;

/**
 * Exception to handle Advisor specific errors (i.e. input parsing)
 */
public class AdvisorException extends Exception{

    public AdvisorException(String message) {
        super(message);
    }

}
