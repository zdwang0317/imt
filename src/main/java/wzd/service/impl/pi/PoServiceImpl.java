package wzd.service.impl.pi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wzd.dao.IBaseDao;
import wzd.model.pi.ToptionContent;
import wzd.model.pi.TturnkeyDetail;
import wzd.model.pi.TturnkeyOrder;
import wzd.model.pi.TturnkeyOrderChangeRecord;
import wzd.model.pi.TturnkeyOrderItem;
import wzd.model.pi.TturnkeyOrderItemDetail;
import wzd.model.pi.Twip;
import wzd.model.pi.TwipDetail;
import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.OptionContent;
import wzd.pageModel.pi.TpnTestFLow;
import wzd.pageModel.pi.TurnkeyOrder;
import wzd.pageModel.pi.TurnkeyOrderChangeRecord;
import wzd.pageModel.pi.TurnkeyOrderItem;
import wzd.service.IPoService;
import wzd.util.ConnUtil;
import wzd.util.DateUtil;
import wzd.util.PiUtil;
import wzd.util.UtilValidate;
import wzd.util.WaferIdFormat;
import wzd.util.WebServiceUtil;
@Service("poService")
public class PoServiceImpl implements IPoService {
	
	private static final Logger logger = Logger.getLogger(PoServiceImpl.class);
	
	private IBaseDao<Twip> wipDao;
	private IBaseDao<TwipDetail> wipDetailDao;
	private IBaseDao<TturnkeyOrder> turnkeyOrder;
	private IBaseDao<TturnkeyOrderItem> turnkeyOrderItem;
	private IBaseDao<TturnkeyOrderItemDetail> turnkeyOrderItemDetail;
	private IBaseDao<TturnkeyOrderChangeRecord> turnkeyOrderChangeRecord;
	private IBaseDao<ToptionContent> optionContentDao;
	private IBaseDao<TturnkeyDetail> turnkeyDetailDao;
	
	@Autowired
	public void setTurnkeyOrderChangeRecord(
			IBaseDao<TturnkeyOrderChangeRecord> turnkeyOrderChangeRecord) {
		this.turnkeyOrderChangeRecord = turnkeyOrderChangeRecord;
	}
	@Autowired
	public void setTurnkeyDetailDao(IBaseDao<TturnkeyDetail> turnkeyDetailDao) {
		this.turnkeyDetailDao = turnkeyDetailDao;
	}
	@Autowired
	public void setOptionContentDao(IBaseDao<ToptionContent> optionContentDao) {
		this.optionContentDao = optionContentDao;
	}
	@Autowired
	public void setWipDao(IBaseDao<Twip> wipDao) {
		this.wipDao = wipDao;
	}
	@Autowired
	public void setWipDetailDao(IBaseDao<TwipDetail> wipDetailDao) {
		this.wipDetailDao = wipDetailDao;
	}
	@Autowired
	public void setTurnkeyOrder(IBaseDao<TturnkeyOrder> turnkeyOrder) {
		this.turnkeyOrder = turnkeyOrder;
	}
	@Autowired
	public void setTurnkeyOrderItem(IBaseDao<TturnkeyOrderItem> turnkeyOrderItem) {
		this.turnkeyOrderItem = turnkeyOrderItem;
	}
	@Autowired
	public void setTurnkeyOrderItemDetail(IBaseDao<TturnkeyOrderItemDetail> turnkeyOrderItemDetail) {
		this.turnkeyOrderItemDetail = turnkeyOrderItemDetail;
	}
	//Have been deprecated
	@Override
	public String createProductOrder(OptionContent option) {
		// TODO Auto-generated method stub
		String serialNumber = "PO"+DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		String ipn = option.getIpn_one()+option.getIpn_ipn().trim()+option.getIpn_three()+option.getIpn_four()+option.getIpn_five()+option.getIpn_six()+option.getIpn_seven();
		int id = save(serialNumber,ipn,option.getCpn_name(),option.getCreatedUserName(),option.getFabSite(),null);
		List<TturnkeyDetail> listOfTurnkeyDetail = getListFromIds(option.getIpn_ids(),option.getCancel_ids());
		int seqId = 1;
		List<ToptionContent> listOfOptionContent = optionContentDao.find("from ToptionContent t where t.name='"+option.getIpn_six()+"' and t.type like 'IPN_%'");
		String cpTestFlow = listOfOptionContent.get(0).getDescription();
		String cpSite = null;
		String ipn_five = option.getIpn_five();
		if(ipn_five.equals("A")||ipn_five.equals("B")||ipn_five.equals("C")){
			cpSite = "KLT";
		}else if(ipn_five.equals("D")||ipn_five.equals("E")){
			cpSite = "SH";
		}else if(ipn_five.equals("F")){
			cpSite = "XMC";
		}else if(ipn_five.equals("G")){
			cpSite = "CHIPMOS";
		}
		
		createProductOrder(ipn,cpTestFlow, cpSite, id, listOfTurnkeyDetail, seqId, option);
		//把turnkeyDetail放入map  lot id相同一条
		/*Map<String,List<TturnkeyDetail>> mapOfCenter = new HashMap<String,List<TturnkeyDetail>>();
		for(TturnkeyDetail p:listOfTurnkeyDetail){
			List<TturnkeyDetail> sublist = mapOfCenter.get(p.getLid());
			if(UtilValidate.isNotEmpty(sublist)){
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}else{
				sublist = new ArrayList<TturnkeyDetail>();
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}
		}
		//生成trunkey item
		for(Map.Entry<String,List<TturnkeyDetail>> entry: mapOfCenter.entrySet()) {
			TturnkeyOrderItem orderItem = new TturnkeyOrderItem();
			orderItem.setId(id);
			orderItem.setSeqId(seqId);
			String ipn = entry.getValue().get(0).getIpn();
			orderItem.setIpn(ipn);
			if(UtilValidate.isEmpty(option.getIpn_ipn())){
				orderItem.setIpn_new(option.getIpn_one()+ipn+option.getIpn_three()+option.getIpn_four()+option.getIpn_five()+option.getIpn_six()+option.getIpn_seven());
			}else{
				orderItem.setIpn_new(option.getIpn_one()+option.getIpn_ipn()+option.getIpn_three()+option.getIpn_four()+option.getIpn_five()+option.getIpn_six()+option.getIpn_seven());
			}
			orderItem.setPn_new(option.getProd_name());
			orderItem.setCpn_new(option.getCpn_name());
			orderItem.setLid(entry.getValue().get(0).getLid());
			orderItem.setQty(entry.getValue().size());
			orderItem.setCpTestFlow(cpTestFlow);
			orderItem.setCpSite(cpSite);
			List<String> widlist = new ArrayList<String>();
			for(TturnkeyDetail t:entry.getValue()){
				widlist.add(t.getWid());
			}
			orderItem.setWid(PiUtil.getWidFromList(widlist));
			turnkeyOrderItem.save(orderItem);
			//生成turnkey item detail
			for(TturnkeyDetail t:entry.getValue()){
				TturnkeyOrderItemDetail obj = new TturnkeyOrderItemDetail();
				obj.setId(id);
				obj.setSeqId(seqId);
				obj.setLid(t.getLid());
				obj.setWid(t.getWid());
				obj.setFid(t.getId());
				obj.setStatus("CREATED");
				turnkeyOrderItemDetail.save(obj);
			}
			seqId++;
		}*/
		updateStatusForTurnkeyDetail(listOfTurnkeyDetail);
		return serialNumber;
	}
	
	private int updateStatusForTurnkeyDetail(List<TturnkeyDetail> listOfTurnkeyDetail) {
		// TODO Auto-generated method stub
		int returnNum =0;
		ConnUtil connUtil = new ConnUtil();
		PreparedStatement pst = null;
		Connection conn = connUtil.getMysqlConnection();
		StringBuilder strBur2 = new StringBuilder();
		for(TturnkeyDetail p:listOfTurnkeyDetail){
			strBur2.append(",'"+p.getId_()+"'");
		}
		String sql = "update zz_turnkey_detail set status='COMPLETED' where id_ in ("+strBur2.substring(1)+")";;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			returnNum = pst.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
		return returnNum;
	}
	private int save(String serialNumber,String ipn,String cpn,String createdName,String fabSite,String tpn) {
		TturnkeyOrder u = new TturnkeyOrder();
		u.setSerialNumber(serialNumber);
		u.setIpn(ipn);
		u.setCpn(cpn);
		u.setFabSite(fabSite);
//		u.setType("TURNKEY");
		u.setStatus("CREATED");
		u.setCreatedUserName(createdName);
		if(UtilValidate.isNotEmpty(tpn)){
			u.setTpn(tpn);
		}
		turnkeyOrder.save(u);
		return u.getId();
	}
	/*
	 * 获得添加的wafer list  调用此方法的需synchronized 以防多人同时对同lot 下的同wafer 进行操作
	 */
	private List<TturnkeyDetail> getListFromIds(String lot_ids,String cancel_ids){
		StringBuilder strBur = new StringBuilder();
		for(String str:lot_ids.split(",")){
			strBur.append(",'"+str+"'");
		}
		StringBuilder strBur2 = new StringBuilder();
		for(String str:cancel_ids.split(",")){
			strBur2.append(",'"+str+"'");
		}
		String hql = "from TturnkeyDetail t where 1=1 and status='CREATED' and lid in("+strBur.substring(1)+") and t.id_ not in("+strBur2.substring(1)+")";
		return turnkeyDetailDao.find(hql);
	}
	
	/*
	 * 判断工单IPN有TPN与之对应
	 */
	private String checkIpnHasATpn(String tpn){
		String retrunStr = "";
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		try {
			pst = conn.prepareStatement("select id from ttpn where id like '%"+tpn+"%' and status='Active' and isbyOtherFrozen is null");
			rst = pst.executeQuery();
			while(rst.next()){
				retrunStr = rst.getString("id");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		return retrunStr;
	}
	
	/*
	 * 判断工单DETAIL ITEM 是否WAFER TYPE为Q的  如果有则无法建立工单并返回结果
	 */
	private String getStatusOfWaferType(List<TturnkeyDetail> listOfTurnkeyDetail,String ipnOne){
		String returnStr = "";
		//验证WAFER TYPE 是否为Q
		Set<String> setOfLotId = new HashSet<String>();
		for(TturnkeyDetail t:listOfTurnkeyDetail){
			setOfLotId.add(t.getParent_lid());
		}
		StringBuilder strBur = new StringBuilder();
		for(String str:setOfLotId){
			strBur.append(",'"+str+"'");
		}
		ConnUtil connUtil = new ConnUtil();
		PreparedStatement pst = null;
		ResultSet rst = null;
		Connection conn = connUtil.getMysqlConnection();
		String sql = "select id,abnormal from t_fabside_wip where lotId in ("+strBur.substring(1)+")";
		try {
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			Map<String,String> mapOfWaferType = new HashMap<String,String>();
			while(rst.next()){
				mapOfWaferType.put(rst.getString("Id"), rst.getString("abnormal"));
			}
			if(mapOfWaferType.size()>0){
				for(TturnkeyDetail t:listOfTurnkeyDetail){
					String waferTypeOfWip = mapOfWaferType.get(t.getParent_lid()+"_"+t.getWid());
					if(UtilValidate.isNotEmpty(waferTypeOfWip)){
						String type = String.valueOf(waferTypeOfWip.charAt(waferTypeOfWip.length()-1));
						if(type.equals("Q")){
							returnStr +=(","+t.getParent_lid()+"_"+t.getWid());
						}else if(type.contains("L")||type.contains("N")){
							if("CMA".equals(ipnOne)){
								returnStr +=(","+t.getParent_lid()+"_"+t.getWid()+"_"+type);
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		return returnStr;
	}
	
	private List<TwipDetail> getListFromWipId(String id){
		return wipDetailDao.find("from TwipDetail t where 1=1 and t.pid = "+id+" order by wid");
	}
	
	private int getCountFromWip(int id){
		return Integer.parseInt(String.valueOf(wipDetailDao.count("select count(*) from TwipDetail t where t.pid ="+id)));
	}
	@Override
	public DataGrid datagrid(TurnkeyOrder to) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from TturnkeyOrder t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(to, hql, params);
		String totalHql = "select count(*) " + hql;
		//hql = addOrder(wip, hql);
		hql = hql +" order by t.id desc";
		List<TturnkeyOrder> l = turnkeyOrder.find(hql, params, to.getPage(), to.getRows());
		List<TurnkeyOrder> nl = new ArrayList<TurnkeyOrder>();
		changeModel(l, nl);
		dg.setTotal(turnkeyOrder.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private String addWhere(TurnkeyOrder to, String hql, Map<String, Object> params) {
		if (UtilValidate.isNotEmpty(to.getSerialNumber())) {
			hql += " and t.serialNumber like :serialNumber";
			params.put("serialNumber", "%%" + to.getSerialNumber().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(to.getStatus())) {
			hql += " and t.status = :status";
			params.put("status", to.getStatus());
		}
		if (UtilValidate.isNotEmpty(to.getLid())) {
			List<TturnkeyOrderItem> listOfItem = turnkeyOrderItem.find("from TturnkeyOrderItem t where 1=1 and t.lid like '%"+to.getLid()+"%'");
			if(UtilValidate.isNotEmpty(listOfItem)){
				StringBuilder ids = new StringBuilder();
				for(TturnkeyOrderItem obj:listOfItem){
					ids.append(","+obj.getId());
				}
				hql += " and t.id in ("+ids.substring(1).toString()+")";
			}
		}
		return hql;
	}
	@Override
	public DataGrid datagridItem(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TturnkeyOrderItem t where 1=1 and t.id="+turnkeyOrder.getId();
		List<TturnkeyOrderItem> orderItem = turnkeyOrderItem.find(hql);
		String totalHql = "select count(*) " + hql;
		List<TurnkeyOrderItem> nl = new ArrayList<TurnkeyOrderItem>();
		changeModel2(orderItem, nl);
		dg.setTotal(turnkeyOrderItem.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel(List<TturnkeyOrder> l, List<TurnkeyOrder> nl) {
		if (l != null && l.size() > 0) {
			for (TturnkeyOrder t : l) {
				TurnkeyOrder u = new TurnkeyOrder();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	
	private void changeModel2(List<TturnkeyOrderItem> l, List<TurnkeyOrderItem> nl) {
		if (l != null && l.size() > 0) {
			for (TturnkeyOrderItem t : l) {
				TurnkeyOrderItem u = new TurnkeyOrderItem();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	private void changeModel3(List<TturnkeyOrderChangeRecord> l, List<TurnkeyOrderChangeRecord> nl) {
		if (l != null && l.size() > 0) {
			for (TturnkeyOrderChangeRecord t : l) {
				TurnkeyOrderChangeRecord u = new TurnkeyOrderChangeRecord();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	@Override
	public String deletePoFromId(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		StringBuilder returnStr = new StringBuilder();
		StringBuilder strs = new StringBuilder();
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst  = null;
		try {
			pst = conn.prepareStatement("select fid_ from zz_turnkey_order_itemdetail where id = '"+turnkeyOrder.getId()+"'");
			rst = pst.executeQuery();
			while(rst.next()){
				strs.append(",'"+rst.getString("fid_")+"'");
			}
			conn.setAutoCommit(false);
			if(turnkeyOrder.getStatus().equals("COMPLETED")){
				List<TturnkeyOrderItem> listOfItem = turnkeyOrderItem.find("from TturnkeyOrderItem t where 1=1 and  t.id ='"+turnkeyOrder.getId()+"'");
				TturnkeyOrderItem latestItem = listOfItem.get(0);
				pst = conn.prepareStatement("insert into zz_turnkey_order_change_record(serialNumber,createdDate,lid,wid,ipn,cpn,prod) values(?,?,?,?,?,?,?)");
				List<TturnkeyOrderItemDetail> listOfItemDetail = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where t.id ='"+turnkeyOrder.getId()+"'");
				Map<String,List<String>> checkMap = new HashMap<String,List<String>>();
				for(TturnkeyOrderItemDetail obj: listOfItemDetail){
					String lid = WaferIdFormat.getMainLot(obj.getLid());
					List<String> listOfWid = checkMap.get(lid);
					String wid = obj.getWid();
					if(UtilValidate.isEmpty(listOfWid)){
						listOfWid = new ArrayList<String>();
					}
					listOfWid.add(wid);
					checkMap.put(lid, listOfWid);
				}
				for(Map.Entry<String, List<String>> entry: checkMap.entrySet()) {
					 String str = checkWaferHasPermissionToDelete(entry.getKey(),entry.getValue());
					 if(UtilValidate.isNotEmpty(str)){
						 returnStr.append("lot :"+entry.getKey()+" wafer :" +str);
					 }
				}
				if(UtilValidate.isEmpty(returnStr)){
					for(TturnkeyOrderItemDetail obj: listOfItemDetail){
						pst.setString(1, turnkeyOrder.getSerialNumber());
						Date d = new Date();
						Timestamp time = new Timestamp(d.getTime());
						pst.setTimestamp(2, time);
						pst.setString(3, obj.getLid());
						pst.setString(4, obj.getWid());
						pst.setString(5, latestItem.getIpn_new());
						pst.setString(6, latestItem.getCpn_new());
						pst.setString(7, latestItem.getPn_new());
						pst.addBatch();
					}
					pst.executeBatch();
					pst = conn.prepareStatement("update zz_turnkey_order set status='CANCELED' where id = '"+turnkeyOrder.getId()+"'");
					pst.executeUpdate();
					pst = conn.prepareStatement("update zz_turnkey_detail set status='CREATED' where id_ in("+strs.substring(1)+")");
					pst.executeUpdate();
					conn.commit();
					returnStr.append("删除成功");
				}else{
					returnStr.append("以上wafer状态不符合删除规则");
				}
			}else{
				pst = conn.prepareStatement("update zz_turnkey_order set status='CANCELED' where id = '"+turnkeyOrder.getId()+"'");
				pst.executeUpdate();
				pst = conn.prepareStatement("update zz_turnkey_detail set status='CREATED' where id_ in("+strs.substring(1)+")");
				pst.executeUpdate();
				conn.commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		return returnStr.toString();
	}
	@Override
	public List<String> completedProductOrder(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		List<String> returnList = new ArrayList<String>();
		List<TturnkeyOrderItemDetail> listOfItemDetail = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where t.id='"+turnkeyOrder.getId()+"'");
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("update zz_turnkey_order set status='COMPLETED' where id ='"+turnkeyOrder.getId()+"'");
			pst.executeUpdate();
			/*pst = conn.prepareStatement("insert into zz_turnkey_order_manage(id,status) values(?,?)");
			pst.setInt(1, turnkeyOrder.getId());
			pst.setString(2, "CREATED");
			pst.execute();*/
			pst = conn.prepareStatement("update t_fabside_wip set ipn_real=?,tpnId=? where Id=?");
			for(TturnkeyOrderItemDetail obj:listOfItemDetail){
				pst.setString(1, turnkeyOrder.getIpn());
				String lid = obj.getLid();
				int pointNumber = lid.indexOf(".");
				if(pointNumber>=0){
					lid = lid.substring(0, lid.indexOf("."));
				}
				pst.setString(2, turnkeyOrder.getTpn());
				pst.setString(3, lid+"_"+obj.getWid());
				int num = pst.executeUpdate();
				if(num<=0){
					returnList.add(String.valueOf(obj.getFid()));
				}
			}
			
			StringBuffer strs = new StringBuffer();
			for(TturnkeyOrderItemDetail obj:listOfItemDetail){
				strs.append(",'"+obj.getFid_()+"'");
			}
			pst = conn.prepareStatement("update zz_turnkey_detail set ipn_new ='"+turnkeyOrder.getIpn()+"',tpn='"+turnkeyOrder.getTpn()+"' where id_ in("+strs.toString().substring(1)+")");
			pst.executeUpdate();
			conn.commit();
			/*//发MAIL
			if(UtilValidate.isNotEmpty(returnList)){
				
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
		return returnList;
	}
	@Override
	public void createProductOrderItem(OptionContent optionContent) {
		// TODO Auto-generated method stub
		//获得待添加wafer
		List<TturnkeyDetail> listOfTurnkeyDetail = getListFromIds(optionContent.getIpn_ids(),optionContent.getCancel_ids());
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst  = null;
		int seqId = 1;
		String cpSite = null;
		String cpTestFlow = null;
		String ipn_new = null;
		String cpn_new = null;
		String pn_new = null;
		try {
			pst = conn.prepareStatement("select seqId,cpSite,cpTestFlow,ipn_new,cpn_new,pn_new from zz_turnkey_order_item where id ='"+optionContent.getId()+"' order by seqId desc");
			rst = pst.executeQuery();
			while(rst.next()){
				seqId = seqId+rst.getInt("seqId");
				cpSite = rst.getString("cpSite");
				cpTestFlow = rst.getString("cpTestFlow");
				ipn_new = rst.getString("ipn_new");
				cpn_new = rst.getString("cpn_new");
				pn_new = rst.getString("pn_new");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		updateProductOrder(ipn_new,cpn_new,pn_new,cpTestFlow, cpSite, optionContent.getId(), listOfTurnkeyDetail, seqId);
		updateStatusForTurnkeyDetail(listOfTurnkeyDetail);
		//重构
		/*Map<String,List<TturnkeyDetail>> mapOfCenter = new HashMap<String,List<TturnkeyDetail>>();
		for(TturnkeyDetail p:listOfTurnkeyDetail){
			List<TturnkeyDetail> sublist = mapOfCenter.get(p.getLid());
			if(UtilValidate.isNotEmpty(sublist)){
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}else{
				sublist = new ArrayList<TturnkeyDetail>();
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}
		}*/
		
	}
	
	
	private void createProductOrder(String ipn_new,String cpTestFlow,String cpSite,int id,List<TturnkeyDetail> listOfTurnkeyDetail,int seqId,OptionContent option){
		Map<String,List<TturnkeyDetail>> mapOfCenter = new HashMap<String,List<TturnkeyDetail>>();
		for(TturnkeyDetail p:listOfTurnkeyDetail){
			List<TturnkeyDetail> sublist = mapOfCenter.get(p.getLid());
			if(UtilValidate.isNotEmpty(sublist)){
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}else{
				sublist = new ArrayList<TturnkeyDetail>();
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}
		}
		//生成trunkey item
		for(Map.Entry<String,List<TturnkeyDetail>> entry: mapOfCenter.entrySet()) {
			TturnkeyOrderItem orderItem = new TturnkeyOrderItem();
			orderItem.setId(id);
			orderItem.setSeqId(seqId);
			String ipn = entry.getValue().get(0).getIpn();
			orderItem.setIpn(ipn);
			orderItem.setIpn_new(ipn_new);
			orderItem.setPn_new(option.getProd_name());
			orderItem.setCpn_new(option.getCpn_name());
			orderItem.setLid(entry.getValue().get(0).getLid());
			orderItem.setQty(entry.getValue().size());
			if(String.valueOf(ipn_new.charAt(14)).equals("T")){
				orderItem.setCpTestFlow(cpTestFlow+"+AOI");
			}else{
				orderItem.setCpTestFlow(cpTestFlow);
			}
			orderItem.setCpSite(cpSite);
			List<String> widlist = new ArrayList<String>();
			for(TturnkeyDetail t:entry.getValue()){
				widlist.add(t.getWid());
			}
			orderItem.setWid(PiUtil.getWidFromList(widlist));
			turnkeyOrderItem.save(orderItem);
			//生成turnkey item detail
			for(TturnkeyDetail t:entry.getValue()){
				TturnkeyOrderItemDetail obj = new TturnkeyOrderItemDetail();
				obj.setId(id);
				obj.setSeqId(seqId);
				obj.setLid(t.getLid());
				obj.setWid(t.getWid());
				obj.setFid(t.getId());
				obj.setFid_(t.getParent_lid()+"_"+t.getWid());
				obj.setStatus("CREATED");
				turnkeyOrderItemDetail.save(obj);
			}
			seqId++;
		}
	}
	
	private void updateProductOrder(String ipn_new,String cpn_new,String pn_new,String cpTestFlow,String cpSite,int id,List<TturnkeyDetail> listOfTurnkeyDetail,int seqId){
		Map<String,List<TturnkeyDetail>> mapOfCenter = new HashMap<String,List<TturnkeyDetail>>();
		for(TturnkeyDetail p:listOfTurnkeyDetail){
			List<TturnkeyDetail> sublist = mapOfCenter.get(p.getLid());
			if(UtilValidate.isNotEmpty(sublist)){
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}else{
				sublist = new ArrayList<TturnkeyDetail>();
				sublist.add(p);
				mapOfCenter.put(p.getLid(), sublist);
			}
		}
		//生成trunkey item
		for(Map.Entry<String,List<TturnkeyDetail>> entry: mapOfCenter.entrySet()) {
			TturnkeyOrderItem orderItem = new TturnkeyOrderItem();
			orderItem.setId(id);
			orderItem.setSeqId(seqId);
			String ipn = entry.getValue().get(0).getIpn();
			orderItem.setIpn(ipn);
			orderItem.setIpn_new(ipn_new);
			orderItem.setPn_new(pn_new);
			orderItem.setCpn_new(cpn_new);
			orderItem.setLid(entry.getValue().get(0).getLid());
			orderItem.setQty(entry.getValue().size());
			orderItem.setCpTestFlow(cpTestFlow);
			orderItem.setCpSite(cpSite);
			List<String> widlist = new ArrayList<String>();
			for(TturnkeyDetail t:entry.getValue()){
				widlist.add(t.getWid());
			}
			orderItem.setWid(PiUtil.getWidFromList(widlist));
			turnkeyOrderItem.save(orderItem);
			//生成turnkey item detail
			for(TturnkeyDetail t:entry.getValue()){
				TturnkeyOrderItemDetail obj = new TturnkeyOrderItemDetail();
				obj.setId(id);
				obj.setSeqId(seqId);
				obj.setLid(t.getLid());
				obj.setWid(t.getWid());
				obj.setFid(t.getId());
				obj.setStatus("CREATED");
				turnkeyOrderItemDetail.save(obj);
			}
			seqId++;
		}
	}
	@Override
	public String deletePoItemFromIdAndSeqId(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		StringBuilder returnStr = new StringBuilder();
		List<TturnkeyOrderItemDetail> listOfItemDetail = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where id='"+turnkeyOrder.getId()+"' and seqId = '"+turnkeyOrder.getSeqId()+"'");
		StringBuilder strs = new StringBuilder();
		Map<String,List<String>> checkMap = new HashMap<String,List<String>>();
		for(TturnkeyOrderItemDetail obj: listOfItemDetail){
			String lid = WaferIdFormat.getMainLot(obj.getLid());
			List<String> listOfWid = checkMap.get(lid);
			String wid = obj.getWid();
			if(UtilValidate.isEmpty(listOfWid)){
				listOfWid = new ArrayList<String>();
			}
			listOfWid.add(wid);
			checkMap.put(lid, listOfWid);
		}
		for(Map.Entry<String, List<String>> entry: checkMap.entrySet()) {
			 String str = checkWaferHasPermissionToDelete(entry.getKey(),entry.getValue());
			 if(UtilValidate.isNotEmpty(str)){
				 returnStr.append("lot :"+entry.getKey()+" wafer :" +str);
			 }
		}
		if(UtilValidate.isEmpty(returnStr)){
			for(TturnkeyOrderItemDetail obj: listOfItemDetail){
				strs.append(",'"+obj.getFid_()+"'");
			}
			ConnUtil connUtil = new ConnUtil();
			Connection conn = connUtil.getMysqlConnection();
			PreparedStatement pst = null;
			try {
				conn.setAutoCommit(false);
				if(turnkeyOrder.getStatus().equals("COMPLETED")){
					List<TturnkeyOrderItem> listOfItem = turnkeyOrderItem.find("from TturnkeyOrderItem t where 1=1 and  t.id ='"+turnkeyOrder.getId()+"' and t.seqId ='"+turnkeyOrder.getSeqId()+"'");
					TturnkeyOrderItem latestItem = listOfItem.get(0);
					pst = conn.prepareStatement("insert into zz_turnkey_order_change_record(serialNumber,createdDate,lid,wid,ipn,cpn,prod) values(?,?,?,?,?,?,?)");
					for(TturnkeyOrderItemDetail obj: listOfItemDetail){
						pst.setString(1, turnkeyOrder.getSerialNumber());
						Date d = new Date();
						Timestamp time = new Timestamp(d.getTime());
						pst.setTimestamp(2, time);
						pst.setString(3, obj.getLid());
						pst.setString(4, obj.getWid());
						pst.setString(5, latestItem.getIpn_new());
						pst.setString(6, latestItem.getCpn_new());
						pst.setString(7, latestItem.getPn_new());
						pst.addBatch();
					}
					pst.executeBatch();
				}
				pst = conn.prepareStatement("delete from zz_turnkey_order_itemdetail where id = "+turnkeyOrder.getId()+" and seqId = "+turnkeyOrder.getSeqId());
				pst.executeUpdate();
				pst = conn.prepareStatement("delete from zz_turnkey_order_item where id = "+turnkeyOrder.getId()+" and seqId = "+turnkeyOrder.getSeqId());
				pst.executeUpdate();
				pst = conn.prepareStatement("update zz_turnkey_detail set status='CREATED' where id_ in("+strs.substring(1)+")");
				pst.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnUtil.close(null, pst);
				ConnUtil.closeConn(conn);
			}
			returnStr.append("删除成功!");
		}else{
			returnStr.append("以上wafer状态不符合删除规则");
		}
		return returnStr.toString();
	}
	@Override
	public void cancelProductOrder(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("update zz_turnkey_order set status='CANCELED' where id ='"+turnkeyOrder.getId()+"'");
			pst.executeUpdate();
			pst = conn.prepareStatement("delete from zz_turnkey_order_manage where id ='"+turnkeyOrder.getId()+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
	}
	@Override
	public DataGrid datagridOfTurnkeyDetail(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		List<TturnkeyOrderItemDetail> l = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where 1=1 and  t.id ='"+turnkeyOrder.getId()+"' and t.seqId ='"+turnkeyOrder.getSeqId()+"'");
		DataGrid dg = new DataGrid();	
		dg.setRows(l);
		return dg;
	}
	@Override
	public String deletePoItemDetailsFromFid(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		StringBuilder returnStr = new StringBuilder();
		List<TturnkeyOrderItem> listOfItem = turnkeyOrderItem.find("from TturnkeyOrderItem t where 1=1 and  t.id ='"+turnkeyOrder.getId()+"' and t.seqId ='"+turnkeyOrder.getSeqId()+"'");
		TturnkeyOrderItem toi = null;
		for(TturnkeyOrderItem item:listOfItem){
			toi = item;
		}
		String fids = turnkeyOrder.getFids().substring(1);
		String condOfFid = "";
		for(String str : fids.split(",")){
			condOfFid = condOfFid+",'"+str+"'";
		}
		//判断
		List<String> wids = new ArrayList<String>();
		try {
			pst = conn.prepareStatement("select wid from zz_turnkey_order_itemdetail where fid_ in ("+condOfFid.substring(1)+")");
			rst = pst.executeQuery();
			while(rst.next()){
				wids.add(rst.getString("wid"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = checkWaferHasPermissionToDelete(WaferIdFormat.getMainLot(toi.getLid()),wids);
		if(UtilValidate.isNotEmpty(str)){
			returnStr.append("lot :"+WaferIdFormat.getMainLot(toi.getLid())+" wafer :" +str);
		}
		//判断 结束
		if(UtilValidate.isEmpty(returnStr)){
			try {
				conn.setAutoCommit(false);
				if(turnkeyOrder.getStatus().equals("COMPLETED")){
					TturnkeyOrderItem latestItem = listOfItem.get(0);
					pst = conn.prepareStatement("insert into zz_turnkey_order_change_record(serialNumber,createdDate,lid,wid,ipn,cpn,prod) values(?,?,?,?,?,?,?)");
					List<TturnkeyOrderItemDetail> listOfItemDetail = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where t.fid_ in ("+condOfFid.substring(1)+")");
					for(TturnkeyOrderItemDetail obj: listOfItemDetail){
						pst.setString(1, turnkeyOrder.getSerialNumber());
						Date d = new Date();
						Timestamp time = new Timestamp(d.getTime());
						pst.setTimestamp(2, time);
						pst.setString(3, obj.getLid());
						pst.setString(4, obj.getWid());
						pst.setString(5, latestItem.getIpn_new());
						pst.setString(6, latestItem.getCpn_new());
						pst.setString(7, latestItem.getPn_new());
						pst.addBatch();
					}
					pst.executeBatch();
				}
				pst = conn.prepareStatement("update zz_turnkey_detail set status='CREATED' where id_ in("+condOfFid.substring(1)+")");
				pst.executeUpdate();
				pst = conn.prepareStatement("delete from zz_turnkey_order_itemdetail where fid_ in ("+condOfFid.substring(1)+")");
				pst.executeUpdate();
				pst = conn.prepareStatement("select wid from zz_turnkey_order_itemdetail where id ='"+turnkeyOrder.getId()+"' and seqId ='"+turnkeyOrder.getSeqId()+"'");
				rst = pst.executeQuery();
				List<String> listOfWid = new ArrayList<String>();
				while(rst.next()){
					listOfWid.add(rst.getString("wid"));
				}
				toi.setWid(PiUtil.getWidFromList(listOfWid));
				toi.setQty(listOfWid.size());
				turnkeyOrderItem.save(toi);
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnUtil.close(null, pst);
				ConnUtil.closeConn(conn);
			}
			returnStr.append("删除成功");
		}else{
			returnStr.append("以上wafer状态不符合删除规则");
		}
		return returnStr.toString();
	}
	@Override
	public synchronized void passIpnOfTurnkeyOrderToPi() {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rst = null;
		ResultSet rst2 = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("select id from zz_turnkey_order_manage where status='CREATED'");
			rst = pst.executeQuery();
			StringBuilder strs = new StringBuilder();
			while(rst.next()){
				int id = rst.getInt("id");
				strs.append(","+id);
				logger.info("pass ipn : deal with id is "+id);
				String ipn_new = null;
				pst2 = conn.prepareStatement("select ipn from zz_turnkey_order where id="+id);
				rst2 = pst2.executeQuery();
				while(rst2.next()){
					ipn_new = rst2.getString("ipn");
				}
				if(UtilValidate.isNotEmpty(ipn_new)){
					pst2 = conn.prepareStatement("select lid,wid from zz_turnkey_order_itemdetail where id="+id);
					rst2 = pst2.executeQuery();
					while(rst2.next()){
						pst = conn.prepareStatement("update t_fabside_wip set ipn_real=? where lotid=? and waferid = ?");
						pst.setString(1, ipn_new);
						String lid = rst2.getString("lid");
						String wid = rst2.getString("wid");
						int pointNumber = lid.indexOf(".");
						if(pointNumber>=0){
							lid = lid.substring(0, lid.indexOf("."));
						}
						pst.setString(2, lid);
						pst.setString(3, wid);
						logger.info("pass ipn update : lid is "+lid+" and wid is "+wid);
						int num = pst.executeUpdate();
						if(num<=0){
							logger.info("Pass Ipn To Pi unsuccess is lid_"+lid+" and wid_"+wid+" and ipn is "+ipn_new);
						}
					}
				}
				pst = conn.prepareStatement("update zz_turnkey_order_manage set status='COMPLETED' where id ="+id);
				pst.executeUpdate();
				logger.info("pass ipn commit begin");
				conn.commit();
				logger.info("pass ipn commit end");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.close(rst2, pst2);
			ConnUtil.closeConn(conn);
		}
	}
	@Override
	public String createProductOrderAndValidateIpn(OptionContent option) {
		// TODO Auto-generated method stub
		String serialNumber = "PO"+DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		String ipn = option.getIpn_one()+option.getIpn_ipn().trim()+option.getIpn_three()+option.getIpn_four()+option.getIpn_five()+option.getIpn_six()+option.getIpn_seven();
		String tpn = option.getIpn_ipn().trim()+option.getIpn_three()+option.getIpn_four()+option.getIpn_five()+option.getIpn_six()+option.getIpn_seven();
		int i = 1;
		if(option.getIpn_five().equals("S")){
			i = 1;
		}else{
			tpn = checkIpnHasATpn(tpn);
			if(UtilValidate.isEmpty(tpn)){
				i=0;
			}
		}
		if (i==0){
			return "创建失败：末找到匹配的TPN";
		}else if (i==1){
			List<TturnkeyDetail> listOfTurnkeyDetail = getListFromIds(option.getIpn_ids(),option.getCancel_ids());
			String hasQType = getStatusOfWaferType(listOfTurnkeyDetail,option.getIpn_one());
			if(UtilValidate.isNotEmpty(hasQType)){
				return "创建失败：Wafer Type 规则不符:"+hasQType.substring(1);
			}else{
				int oid = save(serialNumber,ipn,option.getCpn_name(),option.getCreatedUserName(),option.getFabSite(),tpn);
				int seqId = 1;
				List<ToptionContent> listOfOptionContent = optionContentDao.find("from ToptionContent t where t.name='"+option.getIpn_six()+"' and t.type like 'IPN_%'");
				String cpTestFlow = "";
				if(UtilValidate.isNotEmpty(listOfOptionContent)){
					cpTestFlow = listOfOptionContent.get(0).getDescription();
				}
				String cpSite = null;
				String ipn_five = option.getIpn_five();
				List<ToptionContent> listOfOptionContent2 = optionContentDao.find("from ToptionContent t where t.name='"+ipn_five+"' and t.type ='IPN_FIVE'");
				/*if(ipn_five.equals("A")||ipn_five.equals("B")||ipn_five.equals("C")){
					cpSite = "KLT";
				}else if(ipn_five.equals("D")||ipn_five.equals("E")){
					cpSite = "SH";
				}else if(ipn_five.equals("F")){
					cpSite = "XMC";
				}else if(ipn_five.equals("G")){
					cpSite = "CHIPMOS";
				}*/
				if(UtilValidate.isNotEmpty(listOfOptionContent2)){
					String cpTester = listOfOptionContent2.get(0).getDescription();
					cpSite = cpTester.split("-")[1];
				}
				createProductOrder(ipn,cpTestFlow, cpSite, oid, listOfTurnkeyDetail, seqId, option);
				updateStatusForTurnkeyDetail(listOfTurnkeyDetail);
			}
		}
		return "创建成功：工单流水号为"+serialNumber;
	}
	@Override
	public DataGrid datagridTpnTestFlows(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		List<TpnTestFLow> nl = new ArrayList<TpnTestFLow>();
		try {
			pst = conn.prepareStatement("select cp,testProgram,customerID,uniqueID,holdGrade,temperature,time from ttpntestflowstep where tpnId ='"+turnkeyOrder.getTpn()+"'");
			rst = pst.executeQuery();
			while(rst.next()){
				TpnTestFLow ttf = new TpnTestFLow();
				ttf.setCp(rst.getString("cp"));
				ttf.setCustomerId(rst.getString("customerID"));
				ttf.setHoldGrade(rst.getString("holdGrade"));
				ttf.setTemperature(rst.getString("temperature"));
				ttf.setTestProgram(rst.getString("testProgram"));
				ttf.setTime(rst.getString("time"));
				ttf.setUniqueId(rst.getString("uniqueID"));
				nl.add(ttf);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		dg.setRows(nl);
		return dg;
	}
	@Override
	public void deletePoItemFromIdAndSeqIdByChange(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		List<TturnkeyOrderItemDetail> listOfItemDetail = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where id='"+turnkeyOrder.getId()+"' and seqId = '"+turnkeyOrder.getSeqId()+"'");
		StringBuilder strs = new StringBuilder();
		for(TturnkeyOrderItemDetail obj: listOfItemDetail){
			strs.append(",'"+obj.getFid_()+"'");
		}
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from zz_turnkey_order_itemdetail where id = "+turnkeyOrder.getId()+" and seqId = "+turnkeyOrder.getSeqId());
			pst.executeUpdate();
			pst = conn.prepareStatement("delete from zz_turnkey_order_item where id = "+turnkeyOrder.getId()+" and seqId = "+turnkeyOrder.getSeqId());
			pst.executeUpdate();
			pst = conn.prepareStatement("update zz_turnkey_detail set status='CREATED' where id_ in("+strs.substring(1)+")");
			pst.executeUpdate();
			pst = conn.prepareStatement("insert into zz_turnkey_order_change_record(serialNumber,createdDate,lid,wid) values(?,?,?,?)");
			for(TturnkeyOrderItemDetail obj: listOfItemDetail){
				pst.setString(1, turnkeyOrder.getSerialNumber());
				Date d = new Date();
				Timestamp time = new Timestamp(d.getTime());
				pst.setTimestamp(2, time);
				pst.setString(3, obj.getLid());
				pst.setString(4, obj.getWid());
				pst.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
	}
	@Override
	public void deletePoItemDetailsFromFidByChange(TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		List<TturnkeyOrderItem> listOfItem = turnkeyOrderItem.find("from TturnkeyOrderItem t where 1=1 and  t.id ='"+turnkeyOrder.getId()+"' and t.seqId ='"+turnkeyOrder.getSeqId()+"'");
		TturnkeyOrderItem toi = null;
		for(TturnkeyOrderItem item:listOfItem){
			toi = item;
		}
		String fids = turnkeyOrder.getFids().substring(1);
		String condOfFid = "";
		for(String str : fids.split(",")){
			condOfFid = condOfFid+",'"+str+"'";
		}
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("update zz_turnkey_detail set status='CREATED' where id_ in("+condOfFid.substring(1)+")");
			pst.executeUpdate();
			pst = conn.prepareStatement("select lid,wid from zz_turnkey_order_itemdetail where fid_ in ("+condOfFid.substring(1)+")");
			rst = pst.executeQuery();
			pst = conn.prepareStatement("insert into zz_turnkey_order_change_record(serialNumber,createdDate,lid,wid) values(?,?,?,?)");
			while(rst.next()){
				pst.setString(1, turnkeyOrder.getSerialNumber());
				Date d = new Date();
				Timestamp time = new Timestamp(d.getTime());
				pst.setTimestamp(2, time);
				pst.setString(3, rst.getString("lid"));
				pst.setString(4, rst.getString("wid"));
				pst.execute();
			}
			pst = conn.prepareStatement("delete from zz_turnkey_order_itemdetail where fid in ("+fids+")");
			pst.executeUpdate();
			pst = conn.prepareStatement("select wid from zz_turnkey_order_itemdetail where id ='"+turnkeyOrder.getId()+"' and seqId ='"+turnkeyOrder.getSeqId()+"'");
			rst = pst.executeQuery();
			List<String> listOfWid = new ArrayList<String>();
			while(rst.next()){
				listOfWid.add(rst.getString("wid"));
			}
			toi.setWid(PiUtil.getWidFromList(listOfWid));
			toi.setQty(listOfWid.size());
			turnkeyOrderItem.save(toi);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
	}
	@Override
	public DataGrid datagridOfChangeList(TurnkeyOrder to) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from TturnkeyOrderChangeRecord t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhereForChangeList(to, hql, params);
		String totalHql = "select count(*) " + hql;
		//hql = addOrder(wip, hql);
		hql = hql +" order by t.id desc";
		List<TturnkeyOrderChangeRecord> l = turnkeyOrderChangeRecord.find(hql, params, to.getPage(), to.getRows());
		List<TurnkeyOrderChangeRecord> nl = new ArrayList<TurnkeyOrderChangeRecord>();
		changeModel3(l, nl);
		dg.setTotal(turnkeyOrderChangeRecord.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private String addWhereForChangeList(TurnkeyOrder to, String hql, Map<String, Object> params) {
		if (UtilValidate.isNotEmpty(to.getSerialNumber())) {
			hql += " and t.serialNumber like :serialNumber";
			params.put("serialNumber", "%%" + to.getSerialNumber().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(to.getLid())) {
			hql += " and t.lid = :lid";
			params.put("lid", to.getLid());
		}
		return hql;
	}
	@Override
	public Map<String, List<ToptionContent>> passOptionsByStr(String ipn_ipn) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		List<ToptionContent> k3 = new ArrayList<ToptionContent>();
		List<ToptionContent> k4 = new ArrayList<ToptionContent>();
		List<ToptionContent> k5 = new ArrayList<ToptionContent>();
		List<ToptionContent> k6 = new ArrayList<ToptionContent>();
		List<ToptionContent> k7 = new ArrayList<ToptionContent>();
		List<ToptionContent> pl = new ArrayList<ToptionContent>();
		try {
			pst = conn.prepareStatement("select id,application,testingPID,productSeries,density,testSite,testTool from ttpn where Id like '"+ipn_ipn+"%' and status='Active' ");
			rst = pst.executeQuery();
			Map<String,String> m3 = new HashMap<String, String>();
			Map<String,String> m4 = new HashMap<String, String>();
			Map<String,String> m5 = new HashMap<String, String>();
			Map<String,String> m6 = new HashMap<String, String>();
			Map<String,String> m7 = new HashMap<String, String>();
			while(rst.next()){
				String sub3 = String.valueOf(rst.getString("Id").charAt(6));
				m3.put(sub3, sub3+":"+rst.getString("productSeries"));
				String sub4 = String.valueOf(rst.getString("Id").charAt(7));
				m4.put(sub4, sub4+":"+rst.getString("density"));
				String sub5 = String.valueOf(rst.getString("Id").charAt(8));
				m5.put(sub5, sub5+":"+rst.getString("testTool")+"-"+rst.getString("testSite"));
				String sub6 = String.valueOf(rst.getString("Id").substring(9, 11));
				m6.put(sub6, sub6);
				String sub7 = String.valueOf(rst.getString("Id").charAt(11));
				m7.put(sub7, rst.getString("application"));
				ToptionContent subObj = new ToptionContent();
				subObj.setName(rst.getString("testingPID"));
				subObj.setDescription(rst.getString("testingPID"));
				pl.add(subObj);
			}
			for(Map.Entry<String, String> entry: m3.entrySet()) {
				ToptionContent subObj = new ToptionContent();
				subObj.setName(entry.getKey());
				subObj.setDescription(entry.getValue());
				k3.add(subObj);
			}
			for(Map.Entry<String, String> entry: m4.entrySet()) {
				ToptionContent subObj = new ToptionContent();
				subObj.setName(entry.getKey());
				subObj.setDescription(entry.getValue());
				k4.add(subObj);
			}
			for(Map.Entry<String, String> entry: m5.entrySet()) {
				ToptionContent subObj = new ToptionContent();
				subObj.setName(entry.getKey());
				subObj.setDescription(entry.getValue());
				k5.add(subObj);
			}
			for(Map.Entry<String, String> entry: m6.entrySet()) {
				ToptionContent subObj = new ToptionContent();
				subObj.setName(entry.getKey());
				subObj.setDescription(entry.getValue());
				k6.add(subObj);
			}
			for(Map.Entry<String, String> entry: m7.entrySet()) {
				ToptionContent subObj = new ToptionContent();
				subObj.setName(entry.getKey());
				subObj.setDescription(entry.getValue());
				k7.add(subObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		Map<String, List<ToptionContent>> rt = new HashMap<String, List<ToptionContent>>();
		rt.put("k3", k3);
		rt.put("k4", k4);
		rt.put("k5", k5);
		rt.put("k6", k6);
		rt.put("k7", k7);
		rt.put("pl", pl);
		return rt;
	}
	@Override
	public void completedProductOrderAndInvokeWebService(
			TurnkeyOrder turnkeyOrder) {
		// TODO Auto-generated method stub
		List<TturnkeyOrderItemDetail> listOfItemDetail = turnkeyOrderItemDetail.find("from TturnkeyOrderItemDetail t where t.id='"+turnkeyOrder.getId()+"'");
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("update zz_turnkey_order set status='COMPLETED' where id ='"+turnkeyOrder.getId()+"'");
			pst.executeUpdate();
			Object[] objs = new Object[3];
			objs[0] = turnkeyOrder.getIpn();
			objs[1] = turnkeyOrder.getTpn();
			List<Map<String,String>> listForUpdate = new ArrayList<Map<String,String>>();
			for(TturnkeyOrderItemDetail obj:listOfItemDetail){
				Map<String,String> mapForUpdate = new HashMap<String,String>();
				mapForUpdate.put("productId", "");
				mapForUpdate.put("lotId", WaferIdFormat.getMainLot(obj.getLid()));
				mapForUpdate.put("waferId", obj.getWid());
				listForUpdate.add(mapForUpdate);
			}
			objs[2] = listForUpdate;
			String result = WebServiceUtil.invokeWebService("http://192.168.15.24:8080/productInformation/services/WebService", "setTpnIpn", objs);
//			String result = WebServiceUtil.invokeWebService("http://192.168.15.24:8019/pi-server/services/ReturnInventoryDate", "returnCpQtyAndDate", new  Object[]{ "GD25LQ64BW","GD25Q64BSIG",200,200});
			logger.info(result);
			
			StringBuffer strs = new StringBuffer();
			for(TturnkeyOrderItemDetail obj:listOfItemDetail){
				strs.append(",'"+obj.getFid_()+"'");
			}
			pst = conn.prepareStatement("update zz_turnkey_detail set ipn_new ='"+turnkeyOrder.getIpn()+"',tpn='"+turnkeyOrder.getTpn()+"' where id_ in("+strs.toString().substring(1)+")");
			pst.executeUpdate();
			conn.commit();
			/*//发MAIL
			if(UtilValidate.isNotEmpty(returnList)){
				
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
	}
	
	public String checkWaferHasPermissionToDelete(String lid,List<String> wid){
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		StringBuffer wids = new StringBuffer();
		StringBuffer noPermissionWid = new StringBuffer();
		for(String str : wid){
			wids.append(",'"+str+"'");
		}
		try {
			pst = conn.prepareStatement("select waferId from t_fabside_wip where (status in('ERP to do','Finish') or abnormal like '%S%') and lotid='"+lid+"' and waferid in("+wids.toString().substring(1)+")");
			rst = pst.executeQuery();
			while(rst.next()){
				noPermissionWid.append(","+rst.getString("waferId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(conn);
		}
		if(UtilValidate.isNotEmpty(noPermissionWid)){
			noPermissionWid.substring(1);
		}
		return noPermissionWid.toString();
	}
	
	
}
