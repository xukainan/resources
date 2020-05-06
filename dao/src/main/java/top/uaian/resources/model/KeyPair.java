package top.uaian.resources.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * description:  键值对<br>
 * date: 2020/3/31 11:10 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Data
@AllArgsConstructor
public class KeyPair {

    private String key;

    private String value;
}
