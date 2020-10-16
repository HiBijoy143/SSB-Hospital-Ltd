package dia.appmonsters.ssb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
;
import dia.appmonsters.ssb.R;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.MyViewHolder> {
    private List<String> allDepartmentList;
    private OnItemClickListener onItemClickListener;


    public DepartmentListAdapter(List<String> allDepartmentList, OnItemClickListener onItemClickListener) {
        this.allDepartmentList = allDepartmentList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DepartmentListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DepartmentListAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_department, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentListAdapter.MyViewHolder holder, int position) {
        holder.tvDepartment.setText(allDepartmentList.get(position));

    }

    @Override
    public int getItemCount() {
        return allDepartmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDepartment;

        DepartmentListAdapter.OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, DepartmentListAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;

            tvDepartment = itemView.findViewById(R.id.single_item_department_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
}

