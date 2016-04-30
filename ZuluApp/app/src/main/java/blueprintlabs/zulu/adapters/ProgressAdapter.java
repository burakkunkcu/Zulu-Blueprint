package blueprintlabs.zulu.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.CardView;

import blueprintlabs.zulu.FragmentProgress.OnListFragmentInteractionListener;
import blueprintlabs.zulu.R;
import blueprintlabs.zulu.resources.Task;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    private final int TYPE_WHOLEPROG = 1;
    private final int TYPE_USERPROG = 2;
    private final int TYPE_TASK = 3;
    private final List<Task> mValues;
    private final List<Task> userTasks;
    private final OnListFragmentInteractionListener mListener;

    public ProgressAdapter(List<Task> items, List<Task> userItems, OnListFragmentInteractionListener listener) {
        mValues = items;
        userTasks = userItems;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_TASK) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item_layout, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TASK){
            holder.mItem = mValues.get(position - 2);
            holder.mIdView.setText(mValues.get(position - 2).getDesc());
            holder.pb.setMax(100);
            holder.pb.setProgress(mValues.get(position - 2).getProgress());
            holder.percentView.setText("%" + mValues.get(position - 2).getProgress());
            holder.mIdView.setText(mValues.get(position - 2).getDesc());

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

        else if(getItemViewType(position) == TYPE_USERPROG){
            int userProgress = 0;
            int i = 0;
            for (Task t : userTasks){
                userProgress += t.getProgress();
                i++;
            }
            userProgress = userProgress/i;
            holder.pb.setMax(100);
            holder.pb.setProgress(userProgress);
            holder.mIdView.setText("Your Progress");
            holder.percentView.setText("%" + userProgress);
        }
        else{
            int totalProgress = 0;
            int i = 0;
            for (Task t : mValues){
                totalProgress += t.getProgress();
                i++;
            }
            totalProgress = totalProgress/i;
            holder.pb.setMax(100);
            holder.pb.setProgress(totalProgress);
            holder.percentView.setText("%" + totalProgress);
        }


    }


    public int getItemViewType(int position){
        if(position == 0){
            return TYPE_WHOLEPROG;
        }
        else if(position == 1){
            return TYPE_USERPROG;
        }
        else{
            return TYPE_TASK;
        }
    }


    @Override
    public int getItemCount() {
        return mValues.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public final TextView mIdView;
        public Task mItem;
        public final ProgressBar pb;
        public final TextView percentView;

        CardView cv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            percentView = (TextView) view.findViewById(R.id.percent);
            pb = (ProgressBar) view.findViewById(R.id.progressBar);
            cv = (CardView) view.findViewById(R.id.cardProgressView);
            mIdView = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}
