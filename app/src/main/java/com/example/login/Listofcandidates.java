package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Listofcandidates extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private List<String> username,tickettype,servicetype,departure,arrival,dates,times,combination;
    private List<Integer> idList,ages;
    private List<Long> mobileno;
    int updateIndex;
    String updateusername;
    int updateage;
    long updatemobileno;
    String updatetickettype;
    String updateservicetype;
    String updatedeparture;
    String updatearrival;
    String updatedates;
    String updatetimes;
    ListAdapter la;
    ListView lv;
    DBHelper dhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofcandidates);
        lv = (ListView)findViewById(R.id.listView);
        combination=new ArrayList<String>();
        username=new ArrayList<String>();
        ages=new ArrayList<Integer>();
        mobileno=new ArrayList<Long>();
        tickettype=new ArrayList<String>();
        servicetype=new ArrayList<String>();
        departure=new ArrayList<String>();
        arrival=new ArrayList<String>();
        dates=new ArrayList<String>();
        times=new ArrayList<String>();
        idList = new ArrayList<Integer>();
        dhelper = new DBHelper(this);
        dhelper.open();
        Cursor c = dhelper.getAllEntries();
        c.moveToFirst();
        for (int i =0; i<c.getCount();i++)
        {
            idList.add(c.getInt(0));
            username.add(c.getString(1));
            ages.add(c.getInt(2));
            mobileno.add(c.getLong(3));
            tickettype.add(c.getString(4));
            servicetype.add(c.getString(5));
            departure.add(c.getString(6));
            arrival.add(c.getString(7));
            dates.add(c.getString(8));
            times.add(c.getString(9));
            combination.add(c.getString(1)+" - "+c.getString(4)+" - "+c.getString(8));
            c.moveToNext();
        }
        c.close();
        la = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                combination);
        lv.setAdapter(la);
        lv.setOnItemClickListener(this);
        dhelper.close();
    }
    @Override
    protected void onResume() {
        super.onResume();
        dhelper = new DBHelper(this);
        dhelper.open();
        combination.clear();
        idList.clear();
        username.clear();
        ages.clear();
        mobileno.clear();
        tickettype.clear();
        servicetype.clear();
        departure.clear();
        arrival.clear();
        dates.clear();
        times.clear();
        Cursor c1 = dhelper.getAllEntries();
        c1.moveToFirst();
        for (int i =0; i<c1.getCount();i++)
        {
            idList.add(c1.getInt(0));
            username.add(c1.getString(1));
            ages.add(c1.getInt(2));
            mobileno.add(c1.getLong(3));
            tickettype.add(c1.getString(4));
            servicetype.add(c1.getString(5));
            departure.add(c1.getString(6));
            arrival.add(c1.getString(7));
            dates.add(c1.getString(8));
            times.add(c1.getString(9));
            combination.add(c1.getString(1)+" - "+c1.getString(4)+" - "+c1.getString(8));
            c1.moveToNext();
        }
        lv = (ListView)findViewById(R.id.listView);
        dhelper.close();
        la = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,combination);
        lv.setAdapter(la);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final int indexvalue = idList.get(position);
        dhelper = new DBHelper(this);
        dhelper.open();
        updateIndex = idList.get(position);
        updateusername = username.get(position);
        updateage = ages.get(position);
        updatemobileno = mobileno.get(position);
        updatetickettype = tickettype.get(position);
        updateservicetype = servicetype.get(position);
        updatedeparture = departure.get(position);
        updatearrival = arrival.get(position);
        updatedates = dates.get(position);
        updatetimes = times.get(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete");
        dialog.setMessage("Do you want to delete "+ username.get(position) + " " + indexvalue);
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean result = dhelper.removeEntry(indexvalue);
                dhelper.close();
                onResume();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dhelper.close();
                onResume();
            }
        });

        dialog.setNeutralButton("Show Details", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                Bundle b = new Bundle();
                b.putString("name",updateusername);
                b.putInt("age",updateage);
                b.putLong("mobileno",updatemobileno);
                b.putString("tickettype",updatetickettype);
                b.putString("servicetype",updateservicetype);
                b.putString("departure",updatedeparture);
                b.putString("arrival",updatearrival);
                b.putString("dates",updatedates);
                b.putString("times",updatetimes);

                Intent int1 = new Intent(Listofcandidates.this,CandidateDetails.class);
                int1.putExtras(b);
                startActivity(int1);
            }
        });

        dialog.create();
        dialog.show();

    }
}
