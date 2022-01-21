package com.bitpolarity.zzzoom.alarmlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bitpolarity.zzzoom.R;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmVH> {


    private Context context;
    private List<Alarm> noteList;
    private ULEventListner ul;
    int lastPosition = Integer.MAX_VALUE;


    public AlarmAdapter(Context context, ULEventListner ul){
        this.context=  context;
        this.ul = ul;
    }

//    public void setNoteList(List<Note> noteList){
//        this.noteList = noteList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public AlarmVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.alarm_row, parent , false);
        return new AlarmVH(view,ul);
    }



    @Override
    public void onBindViewHolder(@NonNull AlarmVH holder, int position) {

//        if (!noteList.get(position).title.isEmpty()){
//            holder.title.setVisibility(View.VISIBLE);
//            holder.title.setText(noteList.get(position).title);
//            holder.desc.setText(noteList.get(position).desc);
//        }else{
//            holder.title.setVisibility(View.GONE);
//            holder.desc.setText(noteList.get(position).desc);
//            holder.desc.setTextSize(16f);
//        }

       // setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }



    class AlarmVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView desc;
        ULEventListner ulEventListner;

        public AlarmVH(@NonNull View itemView, ULEventListner ulEventListner) {
            super(itemView);

//            title = itemView.findViewById(R.id.title);
//            desc = itemView.findViewById(R.id.desc);
            this.ulEventListner = ulEventListner;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            ulEventListner.onClick(getAdapterPosition());
        }
    }

    public interface ULEventListner{
        void onClick(int position);
    }



//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position < lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_up);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
}
