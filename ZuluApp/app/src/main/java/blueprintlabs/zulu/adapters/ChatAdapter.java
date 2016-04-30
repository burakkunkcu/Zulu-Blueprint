package blueprintlabs.zulu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import blueprintlabs.zulu.R;
import blueprintlabs.zulu.resources.Message;

/**
 * Created by Ahmet Burak on 28.4.2016.
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private Context context;
    private ArrayList<Message> messages = new ArrayList<Message>();
    private TextView message;
    private TextView sender;

    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public void add(Message message){
        messages.add(message);
        super.add(message);
    }

    public int getCount() {
        return messages.size();
    }

    public Message getItem(int index) {
        return messages.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Message chatMessageObj = getItem(position);
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.chat_left, parent, false);

        message = (TextView) view.findViewById(R.id.message);
        message.setText(chatMessageObj.message);
        sender = (TextView) view.findViewById(R.id.sender);
        sender.setText(chatMessageObj.sender);
        return view;
    }
}
