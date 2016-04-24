package blueprintlabs.zulu.resources;

/**
 * Created by Ahmet Burak on 23.4.2016.
 */
public class Action {
    public final String id;
    public final String content;
    public final String details;

    public Action(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }
}
