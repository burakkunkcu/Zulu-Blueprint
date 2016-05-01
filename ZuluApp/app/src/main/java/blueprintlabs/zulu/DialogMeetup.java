package blueprintlabs.zulu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blueprintlabs.zulu.resources.Task;

/**
 * Created by Ahmet Burak on 27.4.2016.
 * Meetup function pop-up dialog.
 */
public class DialogMeetup extends DialogFragment{

    ListView lw;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_meetup, null);

        //TODO GET AVAILABLE TIMES AS AN ARRAYLIST FROM THE SERVER

        ActivityProjectView activity = (ActivityProjectView) getActivity();


        final ArrayList<Date> avaliableHours = activity.getMeetupDates();

        lw = (ListView) view.findViewById(R.id.times);

        lw.setAdapter(new ArrayAdapter<Date>(getActivity(), android.R.layout.simple_list_item_1, avaliableHours));
        lw.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("MEETUP!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int i = avaliableHours.indexOf(lw.getSelectedItem());

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogMeetup.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
