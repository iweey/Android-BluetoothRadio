package com.devin.BluetoothRadio;

/**
 * Created by Gallery on 13-9-13.
 */

import android.content.SharedPreferences;

public class MathMethod {

    public static byte contentForSecurity = (byte) 0xAA;
    public static byte receiveForSecurity;

    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n=0; n<b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
            sb.append(";");
        }
        return sb.toString().toUpperCase().trim();
    }

    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);
        return addr;
    }

    public static int byteHEXtoInt(byte byteX) {
        byte[] targetBytes = new byte[4];
        targetBytes[0] = byteX;
        targetBytes[1] = 0x00;
        targetBytes[2] = 0x00;
        targetBytes[3] = 0x00;

        return MathMethod.bytesToInt(targetBytes);
    }

    public static byte HexStringToBytes(String src) { //Danger
        return Byte.parseByte(src, 16);
    }

    public static byte hexStr2Bytes(String src) {
        return (byte) (Integer.parseInt(src, 16) & 0xff);
    }

    public static byte[] byteCharToByte(byte[] sourceByte) {
        byte[] sourceByte2 = sourceByte;
        for(int i=0; i<sourceByte.length; ) {
            int j = i + 1;
            byte temporary = sourceByte[j];
            sourceByte[j] = sourceByte[i];
            sourceByte[i] = temporary;
            i = i + 2;
        }
        return sourceByte2;
    }

    public static byte[] copyBytes(byte[] ... eachBytes) {
        int allBytesLength = 0;
        for(int i=0; i<eachBytes.length; i++) {
            allBytesLength = allBytesLength + eachBytes[i].length;
        }
        byte[] targetBytes = new byte[allBytesLength];
        int copyIndex = 0;
        for(int j=0; j<eachBytes.length; j++) {
            System.arraycopy(eachBytes[j], 0, targetBytes, copyIndex, eachBytes[j].length);
            copyIndex = copyIndex + eachBytes[j].length;
        }
        return targetBytes;
    }

    public static String readSharedPreferences(String targetKey, String defaultValue) {
        String resultValue = "";
        SharedPreferences user = Activity02.galleryActivity.getSharedPreferences("BluetoothKeepFile", 0);
        resultValue = user.getString(targetKey, defaultValue);
        return resultValue;
    }

    public static void writeSharedPreferences(String targetKey, String resultValue) {
        SharedPreferences user = Activity02.galleryActivity.getSharedPreferences("BluetoothKeepFile", 0);
        user.edit().putString(targetKey, resultValue).commit();
    }
}


