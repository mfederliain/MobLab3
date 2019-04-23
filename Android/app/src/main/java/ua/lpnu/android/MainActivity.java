package ua.lpnu.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.lpnu.android.db.DBhelper;
import ua.lpnu.android.pojo.Contact;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup viewGroup;
    ArrayList<Contact> contactsChecked = new ArrayList<>();
    ArrayList<Contact> contacts;
    EditText phoneCall;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBhelper = DBhelper.getInstance(getApplicationContext());

        viewGroup = (ViewGroup) findViewById(R.id.list);


        contacts = getContacs();

        showData();
        phoneCall = findViewById(R.id.phoneCall);


        findViewById(R.id.detail).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.edite).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);

    }

    public void showData() {
        viewGroup.removeAllViews();
        for (Contact c : contacts) {
            //init items

            LayoutInflater l = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View item = l.inflate(R.layout.list_item, null);
            final TextView textView = (TextView) item.findViewById(R.id.personis);
            textView.setText(String.valueOf(c.getIdPerson()));


            ((CheckBox) item.findViewById(R.id.checkBox)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.checkBox) {
                        CheckBox ch = (CheckBox) v;
                        if (ch.isChecked()) {
                            //contactsChecked.add(TextView item.findViewById(R.id.itemtext).);
                            int idPerson = Integer.valueOf(((TextView) item.findViewById(R.id.personis)).getText().toString());
                            Contact contact = findByIdPerson(idPerson);
                            if (contact == null) {
                                ch.setChecked(false);
                                return;
                            }

                            contactsChecked.add(contact);
                        } else {
                            int idPerson = Integer.valueOf(((TextView) item.findViewById(R.id.personis)).getText().toString());
                            Contact contact = findByIdPerson(idPerson);
                            if (contact == null) {
                                ch.setChecked(false);
                                return;
                            }

                            contactsChecked.remove(contact);
                        }
                    }
                }
            });

            final TextView hp=  ((TextView) item.findViewById(R.id.phoneNumber));
            hp .setText(c.getPhone());

            TextView n= (TextView) item.findViewById(R.id.itemtext);
           n.setText(c.getName());
            n.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phoneCall.setText(hp.getText().toString());
                }
            });


            item.findViewById(R.id.morebutton).setOnClickListener(this);
            viewGroup.addView(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        contacts = getContacs();
        showData();

    }

    public Contact findByIdPerson(int idPerson) {
        for (Contact contact : contacts) {
            if (contact.getIdPerson() == idPerson)
                return contact;
        }
        return null;
    }

    public ArrayList<Contact> getContacs() {
        if (dBhelper == null) return null;
        ArrayList<Contact> contacts = dBhelper.getContacts();


        return contacts;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.morebutton) {
            View detailview = findViewById(R.id.detail);
            View viewParent = v.getRootView();
            int idPerson = Integer.valueOf(((TextView) viewParent.findViewById(R.id.personis)).getText().toString());
            Contact c = findByIdPerson(idPerson);
            ((TextView) detailview.findViewById(R.id.contactSurname)).setText(c.getSurname());
            ((TextView) detailview.findViewById(R.id.contactName)).setText(c.getName());
            ((TextView) detailview.findViewById(R.id.contactNumber)).setText(c.getPhone());
            ImageView img = ((ImageView) detailview.findViewById(R.id.contactImg));
            switch (c.getTypePhone()) {
                case Contact.TypePhone
                        .FAMILY:
                    img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));
                    break;
                case Contact.TypePhone
                        .FRIEND:
                    img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_person_outline_black_24dp));

                    break;
                case Contact.TypePhone
                        .HOME:
                    img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_black_24dp));

                    break;
                case Contact.TypePhone
                        .WORK:
                    img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_airplay_black_24dp));

                    break;
            }

            float width = findViewById(R.id.main_layout).getWidth();
            TranslateAnimation anim = new TranslateAnimation(width, 0.0f, 0.0f, 0.0f);
            anim.setDuration(300);
            anim.setFillAfter(true);
            detailview.bringToFront();
            detailview.startAnimation(anim);
            detailview.setVisibility(View.VISIBLE);
            detailview.setEnabled(true);
        } else if (v.getId() == R.id.detail) {
            View detailview = v;
            //View parent=detailview.getRootView();

            TranslateAnimation anim = new TranslateAnimation(0.0f, detailview.getWidth(), 0.0f, 0.0f);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    findViewById(R.id.listview).bringToFront();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            anim.setDuration(300);
            anim.setFillAfter(true);
            detailview.startAnimation(anim);
            detailview.setEnabled(false);
        } else if (v.getId() == R.id.delete) {
            if (contactsChecked.isEmpty())
                Toast.makeText(getApplicationContext(),
                        "Nothing to delete",
                        Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getApplicationContext(),
                        "Delete ...",
                        Toast.LENGTH_SHORT).show();
                for (int i = 0; i < contactsChecked.size(); i++) {
                    for (int j = 0; j < contacts.size(); j++) {
                        if (contacts.get(j).getIdPerson() == contactsChecked.get(i).getIdPerson()) {
                            dBhelper.deletePerson(contactsChecked.get(i).getIdPerson());
                            break;
                        }
                    }
                }
                contactsChecked.clear();
                showData();
            }
        } else if (v.getId() == R.id.edite) {
//            Toast.makeText(getApplicationContext(),
//                    "Edit .... not work ^)",
//                    Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.add) {
            Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
            MainActivity.this.startActivity(myIntent);

        } else if (v.getId() == R.id.call) {
            String text = phoneCall.getText().toString();
            if (text != null && text.length() > 0) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + text));
                    startActivity(intent);
                }catch (Exception e){
                                Toast.makeText(getApplicationContext(),
                    "Could not call",
                    Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
}
