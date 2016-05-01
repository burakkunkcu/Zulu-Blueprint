package blueprintlabs.zulu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

import blueprint.zulu.util.*;

/**
 * Created by Ahmet Burak on 22.4.2016.
 * Add task pop-up
 */
public class DialogAddTask extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_signin, null);

        final EditText name = (EditText) view.findViewById(R.id.username);
        final EditText desc = (EditText) view.findViewById(R.id.password);
        final DatePicker date = (DatePicker) view.findViewById(R.id.datePicker);

        name.setHint("Project Name");
        desc.setHint("Project Description");

        final Date datePicked = new Date(date.getYear() - 1900, date.getMonth(), date.getDayOfMonth());

        final String[] mails = desc.getText().toString().split(",");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
                // Add action buttons
                .setPositiveButton(R.string.createTask, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityProjectView activity = (ActivityProjectView) getActivity();
                        for(String s : mails) {
                            activity.addTaskMethod(name.getText().toString(), s, datePicked);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddTask.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}