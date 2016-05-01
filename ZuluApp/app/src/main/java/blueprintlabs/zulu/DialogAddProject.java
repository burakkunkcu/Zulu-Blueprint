package blueprintlabs.zulu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import blueprint.zulu.util.*;

/**
 * Created by Ahmet Burak on 22.4.2016.
 * Add project dialog pop-up
 */
public class DialogAddProject extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View view = inflater.inflate(R.layout.dialog_signin, null);

        TextView tw = (TextView) view.findViewById(R.id.header);
        tw.setText("New Project");
        final EditText name = (EditText) view.findViewById(R.id.username);
        final EditText desc = (EditText) view.findViewById(R.id.password);
        EditText unused = (EditText) view.findViewById(R.id.date);
        unused.setVisibility(View.GONE);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.createTask, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ActivityProjects activity = (ActivityProjects) getActivity();
                        activity.addProject(name.getText().toString(), desc.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddProject.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}