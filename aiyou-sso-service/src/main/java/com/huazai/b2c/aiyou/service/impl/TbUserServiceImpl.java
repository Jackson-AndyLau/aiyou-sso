package com.huazai.b2c.aiyou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.huazai.b2c.aiyou.mapper.TbUserMapper;
import com.huazai.b2c.aiyou.pojo.TbUser;
import com.huazai.b2c.aiyou.pojo.TbUserExample;
import com.huazai.b2c.aiyou.pojo.TbUserExample.Criteria;
import com.huazai.b2c.aiyou.repo.AiyouResultData;
import com.huazai.b2c.aiyou.service.TbUserService;

/**
 * 
 * @author HuaZai
 * @contact who.seek.me@java98k.vip
 *          <ul>
 * @description 用户服务实现类
 *              </ul>
 * @className TbUserServiceImpl
 * @package com.huazai.b2c.aiyou.service.impl
 * @createdTime 2017年06月17日
 *
 * @version V1.0.0
 */
@Service
public class TbUserServiceImpl implements TbUserService
{

	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public AiyouResultData checkUserData(String param, int type)
	{
		// 查询用户数据
		TbUserExample tbUserExample = new TbUserExample();
		Criteria createCriteria = tbUserExample.createCriteria();
		// 1、2、3分别代表username、phone、email
		if (type == 1)
		{
			createCriteria.andUsernameEqualTo(param);
		} else if (type == 2)
		{
			createCriteria.andPhoneEqualTo(param);
		} else if (type == 3)
		{
			createCriteria.andEmailEqualTo(param);
		} else
		{
			return AiyouResultData.build(400, "非法参数");
		}
		List<TbUser> resultUsers = tbUserMapper.selectByExample(tbUserExample);
		if (CollectionUtils.isNotEmpty(resultUsers))
		{
			// 返回 false，表示该账户信息已注册使用了，不可用
			return AiyouResultData.ok(false);
		}
		// 返回 true，表示该账户信息未注册，可用
		return AiyouResultData.ok(true);
	}

}
