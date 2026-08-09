package com.topazkang.homehubagent.firewall;

import org.springframework.stereotype.Service;

@Service
public class FirewallService {

    public boolean checkIpOpen(String ip){
        return true;
    }

    public String openIp(String ip){
        return ip;
    }

    public String closeIp(String ip){
        return ip;
    }
}
