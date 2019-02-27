package com.perfree.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jfinal.kit.PropKit;

public class JwtUtils {

	/**
	 * 生成jwt
	 * @return
	 */
	public static String createJwt(Map<String, String> claims,Date expireDatePoint){
		 try {
			//使用HMAC256进行加密
            Algorithm algorithm = Algorithm.HMAC256(PropKit.get("jwt.secretkey"));

            //创建jwt
            JWTCreator.Builder builder = JWT.create().withIssuer(PropKit.get("jwt.issuer")).withExpiresAt(expireDatePoint);
            //传入参数
            claims.forEach((key,value)-> {
                builder.withClaim(key, value);
            });
            //签名加密
            return builder.sign(algorithm);
		 } catch (IllegalArgumentException e) {
			 return "";
		 } catch (UnsupportedEncodingException e) {
			 return "";
		 }
	}
	
	/**
	 * 验证jwt
	 * @return
	 */
	public static Map<String, String> verifyJwt(String token,String userName) {
		Algorithm algorithm = null;
		Map<String, String> resultMap = new HashMap<>();
        try {
            //使用HMAC256进行加密
            algorithm = Algorithm.HMAC256(PropKit.get("jwt.secretkey"));
	        //解密
	        JWTVerifier verifier = JWT.require(algorithm).withIssuer(PropKit.get("jwt.issuer")).withClaim("name", userName).build();
	        DecodedJWT jwt =  verifier.verify(token);
	        Map<String, Claim> map = jwt.getClaims();
	        map.forEach((k,v) -> resultMap.put(k, v.asString()));
        } catch (Exception e) {
            return null;
        }
        return resultMap;
	}
	
	/**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("name").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
