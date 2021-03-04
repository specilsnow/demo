package com.cdutcm.tcms.sys.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.cdutcm.core.util.*;
import com.cdutcm.tcms.biz.JoinUpCloudPlat.*;
import com.cdutcm.tcms.biz.model.*;
import com.cdutcm.tcms.biz.service.BasedataService;
import com.cdutcm.tcms.biz.service.ConfAnswerService;
import com.cdutcm.tcms.biz.service.UserClinicService;
import com.cdutcm.tcms.biz.service.UserInfoService;
import com.cdutcm.tcms.sys.entity.Clinic;
import com.cdutcm.tcms.sys.mapper.ClinicMapper;
import com.cdutcm.tcms.sys.mapper.SysUserInfoMapper;
import com.cdutcm.tcms.sys.service.ClinicService;
import net.sf.json.JSONObject;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdutcm.core.message.SysMsg;
import com.cdutcm.tcms.biz.mapper.BaseDataMapper;
import com.cdutcm.tcms.biz.mapper.BaseRecipelMapper;
import com.cdutcm.tcms.log.SysServiceLog;
import com.cdutcm.tcms.sys.entity.User;
import com.cdutcm.tcms.sys.entity.UserInfo;
import com.cdutcm.tcms.sys.mapper.UserMapper;
import com.cdutcm.tcms.sys.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BaseDataMapper baseDataMapper;
    @Autowired
    private BaseRecipelMapper baseRecipelMapper;
    @Autowired
    private ClinicMapper clinicMapper;
    @Autowired
    private ConfAnswerService confAnswerService;
    @Autowired
    private BasedataService basedataService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    @Autowired
    private UserClinicService userClinicService;

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    @Transactional
    public boolean insertUser(User user) {
        if (user.getAccount() == "" || user.getAccount() == null || user.getPassword() == ""
                || user.getPassword() == null) {
            return false;
        }
        int count = userMapper.getCountByName(user);
        if (count > 0) {
            return false;
        } else {
            userMapper.insertUser(user);
            userMapper.insertUserInfo(user);
            baseDataMapper.insertData(user.getAccount());//复制基础数据
            BaseRecipel baseRecipel = new BaseRecipel();
            baseRecipel.setDoctorid("13540023046");
            //获取基础处方数据
            List<BaseRecipel> baseRecipelList = baseRecipelMapper.listBaseRecipels(baseRecipel);
            List<Baseselfdata> baseselfdataList = new ArrayList<Baseselfdata>();
            for (BaseRecipel recipel : baseRecipelList) {
                recipel.setPinyin(Pinyin4jUtil.converterToFirstSpell(recipel.getName()));
                Baseselfdata baseselfdata = new Baseselfdata();
                baseselfdata.setName(recipel.getName());
                baseselfdata.setPinyin(recipel.getPinyin());
                baseselfdata.setBase_id(recipel.getId());
                baseselfdata.setUser_id(user.getAccount());
                baseselfdata.setCtype("yp");
                baseselfdataList.add(baseselfdata);
            }
            //绑定基础处方数据
            int addBaseselfdataListCount = baseDataMapper.savabaseselfdataAndBaseId(baseselfdataList);
            return true;
        }

    }

    @Override
    public List<User> listPageUser(User user) {

        if (user.getLastupdate() == null) {
            return userMapper.listPageUser(user);
        } else {
            user.setLastupdate(getNextDay(user.getLastupdate()));
            return userMapper.listPageUser(user);
        }
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, +23);//+1今天的时间加23小时
        calendar.add(Calendar.MINUTE, +59);
        date = calendar.getTime();
        return date;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void updateUserPwd(User user) {
        userMapper.updateUserPwd(user);
    }

    @Override
    public SysMsg updateUserBaseInfo(User user) {
        SysMsg sysMsg = new SysMsg();
        int count = userMapper.getCountByName(user);
        if (count > 0) {
            sysMsg.setContent("用户名已拥有");
            sysMsg.setStatus("F");
        } else {
            userMapper.updateUserBaseInfo(user);
            sysMsg.setContent("修改成功");
            sysMsg.setStatus("T");
        }
        return sysMsg;
    }

    @Override
    public void updateUserRights(User user) {
        userMapper.updateUserRights(user);
    }

    @Override
    @SysServiceLog(description = "service登录")
    public User getUserByNameAndPwd(String loginname, String password) {
        User u = new User();
        u.setAccount(loginname);
        u.setPassword(password);
        User use = userMapper.getUserInfo(u);
        if (!StringUtil.objIsEmpty(use)) {
            return use;
        }
        password = new SimpleHash("md5", password, ByteSource.Util.bytes(loginname), 2).toHex();
        User user = new User();
        user.setAccount(loginname);
        user.setPassword(password);
        return userMapper.getUserInfo(user);
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteUser(userId);
    }

    @Override
    public User getUserAndRoleById(Long userId) {
        return userMapper.getUserAndRoleById(userId);
    }

    @Override
    public void updateLastLogin(User user) {
        userMapper.updateLastLogin(user);
    }

    @Override
    public List<User> listAllUser() {
        return userMapper.listAllUser();
    }

    @Override
    public User getUserByAccount(String account) {
        //去查一下，是不是有管理员权限
        User userByAccount = userMapper.getUserByAccount(account);
        List<String> roles = userMapper.getRoles(userByAccount.getUserId());
        userByAccount.setRoleIds(roles);
        return userByAccount;
    }

    /* (non-Javadoc)
     * @see com.cdutcm.tcms.sys.service.UserService#getClinic(com.cdutcm.tcms.sys.entity.User)
     */
    @Override
    public String getClinic(User user) {
        return userMapper.getClinic(user);
    }

    /* (non-Javadoc)
     * @see com.cdutcm.tcms.sys.service.UserService#listAllFamousHerbalist()
     */
    @Override
    public List<UserInfo> listAllUserInfo(UserInfo userInfo) {

        return userMapper.listPageUserInfo(userInfo);
    }

    @Override
    public List<UserInfo> listPageUserInfoByNameOrSpecialty(UserInfo userInfo) {
        return userMapper.listPageUserInfoByNameOrSpecialty(userInfo);
    }

    /* (non-Javadoc)
     * @see com.cdutcm.tcms.sys.service.UserService#getUserInfoByAccount(java.lang.String)
     */
    @Override
    public UserInfo getUserInfoByAccount(String account) {
        return userMapper.getUserInfoByAccount(account);
    }

    @Override
    public User getUserByOpenid(String openid) {
        return userMapper.getUserByOpenid(openid);
    }

    @Override
    public List<UserInfo> onlineUser(UserInfo userInfo, List<String> accounts) {
        String accountsStr = "";
        for (int i = 0; i < accounts.size(); i++) {
            if (i == accounts.size() - 1) {
                accountsStr = accountsStr.concat("'" + accounts.get(i) + "'");
                continue;
            }
            accountsStr = accountsStr.concat("'" + accounts.get(i) + "',");
        }
//		System.out.println(accountsStr);
        userInfo.setAccounts(accountsStr);
        return userMapper.listPageOnlineUser(userInfo);
    }

    @Override
    public UserInfo getUserInfoByTel(String telphone) {
        return userMapper.getUserInfoByTel(telphone);
    }

    @Override
    public void storageData(User user) {

        String account = user.getAccount();
        List<Clinic> clinicsByAccount = clinicMapper.getClinicsByAccount(account);
        String userId = String.valueOf(user.getUserId());
//        System.out.println(userId);
        UserIndividual userIndividual = new UserIndividual();
        //设置theme
        ConfAnswer confAnswer = confAnswerService.findByUserIdAndChoiceId(userId, IndivEnum.THEME.getChoiceId());
        if (confAnswer != null) {
            userIndividual.setTheme(confAnswer.getChoiceAnswer());
        } else {
            userIndividual.setTheme(confAnswerService.findByChoiceIdAndCtype(IndivEnum.THEME.getChoiceId(), "S").getChoiceAnswer());
        }
        //设置input
        ConfAnswer confAnswer1 = confAnswerService.findByUserIdAndChoiceId(userId, IndivEnum.INPUT.getChoiceId());
        if (confAnswer1 != null) {
            userIndividual.setInput(confAnswer1.getChoiceAnswer());
        } else {
            userIndividual.setInput(confAnswerService.findByChoiceIdAndCtype(IndivEnum.INPUT.getChoiceId(), "S").getChoiceAnswer());
        }
        Basedatawrapper basedatawrapper = new Basedatawrapper();
        basedatawrapper.setDosages(basedataService.findbasedosage(account));
        basedatawrapper.setMaterials(basedataService.findbasematerial(account));
        basedatawrapper.setSymptoms(basedataService.findbasesysptom(account));
        basedatawrapper.setUsages(basedataService.findbaseusage(account));
        basedatawrapper.setEighteenNinteens(basedataService.findmedicineeighteenninteen());
        redisTemplate.opsForValue().set(user.getAccount() + "clinicsBaseData", clinicsByAccount, 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(user.getAccount() + "userIndividualBaseData", userIndividual, 1, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(user.getAccount() + "receipelBaseData", basedatawrapper, 1, TimeUnit.HOURS);
        System.out.println("缓存" + user.getUsername() + "完毕！");
    }

    @Override
    public void authCloudPlat(User u) {
        String password = u.getPassword();
        String account = u.getAccount();
        //根据登录类型 走不同的认证
//      Methods.getInstance()
        HashMap<Object, Object> tokens;
        if (u.getLoginType().equals(LoginTypeConstant.SMS)) {
            String codekey = (String) redisTemplate.opsForValue().get(account + "codekey");
            tokens = Methods.getInstance().getSmsToken(account, password, codekey);
        } else {
            tokens = Methods.getInstance().getToken(account, password);
        }
        Boolean code = (Boolean) tokens.get("code");
        if (code) {
            //redis存入token
            //1.查询是否已经有账号
            User userByAccount = userMapper.getUserByAccount(account);
            User user = new User();
            String md5Password = new SimpleHash("md5", password, account, 2).toHex();
            user.setPassword(md5Password);
            user.setAccount(account);
            TokenObj tokenObj = (TokenObj) tokens.get("data");
            redisTemplate.opsForValue().set(account + "token", tokenObj.getAccess_token(), 2, TimeUnit.HOURS);
            CloudUser cloudUser = Methods.getInstance().getUserInfo(tokenObj.getAccess_token());
            System.out.println("获取云平台用户信息" + cloudUser.toString());
            user.setUsername(cloudUser.getUserName());
            user.setPwd(password);
            if (StringUtil.objIsEmpty(userByAccount)) {
                IdWorker idWorker = new IdWorker();
                user.setUserId(idWorker.nextId());
                userMapper.insertUser(user);
                //插入默认诊所信息
//                clinicService.insertUserAssociatedClinic(account, Const.CDUTCM, Const.ROLE_DOCTOR);
                clinicService.insertUserAssociatedClinic(account, Const.PRIVATE_ACCOUNT, Const.ROLE_DOCTOR);
//                baseDataMapper.insertData(user.getAccount());
            } else {
                user.setUserId(userByAccount.getUserId());
                userMapper.updateUser(user);
                if (userClinicService.findByClinicIdAndAccount("1006",account) == null){
                    clinicService.insertUserAssociatedClinic(account, Const.PRIVATE_ACCOUNT, Const.ROLE_DOCTOR);
                }
            }
            //插入用户信息
            UserInfo userInfoByAccount = userMapper.getUserInfoByAccount(account);
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setName(cloudUser.getUserName());
            userInfo.setTelephone(cloudUser.getPhone());
            userInfo.setStatus(0);
            if (StringUtil.objIsEmpty(userInfoByAccount)) {
                sysUserInfoMapper.insert(userInfo);
            } else {
                userInfo.setId(userInfoByAccount.getId());
                sysUserInfoMapper.update(userInfo);
            }
        }
    }

    @Override
    public ResultVO updateUser(String phone, String password, String code) {
        return null;
    }


    @Override
    public User authHulian(String phone) {
        User userByAccount = userMapper.getUserByAccount(phone);
        User user = new User();
        String password = phone + createRandomCharData(4);
        String md5Password = new SimpleHash("md5", password, phone, 2).toHex();
        user.setPassword(md5Password);
        user.setAccount(phone);
        user.setPwd(password);
        if (StringUtil.objIsEmpty(userByAccount)) {
            IdWorker idWorker = new IdWorker();
            user.setUserId(idWorker.nextId());
            userMapper.insertUser(user);
            //插入默认诊所信息
            clinicService.insertUserAssociatedClinic(phone, Const.CDUTCM, Const.ROLE_DOCTOR);
            clinicService.insertUserAssociatedClinic(phone, Const.PRIVATE_ACCOUNT, Const.ROLE_DOCTOR);
            return user;
        } else {
            return userByAccount;
        }

    }

    public String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;//保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = randdata.nextInt(26) + 97;//保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        String result = sb.toString();
        System.out.println("生成的随机数兑换码为" + result);
        return result;
    }

    public static void main(String[] args) {
      String  password = new SimpleHash("md5", "111111", ByteSource.Util.bytes("18502828673"), 2).toHex();
        System.out.println(password);

    }
}
