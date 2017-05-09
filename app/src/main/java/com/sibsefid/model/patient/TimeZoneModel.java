package com.sibsefid.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 1/10/16.
 */
public class TimeZoneModel {


    /**
     * success : true
     * Data : [{"Item":"(UTC-12:00) International Date Line West","value":"Dateline Standard Time"},{"Item":"(UTC-11:00) Coordinated Universal Time-11","value":"UTC-11"},{"Item":"(UTC-10:00) Hawaii","value":"Hawaiian Standard Time"},{"Item":"(UTC-09:00) Alaska","value":"Alaskan Standard Time"},{"Item":"(UTC-08:00) Baja California","value":"Pacific Standard Time (Mexico)"},{"Item":"(UTC-08:00) Pacific Time (US & Canada)","value":"Pacific Standard Time"},{"Item":"(UTC-07:00) Arizona","value":"US Mountain Standard Time"},{"Item":"(UTC-07:00) Chihuahua, La Paz, Mazatlan","value":"Mountain Standard Time (Mexico)"},{"Item":"(UTC-07:00) Mountain Time (US & Canada)","value":"Mountain Standard Time"},{"Item":"(UTC-06:00) Central America","value":"Central America Standard Time"},{"Item":"(UTC-06:00) Central Time (US & Canada)","value":"Central Standard Time"},{"Item":"(UTC-06:00) Guadalajara, Mexico City, Monterrey","value":"Central Standard Time (Mexico)"},{"Item":"(UTC-06:00) Saskatchewan","value":"Canada Central Standard Time"},{"Item":"(UTC-05:00) Bogota, Lima, Quito, Rio Branco","value":"SA Pacific Standard Time"},{"Item":"(UTC-05:00) Eastern Time (US & Canada)","value":"Eastern Standard Time"},{"Item":"(UTC-05:00) Indiana (East)","value":"US Eastern Standard Time"},{"Item":"(UTC-04:30) Caracas","value":"Venezuela Standard Time"},{"Item":"(UTC-04:00) Asuncion","value":"Paraguay Standard Time"},{"Item":"(UTC-04:00) Atlantic Time (Canada)","value":"Atlantic Standard Time"},{"Item":"(UTC-04:00) Cuiaba","value":"Central Brazilian Standard Time"},{"Item":"(UTC-04:00) Georgetown, La Paz, Manaus, San Juan","value":"SA Western Standard Time"},{"Item":"(UTC-04:00) Santiago","value":"Pacific SA Standard Time"},{"Item":"(UTC-03:30) Newfoundland","value":"Newfoundland Standard Time"},{"Item":"(UTC-03:00) Brasilia","value":"E. South America Standard Time"},{"Item":"(UTC-03:00) Buenos Aires","value":"Argentina Standard Time"},{"Item":"(UTC-03:00) Cayenne, Fortaleza","value":"SA Eastern Standard Time"},{"Item":"(UTC-03:00) Greenland","value":"Greenland Standard Time"},{"Item":"(UTC-03:00) Montevideo","value":"Montevideo Standard Time"},{"Item":"(UTC-03:00) Salvador","value":"Bahia Standard Time"},{"Item":"(UTC-02:00) Coordinated Universal Time-02","value":"UTC-02"},{"Item":"(UTC-02:00) Mid-Atlantic - Old","value":"Mid-Atlantic Standard Time"},{"Item":"(UTC-01:00) Azores","value":"Azores Standard Time"},{"Item":"(UTC-01:00) Cape Verde Is.","value":"Cape Verde Standard Time"},{"Item":"(UTC) Casablanca","value":"Morocco Standard Time"},{"Item":"(UTC) Coordinated Universal Time","value":"UTC"},{"Item":"(UTC) Dublin, Edinburgh, Lisbon, London","value":"GMT Standard Time"},{"Item":"(UTC) Monrovia, Reykjavik","value":"Greenwich Standard Time"},{"Item":"(UTC+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna","value":"W. Europe Standard Time"},{"Item":"(UTC+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague","value":"Central Europe Standard Time"},{"Item":"(UTC+01:00) Brussels, Copenhagen, Madrid, Paris","value":"Romance Standard Time"},{"Item":"(UTC+01:00) Sarajevo, Skopje, Warsaw, Zagreb","value":"Central European Standard Time"},{"Item":"(UTC+01:00) West Central Africa","value":"W. Central Africa Standard Time"},{"Item":"(UTC+01:00) Windhoek","value":"Namibia Standard Time"},{"Item":"(UTC+02:00) Amman","value":"Jordan Standard Time"},{"Item":"(UTC+02:00) Athens, Bucharest","value":"GTB Standard Time"},{"Item":"(UTC+02:00) Beirut","value":"Middle East Standard Time"},{"Item":"(UTC+02:00) Cairo","value":"Egypt Standard Time"},{"Item":"(UTC+02:00) Damascus","value":"Syria Standard Time"},{"Item":"(UTC+02:00) E. Europe","value":"E. Europe Standard Time"},{"Item":"(UTC+02:00) Harare, Pretoria","value":"South Africa Standard Time"},{"Item":"(UTC+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius","value":"FLE Standard Time"},{"Item":"(UTC+02:00) Istanbul","value":"Turkey Standard Time"},{"Item":"(UTC+02:00) Jerusalem","value":"Israel Standard Time"},{"Item":"(UTC+02:00) Tripoli","value":"Libya Standard Time"},{"Item":"(UTC+03:00) Baghdad","value":"Arabic Standard Time"},{"Item":"(UTC+03:00) Kaliningrad, Minsk","value":"Kaliningrad Standard Time"},{"Item":"(UTC+03:00) Kuwait, Riyadh","value":"Arab Standard Time"},{"Item":"(UTC+03:00) Nairobi","value":"E. Africa Standard Time"},{"Item":"(UTC+03:30) Tehran","value":"Iran Standard Time"},{"Item":"(UTC+04:00) Abu Dhabi, Muscat","value":"Arabian Standard Time"},{"Item":"(UTC+04:00) Baku","value":"Azerbaijan Standard Time"},{"Item":"(UTC+04:00) Moscow, St. Petersburg, Volgograd","value":"Russian Standard Time"},{"Item":"(UTC+04:00) Port Louis","value":"Mauritius Standard Time"},{"Item":"(UTC+04:00) Tbilisi","value":"Georgian Standard Time"},{"Item":"(UTC+04:00) Yerevan","value":"Caucasus Standard Time"},{"Item":"(UTC+04:30) Kabul","value":"Afghanistan Standard Time"},{"Item":"(UTC+05:00) Ashgabat, Tashkent","value":"West Asia Standard Time"},{"Item":"(UTC+05:00) Islamabad, Karachi","value":"Pakistan Standard Time"},{"Item":"(UTC+05:30) Chennai, Kolkata, Mumbai, New Delhi","value":"India Standard Time"},{"Item":"(UTC+05:30) Sri Jayawardenepura","value":"Sri Lanka Standard Time"},{"Item":"(UTC+05:45) Kathmandu","value":"Nepal Standard Time"},{"Item":"(UTC+06:00) Astana","value":"Central Asia Standard Time"},{"Item":"(UTC+06:00) Dhaka","value":"Bangladesh Standard Time"},{"Item":"(UTC+06:00) Ekaterinburg","value":"Ekaterinburg Standard Time"},{"Item":"(UTC+06:30) Yangon (Rangoon)","value":"Myanmar Standard Time"},{"Item":"(UTC+07:00) Bangkok, Hanoi, Jakarta","value":"SE Asia Standard Time"},{"Item":"(UTC+07:00) Novosibirsk","value":"N. Central Asia Standard Time"},{"Item":"(UTC+08:00) Beijing, Chongqing, Hong Kong, Urumqi","value":"China Standard Time"},{"Item":"(UTC+08:00) Krasnoyarsk","value":"North Asia Standard Time"},{"Item":"(UTC+08:00) Kuala Lumpur, Singapore","value":"Singapore Standard Time"},{"Item":"(UTC+08:00) Perth","value":"W. Australia Standard Time"},{"Item":"(UTC+08:00) Taipei","value":"Taipei Standard Time"},{"Item":"(UTC+08:00) Ulaanbaatar","value":"Ulaanbaatar Standard Time"},{"Item":"(UTC+09:00) Irkutsk","value":"North Asia East Standard Time"},{"Item":"(UTC+09:00) Osaka, Sapporo, Tokyo","value":"Tokyo Standard Time"},{"Item":"(UTC+09:00) Seoul","value":"Korea Standard Time"},{"Item":"(UTC+09:30) Adelaide","value":"Cen. Australia Standard Time"},{"Item":"(UTC+09:30) Darwin","value":"AUS Central Standard Time"},{"Item":"(UTC+10:00) Brisbane","value":"E. Australia Standard Time"},{"Item":"(UTC+10:00) Canberra, Melbourne, Sydney","value":"AUS Eastern Standard Time"},{"Item":"(UTC+10:00) Guam, Port Moresby","value":"West Pacific Standard Time"},{"Item":"(UTC+10:00) Hobart","value":"Tasmania Standard Time"},{"Item":"(UTC+10:00) Yakutsk","value":"Yakutsk Standard Time"},{"Item":"(UTC+11:00) Solomon Is., New Caledonia","value":"Central Pacific Standard Time"},{"Item":"(UTC+11:00) Vladivostok","value":"Vladivostok Standard Time"},{"Item":"(UTC+12:00) Auckland, Wellington","value":"New Zealand Standard Time"},{"Item":"(UTC+12:00) Coordinated Universal Time+12","value":"UTC+12"},{"Item":"(UTC+12:00) Fiji","value":"Fiji Standard Time"},{"Item":"(UTC+12:00) Magadan","value":"Magadan Standard Time"},{"Item":"(UTC+12:00) Petropavlovsk-Kamchatsky - Old","value":"Kamchatka Standard Time"},{"Item":"(UTC+13:00) Nuku'alofa","value":"Tonga Standard Time"},{"Item":"(UTC+13:00) Samoa","value":"Samoa Standard Time"}]
     */

    private boolean success;
    /**
     * Item : (UTC-12:00) International Date Line West
     * value : Dateline Standard Time
     */

    @SerializedName("Data")
    private ArrayList<TimeZoneBean> timezonebean;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<TimeZoneBean> getTimezonebean() {
        return timezonebean;
    }

    public void setTimezonebean(ArrayList<TimeZoneBean> timezonebean) {
        this.timezonebean = timezonebean;
    }

    public static class TimeZoneBean {
        private String Item;
        private String value;

        public String getItem() {
            return Item;
        }

        public void setItem(String Item) {
            this.Item = Item;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
