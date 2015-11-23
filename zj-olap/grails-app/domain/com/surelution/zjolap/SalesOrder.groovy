package com.surelution.zjolap

/**
 * 销售台账明细
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
class SalesOrder {

	static String OPTION_VALUE_EXCEL_ADD = "EXCEL_ADD"
	static String OPTION_VALUE_ADD = "ADD"
	static String OPTION_VALUE_DELETE = "DELETE"
	static String OPTION_VALUE_UPDATE = "UPDATE"
    static constraints = {
		upload nullable:true
		sales nullable:true
		excelRowIndex nullable:true
		updateFrom nullable:true
		isClosed default:false
	}

    String orderFormNo
    Branch branch
    Sales sales
    Customer customer
    SalingType salingtype
    Date salingAt
    GasType gasType
	FinancialMonth month
	
	CustomerBranch customerBranch

    /**
     * 日期统计
     */
    TimeByDay timeByDay
    
    /**
     * 数量
     */
    Double quantity
    /**
     * 销售单价
     */
    Double purchasingUnitPrice
    
    /**
     * 批发到位单价
     */
    Double listUnitPrice
    
    /**
     * 销售收入
     */
    Double purchasingPrice

    /**
     * 价格到位率
     */
    Double purchasingPriceRate
	
	/**
	 * 结算价格
	 */
	Double countPrice
	
	/**
	 * 所属区域零售价格
	 */
	Double retailPrice
	
	SalesOrderUpload upload
	Integer excelRowIndex
	
	/**
	 * 是否可见
	 */
	Boolean isVail
	
	/**
	 * update delete add
	 */
	String optionValue 
	
	
	String status
	
	
	Long updateFrom
	
	/**
	 * 是否关闭（结算）
	 */
	boolean isClosed
	
	BigCase bigCase
	
	final static	String STATUS_ADD ='ADD'
	final static	String STATUS_DELETE = 'DELETE'
	final static	String STATUS_UPDATE ='UPDATE'
	final static  String STATUS_ABLE = 'ABLE'
	final static  String STATUS_DISABLE = 'DISABLE'
	
	
	def getStatusName() {
		if(status == STATUS_ADD) {
			return "新增待审批";
		}else if(status == STATUS_DELETE) {
			return "删除待审批";
		}else if(status == STATUS_UPDATE) {
			return "修改待审批";
		}else if(status == STATUS_ABLE) {
			return "可用";
		}else if(status == STATUS_DISABLE) {
			return "审批驳回";
		}
	}
}

