package cool.ange.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: ange
 * @package: cool.ange.feign
 * @className: JwtToken
 * @creationTime: 2023-03-21 11:56
 * @Version: v1.0
 * @description: jwtToken
 */
@Data
public class JwtToken {

    /**
     * access_token
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 过期时间
     */
    @JsonProperty("expires_in")
    private Long expiresIn;


    /**
     * token的范围
     */
    private String scope;

    /**
     * 颁发的凭证
     */
    private String jti;
}
