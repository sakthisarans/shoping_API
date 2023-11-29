package com.example.useractivity;

import com.example.useractivity.cart.cart;
import com.example.useractivity.cart.cartitems;
import com.example.useractivity.dbconnect.Cartdb;
import com.example.useractivity.dbconnect.Orderdata;
import com.example.useractivity.error.exception;
import com.example.useractivity.error.internalerror;
import com.example.useractivity.merchantorder.merchantorderlist;
import com.example.useractivity.product.products;
import com.example.useractivity.service.accesstokenvalidator;
import com.example.useractivity.service.getsellerids;
import com.example.useractivity.service.merchentordercreatior;
import com.example.useractivity.shoppinghistory.product;
import com.example.useractivity.shoppinghistory.shopping;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    accesstokenvalidator valid;
    @Autowired
    Cartdb cartdb;
    @Autowired
    Orderdata od;
    @Autowired
    getsellerids gs;
    @Autowired
    merchentordercreatior morder;


    @GetMapping("/addtocart")
    public ResponseEntity<?> addtocart(@RequestParam(required = true) String productid,
                                       @RequestParam(required = true) String accesstoken,
                                       @RequestParam(required = false,defaultValue = "shopping")String cartname){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            if(productid.isEmpty()||productid.equals("")||productid.equals(null)){
                return new ResponseEntity<>("productid is mandidatory",HttpStatus.BAD_REQUEST);
            }
            else if((boolean)valid.tokenvalidator(accesstoken)[0]){
                cart temp=cartdb.findByid((String) valid.tokenvalidator(accesstoken)[1]);
                System.out.println("--------------------------------------------------------");
                System.out.println(temp);
                if(gs.checkstock(productid)) {
                    if (temp == null) {
                        cart usercart = new cart();
                        usercart.setId((String) valid.tokenvalidator(accesstoken)[1]);
                        cartitems tempitem = new cartitems();
                        List<cartitems> templist = new ArrayList<>();
                        tempitem.setCarname(cartname);
                        tempitem.setProdictid(productid);
                        tempitem.setCount(1);
                        templist.add(tempitem);
                        usercart.setCartitems(templist);
                        return new ResponseEntity<>(cartdb.save(usercart), HttpStatus.CREATED);
                    } else {
                        List<cartitems> tempitem = (temp.getCartitems());
                        System.out.println(tempitem);
                        AtomicBoolean sameitem = new AtomicBoolean(false);
                        tempitem.forEach(item -> {
                            if (item.getProdictid().equals(productid) && item.getCarname().equals(cartname)) {
                                item.setCount(item.getCount() + 1);
                                sameitem.set(true);
                            }
                        });
                        if (!sameitem.get()) {
                            cartitems tempcartitem = new cartitems();
                            tempcartitem.setCarname(cartname);
                            tempcartitem.setCount(1);
                            tempcartitem.setProdictid(productid);
                            tempitem.add(tempcartitem);
                        }
                        temp.setCartitems(tempitem);
                        return new ResponseEntity<>(cartdb.save(temp), HttpStatus.OK);
                    }
                }else{
                    return new ResponseEntity<>("out of stock", HttpStatus.NOT_FOUND);
                }
            }
        }else {
            return new ResponseEntity<>("invalid Access token", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
    @GetMapping("/clearcart")
    public ResponseEntity<?> clearcart(@RequestParam(required = true) String cartname,
                                @RequestParam(required = true) String accesstoken,
                                @RequestParam(required = true) boolean removeitem,
                                @RequestParam(required = false) String productid){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            cart temp=cartdb.findByid((String) valid.tokenvalidator(accesstoken)[1]);
            if(temp!=null) {
                if(removeitem){
                    List<cartitems> tempitem = temp.getCartitems();
                    System.out.println(tempitem.size());
                    for(int i=0;i<tempitem.size();i++){
                        cartitems item=tempitem.get(i);
                        if (item.getCarname().equals(cartname)&&item.getProdictid().equals(productid)) {
                            tempitem.remove(i);
                        }
                    }
                    temp.setCartitems(tempitem);
                    System.out.println(tempitem.size());
                    System.out.println(tempitem);

                    return new ResponseEntity<>( cartdb.save(temp),HttpStatus.OK);
                }
                else {
                    List<cartitems> tempitem = temp.getCartitems();
                    System.out.println(tempitem);
                    System.out.println(tempitem.size());
                    List<cartitems> out=new ArrayList<>();
                    tempitem.forEach(x->{
                        if(!(x.getCarname().equals(cartname))){
                            out.add(x);
                        }
                    });
                    temp.setCartitems(out);
                    System.out.println(out.size());
                    System.out.println(out);

                    return new ResponseEntity<>(cartdb.save(temp),HttpStatus.OK);
                }
            }
            else{
                return new ResponseEntity<>("cart not found",HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("invalid access token",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getcart")
    public ResponseEntity<?> getcart(@RequestParam(required = true)String accesstoken,@RequestParam(required = false,defaultValue = "")String cartname){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.OK);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            cart temp=cartdb.findByid((String) valid.tokenvalidator(accesstoken)[1]);
            System.out.println(temp);
            if(temp==null){
                return new ResponseEntity<>("items not found",HttpStatus.NOT_FOUND);
            }
            else if(!(cartname.isEmpty()||cartname.isBlank())){
                List<cartitems> filteredList=temp.getCartitems().stream().filter(cart->cart.getCarname().equals(cartname)).toList();

                if(!filteredList.isEmpty()) {
                    cart out=new cart();
                    out.setCartitems(filteredList);
                    return new ResponseEntity<>(out, HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("cart not found", HttpStatus.NOT_FOUND);
                }
            }else {
                return new ResponseEntity<>(temp, HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>("invalid access token", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/checkout")
    public ResponseEntity<?> checkoutcart(@RequestParam(required = true)String accesstoken,@RequestParam(required = true)String cartname) throws JsonProcessingException {
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.OK);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            String token=(String) valid.tokenvalidator(accesstoken)[1];
            cart temp=cartdb.findByid(token);
            if(temp==null){
                return new ResponseEntity<>("items not found",HttpStatus.NOT_FOUND);
            }else{
                List<cartitems> filteredList=temp.getCartitems().stream().filter(cart->cart.getCarname().equals(cartname)).toList();
                System.out.println(filteredList.toString());
                if(!filteredList.isEmpty()) {
                    List<String> productid=new ArrayList<>();
                    filteredList.forEach(x->{
                        productid.add(x.getProdictid());
                    });
                    System.out.println("-----------------------");
                    List<products> np=gs.getseller(productid,accesstoken);
                    System.out.println(np);
                    shopping shopping=new shopping();
                    List<product> prod=new ArrayList<>();
                    np.forEach(x->{
                        filteredList.forEach(y->{
                            if(x.getProductid().equals(y.getProdictid())) {
                                product p = new product();
                                p.setProductid(x.getProductid());
                                p.setSellerid(x.getSellerid());
                                p.setCount(y.getCount());
                                prod.add(p);
                            }
                        });
                    });
                    shopping.setProduct(prod);

                    shopping.setUserid(token);
                    shopping.setOrderid(gs.getorderid());
                    shopping.setPaymentinitiative(true);
                    List<Object> tempobj=new ArrayList<>();
                    tempobj.add("Order placed successfully");
                    shopping.setUpdate(tempobj);
                    if(gs.reducecount(accesstoken,prod).equals(HttpStatus.OK)) {
                        if(clearcart(cartname, accesstoken, false, "").getStatusCode().equals(HttpStatus.OK)) {
                            System.out.println(prod);
                            morder.createorder(prod,shopping.getOrderid());
                            return new ResponseEntity<>(od.save(shopping), HttpStatus.OK);
                        }
                        else
                            return new ResponseEntity<>("something went wrong in clear cart", HttpStatus.BAD_REQUEST);
                    }else{
                        return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>("cart not found", HttpStatus.NOT_FOUND);
                }
            }
        }
        else{
            return new ResponseEntity<>("invalid access token", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cancelorder/{orderid}")
    public ResponseEntity<?> cancelorder(@RequestParam(defaultValue = "") String accesstoken,@PathVariable String orderid){
        System.out.println("-------------------------------------------------");
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            shopping sh=od.findByOrderid(orderid);
            if(sh==null){
                return new ResponseEntity<>("order not found", HttpStatus.NOT_FOUND);
            }else {
                sh.setCancel(true);
                od.save(sh);
                return new ResponseEntity<>("order cancelled", HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("invalid access token", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getorders")
    public ResponseEntity<?> getorders(@RequestParam(defaultValue = "") String accesstoken){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.OK);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            List<shopping> sh=od.findByUserid((String) valid.tokenvalidator(accesstoken)[1]);
            if(sh==null){ return new ResponseEntity<>("", HttpStatus.NOT_FOUND);}
            else {
                return new ResponseEntity<>(sh, HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("invalid access token", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getorder/{orderid}")
    public ResponseEntity<?> getorder(@RequestParam(defaultValue = "") String accesstoken,@PathVariable String orderid){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access token is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]){
            if(od.findByOrderid(orderid)==null){
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }else {
                List<shopping> sh = new ArrayList<>();
                sh.add(od.findByOrderid(orderid));
                return new ResponseEntity<>(sh, HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("invalid access token", HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler
    public ResponseEntity<?> error(internalerror ex){
        exception exc=new exception(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        return new ResponseEntity<>(exc, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//        @PostMapping(value = "/test")
//    public ResponseEntity<?> checkout(@RequestParam("image") MultipartFile image) throws IOException {
//        try {
//            System.out.println(Arrays.toString(image.getBytes()));
//        }catch (Exception e){System.out.println(e+"===================================================================");}
//        image.transferTo(new File("C:\\Users\\sakth\\OneDrive\\Documents\\new\\New folder\\useractivity\\logs\\" + image.getOriginalFilename()));
//        return new ResponseEntity<>("ok", HttpStatus.OK);
//    }
}
