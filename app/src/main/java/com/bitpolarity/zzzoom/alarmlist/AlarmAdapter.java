package com.bitpolarity.zzzoom.alarmlist;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.bitpolarity.zzzoom.R;
import com.bitpolarity.zzzoom.db.Alarm;
import com.bitpolarity.zzzoom.db.AlarmDao;
import com.bitpolarity.zzzoom.db.AppDatabase;
import com.bitpolarity.zzzoom.db.DBManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmVH> {

    public static final String TAG = "AlarmAdapter";

    private Context context;
    private List<Alarm> alarmList;
    private ULEventListner ul;
    AppDatabase db;
    // int lastPosition = Integer.MAX_VALUE;


    public AlarmAdapter(Context context, ULEventListner ul){
        this.context=  context;
        this.ul = ul;
        db = AppDatabase.getDbInstance(context);



    }

    void setAL(List<Alarm> alarmList){
        this.alarmList = alarmList;
        notifyDataSetChanged();

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

        if (alarm.isRepeated){
            holder.isRepeated.setVisibility(View.VISIBLE);
            holder.repeatTxt.setText(formatRepeatDays(alarm.repeatOn));
        }else {
            holder.isRepeated.setVisibility(View.GONE);
            holder.repeatTxt.setText(alarm.repeatOn);
        }

      //  holder.switchCompat.setChecked(checkPositions.contains(alarm));

        if (alarm.isActive){
            holder.mainbg.setBackgroundResource(R.drawable.secondary_button_bg_ma);
        }else {
            holder.mainbg.setBackgroundResource(R.drawable.inactive);
        }

        holder.switchCompat.setChecked(alarm.isActive);

        Log.d(TAG, "onBindViewHolder: Hour "+alarm.time);

        if (alarm.time.contains("am")){
            String time = alarm.time.replace(" am","");
            holder.time.setText(time);
            Log.d(TAG, "onBindViewHolder: Changed time  "+time);

            SpannableString ss1=  new SpannableString("am");
            ss1.setSpan(new RelativeSizeSpan(0.4f), 0, ss1.length(), 0);
            holder.time.append(ss1);

        }else {
            holder.time.setText(alarm.time.replace(" pm",""));
            SpannableString ss1=  new SpannableString("pm");
            ss1.setSpan(new RelativeSizeSpan(0.4f), 0, ss1.length(), 0);
            holder.time.append(ss1);
        }

       // holder.time.se/tText(alarm.time);

       if (alarm.title.equals("")){
           holder.title.setText("Meeting");
       }else {
            holder.title.setText(alarm.title);
    }

     //  setAnimation(holder.itemView,position);

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
        ImageView isRepeated;
        SwitchCompat switchCompat;
        ULEventListner ulEventListner;
        LinearLayoutCompat mainbg;
        TextView am_pm;

        public AlarmVH(@NonNull View itemView, ULEventListner ulEventListner) {
            super(itemView);

            title = itemView.findViewById(R.id.title_alarm);
            time= itemView.findViewById(R.id.time_txt);
            repeatTxt = itemView.findViewById(R.id.repeatDays);
            app_ico = itemView.findViewById(R.id.app_ico);
            isRepeated= itemView.findViewById(R.id.isRepeated);
            switchCompat = itemView.findViewById(R.id.isActiveSwitch);
            this.ulEventListner = ulEventListner;
            itemView.setOnClickListener(this);
            switchCompat.setOnClickListener(this);
            mainbg = itemView.findViewById(R.id.main_bg);
        }


        @Override
        public void onClick(View view) {
            if (view == itemView) {
                ulEventListner.onClick(getAdapterPosition());
            }else if (view == switchCompat){
                ulEventListner.onClickSwitch(getAdapterPosition());
            }
        }



    }

    public interface ULEventListner{
        void onClick(int position);
        void onClickSwitch(int position);
    }



//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position < lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.fade_in);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
}
