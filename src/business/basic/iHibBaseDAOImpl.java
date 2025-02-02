package business.basic;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.VRoleButton;
import model.VRoleClumn;
import model.VRoleSystemModel;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.layuitree;

import com.alibaba.fastjson.JSON;

public class iHibBaseDAOImpl implements iHibBaseDAO {
	public static final int INSERT = 1;// 代表添加操作
	public static final int UPDATE = 2;// 代表更新操作
	public static final int DELETE = 3;// 代表删除操作

	// private static final Log log=LogFactory.getLog(iHibBaseDAOImpl.class);

	@Override
	public Object insert(Object obj) {// obj必须是符合hibernate的pojo对象

		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			Serializable key = session.save(obj);
			tx.commit();// 持久化操作
			session.close();
			return key;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.insert",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public boolean insertList(List<Object> list) {
		Session session = HibSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			for (Object obj : list) {
				session.save(obj);
			}
			tx.commit();// 持久化操作
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.insert",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean delete(Class cls, Serializable id) {
		Session session = HibSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 先用cls和id查询出要删除的对象
			session.delete(session.get(cls, id));

			tx.commit();// 持久化操作
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.delete",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean delete(Object obj) {
		Session session = HibSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			session.delete(obj);
			tx.commit();// 持久化操作
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.delete",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean delete(List<Object> objlist) {
		Session session = HibSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			for (Object obj : objlist) {
				session.delete(obj);
			}
			tx.commit();// 持久化操作
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.delete",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean update(Object obj) {
		Session session = HibSessionFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			session.update(obj);
			tx.commit();// 持久化操作
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.update",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public List select(String hql) {
		Session session = HibSessionFactory.getSession();
		try {
			Query query = session.createQuery(hql);
			List list = query.list();

			session.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.select",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public List select(String hql, int startIndex, int length) {
		Session session = HibSessionFactory.getSession();
		try {
			Query query = session.createQuery(hql);
			query.setFirstResult(startIndex);// 设置起始记录位置
			query.setMaxResults(length);// 设置返回记录数
			List list = query.list();

			session.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.select",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public List select(String hql, Object[] para) {
		Session session = HibSessionFactory.getSession();
		try {
			Query query = session.createQuery(hql);
			// 根据para设置参数
			for (int i = 0; i < para.length; i++) {
				query.setParameter(i, para[i]);
			}
			List list = query.list();
			session.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.select",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public List select(String hql, Object[] para, int startIndex, int length) {
		Session session = HibSessionFactory.getSession();
		try {
			Query query = session.createQuery(hql);
			// 根据para设置参数
			for (int i = 0; i < para.length; i++) {
				query.setParameter(i, para[i]);
			}

			query.setFirstResult(startIndex);// 设置起始记录位置
			query.setMaxResults(length);// 设置返回记录数
			List list = query.list();

			session.close();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.select",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public int selectValue(String hql) {
		Session session = HibSessionFactory.getSession();
		try {
			Query query = session.createQuery(hql);
			Object obj = query.uniqueResult();
			session.close();
			if (obj instanceof Long) {
				return ((Long) obj).intValue();
			} else if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.selectValue",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return 0;
	}

	@Override
	public int selectValue(String hql, Object[] para) {
		Session session = HibSessionFactory.getSession();
		try {
			Query query = session.createQuery(hql);
			// 根据para设置参数
			for (int i = 0; i < para.length; i++) {
				query.setParameter(i, para[i]);
			}
			Object obj = query.uniqueResult();
			session.close();
			if (obj instanceof Long) {
				return ((Long) obj).intValue();
			} else if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.selectValue",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return 0;
	}

	@Override
	public int selectPages(String hql, int pageSize) {
		Session session = HibSessionFactory.getSession();
		long pages_all = 0;
		try {
			Query query = session.createQuery(hql);
			List list = query.list();
			// 获取查询记录总数
			long records = list.size();
			// 计算分页
			pages_all = records % pageSize == 0 ? records / pageSize : records
					/ pageSize + 1;// 获取总页数
			session.close();
			return (int) pages_all;
		} catch (Exception e) {
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.selectPages",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return 0;
	}

	@Override
	public int selectPages(String hql, Object[] para, int pageSize) {
		Session session = HibSessionFactory.getSession();
		// 编程思维：先获取查询记录数，再使用算法来计算出分页的页数

		long pages_all = 0;
		try {
			Query query = session.createQuery(hql);
			// 根据para设置参数
			for (int i = 0; i < para.length; i++) {
				query.setParameter(i, para[i]);
			}
			List list = query.list();
			// 获取查询记录总数
			long records = list.size();
			// 计算分页
			pages_all = records % pageSize == 0 ? records / pageSize : records
					/ pageSize + 1;// 获取总页数
			session.close();
			return (int) pages_all;
		} catch (Exception e) {
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.selectPages",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return 0;
	}

	@Override
	public List selectByPage(String hql, int startPage, int pageSize) {
		// 创建连接
		Session session = HibSessionFactory.getSession();
		List pList = null;
		int currentPage;
		try {
			Query query = session.createQuery(hql);
			// 先求出按照pageSize得到的分页页数
			List list = query.list();
			// 获取查询记录总数
			long records = list.size();
			// 计算分页数
			int pages_all = (int) (records % pageSize == 0 ? records / pageSize
					: records / pageSize + 1);
			// 设置类成员当前页面的操作页码
			if (startPage <= 1) {
				currentPage = 1;
			} else if (startPage > pages_all) {
				currentPage = pages_all;
			} else {
				currentPage = startPage;
			}

			Query query2 = session.createQuery(hql);
			query2.setFirstResult((currentPage - 1) * pageSize);// 从第几条记录开始查询
			query2.setMaxResults(pageSize);// 每页显示多少条记录数
			pList = query2.list();
			session.close();
		} catch (Exception e) {
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.selectPages",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return pList;
	}

	@Override
	public List selectByPage(String hql, Object[] para, int startPage,
			int pageSize) {
		Session session = HibSessionFactory.getSession();
		List pList = null;
		int currentPage;
		try {
			Query query = session.createQuery(hql);
			// 设置参数
			for (int i = 0; i < para.length; i++) {
				query.setParameter(i, para[i]);
			}
			// 先求出按照pagesize得到的分页的页数
			List list = query.list();
			// 获取查询记录总数
			long records = list.size();
			// 获得总页数

			int pages_all = (int) (records % pageSize == 0 ? records / pageSize
					: records / pageSize + 1);
			// 设置类成员当前页面的操作页码
			if (startPage <= 1) {
				currentPage = 1;
			} else if (startPage >= pages_all) {
				currentPage = pages_all;
			} else {
				currentPage = startPage;
			}
			Query query2 = session.createQuery(hql);

			// 设置参数
			for (int i = 0; i < para.length; i++) {
				query2.setParameter(i, para[i]);
			}
			query2.setFirstResult((currentPage - 1) * pageSize);// 从第几条记录开始查询
			query2.setMaxResults(pageSize);// 每页记录多少条记录
			pList = query2.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.selectPages",
			// e));//向日志输出error级别的日志信息
			if (session != null)
				session.close();
		}
		return pList;
	}

	@Override
	public Object findById(Class cls, Serializable id) {
		Session session = HibSessionFactory.getSession();
		// log.debug("------根据id查询用户信息-----");//向日志输出debug级别的日志信息
		try {
			Object obj = session.get(cls, id);
			session.close();
			// log.debug("------根据id查询用户信息成功-----");//向日志输出debug级别的日志信息

			return obj;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			// log.error("------根据id查询用户信息失败-----", e);
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.findById",
			// e));//向日志输出error级别的日志信息

			e.printStackTrace();
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public boolean update(String sql) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 将会话session对象转换为jdbc的connection
			Connection con = session.connection();
			PreparedStatement ptmt = con.prepareStatement(sql);
			int row = ptmt.executeUpdate();
			tx.commit();// 持久化操作
			session.close();
			if (row > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.update",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean update(String sql, Object[] para) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 将会话session对象转换为jdbc的connection
			Connection con = session.connection();
			PreparedStatement ptmt = con.prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < para.length; i++) {
				ptmt.setObject(i + 1, para[i]);
			}
			int row = ptmt.executeUpdate();
			tx.commit();// 持久化操作
			session.close();
			if (row > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.update",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean delete(String sql) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 将会话session对象转换为jdbc的connection
			Connection con = session.connection();
			PreparedStatement ptmt = con.prepareStatement(sql);

			int row = ptmt.executeUpdate();
			tx.commit();// 持久化操作
			session.close();
			if (row > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.error(LogUtil.error("Basic.iHibBaseDAOImpl.delete",
			// e));//向日志输出error级别的日志信息
			e.printStackTrace();

			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean delete(String sql, Object[] para) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 将会话session对象转换为jdbc的connection
			Connection con = session.connection();
			PreparedStatement ptmt = con.prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < para.length; i++) {
				ptmt.setObject(i + 1, para[i]);
			}
			int row = ptmt.executeUpdate();
			tx.commit();// 持久化操作
			session.close();
			if (row > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public Object executeProduce(String procName) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 将会话session对象转换为jdbc的connection
			Connection con = session.connection();
			CallableStatement ctmt = con.prepareCall("{? = call " + procName
					+ "}");
			ctmt.registerOutParameter(1, java.sql.Types.INTEGER);
			boolean type = ctmt.execute();
			tx.commit();
			if (type)// 为true 表明存储过程是一个select语句
			{
				ResultSet rs = ctmt.getResultSet();// 获得返回值
				return rs;
			} else // 不是select 语句，则获取返回值
			{
				int isSuccess = ctmt.getInt(1);// 获取返回值
				session.close();
				return new Integer(isSuccess);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public Object executeProduce(String procName, Object[] para) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			// 将会话session对象转换为jdbc的connection
			Connection con = session.connection();
			CallableStatement ctmt = con.prepareCall("{? = call " + procName
					+ "}");
			ctmt.registerOutParameter(1, java.sql.Types.INTEGER);
			for (int i = 0; i < para.length; i++) {
				ctmt.setObject(i + 2, para[i]);
			}
			boolean type = ctmt.execute();
			tx.commit();
			if (type)// 为true 表明存储过程是一个select语句
			{
				ResultSet rs = ctmt.getResultSet();// 获得返回值
				return rs;
			} else // 不是select 语句，则获取返回值
			{
				int isSuccess = ctmt.getInt(1);// 获取返回值
				session.close();
				return new Integer(isSuccess);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return null;
	}

	@Override
	public boolean executeBatch(Object[] obj, int[] model) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			for (int i = 0; i < obj.length; i++) {
				if (model[i] == INSERT)
					session.save(obj[i]);
				else if (model[i] == UPDATE)
					session.update(obj[i]);
				else if (model[i] == DELETE)
					session.delete(obj[i]);
			}
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public boolean executeBatch(List<Object> list, List<Integer> models) {
		Session session = HibSessionFactory.getSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();// 开始事务
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Integer model = (Integer) models.get(i);

				if (model.intValue() == INSERT)
					session.save(obj);
				else if (model.intValue() == UPDATE)
					session.update(obj);
				else if (model.intValue() == DELETE)
					session.delete(obj);
			}
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (tx != null)
				tx.rollback();// 撤销
			if (session != null)
				session.close();
		}
		return false;
	}

	@Override
	public List selectBysql(String sql) {
		Session session = HibSessionFactory.getSession();

		// 将会话session对象转换为jdbc的connection
		Connection con = session.connection();
		PreparedStatement ptmt;
		try {
			ptmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ptmt.executeQuery();
			// 转list
			List list = new ArrayList();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount(); // Map rowData;
			while (rs.next()) { // rowData = new HashMap(columnCount);
				Map rowData = new HashMap();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getModelName(String sqlstr) {
		Session session = HibSessionFactory.getSession();
		List<String> list = new ArrayList<String>();
		// 将会话session对象转换为jdbc的connection
		Connection con = session.connection();

		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = sqlstr;
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取字段名

			// String sName = rsmd.getColumnName(1);
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				// System.out.println(rsmd.getColumnName(i));
				list.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 获取权限菜单
	 * 
	 * @param roleid
	 *            角色id
	 * @return
	 */
	public static List<layuitree> getlist(Integer roleid) {
		iHibBaseDAOImpl bdao = new iHibBaseDAOImpl();

		List<layuitree> tree = new ArrayList<layuitree>();

		// 获取一级菜单
		List<VRoleSystemModel> sysmodel = (List<VRoleSystemModel>) bdao
				.select("from VRoleSystemModel where roleid=" + roleid
						+ " and deepth=1 and isdelete=False");
		for (VRoleSystemModel sys : sysmodel) {
			layuitree leve1 = new layuitree();
			leve1.setId(sys.getSysid());
			leve1.setName(sys.getChinesename());
			leve1.setType("other");

			// 获取二级菜单
			List<VRoleSystemModel> sysmodel2 = (List<VRoleSystemModel>) bdao
					.select("from VRoleSystemModel where roleid=" + roleid
							+ " and parentid=" + sys.getSysid()
							+ " and isdelete=False");
			List<layuitree> tree2 = new ArrayList<layuitree>();

			for (VRoleSystemModel sys2 : sysmodel2) {
				layuitree leve2 = new layuitree();
				leve2.setId(sys2.getSysid());
				leve2.setName(sys2.getChinesename());
				leve2.setType("other");

				// // 获取二级菜单的按钮
				//
				// // 获取二级菜单的列
				// List<VRoleClumn> clumns = (List<VRoleClumn>) bdao
				// .select("from VRoleClumn where sysModleId="
				// + sys2.getSysid() + " and  roleid=" + roleid);

				List<layuitree> tree3 = new ArrayList<layuitree>();
				for (int i = 0; i <= 1; i++) {
					if (i == 0) {
						layuitree btn = new layuitree();
						btn.setId(1);
						btn.setName("按钮");
						btn.setType("other");
						List<VRoleButton> buttons = (List<VRoleButton>) bdao
								.select("from VRoleButton where sysModelId="
										+ sys2.getSysid() + " and  roleid="
										+ roleid);
						if (buttons.size() != 0) {
							List<layuitree> tree4 = new ArrayList<layuitree>();
							for (VRoleButton vRoleButton : buttons) {
								layuitree button = new layuitree();
								button.setId(vRoleButton.getBtnId());
								button.setName(vRoleButton.getBtnname());
								button.setType("button");
								button.setRelationshipid(vRoleButton.getId());
								if (vRoleButton.getIsedit()) {
									button.setSpread(true);
								}
								tree4.add(button);
							}

							btn.setChildren(tree4);
							tree3.add(btn);
						}

					} else {
						layuitree clumn = new layuitree();
						clumn.setId(2);
						clumn.setName("列名");
						clumn.setType("other");
						List<VRoleClumn> clumns = (List<VRoleClumn>) bdao
								.select("from VRoleClumn where sysModleId="
										+ sys2.getSysid() + " and  roleid="
										+ roleid);
						if (clumns.size() != 0) {
							List<layuitree> tree4 = new ArrayList<layuitree>();
							for (VRoleClumn vRoleClumn : clumns) {
								layuitree clumn1 = new layuitree();
								clumn1.setId(vRoleClumn.getClumnId());
								clumn1.setName(vRoleClumn.getClumnName());
								clumn1.setType("clumn");
								clumn1.setRelationshipid(vRoleClumn.getId());
								if (vRoleClumn.getIsedit()) {
									clumn1.setSpread(true);
								}
								tree4.add(clumn1);
							}
							clumn.setChildren(tree4);
							tree3.add(clumn);
						}
					}
				}

				leve2.setChildren(tree3);
				tree2.add(leve2);

			}
			leve1.setChildren(tree2);
			tree.add(leve1);

		}

		return tree;
	}

	public static void main(String args[]) {
		layuitree laytree = new layuitree();
		laytree.setId(1);
		laytree.setSpread(true);
		laytree.setName("cesss");
		laytree.setType("other");
		String dddString = JSON.toJSONString(laytree);
		String dddd = "[{'id':1,'name':'cesss','type':'other'},{'id':1,'name':'aaaa','type':'other'}]";
		List<layuitree> treebtns = JSON.parseArray(dddd, layuitree.class);
		for (layuitree layuitree : treebtns) {
			System.out.println(layuitree.getName());
		}

	}

	public static layuitree findChildren(layuitree menu,
			List<layuitree> menuList) {
		for (layuitree layuitree : menuList) {
			if (menu.getId().equals(layuitree.getId())) {
				if (menu.getChildren() == null) {
					menu.setChildren(new ArrayList<layuitree>());
				}
				menu.getChildren().add(findChildren(layuitree, menuList));
			}
		}
		return menu;
	}
}
