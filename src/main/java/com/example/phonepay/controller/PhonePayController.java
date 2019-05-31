package com.example.phonepay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.example.phonepay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 9:47 2019/5/31
 * @ Description：手机网站支付
 * @ Modified By：
 * @Version: $
 */

@RequestMapping("/phone")
@Controller
public class PhonePayController {

    @Autowired
    private AlipayConfig properties;


    /**
     * 跳转到支付页面
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("goPay")
    public void goPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(request.getParameter("WIDout_trade_no")!=null){
            // 商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            // 订单名称，必填
            String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
            System.out.println(subject);
            // 付款金额，必填
            String total_amount=new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
            // 商品描述，可空
            String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
            // 超时时间 可空
            String timeout_express="2m";
            // 销售产品码 必填
            String product_code="QUICK_WAP_WAY";
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //调用RSA签名方式
            //获得初始化的AlipayClient
            AlipayClient client = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getApp_id(), properties.getMerchant_private_key(), "json", properties.getCharset(), properties.getAlipay_public_key(), properties.getSign_type());


            AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
            model.setOutTradeNo(out_trade_no);
            model.setSubject(subject);
            model.setTotalAmount(total_amount);
            model.setBody(body);
            model.setTimeoutExpress(timeout_express);
            model.setProductCode(product_code);
            alipay_request.setBizModel(model);
            // 设置异步通知地址
            alipay_request.setNotifyUrl(properties.getNotify_url());
            // 设置同步地址
            alipay_request.setReturnUrl(properties.getReturn_url());

            // form表单生产
            String form = "";
            try {
                // 调用SDK生成表单
                form = client.pageExecute(alipay_request).getBody();
                response.setContentType("text/html;charset=" + properties.getCharset());
                response.getWriter().write(form);//直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();
            } catch (AlipayApiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 交易查询
     * @param request
     * @param trade_no
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("query")
    public String query(HttpServletRequest request,String trade_no,String out_trade_no) throws Exception{

            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no1 = new String(out_trade_no.getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no1 = new String(trade_no.getBytes("ISO-8859-1"),"UTF-8");
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //获得初始化的AlipayClient
            AlipayClient client = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getApp_id(), properties.getMerchant_private_key(), "json", properties.getCharset(), properties.getAlipay_public_key(), properties.getSign_type());

            AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();

            AlipayTradeQueryModel model=new AlipayTradeQueryModel();
            model.setOutTradeNo(out_trade_no1);
            model.setTradeNo(trade_no1);
            alipay_request.setBizModel(model);

            AlipayTradeQueryResponse alipay_response =client.execute(alipay_request);
            System.out.println(alipay_response.getBody());

        return alipay_response.getBody();
    }


    /**
     * 退款
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("return")
    public String tui(HttpServletRequest request,
                      String out_trade_no,String trade_no
            ,String refund_amount, String refund_reason,
                      String out_request_no) throws Exception{

            //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
            //商户订单号，和支付宝交易号二选一
            String out_trade_no1 = new String(out_trade_no.getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号，和商户订单号二选一
            String trade_no1 = new String(trade_no.getBytes("ISO-8859-1"),"UTF-8");
            //退款金额，不能大于订单总金额
            String refund_amount1=new String(refund_amount.getBytes("ISO-8859-1"),"UTF-8");
            //退款的原因说明
            String refund_reason1=new String(refund_reason.getBytes("ISO-8859-1"),"UTF-8");
            //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
            String  out_request_no1=new String(out_request_no.getBytes("ISO-8859-1"),"UTF-8");
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //获得初始化的AlipayClient
            AlipayClient client = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getApp_id(), properties.getMerchant_private_key(), "json", properties.getCharset(), properties.getAlipay_public_key(), properties.getSign_type());

            AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();

            AlipayTradeRefundModel model=new AlipayTradeRefundModel();
            model.setOutTradeNo(out_trade_no1);
            model.setTradeNo(trade_no1);
            model.setRefundAmount(refund_amount1);
            model.setRefundReason(refund_reason1);
            model.setOutRequestNo(out_request_no1);
            alipay_request.setBizModel(model);

            AlipayTradeRefundResponse alipay_response =client.execute(alipay_request);
            System.out.println(alipay_response.getBody());

            return alipay_response.getBody();
    }




    /**
     * 退款交易查询
     * @param request
     * @param trade_no
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("Tkquery")
    public String Tkquery(HttpServletRequest request,String trade_no,
                          String out_trade_no,
                          String out_request_no) throws Exception{

            //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
            //商户订单号，和支付宝交易号二选一
            String out_trade_no1 = new String(out_trade_no.getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号，和商户订单号二选一
            String trade_no1 = new String(trade_no.getBytes("ISO-8859-1"),"UTF-8");
            //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
            String out_request_no1 = new String(out_request_no.getBytes("ISO-8859-1"),"UTF-8");
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //获得初始化的AlipayClient
            AlipayClient client = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getApp_id(), properties.getMerchant_private_key(), "json", properties.getCharset(), properties.getAlipay_public_key(), properties.getSign_type());


            AlipayTradeFastpayRefundQueryRequest alipay_request = new AlipayTradeFastpayRefundQueryRequest();

            AlipayTradeFastpayRefundQueryModel model=new AlipayTradeFastpayRefundQueryModel();
            model.setOutTradeNo(out_trade_no1);
            model.setTradeNo(trade_no1);
            model.setOutRequestNo(out_request_no1);
            alipay_request.setBizModel(model);

            AlipayTradeFastpayRefundQueryResponse alipay_response=client.execute(alipay_request);
            System.out.println(alipay_response.getBody());

            return alipay_response.getBody();

    }


    /**
     * 关闭交易
     * @param request
     * @param trade_no
     * @param out_trade_no
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("close")
    public String close(HttpServletRequest request,String trade_no,
                        String out_trade_no) throws Exception{

            //商户订单号和支付宝交易号不能同时为空。 trade_no、  out_trade_no如果同时存在优先取trade_no
            //商户订单号，和支付宝交易号二选一
            String out_trade_no1 = new String(out_trade_no.getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号，和商户订单号二选一
            String trade_no1 = new String(trade_no.getBytes("ISO-8859-1"),"UTF-8");
            /**********************/
            // SDK 公共请求类，包
            // 含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //获得初始化的AlipayClient
            AlipayClient client = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getApp_id(), properties.getMerchant_private_key(), "json", properties.getCharset(), properties.getAlipay_public_key(), properties.getSign_type());

            AlipayTradeCloseRequest alipay_request=new AlipayTradeCloseRequest();

            AlipayTradeCloseModel model =new AlipayTradeCloseModel();
            model.setOutTradeNo(out_trade_no1);
            model.setTradeNo(trade_no1);
            alipay_request.setBizModel(model);

            AlipayTradeCloseResponse alipay_response=client.execute(alipay_request);
            System.out.println(alipay_response.getBody());

        return alipay_response.getBody();
    }



    @ResponseBody
    @RequestMapping("url")
    public String url(HttpServletRequest request,String bill_type,String bill_date) throws Exception{

            // 账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型：trade、signcustomer；
            // trade指商户基于支付宝交易收单的业务账单；signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单；
            String bill_type1 = new String(bill_type.getBytes("ISO-8859-1"),"UTF-8");
            // 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
            String bill_date1 = new String(bill_date.getBytes("ISO-8859-1"),"UTF-8");
            /**********************/
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            //获得初始化的AlipayClient
            AlipayClient client = new DefaultAlipayClient(properties.getGatewayUrl(), properties.getApp_id(), properties.getMerchant_private_key(), "json", properties.getCharset(), properties.getAlipay_public_key(), properties.getSign_type());
            AlipayDataDataserviceBillDownloadurlQueryRequest alipay_request = new AlipayDataDataserviceBillDownloadurlQueryRequest();

            AlipayDataDataserviceBillDownloadurlQueryModel model =new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType(bill_type1);
            model.setBillDate(bill_date1);
            alipay_request.setBizModel(model);

            AlipayDataDataserviceBillDownloadurlQueryResponse alipay_response = client.execute(alipay_request);
            System.out.println(alipay_response.getBillDownloadUrl());

        return alipay_response.getBillDownloadUrl();
    }




}
