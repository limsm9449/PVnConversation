package com.sleepingbear.pvnconversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GrammarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        Bundle b = this.getIntent().getExtras();

        ActionBar ab = (ActionBar) getSupportActionBar();
        ab.setTitle("문법 상세");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        ArrayList<GrammarViewItem> al = new ArrayList<GrammarViewItem>();
        if ( !"".equals(b.getString("grammar")) ) {
            al.add(new GrammarViewItem("* 문법", b.getString("grammar")));
        }
        if ( !"".equals(b.getString("mean")) ) {
            al.add(new GrammarViewItem("* 뜻", b.getString("mean")));
        }
        if ( !"".equals(b.getString("description")) ) {
            al.add(new GrammarViewItem("* 설명", b.getString("description")));
        }
        String[] samples = b.getString("samples").split("\n");
        for ( int i = 0; i < samples.length; i++ ) {
            if ( !"".equals(samples[i]) ) {
                String[] row = samples[i].split(":");
                if (row.length == 1) {
                    al.add(new GrammarViewItem(row[0].trim(), ""));
                } else if (row.length == 2) {
                    al.add(new GrammarViewItem(row[0].trim(), row[1].trim()));
                }
            }
        }

        GrammarAdapter m_adapter = new GrammarAdapter(this, R.layout.content_grammar_item, al);
        ((ListView) this.findViewById(R.id.my_c_gv_lv1)).setAdapter(m_adapter);

        DicUtils.setAdView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

class GrammarAdapter extends ArrayAdapter<GrammarViewItem> {
    private ArrayList<GrammarViewItem> items;
    private int fontSize = 0;

    public GrammarAdapter(Context context, int textViewResourceId, ArrayList<GrammarViewItem> items) {
        super(context, textViewResourceId, items);
        this.items = items;

        fontSize = Integer.parseInt( DicUtils.getPreferencesValue( context, CommConstants.preferences_font ) );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.content_grammar_item, null);
        }

        GrammarViewItem p = items.get(position);
        if (p != null) {
            ((TextView) v.findViewById(R.id.my_tv_line1)).setText(p.getLine1());
            ((TextView) v.findViewById(R.id.my_tv_line2)).setText(p.getLine2());

            ((TextView) v.findViewById(R.id.my_tv_line1)).setTextSize(fontSize);
            ((TextView) v.findViewById(R.id.my_tv_line2)).setTextSize(fontSize);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.line1 = p.getLine1();
            viewHolder.line2 = p.getLine2();
            v.setTag(viewHolder);

            //Item 선택
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder vViewHolder = (ViewHolder) v.getTag();

                    if ( !"* 문법".equals(vViewHolder.line1) &&
                            !"* 뜻".equals(vViewHolder.line1) &&
                            !"* 설명".equals(vViewHolder.line1) ) {
                        Bundle bundle = new Bundle();
                        bundle.putString("foreign", vViewHolder.line1);
                        bundle.putString("han", vViewHolder.line2);
                        bundle.putString("sampleSeq", "");

                        Intent intent = new Intent(getContext(), SentenceViewActivity.class);
                        intent.putExtras(bundle);

                        getContext().startActivity(intent);
                    }
                }
            });
        }

        return v;
    }

    static class ViewHolder {
        protected String line1;
        protected String line2;
    }
}

class GrammarViewItem {
    private String line1;
    private String line2;

    public GrammarViewItem(String _line1, String _line2) {
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