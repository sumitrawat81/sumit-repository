package com.sibsefid.model;

/**
 * Created by ubuntu on 8/2/17.
 */
import java.util.List;

/**
 * Created by sumit on 26/12/16.
 */
public class LanguageModule {



    private List<String> data;


    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}