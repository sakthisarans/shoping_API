import http from "k6/http"
import { check, group, sleep } from "k6"
export let options = {
    stages: [
        { duration: "30s", target: 20 },
        { duration: "5s", target: 200 },
        { duration: "1m", target: 100 },
        { duration: "30s", target: 200 }
    ]
};

export default (data) => {
    group("Sign in", () => {

        var payload = "{\"siteId\":\"5ebb27df-a2e6-40d8-95a2-6da486737327\",\"requestType\":\"SignIn\",\"email\":\"sakthisaransss58@gmail.com\",\"password\":\"qwertyuiop#S5\",\"deviceFingerPrint\":{\"deviceFingerPrintId\":\"95ae54c843da5c42e72c2d525ab6411bMac/iOSChrome108.0.0.0\",\"browserInfo\":{\"browserName\":\"Chrome\",\"fullVersion\":\"117.0.0.2\",\"majorVersion\":\"117\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36\",\"url\":\"https://app.mondee.com/admin/enroll/login\",\"ipAddress\":\"152.58.232.127\",\"os\":\"Mac/iOS\",\"deviceName\":\"Desktop_MAC\",\"deviceType\":\"Web\"},\"ipAddress\":\"152.58.232.127\",\"fingerPrintId\":\"db6ec3639bdf6d72e2ff51f0bf5e8dba\"},\"domain\":\"app.mondee.com\"}"
        const headers = { 'Content-Type': 'application/json' };
        let res = http.post(`${__ENV.hostname}/endpoint/`, payload, { headers })
        check(res, {
            "Post status is 200": (r) => res.status == 200,
            "post check token": (r) => res.json().hasOwnProperty("accesstoken")
        })
    })
    group("get product", () => {
        let res = http.get(`${__ENV.hostname}/product/getProduct?accesstoken=${__ENV.accesstoken}&productid=1112`)
        check(res, {
            "status was 200": (r) => res.status == 200
        });
    })
    group("get cart", () => {
        let res = http.get(`${__ENV.hostname}/user/getcart?accesstoken=${__ENV.accesstoken}`)
        check(res, {
            "status was 200": (r) => res.status == 200,
            "check body": (r) => res.json().hasOwnProperty("cartitems")
        })
    })
    group("get all product", () => {
        let res = http.get(`${__ENV.hostname}/product/getProducts?accesstoken=${__ENV.accesstoken}`)
        check(res, {
            "status was 200": (r) => res.status == 200,
            "check body": (r) => res.json().length > 0
        })
    })
    group("stock check", () => {
        let res = http.get(`${__ENV.hostname}/product/stockcount/1356991003871010?accesstoken=password`)
        check(res, {
            "status was 200": (r) => res.status == 200
        })
    })
    group("get order", () => {
        let res = http.get(`${__ENV.hostname}/user/getorders?accesstoken=${__ENV.accesstoken}`)
        check(res, {
            "status was 200": (r) => res.status == 200
        })
    })
    sleep(0.5)
}