package com.tzubeli.contactme;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {
    private static final String PREFS_NAME = "profile";

    private SharedPreferences profilePrefs;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyProfileFragment.
     */
    public static MyProfileFragment newInstance() {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);

        profilePrefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final EditText inputFirstName = (EditText) v.findViewById(R.id.profile_first_name);
        inputFirstName.setText(profilePrefs.getString("first_name", null));
        final EditText inputLastName = (EditText) v.findViewById(R.id.profile_last_name);
        inputLastName.setText(profilePrefs.getString("last_name", null));
        final EditText inputPhoneMobile = (EditText) v.findViewById(R.id.profile_phone_mobile);
        inputPhoneMobile.setText(profilePrefs.getString("phone_mobile", null));

        Button btnSaveProfile = (Button) v.findViewById(R.id.profile_save);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  SharedPreferences.Editor editor = profilePrefs.edit();
                                                  editor.putString("first_name", inputFirstName.getText().toString());
                                                  editor.putString("last_name", inputLastName.getText().toString());
                                                  editor.putString("phone_mobile", inputPhoneMobile.getText().toString());
                                                  editor.commit();
                                                  ((MainActivity)getActivity()).hideKeyboard(v);
                                                  Snackbar.make(v, "Profile saved", Snackbar.LENGTH_LONG).show();
                                              }
                                          }
        );

        return v;
    }
}
