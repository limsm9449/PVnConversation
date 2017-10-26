package com.sleepingbear.pvnconversation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PatchActivity extends AppCompatActivity {
    private int fontSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = (ActionBar) getSupportActionBar();
        ab.setTitle("패치 내용");
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        StringBuffer patch = new StringBuffer();

        patch.append("* 신규 패치" + CommConstants.sqlCR);
        patch.append("" + CommConstants.sqlCR);
        patch.append("- 단어장의 단어를 추가, 삭제할 경우 메인의 단어 갯수가 변경이 안되는 문제점 수정" + CommConstants.sqlCR);
        patch.append("- 단어장에서 TTS, 전체 암기, 전체 미암기 기능 추가" + CommConstants.sqlCR);
        patch.append("" + CommConstants.sqlCR);
        patch.append("" + CommConstants.sqlCR);
        patch.append("- Android 7에서 다이얼로그 버튼이 안나오는 문제점 수정" + CommConstants.sqlCR);
        patch.append("- 환경설정에서 글씨 크기를 변경할 수 있도록 변경" + CommConstants.sqlCR);
        patch.append("- 회화 검색에서 성조없이 검색이 가능하도록 수정" + CommConstants.sqlCR);
        patch.append("- 예문 데이타 추가" + CommConstants.sqlCR);
        patch.append("- Android 4 - KitKat 버젼에서 실행이 안되는 문제점 수정" + CommConstants.sqlCR);
        patch.append("- 네이버 회화를 보고 돌아올 경우 리스트가 처음으로 가는 문제점 수정" + CommConstants.sqlCR);

        ((TextView) this.findViewById(R.id.my_c_patch_tv1)).setText(patch.toString());

        DicUtils.setAdView(this);

        fontSize = Integer.parseInt( DicUtils.getPreferencesValue( this, CommConstants.preferences_font ) );
        ((TextView) this.findViewById(R.id.my_c_patch_tv1)).setTextSize(fontSize);
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
