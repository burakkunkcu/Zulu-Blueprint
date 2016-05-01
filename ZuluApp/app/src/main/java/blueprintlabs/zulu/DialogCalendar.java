package blueprintlabs.zulu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blueprint.zulu.util.*;

/**
 * Created by Ahmet Burak on 26.4.2016.
 * Calendar popup that shows events in a day
 */
public class DialogCalendar extends DialogFragment{


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.calendar_box_layout, null);

        Bundle args = getArguments();

        ArrayList<Task> day_tasks = new ArrayList<Task>();

        ListView lw = (ListView) view.findViewById(R.id.tasks);

        if(day_tasks.size() > 0) {
            lw.setAdapter(new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, day_tasks));
        }
        else{
            ArrayList<String> notask = new ArrayList<String>();
            notask.add("No events or deadlines today!");
            lw.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, notask));
        }

        TextView date = (TextView) view.findViewById(R.id.dateName);
        TextView month = (TextView) view.findViewById(R.id.monthName);
        date.setText("" + args.getInt("date"));
        month.setText(args.getString("month"));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
        return builder.create();
    }
}
