package wzd.service.impl.tt;

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

import wzd.util.ConnUtil;
import wzd.util.UtilValidate;
import wzd.util.WaferIdFormat;

public class ActionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pstForAdd = null;
		PreparedStatement pstForUpdate = null;
		Map<String,String[]> mapOfDb = new HashMap<String,String[]>();
		Map<String,Map<String,String>> mapOfDb2 = new HashMap<String,Map<String,String>>();
		Map<String,String> mapOfKltToday = new HashMap<String,String>();
		try {
			//准备ADD 数据
			pstForAdd = conn.prepareStatement("insert into zz_turnkey_detail(wid,pn,lid,ipn,status,cpn,parent_lid,tpnFLow,id_) values(?,?,?,?,?,?,?,?,?)");
			pstForUpdate = conn.prepareStatement("update zz_turnkey_detail set ipn=?,lid=?,tpnFLow=?,pn=? where id_=?");
			//准备No-Repeat 数据
			pst = conn.prepareStatement("select parent_lid,wid,ipn,id,lid,tpnFlow,status,pn from zz_turnkey_detail");
			rst = pst.executeQuery();
			while(rst.next()){
				mapOfDb.put(rst.getString("parent_lid")+"_"+rst.getString("wid"), new String[]{rst.getString("ipn"),String.valueOf(rst.getInt("id")),rst.getString("lid"),rst.getString("tpnFLow")});
				Map<String,String> newMap = new HashMap<String,String>();
				newMap.put("parent_lid", rst.getString("parent_lid"));
				newMap.put("wid", rst.getString("wid"));
				newMap.put("ipn", rst.getString("ipn"));
				newMap.put("id", rst.getString("id"));
				newMap.put("lid", rst.getString("lid"));
				newMap.put("tpnFlow", rst.getString("tpnFlow"));
				newMap.put("status", rst.getString("status"));
				newMap.put("pn", rst.getString("pn"));
				mapOfDb2.put(rst.getString("parent_lid")+"_"+rst.getString("wid"), newMap);
			}
			//准备No-Repeat 数据 结束
			//先处理klt数据,把处理过的数据记录存入mapOfKltToday 后续如有相同数据,不处理  key为 parent_lid+"_"+wid
			Date date = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yy/MM/dd");
			pst = conn.prepareStatement("select wid,lid,pn,cpn,firm,ipn,tpnFlow from cp_wip where erpDate = ? and firm ='klt'");
			pst.setString(1, sf2.format(date));
			rst = pst.executeQuery();
			conn.setAutoCommit(false);
			while(rst.next()){
				List<String> list = new ArrayList<String>();
				String wid = rst.getString("wid");
				if(UtilValidate.isNotEmpty(wid)){
					String lid = rst.getString("lid");
//					logger.info(iii+"deal lid is "+lid);
					String pn = rst.getString("pn");
					String cpn = rst.getString("cpn");
					String tpnFlow = rst.getString("tpnFlow");
					String ipn = "";
					if(UtilValidate.isNotEmpty(rst.getString("ipn"))&&(!rst.getString("ipn").equals("NULL"))){
						ipn = rst.getString("ipn");
					}
					if(ipn.length()>40){
						ipn = ipn.substring(0,40);
					}
					String parent_lid = lid.split("\\.")[0];
					list = WaferIdFormat.getWaferIdList(wid);
					for (String s : list) {
						if (s.length() == 1) {
							s = "0" + s;
						}
						//如果map中有值  则判断ipn是否改变(改变,进行UPDATE，放入KLT MAP:否则过滤掉，放入KLT MAP)  如map中无值 则进行ADD
//						String[] valuesOfMap = mapOfDb.get(parent_lid+"_"+s);
						Map<String,String> dbHas = mapOfDb2.get(parent_lid+"_"+s);
//						if(null!=valuesOfMap&&valuesOfMap.length>0){
						mapOfKltToday.put(parent_lid+"_"+s, "anything");
						if(null!=dbHas&&dbHas.size()>0){
							boolean hasUpdate = false;
							if(dbHas.get("status").equals("CREATED")){
								if(!ipn.equals(dbHas.get("ipn"))){
									hasUpdate = true;
								}
							}
							if(UtilValidate.isNotEmpty(pn)&&!pn.equals(dbHas.get("pn"))){
								hasUpdate = true;
							}
							if(UtilValidate.isNotEmpty(tpnFlow)&&!tpnFlow.equals(dbHas.get("tpnFlow"))){
								hasUpdate = true;
							}
							if(!lid.equals(dbHas.get("lid"))){
								hasUpdate = true;
							}
							//更新
							if(hasUpdate){
								pstForUpdate.setString(1, ipn);
								pstForUpdate.setString(2, lid);
								pstForUpdate.setString(3, tpnFlow);
								pstForUpdate.setString(4, pn);
								pstForUpdate.setString(5, parent_lid+"_"+s);
								pstForUpdate.addBatch();
							}
						}else{
							//添加
							pstForAdd.setString(1, s);
							pstForAdd.setString(2, pn);
							pstForAdd.setString(3, lid);
							pstForAdd.setString(4, ipn);
							pstForAdd.setString(5, "CREATED");
							pstForAdd.setString(6, cpn);
							pstForAdd.setString(7, parent_lid);
							pstForAdd.setString(8, tpnFlow);
							pstForAdd.setString(9, parent_lid+"_"+s);
							// 把一个SQL命令加入命令列表
							pstForAdd.addBatch();
						}
					}
				}
				
			}
			//非KLT WIP
			pst = conn.prepareStatement("select wid,lid,pn,cpn,firm,ipn,stage,tpnFlow from cp_wip where erpDate = ? and firm !='klt'");
			pst.setString(1, sf2.format(date));
			rst = pst.executeQuery();
			while(rst.next()){
				List<String> list = new ArrayList<String>();
				String wid = rst.getString("wid");
				if(UtilValidate.isNotEmpty(wid)){
					String lid = rst.getString("lid");
					String pn = rst.getString("pn");
					String cpn = rst.getString("cpn");
					String firm = rst.getString("firm");
					String tpnFlow = rst.getString("tpnFlow");
					String ipn = "";
					if(UtilValidate.isNotEmpty(rst.getString("ipn"))&&(!rst.getString("ipn").equals("NULL"))){
						ipn = rst.getString("ipn");
					}
					String parent_lid = lid.split("\\.")[0];
					if (firm.equals("smic") || firm.equals("xmc")) {
						list = WaferIdFormat.getWaferIdFromSmic(wid);
					} else if (firm.equals("csmc")) {
						list = WaferIdFormat.getWaferIdList(wid);
					} else if (firm.equals("chipmos")) {
						String stage = rst.getString("stage");
						if (stage.equals("STOCK")) {
							list = WaferIdFormat.getWaferIdList(wid);
						} else {
							list = WaferIdFormat.getWaferIdFromChipmos(list,wid);
						}
					}
					for (String s : list) {
						if (s.length() == 1) {
							s = "0" + s;
						}
						String kltOfMap = mapOfKltToday.get(parent_lid+"_"+s);
						if(UtilValidate.isEmpty(kltOfMap)){
							Map<String,String> dbHas = mapOfDb2.get(parent_lid+"_"+s);
							mapOfKltToday.put(parent_lid+"_"+s, "anything");
							if(null!=dbHas&&dbHas.size()>0){
								boolean hasUpdate = false;
								if(dbHas.get("status").equals("CREATED")){
									if(!ipn.equals(dbHas.get("ipn"))){
										hasUpdate = true;
									}
								}
								if(UtilValidate.isNotEmpty(pn)&&!pn.equals(dbHas.get("pn"))){
									hasUpdate = true;
								}
								if(UtilValidate.isNotEmpty(tpnFlow)&&!tpnFlow.equals(dbHas.get("tpnFlow"))){
									hasUpdate = true;
								}
								if(!lid.equals(dbHas.get("lid"))){
									hasUpdate = true;
								}
								//更新
								if(hasUpdate){
									pstForUpdate.setString(1, ipn);
									pstForUpdate.setString(2, lid);
									pstForUpdate.setString(3, tpnFlow);
									pstForUpdate.setString(4, pn);
									pstForUpdate.setString(5, parent_lid+"_"+s);
									pstForUpdate.addBatch();
								}
							}else{
								//添加
								pstForAdd.setString(1, s);
								pstForAdd.setString(2, pn);
								pstForAdd.setString(3, lid);
								pstForAdd.setString(4, ipn);
								pstForAdd.setString(5, "CREATED");
								pstForAdd.setString(6, cpn);
								pstForAdd.setString(7, parent_lid);
								pstForAdd.setString(8, tpnFlow);
								pstForAdd.setString(9, parent_lid+"_"+s);
								// 把一个SQL命令加入命令列表
								pstForAdd.addBatch();
//								logger.info("batch after");
							}
						}
						
					}
				}
			}
			int[] rowsOne =pstForUpdate.executeBatch();
			int[] rowsTwo = pstForAdd.executeBatch();
			System.out.println(rowsOne.length);
			System.out.println(rowsTwo.length);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.close(rst, pst);
			ConnUtil.close(rst, pst);
			ConnUtil.close(null, pstForUpdate);
			ConnUtil.close(null, pstForAdd);
			ConnUtil.closeConn(conn);
		}
	}
		
}
