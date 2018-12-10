package uiuc.kalysta.cs125final;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;

public class MainPageActivity extends AppCompatActivity {
    private int[] imageArray = {
            R.drawable.hambuger,
            R.drawable.huoguo,
            R.drawable.jianbing,
            R.drawable.mian,
            R.drawable.jiaozi,
            R.drawable.longxia,
            R.drawable.mixian,
            R.drawable.shousi,
            R.drawable.shaokao,
            R.drawable.niupai,
    };

    ArrayList<UserObject> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        userList = new ArrayList<>();
        getContactList();



        Button mLogout = findViewById(R.id.logout);
        Button mFindUser = findViewById(R.id.findUser);
        Button mPerson = findViewById(R.id.Who);
        final TextView mText = findViewById(R.id.personText);
        final ImageView mView = findViewById(R.id.imageView1);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        mPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random ran = new Random();
                int index = ran.nextInt(userList.size());
                String name = userList.get(index).getName();
                mText.setText(name);
            }
        });

        mFindUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random ran = new Random();
                int index = ran.nextInt(imageArray.length);
                mView.setImageResource(imageArray[index]);
            }
        });


        /*mFindUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), FindUserActivity.class));
            }
        });*/

        getPermissions();
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS},1);
        }
    }

    private void getContactList() {

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while(phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            UserObject mContact = new UserObject(name, phone);
            userList.add(mContact);
            //mUserListAdapter.notifyDataSetChanged();
            //getUserDetails(mContact);
        }
    }
}
