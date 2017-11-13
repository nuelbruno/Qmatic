package ae.qmatic.tacme.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by nikunjkumar on 8/24/16.
 */
public class GetAvailableTimes {

    /**
     * time : 07:30
     */

    private List<AvailableTimeEntity> availableTime;

    public static GetAvailableTimes objectFromData(String str) {

        return new Gson().fromJson(str, GetAvailableTimes.class);
    }

    public List<AvailableTimeEntity> getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(List<AvailableTimeEntity> availableTime) {
        this.availableTime = availableTime;
    }

    public static class AvailableTimeEntity {
        private String time;

        public static AvailableTimeEntity objectFromData(String str) {

            return new Gson().fromJson(str, AvailableTimeEntity.class);
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
