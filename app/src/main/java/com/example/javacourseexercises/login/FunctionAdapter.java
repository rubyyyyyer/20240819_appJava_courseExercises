package com.example.javacourseexercises.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javacourseexercises.R;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder> {
    private static final String TAG = FunctionAdapter.class.getSimpleName();
    private final String[] functions;
    Context context;

    // 重新初始化 listener
    private OnItemListener listener;

    public FunctionAdapter(Context context, OnItemListener listener) {
        this.context = context;
        this.listener = listener;
        functions = context.getResources().getStringArray(R.array.listFunctions);
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        holder.nameText.setText(functions[position]);
        //Onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:" + holder.nameText.getText());
//                Toast.makeText(context,"" + functions[position],Toast.LENGTH_LONG).show();

                // 當我點擊按鈕時，將 position 與 functions 傳回 MainActivity
                listener.OnItemClicked(position, functions);
            }
        });
    }


    @Override
    public int getItemCount() {
        return functions.length;
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;

        //一定要有一個預設的建構子裡面要有View，這個ViewHolder是專門儲存一列資料裡面的內容。
        public FunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            //預設資料要取得元件的方法
            nameText = itemView.findViewById(android.R.id.text1);
        }
    }
}

