package sbgl3.yunda.entry;


/**
 * <li>标题: ydframe应用开发平台
 * <li>说明: 该类描述where语句查询条件，用于单表动态查询的条件包装
 * <li>创建人：刘晓斌
 * <li>创建日期：2012-12-10
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 刘晓斌
 * @version 1.0
 */
public class Condition {
	/** 对应SQL的"field=value"表达式,如：Expression.eq("name","zx"); */
	public static final int EQ = 1;
	public static final String EQ_STR = "=";
	/** 方法的参数为一个Map类型对象，包含多个名/值对对应关系，相当于多个Expression.eq的叠加 */
	public static final int ALL_EQ = 2;
	public static final String ALL_EQ_STR = "==";
	/** 对应SQL的“field>value”表达式 */
	public static final int GT = 3;
	public static final String GT_STR = ">";
	/** 对应SQL的“field>=value”表达式 */
	public static final int GE = 4;
	public static final String GE_STR = ">=";
	/** 对应SQL的“field<value”表达式 */
	public static final int LT = 5;
	public static final String LT_STR = "<";
	/** 对应SQL的“field<=value”表达式 */
	public static final int LE = 6;
	public static final String LE_STR = "<=";
	/** 对应SQL语句的between表达式 */
	public static final int BETWEEN = 7;
	public static final String BETWEEN_STR = "between";
	/** 对应SQL语句的”field like value”表达式 */
	public static final int LIKE = 8;
	public static final String LIKE_STR = "like";
	/** 对应SQL语句的“field in(……)”表达式 */
	public static final int IN = 9;
	public static final String IN_STR = "in";
	/** 用于比较两个属性值，对应”field=field”SQL表达式 */
	public static final int EQ_PROPERTY = 10;
	public static final String EQ_PROPERTY_STR = "prop=";
	/** 用于比较两个属性值，对应”field>field”SQL表达式 */
	public static final int GT_PROPERTY = 11;
	public static final String GT_PROPERTY_STR = "prop>";
	/** 用于比较两个属性值，对应”field>=field”SQL表达式 */
	public static final int GE_PROPERTY = 12;
	public static final String GE_PROPERTY_STR = "prop>=";
	/** 用于比较两个属性值，对应”field<field”SQL表达式 */
	public static final int LT_PROPERTY = 13;
	public static final String LT_PROPERTY_STR = "prop<";
	/** 用于比较两个属性值，对应”field<=field”SQL表达式 */
	public static final int LE_PROPERTY = 14;
	public static final String LE_PROPERTY_STR = "prop<=";
	/** 对应SQL的“not”表达式 逻辑非操作，相当java中的!逻辑操作 */
	public static final int NOT = 15;
	public static final String NOT_STR = "not";
	/** 对应SQL的“pk=value”表达式 主键等于某个值*/
	public static final int PK_EQ = 16;
	public static final String PK_EQ_STR = "pk=";
	/** 具体不详，备用，待探讨 */
	public static final int ILIKE = 17;
	/** 对应SQL的“field为空白”表达式，待探讨 */
	public static final int IS_EMPTY = 18;
	/** 对应SQL的“field非空白”表达式，待探讨 */
	public static final int IS_NOT_EMPTY = 19;
	/** 对应SQL的“field is not null”表达式 字段不为空*/
	public static final int IS_NOT_NULL = 20;
	public static final String IS_NOT_NULL_STR = "is not null";
	/** 对应SQL的“field is null”表达式 字段为空*/
	public static final int IS_NULL = 21;
	public static final String IS_NULL_STR = "is null";
	/** 对应SQL的“filed!=value”表达式 字段不等于某值 */
	public static final int NE = 22;
	public static final String NE_STR = "!=";
	/** 对应SQL的“field1!=field2”表达式 字段1不等于字段2*/
	public static final int NE_PROPERTY = 23;
	public static final String NE_PROPERTY_STR = "prop!=";
	/** 具体不详，备用，待探讨*/
	public static final int SIZE_EQ = 24;
	/** 具体不详，备用，待探讨*/
	public static final int SIZE_GE = 25;
	/** 具体不详，备用，待探讨*/
	public static final int SIZE_GT = 26;
	/** 具体不详，备用，待探讨*/
	public static final int SIZE_LE = 27;
	/** 具体不详，备用，待探讨*/
	public static final int SIZE_LT = 28;
	/** 具体不详，备用，待探讨*/
	public static final int SIZE_NE = 29;	
	/**
	 *作为补充这个方法提供了原生SQL语句查询的支持，在执行时直接通过原生SQL语句进行限定，
	 *如：Expression.sql(“lower({alias}.name) like (?)”,“zhao%”,Hibernate.STRING) ;
	 *在运行时{ alias }将会由当前查询所关联的实体类名替换，()中的?将会由”zhao%”替换，并且类型由Hibernate.STRING指定。
	 */
	public static final int SQL = 30;
	public static final String SQL_STR = "sql";
	/** 
	 * 在执行时直接通过原生SQL语句进行限定，对应Expression.sqlRestriction(String arg0, Object arg1, Type arg2)操作 
	 * 当Condition.compare == SQL_PARAM时，将根据sql，propValues和valueTypes构造原生sql语句查询条件
	 */
	public static final int SQL_PARAM = 31;
	public static final String SQL_PARAM_STR = "sqlParam";
	/** 
	 * 在执行时直接通过原生SQL语句进行限定，对应Expression.sqlRestriction(String arg0, Object[] arg1, Type[] arg2)操作 
	 * 当Condition.compare == SQL_PARAM时，将根据sql，propValues和valueTypes构造原生sql语句查询条件
	 */	
	public static final int SQL_PARAMS = 32;
	public static final String SQL_PARAMS_STR = "sqlParams";
	/**
	 * 对应SQL语句的and关系组合，expression1 and expression2如：Expression.and(Expression.eq(“name”,”zx”),Expression.eq(“sex”,”1”));
	 * 当Condition.compare == AND时，将从conditionList取得第一个和第二个condition的expression进行组装
	 */
	public static final int AND = 41;
	public static final String AND_STR = "and";
	/**
	 * 对应SQL语句的or关系组合，expression1 or expression2如：Expression.or(Expression.eq(“name”,”zx”),Expression.eq(“name”,”zhaoxin”));
	 * 当Condition.compare == OR时，将从conditionList取得第一个和第二个condition的expression进行组装
	 */
	public static final int OR = 42;
	public static final String OR_STR = "or";
	/** 对应SQL语句的and关系组合，如：(expression1 and expression2 and expression3 and ...) 
	 * 当Condition.compare == CONJUNCTION时，将遍历conditionList取得每个expression进行组装
	 */
	public static final int CONJUNCTION = 43;
	public static final String CONJUNCTION_STR = "AND";
	/** 对应SQL语句的or关系组合，如：(expression1 or expression2 or expression3 or ...) 
	 * 当Condition.compare == DISJUNCTION时，将遍历conditionList取得每个expression进行组装
	 */
	public static final int DISJUNCTION = 44;
	public static final String DISJUNCTION_STR = "OR";
}
