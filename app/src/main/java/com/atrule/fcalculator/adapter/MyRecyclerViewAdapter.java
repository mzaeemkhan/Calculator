package com.atrule.fcalculator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.atrule.fcalculator.R;
import com.atrule.fcalculator.model.MyCalculationClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    // region Variables
    private final ArrayList<MyCalculationClass> myCalculationList;
    final Context mContext;
    // endregion

    // region Constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<MyCalculationClass> tempList)
    {
        mContext = context;
        myCalculationList = tempList;
    }
    // endregion

    // region ViewHolder Creation
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_layout, parent, false);

        return new ViewHolder(view);
    }
    // endregion

    // region ViewHolder Binding
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyCalculationClass myObject = myCalculationList.get(position);

        // region Getting Values
        double num1 = myObject.getNum1();
        String operation = String.valueOf(myObject.getOperation());
        double num2 = myObject.getNum2();
        double result = myObject.getResult();
        // endregion

        // region Random Color to RV Item
        int[] androidColors  = mContext.getResources().getIntArray(R.array.rainbow);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        holder.cardView.setCardBackgroundColor(randomAndroidColor);
        // endregion

        holder.textView.setText(calculateResult(num1) + " " + operation + " " + calculateResult(num2) + " " + mContext.getResources().getString(R.string.equal) + " " + calculateResult(result));
    }
    // endregion

    // region ItemCount
    @Override
    public int getItemCount() {
        return myCalculationList.size();
    }
    // endregion

    // region ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;
        final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.parent);
            textView = itemView.findViewById(R.id.myTextView);
        }
    }
    // endregion

    // region Result Function
    @SuppressLint("DefaultLocale")
    private String calculateResult(Double num) {

        String[] splitter = num.toString().split("\\.");
        boolean answer = ((splitter[1].startsWith(String.valueOf(0)) && (splitter[1].length() == 1)) || splitter[1].equals("00"));        // After  Decimal Count

        if (answer)
        {
            return splitter[0];
        }
        else
        {
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            return String.valueOf(decimalFormat.format(num));
        }
    }
    // endregion

}
