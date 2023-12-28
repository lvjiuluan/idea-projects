package com.immoc.mall.form;

import lombok.Data;

import java.util.Date;

@Data
public class ShippingAddOrUpdateForm {

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
