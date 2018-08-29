package com.example.jwtdemo.domain;

import com.example.jwtdemo.JwtDemoApplicationTests;
import com.example.jwtdemo.domain.util.RSAUtils;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author liuyiqian
 */
@RunWith(SpringRunner.class)
// 测试API的测试环境采用RANDOM_PORT
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JwtDemoApplicationTests.class)
public class rsaTest {

    @Test
    public void key() {
        try {
            // 生成密钥
            Map<String, Object> key = RSAUtils.initKey();
            String privateKey = RSAUtils.getPrivateKey(key);
            String publicKey = RSAUtils.getPublicKey(key);

            String data = "123";

            // 公钥加密
            String encryptedData = Base64.encodeBase64String(RSAUtils.encryptByPublicKey(data.getBytes(), publicKey));

            // 私钥解密
            String decryptedData = new String(RSAUtils.decryptByPrivateKey(Base64.decodeBase64(encryptedData), privateKey));

            Assert.assertEquals(data, decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
