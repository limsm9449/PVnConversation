package com.sleepingbear.pvnconversation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        Bundle b = this.getIntent().getExtras();

        ActionBar ab = (ActionBar) getSupportActionBar();
        ab.setTitle(b.getString("category"));
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        ArrayList<CategoryViewItem> al = new ArrayList<CategoryViewItem>();
        String[] samples = b.getString("samples").split("\n");
        for ( int i = 0; i < samples.length; i++ ) {
            if ( !"".equals(samples[i]) ) {
                String[] row = samples[i].split(":");
                if (row.length == 1) {
                    al.add(new CategoryViewItem(row[0].trim(), ""));
                } else if (row.length == 2) {
                    al.add(new CategoryViewItem(row[0].trim(), row[1].trim()));
                }
            }
        }

        adapter = new CategoryAdapter(this, R.layout.content_category_item, al);
        ListView listView = (ListView) this.findViewById(R.id.my_c_category_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CategoryViewItem cur = (CategoryViewItem) adapter.getItem(position);

            Bundle bundle = new Bundle();
            bundle.putString("foreign", cur.getLine1());
            bundle.putString("han", cur.getLine2());
            bundle.putString("sampleSeq", "");

            Intent intent = new Intent(getApplicationContext(), SentenceViewActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

class CategoryAdapter extends ArrayAdapter<CategoryViewItem> {
    private ArrayList<CategoryViewItem> items;
    int fontSize = 0;

    public CategoryAdapter(Context context, int textViewResourceId, ArrayList<CategoryViewItem> items) {
        super(context, textViewResourceId, items);
        this.items = items;

        fontSize = Integer.parseInt( DicUtils.getPreferencesValue( context, CommConstants.preferences_font ) );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.content_category_item, null);
        }

        CategoryViewItem p = items.get(position);
        if (p != null) {
            ((TextView) v.findViewById(R.id.my_tv_line1)).setText(p.getLine1());
            ((TextView) v.findViewById(R.id.my_tv_line2)).setText(p.getLine2());

            //사이즈 설정
            ((TextView) v.findViewById(R.id.my_tv_line1)).setTextSize(fontSize);
            ((TextView) v.findViewById(R.id.my_tv_line2)).setTextSize(fontSize);
        }

        return v;
    }
}

class CategoryViewItem {
    private String line1;
    private String line2;

    public CategoryViewItem(String _line1, String _line2) {
        this.line1 = _line1;
        this.line2 = _line2;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }
}