package com.smarter.LoveLog.model.orderMy;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class OrderFlowCheckOut {


    /**
     * succeed : 1
     * error_code : 10002
     * error_desc : 购物车中没有商品
     */

    private StatusEntity status;
    /**
     * goods_list : [{"rec_id":"9438","user_id":"2","goods_id":"2","goods_name":"爱的日志纪州备长炭净颜焕彩面膜","goods_sn":"MC000002","goods_number":"5","market_price":"8.90","goods_price":"5.90","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"29.50","formated_market_price":"¥8.90","formated_goods_price":"¥5.90","formated_subtotal":"¥29.50","img_thumb":"http://www.aiderizhi.com/images/201603/thumb_img/2_thumb_G_1458155762687.png"},{"rec_id":"9437","user_id":"2","goods_id":"1","goods_name":"爱的日志玫瑰水润蚕丝面膜","goods_sn":"MC000001","goods_number":"5","market_price":"5.90","goods_price":"3.90","goods_attr":"","is_real":"1","extension_code":"","parent_id":"0","is_gift":"0","is_shipping":"0","subtotal":"19.50","formated_market_price":"¥5.90","formated_goods_price":"¥3.90","formated_subtotal":"¥19.50","img_thumb":"http://www.aiderizhi.com/images/201603/thumb_img/1_thumb_G_1458155685646.png"}]
     * consignee : {"address_id":"2470","consignee":"曲强","mobile":"15083806889","country":"1","province":"2","city":"52","district":"500","country_name":"中国","province_name":"北京","city_name":"北京","district_name":"东城区","address":"地址","zipcode":"","is_default":1}
     * shipping_list : [{"shipping_id":"1","shipping_code":"sf_express","shipping_name":"顺丰速运","insure":"0","support_cod":"1","format_shipping_fee":"¥25.00","shipping_fee":"25","free_money":"¥300.00","insure_formated":"¥0.00","is_default":0},{"shipping_id":"10","shipping_code":"yunda_express","shipping_name":"韵达快递","insure":"0","support_cod":"0","format_shipping_fee":"¥0.00","shipping_fee":0,"free_money":"¥39.00","insure_formated":"¥0.00","is_default":0}]
     * payment_list : [{"pay_id":"1","pay_code":"alipay","pay_cat":"0","pay_name":"支付宝","pay_fee":"0","is_cod":"0","format_pay_fee":"¥0.00"},{"pay_id":"37","pay_code":"upop","pay_cat":"0","pay_name":"银联在线支付","pay_fee":"0","is_cod":"0","format_pay_fee":"¥0.00"},{"pay_id":"35","pay_code":"wx_new_qrcode","pay_cat":"0","pay_name":"微信支付","pay_fee":"0","is_cod":"0","format_pay_fee":"¥0.00"}]
     * allow_use_bonus : 1
     * bonus : [{"type_id":"15","type_name":"好面膜齐分享2元红包","type_money":"2.00","bonus_id":"11876","bonus_money_formated":"¥2.00"}]
     * inv_content_list : null
     * inv_type_list : null
     * user_integral : 104
     * order_max_integral : 0
     * total : {"real_goods_count":2,"gift_amount":0,"goods_price":49,"market_price":74,"discount":0,"pack_fee":0,"card_fee":0,"shipping_fee":0,"shipping_insure":0,"integral_money":0,"bonus":0,"surplus":0,"cod_fee":0,"pay_fee":0,"tax":0,"saving":25,"save_rate":"34%","goods_price_formated":"¥49.00","market_price_formated":"¥74.00","saving_formated":"¥25.00","free_shipping_left":-10,"discount_formated":"¥0.00","tax_formated":"¥0.00","pack_fee_formated":"¥0.00","card_fee_formated":"¥0.00","bonus_formated":"¥0.00","shipping_fee_formated":"¥0.00","shipping_insure_formated":"¥0.00","amount":49,"surplus_formated":"¥0.00","integral":0,"integral_formated":"¥0.00","pay_fee_formated":"¥0.00","amount_formated":"¥49.00","will_get_integral":0,"will_get_bonus":"¥0.00","formated_goods_price":"¥49.00","formated_market_price":"¥74.00","formated_saving":"¥25.00"}
     */

    private DataEntity data;

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public static class StatusEntity {
        private int succeed;
        private int error_code;
        private String error_desc;

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public void setError_desc(String error_desc) {
            this.error_desc = error_desc;
        }

        public int getSucceed() {
            return succeed;
        }

        public int getError_code() {
            return error_code;
        }

        public String getError_desc() {
            return error_desc;
        }
    }

    public static class DataEntity {
        /**
         * address_id : 2470
         * consignee : 曲强
         * mobile : 15083806889
         * country : 1
         * province : 2
         * city : 52
         * district : 500
         * country_name : 中国
         * province_name : 北京
         * city_name : 北京
         * district_name : 东城区
         * address : 地址
         * zipcode :
         * is_default : 1
         */

        private ConsigneeEntity consignee;
        private int allow_use_bonus;
//        private Object inv_content_list;
//        private Object inv_type_list;
        private String user_integral;
        private int order_max_integral;
        /**
         * real_goods_count : 2
         * gift_amount : 0
         * goods_price : 49
         * market_price : 74
         * discount : 0
         * pack_fee : 0
         * card_fee : 0
         * shipping_fee : 0
         * shipping_insure : 0
         * integral_money : 0
         * bonus : 0
         * surplus : 0
         * cod_fee : 0
         * pay_fee : 0
         * tax : 0
         * saving : 25
         * save_rate : 34%
         * goods_price_formated : ¥49.00
         * market_price_formated : ¥74.00
         * saving_formated : ¥25.00
         * free_shipping_left : -10
         * discount_formated : ¥0.00
         * tax_formated : ¥0.00
         * pack_fee_formated : ¥0.00
         * card_fee_formated : ¥0.00
         * bonus_formated : ¥0.00
         * shipping_fee_formated : ¥0.00
         * shipping_insure_formated : ¥0.00
         * amount : 49
         * surplus_formated : ¥0.00
         * integral : 0
         * integral_formated : ¥0.00
         * pay_fee_formated : ¥0.00
         * amount_formated : ¥49.00
         * will_get_integral : 0
         * will_get_bonus : ¥0.00
         * formated_goods_price : ¥49.00
         * formated_market_price : ¥74.00
         * formated_saving : ¥25.00
         */

        private TotalEntity total;
        /**
         * rec_id : 9438
         * user_id : 2
         * goods_id : 2
         * goods_name : 爱的日志纪州备长炭净颜焕彩面膜
         * goods_sn : MC000002
         * goods_number : 5
         * market_price : 8.90
         * goods_price : 5.90
         * goods_attr :
         * is_real : 1
         * extension_code :
         * parent_id : 0
         * is_gift : 0
         * is_shipping : 0
         * subtotal : 29.50
         * formated_market_price : ¥8.90
         * formated_goods_price : ¥5.90
         * formated_subtotal : ¥29.50
         * img_thumb : http://www.aiderizhi.com/images/201603/thumb_img/2_thumb_G_1458155762687.png
         */

        private List<GoodsListEntity> goods_list;
        /**
         * shipping_id : 1
         * shipping_code : sf_express
         * shipping_name : 顺丰速运
         * insure : 0
         * support_cod : 1
         * format_shipping_fee : ¥25.00
         * shipping_fee : 25
         * free_money : ¥300.00
         * insure_formated : ¥0.00
         * is_default : 0
         */

        private List<ShippingListEntity> shipping_list;
        /**
         * pay_id : 1
         * pay_code : alipay
         * pay_cat : 0
         * pay_name : 支付宝
         * pay_fee : 0
         * is_cod : 0
         * format_pay_fee : ¥0.00
         */

        private List<PaymentListEntity> payment_list;
        /**
         * type_id : 15
         * type_name : 好面膜齐分享2元红包
         * type_money : 2.00
         * bonus_id : 11876
         * bonus_money_formated : ¥2.00
         */

        private List<BonusEntity> bonus;

        public void setConsignee(ConsigneeEntity consignee) {
            this.consignee = consignee;
        }

        public void setAllow_use_bonus(int allow_use_bonus) {
            this.allow_use_bonus = allow_use_bonus;
        }



        public void setUser_integral(String user_integral) {
            this.user_integral = user_integral;
        }

        public void setOrder_max_integral(int order_max_integral) {
            this.order_max_integral = order_max_integral;
        }

        public void setTotal(TotalEntity total) {
            this.total = total;
        }

        public void setGoods_list(List<GoodsListEntity> goods_list) {
            this.goods_list = goods_list;
        }

        public void setShipping_list(List<ShippingListEntity> shipping_list) {
            this.shipping_list = shipping_list;
        }

        public void setPayment_list(List<PaymentListEntity> payment_list) {
            this.payment_list = payment_list;
        }

        public void setBonus(List<BonusEntity> bonus) {
            this.bonus = bonus;
        }

        public ConsigneeEntity getConsignee() {
            return consignee;
        }

        public int getAllow_use_bonus() {
            return allow_use_bonus;
        }



        public String getUser_integral() {
            return user_integral;
        }

        public int getOrder_max_integral() {
            return order_max_integral;
        }

        public TotalEntity getTotal() {
            return total;
        }

        public List<GoodsListEntity> getGoods_list() {
            return goods_list;
        }

        public List<ShippingListEntity> getShipping_list() {
            return shipping_list;
        }

        public List<PaymentListEntity> getPayment_list() {
            return payment_list;
        }

        public List<BonusEntity> getBonus() {
            return bonus;
        }

        public static class ConsigneeEntity {
            private String address_id;
            private String consignee;
            private String mobile;
            private String country;
            private String province;
            private String city;
            private String district;
            private String country_name;
            private String province_name;
            private String city_name;
            private String district_name;
            private String address;
            private String zipcode;
            private int is_default;

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public void setCountry_name(String country_name) {
                this.country_name = country_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getAddress_id() {
                return address_id;
            }

            public String getConsignee() {
                return consignee;
            }

            public String getMobile() {
                return mobile;
            }

            public String getCountry() {
                return country;
            }

            public String getProvince() {
                return province;
            }

            public String getCity() {
                return city;
            }

            public String getDistrict() {
                return district;
            }

            public String getCountry_name() {
                return country_name;
            }

            public String getProvince_name() {
                return province_name;
            }

            public String getCity_name() {
                return city_name;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public String getAddress() {
                return address;
            }

            public String getZipcode() {
                return zipcode;
            }

            public int getIs_default() {
                return is_default;
            }
        }

        public static class TotalEntity {
            private int real_goods_count;
            private int gift_amount;
            private int goods_price;
            private int market_price;
            private int discount;
            private int pack_fee;
            private int card_fee;
            private int shipping_fee;
            private int shipping_insure;
            private int integral_money;
            private int bonus;
            private int surplus;
            private int cod_fee;
            private int pay_fee;
            private int tax;
            private int saving;
            private String save_rate;
            private String goods_price_formated;
            private String market_price_formated;
            private String saving_formated;
            private int free_shipping_left;
            private String discount_formated;
            private String tax_formated;
            private String pack_fee_formated;
            private String card_fee_formated;
            private String bonus_formated;
            private String shipping_fee_formated;
            private String shipping_insure_formated;
            private int amount;
            private String surplus_formated;
            private int integral;
            private String integral_formated;
            private String pay_fee_formated;
            private String amount_formated;
            private int will_get_integral;
            private String will_get_bonus;
            private String formated_goods_price;
            private String formated_market_price;
            private String formated_saving;

            public void setReal_goods_count(int real_goods_count) {
                this.real_goods_count = real_goods_count;
            }

            public void setGift_amount(int gift_amount) {
                this.gift_amount = gift_amount;
            }

            public void setGoods_price(int goods_price) {
                this.goods_price = goods_price;
            }

            public void setMarket_price(int market_price) {
                this.market_price = market_price;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public void setPack_fee(int pack_fee) {
                this.pack_fee = pack_fee;
            }

            public void setCard_fee(int card_fee) {
                this.card_fee = card_fee;
            }

            public void setShipping_fee(int shipping_fee) {
                this.shipping_fee = shipping_fee;
            }

            public void setShipping_insure(int shipping_insure) {
                this.shipping_insure = shipping_insure;
            }

            public void setIntegral_money(int integral_money) {
                this.integral_money = integral_money;
            }

            public void setBonus(int bonus) {
                this.bonus = bonus;
            }

            public void setSurplus(int surplus) {
                this.surplus = surplus;
            }

            public void setCod_fee(int cod_fee) {
                this.cod_fee = cod_fee;
            }

            public void setPay_fee(int pay_fee) {
                this.pay_fee = pay_fee;
            }

            public void setTax(int tax) {
                this.tax = tax;
            }

            public void setSaving(int saving) {
                this.saving = saving;
            }

            public void setSave_rate(String save_rate) {
                this.save_rate = save_rate;
            }

            public void setGoods_price_formated(String goods_price_formated) {
                this.goods_price_formated = goods_price_formated;
            }

            public void setMarket_price_formated(String market_price_formated) {
                this.market_price_formated = market_price_formated;
            }

            public void setSaving_formated(String saving_formated) {
                this.saving_formated = saving_formated;
            }

            public void setFree_shipping_left(int free_shipping_left) {
                this.free_shipping_left = free_shipping_left;
            }

            public void setDiscount_formated(String discount_formated) {
                this.discount_formated = discount_formated;
            }

            public void setTax_formated(String tax_formated) {
                this.tax_formated = tax_formated;
            }

            public void setPack_fee_formated(String pack_fee_formated) {
                this.pack_fee_formated = pack_fee_formated;
            }

            public void setCard_fee_formated(String card_fee_formated) {
                this.card_fee_formated = card_fee_formated;
            }

            public void setBonus_formated(String bonus_formated) {
                this.bonus_formated = bonus_formated;
            }

            public void setShipping_fee_formated(String shipping_fee_formated) {
                this.shipping_fee_formated = shipping_fee_formated;
            }

            public void setShipping_insure_formated(String shipping_insure_formated) {
                this.shipping_insure_formated = shipping_insure_formated;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public void setSurplus_formated(String surplus_formated) {
                this.surplus_formated = surplus_formated;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public void setIntegral_formated(String integral_formated) {
                this.integral_formated = integral_formated;
            }

            public void setPay_fee_formated(String pay_fee_formated) {
                this.pay_fee_formated = pay_fee_formated;
            }

            public void setAmount_formated(String amount_formated) {
                this.amount_formated = amount_formated;
            }

            public void setWill_get_integral(int will_get_integral) {
                this.will_get_integral = will_get_integral;
            }

            public void setWill_get_bonus(String will_get_bonus) {
                this.will_get_bonus = will_get_bonus;
            }

            public void setFormated_goods_price(String formated_goods_price) {
                this.formated_goods_price = formated_goods_price;
            }

            public void setFormated_market_price(String formated_market_price) {
                this.formated_market_price = formated_market_price;
            }

            public void setFormated_saving(String formated_saving) {
                this.formated_saving = formated_saving;
            }

            public int getReal_goods_count() {
                return real_goods_count;
            }

            public int getGift_amount() {
                return gift_amount;
            }

            public int getGoods_price() {
                return goods_price;
            }

            public int getMarket_price() {
                return market_price;
            }

            public int getDiscount() {
                return discount;
            }

            public int getPack_fee() {
                return pack_fee;
            }

            public int getCard_fee() {
                return card_fee;
            }

            public int getShipping_fee() {
                return shipping_fee;
            }

            public int getShipping_insure() {
                return shipping_insure;
            }

            public int getIntegral_money() {
                return integral_money;
            }

            public int getBonus() {
                return bonus;
            }

            public int getSurplus() {
                return surplus;
            }

            public int getCod_fee() {
                return cod_fee;
            }

            public int getPay_fee() {
                return pay_fee;
            }

            public int getTax() {
                return tax;
            }

            public int getSaving() {
                return saving;
            }

            public String getSave_rate() {
                return save_rate;
            }

            public String getGoods_price_formated() {
                return goods_price_formated;
            }

            public String getMarket_price_formated() {
                return market_price_formated;
            }

            public String getSaving_formated() {
                return saving_formated;
            }

            public int getFree_shipping_left() {
                return free_shipping_left;
            }

            public String getDiscount_formated() {
                return discount_formated;
            }

            public String getTax_formated() {
                return tax_formated;
            }

            public String getPack_fee_formated() {
                return pack_fee_formated;
            }

            public String getCard_fee_formated() {
                return card_fee_formated;
            }

            public String getBonus_formated() {
                return bonus_formated;
            }

            public String getShipping_fee_formated() {
                return shipping_fee_formated;
            }

            public String getShipping_insure_formated() {
                return shipping_insure_formated;
            }

            public int getAmount() {
                return amount;
            }

            public String getSurplus_formated() {
                return surplus_formated;
            }

            public int getIntegral() {
                return integral;
            }

            public String getIntegral_formated() {
                return integral_formated;
            }

            public String getPay_fee_formated() {
                return pay_fee_formated;
            }

            public String getAmount_formated() {
                return amount_formated;
            }

            public int getWill_get_integral() {
                return will_get_integral;
            }

            public String getWill_get_bonus() {
                return will_get_bonus;
            }

            public String getFormated_goods_price() {
                return formated_goods_price;
            }

            public String getFormated_market_price() {
                return formated_market_price;
            }

            public String getFormated_saving() {
                return formated_saving;
            }
        }

        public static class GoodsListEntity {
            private String rec_id;
            private String user_id;
            private String goods_id;
            private String goods_name;
            private String goods_sn;
            private String goods_number;
            private String market_price;
            private String goods_price;
            private String goods_attr;
            private String is_real;
            private String extension_code;
            private String parent_id;
            private String is_gift;
            private String is_shipping;
            private String subtotal;
            private String formated_market_price;
            private String formated_goods_price;
            private String formated_subtotal;
            private String img_thumb;

            public void setRec_id(String rec_id) {
                this.rec_id = rec_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public void setIs_real(String is_real) {
                this.is_real = is_real;
            }

            public void setExtension_code(String extension_code) {
                this.extension_code = extension_code;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public void setIs_gift(String is_gift) {
                this.is_gift = is_gift;
            }

            public void setIs_shipping(String is_shipping) {
                this.is_shipping = is_shipping;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public void setFormated_market_price(String formated_market_price) {
                this.formated_market_price = formated_market_price;
            }

            public void setFormated_goods_price(String formated_goods_price) {
                this.formated_goods_price = formated_goods_price;
            }

            public void setFormated_subtotal(String formated_subtotal) {
                this.formated_subtotal = formated_subtotal;
            }

            public void setImg_thumb(String img_thumb) {
                this.img_thumb = img_thumb;
            }

            public String getRec_id() {
                return rec_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public String getGoods_sn() {
                return goods_sn;
            }

            public String getGoods_number() {
                return goods_number;
            }

            public String getMarket_price() {
                return market_price;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public String getIs_real() {
                return is_real;
            }

            public String getExtension_code() {
                return extension_code;
            }

            public String getParent_id() {
                return parent_id;
            }

            public String getIs_gift() {
                return is_gift;
            }

            public String getIs_shipping() {
                return is_shipping;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public String getFormated_market_price() {
                return formated_market_price;
            }

            public String getFormated_goods_price() {
                return formated_goods_price;
            }

            public String getFormated_subtotal() {
                return formated_subtotal;
            }

            public String getImg_thumb() {
                return img_thumb;
            }
        }

        public static class ShippingListEntity {
            private String shipping_id;
            private String shipping_code;
            private String shipping_name;
            private String insure;
            private String support_cod;
            private String format_shipping_fee;
            private String shipping_fee;
            private String free_money;
            private String insure_formated;
            private int is_default;

            public void setShipping_id(String shipping_id) {
                this.shipping_id = shipping_id;
            }

            public void setShipping_code(String shipping_code) {
                this.shipping_code = shipping_code;
            }

            public void setShipping_name(String shipping_name) {
                this.shipping_name = shipping_name;
            }

            public void setInsure(String insure) {
                this.insure = insure;
            }

            public void setSupport_cod(String support_cod) {
                this.support_cod = support_cod;
            }

            public void setFormat_shipping_fee(String format_shipping_fee) {
                this.format_shipping_fee = format_shipping_fee;
            }

            public void setShipping_fee(String shipping_fee) {
                this.shipping_fee = shipping_fee;
            }

            public void setFree_money(String free_money) {
                this.free_money = free_money;
            }

            public void setInsure_formated(String insure_formated) {
                this.insure_formated = insure_formated;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getShipping_id() {
                return shipping_id;
            }

            public String getShipping_code() {
                return shipping_code;
            }

            public String getShipping_name() {
                return shipping_name;
            }

            public String getInsure() {
                return insure;
            }

            public String getSupport_cod() {
                return support_cod;
            }

            public String getFormat_shipping_fee() {
                return format_shipping_fee;
            }

            public String getShipping_fee() {
                return shipping_fee;
            }

            public String getFree_money() {
                return free_money;
            }

            public String getInsure_formated() {
                return insure_formated;
            }

            public int getIs_default() {
                return is_default;
            }
        }

        public static class PaymentListEntity {
            private String pay_id;
            private String pay_code;
            private String pay_cat;
            private String pay_name;
            private String pay_fee;
            private String is_cod;
            private String format_pay_fee;

            public void setPay_id(String pay_id) {
                this.pay_id = pay_id;
            }

            public void setPay_code(String pay_code) {
                this.pay_code = pay_code;
            }

            public void setPay_cat(String pay_cat) {
                this.pay_cat = pay_cat;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name;
            }

            public void setPay_fee(String pay_fee) {
                this.pay_fee = pay_fee;
            }

            public void setIs_cod(String is_cod) {
                this.is_cod = is_cod;
            }

            public void setFormat_pay_fee(String format_pay_fee) {
                this.format_pay_fee = format_pay_fee;
            }

            public String getPay_id() {
                return pay_id;
            }

            public String getPay_code() {
                return pay_code;
            }

            public String getPay_cat() {
                return pay_cat;
            }

            public String getPay_name() {
                return pay_name;
            }

            public String getPay_fee() {
                return pay_fee;
            }

            public String getIs_cod() {
                return is_cod;
            }

            public String getFormat_pay_fee() {
                return format_pay_fee;
            }
        }

        public static class BonusEntity {
            private String type_id;
            private String type_name;
            private String type_money;
            private String bonus_id;
            private String bonus_money_formated;

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public void setType_money(String type_money) {
                this.type_money = type_money;
            }

            public void setBonus_id(String bonus_id) {
                this.bonus_id = bonus_id;
            }

            public void setBonus_money_formated(String bonus_money_formated) {
                this.bonus_money_formated = bonus_money_formated;
            }

            public String getType_id() {
                return type_id;
            }

            public String getType_name() {
                return type_name;
            }

            public String getType_money() {
                return type_money;
            }

            public String getBonus_id() {
                return bonus_id;
            }

            public String getBonus_money_formated() {
                return bonus_money_formated;
            }
        }
    }
}
