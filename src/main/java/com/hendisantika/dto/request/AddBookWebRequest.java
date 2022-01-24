package com.hendisantika.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-reactive-web-api-rxjava
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 24/01/22
 * Time: 12.10
 * To change this template use File | Settings | File Templates.
 */
@Data
@NoArgsConstructor
public class AddBookWebRequest {
    private String title;
    private String authorId;
}
