package eunAI.exception;

/**
 * Represents an exception that is thrown when a task is created without a description.
 * Inherits from {@link EunAIException} to handle task-specific input errors.
 */
public class EmptyTaskException extends EunAIException {

    /**
     * Constructs an {@code EmptyTaskException} with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public EmptyTaskException(String message) {
        super(message);
    }
}
