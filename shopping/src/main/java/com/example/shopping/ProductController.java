package com.example.shopping;

import com.example.shopping.products.multiproduct;
import com.example.shopping.products.products;
import com.example.shopping.reducecount.reducecount;
import com.example.shopping.service.accesstokenvalidator;
import jakarta.validation.Valid;
import com.example.shopping.productdb.productdb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    Logger logger
            = LoggerFactory.getLogger(ProductController.class);
    @Autowired(required = false)
    protected productdb productdb;
    @Autowired(required = false)
    protected accesstokenvalidator valid;
    @PostMapping("/updateProduct/{productid}")
    public ResponseEntity<?> updateproduct(@Valid @RequestBody products product,@RequestParam(defaultValue = "") String accesstoken,@PathVariable String productid){
        System.out.println(productid);
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.OK);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            if(((List<String>)valid.tokenvalidator(accesstoken)[2]).contains("merchant")) {
                if (productid != null) {
                    products obj = productdb.findByproductid(productid);
                    System.out.println(obj);
                    if (obj != null) {
                        product.setId(obj.getId());
                        product.setProductid(productid);
                        product.setCreator((String) valid.tokenvalidator(accesstoken)[1]);
                        product.setSellerid(obj.getSellerid());
                        System.out.println(product);
                        return new ResponseEntity<>(productdb.save(product), HttpStatus.OK);
                    } else
                        return new ResponseEntity<>("product not found", HttpStatus.OK);
                } else {
                        return new ResponseEntity<>("productid is madidatory", HttpStatus.OK);
                }
            }else {
                return new ResponseEntity<>("access denied", HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>("invalid AccessToken", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/addProduct")
    public ResponseEntity<?> addproduct(@Valid @RequestBody products product,@RequestParam(defaultValue = "") String accesstoken){
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            if (((List<String>) valid.tokenvalidator(accesstoken)[2]).contains("merchant")) {
                String id = (String) valid.tokenvalidator(accesstoken)[1];
                if (productdb.findByproductid(product.getProductid()) == null) {
                    product.setCreator(id);
                    product.setSellerid((String) valid.tokenvalidator(accesstoken)[1]);
                    return new ResponseEntity<>(productdb.save(product), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("product already exists", HttpStatus.FOUND);
                }
            }
            else {
                return new ResponseEntity<>("access denied", HttpStatus.FORBIDDEN);
            }
        }
        else{
            return new ResponseEntity<>("invalid AccessToken",HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/addProducts")
    public ResponseEntity<?> addproducts(@Valid @RequestBody multiproduct productsdata,@RequestParam(defaultValue = "") String accesstoken) {
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            if (((List<String>) valid.tokenvalidator(accesstoken)[2]).contains("merchant")) {
                String id = (String) valid.tokenvalidator(accesstoken)[1];
                final List<String> error = new ArrayList<>();
                (productsdata.getProducts()).forEach(list -> {
                    if (productdb.findByproductid(list.getProductid()) != null) {
                        error.add("product id : " + list.getProductid() + " already exists");
                    }
                });
                if (!error.isEmpty()) {
                    return new ResponseEntity<>(error, HttpStatus.FOUND);
                } else {
                    for (int i = 0; i < productsdata.getProducts().size(); i++) {
                        products temp = productsdata.getProducts().get(i);
                        temp.setCreator(id);
                        temp.setSellerid(id);
                        productdb.save(temp);
                    }
                    return new ResponseEntity<>("Products added successfull", HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>("access denied", HttpStatus.FORBIDDEN);
            }
        }else{
            System.out.println((boolean)valid.tokenvalidator(accesstoken)[0]);
            return new ResponseEntity<>("invalid AccessToken",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getProduct")
    public ResponseEntity<?> getProduct(@RequestParam(defaultValue = "")String productid,@RequestParam(defaultValue = "") String accesstoken){
       System.out.println(productid);
        if(accesstoken.isEmpty()){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            if(!productid.isEmpty()){
                Object obj=(Object) productdb.findByproductid(productid);
                if(obj!=null)
                    return new ResponseEntity<>(obj,HttpStatus.OK);
                else
                    return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>("productid is madidatory", HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>("invalid AccessToken", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getProducts")
    public ResponseEntity<?> getProducts(@RequestParam(required = false,defaultValue = "") String accesstoken){
        System.out.println(valid.tokenvalidator(accesstoken).toString());
        logger.info("-----------"+accesstoken);
        if(accesstoken.isEmpty()||accesstoken==null||accesstoken.equals("")){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            return new ResponseEntity<>(productdb.findAll(), HttpStatus.OK);
        }else {
            System.out.println((boolean)valid.tokenvalidator(accesstoken)[0]);
            return new ResponseEntity<>("invalid AccessToken", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/getProductlist")
    public ResponseEntity<?> getProductslist(@RequestParam(required = false,defaultValue = "") String accesstoken,@RequestBody List<String> ids){
        System.out.println("----------------------------"+accesstoken);
        if(accesstoken.isEmpty()||accesstoken==null||accesstoken.equals("")){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            System.out.println(ids);
//            System.out.println(productdb.findByproductid(ids));
            return new ResponseEntity<>(productdb.findByproductid(ids), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("invalid AccessToken", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/reducecount")
    public ResponseEntity<?> reduceount(@RequestParam(required = false,defaultValue = "") String accesstoken, @RequestBody List<reducecount> rc){
        if(accesstoken.isEmpty()||accesstoken==null||accesstoken.equals("")){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if((boolean)valid.tokenvalidator(accesstoken)[0]) {
            List<String> temp=new ArrayList<>();
            rc.forEach(x->{
                temp.add(x.getProductid());
            });
            List<products> p=productdb.findByproductid(temp);
            List<products> updated=new ArrayList<>();
            p.forEach(x->{
                rc.forEach(y->{
                    if(x.getProductid().equals(y.getProductid())){
                        x.setStockcount(x.getStockcount()-y.getCount());
                        updated.add(x);
                    }
                });
            });
            productdb.saveAll(updated);
            return new ResponseEntity<>("", HttpStatus.OK);

        }else {
            return new ResponseEntity<>("invalid AccessToken", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/stockcount/{productid}")
    public ResponseEntity<?> updateproduct(@RequestParam(defaultValue = "") String accesstoken,@PathVariable String productid){
        if(accesstoken.isEmpty()||accesstoken==null||accesstoken.equals("")){
            return new ResponseEntity<>("access tokendb is mandidatory",HttpStatus.BAD_REQUEST);
        }
        else if(accesstoken.equals("password")) {
            System.out.println("---------------------------------------");
            System.out.println(productdb.findByproductid(productid));
            if(productdb.findByproductid(productid).getStockcount()>0){
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>("invalid AccessToken", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/test")
    public ResponseEntity<?> reduceount(){
        System.out.println("new");
        return ResponseEntity.ok("");
    }


        @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
