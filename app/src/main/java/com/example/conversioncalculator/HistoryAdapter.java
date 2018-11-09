package com.example.conversioncalculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.conversioncalculator.HistoryFragment.OnListFragmentInteractionListener;
import com.example.conversioncalculator.dummy.HistoryContent;
import com.example.conversioncalculator.dummy.HistoryContent.HistoryItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.conversioncalculator.dummy.HistoryContent.HistoryItem} and makes a call to the
 * specified {@link HistoryFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<HistoryContent.HistoryItem> mValues;
    private final HistoryFragment.OnListFragmentInteractionListener mListener;

    public HistoryAdapter(List<HistoryContent.HistoryItem> items, HistoryFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mP1.setText(holder.mItem.toString());
        holder.mDateTime.setText(holder.mItem.timestamp.toString());

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mP1;
        public final TextView mDateTime;
        public HistoryContent.HistoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mP1 = (TextView) view.findViewById(R.id.p1);
            mDateTime = (TextView) view.findViewById(R.id.timestamp);
        }

        @Override
        public String toString() {

            return super.toString() + " '" + mDateTime.getText() + "'";
        }
    }
}
