package com.example.a515_01.homework1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.bean.Chat;

import java.util.List;

public class Adapter_f1 extends BaseAdapter {
    private static final String TAG = "Adapter_f1";
    private static final int TYPE_Left = 0;
    private static final int TYPE_Right = 1;
    private List<Chat> objects;
    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter_f1(Context context, List<Chat> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Chat getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //多布局的核心，通过这个判断类别
    @Override
    public int getItemViewType(int position) {
        if (objects.get(position).getType()) {
            return TYPE_Left;
        } else if (!objects.get(position).getType()) {
            return TYPE_Right;
        } else {
            return super.getItemViewType(position);
        }
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolderLeft left = null;
        ViewHolderRight right = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_Left:
                    convertView = layoutInflater.inflate(R.layout.listview_f1_left, parent, false);
                    left = new ViewHolderLeft(convertView);
                    convertView.setTag(R.id.Tag_Left, left);
                    break;
                case TYPE_Right:
                    convertView = layoutInflater.inflate(R.layout.listview_f1_right, parent, false);
                    right = new ViewHolderRight(convertView);
                    convertView.setTag(R.id.Tag_Right, right);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_Left:
                    left = (ViewHolderLeft) convertView.getTag(R.id.Tag_Left);
                    break;
                case TYPE_Right:
                    right = (ViewHolderRight) convertView.getTag(R.id.Tag_Right);
                    break;
            }
        }
        Chat chat = objects.get(position);
        switch (type) {
            case TYPE_Left:
                left.tvContentF1Left.setText(chat.getContent());
                break;
            case TYPE_Right:
                right.tvContentF1Right.setText(chat.getContent());
                break;
        }
        return convertView;
    }

    protected class ViewHolderLeft {
        private TextView tvContentF1Left;

        public ViewHolderLeft(View view) {
            tvContentF1Left = (TextView) view.findViewById(R.id.tv_content_f1_left);
        }
    }

    protected class ViewHolderRight {

        private TextView tvContentF1Right;

        public ViewHolderRight(View view) {
            tvContentF1Right = (TextView) view.findViewById(R.id.tv_content_f1_right);
        }

    }
}
