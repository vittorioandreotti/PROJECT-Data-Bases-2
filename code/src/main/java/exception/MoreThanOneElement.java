package exception;

public class MoreThanOneElement extends Exception {
    private String message;

    public MoreThanOneElement() {
        message = "Another Optional Product with this name found: please change it";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
