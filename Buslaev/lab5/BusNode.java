package com.company;

class BusNode {
    private String number;
    private String route;
    private String time;
    private String platform;

    BusNode(String number, String route, String time, String platform) {
        this.time = time;
        this.route = route;
        this.number = number;
        this.platform = platform;
    }

    String getTime() {
        return time;
    }
    String getNumber() {
        return number;
    }
    String getRoute() {
        return route;
    }
    String getPlatform() {
        return platform;
    }
}
