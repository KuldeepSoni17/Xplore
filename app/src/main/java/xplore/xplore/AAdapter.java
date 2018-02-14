package xplore.xplore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Smart on 1/23/2018.
 */
public class AAdapter extends BaseAdapter{
    ArrayList<Mainlist> mainlists1;
    LayoutInflater inflater;
    Context context;


    public AAdapter(ListActivity listActivity, ArrayList<Mainlist> mainlists) {
        this.mainlists1 = mainlists;
        this.context =listActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mainlists1.size();
    }

    @Override
    public Object getItem(int position) {
        return mainlists1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_list,null,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
        iv.setImageResource(Integer.parseInt(mainlists1.get(position).getImages()));
        tv.setText(mainlists1.get(position).getNames());
        tv1.setText(mainlists1.get(position).getOffers());
        return convertView;
    }
}