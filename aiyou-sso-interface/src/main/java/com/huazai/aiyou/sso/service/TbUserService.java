package com.huazai.aiyou.sso.service;

import com.huazai.aiyou.common.response.AiyouResultData;
import com.huazai.aiyou.sso.pojo.TbUser;

/**
 * 
 * @author HuaZai
 * @contact who.seek.me@java98k.vip
 *          <ul>
 * @description 用户服务接口层
 *              </ul>
 * @className TbUserService
 * @package com.huazai.b2c.aiyou.service
 * @createdTime 2017年06月17日
 *
 * @version V1.0.0
 */
public interface TbUserService
{

	/**
	 * 
	 * @author HuaZai
	 * @contact who.seek.me@java98k.vip
	 * @title checkUserData
	 *        <ul>
	 * @description 用户数据校验
	 *              </ul>
	 * @createdTime 2017年06月17日
	 * @param param 参数
	 * @param type  类型
	 * @return
	 * @return AiyouResultData
	 *
	 * @version : V1.0.0
	 */
	public AiyouResultData checkUserData(String param, int type);

	/**
	 * 
	 * @author HuaZai
	 * @contact who.seek.me@java98k.vip
	 * @title registerInfo
	 *        <ul>
	 * @description 注册用户信息
	 *              </ul>
	 * @createdTime 2017年06月17日
	 * @param tbUser 用户实体
	 * @return
	 * @return AiyouResultData
	 *
	 * @version : V1.0.0
	 */
	public AiyouResultData registerInfo(TbUser tbUser);

	/**
	 * 
	 * @author HuaZai
	 * @contact who.seek.me@java98k.vip
	 * @title login
	 *        <ul>
	 * @description 用户登录接口
	 *              </ul>
	 * @createdTime 2017年06月17日
	 * @param username
	 * @param passworld
	 * @return
	 * @return AiyouResultData
	 *
	 * @version : V1.0.0
	 */
	public AiyouResultData login(String username, String password);

	/**
	 * 
	 * @author HuaZai
	 * @contact who.seek.me@java98k.vip
	 * @title getUserInfoByToken
	 *        <ul>
	 * @description 根据 Token 获取用户信息
	 *              </ul>
	 * @createdTime 2017年06月17日
	 * @param token
	 * @return
	 * @return AiyouResultData
	 *
	 * @version : V1.0.0
	 */
	public AiyouResultData getUserInfoByToken(String token);

	/**
	 * 
	 * @author HuaZai
	 * @contact who.seek.me@java98k.vip
	 * @title loginOut
	 *        <ul>
	 * @description 退出登录，根据用户Token删除Redis用户登录消息
	 *              </ul>
	 * @createdTime 2017年06月17日
	 * @param token
	 * @return
	 * @return AiyouResultData
	 *
	 * @version : V1.0.0
	 */
	public AiyouResultData loginOut(String token);

}
