package vk.polis.espresso;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<ListItem> {
    private final int layoutResource;
    private final List<ListItem> items;

    public MyListAdapter(@NonNull Context context, int layoutResource, @NonNull List<ListItem> items) {
        super(context, layoutResource, items);
        this.layoutResource = layoutResource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layoutResource, null);
            viewHolder = new ViewHolder();
            viewHolder.itemText = view.findViewById(R.id.listItemText);
            viewHolder.deleteButton = view.findViewById(R.id.deleteButton);
            viewHolder.checkBox = view.findViewById(R.id.listItemCheckBox);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final ListItem item = items.get(position);

        if (item != null) {
            viewHolder.itemText.setText(item.getItemText());
            viewHolder.itemText.setOnClickListener(v -> showEditDialog(position));

            viewHolder.deleteButton.setOnClickListener(v -> removeItem(position));
            viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setDone(isChecked);
                notifyDataSetChanged();
            });
            viewHolder.checkBox.setChecked(item.isDone());
        }

        return view;
    }

    private void showEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit item");

        final EditText input = new EditText(getContext());
        input.setText(items.get(position).getItemText());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newItemText = input.getText().toString();
            items.get(position).setItemText(newItemText);
            notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView itemText;
        Button deleteButton;
        CheckBox checkBox;
    }
}
