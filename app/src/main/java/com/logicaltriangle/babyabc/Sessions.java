package com.logicaltriangle.babyabc;

import android.content.Context;
import android.content.SharedPreferences;

public class Sessions {
    private SharedPreferences preferences;
    private Context context;

    Sessions(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("sessionInfo",Context.MODE_PRIVATE);
    }

    /************************Position for number activity***************************/

    public int getPosition() {
        return preferences.getInt("position",0);
    }

    public void setPosition(int position) {
        if(position < 10 && position >= 0){
            preferences.edit().putInt("position", position).commit();
        }
    }

    /************************Position for letter activity***************************/


    public int getPositionl() {
        return preferences.getInt("positionl",0);
    }

    public void setPositionl(int positionl) {
        if(positionl< 26 && positionl>= 0){
            preferences.edit().putInt("positionl", positionl).commit();
        }
    }

    /************************Sound state for both number and letter***************************/


    public int getSoundState() {
        return preferences.getInt("sound_state",0);
    }

    public void setSoundState(int state) {
        preferences.edit().putInt("sound_state", state).commit();
    }


}
