package com.huazai.aiyou.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.huazai.aiyou.common.response.AiyouResultData;
import com.huazai.aiyou.common.utils.DateTimeUtils;
import com.huazai.aiyou.common.utils.JsonUtils;
import com.huazai.aiyou.sso.mapper.TbUserMapper;
import com.huazai.aiyou.sso.pojo.TbUser;
import com.huazai.aiyou.sso.pojo.TbUserExample;
import com.huazai.aiyou.sso.pojo.TbUserExample.Criteria;
import com.huazai.aiyou.sso.service.TbJedisClientService;
import com.huazai.aiyou.sso.service.TbUserService;

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

	@Autowired
	private TbJedisClientService tbJedisClientService;

	@Value(value = "${AIYOU_TB_LOGIN_USER_INFO_KEY}")
	private String AIYOU_TB_LOGIN_USER_INFO_KEY;

	@Value(value = "${AIYOU_TB_LOGIN_USER_INFO_KEY_EXPIRE}")
	private Integer AIYOU_TB_LOGIN_USER_INFO_KEY_EXPIRE;

	@Override
	public AiyouResultData checkUserData(String param, int type)
	{
		try
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
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 返回 true，表示该账户信息未注册，可用
		return AiyouResultData.ok(true);
	}

	@Override
	public AiyouResultData registerInfo(TbUser tbUser)
	{
		try
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
				return AiyouResultData.build(400, "用户注册异常，后台小哥哥正在努力修改中");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return AiyouResultData.ok();
	}

	@Override
	public AiyouResultData login(String username, String passworld)
	{
		// 查询用户信息
		TbUserExample tbUserExample = new TbUserExample();
		Criteria createCriteria = tbUserExample.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);

		// 判断用户名是否正确
		if (CollectionUtils.isEmpty(tbUsers))
		{
			return AiyouResultData.build(400, "用户名或密码错误");
		}

		// 判断密码是否错误
		TbUser tbUser = tbUsers.get(0);
		if (!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(passworld.getBytes())))
		{
			return AiyouResultData.build(400, "用户名或密码错误");
		}

		// 通过校验，登录成功，生成Token,使用UUID
		String token = UUID.randomUUID().toString();
		try
		{
			tbUser.setPassword(null);
			tbJedisClientService.set(AIYOU_TB_LOGIN_USER_INFO_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
			tbJedisClientService.expire(AIYOU_TB_LOGIN_USER_INFO_KEY + ":" + token, AIYOU_TB_LOGIN_USER_INFO_KEY_EXPIRE);
		} catch (Exception e)
		{
			e.printStackTrace();
			return AiyouResultData.build(400, "登录异常，后台小哥哥正在努力修改中");
		}
		return AiyouResultData.ok(token);
	}

	@Override
	public AiyouResultData getUserInfoByToken(String token)
	{
		// 获取用户登录信息
		String resultUserInfo = tbJedisClientService.get(AIYOU_TB_LOGIN_USER_INFO_KEY + ":" + token);
		if (StringUtils.isBlank(resultUserInfo))
		{
			return AiyouResultData.build(400, "用户登录已过期，请重新登录");
		}

		// 刷新Token过期时间
		tbJedisClientService.expire(AIYOU_TB_LOGIN_USER_INFO_KEY + ":" + token, AIYOU_TB_LOGIN_USER_INFO_KEY_EXPIRE);

		// 返回用户信息
		TbUser tbUser = JsonUtils.jsonToPojo(resultUserInfo, TbUser.class);
		return AiyouResultData.ok(tbUser);
	}

	@Override
	public AiyouResultData loginOut(String token)
	{
		try
		{
			// 根据Token清除用户登录信息
			tbJedisClientService.del(AIYOU_TB_LOGIN_USER_INFO_KEY + ":" + token);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return AiyouResultData.ok();
	}

}
