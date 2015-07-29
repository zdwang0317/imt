package wzd.service.impl.pi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wzd.dao.IBaseDao;
import wzd.model.pi.TlotToHold;
import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.LotToHold;
import wzd.service.ILotToHold;
import wzd.util.ConnUtil;
import wzd.util.PiUtil;
import wzd.util.UtilValidate;
import wzd.util.WaferIdFormat;

@Service("lthService")
public class LotToHoldServiceImpl implements ILotToHold{
	
	private IBaseDao<TlotToHold> lthDao;
	
	@Autowired
	public void setLthDao(IBaseDao<TlotToHold> lthDao) {
		this.lthDao = lthDao;
	}
	
	@Override
	public DataGrid datagridOfHoldLot(LotToHold lotToHold) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from TlotToHold t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
//		hql = addWhere(wip, hql, params);
		String totalHql = "select count(*) " + hql;
//		hql = addOrder(wip, hql);
		List<TlotToHold> l = lthDao.find(hql, params, lotToHold.getPage(), lotToHold.getRows());
		List<LotToHold> nl = new ArrayList<LotToHold>();
		changeModel(l, nl);
		dg.setTotal(lthDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private void changeModel(List<TlotToHold> l, List<LotToHold> nl) {
		if (l != null && l.size() > 0) {
			for (TlotToHold t : l) {
				LotToHold u = new LotToHold();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	@Override
	public int addHoldLot(LotToHold lotToHold) {
		// TODO Auto-generated method stub
		TlotToHold t = new TlotToHold();
		t.setLid(lotToHold.getLid());
		t.setPn(lotToHold.getPn());
		String wid = lotToHold.getWid();
		wid = wid.replace("，", ",");
		t.setWid(lotToHold.getWid());
		t.setQty(getQtyFromWid(wid));
		t.setRemark(lotToHold.getRemark());
		lthDao.save(t);
		return t.getId();
	}

	@Override
	public void deleteHoldLot(LotToHold lotToHold) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from zz_lot_to_hold where id = "+lotToHold.getId());
			pst.executeUpdate();
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
	public DataGrid holdLotCompare(Map<String, String> mapOfHoldLot,
			String conditions) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String sql = "select lid,wid,pn,remark from zz_lot_to_hold where 1=1 "+conditions;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		List<LotToHold> nl = new ArrayList<LotToHold>();
		try {
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			while(rst.next()){
				String lid = rst.getString("lid");
				String wid = rst.getString("wid");
				String lidOfMu = lid;
				if(lidOfMu.indexOf(".")>=0){
					lidOfMu = lidOfMu.substring(0, lidOfMu.indexOf("."));
				}
				String mapOfWid = mapOfHoldLot.get(lidOfMu);
				if(UtilValidate.isNotEmpty(mapOfWid)){
					String result = analysisWid(wid,mapOfWid);
					if(UtilValidate.isNotEmpty(result)){
						LotToHold obj = new LotToHold();
						obj.setLid(lid);
						obj.setWid(result);
						obj.setPn(rst.getString("pn"));
						obj.setRemark(rst.getString("remark"));
						obj.setQty(getQtyFromWid(result));
						nl.add(obj);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dg.setRows(nl);
		return dg;
	}

	private String analysisWid(String db, String excel) {
		// TODO Auto-generated method stub
		String result = null;
		Map<String,String> widOfDb = WaferIdFormat.getWaferIdMap(db);
		List<String> widOfExcel = WaferIdFormat.getWaferIdList(excel);
		List<String> bingGoWid = new ArrayList<String>();
		for(String str:widOfExcel){
			String bing = widOfDb.get(str);
			if(UtilValidate.isNotEmpty(bing)){
				bingGoWid.add(str);
			}
		}
		if(UtilValidate.isNotEmpty(bingGoWid)){
			result = PiUtil.getWidFromList(bingGoWid);
		}
		return result;
	}
	
	private int getQtyFromWid(String wid){
		int qty =0;
		String[] ids = wid.split(",");
		for(String str:ids){
			int num = str.indexOf("-");
			if(num>=0){
				int big = Integer.parseInt(str.substring(num+1));
				int small = Integer.parseInt(str.substring(0,num));
				qty = qty+(big-small+1);
			}else{
				qty++;
			}
		}
		return qty;
	}

}
