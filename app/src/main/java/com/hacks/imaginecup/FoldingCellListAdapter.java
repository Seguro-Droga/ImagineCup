package com.hacks.imaginecup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hacks.imaginecup.model.Medicine;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class FoldingCellListAdapter extends ArrayAdapter<Medicine> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    FoldingCellListAdapter(@NonNull Context context, @NonNull List<Medicine> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Medicine med = getItem(position);

        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder holder;
        if (cell == null) {
            holder = new ViewHolder();
            LayoutInflater inf = LayoutInflater.from(getContext());
            cell = (FoldingCell) inf.inflate(R.layout.cell, parent, false);

            holder.price = cell.findViewById(R.id.title_price);
            holder.time = cell.findViewById(R.id.title_time_label);
            holder.date = cell.findViewById(R.id.title_date_label);
            holder.fromAddress = cell.findViewById(R.id.title_from_address);
            holder.toAddress = cell.findViewById(R.id.title_to_address);
            holder.requestsCount = cell.findViewById(R.id.title_requests_count);
            holder.pledgePrice = cell.findViewById(R.id.title_pledge);
            holder.contentNameView = cell.findViewById(R.id.content_name_view);
            holder.contentManufacName = cell.findViewById(R.id.content_from_address_1);
            holder.expiryDate = cell.findViewById(R.id.content_to_address_1);
            holder.medicineName = cell.findViewById(R.id.medicine_name);
            holder.mfdDate = cell.findViewById(R.id.content_deadline_time);
            holder.contents = cell.findViewById(R.id.content_delivery_time);
            cell.setTag(holder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            holder = (ViewHolder) cell.getTag();
        }

        if (null == med) {
            return cell;
        }

        holder.price.setText(med.getMedicineName());
        holder.time.setText("Rs 48");
        holder.date.setText(med.getManufacturer()
                .substring(Math.max(med.getManufacturer().length() - 2, 0)));
        holder.fromAddress.setText(med.getManufactureDate());
        holder.toAddress.setText(med.getExpiryDate());
        holder.requestsCount.setText("O+ve");
        holder.pledgePrice.setText(med.getMedicineID()
                .substring(Math.max(med.getMedicineID().length() - 3, 0)));
        holder.contentNameView.setText(med.getOwner()
                .substring(Math.max(med.getOwner().length() - 2, 0)));
        holder.contentManufacName.setText(med.getManufacturer()
                .substring(Math.max(med.getManufacturer().length() - 2, 0)));
        holder.expiryDate.setText(med.getExpiryDate());
        holder.medicineName.setText(med.getMedicineName());
        holder.mfdDate.setText(med.getManufactureDate());

        List<String> contents = med.getContents();
        StringBuilder content = new StringBuilder();
        if (contents != null) {
            for (String s: contents) {
                String[] parts = s.split("#");
                content.append(parts[1]).append(" ");
            }
            holder.contents.setText(content.toString());
        }

        return cell;
    }

    void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    private void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    private void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    private static class ViewHolder {
        TextView price;
        TextView pledgePrice;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;
        TextView contentNameView;
        TextView contentManufacName;
        TextView expiryDate;
        TextView medicineName;
        TextView mfdDate;
        TextView contents;
    }
}
