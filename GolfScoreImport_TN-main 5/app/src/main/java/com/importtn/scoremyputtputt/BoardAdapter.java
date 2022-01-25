package com.importtn.scoremyputtputt;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private final List<Player> players;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView playerNameThing;
        private final TextView playerScoreThing;

        public ViewHolder(View view) {
            super(view);

            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/aldrich.ttf");

            playerNameThing = (TextView) view.findViewById(R.id.playerNameThing);
            playerNameThing.setTypeface(typeface);
            playerNameThing.setTextColor(Color.BLACK);
            playerNameThing.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            playerNameThing.setGravity(Gravity.CENTER);
            playerNameThing.setPadding(8, 24, 8, 24);
            playerNameThing.setBackgroundResource(R.drawable.customborder);
            playerNameThing.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            playerScoreThing = (TextView) view.findViewById(R.id.playerScoreThing);
            playerScoreThing.setTypeface(typeface);
            playerScoreThing.setTextColor(Color.BLACK);
            playerScoreThing.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            playerScoreThing.setGravity(Gravity.CENTER);
            playerScoreThing.setPadding(8, 24, 8, 24);
            playerScoreThing.setBackgroundResource(R.drawable.customborder);
            playerScoreThing.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        }

        public TextView getPlayerNameThing() {
            return playerNameThing;
        }

        public TextView getPlayerScoreThing() {
            return playerScoreThing;
        }
    }

    public BoardAdapter(Game gameObject) {
        this.players = gameObject.getPlayers();
        Collections.sort(players);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.board_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getPlayerNameThing().setText(players.get(position).getName());
        viewHolder.getPlayerScoreThing().setText(Integer.toString(players.get(position).getTotalScore()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
