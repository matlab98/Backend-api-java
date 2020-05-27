package com.restfulproyecto.restfulspringboot.Utils;

import java.net.InetAddress;

public class IPManager {
    //region Static methods
    public static String getIpAddress() throws java.net.UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
    //endregion
}
