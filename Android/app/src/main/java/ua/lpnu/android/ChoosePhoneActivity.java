package ua.lpnu.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ua.lpnu.android.db.DBhelper;
import ua.lpnu.android.pojo.TPhone;

public class ChoosePhoneActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<TPhone> phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_phone);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DBhelper dBhelper= DBhelper.getInstance(getApplicationContext());
        phones=dBhelper.getUnicPhones();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(phones);
        ((MyAdapter) mAdapter).setLisener(new MyAdapter.Lisener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("phone",phones.get(position).getNumber());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
