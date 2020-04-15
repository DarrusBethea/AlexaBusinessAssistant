package com.example.androidalexaskillproject;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

//FAH 2/24/2020: this class is mainly for profits sheet
// i should have added better naming conventions, but if your trying to create list
// view for different sheet, justt create new class and reuse/ edit code
public class MainListFragment extends Fragment {
    private RecyclerView mProfitRecyclerView;
    private MainAdapter mAdapter;


    // public ImageView mDeleteImage;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.main_list_fragment, container, false);

        mProfitRecyclerView = view.findViewById(R.id.profits_recycler_view);
        mProfitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        (view.findViewById(R.id.Add_item)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfitReturnAddFragment.class);
                startActivity(intent);
            }
        });
        updateUI();
        return view;
    }

    private void updateUI() {

        try {
            new ListInfo.GetData().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListInfo  listinfo = ListInfo.get(getActivity());


        List<Profits> profits = listinfo.getInfo();
        for(int i = 0; i < profits.size(); i++){
            System.out.println(profits.get(i).getmAmount());

        }
        mAdapter = new MainAdapter(profits);
        mProfitRecyclerView.setAdapter(mAdapter);
    }

    private class Viewholder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView mAddImage;
        public ImageView mEditImage;
        public ImageView mDeleteImage;
        private TextView mNameProfits;
        private TextView MValueProfits;
        private TextView mLastnameProfits;
        private TextView mDateProfits;

        private Profits mProfit;

        public Viewholder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_view_items, parent, false));
            itemView.setOnClickListener(this);
            mNameProfits = itemView.findViewById(R.id.name_list);
            MValueProfits = itemView.findViewById(R.id.profit_list);
            mLastnameProfits = itemView.findViewById(R.id.list_profits_lastname);
            mDateProfits = itemView.findViewById(R.id.list_profit_date);
            mDeleteImage = itemView.findViewById(R.id.profit_image_delete_list);

            //Removed this from the view card and made this a general add button on the top bar
            //mAddImage = itemView.findViewById(R.id.profit_image_add_list);

            mEditImage = itemView.findViewById(R.id.profit_image_edit_list);


        }

        public void bind(Profits profit) {
            mProfit = profit;
            mNameProfits.setText(mProfit.getmName());
            MValueProfits.setText(mProfit.getmAmount());
            mLastnameProfits.setText(mProfit.getmLastname());
            mDateProfits.setText(mProfit.getmDate());

        }

        @Override
        public void onClick(View v) {

        }




    }



    private class MainAdapter extends RecyclerView.Adapter<Viewholder> {
        private List<Profits> mProfts;



        public MainAdapter(List<Profits> _profits) {
            mProfts = _profits;
        }




        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Viewholder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(Viewholder holder, int position) {
            final Profits profit = mProfts.get(position);
            holder.bind(profit);


            holder.mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("delete_name",profit.getmName() );
                    intent.putExtra("delete_last_name",profit.getmLastname() );
                    intent.putExtra("delete_amount",profit.getmAmount() );
                    intent.putExtra("delete_date",profit.getmDate());
                    startActivity(intent);
                }
            });

            //M.Marinaro 3/8/20 : Add button moved to top bar
//            holder.mAddImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), ProfitReturnAddFragment.class);
//
//                    startActivity(intent);
//                }
//            });

            holder.mEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ProfitReturnEditFragment.class);
                    intent.putExtra("edit_name",profit.getmName() );
                    intent.putExtra("edit_last_name",profit.getmLastname() );
                    intent.putExtra("edit_amount",profit.getmAmount() );
                    intent.putExtra("edit_date",profit.getmDate());
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return mProfts.size();
        }
    }

}
