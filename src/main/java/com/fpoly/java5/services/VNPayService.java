package com.fpoly.java5.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import com.fpoly.java5.utils.Sha256;
import org.springframework.stereotype.Service;

@Service
public class VNPayService {

    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_TmnCode = "PDP6U7Q0";
    public static final String vnp_HashSecret = "9X6YRWEW0ON526SSIGFZ8IMNN67LS00L";
    public static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_ReturnUrl = "http://localhost:8080/user/vnpay-return";

    public String createPaymentUrl(int orderId, double amount) throws UnsupportedEncodingException {
        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_OrderInfo = "Thanh toan don hang:" + vnp_TxnRef;
        String vnp_IpAddr = "127.0.0.1";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", getCurrentDate());

        // Sort params by key
        vnp_Params = sortByKey(vnp_Params);

        // Build hash data
        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            hashData.append(entry.getKey());
            hashData.append('=');
            hashData.append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()));
            hashData.append('&');
        }
        hashData.append("vnp_SecureHash=" + vnp_HashSecret);

        String secureHash = Sha256.hash(hashData.toString());
        vnp_Params.put("vnp_SecureHashType", "SHA256");
        vnp_Params.put("vnp_SecureHash", secureHash);

        // Build URL
        StringBuilder url = new StringBuilder(vnp_Url);
        url.append("?");
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            url.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString()));
            url.append('=');
            url.append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()));
            url.append('&');
        }

        return url.toString();
    }

    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(cal.getTime());
    }

    private Map<String, String> sortByKey(Map<String, String> map) {
        Map<String, String> sortedMap = new HashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
        return sortedMap;
    }
}