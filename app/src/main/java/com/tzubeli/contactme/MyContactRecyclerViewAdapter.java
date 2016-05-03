package com.tzubeli.contactme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzubeli.contactme.beans.Profile;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Profile} and makes a call to the
 * specified {@link ContactsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {

    private final List<Profile> mValues;
    private final ContactsFragment.OnListFragmentInteractionListener mListener;

    public MyContactRecyclerViewAdapter(List<Profile> items, ContactsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getFirstName());
        holder.mPhoneView.setText(mValues.get(position).getPhoneMobile());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (null != mListener) {
                mListener.onContactListItemInteraction(holder.mItem, position);
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
        public final TextView mNameView;
        public final TextView mPhoneView;
        public Profile mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.contact_list_name);
            mPhoneView = (TextView) view.findViewById(R.id.contact_list_phone);
        }
    }
}
