package farsight.solutions.tutorial.diceroller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    DiceAdapter diceAdapter;
    List <Dice> diceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connecting activity to layout
        setContentView(R.layout.activity_main);

        //Setup ListView and Adapter
        ListView listView = findViewById(R.id.dice_list);
        diceAdapter = new DiceAdapter(this, R.layout.dice_row, diceList);
        listView.setAdapter(diceAdapter);

        //Initialize Data
        diceAdapter.add(new Dice());
    }

    public void rollDice(View view) {
        //roll all dice
        for(Dice dice : diceList) {
            if(!dice.hold)
                dice.roll();
        }

        //notify adapter to update view
        diceAdapter.notifyDataSetChanged();
    }

    public void addDice(View view) {
        diceAdapter.add(new Dice());
    }

    public void removeDice(View view) {
        int index = diceList.size()-1;
        if(index >= 0) {
            diceAdapter.remove(diceAdapter.getItem(index));
        }
    }

    public class DiceAdapter extends ArrayAdapter<Dice> {
        public DiceAdapter(@NonNull Context context, int resource, List<Dice> list) {
            super(context, resource, list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.dice_row, parent, false);
            }

            //setup dice label
            TextView textView = convertView.findViewById(R.id.dice_number);
            textView.setText(Integer.toString(position));

            //setup dice image
            ImageView imageView = convertView.findViewById(R.id.dice_icon);
            final Dice dice = diceAdapter.getItem(position);
            switch (dice.getValue()) {
                case BLANK:
                    imageView.setImageResource(R.drawable.blank_dice);
                    break;
                case MAGNIFY:
                    imageView.setImageResource(R.drawable.magnifying_glass);
                    break;
                case STAR:
                    imageView.setImageResource(R.drawable.star);
                    break;
            }

            //setup dice hold button
            Button button = convertView.findViewById(R.id.dice_hold_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dice dice = diceList.get(position);
                    dice.toggleHold();
                }
            });

            return convertView;
        }
    }

    public static class Dice {
        boolean hold = false;
        public Type value;

        public enum Type {
            BLANK,
            MAGNIFY,
            STAR
        }

        public static Random random = new Random();

        Dice() {
            roll();
        }

        public void roll() {
            int num = random.nextInt(Type.values().length);
            this.value = Type.values()[num];
        }

        public Type getValue() {
            return value;
        }

        public void toggleHold() {
            hold = !hold;
        }
    }
}
