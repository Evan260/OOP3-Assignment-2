package utilities;

public class EmptyQueueException extends Exception{
	
    public EmptyQueueException() {
        super("Queue is empty. Cannot dequeue.");
    }
}
