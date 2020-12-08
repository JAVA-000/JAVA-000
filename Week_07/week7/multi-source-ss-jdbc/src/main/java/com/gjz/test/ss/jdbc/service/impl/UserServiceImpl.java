package com.gjz.test.ss.jdbc.service.impl;

import com.gjz.test.ss.jdbc.entity.User;
import com.gjz.test.ss.jdbc.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <pre>
 * jdbcTemplate
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/6 下午11:41
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource(name = "masterJdbcTemplate")
    JdbcTemplate masterJdbcTemplate;

    @Resource(name = "salveJdbcTemplate")
    JdbcTemplate salveJdbcTemplate;

    @Resource
    JdbcTemplate jdbcTemplate;


    /**
     * 添加
     *
     * @param user
     */
    @Override
    public void create(User user) {

        String sql = "insert into user(name, age) values (?, ?)";

        masterJdbcTemplate.update((connection)->{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            return preparedStatement;
        });
    }

    /**
     * 删除
     *
     * @param userId
     */
    @Override
    public void delete(Long userId) {
        String sql = "delete from user where id = ?";

        masterJdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            return preparedStatement;
        });
    }

    /**
     * 更新
     *
     * @param user
     */
    @Override
    public void update(User user) {
        String sql = "update user set name = ?, age = ? where id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setLong(3, user.getId());
            return preparedStatement;
        });
    }

    /**
     * 查找
     *
     * @param userId
     * @return
     */
    @Override
    public User get(Long userId) {
        String sql = "select * from user where id = ?";

        User user = new User();
        salveJdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            return preparedStatement;
        }, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setAge(rs.getInt(3));
            }
        });
        return user;
    }

    /**
     * 查询
     *
     * @return
     */
    @Override
    public List<User> list() {

        return jdbcTemplate.query("select * from user", new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setAge(rs.getInt(3));
                return user;
            }
        });
    }
}

