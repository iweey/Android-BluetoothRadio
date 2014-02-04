package com.devin.BluetoothRadio;

/**
 * Created by Gallery on 13-9-14.
 */
public class TestJava {
    public static void main(String args[]) {
        //byte[] temporary = { 0x12 };
        //int volumeValue = Integer.parseInt(MathMethod.byte2HexStr(temporary), 16);
        //System.out.println(MathMethod.byte2HexStr(temporary) + " ; " + volumeValue);

        byte[] targetByte = { 0x12, 0x00, 0x00, 0x00 };
        System.out.println(MathMethod.bytesToInt(targetByte));

        String volumeStrHex = Integer.toHexString(40);
        System.out.println(volumeStrHex);
        byte volumeByteHex = Byte.parseByte(volumeStrHex, 16);
        System.out.println(volumeByteHex);

        byte xxx2 = (byte)0x28;
        if(volumeByteHex == xxx2) {
            System.out.println("OKOKOK");
        }

        int xxxxx = 92000;
        String hexStr = Integer.toHexString(xxxxx);

        String firstHex = hexStr.substring(1, 3);
        String secondHex = hexStr.substring(3,5);

        System.out.println(hexStr + " = " + firstHex + " = " + secondHex);
        byte volumeByteHex2 = MathMethod.HexStringToBytes(firstHex);
        MathMethod.HexStringToBytes(secondHex);

        byte xxx3 = (byte)0x67;
        if(volumeByteHex2 == xxx3) {
            System.out.println("OKOKOK");
        }

        //byte[] volueByteHexs = MathMethod.HexString2Bytes(volumeStrHex);
        //System.out.println(" =====> " + volueByteHexs.length);
        //for(int i=0; i<volueByteHexs.length; i++) {
            //System.out.println(volueByteHexs[i]);
        //}

        String testHEX = "c0";
        byte testBytes = MathMethod.hexStr2Bytes(testHEX);
        byte testBytes2 = (byte)0xc0;
        if(testBytes == testBytes2) {
            System.out.println("HAHAHA");
        }

        byte galleryByte = (byte)0x00;
        System.out.println(MathMethod.byteHEXtoInt(galleryByte));

        byte[] textBytes = {(byte)0xE0, (byte)0xA5, (byte)0x01, (byte)0x00};
        System.out.println("gallery: " + MathMethod.bytesToInt(textBytes));

        byte[] textBytes22 = {(byte)0xCC, (byte)0x55, (byte)0x01, (byte)0x00};
        System.out.println("gallery: " + MathMethod.bytesToInt(textBytes22));

        byte[] textBytes33 = {(byte)0x64, (byte)0x00, (byte)0x00, (byte)0x00};
        System.out.println("gallery: " + MathMethod.bytesToInt(textBytes33));

        byte[] textBytes44 = {(byte)0x2E, (byte)0x22, (byte)0x01, (byte)0x00};
        System.out.println("gallery ================= " + MathMethod.bytesToInt(textBytes44));

        String ok = "123456";
        System.out.println(Integer.parseInt(ok.substring(0, 1)));

        int ok2 = 250;
        System.out.println(Integer.toBinaryString(ok2));

        String passwordValue = "862193";
        if(passwordValue.length() == 6) {
            int valueSum = 0;
            for(int i=0; i<passwordValue.length(); ) {
                int wordXX = Integer.parseInt(passwordValue.substring(i, i+1));
                int wordYY = Integer.parseInt(passwordValue.substring((i+1), i+2));
                valueSum = valueSum + wordXX * wordYY;
                i = i + 2;
            }
            String valueSumHex = Integer.toHexString(valueSum);
            System.out.println("valueSumHex ===> " + valueSumHex);
            byte valueSumByte = MathMethod.hexStr2Bytes(valueSumHex);
            System.out.println("valueSumByte ===> " + valueSumByte);
            byte sendCiphertext = (byte) (valueSumByte ^ MathMethod.contentForSecurity);
            System.out.println("sendCiphertext ===> " + sendCiphertext);

            byte valueSumByteX = (byte) 0x4D;
            System.out.println("valueSumByteX ===> " + valueSumByteX);
            byte contentForSecurityX = (byte) 0xAA;
            byte sendCiphertextX = (byte) (valueSumByteX ^ contentForSecurityX);

            System.out.println("sendCiphertextX ===> " + sendCiphertextX);
        }

        byte x111 = (byte)0x10;
        byte x222 = (byte)0x35;
        byte[] xxxbyte = {x222, x111, (byte)0, (byte)0};
        int xxx = MathMethod.bytesToInt(xxxbyte);
        System.out.println(xxx);

        String temporary1 = "66.90";
        if(temporary1.indexOf(".") == (temporary1.length()-2)) {
            temporary1 = temporary1 + "0";
        }
        System.out.println("temporary1 = " + temporary1);

        String xxxStr = "com.google.android.tts";
        if(xxxStr.contains("android.tts")) {
            System.out.println("===com.google.android.tts");
        }

        String StringOK = "我爱北京天安门";
        String stringOK2 = StringOK.substring(0, 3);
        System.out.println(stringOK2);
    }
}
