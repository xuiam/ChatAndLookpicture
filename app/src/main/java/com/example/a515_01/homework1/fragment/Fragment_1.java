package com.example.a515_01.homework1.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.adapter.Adapter_f1;
import com.example.a515_01.homework1.bean.Chat;
import com.example.a515_01.homework1.util.ConfigUtils;
import com.example.a515_01.homework1.util.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_1 extends Fragment implements View.OnClickListener {
    private static final String KEY = "23c29a9e9159400e8b1f4190726d77e8";
    private ListView lv_f1;
    private EditText et_f1;
    private Button btn_f1;
    private Adapter_f1 mAdapter;
    private ArrayList<Chat> mList;
    private Context context;


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            onStop();
        } else {
            onResume();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        initView(view);
        mList = new ArrayList<>();
        mAdapter = new Adapter_f1(context, mList);
        lv_f1.setAdapter(mAdapter);
        sendXL("讲笑话");
        return view;
    }

    private void initView(View view) {
        lv_f1 = (ListView) view.findViewById(R.id.lv_f1);
        et_f1 = (EditText) view.findViewById(R.id.et_f1);
        btn_f1 = (Button) view.findViewById(R.id.btn_f1);

        btn_f1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_f1:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String f1 = et_f1.getText().toString().trim();
        if (TextUtils.isEmpty(f1)) {
            return;
        }
        mList.add(new Chat(f1, false));
        sendXL(f1);
        et_f1.setText("");
        mAdapter.notifyDataSetChanged();
    }

    private void xlSend(String msg) {
        mList.add(new Chat(msg, true));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 发送消息给小灵
     */
    private void sendXL(String msg) {
        JSONObject params = new JSONObject();
        JSONObject perception = new JSONObject();
        JSONObject inputText = new JSONObject();
        JSONObject userInfo = new JSONObject();
        try {
            inputText.put("text", msg);
            perception.put("inputText", inputText);
            userInfo.put("apiKey", KEY);
            userInfo.put("userId", ConfigUtils.get(context, "username", "空的用户名"));
            params.put("perception", perception);
            params.put("userInfo", userInfo);
            params.put("reqType", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyUtils.post("http://openapi.tuling123.com/openapi/api/v2", params,
                new VolleyUtils.CallBack() {
                    @Override
                    public void success(JSONObject response) throws JSONException {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject jo;
                        Iterator<?> keys;
                        for (int i = 0; i < results.length(); i++) {
                            jo = results.getJSONObject(i).getJSONObject("values");
                            keys = jo.keys();
                            while (keys.hasNext()) {
                                xlSend(jo.getString(keys.next().toString()));
                            }
                        }
                    }
                });
    }

}
