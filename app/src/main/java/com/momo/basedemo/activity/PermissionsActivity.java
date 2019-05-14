package com.momo.basedemo.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;
import com.momo.basedemo.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 动态申请权限activity
 */

public class PermissionsActivity extends BaseActivity {

    @BindView(R.id.read_sms)
    TextView read_sms;
    @BindView(R.id.read_sms_content)
    TextView read_sms_content;

    @BindView(R.id.read_contact)
    TextView readContact;
    @BindView(R.id.read_content)
    TextView read_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("permissions_test");
        setContentView(R.layout.activity_permissions);
        ButterKnife.bind(this);
        initView();

    }


    /**
     * 初始化view
     */
    private void initView() {

    }


    @OnClick({R.id.read_sms, R.id.read_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.read_sms:
                getSmsContent();
                break;
            case R.id.read_contact:
                getContactContent();
                break;
        }
    }

    private void getContactContent() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(PermissionsActivity.this, Manifest.permission.READ_CONTACTS);
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {//如果权限有的话
            String contact = getContact();
            read_content.setText(contact);
        } else {
            LogUtils.e(TAG, "申请权限");
            ActivityCompat.requestPermissions(PermissionsActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 10);
        }

    }

    private void getSmsContent() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(PermissionsActivity.this, Manifest.permission.READ_SMS);
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {//如果权限有的话

            String smsInPhone = getSmsInPhone();
            read_sms_content.setText(smsInPhone);
        } else {
            LogUtils.e(TAG, "申请权限");
            ActivityCompat.requestPermissions(PermissionsActivity.this, new String[]{Manifest.permission.READ_SMS}, 11);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtils.e(TAG, requestCode + "----" + permissions.toString() + "-----" + grantResults.toString());
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (11 == requestCode) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) { //如果允许
                String smsInPhone = getSmsInPhone();
                read_sms_content.setText(smsInPhone);
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                    showCoInfo();
                } else {
                    Toast.makeText(mContext, "权限被拒绝了", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (10 == requestCode) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) { //如果允许
                String contact = getContact();
                read_content.setText(contact);
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    showCoInfo();
                } else {
                    Toast.makeText(mContext, "权限被拒绝了", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 获取所有短信
     *
     * @return
     */
    public String getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";

        StringBuilder smsBuilder = new StringBuilder();

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Cursor cur = getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信

            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");

                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else {
                        strType = "null";
                    }

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(strDate + ", ");
                    smsBuilder.append(strType);
                    smsBuilder.append(" ]\n\n");
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            } // end if

            smsBuilder.append("getSmsInPhone has executed!");

        } catch (SQLiteException ex) {
            Log.d("SQLiteException", ex.getMessage());
        }

        return smsBuilder.toString();
    }

    /**
     * 获取联系人
     */
    private String getContact() {
        String string = "";
        int count = 0;
        ContentResolver resolver = getApplicationContext().getContentResolver();

        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        while (cursor.moveToNext()) {

            // 取得联系人的名字索引
            int nameIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameIndex);
            string += contact + ":";
            // 取得联系人的ID索引值
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));

            // 查询该位联系人的电话号码，类似的可以查询email，photo
            Cursor phone = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);// 第一个参数是确定查询电话号，第三个参数是查询具体某个人的过滤值

            // 一个人可能有几个号码
            while (phone.moveToNext()) {
                String strPhoneNumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                string = string + strPhoneNumber + " ;";

            }

            string += "\n";
            count++;
            phone.close();
        }
        cursor.close();
        Log.i(TAG, String.valueOf(count));
        // 设置显示内容
        return string;

    }

    public void showCoInfo() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("再次申请权限")
                .setMessage("为了更好的给您提供服务，我们需要读取短信权限！谢谢配合！")
                .setCancelable(false)
                .setPositiveButton("马上授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCompat.requestPermissions(PermissionsActivity.this, new String[]{Manifest.permission.READ_SMS}, 11);


                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 13);//设置了requestcode 需要在OnActivityResult 中再次判断是否勾选了所需权限
                    }
                }).setNegativeButton("狠心拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }

}
