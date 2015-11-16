package wzd.service.impl.pi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import wzd.dao.IBaseDao;
import wzd.model.pi.Ttpn;
import wzd.model.tpn.TtpnPnRelation;
import wzd.model.tpn.TtpnRuleHeader;
import wzd.model.tpn.TtpnRuleItem;
import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.Series;
import wzd.pageModel.pi.Tpn;
import wzd.pageModel.tpn.TpnPnRelation;
import wzd.pageModel.tpn.TpnRule;
import wzd.pageModel.tpn.TpnRuleItem;
import wzd.service.ITpnService;
import wzd.util.ConnUtil;
import wzd.util.UtilValidate;

@Service("tpnService")
public class TpnService implements ITpnService{

	private IBaseDao<TtpnRuleHeader> tpnRuleHeaderDao;
	private IBaseDao<TtpnRuleItem> tpnRuleItemDao;
	private IBaseDao<TtpnPnRelation> tpnPnRelationDao;
	private IBaseDao<Ttpn> tpnDao;
	public IBaseDao<Object[]> wiplistDao;

	public IBaseDao<Object[]> getWiplistDao() {
		return wiplistDao;
	}

	@Autowired
	public void setWiplistDao(IBaseDao<Object[]> wiplistDao) {
		this.wiplistDao = wiplistDao;
	}
	public IBaseDao<Ttpn> getTpnDao() {
		return tpnDao;
	}
	@Autowired
	public void setTpnDao(IBaseDao<Ttpn> tpnDao) {
		this.tpnDao = tpnDao;
	}
	public IBaseDao<TtpnRuleHeader> getTpnRuleHeaderDao() {
		return tpnRuleHeaderDao;
	}
	@Autowired
	public void setTpnRuleHeaderDao(IBaseDao<TtpnRuleHeader> tpnRuleHeaderDao) {
		this.tpnRuleHeaderDao = tpnRuleHeaderDao;
	}
	
	public IBaseDao<TtpnRuleItem> getTpnRuleItemDao() {
		return tpnRuleItemDao;
	}
	@Autowired
	public void setTpnRuleItemDao(IBaseDao<TtpnRuleItem> tpnRuleItemDao) {
		this.tpnRuleItemDao = tpnRuleItemDao;
	}
	public IBaseDao<TtpnPnRelation> getTpnPnRelationDao() {
		return tpnPnRelationDao;
	}
	@Autowired
	public void setTpnPnRelationDao(IBaseDao<TtpnPnRelation> tpnPnRelationDao) {
		this.tpnPnRelationDao = tpnPnRelationDao;
	}
	@Override
	public DataGrid datagrid(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from TtpnRuleHeader t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		String totalHql = "select count(*) " + hql;
		List<TtpnRuleHeader> l = tpnRuleHeaderDao.find(hql, params, tpnRule.getPage(), tpnRule.getRows());
		List<TpnRule> nl = new ArrayList<TpnRule>();
		changeModel(l, nl);
		dg.setTotal(tpnRuleHeaderDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel(List<TtpnRuleHeader> l, List<TpnRule> nl) {
		if (l != null && l.size() > 0) {
			for (TtpnRuleHeader t : l) {
				TpnRule u = new TpnRule();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	@Override
	public DataGrid datagridItem(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TtpnRuleItem t where 1=1 and t.ruleId="+tpnRule.getRuleId()+" order by t.remLayer DESC";
		List<TtpnRuleItem> ruleItem = tpnRuleItemDao.find(hql);
		String totalHql = "select count(*) " + hql;
		List<TpnRuleItem> nl = new ArrayList<TpnRuleItem>();
		changeModel2(ruleItem, nl);
		dg.setTotal(tpnRuleItemDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel2(List<TtpnRuleItem> l, List<TpnRuleItem> nl) {
		if (l != null && l.size() > 0) {
			for (TtpnRuleItem t : l) {
				TpnRuleItem u = new TpnRuleItem();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	@Override
	public DataGrid datagridPn(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TtpnPnRelation t where 1=1 and t.ruleId="+tpnRule.getRuleId();
		List<TtpnPnRelation> ruleItem = tpnPnRelationDao.find(hql);
		String totalHql = "select count(*) " + hql;
		List<TpnPnRelation> nl = new ArrayList<TpnPnRelation>();
		changeModel3(ruleItem, nl);
		dg.setTotal(tpnPnRelationDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel3(List<TtpnPnRelation> l, List<TpnPnRelation> nl) {
		if (l != null && l.size() > 0) {
			for (TtpnPnRelation t : l) {
				TpnPnRelation u = new TpnPnRelation();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	
	@Override
	public DataGrid datagridOfTpnFlow(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		String sql = "select a.tpn,b.remLayer,b.tpn tpnii from z_tpn a left join z_tpn_rule_item b on b.tpn=a.tpn and b.ruleId="+tpnRule.getRuleId()+" where a.ruleTypeId='"+tpnRule.getRuleTypeId()+"' order by a.tpnOrder";
		List<Tpn> nl = new ArrayList<Tpn>();
		try {
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			while(rst.next()){
				Tpn u = new Tpn();
				u.setTpn(rst.getString("tpn"));
				String hasTpn = rst.getString("tpnii");
				if(UtilValidate.isNotEmpty(hasTpn)){
					u.setRemLayer(String.valueOf(rst.getInt("remLayer")));
				}
				nl.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst,pst);
			ConnUtil.closeConn(conn);
		}
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel4(List<Ttpn> l, List<Tpn> nl) {
		if (l != null && l.size() > 0) {
			for (Ttpn t : l) {
				Tpn u = new Tpn();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	@Override
	public void createTpnRuleItem(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from z_tpn_rule_item where ruleId = "+tpnRule.getRuleId());
			pst.executeUpdate();
			pst = conn.prepareStatement("insert into z_tpn_rule_item(ruleId,tpn,remLayer) values(?,?,?)");
			JSONArray itemArray = com.alibaba.fastjson.JSONArray.parseArray(tpnRule.getStatus());
			for(int i=0;i<itemArray.size();i++){
				JSONObject itemObj = itemArray.getJSONObject(i);
				String status = itemObj.getString("remLayer");
				if(UtilValidate.isNotEmpty(status)){
					pst.setInt(1, tpnRule.getRuleId());
					pst.setString(2, itemObj.getString("tpn"));
					pst.setInt(3, Integer.parseInt(itemObj.getString("remLayer")));
					pst.addBatch();
				}
			}
			pst.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.closePst(pst);
			ConnUtil.closeConn(conn);
		}
		
	}
	@Override
	public void createPnRelation(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("insert into z_tpn_pn_relation(pn,ruleId) values(?,?)");
			pst.setString(1, tpnRule.getPn());
			pst.setInt(2, tpnRule.getRuleId());
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.closePst(pst);
			ConnUtil.closeConn(conn);
		}
	}
	@Override
	public void deletePnRelation(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("delete from z_tpn_pn_relation where pn='"+tpnRule.getPn()+"'");
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.closePst(pst);
			ConnUtil.closeConn(conn);
		}
	}
	@Override
	public DataGrid getRuleHeaderFromRuleTypeId(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from TtpnRuleHeader t where 1=1 and t.ruleTypeId='"+tpnRule.getRuleTypeId()+"'";
		List<TtpnRuleHeader> ruleItem = tpnRuleHeaderDao.find(hql);
		List<TpnRule> nl = new ArrayList<TpnRule>();
		changeModel5(ruleItem, nl);
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel5(List<TtpnRuleHeader> l, List<TpnRule> nl) {
		if (l != null && l.size() > 0) {
			for (TtpnRuleHeader t : l) {
				TpnRule u = new TpnRule();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	@Override
	public List<Series> getDataToChartsByNewRule(TpnRule tpnRule,String xAxis) {
		// TODO Auto-generated method stub
		List<Series> returnList = new ArrayList<Series>();
		Map<String, Integer> mapOfTpn = new HashMap<String, Integer>();
		String[] tpns = xAxis.split(",");
		for (int i=0;i<tpns.length;i++) {
			mapOfTpn.put(tpns[i], i);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		String hql = "from TtpnPnRelation t where 1=1 and t.ruleId="+tpnRule.getRuleId();
		List<TtpnPnRelation> pnList = tpnPnRelationDao.find(hql);
		StringBuilder sb = new StringBuilder();
		for(TtpnPnRelation pr : pnList){
			sb.append("or t.productNo like '"+pr.getPn()+"%'");
		}
		String hql2 = "select sum(t.qty),t.productNo,t.tpnFlow from TwipDetailChart t where t.status='Y' and ("+sb.substring(2)+") and t.erpDate = '"+ sf.format(new Date()) + "' group by t.productNo,t.tpnFlow order by t.productNo";
		List<Object[]> l = wiplistDao.find(hql2);
		Map<String, Series> mapOfSeries = new HashMap<String, Series>();
		List<String> listOfProductNoOrder = new ArrayList<String>();
		String record = null;
		for (Object[] o : l) {
			String name = o[1].toString();
			String qty = o[0].toString();
			String tpn = o[2].toString();
			if(mapOfTpn.get(tpn)!=null){
				if(UtilValidate.isEmpty(record)){
					listOfProductNoOrder.add(name);
				}else if(!record.equals(name)){
					listOfProductNoOrder.add(name);
				}
				record = name;
				Series series = mapOfSeries.get(name);
				int[] data;
				if (series == null) {
					series = new Series();
					data = new int[tpns.length];
					series.setName(name);
				} else {
					data = series.getData();
				}
				data[mapOfTpn.get(tpn)] = Integer.parseInt(qty);
				series.setData(data);
				mapOfSeries.put(name, series);
			}
			
		}
		for(String o:listOfProductNoOrder){
			//logger.info(o);
			returnList.add(mapOfSeries.get(o));
		}
		return returnList;
	}
	@Override
	public String getTpnToXAxis(TpnRule tpnRule) {
		// TODO Auto-generated method stub
		String hql = "from TtpnRuleHeader t where 1=1 and t.ruleId="+tpnRule.getRuleId();
		List<TtpnRuleHeader> ruleHeader = tpnRuleHeaderDao.find(hql);
		String ruleTypeId = "";
		if(UtilValidate.isNotEmpty(ruleHeader)){
			ruleTypeId = ruleHeader.get(0).getRuleTypeId();
		}
		StringBuffer sb = new StringBuffer();
		String returnSb = "";
		if(UtilValidate.isNotEmpty(ruleTypeId)){
			/*String hql2 = "from TtpnRuleItem t left join Ttpn b on t.tpn=b.tpn and ruleTypeId="+ruleTypeId+" where 1=1 and t.ruleId="+tpnRule.getRuleId()+" order by b.tpnOrder DESC";
			List<TtpnRuleItem> ruleItem = tpnRuleItemDao.find(hql2);
			for (TtpnRuleItem t : ruleItem) {
				sb.append("," + t.getTpn());
			}*/
			ConnUtil connUtil = new ConnUtil();
			Connection conn = connUtil.getMysqlConnection();
			PreparedStatement pst = null;
			ResultSet rst = null;
			String sql = "select a.tpn from z_tpn_rule_item a left join z_tpn b on b.tpn=a.tpn and b.ruleTypeId='"+ruleTypeId+"' where a.ruleId='"+tpnRule.getRuleId()+"' order by b.tpnOrder";
			try {
				pst = conn.prepareStatement(sql);
				rst = pst.executeQuery();
				while(rst.next()){
					sb.append("," + rst.getString("tpn"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnUtil.close(rst,pst);
				ConnUtil.closeConn(conn);
			}
			List<Ttpn> l = tpnDao.find("from Ttpn t where t.status = 'VALID' and t.ruleTypeId='CP' order by t.tpnOrder");
			for (Ttpn t : l) {
				sb.append("," + t.getTpn());
			}
			if(UtilValidate.isNotEmpty(sb.toString())){
				returnSb = sb.toString().substring(1);
			}
		}
		return returnSb;
	}
}
