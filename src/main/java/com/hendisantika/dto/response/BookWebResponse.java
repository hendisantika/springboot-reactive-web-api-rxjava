package com.hendisantika.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.12
 * To change this template use File | Settings | File Templates.
 */
@Data
@NoArgsConstructor
public class BookWebResponse {
    private String id;
    private String title;
    private String authorName;
}
