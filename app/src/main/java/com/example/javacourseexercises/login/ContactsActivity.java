package com.example.javacourseexercises.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseexercises.R;

public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CONTACTS = 10;
    private static final String TAG = ContactsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permission == PackageManager.PERMISSION_GRANTED){

            readContacts();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_CONTACTS);
        }
    }

    private void readContacts() {
        //read contacts
        //取得 ContentResolver 物件：就是在任何一個 Activity 中都可以利用它來取得讀取器，等於是一個讀取聯絡人資料的介面。
        //裡面會有 query (查詢)、 insert、 delete、 update …等方法。
        //Cursor 在 Content Provider 讀取資料後，會產生一個資料集指標 (Cursor)，它會停在第一筆資料之前，資料集不會整個被搬到App中，這樣太耗費資源。
        //當 Cursor 停放在某一筆資料時，可以利用 Cursor 身上的方法去讀資料集的欄位內容。
        //Android 類別裡面的 Uri(Uniform Resoures Identifier)，如同網址的概念，利用 Uri 就可以找到資料的位置，對應資料庫有點像是表格名稱。
        //Uri 都被附在一個特別的類別，這個類別稱為 Contract(制約類別/合約類別)，目的為提供存取資料的規範，跟 Contract 取得 Uri 就可以了！
        //Projection：查詢的時候想要有多少欄位 From Table。
        //Selection / SelectionArgs：(SQL)這是 Where 語法中的表現方式。
        //SortOlder：(SQL) 排序，Order By + 欄位名稱 。
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        //cursor.moveToNext()，如果往下移一筆有資料的話就回傳 true，沒有資料就是 false。
        while (cursor.moveToNext()){
            //cursor.getString(Int i)：欄位的 index 值，也就是單筆資料裡，每個欄位的索引值。
            //要得到聯絡人名稱卻沒有該欄位的 index 值，可以拿聯絡人名稱這個欄位去查詢 index 值，透過 cursor.getColumnIndex(聯絡人名稱) 就會傳回該欄位名稱的值。
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            //每一筆資料都有一個整數的 id 值
            //ContactsContract.Contacts._ID 取得欄位名稱
            //將欄位名稱送到 getColumnIndex() 可以得到一個 Int 值
            Integer id  = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            //HAS_PHONE_NUMBER 是一個 indicator(指示值)，如果是 1 代表有電話，如果是 0 代表沒有電話。
            Integer hasPhone = cursor.getInt(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Log.d(TAG, "readContacts: " + name);
            Log.d(TAG, "readContacts: " + id);

            if (hasPhone == 1){
                //ContactsContract.CommonDataKinds.Phone，電話號碼的 Contract 中有一個欄位稱 CONTENT_URI。
                //Selection / SelectionArgs：(SQL)這是 Where 語法中的表現方式。
                //Selection：查詢一個人身上的電話號碼，電話號碼裡有 CONTACT_ID 的參數 + "=?"，代表 CONTACT_ID = ?，有問號第四個參數就要給資料。
                //SelectionArgs：給它問號的資料，問號的資料就是剛剛給的 id 資料，且要將 Integer 轉型為字串。
                Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{String.valueOf(id)},
                        null);
                while (cursorPhone.moveToNext()){
                    String phone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    Log.d(TAG, "readContacts: " + phone);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CONTACTS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                readContacts();
            }
        }
    }
}