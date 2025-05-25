package com.example.garbagecollectionapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garbagecollectionapp.R;
import com.example.garbagecollectionapp.models.SpecialRequest;
import com.example.garbagecollectionapp.utils.DateUtils;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private Context context;
    private List<SpecialRequest> requestList;

    public RequestAdapter(Context context, List<SpecialRequest> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        SpecialRequest request = requestList.get(position);

        holder.tvDate.setText(DateUtils.formatDate(request.getRequestDate(), DateUtils.DISPLAY_DATE_FORMAT));
        holder.tvDescription.setText(request.getDescription());
        holder.tvStatus.setText(request.getStatus());

        // Set status color
        switch (request.getStatus()) {
            case "PENDING":
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorWarning));
                holder.btnCancel.setVisibility(View.VISIBLE);
                break;
            case "APPROVED":
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorSuccess));
                holder.btnCancel.setVisibility(View.GONE);
                break;
            case "REJECTED":
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorError));
                holder.btnCancel.setVisibility(View.GONE);
                break;
            case "CANCELLED":
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
                holder.btnCancel.setVisibility(View.GONE);
                break;
        }

        holder.btnCancel.setOnClickListener(v -> {
            // TODO: Implement cancel request
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDescription, tvStatus;
        Button btnCancel;

        RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }
    }
}