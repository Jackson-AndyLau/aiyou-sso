package com.huazai.b2c.aiyou.service;

import com.huazai.b2c.aiyou.repo.AiyouResultData;

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
	 * @param type 类型
	 * @return
	 * @return AiyouResultData
	 *
	 * @version : V1.0.0
	 */
	public AiyouResultData checkUserData(String param, int type);

}
