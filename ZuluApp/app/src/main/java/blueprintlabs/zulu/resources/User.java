package blueprintlabs.zulu.resources;

/**
 * Created by Ahmet Burak on 23.4.2016.
 */
public class User {

    public final String id;
    public final String content;
    public final String details;
    public int imageID;

    public User(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    public User(String id, String details, int i) {
        this.id = id;
        this.content = details;
        this.details = details;
        imageID = i;
    }



    @Override
    public String toString() {
        return content;
    }
}
