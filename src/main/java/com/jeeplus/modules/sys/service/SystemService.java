/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.Encodes;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.security.Digests;
import com.jeeplus.core.security.shiro.session.SessionDAO;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.core.service.ServiceException;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.module.ModuleContent;
import com.jeeplus.modules.meiguotong.entity.module.ModuleDetails;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtml;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.mapper.car.CarInfoMapper;
import com.jeeplus.modules.meiguotong.mapper.comcity.ComCityMapper;
import com.jeeplus.modules.meiguotong.mapper.comcomment.ComCommentMapper;
import com.jeeplus.modules.meiguotong.mapper.guideroute.GuideRouteMapper;
import com.jeeplus.modules.meiguotong.mapper.liner_line.LinerLineMapper;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleHtmlMapper;
import com.jeeplus.modules.meiguotong.mapper.order.OrderSysMapper;
import com.jeeplus.modules.meiguotong.mapper.product.RouteMapper;
import com.jeeplus.modules.meiguotong.mapper.travel.TravelCustomizationMapper;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.citystrategy.CityStrategyService;
import com.jeeplus.modules.meiguotong.service.comadd.ComAdService;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.module.ModuleContentService;
import com.jeeplus.modules.meiguotong.service.module.ModuleHtmlService;
import com.jeeplus.modules.meiguotong.service.module.ModuleNameService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import com.jeeplus.modules.meiguotong.service.travel.TravelCustomizationService;
import com.jeeplus.modules.sys.entity.DataRule;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.sysUser.Role;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.sys.mapper.sysUser.RoleMapper;
import com.jeeplus.modules.sys.mapper.sysUser.SysUserMapper;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;
import com.jeeplus.modules.sys.utils.LogUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author jeeplus
 * @version 2016-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	

	@Autowired
	private DataRuleService dataRuleService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private SessionDAO sessionDao;

	@Autowired
	private ModuleHtmlMapper moduleHtmlMapper;
	@Autowired
	private ModuleContentService moduleContentService;
	@Autowired
	private CarInfoMapper carInfoMapper;
	@Autowired
	private ComCommentMapper comCommentMapper;
	@Autowired
	private ComCityMapper comCityMapper;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private LinerLineMapper linerLineMapper;
	@Autowired
	private OrderSysMapper orderSysMapper;
	@Autowired
	private TravelCustomizationMapper travelCustomizationMapper;
	@Autowired
	private CityStrategyService cityStrategyService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private GuideRouteMapper guideRouteMapper;
	@Autowired
	private RouteMapper routeMapper;
	@Autowired
	private ComArticleService comArticleService;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ScenicSpotTicketService scenicSpotTicketService;
	@Autowired
	private ComAdService comAdService;
	
	
	
	
	
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}


	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		dataRuleFilter(user);
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userMapper.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		dataRuleFilter(user);
		List<User> list = userMapper.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userMapper.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userMapper.insert(user);
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userMapper.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userMapper.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userMapper.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userMapper.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userMapper.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userMapper.deleteUserRole(user);
		userMapper.delete(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userMapper.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(UserUtils.getSession().getHost());
		user.setLoginDate(new Date());
		userMapper.updateLoginInfo(user);
		//给后台登陆IP管理插入数据
/*		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setCustomerid(user.getId());
		sysLoginLog.setCustomerName(user.getName());
		sysLoginLog.setLoginIp(UserUtils.getSession().getHost());
		sysLoginLog.setLoginDate(new Date());
		sysLoginLogMapper.insert(sysLoginLog);*/
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	public static void main(String[] args) {
		System.out.println(validatePassword("123456","e10adc3949ba59abbe56e057f20f883e"));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleMapper.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleMapper.getByName(r);
	}
	
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleMapper.getByEnname(r);
	}
	
	public List<Role> findRole(Role role){
		return roleMapper.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleMapper.insert(role);
		}else{
			role.preUpdate();
			roleMapper.update(role);
		}
		// 更新角色与菜单关联
		roleMapper.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleMapper.insertRoleMenu(role);
		}
		
		// 更新角色与数据权限关联
		roleMapper.deleteRoleDataRule(role);
		if (role.getDataRuleList().size() > 0){
			roleMapper.insertRoleDataRule(role);
		}
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleMapper.deleteRoleMenu(role);
		roleMapper.deleteRoleDataRule(role);
		roleMapper.delete(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuMapper.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	public List<Menu> getChildren(String parentId){
		return menuMapper.getChildren(parentId);
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuMapper.insert(menu);
		}else{
			menu.preUpdate();
			menuMapper.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuMapper.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuMapper.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuMapper.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {

		// 解除菜单角色关联
		List<Object> mrlist =  menuMapper.execSelectSql(
				"SELECT distinct a.menu_id as id FROM sys_role_menu a left join sys_menu menu on a.menu_id = menu.id WHERE a.menu_id ='"
						+ menu.getId() + "' OR menu.parent_ids LIKE  '%," + menu.getId() + ",%'");
		for (Object mr : mrlist) {
			menuMapper.deleteMenuRole(mr.toString());
		}

		// 删除菜单关联的数据权限数据，以及解除角色数据权限关联
		List<Object> mdlist = menuMapper.execSelectSql(
				"SELECT distinct a.id as id FROM sys_datarule a left join sys_menu menu on a.menu_id = menu.id WHERE a.menu_id ='"
						+ menu.getId() + "' OR menu.parent_ids LIKE  '%," + menu.getId() + ",%'");
		for (Object md : mdlist) {
			DataRule dataRule = new DataRule(md.toString());
			dataRuleService.delete(dataRule);
		}

		menuMapper.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// // 清除权限缓存
		// systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取产品信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("...．．∵ ∴★．∴∵∴ ╭ ╯╭ ╯╭ ╯╭ ╯∴∵∴∵∴ \r\n ");
		sb.append("．☆．∵∴∵．∴∵∴▍▍ ▍▍ ▍▍ ▍▍☆ ★∵∴ \r\n ");
		sb.append("▍．∴∵∴∵．∴▅███████████☆ ★∵ \r\n ");
		sb.append("◥█▅▅▅▅███▅█▅█▅█▅█▅█▅███◤          欢迎使用 "+Global.getConfig("productName")+Global.getConfig("version")+"\r\n ");
		sb.append("． ◥███████████████████◤                    http://www.jeeplus.org\r\n ");
		sb.append(".．.．◥████████████████■◤\r\n ");
		System.out.println(sb.toString());
		return true;
	}
	
	public void afterPropertiesSet() throws Exception {
	}


	public List<Role> findFatherRoleList(Role role) {
		return roleMapper.findFatherRoleList(role);
	}
	/**
	 *  获取页面内容配置
	 * @param moduleHtmlid
	 * @param languageId
	 * @return
	 */
	public List<ModuleContent> getHomeContent(String moduleHtmlid,String languageId)
	{
		ModuleContent moduleContent = new ModuleContent();
		moduleContent.setLanguageId(Integer.parseInt(languageId));
		moduleContent.setModuleHtmlId(Integer.parseInt(moduleHtmlid));
		List<ModuleContent> moduleContentList = moduleContentService.getModuleDataInfo(moduleContent);
		
		CarInfo carInfo = new CarInfo();
		carInfo.setLanguage(Integer.parseInt(languageId));
		carInfo.setStatus(3);
		
		ComComment comComment = new ComComment();
		comComment.setLanguageId(Integer.parseInt(languageId));
		comComment.setFatherId(0);
		comComment.setStatus(1);

		ComCity comCity = new ComCity();
		comCity.setLanguageId(languageId);
		comCity.setStatus(1);
		
		ScenicSpot scenicSpot = new ScenicSpot();
		scenicSpot.setLanguageId(Integer.parseInt(languageId));
		scenicSpot.setStatus(3);

		LinerLine linerLine = new LinerLine();
        linerLine.setLanguageId(Integer.parseInt(languageId));
        linerLine.setStatus(3);
		
		OrderSys orderSys = new OrderSys();
		orderSys.setLanguageid(Integer.parseInt(languageId));
		orderSys.setFatherid(0);
		
		TravelCustomization travelCustomization = new TravelCustomization();
		travelCustomization.setLanguage(Integer.parseInt(languageId));
		travelCustomization.setStatus(1);
		
		CityStrategy cityStrategy = new CityStrategy();
		cityStrategy.setLanguageId(Integer.parseInt(languageId));
		cityStrategy.setStatus(1);
		
		GuideRoute guideRoute = new GuideRoute();
		guideRoute.setLanguageid(Integer.parseInt(languageId));
		
		Guide guide = new Guide();
		guide.setStatus(1);
		guide.setType(1);
		
		Route route = new Route();
		route.setLanguage(Integer.parseInt(languageId));
		route.setStatus(3);
		
		ComArticle comArticle = new ComArticle();
		comArticle.setLanguageId(Integer.parseInt(languageId));
		comArticle.setStatus(1);
		
		SysUser sysUser = new SysUser();
		sysUser.setUserType(2);
		sysUser.setFatherid(0);
		sysUser.setCompanyType("1");
		
		ScenicSpotTicket scenicSpotTicket = new ScenicSpotTicket();
		scenicSpotTicket.setStatus(1);
		scenicSpotTicket.setLanguageId(Integer.parseInt(languageId));
		
		for(ModuleContent m : moduleContentList) {
			//左侧广告  右侧广告
			if(m.getType() == 7) {continue;
			}else if(m.getModuleType() == 1 || m.getModuleType() == 2 || (m.getModuleType() == 3 && m.getType() == 14)) {
				switch (m.getType()) {
				case 1: 
					carInfo.setIds(m.getTypeid());
					m.setCarInfoList(carInfoMapper.findList(carInfo));
				break;
				case 2: 
					comComment.setIds(m.getTypeid());
					m.setComCommentList(comCommentMapper.findList(comComment));
					break;
				case 3: 
					comCity.setIds(m.getTypeid());
					m.setComCityList(comCityMapper.findList(comCity));
					break;
				case 4: 
					scenicSpot.setIds(m.getTypeid());
					m.setScenicSpotList(scenicSpotService.getScenicSpotList(scenicSpot));
					break;
				case 5:
                    linerLine.setIds(m.getTypeid());
					m.setLinerLineList(linerLineMapper.findList(linerLine));
					break;
				case 6: 
					orderSys.setIds(m.getTypeid());
					m.setOrderSysList(orderSysMapper.findList(orderSys));
					break;
				case 8: 
					travelCustomization.setIds(m.getTypeid());
					m.setTravelCustomizationList(travelCustomizationMapper.findList(travelCustomization));
					break;
				case 9: 
					cityStrategy.setIds(m.getTypeid());
					m.setCityStrategyList(cityStrategyService.getCityStrategyList(cityStrategy));
					break;
				case 10: 
					guideRoute.setIds(m.getTypeid());
					m.setGuideRouteList(guideRouteMapper.findList(guideRoute));
					break;
				case 11: 
					guide.setIds(m.getTypeid());
					m.setGuideList(guideService.getGuideList(guide));
					break;
				case 12:
					route.setType(1);
					route.setIds(m.getTypeid());
					m.setRouteList(routeMapper.findList(route));
					break;
				case 13:
					comArticle.setType(2);
					comArticle.setIds(m.getTypeid());
					m.setComArticleList(comArticleService.getComArticleList(comArticle));
					break;
				case 14:
					sysUser.setIds(m.getTypeid());
					m.setSysUserList(sysUserMapper.findList(sysUser));
					break;
				case 15:
					route.setType(2);
					route.setIds(m.getTypeid());
					m.setRouteList(routeMapper.findList(route));
					break;
				case 16:
					scenicSpotTicket.setIds(m.getTypeid());
					m.setScenicSpotTicketList(scenicSpotTicketService.getScenicSpotTicketList(scenicSpotTicket));
					break;
				case 17:
					comArticle.setType(1);
					comArticle.setIds(m.getTypeid());
					m.setComArticleList(comArticleService.getComArticleList(comArticle));
					break;
				default:
				}
			}else if(m.getModuleType() == 3) {
				if(null != m.getModuleDetails()) {
					for(ModuleDetails d: m.getModuleDetails()) {
						switch (m.getType()) {
						case 1: 
							carInfo.setIds(d.getDesid());
							d.setCarInfoList(carInfoMapper.findList(carInfo));
						break;
						case 2: 
							comComment.setIds(d.getDesid());
							d.setComCommentList(comCommentMapper.findList(comComment));
							break;
						case 3: 
							comCity.setIds(d.getDesid());
							d.setComCityList(comCityMapper.findList(comCity));
							break;
						case 4: 
							scenicSpot.setIds(d.getDesid());
							d.setScenicSpotList(scenicSpotService.getScenicSpotList(scenicSpot));
							break;
						case 5:
                            linerLine.setIds(d.getDesid());
                            d.setLinerLineList(linerLineMapper.findList(linerLine));
							break;
						case 6: 
							orderSys.setIds(d.getDesid());
							d.setOrderSysList(orderSysMapper.findList(orderSys));
							break;
						case 8: 
							travelCustomization.setIds(d.getDesid());
							d.setTravelCustomizationList(travelCustomizationMapper.findList(travelCustomization));
							break;
						case 9: 
							cityStrategy.setIds(d.getDesid());
							d.setCityStrategyList(cityStrategyService.getCityStrategyList(cityStrategy));
							break;
						case 10: 
							guideRoute.setIds(d.getDesid());
							d.setGuideRouteList(guideRouteMapper.findList(guideRoute));
							break;
						case 11: 
							guide.setIds(d.getDesid());
							d.setGuideList(guideService.getGuideList(guide));
							break;
						case 12:
							route.setType(1);
							route.setIds(d.getDesid());
							d.setRouteList(routeMapper.findList(route));
							break;
						case 13:
							comArticle.setType(2);
							comArticle.setIds(d.getDesid());
							d.setComArticleList(comArticleService.getComArticleList(comArticle));
							break;
						case 14:
							sysUser.setIds(d.getDesid());
							d.setSysUserList(sysUserMapper.findList(sysUser));
							break;
						case 15:
							route.setType(2);
							route.setIds(d.getDesid());
							d.setRouteList(routeMapper.findList(route));
							break;
						case 16:
							scenicSpotTicket.setIds(d.getDesid());
							d.setScenicSpotTicketList(scenicSpotTicketService.getScenicSpotTicketList(scenicSpotTicket));
							break;
						case 17:
							comArticle.setType(1);
							comArticle.setIds(d.getDesid());
							d.setComArticleList(comArticleService.getComArticleList(comArticle));
							break;
						default:
						}
					}
				}
			}
		}
		return moduleContentList;
	}
	/**
	 *  获取页面广告配置
	 * @param moduleHtmlid
	 * @param languageId
	 * @return
	 */
	public List<ModuleContent> getAdvertContent(String moduleHtmlid,String languageId)
	{
		List<ModuleContent> list = new ArrayList<>();
		ModuleContent moduleContent = new ModuleContent();
		moduleContent.setLanguageId(Integer.parseInt(languageId));
		moduleContent.setModuleHtmlId(Integer.parseInt(moduleHtmlid));
		List<ModuleContent> moduleContentList = moduleContentService.getModuleDataInfo(moduleContent);
		if(moduleContentList!=null&&moduleContentList.size()>0){
			String advertids = "";
			ModuleContent moduleContent1 = new ModuleContent();
			for(ModuleContent a: moduleContentList){
				moduleContent1 = a;
				advertids += a.getTypeid()+",";
			}
			advertids.substring(0, advertids.length()-1); 
			List<ComAd> list1 = comAdService.getAdvertListById(advertids);
			moduleContent1.setAdvertList(list1);
			list.add(moduleContent1);
		}
		return list;
	}
	/**
	 *  获取页面底部导航配置
	 * @param moduleHtmlid
	 * @param languageId
	 * @return
	 */
	public List<ModuleContent> getFootContent(String moduleHtmlid,String languageId)
	{
		List<ModuleContent> list = new ArrayList<>();
		return list;
	}
}
