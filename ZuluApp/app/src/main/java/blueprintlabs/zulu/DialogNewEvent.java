package blueprintlabs.zulu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahmet Burak on 27.4.2016.
 * New calendar event pop-up dialog.
 */
public class DialogNewEvent extends DialogFragment {

    private final String[] HOURS = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00",
                                    "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};

    EditText textInput;
    Spinner startingTime;
    Spinner finishingTime;
    TextView date;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_newevent, null);

        //TODO GET AVAILABLE TIMES AS AN ARRAYLIST FROM THE SERVER

        Bundle args = getArguments();

        textInput = (EditText) view.findViewById(R.id.editText);
        startingTime = (Spinner) view.findViewById(R.id.spinner1);
        finishingTime = (Spinner) view.findViewById(R.id.spinner2);
        date = (TextView) view.findViewById(R.id.date);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, HOURS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startingTime.setAdapter(adapter);
        finishingTime.setAdapter(adapter);
        date.setText(args.getString("date"));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String start_time = (String) startingTime.getSelectedItem();
                        String end_time = (String) startingTime.getSelectedItem();
                        String desc = textInput.getText().toString();

                        //TODO SEND REQUEST TO SERVER TO CREATE NEW EVENT
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogNewEvent.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
