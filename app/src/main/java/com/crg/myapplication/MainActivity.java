package com.crg.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private MyDatabaseHelper mMyDatabaseHelper;
    private Button addDataButton;
    private Button updateaButton;
    private Button deleteDbButton;
    private Button queryDbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
        createDatabase();
        addData();
        updateDatabase();
        deleteData();
        queryData();

    }

    //数据库查询
    private void queryData() {
        queryDbButton = (Button) findViewById(R.id.bt_query);
        queryDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();

                //查询book表中的所有数据
                Cursor cursor = db.query("book", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        //遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "name = " + name);
                        Log.d("MainActivity", "author = " + author);
                        Log.d("MainActivity", "pages = " + pages);
                        Log.d("MainActivity", "price = " + price);


                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

    private void deleteData() {
        deleteDbButton = (Button) findViewById(R.id.bt_delete);
        deleteDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
                db.delete("book", "pages > ?", new String[]{"500"});
            }
        });
    }

    /**
     * 更新数据库
     */
    private void updateDatabase() {
        updateaButton = (Button) findViewById(R.id.bt_update);
        updateaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 100);
                db.update("book", values, "name = ?", new String[]{"first code"});
            }
        });
    }


    /**
     * 创建数据库
     */
    private void createDatabase() {
        mButton = (Button) findViewById(R.id.bt_createdb);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDatabaseHelper.getWritableDatabase();
            }
        });
    }


    /**
     * 为数据库添加数据
     */
    private void addData() {
        addDataButton = (Button) findViewById(R.id.bt_addData);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mMyDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //开始组装第一条数据
                values.put("name", "android");
                values.put("author", "ligang");
                values.put("pages", 465);
                values.put("price", 16.96);

                //插入第一条数据
                db.insert("book", null, values);
                values.clear();

                //开始组装第二条数据

                values.put("name", "head first design pattern");
                values.put("author", "head first");
                values.put("pages", 510);
                values.put("price", 19.95);

                //插入第二条数据
                db.insert("book", null, values);

                //开始组装第三条数据
                values.put("name", "first code");
                values.put("author", "guoling");
                values.put("pages", 552);
                values.put("price", 79);

                //插入第三条数据
                db.insert("book", null, values);
            }
        });
    }
}
