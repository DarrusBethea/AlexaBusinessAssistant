package com.example.androidalexaskillproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//FAH 2/24/2020: this class is mainly for expenses sheet
// i should have added better naming conventions, but if your trying to create list
// view for different sheet, justt create new class and reuse/ edit code
public class ExpenseListFragment extends Fragment {
    private RecyclerView mExpensesRecyclerView;
    private MainAdapter mAdapter;


    // public ImageView mDeleteImage;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.expense_list_fragment, container, false);

        mExpensesRecyclerView = view.findViewById(R.id.expenses_recycler_view);
        mExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        ExpenseListInfo  listinfo = ExpenseListInfo.get(getActivity());


        List<Expenses> expenses = listinfo.getInfo();
        for(int i = 0; i < expenses.size(); i++){
            System.out.println(expenses.get(i).getmAmount());

        }
        mAdapter = new MainAdapter(expenses);
        mExpensesRecyclerView.setAdapter(mAdapter);
    }

    private class Viewholder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView mAddImage;
        public ImageView mEditImage;
        public ImageView mDeleteImage;
        private TextView mNameExpenses;
        private TextView MValueExpenses;
        private TextView mTypeExpenses;
        private TextView mDateExpenses;

        private Expenses mExpenses;

        public Viewholder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.expense_list_view_items, parent, false));
            itemView.setOnClickListener(this);
            mNameExpenses = itemView.findViewById(R.id.name_list);
            MValueExpenses = itemView.findViewById(R.id.expense_list);
            mTypeExpenses = itemView.findViewById(R.id.list_expenses_type);
            mDateExpenses = itemView.findViewById(R.id.list_expense_date);
            mDeleteImage = itemView.findViewById(R.id.expense_image_delete_list);
            mAddImage = itemView.findViewById(R.id.expense_image_add_list);
            mEditImage = itemView.findViewById(R.id.expense_image_edit_list);


        }

        public void bind(Expenses expense) {
            mExpenses = expense;
            mNameExpenses.setText(mExpenses.getmName());
            MValueExpenses.setText(mExpenses.getmAmount());
            mTypeExpenses.setText(mExpenses.getmType());
            mDateExpenses.setText(mExpenses.getmDate());

        }

        @Override
        public void onClick(View v) {

        }




    }



    private class MainAdapter extends RecyclerView.Adapter<Viewholder> {
        private List<Expenses> mProfts;



        public MainAdapter(List<Expenses> _expenses) {
            mProfts = _expenses;
        }




        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Viewholder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(Viewholder holder, int position) {
            final Expenses expense = mProfts.get(position);
            holder.bind(expense);


            holder.mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("delete_name",expense.getmName() );
                    intent.putExtra("delete_last_name",expense.getmType() );
                    intent.putExtra("delete_amount",expense.getmAmount() );
                    intent.putExtra("delete_date",expense.getmDate());
                    startActivity(intent);
                }
            });

            holder.mAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ExpenseReturnAddFragment.class);

                    startActivity(intent);
                }
            });

            holder.mEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ExpenseReturnEditFragment.class);
                    intent.putExtra("edit_name",expense.getmName() );
                    intent.putExtra("edit_last_name",expense.getmType() );
                    intent.putExtra("edit_amount",expense.getmAmount() );
                    intent.putExtra("edit_date",expense.getmDate());
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
