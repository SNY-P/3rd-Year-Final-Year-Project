package com.example.fyp_926975;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// an adapter for RecyclerView <- which is used to show the cartesian product results
public class cartesianAdapter extends RecyclerView.Adapter<cartesianAdapter.MyViewHolder> {
    private Context context;
    private ArrayList questionNum, list1, list2, userAnswer, correctAnswer, ifCorrect;

    public cartesianAdapter(Context context, ArrayList questionNum, ArrayList list1, ArrayList list2, ArrayList userAnswer, ArrayList correctAnswer, ArrayList ifCorrect) {
        this.context = context;
        this.questionNum = questionNum;
        this.list1 = list1;
        this.list2 = list2;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.ifCorrect = ifCorrect;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartesian_result_list, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.questionNum.setText(String.valueOf(questionNum.get(position)));
        holder.list1.setText(String.valueOf(list1.get(position)));
        holder.list2.setText(String.valueOf(list2.get(position)));
        holder.userAnswer.setText(String.valueOf(userAnswer.get(position)));
        holder.correctAnswer.setText(String.valueOf(correctAnswer.get(position)));
        holder.ifCorrect.setText(String.valueOf(ifCorrect.get(position)));
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionNum, list1, list2, userAnswer, correctAnswer, ifCorrect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNum = itemView.findViewById(R.id.questionIDinput);
            list1 = itemView.findViewById(R.id.textlist1);
            list2 = itemView.findViewById(R.id.textlist2);
            userAnswer = itemView.findViewById(R.id.textuseranswer);
            correctAnswer = itemView.findViewById(R.id.textcorrectanswer);
            ifCorrect = itemView.findViewById(R.id.textcheck);
        }
    }
}
