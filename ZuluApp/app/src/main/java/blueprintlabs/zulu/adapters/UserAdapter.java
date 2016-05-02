package blueprintlabs.zulu.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import blueprintlabs.zulu.DialogAddTask;
import blueprintlabs.zulu.FragmentTasks;
import blueprintlabs.zulu.FragmentUsers;
import blueprintlabs.zulu.FragmentUsers.OnListFragmentInteractionListener;
import blueprintlabs.zulu.R;
import blueprint.zulu.util.*;

//import blueprintlabs.zulu.ItemFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> mValues;
    private final OnListFragmentInteractionListener mListener;
    FragmentUsers context;

    public UserAdapter(List<User> items, OnListFragmentInteractionListener listener, FragmentUsers context) {
        //will be used to draw add user button on
        items.add(new User("", "", "", "", new ArrayList<String>(), new ArrayList<String>(), ""));

        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position != mValues.size()) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getName());
            holder.mContentView.setText(mValues.get(position).getDesc());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        }
        else{
            holder.mItem = mValues.get(position);
            holder.mIdView.setText("Add new member");

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                        DialogAddTask dialogBox = new DialogAddTask();
                        FragmentActivity activity = (FragmentActivity)(context.getActivity());
                        FragmentManager fm = activity.getSupportFragmentManager();
                        dialogBox.show(fm, "missiles");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
