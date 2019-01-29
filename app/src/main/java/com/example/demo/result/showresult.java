package com.example.demo.result;

import java.util.ArrayList;

public class showresult {
    public static showresult INSTENTS=new showresult();
    private  int num1 = 0;
    private  int num2 = 0;
    private  int num3 = 0;
    private  int num4 = 0;

    private  ArrayList arrayList1 = new ArrayList();
    private  ArrayList arrayList2 = new ArrayList();
    private  ArrayList arrayList3 = new ArrayList();
    private  ArrayList arrayList4 = new ArrayList();

    public void setnum1() {
        this.num1 ++;
    }
    public void setnum2() {
        this.num2 ++;
    }
    public void setnum3() {
        this.num3 ++;
    }
    public void setnum4() {
        this.num4 ++;
    }

    public int getnum1() {
        return num1;
    }
    public int getnum2() {
        return num2;
    }
    public int getnum3() {
        return num3;
    }
    public int getnum4() {
        return num4;
    }

    public ArrayList getArrayList1() {
        return arrayList1;
    }
    public ArrayList getArrayList2() {
        return arrayList2;
    }
    public ArrayList getArrayList3() {
        return arrayList3;
    }
    public ArrayList getArrayList4() {
        return arrayList4;
    }

    public void setArrayList1(String str)
    {
        arrayList1.add(str);
    }
    public void setArrayList2(String str)
    {
        arrayList2.add(str);
    }
    public void setArrayList3(String str)
    {
        arrayList3.add(str);
    }
    public void setArrayList4(String str)
    {
        arrayList4.add(str);
    }

}
