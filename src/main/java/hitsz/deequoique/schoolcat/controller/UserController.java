package hitsz.deequoique.schoolcat.controller;

import hitsz.deequoique.schoolcat.common.Constants;
import hitsz.deequoique.schoolcat.common.Result;
import hitsz.deequoique.schoolcat.controller.dto.UserDTO;
import hitsz.deequoique.schoolcat.entity.User;
import hitsz.deequoique.schoolcat.mapper.UserMapper;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author deequoique
 * user相关控制层
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public Result save(@RequestBody User user){
       return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO user){
        for (User users:index()){
            if(users.getId().equals(user.getUserId())){
                return Result.error(Constants.CODE_400,"已有该账号");
            }
        }
        if(!Objects.equals(user.getPassword(), user.getConfig())){
            return Result.error(Constants.CODE_403,"先后密码不一致");
        }
        userMapper.insert(new User(user.getUserId(), user.getPassword(), false));
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        for (User user:index()){
            if (user.getId().equals(userDTO.getUserId())){
                if(Objects.equals(user.getPassword(), userDTO.getPassword())){
                    return Result.success();
                }
                else {
                    return Result.error(Constants.CODE_403,"密码错误");
                }
            }
        }
        return Result.error(Constants.CODE_500,"没有该用户");
    }

    @GetMapping("/")
    public List<User> index(){
        return userMapper.findAll();
    }
}