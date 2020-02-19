package com.huazai.b2c.aiyou.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.huazai.b2c.aiyou.mapper.TbUserMapper;
import com.huazai.b2c.aiyou.pojo.TbUser;
import com.huazai.b2c.aiyou.pojo.TbUserExample;
import com.huazai.b2c.aiyou.pojo.TbUserExample.Criteria;
import com.huazai.b2c.aiyou.repo.AiyouResultData;
import com.huazai.b2c.aiyou.service.TbUserService;
import com.huazai.b2c.aiyou.utils.DateTimeUtils;

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

	@Override
	public AiyouResultData registerInfo(TbUser tbUser)
	{
		// 校验用户数据，强制不能为空值
		if (StringUtils.isEmpty(tbUser.getUsername()))
		{
			return AiyouResultData.build(400, "注册失败，用户名不能为空");
		}
		if (StringUtils.isEmpty(tbUser.getPassword()))
		{
			return AiyouResultData.build(400, "注册失败，用户密码不能为空");
		}
		
		// 校验用户信息是否可用
		if (!(boolean) this.checkUserData(tbUser.getUsername(), 1).getData())
		{
			return AiyouResultData.build(400, "该用户名已被注册");
		}
		if (StringUtils.isNotEmpty(tbUser.getPhone()))
		{
			if (!(boolean) this.checkUserData(tbUser.getPhone(), 2).getData())
			{
				return AiyouResultData.build(400, "该手机号被注册");
			}
		}
		if (StringUtils.isNotEmpty(tbUser.getEmail()))
		{
			if (!(boolean) this.checkUserData(tbUser.getEmail(), 3).getData())
			{
				return AiyouResultData.build(400, "该邮箱被注册");
			}
		}
		
		// MD5 加密密码
		String passString = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		tbUser.setPassword(passString);
		
		// 不去用户属性
		tbUser.setCreated(DateTimeUtils.getCurrentDateTime());
		tbUser.setUpdated(DateTimeUtils.getCurrentDateTime());
		
		// 插入数据
		try
		{
			tbUserMapper.insertSelective(tbUser);
		} catch (Exception e)
		{
			e.printStackTrace();
			return AiyouResultData.build(400, "用户注册异常，请联系管理员");
		}
		return AiyouResultData.ok();
	}

}
