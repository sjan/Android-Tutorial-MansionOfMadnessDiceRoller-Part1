package farsight.solutions.tutorial.diceroller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.dice_list);
        DiceAdapter diceAdapter = new DiceAdapter(this, R.layout.dice_row, new ArrayList<Dice>());
        listView.setAdapter(diceAdapter);
        diceAdapter.add(new Dice());
    }

    public class DiceAdapter extends ArrayAdapter<Dice> {
        public DiceAdapter(@NonNull Context context, int resource, List<Dice> list) {
            super(context, resource, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.dice_row, parent, false);
            }

            TextView textView = convertView.findViewById(R.id.dice_number);
            textView.setText(Integer.toString(position));

            return convertView;
        }
    }

    public class Dice {

    }
}
