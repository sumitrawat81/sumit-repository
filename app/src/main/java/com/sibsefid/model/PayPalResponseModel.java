package com.sibsefid.model;

/**
 * Created by root on 3/10/16.
 */
public class PayPalResponseModel {


    /**
     * environment : sandbox
     * paypal_sdk_version : 2.14.6
     * platform : Android
     * product_name : PayPal-Android-SDK
     */

    private ClientBean client;
    /**
     * create_time : 2016-10-03T11:12:19Z
     * id : PAY-3AE0825501428000GK7ZD2BY
     * intent : sale
     * state : approved
     */

    private ResponseBean response;
    /**
     * client : {"environment":"sandbox","paypal_sdk_version":"2.14.6","platform":"Android","product_name":"PayPal-Android-SDK"}
     * response : {"create_time":"2016-10-03T11:12:19Z","id":"PAY-3AE0825501428000GK7ZD2BY","intent":"sale","state":"approved"}
     * response_type : payment
     */

    private String response_type;

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public static class ClientBean {
        private String environment;
        private String paypal_sdk_version;
        private String platform;
        private String product_name;

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getPaypal_sdk_version() {
            return paypal_sdk_version;
        }

        public void setPaypal_sdk_version(String paypal_sdk_version) {
            this.paypal_sdk_version = paypal_sdk_version;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }

    public static class ResponseBean {
        private String create_time;
        private String id;
        private String intent;
        private String state;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
