package com.example.javacourseexercises.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javacourseexercises.R;
import com.example.javacourseexercises.databinding.ActivityContactBinding;
import com.example.javacourseexercises.databinding.ActivityMainBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CONTACTS = 10;
    private static final String TAG = ContactsActivity.class.getSimpleName();
    private ActivityContactBinding binding;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarContact);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textContacts = findViewById(R.id.textContacts);
        textContacts.setTextColor(Color.parseColor("#EADDFF"));

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permission == PackageManager.PERMISSION_GRANTED){

            readContacts();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_CONTACTS);
        }
    }

    private void readContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        contactList = new ArrayList<>();

        while (cursor.moveToNext()){
          String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Integer id  = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Integer hasPhone = cursor.getInt(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Log.d(TAG, "readContacts: " + name);
            Log.d(TAG, "readContacts: " + id);

            Contact contact = new Contact(id,name);

            if (hasPhone == 1){
               Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{String.valueOf(id)},
                        null);
                while (cursorPhone.moveToNext()){
                    String phone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    Log.d(TAG, "readContacts: " + phone);

                    contact.getPhones().add(phone);
                }
            }
            contactList.add(contact);
        }

        ContactAdapter contactAdapter = new ContactAdapter(contactList);

        RecyclerView recyclerViewContacts = findViewById(R.id.recyclerView_contacts);
        recyclerViewContacts.setHasFixedSize(true);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(contactAdapter);
    }

    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
        List<Contact> contactList;
        public ContactAdapter(List<Contact> contactList){
            this.contactList = contactList;
        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_expandable_list_item_2, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            Contact contact = contactList.get(position);
            holder.nameText.setText(contact.getName());
            StringBuilder sb = new StringBuilder();
            for (String phone : contact.getPhones()) {
                sb.append(phone);
                sb.append("\n");
            }
            holder.phoneText.setText(sb.toString());
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }

        public class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView nameText;
            TextView phoneText;

            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                nameText = itemView.findViewById(android.R.id.text1);
                phoneText = itemView.findViewById(android.R.id.text2);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: 我有產生按鈕");
        //呼叫getMenuInflater().inflate(R.menu.menu_contacts, menu);把res充氣到menu物件中
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //當使用者點擊MenuItem就會跑到onOptionsItemSelected中，並把item傳進來
        //getItemId()是一個int值==R.id.actionUpload的id值，就把聯絡人資料上傳到Firebase
        if (item.getItemId() ==R.id.actionUpload){
            Log.d(TAG, "onOptionsItemSelected: Upload Firebase");
            //Upload Firebase
            String userID = getSharedPreferences("Logon",MODE_PRIVATE).getString("ids",null);
            if (userID != null) {
                FirebaseDatabase.getInstance().getReference("users").child(userID)
                        .child("contactList").setValue(contactList);
            }
        }
        return super.onOptionsItemSelected(item);
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