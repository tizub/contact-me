package com.tzubeli.contactme;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tzubeli.contactme.beans.Profile;
import com.tzubeli.contactme.services.QRCodeSvc;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {
    private static final String PREFS_NAME = "profile";

    private SharedPreferences profilePrefs;
    boolean isImageFitToScreen;

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
        String firstName = profilePrefs.getString("first_name", null);
        final EditText inputLastName = (EditText) v.findViewById(R.id.profile_last_name);
        String lastName = profilePrefs.getString("last_name", null);
        final EditText inputPhoneMobile = (EditText) v.findViewById(R.id.profile_phone_mobile);
        String phoneMobile = profilePrefs.getString("phone_mobile", null);

        // Generating QRCode
        final Profile myProfile = new Profile(firstName, lastName, phoneMobile);
        final Bitmap qrProfile = QRCodeSvc.getInstance().profileToImage(myProfile);
        final ImageView qrCodeImageView = (ImageView) v.findViewById(R.id.profile_qr_code);
        qrCodeImageView.setImageBitmap(qrProfile);
        qrCodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    qrCodeImageView.setLayoutParams(layoutParams);
                    qrCodeImageView.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                    qrCodeImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    qrCodeImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        });

        // Init view elements
        inputFirstName.setText(myProfile.getFirstName());
        inputLastName.setText(myProfile.getLastName());
        inputPhoneMobile.setText(myProfile.getPhoneMobile());
        Button btnSaveProfile = (Button) v.findViewById(R.id.profile_save);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  SharedPreferences.Editor editor = profilePrefs.edit();
                  myProfile.setFirstName(inputFirstName.getText().toString());
                  editor.putString("first_name", myProfile.getFirstName());
                  myProfile.setLastName(inputLastName.getText().toString());
                  editor.putString("last_name", myProfile.getLastName());
                  myProfile.setPhoneMobile(inputPhoneMobile.getText().toString());
                  editor.putString("phone_mobile", myProfile.getPhoneMobile());
                  editor.commit();
                  ((MainActivity)getActivity()).hideKeyboard(v);
                  Snackbar.make(v, "Profile saved", Snackbar.LENGTH_LONG).show();
              }
        });



        return v;
    }
}
