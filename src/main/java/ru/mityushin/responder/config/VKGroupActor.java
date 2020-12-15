package ru.mityushin.responder.config;

import com.vk.api.sdk.client.actors.GroupActor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vk.api")
public class VKGroupActor {
    private String accesstoken;
    private String v;
    private String secret;
    private int groupid;

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }


    public GroupActor getGroupActor() {
        GroupActor groupActor = new GroupActor(groupid, accesstoken);
        return groupActor;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    private String confirmation;

}
