// product
import http from "k6/http"
import { check, group, sleep } from "k6"

export let options = {
    stages: [
        // { duration: "3s", target: 20 },
        // { duration: "5s", target: 200 },
        // { duration: "10s", target: 100 },
        { duration: "30s", target: 200 }
        // { duration: "20s", target: 300 },
        // { duration: "40s", target: 230 }
    ]
};

export default (data) => {

    group("get product", () => {
        let res = http.get(`http://localhost:8080/product/test`)
        check(res, {
            "status was 200": (r) => res.status == 200
        });
    })
}
