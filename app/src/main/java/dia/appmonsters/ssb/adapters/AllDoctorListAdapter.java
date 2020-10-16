package dia.appmonsters.ssb.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dia.appmonsters.ssb.R;
import dia.appmonsters.ssb.model.DoctorInfo;

public class AllDoctorListAdapter extends RecyclerView.Adapter<AllDoctorListAdapter.MyViewHolder> {
    private List<DoctorInfo> allDoctorList;
    private OnItemClickListener onItemClickListener;

    private List<DoctorInfo> tempFilterList;

    public AllDoctorListAdapter(List<DoctorInfo> allDoctorList, OnItemClickListener onItemClickListener) {
        this.allDoctorList = allDoctorList;
        this.onItemClickListener = onItemClickListener;

        this.tempFilterList = new ArrayList<>(allDoctorList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_doctor_list, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DoctorInfo model = allDoctorList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(model.getImage(), 0, model.getImage().length);
        holder.civImage.setImageBitmap(bitmap);
        holder.tvName.setText(model.getName());
        holder.tvDepartment.setText(model.getDepartment());
        holder.tvDesignation.setText(model.getDesignation());

    }

    @Override
    public int getItemCount() {
        return allDoctorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvDesignation, tvDepartment;
        CircleImageView civImage;

        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;

            tvName = itemView.findViewById(R.id.single_doctor_name);
            tvDesignation = itemView.findViewById(R.id.single_doctor_designation);
            tvDepartment = itemView.findViewById(R.id.single_doctor_department);
            civImage = itemView.findViewById(R.id.single_doctor_image);

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

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<DoctorInfo> tempList = new ArrayList<>();

            if (charSequence == null || charSequence.length() <= 0) {
                tempList.addAll(tempFilterList);
            } else {
                CharSequence filterString = charSequence.toString().toLowerCase();

                for (DoctorInfo doctorInfo : tempFilterList) {
                    if (doctorInfo.getName().toLowerCase().contains(filterString)
                            || String.valueOf(doctorInfo.getId()).toLowerCase().contains(filterString)
                            || (filterString.length() > 5 && doctorInfo.getPhone().toLowerCase().contains(filterString))
                            || (filterString.length() > 5 && filterString.toString().contains("@") && doctorInfo.getEmail().toLowerCase().contains(filterString))) {

                        tempList.add(doctorInfo);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            allDoctorList.clear();
            allDoctorList.addAll((List<DoctorInfo>)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public Filter getFilter(){
        return filter;
    }
}
