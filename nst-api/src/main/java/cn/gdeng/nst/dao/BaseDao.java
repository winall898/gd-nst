package cn.gdeng.nst.dao;

import java.util.List;
import java.util.Map;

import com.gudeng.framework.page.QueryParam;
import com.gudeng.framework.page.QueryResult;

/**
 * 功能描述： baseDao
 * 
 * @param <T>
 *            操作泛型
 */

public interface BaseDao<T> {

	/**
	 * 保存接口调用错误MqError
	 * @param memberId  用户ID 
	 * @param content   推送内容json
	 * @param topic     0 会员同步 1 货源分配 2 会员统计 3 支付流水 4 定时任务 5 APP消息推送
	 * @param bizType   收发类型 0 消费者 1 生产者
	 * @param httpUrl   请求Url
	 * @param remark    备注
	 * @return
	 */
	Number insertMqError(Integer memberId, T content,Integer topic,  Integer bizType,String httpUrl,
			 String remark);
	/**
	 * 
	 * 功能描述:以下是实现对T表插入数据的功能
	 * 
	 * @param 将一行表数据存在T中
	 *            ，作为参数
	 * @return 执行结果，类型 是Boolean
	 */
	Number persist(T t);

	/**
	 * 根据传入主键类型requiredType，返回主键值<br>
	 * 非自增长主键，如果传入类型与主键类型不符，会抛出ClassCastException<br>
	 * 自增长主键，必须传入类型为Number类型
	 */
	<E> E persist(Object entity, Class<E> requiredType);

	/**
	 * 
	 * 功能描述：以下是实现数据的更新功能，根据T表中id，对那一行的数据进行更新
	 * 
	 * @param 将那一行数据存在T中
	 *            （其中有更新内容）作为参数
	 * @return 执行结果，类型 是Boolean
	 */
	int merge(T t);

	/** 动态更新 */
	int dynamicMerge(Object entity);

	/**
	 * //成功返回主键
	 * 
	 * @param t
	 * @return
	 */
	int remove(T t);

	<E> E find(Class<E> entityClass, Object entity);

	/**
	 * //成功返回主键
	 * 
	 * @param t
	 * @return
	 */
	T find(T t);

	/** 根据sqlId查询单个对象，返回requiredType类型对象，不需要强转，查不到或查询多个返回第一个 */
	<E> E queryForObject(String sqlId, Map<String, ?> paramMap, Class<E> requiredType);

	/** 根据sqlId查询单个对象，返回requiredType类型对象，不需要强转，查不到或查询多个返回第一个 */
	<E> E queryForObject(String sqlId, Object param, Class<E> requiredType);

	/** 根据sqlId查询单个对象，返回Map集合，key是数据库字段 ，查不到或查询多个返回第一个 */
	Map<String, Object> queryForMap(String sqlId, Map<String, ?> paramMap);

	/** 根据sqlId查询单个对象，返回Map集合，key是数据库字段 ，查不到或查询多个返回第一个 */
	Map<String, Object> queryForMap(String sqlId, Object param);

	/** 根据sqlId查询多个对象，返回requiredType类型对象List集合，不需要强转 */
	<E> List<E> queryForList(String sqlId, Map<String, ?> paramMap, Class<E> elementType);

	/** 根据sqlId查询多个对象，返回requiredType类型对象List集合，不需要强转 */
	<E> List<E> queryForList(String sqlId, Object param, Class<E> requiredType);

	/** 根据sqlId查询，返回Map集合List，key是数据库字段 */
	List<Map<String, Object>> queryForList(String sqlId, Map<String, ?> paramMap);

	/** 根据sqlId查询，返回Map集合List，key是数据库字段 */
	List<Map<String, Object>> queryForList(String sqlId, Object param);

	/** 根据sqlId执行，返回执行成功的记录条数 */
	int execute(String sqlId, Map<String, ?> paramMap);

	/** 根据sqlId执行，返回执行成功的记录条数 */
	int execute(String sqlId, Object param);

	/** 根据sqlId执行，批量执行 */
	int[] batchUpdate(String sqlId, Map<String, Object>[] batchValues);

	/**
	 * 功能描述: 分页查询
	 * 
	 * @param param
	 *            ：查询参数
	 * @return 查询结果集
	 */
	<E> QueryResult<E> pageQuery(String countSqlId, String pageSqlId, QueryParam<Map<String, Object>> param, Class<E> requiredType);

}
