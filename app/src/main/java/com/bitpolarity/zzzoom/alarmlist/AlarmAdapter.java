package com.bitpolarity.zzzoom.alarmlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bitpolarity.zzzoom.R;
import com.bitpolarity.zzzoom.db.Alarm;
import com.bitpolarity.zzzoom.db.AlarmDao;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmVH> {

    public static final String TAG = "AlarmAdapter";

    private Context context;
    private List<Alarm> alarmList;
    private ULEventListner ul;


    public AlarmAdapter(Context context, ULEventListner ul){
        this.context=  context;
        this.ul = ul;
    }

    public void setAlarmList(List<Alarm> alarmList){
        this.alarmList = alarmList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlarmVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.alarm_row, parent , false);
        return new AlarmVH(view,ul);
    }



    @Override
    public void onBindViewHolder(@NonNull AlarmVH holder, int position) {


        Alarm alarm = alarmList.get(position);

        if (alarm.app == 'z'){
            holder.app_ico.setImageResource(R.drawable.zoom_ico);
        }else{
            holder.app_ico.setImageResource(R.drawable.meet_ico);
        }

        holder.time.setText(alarm.time);
        holder.repeatTxt.setText(formatRepeatDays(alarm.repeatOn));
        holder.title.setText(alarm.title);

    //    setAnimation(holder.itemView,position);
    }

    String formatRepeatDays(String raw){
        Log.d("AlarmAdapter", "Raw: "+raw);
        StringBuilder s = new StringBuilder("");

       for (int i = 0; i < raw.length() ; i++){
           Log.d(TAG, "Char at : "+raw.charAt(i));

               if (raw.charAt(i) == '0') s.append("Sun, ");
               else if (raw.charAt(i) == '1') s.append("Mon, ");
               else if (raw.charAt(i) == '2') s.append("Tue, ");
               else if (raw.charAt(i) == '3') s.append("Wed, ");
               else if (raw.charAt(i) == '4') s.append("Thu, ");
               else if (raw.charAt(i) == '5') s.append("Fri, ");
               else if (raw.charAt(i) == '6') s.append("Sat, ");

//               if (raw.charAt(i) == '0') s.append(" Sun");
//               else if (raw.charAt(i) == '1') s.append(" Mon");
//               else if (raw.charAt(i) == '2') s.append(" Tue");
//               else if (raw.charAt(i) == '3') s.append(" Wed");
//               else if (raw.charAt(i) == '4') s.append(" Thu");
//               else if (raw.charAt(i) == '5') s.append(" Fri");
//               else if (raw.charAt(i) == '6') s.append(" Sat");

           }

        s.replace(s.length()-2,s.length()-1,"");


        Log.d(TAG, "Final Days: "+s.toString());

      return s.toString();

    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }



    class AlarmVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView time;
        TextView repeatTxt;
        ImageView app_ico;
        ULEventListner ulEventListner;

        public AlarmVH(@NonNull View itemView, ULEventListner ulEventListner) {
            super(itemView);

            title = itemView.findViewById(R.id.title_alarm);
            time= itemView.findViewById(R.id.time_txt);
            repeatTxt = itemView.findViewById(R.id.repeatDays);
            app_ico = itemView.findViewById(R.id.app_ico);

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
