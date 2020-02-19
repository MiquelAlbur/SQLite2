package com.example.sqlite2;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        MyDB db = new MyDB(this);
        db.insertarDades();
        ArrayList<RowModel> list = new ArrayList<RowModel>();
        Cursor cur = db.selectRecords();
        for (int i = 0;i<cur.getCount();i++) {
            list.add(new RowModel(cur.getString(1)));
            cur.moveToNext();
        }
        setListAdapter(new RatingAdapter(list));
    }

    private RowModel getModel(int position) {
        return (((RatingAdapter) getListAdapter()).getItem(position));
    }

    class RatingAdapter extends ArrayAdapter<RowModel> {
        RatingAdapter(ArrayList<RowModel> list) {
            super(MainActivity.this, R.layout.row, R.id.label, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ViewHolder holder = (ViewHolder) row.getTag();
            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
                RatingBar.OnRatingBarChangeListener l = new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromTouch) {
                        Integer myPosition = (Integer) ratingBar.getTag();
                        RowModel model = getModel(myPosition);
                        model.rating = rating;
                        LinearLayout parent = (LinearLayout) ratingBar.getParent();
                        TextView label = (TextView) parent.findViewById(R.id.label);
                        label.setText(model.toString());
                    }
                };
                holder.rate.setOnRatingBarChangeListener(l);
            }
            RowModel model = getModel(position);
            holder.rate.setTag(new Integer(position));
            holder.rate.setRating(model.rating);
            return (row);
        }
    }

    class RowModel {
        String label;
        float rating = 2.0f;

        RowModel(String label) {
            this.label = label;
        }

        public String toString() {
            if (rating >= 3.0) {
                return (label.toUpperCase());
            }
            return (label);
        }
    }
}
