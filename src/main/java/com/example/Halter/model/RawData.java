package com.example.JavaWithDocker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RawData {
        @JsonIgnoreProperties(value="id", ignoreUnknown=true)
        private String id;
        @JsonIgnoreProperties(value="lat", ignoreUnknown=true)
        private String lat;
        @JsonIgnoreProperties(value="healthy", ignoreUnknown = true)
        private Boolean healthy;
        @JsonIgnoreProperties(value="lng", ignoreUnknown = true)
        private String lng;
        @JsonIgnoreProperties(value="timestamp", ignoreUnknown = true)
        private String timestamp;

        public RawData() {}

        public RawData getRawData() {
            return this;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }
        public void setLat(String lat) {
            this.lat = lat;
        }

        public Boolean getHealthy() {
            return healthy;
        }

        public void setHealthy(Boolean healthy) {
            this.healthy = healthy;
        }

        public String getLng() {
            return lng;
        }
        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getTimeStamp() {
            return timestamp;
        }
        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }