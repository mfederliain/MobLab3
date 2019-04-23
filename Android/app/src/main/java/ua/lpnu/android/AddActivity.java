package ua.lpnu.android;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ua.lpnu.android.db.DBhelper;
import ua.lpnu.android.pojo.Contact;
import ua.lpnu.android.pojo.TPhone;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText phone, name, surname;
    TextInputLayout inName, inSurname;
    int type= Contact.TypePhone
            .UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        phone = findViewById(R.id.phone);
        inName = (TextInputLayout) findViewById(R.id.in_name);
        name = findViewById(R.id.edtName);

        inSurname = (TextInputLayout) findViewById(R.id.in_surname);
        surname = findViewById(R.id.edtSurname);

        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        spinner.setOnItemSelectedListener(this);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onAdd(View view) {
        if (name.getText().length() == 0) {
            inName.setError("First name is required");
            return;
        }
        if (surname.getText().length() == 0) {
            inSurname.setError("Second name is required");
            return;
        }
        if (phone.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Write phone number or chose", Toast.LENGTH_SHORT).show();
            return;
        }
        DBhelper dBhelper=DBhelper.getInstance(getApplicationContext());
        if (dBhelper==null)return;
        dBhelper.inserrUserPhone(phone.getText().toString(),type,name.getText().toString(),surname.getText().toString());
              Toast.makeText(getApplicationContext(),"Added!",Toast.LENGTH_SHORT).show();

        finish();
    }
    public void onClickChoose(View v){
        Intent intent = new Intent(this, ChoosePhoneActivity.class);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("phone");
        phone.setText( name);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s=(String) parent.getItemAtPosition(position);
        type= TPhone.getIdByName(s);
//        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
