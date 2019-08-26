package com.androidplugin.hclaunhcer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private List<AppInfo> listAppInfo;
    private GridView gridView;
    private AppAdapter appAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        gridView=(GridView) findViewById(R.id.grid_all_app);
        gridView.setBackgroundResource(R.mipmap.wallpaper);
        //getListAppInfo();
        getCustomListAppInfo();
        appAdapter=new AppAdapter();
        gridView.setAdapter(appAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemClick", "onItemClick: "+parent+"/"+view+"/"+position+"/"+id);
        AppInfo appInfo=listAppInfo.get(position);
        Toast.makeText(this,appInfo.getApppackagename(),Toast.LENGTH_SHORT).show();
        //获取包名 跳转到指定应用
        Intent intent=this.getPackageManager().getLaunchIntentForPackage(appInfo.getApppackagename());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        AppInfo appInfo=listAppInfo.get(i);
            if(appInfo.getApppackagename().equals("com.")){
               // Intent intent=new Intent(this,com.android.ipro.FactoryTestActivity.class);
                Intent intent = new Intent();
                intent.setClassName("com.android.ipro", "com.android.ipro.FactoryTestActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        return true;
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listAppInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return listAppInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //加载布局 并且判断是否有可复用的
            if(convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.appinfo,null);
            }
            //获取appinfo
            AppInfo appInfo=listAppInfo.get(position);
            //加载子布局
            ImageView app_icon=(ImageView) convertView.findViewById(R.id.app_icon);
            TextView app_name=(TextView) convertView.findViewById(R.id.app_name);
            app_icon.setImageDrawable(appInfo.getAppicon());
            app_name.setText(appInfo.getAppname());


            return convertView;
        }
    }
    public  List<AppInfo> getCustomListAppInfo(){
        listAppInfo=new ArrayList<AppInfo>();
        TypedArray appiconid=getResources().obtainTypedArray(R.array.appicon);
        String[] appname=getResources().getStringArray(R.array.appename);
        String[] packagename=getResources().getStringArray(R.array.packagename);
        for(int i=0;i<appname.length;i++){
            Drawable drawable=appiconid.getDrawable(i);
            listAppInfo.add(new AppInfo(drawable,appname[i],packagename[i]));
        }
        return listAppInfo;

    }
    public  List<AppInfo> getListAppInfo(){
        listAppInfo=new ArrayList<AppInfo>();
        //获取应用管理对象
      PackageManager packageManager=getPackageManager();
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        Toast.makeText(this,R.string.app_name,Toast.LENGTH_LONG).show();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //查询符合intent信息的应用信息
        List<ResolveInfo> resolveInfos=packageManager.queryIntentActivities(intent,0);
        //遍历
        for(ResolveInfo resolveInfo:resolveInfos){
            String packageName=resolveInfo.activityInfo.packageName;
            Drawable drawable=resolveInfo.loadIcon(packageManager);
            String appName=resolveInfo.loadLabel(packageManager).toString();
            AppInfo appInfo=new AppInfo(drawable,appName,packageName);
            listAppInfo.add(appInfo);
        }
        Log.d("getListAppInfo", "getListAppInfo: "+listAppInfo);


        return listAppInfo;
    }
}
