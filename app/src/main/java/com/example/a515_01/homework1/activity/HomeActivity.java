package com.example.a515_01.homework1.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.fragment.Fragment_1;
import com.example.a515_01.homework1.fragment.Fragment_3;
import com.example.a515_01.homework1.fragment.Fragment_home;
import com.example.a515_01.homework1.util.ConfigUtils;
import com.example.a515_01.homework1.util.VolleyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";
    private ImageView iv_titleBar_change;//标题栏左侧唤醒侧边栏图片
    private TextView tv_titleBar_title;//标题栏标题
    private DrawerLayout dl_home_layout;//布局文件
    private NavigationView nv_home_navigation;//侧边栏
    private HashMap<Integer, Fragment> fHMap;//fragment的集合
    private Fragment currentFragment;//保存当前的fragment
    private Menu menu;//侧滑菜单栏
    private LinearLayout ll_header;//头像
    private long exitTime = 0;//退出时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        dl_home_layout = findViewById(R.id.dl_home_layout);
        nv_home_navigation = findViewById(R.id.nv_home_navigation);
        View titleBar = findViewById(R.id.titleBar_home);
        iv_titleBar_change = titleBar.findViewById(R.id.iv_titleBar_change);
        tv_titleBar_title = titleBar.findViewById(R.id.tv_titleBar_title);
        View headerView = nv_home_navigation.getHeaderView(0);
        ll_header = headerView.findViewById(R.id.ll_header);
        menu = nv_home_navigation.getMenu();//侧滑菜单栏
        menu.findItem(R.id.menu_group1_home).setVisible(false);//隐藏
        fHMap = new HashMap<>();//fragment的集合
        iv_titleBar_change.setOnClickListener(this);//弹出侧滑拦图片监听
        ll_header.setOnClickListener(this);//头像监听
        nv_home_navigation.setNavigationItemSelectedListener(this);//侧滑拦菜单监听
        switchFragment(menu.findItem(R.id.menu_group1_home));//显示
    }

    /**
     * 单击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_titleBar_change:
                dl_home_layout.openDrawer(nv_home_navigation);
                break;
            case R.id.ll_header:
                switchFragment(menu.findItem(R.id.menu_group1_home));
                offItem();//关闭所有选中状态
                break;
        }
    }

    /**
     * 导航栏菜单监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return switchFragment(item);//切换fragment
    }

    /**
     * 根据MenuItem切换fragment
     *
     * @param item
     * @return
     */
    private boolean switchFragment(MenuItem item) {
        int itemId = item.getItemId();//id
        Fragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();//fragment事务
        if (fHMap.containsKey(itemId)) {
            fragment = fHMap.get(itemId);
        } else {
            switch (itemId) {
                case R.id.menu_group1_home:
                    fragment = new Fragment_home();
                    break;
                case R.id.menu_group1_item1:
                    fragment = new Fragment_1();
                    break;
//                case R.id.menu_group1_item2:
//                    fragment = new Fragment_2();
//                    break;
                case R.id.menu_group1_item3:
                    fragment = new Fragment_3();
                    break;
//                case R.id.menu_group1_item4:
//                    fragment = new Fragment_4();
//                    break;
                default:
                    exit();
                    return false;
            }
            fHMap.put(itemId, fragment);//将新的Fragment记录
            ft.add(R.id.ll_home_content, fragment);//将新的Fragment添加到布局
        }
        if (currentFragment != null) {
            ft.hide(currentFragment);//隐藏当前
        }
        ft.show(fragment);//显示fragment
        ft.commit();//提交事务
        //成功切换后的操作
        currentFragment = fragment;//设置当前fragment
        tv_titleBar_title.setText(item.getTitle().toString());//设置标题栏标题
        item.setCheckable(true);//选中该选项
        dl_home_layout.closeDrawer(nv_home_navigation);//关闭侧滑拦
        return true;
    }

    @Override
    public void onBackPressed() {//返回键监听
        if (dl_home_layout.isDrawerOpen(nv_home_navigation)) {//打开状态，先关闭
            dl_home_layout.closeDrawer(nv_home_navigation);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
        }
    }

    //关闭所有item的选中状态
    private void offItem() {
        Set<Integer> key = fHMap.keySet();
        Iterator it = key.iterator();
        while (it.hasNext()) {//遍历
//            if (itemKey != bar_home) {//不是主页
            menu.findItem((Integer) it.next()).setChecked(false);//取消选中
//            }
        }
    }

    /**
     * 用户退出（删除密码记录）
     */
    private void exit() {
        new AlertDialog.Builder(this)
                .setTitle("您确定要退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConfigUtils.remove(HomeActivity.this, "password");
                        ConfigUtils.remove(HomeActivity.this, "rememberPassword");
                        ConfigUtils.remove(HomeActivity.this, "autoLogin");
                        finish();
                    }
                })
                .setNegativeButton("取消", null).show();
    }
}
