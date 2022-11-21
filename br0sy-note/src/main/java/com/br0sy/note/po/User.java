package com.br0sy.note.po;


import lombok.Getter;
import lombok.Setter;



// po层仅仅是表示数据的对象
// lombok 构造器，免去了我们写成员变量的get和set，方便很多，减少了代码量（不然调用类的时候，我们需要写每个成员变量的getset会很繁杂）
@Getter
@Setter
public class User {

    private Integer userId; // 用户ID
    private String uname; // 用户名称
    private String upwd; // 用户密码
    private String nick; // 用户昵称
    private String head; // 用户头像
    private String mood; // 用户签名


}
