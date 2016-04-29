package blueprintlabs.zulu.resources;

/**
 * Created by Ahmet Burak on 28.4.2016.
 */
public class Message {
    public boolean left;
    public String message;
    public String sender;

    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }
}
