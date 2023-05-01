package vk.polis.espresso;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyListAdapter adapter;
    private List<ListItem> items;
    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        Button addButton = findViewById(R.id.add_item);
        Button clearButton = findViewById(R.id.remove_all_items);
        ListView listView = findViewById(R.id.listView);

        items = new ArrayList<>();
        adapter = new MyListAdapter(this, R.layout.list_item, items);

        listView.setAdapter(adapter);

        addButton.setOnClickListener(view -> {
            String text = inputText.getText().toString();
            if (!text.isEmpty()) {
                ListItem item = new ListItem(text);
                items.add(item);
                adapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });

        clearButton.setOnClickListener(view -> {
            items.clear();
            adapter.notifyDataSetChanged();
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            final EditText editText = new EditText(MainActivity.this);
            editText.setText(items.get(i).getItemText());

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.edit_dialog_title)
                    .setMessage(R.string.edit_dialog_message)
                    .setView(editText)
                    .setPositiveButton(R.string.edit_dialog_ok_button, (dialogInterface, i1) -> {
                        String text = editText.getText().toString();
                        if (!text.isEmpty()) {
                            items.get(i1).setItemText(text);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(R.string.edit_dialog_negative_button, null)
                    .show();
        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            items.remove(i);
            adapter.notifyDataSetChanged();
            return true;
        });
    }
}
