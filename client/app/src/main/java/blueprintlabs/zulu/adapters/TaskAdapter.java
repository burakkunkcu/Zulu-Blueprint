package blueprintlabs.zulu.adapters;



import java.util.List;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import blueprintlabs.zulu.DialogAddTask;
import blueprintlabs.zulu.FragmentTasks;
import blueprintlabs.zulu.R;
import blueprint.zulu.util.*;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User}.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final FragmentTasks.OnListFragmentInteractionListener mListener;
    private final int VIEW_TYPE_FOOTER = 1;
    private final int VIEW_TYPE_CELL = 0;
    FragmentTasks context;

    public TaskAdapter(List<Task> items, FragmentTasks.OnListFragmentInteractionListener listener, FragmentTasks aa) {
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
            holder.mIdView.setText(mValues.get(position).getDesc());
            holder.mContentView.setText(mValues.get(position).getDate().toString());
            holder.iV.setVisibility(View.GONE);

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
        public Task mItem;
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

    public void insert(int position, Task data) {
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

