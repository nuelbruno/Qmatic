package ae.qmatic.tacme.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by nikunjkumar on 8/24/16.
 */
public class GetAvailableDates {


    /**
     * date : 2017-08-31
     */

    private List<AvailableDateEntity> availableDate;

    public static GetAvailableDates objectFromData(String str) {

        return new Gson().fromJson(str, GetAvailableDates.class);
    }

    public List<AvailableDateEntity> getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(List<AvailableDateEntity> availableDate) {
        this.availableDate = availableDate;
    }

    public static class AvailableDateEntity {
        private String date;

        public static AvailableDateEntity objectFromData(String str) {

            return new Gson().fromJson(str, AvailableDateEntity.class);
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
