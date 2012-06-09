package com.dao;

import com.model.User;
import com.model.UserExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public UserDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int countByExample(UserExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("user.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int deleteByExample(UserExample example) {
        int rows = getSqlMapClientTemplate().delete("user.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int deleteByPrimaryKey(Long recId) {
        User key = new User();
        key.setRecId(recId);
        int rows = getSqlMapClientTemplate().delete("user.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public void insert(User record) {
        getSqlMapClientTemplate().insert("user.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public void insertSelective(User record) {
        getSqlMapClientTemplate().insert("user.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public List selectByExample(UserExample example) {
        List list = getSqlMapClientTemplate().queryForList("user.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public User selectByPrimaryKey(Long recId) {
        User key = new User();
        key.setRecId(recId);
        User record = (User) getSqlMapClientTemplate().queryForObject("user.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int updateByExampleSelective(User record, UserExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("user.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int updateByExample(User record, UserExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("user.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int updateByPrimaryKeySelective(User record) {
        int rows = getSqlMapClientTemplate().update("user.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    public int updateByPrimaryKey(User record) {
        int rows = getSqlMapClientTemplate().update("user.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table user
     *
     * @ibatorgenerated Sat Jun 09 19:34:32 CST 2012
     */
    private static class UpdateByExampleParms extends UserExample {
        private Object record;

        public UpdateByExampleParms(Object record, UserExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}