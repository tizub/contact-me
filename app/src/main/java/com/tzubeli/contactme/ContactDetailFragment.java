package com.tzubeli.contactme;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tzubeli.contactme.beans.Profile;
import com.tzubeli.contactme.services.ProfileSvc;

public class ContactDetailFragment extends Fragment {

    private Profile contact;
    private int position;

    public ContactDetailFragment() {
    }

    public static ContactDetailFragment newInstance(Profile contact, int position) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.contact = contact;
        fragment.position = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        final EditText mFirstNameView = (EditText)v.findViewById(R.id.contact_first_name);
        mFirstNameView.setText(this.contact.getFirstName());
        final EditText mLastNameView = (EditText)v.findViewById(R.id.contact_last_name);
        mLastNameView.setText(this.contact.getLastName());
        final EditText mPhoneMobileView = (EditText)v.findViewById(R.id.contact_phone_mobile);
        mPhoneMobileView.setText(this.contact.getPhoneMobile());
        final Button mDeleteButtonView = (Button)v.findViewById(R.id.contact_delete_btn);
        mDeleteButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileSvc.getInstance().deleteContact(position);
                getActivity().onBackPressed();
            }
        });
        return v;
    }
}
