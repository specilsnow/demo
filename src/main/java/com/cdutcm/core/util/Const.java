package com.cdutcm.core.util;


/**
 * @author zhufj
 * @Description 一些基础常量
 * @Date 2016-9-20
 */
public class Const {
    //诊所
    public static final String CDUTCM = "1000";
    public static final String PRIVATE_ACCOUNT = "1006";
    public static final String CDUTCM_2015 = "1012";
    //	医生
    public static final long ROLE_DOCTOR = 1;

    public static final String SESSION_SECURITY_CODE = "sessionSecCode";
    public static final String SESSION_USER = "sessionUser";
    public static final String SESSION_USER_RIGHTS = "sessionUserRights";
    public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String PATIENT = "patient";//病人

    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(userlogin)).*";    //不对匹配该值的访问路径拦截（正则）
    //	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
    // 电子病历相关
    public static final String SESSION_CURRENT_PATIENTEMR = "sessionCurrentPatientEmr";
    public static final String SESSION_CURRENT_EMR = "currentEmr";    // 当前病历
    public static final String SESSION_CURRENT_EMR_VISITNO = "currentEmrVisitNo";   // 当前病历的病人对应的visit_no
    public static final String CURRENTEMR_ = "CURRENTEMR_";    // 当前病历的病人对应的visit_no对应的值的前缀
    public static final String LUNAR = "LUNAR";    // 农历

    // 日志相关
    public static final String LOG_USERlOGIN = "userlogin";        // 用户登录
    public static final String SAVE_ROLE = "saveRole";            //保存角色
    public static final String SAVE_ROLE_PERMISSIONS = "saveRolePermissions";            //保存角色权限
    public static final String DELETE_ROLE = "deleteRole";            //删除角色
    public static final String DELETE_USER = "deleteUser";            //删除用户
    public static final String SAVE_USER = "saveUser";            //保存用户
    public static final String SAVE_User_PERMISSIONS = "saveUserPermissions";            //保存用户权限
    public static final String EXPORT_USER_TOXML = "exportUserToXml";            //导出用户到xml
    public static final String SAVE_MENU = "saveMenu";            //保存菜單
    public static final String DELETE_MENU = "deleteMenu";            //删除菜單
    public static final String LOG_SYNCHISUSER = "syncHisUser";    // 同步His用户
    public static final String LOG_SYNCHISDEPT = "syncHisDept"; // 同步His部门
    public static final String LOG_SYNCHISRECIPELUSAGE = "syncHisRecipelUsage"; // 同步His处方用法
    public static final String LOG_SYNCHISMEDICINEUSAGE = "syncHisMedicinelUsage"; // 同步His药品用法
    public static final String LOG_SYNCHISFREQUENCY = "syncHisFrequency"; // 同步His用药频率
    public static final String LOG_SYNCHISCLINICDATA = "syncHisClinicdata"; // 同步His药品基础信息
    public static final String LOG_SYNCHISPHARMACY = "syncHisPharmacy"; // 同步His药房
    public static final String LOG_SYSTEMUSER = "systemUser";       // 系统用户自动执行
    public static final String LOG_CONSTANTSYSTEM = "syncHisConstantData";       // 系统用户自动同步常量信息
    public static final String LOG_VARIABLESYSTEM = "syncHisVariabletData";       // 系统用户自动同步变量信息
    // 系统
    public static final String SYS_CONFIG_EMRPAGE = "emrPage";       // 系统配置的电子病历页面
    public static final String SYS_USER_CONFIG = "sysUserConfig";       // 用户配置信息

    /**
     * 病位归经46位
     */
    public static String[] bingweiguijing = new String[]{"心", "肝", "胆", "脾", "胃", "肠", "肺", "肾", "膀胱", "胞宫", "冲任", "精室", "表", "腑实", "不调",
            "热", "虚热", "血热", "津亏", "毒", "脓", "积聚", "寒", "暑", "气虚", "血虚", "阴虚", "阳虚", "不固", "心烦", "神昏",
            "血瘀", "络阻", "出血", "气滞", "咳喘", "气逆", "阳亢", "风", "动风", "燥", "湿", "水", "痰", "食", "虫", "程度", "体质",};
}
