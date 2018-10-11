package com.xuexibao.fastgun.utils;


import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import java.util.Locale;




public class PhoneUtils {
    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();

    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    /**
     * 根据国家代码和手机号  判断手机号是否有效
     * @param phoneNumber
     * @param countryCode
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNumber, Integer countryCode){

        long phone = Long.parseLong(phoneNumber);

        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(countryCode);
        pn.setNationalNumber(phone);

        return phoneNumberUtil.isValidNumber(pn);

    }

    /**
     * 根据国家代码和手机号  判断手机运营商
     * @param phoneNumber
     * @param countryCode
     * @return
     */
    public static String getCarrier(String phoneNumber, Integer countryCode){

        long phone = Long.parseLong(phoneNumber);

        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(countryCode);
        pn.setNationalNumber(phone);
        //返回结果只有英文，自己转成成中文
        String carrierEn = carrierMapper.getNameForNumber(pn, Locale.ENGLISH);
        String carrierZh = "";
        carrierZh += geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
        switch (carrierEn) {
            case "China Mobile":
                carrierZh += "移动";
                break;
            case "China Unicom":
                carrierZh += "联通";
                break;
            case "China Telecom":
                carrierZh += "电信";
                break;
            default:
                break;
        }
        return carrierZh;
    }

    /**
     * 根据国家代码和手机号  判断手机运营商
     * @date 2017-4-26 上午11:30:18
     * @param phoneNumber
     * @return
     */
    public static String getCarrier(String phoneNumber){

        long phone = Long.parseLong(phoneNumber);

        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(86);
        pn.setNationalNumber(phone);
        //返回结果只有英文，自己转成成中文
        String carrierEn = carrierMapper.getNameForNumber(pn, Locale.ENGLISH);
        String carrierZh = "";
        carrierZh += geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
        switch (carrierEn) {
            case "China Mobile":
                carrierZh += "移动";
                break;
            case "China Unicom":
                carrierZh += "联通";
                break;
            case "China Telecom":
                carrierZh += "电信";
                break;
            default:
                break;
        }
        return carrierZh;
    }


    /**
     *
     * @Description: 根据国家代码和手机号  手机归属地
     * @date 2017-4-26 上午11:33:18
     * @param phoneNumber
     * @param countryCode
     * @return    参数
     */
    public static String getGeo(String phoneNumber, Integer countryCode){

        long phone = Long.parseLong(phoneNumber);

        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(countryCode);
        pn.setNationalNumber(phone);

        return geocoder.getDescriptionForNumber(pn, Locale.CHINESE);

    }

    public static void main(String[] args) {

        //166 号段测试 16638390283
        System.out.println(PhoneUtils.getCarrier("16638390283",86));
        System.out.println(PhoneUtils.getCarrier("17660630068"));



    }



}

