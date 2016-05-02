package blueprintlabs.zulu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ahmet Burak on 2.5.2016.
 */
public class DialogAddUser extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View view = inflater.inflate(R.layout.dialog_newproject, null);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.createTask, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog f = (Dialog) dialog;

                        final EditText name = (EditText) f.findViewById(R.id.username);
                        ActivityProjectView activity = (ActivityProjectView) getActivity();
                        activity.addUser(name.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogAddUser.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
