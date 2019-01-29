package com.example.demo.result;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.demo.R;

import java.util.ArrayList;

public class myresult extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myresult);

        textView1 =findViewById(R.id.aaa);
        textView2 =findViewById(R.id.bbb);
        textView3 =findViewById(R.id.ccc);
        textView4 =findViewById(R.id.ddd);
        textView5 =findViewById(R.id.eee);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText(" 我 的 点 赞: 共 "+showresult.INSTENTS.getnum1()+" 条！");
                writeinview(showresult.INSTENTS.getArrayList1());
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setText(" 我 的 收 藏: 共 "+showresult.INSTENTS.getnum2()+" 条！");
                writeinview(showresult.INSTENTS.getArrayList2());
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView3.setText(" 我 的 分 享: 共 "+showresult.INSTENTS.getnum3()+" 条！");
                writeinview(showresult.INSTENTS.getArrayList3());
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView4.setText(" 我 的 举 报: 共 "+showresult.INSTENTS.getnum4()+" 条！");
                writeinview(showresult.INSTENTS.getArrayList4());
            }
        });

    }
    private void writeinview(ArrayList list)
    {
        textView5.setText(" 显 示 记 录：");
        for(int i = 0;i < list.size(); i ++) {
            textView5.append("\r\n" + list.get(i).toString());
        }
    }
}
