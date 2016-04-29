package blueprintlabs.zulu;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import blueprintlabs.zulu.resources.Project;
import blueprintlabs.zulu.resources.User;

/**
 * Created by Ahmet Burak on 26.4.2016.
 */
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private final List<Project> mValues;
    private final ProjectsAdapter.OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ProjectsAdapter(List<Project> items, Context context) {
        mValues = items;
        mContext = context;
        mListener = (OnListFragmentInteractionListener) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projects_row_layout, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        final int pos = position;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(pos);
                }
            }
        });
    }

    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public Project mItem;
        public CardView cv;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cv = (CardView) view.findViewById(R.id.projectsCardView);
            mIdView = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int position);
    }

}
