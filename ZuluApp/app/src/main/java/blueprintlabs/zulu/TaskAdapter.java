package blueprintlabs.zulu;



import java.util.List;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import blueprintlabs.zulu.resources.User;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<User> mValues;
    private final PersonFragment.OnListFragmentInteractionListener mListener;
    private final int VIEW_TYPE_FOOTER = 1;
    private final int VIEW_TYPE_CELL = 0;
    PersonFragment context;

    public TaskAdapter(List<User> items, PersonFragment.OnListFragmentInteractionListener listener, PersonFragment aa) {
        mValues = items;
        mListener = listener;
        context = aa;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CELL) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_layout, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_last_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_CELL){
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).name);
            holder.mContentView.setText(mValues.get(position).desc);
            holder.iV.setImageResource(mValues.get(position).imageID);

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

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                        AddTaskDialog dialogBox = new AddTaskDialog();
                        FragmentActivity activity = (FragmentActivity)(context.getActivity());
                        FragmentManager fm = activity.getSupportFragmentManager();
                        dialogBox.show(fm, "missiles");
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position){
        return (position == mValues.size() - 1) ? VIEW_TYPE_FOOTER : VIEW_TYPE_CELL;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public User mItem;
        ImageView iV;

        CardView cv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cv = (CardView) view.findViewById(R.id.cardView);
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.description);
            iV = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void insert(int position, User data) {
        mValues.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(User data) {
        int position = mValues.indexOf(data);
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

