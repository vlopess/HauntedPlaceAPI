package com.hauntedplace.HauntedPlaceAPI.Models;
import org.apache.tomcat.util.codec.binary.Base64;

public class EncryptedId {
    private final String encryptedId;

    public EncryptedId(Long id){
        this.encryptedId = new String(new Base64().encode(id.toString().getBytes()));
    }

    public EncryptedId(String encryptedId){
        this.encryptedId = encryptedId;
    }

    public String get() {
        return encryptedId;
    }

    public Long getDecrypted() {
        if(encryptedId == null) return null;
        return Long.valueOf(new String(new Base64().decode(encryptedId.getBytes())));
    }
}
