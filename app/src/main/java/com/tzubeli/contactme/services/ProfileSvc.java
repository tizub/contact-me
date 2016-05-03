package com.tzubeli.contactme.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tzubeli.contactme.beans.Profile;

import java.util.ArrayList;

public class ProfileSvc {
    private static final String PREFS_NAME = "contacts";
    private static final String PREFS_KEY_LIST = "list";
    private SharedPreferences contactsPrefs;

    private ArrayList<Profile> contacts;

    private static ProfileSvc mInstance;

    public static ProfileSvc getInstance(){
        if(mInstance == null){
            mInstance = new ProfileSvc();
        }
        return mInstance;
    }

    public void init(Context ctx) {
        this.contactsPrefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.loadContacts();
    }

    private void loadContacts() {
        String contactsListString = this.contactsPrefs.getString(PREFS_KEY_LIST, null);
        if (contactsListString == null) {
            this.contacts = new ArrayList<Profile>();
        } else {
            this.contacts = (ArrayList<Profile>) SerializationSvc.stringToObject(contactsListString);
        }
    }

    private void storeContacts() {
        String contactsListString = SerializationSvc.objectToString(this.contacts);
        SharedPreferences.Editor editor = contactsPrefs.edit();
        editor.putString(PREFS_KEY_LIST, contactsListString);
        editor.commit();
    }

    /*public String profileToString(Profile profile) {
        return SerializationSvc.objectToString(profile);
    }

    public Profile stringToProfile(String encodedObject) {
        return (Profile)SerializationSvc.stringToObject(encodedObject);
    }

    public Bitmap profileToImage(Profile profile) {
        return QRCodeSvc.stringToImage(SerializationSvc.objectToString(profile));
    }*/

    public Profile readProfile(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        return (Profile)SerializationSvc.stringToObject(result.getContents());
    }

    public void addContact(Profile newContact) {
        if (contacts == null) {
            contacts = new ArrayList<Profile>();
        }
        contacts.add(newContact);
        storeContacts();
    }

    public void deleteContact(int index) {
        contacts.remove(index);
        storeContacts();
    }

    public ArrayList<Profile> getContacts() {
        return this.contacts;
    }

    public void generateMocks() {
        contacts.add(new Profile("LeBron", "JAMES", "06 23 23 23 23"));
        contacts.add(new Profile("Stephen", "CURRY", "06 30 30 30 30"));
        contacts.add(new Profile("Kevin", "DURANT", "06 35 35 35 35"));
        contacts.add(new Profile("Kawhi", "LEONARD", "06 02 02 02 02"));
        contacts.add(new Profile("James", "HARDEN", "06 13 13 13 13"));
    }
}
