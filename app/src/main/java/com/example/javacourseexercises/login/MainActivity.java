package com.example.javacourseexercises.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.javacourseexercises.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javacourseexercises.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

//Function Adapter OnItemExitListener 利用介面來傳遞值
interface OnItemListener {
    //OnItemExitClicked(我要傳回什麼東西)
    void OnItemClicked(int position, String[] functions);
}

//要實現一個接口 implements OnItemExitListener
public class MainActivity extends AppCompatActivity implements OnItemListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    //    private static final int REQUEST_LOGIN = 100;
    Boolean logon = false;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ActivityResultLauncher activityResultLauncher;
    private Intent intent;
    private List<Function> gameFunctions;
    //    private String[] functions = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //[Android] 移除標題列 (Action Bar)
/*        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }*/

        TextView welcome = findViewById(R.id.welcome);
        welcome.setTextColor(Color.parseColor("#EADDFF"));
        welcome.setText("Welcome, " + getSharedPreferences("Logon", MODE_PRIVATE)
                .getString("ids", ""));


        if (!logon) {
            Toast.makeText(this, "尚未登入", Toast.LENGTH_LONG)
                    .show();

/*            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);*/
            //registerForActivityResult()方法
            activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        logon = true;
                    } else {
                        finish();
                    }
                }
            });
            intent = new Intent(this, LoginActivity.class);
            activityResultLauncher.launch(intent);
        }

/*        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });



        //RecycleView_grid
        setupFunctions();
        RecyclerView recyclerViewGrid = findViewById(R.id.recyclerView_grid);
        recyclerViewGrid.setHasFixedSize(true);
        recyclerViewGrid.setLayoutManager(new GridLayoutManager(this,3));

        //adapter_grid
        FunctionIconAdapter adapterGrid = new FunctionIconAdapter();
        recyclerViewGrid.setAdapter(adapterGrid);


        //-------------------------------------------------------------------
        //RecycleView_line
        RecyclerView recyclerView = findViewById(R.id.recyclerView_list);
        recyclerView.setHasFixedSize(true); //固定大小
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter_line
        FunctionAdapter adapterList = new FunctionAdapter(this, this);
        recyclerView.setAdapter(adapterList);


    }

    private void setupFunctions() {
        gameFunctions = new ArrayList<>();
        String[] functionItem = getResources().getStringArray(R.array.gameFunctions);
        gameFunctions.add(new Function(functionItem[0],R.drawable.ic_game_guessnum));
        gameFunctions.add(new Function(functionItem[1],R.drawable.ic_game_color));
        gameFunctions.add(new Function(functionItem[2],R.drawable.ic_game_shoot));
        gameFunctions.add(new Function(functionItem[3],R.drawable.ic_game_dice));
        gameFunctions.add(new Function(functionItem[4],R.drawable.ic_game_poker));
    }

    public class FunctionIconAdapter extends RecyclerView.Adapter<FunctionIconAdapter.IconViewHolder> {


        @NonNull
        @Override
        public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_icon,parent,false);
            return new IconViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
            Function functionsPosition = gameFunctions.get(position);
            holder.gametext.setText(functionsPosition.getGametext());
            holder.gameImg.setImageResource(functionsPosition.getGameImg());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameClicked(functionsPosition);
                }
            });

        }

        @Override
        public int getItemCount() {
            return gameFunctions.size();
        }

        public class IconViewHolder extends RecyclerView.ViewHolder{
            ImageView gameImg ;
            TextView gametext;

            public IconViewHolder(@NonNull View itemView) {
                super(itemView);
                gameImg = itemView.findViewById(R.id.gameImg);
                gametext = itemView.findViewById(R.id.gametext);
            }
        }


    }

    private void gameClicked(Function functionsPosition) {
        Log.d(TAG, "onClick: " + functionsPosition.getGametext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        TextView welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome, " + getSharedPreferences("Logon", MODE_PRIVATE)
                .getString("ids", ""));



    }


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN){//如果 == REQUEST_LOGIN，就代表是從LoginActivity回來的。
            if (resultCode != RESULT_OK){//如果 == RESULT_OK，就代表有正常登入回來。
                finish();
            }
        }
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_graph);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // 覆寫中 OnItemExitListener 的 OnItemExitClicked
    @Override
    public void OnItemClicked(int position, String[] functions) {
        Log.d(TAG, "onClick(main): 將position返回MainActivity");
//        Log.d(TAG, "onClick(main): 點擊了第position>>>" + position  + "項");
//        Log.d(TAG, "onClick(main): 點擊了第position + 1>>>" + (position + 1) + "項");
//        Log.d(TAG, "onClick(main): 總共有幾項, functions.length>>>" + functions.length);
//        Log.d(TAG, "onClick(main): test, functions.length-1>>>" + (functions.length-1));

        //登出
        if ((position + 1) == (functions.length-1)){
            intent = new Intent(this, LoginActivity.class);
            activityResultLauncher.launch(intent);
        }

            //離開
        if ((position + 1) == functions.length) {
            new AlertDialog.Builder(this)
                    .setTitle("確定要離開嗎?")
                    .setPositiveButton("留下",null)
                    .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();

        }
    }
}


