package wzd.service.impl.tt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import wzd.pageModel.tt.Tt;
import wzd.service.ITtService;
import wzd.util.ConnUtil;
import wzd.util.UtilValidate;
import wzd.util.WaferIdFormat;

@Service("ttService")
public class TtServiceImpl implements ITtService {
	
	private static final Logger logger = Logger.getLogger(TtServiceImpl.class);
	
	public int passWafer(String firmName, String sqlOfFirm) { 
		ConnUtil connUtil = new ConnUtil();
		int returnNum = 0;
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		Date date = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf1 = new SimpleDateFormat("yy/MM/dd");
		String erpDate = sf1.format(date);// "13/08/20";
		Connection conn = connUtil.getMysqlConnection();
		Connection conn2 = connUtil.getOracleConnection();
		if (conn != null) {
			String importName = firmName;
			String sql = sqlOfFirm;
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, erpDate);
				rst = pst.executeQuery();
				conn.setAutoCommit(false);
				pst = conn2.prepareStatement("insert into tc_wfi_file(tc_wfi01,tc_wfi02,tc_wfi03,tc_wfi04,tc_wfi05,tc_wfi06,tc_wfi08,tc_wfi12,tc_wfi07,tc_wfi09) values(?,?,?,?,?,?,?,?,?,?)");
				// 取出klz当天数据
				Map<String, String> map = new HashMap<String, String>();
				if (importName.equals("csmc")) {
					String kltSql = "select lid,wid from z_wip_detail where erpDate=? and firm = 'klt'";
					pst2 = conn.prepareStatement(kltSql);
					pst2.setString(1, sf2.format(date));
					rst2 = pst2.executeQuery();
					while (rst2.next()) {
						String key = rst2.getString("lid").substring(0, 6)
								+ rst2.getString("wid");
						map.put(key, "1");
					}
				}
				while (rst.next()) {
					returnNum++;
					// 如果厂商为csmc 则过滤掉与klt相同数据
					if (importName.equals("csmc") && map.size() > 0) {
						String key = rst.getString("lid").substring(0, 6)
								+ rst.getString("wid");
						String value = map.get(key);
						if (UtilValidate.isNotEmpty(value)) {
							continue;
						}
					}
					pst.setString(1, rst.getString("firm").toUpperCase());
					pst.setString(2, rst.getString("pn"));
					// IPN
					String ipn = rst.getString("ipn");
					if (UtilValidate.isNotEmpty(ipn)) {
						pst.setString(3, ipn);
					} else {
						pst.setString(3, " ");
					}
					pst.setString(4, rst.getString("lid"));
					if (UtilValidate.isEmpty(rst.getString("wid"))) {
						continue;
					} else {
						pst.setString(5, rst.getString("wid"));
					}
					pst.setInt(6, 1);
					pst.setDate(7, new java.sql.Date(date.getTime()));
					pst.setString(8, "00:00:00");
					pst.setString(9, "N");
					pst.setString(10, "N");
					// 把一个SQL命令加入命令列表
					pst.addBatch();
				}
				pst.executeBatch();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnUtil.close(rst, pst);
				ConnUtil.closeConn(conn);
				ConnUtil.close(rst2, pst2);
				ConnUtil.closeConn(conn2);
			}
		}
		return returnNum;
	}
	
	public int passWaferToHk(String firmName, String sqlOfFirm) {
		ConnUtil connUtil = new ConnUtil();
		int returnNum = 0;
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		Date date = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf1 = new SimpleDateFormat("yy/MM/dd");
		String erpDate = sf1.format(date);// "13/08/20";
		Connection conn = connUtil.getMysqlConnection();
		Connection conn2 = connUtil.getOracleConnectionHk();
		if (conn != null) {
			String importName = firmName;
			String sql = sqlOfFirm;
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, erpDate);
				rst = pst.executeQuery();
				conn.setAutoCommit(false);
				pst = conn2.prepareStatement("insert into tc_wfi_file(tc_wfi01,tc_wfi02,tc_wfi03,tc_wfi04,tc_wfi05,tc_wfi06,tc_wfi08,tc_wfi12,tc_wfi07,tc_wfi09) values(?,?,?,?,?,?,?,?,?,?)");
				// 取出klz当天数据
				Map<String, String> map = new HashMap<String, String>();
				if (importName.equals("csmc")) {
					String kltSql = "select lid,wid from z_wip_detail where erpDate=? and firm = 'klt'";
					pst2 = conn.prepareStatement(kltSql);
					pst2.setString(1, sf2.format(date));
					rst2 = pst2.executeQuery();
					while (rst2.next()) {
						String key = rst2.getString("lid").substring(0, 6)
								+ rst2.getString("wid");
						map.put(key, "1");
					}
				}
				while (rst.next()) {
					returnNum++;
					// 如果厂商为csmc 则过滤掉与klt相同数据
					if (importName.equals("csmc") && map.size() > 0) {
						String key = rst.getString("lid").substring(0, 6)
								+ rst.getString("wid");
						String value = map.get(key);
						if (UtilValidate.isNotEmpty(value)) {
							continue;
						}
					}
					pst.setString(1, rst.getString("firm").toUpperCase());
					pst.setString(2, rst.getString("pn"));
					// IPN
					String ipn = rst.getString("ipn");
					if (UtilValidate.isNotEmpty(ipn)) {
						pst.setString(3, ipn);
					} else {
						pst.setString(3, " ");
					}
					pst.setString(4, rst.getString("lid"));
					if (UtilValidate.isEmpty(rst.getString("wid"))) {
						continue;
					} else {
						pst.setString(5, rst.getString("wid"));
					}
					pst.setInt(6, 1);
					pst.setDate(7, new java.sql.Date(date.getTime()));
					pst.setString(8, "00:00:00");
					pst.setString(9, "N");
					pst.setString(10, "N");
					// 把一个SQL命令加入命令列表
					pst.addBatch();
				}
				pst.executeBatch();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnUtil.close(rst, pst);
				ConnUtil.closeConn(conn);
				ConnUtil.close(rst2, pst2);
				ConnUtil.closeConn(conn2);
			}
		}
		return returnNum;
	}

	@Override
	public int dataResolve() {
		// TODO Auto-generated method stub
		logger.info("Data Resolve Start!");
		ConnUtil connUtil = new ConnUtil();
		Map<String,String> mapOfTpn = new HashMap<String,String>();
		List<String> updateWipId = new ArrayList<String>();
		int returnNum = 0;
		PreparedStatement pst = null;
		ResultSet rst = null;
		Date date = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yy/MM/dd");
		Connection conn = connUtil.getMysqlConnection();
		
		String sql = "select * from cp_wip where qty>0 and erpDate = ? and resolved is null and id not in(select id from cp_wip where erpdate=? and status='TRAN' and firm='csmc')";
		try {
			pst = conn.prepareStatement("select a.productNo,a.lid,a.wid from z_wip_detail a left join z_tpn b on b.tpn = a.tpnflow where tpnflow in('PAS1','PAD','PAS2','UV','WAT','QC','INV1') group by lid,wid order by tpnorder desc");
			rst = pst.executeQuery();
			while (rst.next()) {
				mapOfTpn.put(rst.getString("lid")+rst.getString("wid"), rst.getString("productNo"));
			}
			pst = conn.prepareStatement(sql);
			pst.setString(1, sf2.format(date));
			pst.setString(2, sf2.format(date));
			// pst.setString(1, "13/08/25");
			rst = pst.executeQuery();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("insert into z_wip_detail(pid,wid,erpDate,pn,firm,lid,location,sendDate,ipn,status,cpn,productNo,tpnFlow,stage,wipStatus,ifCp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			int i = 0;
			while (rst.next()) {
				returnNum++;
				String wid = rst.getString("wid");
				String firm = rst.getString("firm");
				if (UtilValidate.isNotEmpty(wid)) {
					List<String> list = new ArrayList<String>();
					if (firm.equals("smic")) {
						list = WaferIdFormat
								.getWaferIdFromSmic(wid);
					} else if (firm.equals("klt")
							|| firm.equals("csmc")) {
						list = WaferIdFormat.getWaferIdList(wid);
					} else if (firm.contains("chipmos")) {
						String stage = rst.getString("stage");
						if (stage.equals("STOCK")) {
							list = WaferIdFormat
									.getWaferIdList(wid);
						} else {
							list = WaferIdFormat
									.getWaferIdFromChipmos(list,
											wid);
						}
					} else if (firm.equals("hlmc")||firm.equals("umc")|| firm.equals("xmc")) {
						list = WaferIdFormat.getWaferIdFromHlmc(wid);
					}else if (firm.equals("sjsemi")) {
						list = WaferIdFormat.getWaferIdList(wid);
					}
					if(UtilValidate.isNotEmpty(list)){
						updateWipId.add(rst.getString("id"));
					}
					for (String s : list) {
						pst.setInt(1, rst.getInt("id"));
						if (s.length() == 1) {
							s = "0" + s;
						}
						pst.setString(2, s);
						pst.setString(3, rst.getString("erpDate"));
						pst.setString(4, rst.getString("pn"));
						pst.setString(5, rst.getString("firm"));
						pst.setString(6, rst.getString("lid"));
						pst.setString(7, rst.getString("location"));
						pst.setDate(8, rst.getDate("sendDate"));
						String ipn = "";
						if(UtilValidate.isNotEmpty(rst.getString("ipn"))&&(!rst.getString("ipn").equals("NULL"))){
							ipn = rst.getString("ipn");
						}
						if(ipn.length()>40){
							ipn = ipn.substring(0, 40);
						}
						pst.setString(9, ipn);
						pst.setString(11, rst.getString("cpn"));
						String productNo = mapOfTpn.get(rst.getString("lid")+rst.getString("wid"));
						
						if (UtilValidate.isEmpty(productNo)&&UtilValidate.isNotEmpty(rst.getString("productNo")) && !rst.getString("productNo").equals("NULL")) {
							i++;
							productNo = rst.getString("productNo");
						}
						
						pst.setString(12, productNo);
						String tpnFlow = null;
						if (UtilValidate.isNotEmpty(rst.getString("tpnFlow")) && !rst.getString("tpnFlow").equals("NULL")) {
							tpnFlow = rst.getString("tpnFlow");
						}
						if(UtilValidate.isNotEmpty(tpnFlow)){
							pst.setString(10, "Y");
						}else{
							pst.setString(10, "");
						}
						pst.setString(13, tpnFlow);
						pst.setString(14, rst.getString("stage"));
						pst.setString(15, rst.getString("status"));
						pst.setString(16, rst.getString("ifCp"));
						// 把一个SQL命令加入命令列表
						pst.addBatch();
					}
				}
			}
			logger.info("Data Resolve Records is "+i);
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			conn.commit();
			if (UtilValidate.isNotEmpty(updateWipId)) {
				StringBuffer strs = new StringBuffer();
				for (String str : updateWipId) {
					strs.append(",'" + str + "'");
				}
				conn.setAutoCommit(false);
				pst = conn.prepareStatement("update cp_wip set resolved='Y' where id in ("+ strs.toString().substring(1) + ")");
				pst.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		logger.info("Data Resolve End!");
		return returnNum;
	}

	@Override
	public synchronized int passCpWip() {
		logger.info("Pass Cp Wip Start!");
		ConnUtil connUtil = new ConnUtil();
		int returnNum = 0;
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		PreparedStatement pst3 = null;
		ResultSet rst3 = null;
		PreparedStatement pst4 = null;
		ResultSet rst4 = null;
		PreparedStatement pst5 = null;
		ResultSet rst5 = null;
		Date date = new Date();
		Connection connOfPi = connUtil.getMysqlConnection();
		Connection connOfBj = connUtil.getOracleConnection();
		Connection connOfHk = connUtil.getOracleConnectionHk();
		if (connOfPi != null) {
			// 查询PI系统中CP可出货记录
			String sql = "select id,waferid,lotid,grade,productId,filePath,tpnId,erpProgram,ipn_real from t_fabside_wip where ((status in('Inkless map available','OP ship','OP to do') and abnormal='M') or (status in('Inkless map available','OP ship') and abnormal<>'M' and abnormal not like '%T%') or (status ='ERP to do') or (status ='Mapping to do')) and ipn is null and lotid is not null";
			// 更新Pi系统
			List<String> updateWipId = new ArrayList<String>();
			try {
				pst2 = connOfPi.prepareStatement(sql);
				rst2 = pst2.executeQuery();
				// 插入TIPTOP语句
				String insertSql = "insert into tc_cpi_file"
						+ "(tc_cpi01,tc_cpi02,tc_cpi03,tc_cpi05,tc_cpi06,tc_cpi07,"
						+ "tc_cpi10,tc_cpi11,tc_cpi09,tc_cpi12,tc_cpi04,tc_cpi08,tc_cpi13,"
						+ "tc_cpi14,tc_cpi15,tc_cpi16,tc_cpi17) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = connOfBj.prepareStatement(insertSql);
				pst3 = connOfHk.prepareStatement(insertSql);
				connOfBj.setAutoCommit(false);
				connOfHk.setAutoCommit(false);
				while (rst2.next()) {
					/*String key = rst2.getString("lotid").substring(0, 6)
							+ rst2.getString("waferid");*/
					updateWipId.add(rst2.getString("id"));
					// NewWipDetail nwd = mapOfCpLatest.get(key);
					String firm = " ";
					String lid = " ";
					String ipn = rst2.getString("ipn_real");
					String cpn = " ";
					String filePath = rst2.getString("filePath");
					/*if (UtilValidate.isNotEmpty(filePath)) {
						firm = filePath
								.substring(filePath.lastIndexOf("/") + 1);
					}*/
					//查询yield
					String yield = null;
					String sqlOfYield = "select lotid,waferid,yield from t_fabside_yield where lotid=? and waferid= ? and stage='CP2' and lotid is not null and lotid!=' ' order by starttime desc limit 0,1";
					pst5 = connOfPi.prepareStatement(sqlOfYield);
					pst5.setString(1, rst2.getString("lotid").substring(0, 6));
					pst5.setString(2, rst2.getString("waferid"));
					rst5 = pst5.executeQuery();
					while (rst5.next()) {
						yield = rst5.getString("yield");
					}
					// 查询z_wip_detail 得到ipn fabid
					String sqlOfWip = "select ipn,cpn,lid,wid,location,firm from z_wip_detail where lid like ? and wid = ? and (location not in('wafer','B1','FAB7','S1','Fab12') or location is null) and pn not like '%-7%' order by lid,wid,firm,senddate desc limit 0,1";
					pst4 = connOfPi.prepareStatement(sqlOfWip);
					pst4.setString(1, rst2.getString("lotid") + "%");
					pst4.setString(2, rst2.getString("waferid"));
					rst4 = pst4.executeQuery();
					String location = " ";
					String detail_firm = " ";
					while (rst4.next()) {
						lid = rst4.getString("lid");
						location = rst4.getString("location");
//						ipn = rst4.getString("ipn");
						cpn = rst4.getString("cpn");
						detail_firm = rst4.getString("firm");
					}
					if (detail_firm.equals("smic")) {
						firm = "SMIC-SH";
					} else if (detail_firm.equals("xmc")) {
						if (location.equals("SH_TESTING")) {
							firm = "XMC-SH";
						} else {
							firm = "XMC-WH";
						}
					} else if (detail_firm.equals("klt")) {
						firm = "KLT";
					} else if (detail_firm.equals("chipmos")) {
						firm = "CHIPMOS";
					}
					pst.setString(1, firm);
					pst3.setString(1, firm);
					pst.setString(2, rst2.getString("productId"));
					pst3.setString(2, rst2.getString("productId"));
					// IPN
					if(UtilValidate.isEmpty(ipn)){
						ipn = " ";
					}
					pst.setString(3, ipn);
					pst3.setString(3, ipn);
					if (lid.equals(" ")) {
						lid = rst2.getString("lotid");
					}
					pst.setString(4, lid);
					pst3.setString(4, lid);
					pst.setString(5, rst2.getString("waferid"));
					pst.setInt(6, 1);
					pst.setDate(7, new java.sql.Date(date.getTime()));
					pst.setString(8, "00:00:00");
					pst.setString(9, "N");
					pst.setString(10, "N");
					if(UtilValidate.isEmpty(cpn)){
						cpn = " ";
					}
					pst.setString(11, cpn);
					pst.setDouble(12, 0);
					pst.setString(13, rst2.getString("grade"));
					pst3.setString(5, rst2.getString("waferid"));
					pst3.setInt(6, 1);
					pst3.setDate(7, new java.sql.Date(date.getTime()));
					pst3.setString(8, "00:00:00");
					pst3.setString(9, "N");
					pst3.setString(10, "N");
					pst3.setString(11, " ");
					pst3.setDouble(12, 0);
					pst3.setString(13, rst2.getString("grade"));
					// yield
					if (UtilValidate.isNotEmpty(yield)) {
						pst.setString(14, yield);
						pst3.setString(14, yield);
					} else {
						pst.setString(14, " ");
						pst3.setString(14, " ");
					}
					pst.setString(15, " ");
					pst3.setString(15, " ");
					pst.setString(16, rst2.getString("tpnId"));
					pst3.setString(16, rst2.getString("tpnId"));
					pst.setString(17, rst2.getString("erpProgram"));
					pst3.setString(17, rst2.getString("erpProgram"));
					pst.addBatch();
					pst3.addBatch();
					returnNum++;
				}
				pst.executeBatch();
				connOfBj.commit();
				pst3.executeBatch();
				connOfHk.commit();
				if (UtilValidate.isNotEmpty(updateWipId)) {
					StringBuffer strs = new StringBuffer();
					for (String str : updateWipId) {
						strs.append(",'" + str + "'");
					}
					connOfPi.setAutoCommit(false);
					pst = connOfPi
							.prepareStatement("update t_fabside_wip set ipn='Y' where id in ("
									+ strs.toString().substring(1) + ")");
					pst.executeUpdate();
					connOfPi.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnUtil.close(rst, pst);
				ConnUtil.closeConn(connOfPi);
				ConnUtil.close(rst2, pst2);
				ConnUtil.close(rst3, pst3);
				ConnUtil.close(rst4, pst4);
				ConnUtil.close(rst5, pst5);
				ConnUtil.closeConn(connOfBj);
				ConnUtil.closeConn(connOfHk);
			}
		}
		logger.info("Pass Cp Wip ReturnNum is "+returnNum);
//		logger.info("Pass Cp Wip End!");
		return returnNum;
	}

	@Override
	public int passWaferWip() {
		// TODO Auto-generated method stub
		logger.info("Pass Wafer Wip Start!");
		int returnNum =0;
		String sqlOfSmic = "select * from z_wip_detail where erpDate = ? and firm='smic' and ((location in('B1','FAB7','S1') and stage like 'QC%') or(pn not like '4%' and location not in('B1','FAB7','S1')) or (location in('B1','FAB7','S1') and stage = 'Inventory'))";
		returnNum += passWafer("smic", sqlOfSmic);
		returnNum += passWaferToHk("smic", sqlOfSmic);
		String sqlOfXmc = "select * from z_wip_detail where erpDate = ? and firm='xmc' and ((location in('Fab12') and stage like 'QC%') or location in('WH_TESTING','SH_TESTING'))";
		returnNum += passWafer("xmc", sqlOfXmc);
		String sqlOfCSmc = "select * from z_wip_detail where erpDate = ? and firm='csmc' and status='TRAN'";
		returnNum += passWafer("csmc", sqlOfCSmc);
		logger.info("Pass Wafer Wip End!");
		return returnNum;
	}

	@Override
	public int updateBjGooddie() {
		int returnNum = 0;
		ConnUtil connUtil = new ConnUtil();
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		Connection connOfPi = connUtil.getMysqlConnection();
		Connection connOfBj = connUtil.getOracleConnection();
		try {
			/*
			 * search cp have been ship to in tiptop sys
			 */
			String sqlOfTiptop = "select tc_cpj01,tc_cpj15,tc_cpj16,tc_cpj18 from tc_cpj_file where tc_cpj08='N'";
			pst = connOfBj.prepareStatement(sqlOfTiptop);
			rst = pst.executeQuery();
			/*
			 * search gooddie in pi sys
			 */
			String sqlOfGooddie = "select lotid,waferid,gooddieqty from t_fabside_gooddie_info where lotid=? and waferid=? order by uploadtype desc,uploadtime desc limit 0,1";
			/*
			 * update cp record in tiptop sys
			 */
			String updateSql = "update tc_cpj_file set tc_cpj18=? where tc_cpj01=? and tc_cpj15 = ? and tc_cpj16=?";
			pst2 = connOfBj.prepareStatement(updateSql);
			connOfBj.setAutoCommit(false);
			while (rst.next()) {
				
				String id = rst.getString("tc_cpj01");
				String lotId = rst.getString("tc_cpj15");
				String waferId = rst.getString("tc_cpj16");
//				logger.info("tc_cpj01 is "+id);
				int goodDie = Integer.parseInt(rst.getString("tc_cpj18"));
				pst = connOfPi.prepareStatement(sqlOfGooddie);
				if (lotId.length() > 6) {
					pst.setString(1, lotId.substring(0, 6));
				} else {
					pst.setString(1, lotId);
				}
				pst.setString(2, waferId);
				rst2 = pst.executeQuery();
				if (rst2.next()) {
					int gd = rst2.getInt("gooddieqty");
					if (gd != goodDie) {
						pst2.setDouble(1, gd);
						pst2.setString(2, id);
						pst2.setString(3, rst.getString("tc_cpj15"));
						pst2.setString(4, waferId);
						pst2.addBatch();
						returnNum++;
					}
				}
			}
			pst2.executeBatch();
			connOfBj.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(connOfPi);
			ConnUtil.close(rst2, pst2);
			ConnUtil.closeConn(connOfBj);
		}
		return returnNum;
	}

	@Override
	public int updateHkGooddie() {
		int returnNum = 0;
		ConnUtil connUtil = new ConnUtil();
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		Connection connOfPi = connUtil.getMysqlConnection();
		Connection connOfHk = connUtil.getOracleConnectionHk();
		try {
			/*
			 * search cp have been ship to in tiptop sys
			 */
			String sqlOfTiptop = "select tc_cpj01,tc_cpj15,tc_cpj16,tc_cpj18 from tc_cpj_file where tc_cpj08='N'";
			pst = connOfHk.prepareStatement(sqlOfTiptop);
			rst = pst.executeQuery();
			/*
			 * search gooddie in pi sys
			 */
			String sqlOfGooddie = "select lotid,waferid,gooddieqty from t_fabside_gooddie_info where lotid=? and waferid=? order by uploadtype desc,uploadtime desc limit 0,1";
			/*
			 * update cp record in tiptop sys
			 */
			String updateSql = "update tc_cpj_file set tc_cpj18=? where tc_cpj01=? and tc_cpj15 = ? and tc_cpj16=?";
			pst2 = connOfHk.prepareStatement(updateSql);
			connOfHk.setAutoCommit(false);
			while (rst.next()) {
				String id = rst.getString("tc_cpj01");
				String lotId = rst.getString("tc_cpj15");
				String waferId = rst.getString("tc_cpj16");
				int goodDie = Integer.parseInt(rst.getString("tc_cpj18"));
				pst = connOfPi.prepareStatement(sqlOfGooddie);
				if (lotId.length() > 6) {
					pst.setString(1, lotId.substring(0, 6));
				} else {
					pst.setString(1, lotId);
				}
				pst.setString(2, waferId);
				rst2 = pst.executeQuery();
				if (rst2.next()) {
					int gd = rst2.getInt("gooddieqty");
					if (gd != goodDie) {
						pst2.setDouble(1, gd);
						pst2.setString(2, id);
						pst2.setString(3, rst.getString("tc_cpj15"));
						pst2.setString(4, waferId);
						pst2.addBatch();
						returnNum++;
					}
				}
			}
			pst2.executeBatch();
			connOfHk.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(connOfPi);
			ConnUtil.close(rst2, pst2);
			ConnUtil.closeConn(connOfHk);
		}
		return returnNum;
	}

	@Override
	public int returnGooddieOfPi(Tt tt) {
		// TODO Auto-generated method stub
		int returnNum = 0;
		/*PreparedStatement pst = null;
		ResultSet rst = null;
		Connection conn = Conn.getMysqlConnection();*/
		return returnNum;
	}

	@Override
	public synchronized void test() {
		// TODO Auto-generated method stub
		logger.info("Test start");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Test end");
	}

	@Override
	public int updateGooddie(Tt tt) {
//		logger.info("Update Gooddie Start!");
		int returnNum = 0;
		ConnUtil connUtil = new ConnUtil();
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		Connection connOfPi = connUtil.getMysqlConnection();
		Connection conn = connUtil.getOracleConnection(tt.getDb(),tt.getDb());
		try {
			/*
			 * search cp have been ship to in tiptop sys
			 */
			String sqlOfTiptop = "select tc_cpj01,tc_cpj15,tc_cpj16,tc_cpj18 from tc_cpj_file where tc_cpj08='N'";
			if(tt.getMode().equals("1")){
				sqlOfTiptop = sqlOfTiptop+" and tc_cpj18=0";
			}else if(tt.getMode().equals("2")){
				sqlOfTiptop = sqlOfTiptop+" and tc_cpj18<>0";
			}
			if(UtilValidate.isNotEmpty(tt.getTc_cpj01())){
				String[] strs = tt.getTc_cpj01().split(",");
				StringBuffer sb = new StringBuffer();
				for(String o:strs){
					sb.append("'"+o+"',");
				}
				String condition = sb.substring(0, sb.length()-1);
				sqlOfTiptop = sqlOfTiptop + " and tc_cpj01 in("+condition+")";
			}
			pst = conn.prepareStatement(sqlOfTiptop);
			rst = pst.executeQuery();
			/*
			 * search gooddie in pi sys
			 */
			String sqlOfGooddie = "";
			String tableName = tt.getLotId();
			if(tableName.equals("old")){
				sqlOfGooddie = "select a.lotid,a.waferid,a.gooddieqty from t_fabside_gooddie_info a left join t_fabside_wip b on b.lotid=a.lotid and b.waferid=a.waferid where b.status='ERP to do' and a.lotid=? and a.waferid=? order by a.uploadtype desc,a.uploadtime desc limit 0,1";
			}else{
				sqlOfGooddie = "select lotid,waferid,gooddieqty from t_gigamapping where lotid=? and waferid=? and status='Active' limit 0,1";
			}
//			String sqlOfGooddie = "select lotid,waferid,gooddieqty from t_fabside_gooddie_info where lotid=? and waferid=? order by uploadtype desc,uploadtime desc limit 0,1";
			
			/*
			 * update cp record in tiptop sys
			 */
			String updateSql = "update tc_cpj_file set tc_cpj18=? where tc_cpj01=? and tc_cpj15 = ? and tc_cpj16=?";
			pst2 = conn.prepareStatement(updateSql);
			conn.setAutoCommit(false);
			while (rst.next()) {
				String id = rst.getString("tc_cpj01");
				String lotId = rst.getString("tc_cpj15");
				String waferId = rst.getString("tc_cpj16");
				//logger.info("tc_cpj01 is "+id);
				int goodDie = Integer.parseInt(rst.getString("tc_cpj18"));
				pst = connOfPi.prepareStatement(sqlOfGooddie);
				if (lotId.contains(".")) {
					pst.setString(1, lotId.substring(0, lotId.indexOf(".")));
				} else {
					pst.setString(1, lotId);
				}
				pst.setString(2, waferId);
				rst2 = pst.executeQuery();
				if (rst2.next()) {
					int gd = rst2.getInt("gooddieqty");
					if (gd != goodDie) {
						pst2.setDouble(1, gd);
						pst2.setString(2, id);
						pst2.setString(3, rst.getString("tc_cpj15"));
						pst2.setString(4, waferId);
						pst2.addBatch();
						returnNum++;
					}
				}
			}
			pst2.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(connOfPi);
			ConnUtil.close(rst2, pst2);
			ConnUtil.closeConn(conn);
		}
//		logger.info("Update Gooddie End!");
		return returnNum;
	}

	@Override
	public int CancelShipTo(String num,String db,String mode) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = null;
		Statement pst = null;
		int returnNum = 0;
		if(db.equals("bj")){
			conn = connUtil.getOracleConnection();
		}else{
			conn = connUtil.getOracleConnectionHk();
		}
		String updateSql = null;
		if(mode.equals("002")){
			updateSql = "update tc_wfj_file set tc_wfj08='N' where tc_wfj01='"+num+"'";
		}else{
			updateSql = "update tc_cpj_file set tc_cpj08='N' where tc_cpj01='"+num+"'";
		}
		try {
			pst = conn.createStatement();
			returnNum = pst.executeUpdate(updateSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return returnNum;
	}

	@Override
	public int deleteRepetitiveData() {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection connection = null;
		int returnNum = 0;
		List<String> listOfDb = new ArrayList<String>();
		listOfDb.add("dsbj");
		listOfDb.add("dshk");
		listOfDb.add("dssh");
		listOfDb.add("dshf");
		PreparedStatement pst = null;
		try {
			for(String db : listOfDb){
				connection = connUtil.getOracleConnection(db,db);
				pst = connection.prepareStatement("delete from tc_cpi_file a where (a.tc_cpi05,a.tc_cpi06) in (select tc_cpi05,tc_cpi06  from tc_cpi_file group by tc_cpi05,tc_cpi06 having count(*)>1) and rowid not in(select min(rowid) from tc_cpi_file group by tc_cpi05,tc_cpi06 having count(*)>1)");
				pst.executeUpdate();
				ConnUtil.closeConn(connection);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnUtil.closeConn(connection);
		}finally{
			ConnUtil.close(null, pst);
			ConnUtil.closeConn(connection);
		}
		return returnNum;
		
	}
	
	@Override
	public int dataResolveForPo() {
		// TODO Auto-generated method stub
		logger.info("Data Resolve To Table of zz_turnkey_detail Start!");
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
			pstForAdd = conn.prepareStatement("insert into zz_turnkey_detail(wid,pn,lid,ipn,status,cpn,parent_lid,tpnFLow,id_,ifCp) values(?,?,?,?,?,?,?,?,?,?)");
			pstForUpdate = conn.prepareStatement("update zz_turnkey_detail set ipn=?,lid=?,tpnFLow=?,pn=?,id=?,ifCp=? where id_=?");
			//准备No-Repeat 数据
			pst = conn.prepareStatement("select parent_lid,wid,ipn,id,lid,tpnFlow,status,pn,ifCp from zz_turnkey_detail");
			rst = pst.executeQuery();
//			logger.info("1");
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
				newMap.put("ifCp", rst.getString("ifCp"));
				mapOfDb2.put(rst.getString("parent_lid")+"_"+rst.getString("wid"), newMap);
			}
			//准备No-Repeat 数据 结束
			//先处理klt数据,把处理过的数据记录存入mapOfKltToday 后续如有相同数据,不处理  key为 parent_lid+"_"+wid
//			logger.info("2");
			Date date = new Date();
			SimpleDateFormat sf2 = new SimpleDateFormat("yy/MM/dd");
			pst = conn.prepareStatement("select wid,lid,pn,cpn,firm,ipn,tpnFlow,ifCp from cp_wip where erpDate = ? and firm ='klt' and qty>0 and tpnflow is not null");
			pst.setString(1, sf2.format(date));
			rst = pst.executeQuery();
			conn.setAutoCommit(false);
			while(rst.next()){
				List<String> list = new ArrayList<String>();
				String wid = rst.getString("wid");
				if(UtilValidate.isNotEmpty(wid)){
					String lid = rst.getString("lid");
//					logger.info("lid_"+lid);
					String ifCp = rst.getString("ifCp");
					/*if(lid.equals("AYCGR")){
						System.out.println("com ein");
					}
					logger.info("deal lid is "+lid+"_"+ wid);*/
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
					String parent_lid = lid;
					if(lid.contains(".")){
						parent_lid = lid.split("\\.")[0];
					}
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
							if(!pn.equals(dbHas.get("pn"))){
								hasUpdate = true;
							}
							if(UtilValidate.isNotEmpty(tpnFlow)){
								if(!tpnFlow.equals(dbHas.get("tpnFlow"))){
									hasUpdate = true;
								}
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
								if(UtilValidate.isNotEmpty(ifCp)&&ifCp.equals("Y")){
									pstForUpdate.setInt(5, 0);
									pstForUpdate.setString(6, "Y");
								}else{
									pstForUpdate.setInt(5, Integer.parseInt(dbHas.get("id")));
									pstForUpdate.setString(6, "N");
								}
								pstForUpdate.setString(7, parent_lid+"_"+s);
								pstForUpdate.addBatch();
							}else if(dbHas.get("status").equals("COMPLETED")&&Integer.parseInt(dbHas.get("id"))==1&&ifCp.equals("Y")){
								pstForUpdate.setString(1, ipn);
								pstForUpdate.setString(2, lid);
								pstForUpdate.setString(3, tpnFlow);
								pstForUpdate.setString(4, pn);
								pstForUpdate.setInt(5, 0);
								pstForUpdate.setString(6, "Y");
								pstForUpdate.setString(7, parent_lid+"_"+s);
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
							pstForAdd.setString(10, ifCp);
							// 把一个SQL命令加入命令列表
							pstForAdd.addBatch();
						}
					}
				}
			}
			logger.info("3");
			//非KLT WIP
			pst = conn.prepareStatement("select wid,lid,pn,cpn,firm,ipn,stage,tpnFlow,ifCp from cp_wip where erpDate = ? and firm !='klt' and tpnflow is not null");
			pst.setString(1, sf2.format(date));
			rst = pst.executeQuery();
			while(rst.next()){
				List<String> list = new ArrayList<String>();
				String wid = rst.getString("wid");
				if(UtilValidate.isNotEmpty(wid)){
//					logger.info("~~~~"+rst.getString("lid")+"_"+wid);
					/*if(rst.getString("lid").equals("PP3699")){
						System.out.println("come in");
					}*/
					String ifCp = rst.getString("ifCp");
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
					if (firm.equals("smic") ) {
						list = WaferIdFormat.getWaferIdFromSmic(wid);
					} else if (firm.equals("csmc")) {
						list = WaferIdFormat.getWaferIdList(wid);
					} else if (firm.contains("chipmos")) {
						String stage = rst.getString("stage");
						if (stage.equals("STOCK")) {
							list = WaferIdFormat.getWaferIdList(wid);
						} else {
							list = WaferIdFormat.getWaferIdFromChipmos(list,wid);
						}
					}else if (firm.equals("hlmc")|| firm.equals("umc")|| firm.equals("xmc")) {
						if(UtilValidate.isNotEmpty(wid)){
							list = WaferIdFormat.getWaferIdFromHlmc(wid);
						}
					}else if (firm.contains("sjsemi")) {
						list = WaferIdFormat.getWaferIdList(wid);
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
								if(!pn.equals(dbHas.get("pn"))){
									hasUpdate = true;
								}
								if(UtilValidate.isNotEmpty(tpnFlow)&&(!tpnFlow.equals(dbHas.get("tpnFlow")))){
									hasUpdate = true;
								}
								if(!lid.equals(dbHas.get("lid"))){
									hasUpdate = true;
								}
								String ifCp_old = dbHas.get("ifCp");
								if(UtilValidate.isEmpty(ifCp_old)){
									hasUpdate = true;
								}else{
									if(!ifCp_old.equals(ifCp)){
										hasUpdate = true;
									}
								}
								//更新
								if(hasUpdate){
									pstForUpdate.setString(1, ipn);
									pstForUpdate.setString(2, lid);
									pstForUpdate.setString(3, tpnFlow);
									pstForUpdate.setString(4, pn);
									if(ifCp.equals("Y")){
										pstForUpdate.setInt(5, 0);
										pstForUpdate.setString(6, "Y");
									}else{
										pstForUpdate.setInt(5, Integer.parseInt(dbHas.get("id")));
										pstForUpdate.setString(6, "N");
									}
									pstForUpdate.setString(7, parent_lid+"_"+s);
									pstForUpdate.addBatch();
								}else if(dbHas.get("status").equals("COMPLETED")&&Integer.parseInt(dbHas.get("id"))==1&&ifCp.equals("Y")){
									pstForUpdate.setString(1, ipn);
									pstForUpdate.setString(2, lid);
									pstForUpdate.setString(3, tpnFlow);
									pstForUpdate.setString(4, pn);
									pstForUpdate.setInt(5, 0);
									pstForUpdate.setString(6, "Y");
									pstForUpdate.setString(7, parent_lid+"_"+s);
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
								pstForAdd.setString(10, ifCp);
								// 把一个SQL命令加入命令列表
								pstForAdd.addBatch();
//								logger.info("batch after");
							}
						}
					}
				}
			}
			logger.info("Data Resolve To Table of zz_turnkey_detail Execute Update Start");
			int[] rowsOne =pstForUpdate.executeBatch();
			logger.info("Data Resolve To Table of zz_turnkey_detail Execute Create Start");
			int[] rowsTwo = pstForAdd.executeBatch();
			logger.info("Data Resolve To Table of zz_turnkey_detail, UpdateRows is "+rowsOne.length);
			logger.info("Data Resolve To Table of zz_turnkey_detail, AddRows is "+rowsTwo.length);
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
		logger.info("Data Resolve To Table of zz_turnkey_detail End!");
		return 0;
	}
	/*@Override
	public int dataResolveForPo() {
		// TODO Auto-generated method stub
		logger.info("Data Resolve For Po Start!");
		int returnNum = 0;
		PreparedStatement pst = null;
		ResultSet rst = null;
		Date date = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yy/MM/dd");
		Connection conn = Conn.getMysqlConnection();
		String sql = "select * from cp_wip where erpDate = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, sf2.format(date));
			// pst.setString(1, "13/08/25");
			rst = pst.executeQuery();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("insert into zz_turnkey_detail(wid,pn,lid,ipn,status,cpn) values(?,?,?,?,?,?)");
			int i = 0;
			while (rst.next()) {
				returnNum++;
				String wid = rst.getString("wid");
				String firm = rst.getString("firm");
				if (UtilValidate.isNotEmpty(wid)) {
					List<String> list = new ArrayList<String>();
					if (firm.equals("smic") || firm.equals("xmc")) {
						list = WaferIdFormat
								.getWaferIdFromSmic(wid);
					} else if (firm.equals("klt")
							|| firm.equals("csmc")) {
						list = WaferIdFormat.getWaferIdList(wid);
					} else if (firm.equals("chipmos")) {
						String stage = rst.getString("stage");
						if (stage.equals("STOCK")) {
							list = WaferIdFormat
									.getWaferIdList(wid);
						} else {
							list = WaferIdFormat
									.getWaferIdFromChipmos(list,
											wid);
						}
					}
					for (String s : list) {
						if (s.length() == 1) {
							s = "0" + s;
						}
						pst.setString(1, s);
						pst.setString(2, rst.getString("pn"));
						pst.setString(3, rst.getString("lid"));
						String ipn = null;
						if(UtilValidate.isNotEmpty(rst.getString("ipn"))&&(!rst.getString("ipn").equals("NULL"))){
							ipn = rst.getString("ipn");
						}
						pst.setString(4, ipn);
						pst.setString(5, "CREATED");
						pst.setString(6, rst.getString("cpn"));
						// 把一个SQL命令加入命令列表
						pst.addBatch();
					}
				}
			}
			logger.info(i);
			pst.executeBatch();
			// 语句执行完毕，提交本事务
			conn.commit();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from zz_turnkey_detail " +
					"where (lid,wid) in (select lid,wid from(select lid,wid from zz_turnkey_detail group by lid,wid having count(*)>1)dd) " +
					"and id not in(select id from (select min(id) id from zz_turnkey_detail group by lid,wid having count(*)>1)df)");
			pst.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Conn.close(rst, pst);
			Conn.closeConn(conn);
		}
		logger.info("Data Resolve For PO End!");
		return returnNum;
	}*/

	@Override
	public void helperOfWorkOrder() {
		// TODO Auto-generated method stub
		logger.info("Pass IPN From Erp Work Order Start!");
		ConnUtil connUtil = new ConnUtil();
		Connection connOfPi = connUtil.getMysqlConnection();
		PreparedStatement pst = null;
		ResultSet rst = null;
		ResultSet rst2 = null;
		Connection	connOfBj = connUtil.getOracleConnection();
		Connection	connOfHk =  connUtil.getOracleConnectionHk();
		Connection connOfErp = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		StringBuilder idlist = new StringBuilder();
		String ipn_new = null;
		List<String> piList = new ArrayList<String>();
		try {
			pst = connOfPi.prepareStatement("select id,action,db,status,workOrderNumber from zz_work_order_status where status='CREATED' and action='EXAMINE'");
			rst2 = pst.executeQuery();
			connOfPi.setAutoCommit(false);
			while(rst2.next()){
				int id = rst2.getInt("id");
				logger.info("pass erp ipn : deal with id is "+id);
				String action  = rst2.getString("action");
				String db = rst2.getString("db");
				String workOrderNumber = rst2.getString("workOrderNumber");
				if(db.equals("dsbj")){
					connOfErp = connOfBj;
				}else if(db.equals("dshk")){
					connOfErp =  connOfHk;
				}
				pst = connOfErp.prepareStatement("select idd04,idd05,sfb05 from idd_file d"+
									" left join sfq_file s on s.sfq01=d.idd10"+
									" left join sfb_file sb on sb.sfb01 = s.sfq02"+
									" where sfq02='"+workOrderNumber+"'");
				rst = pst.executeQuery();
				if(action.equals("EXAMINE")){
					pst = connOfPi.prepareStatement("update zz_turnkey_detail set ipn_new =?,status='COMEPLETED' where parent_lid = ? and wid = ? and status!='COMEPLETED'");
					pst2 = connOfPi.prepareStatement("update t_fabside_wip set ipn_real=? where lotid=? and waferid = ?");
					pst3 = connOfPi.prepareStatement("insert into zz_wip_temporary(ipn,lid,wid,status) values(?,?,?,?)");
					while(rst.next()){
						logger.info("deal with "+rst.getString("idd04")+"_"+rst.getString("idd05"));
						String[] strs =  rst.getString("idd04").split("\\.");
						pst.setString(1, rst.getString("sfb05"));
						pst.setString(2, strs[0]);
						pst.setString(3, rst.getString("idd05"));
						int num = pst.executeUpdate();
						if(num<1){
							pst3.setString(1, rst.getString("sfb05"));
							pst3.setString(2, strs[0]);
							pst3.setString(3, rst.getString("idd05"));
							pst3.setString(4, "CREATED");
							pst3.addBatch();
						}
						pst2.setString(1, rst.getString("sfb05"));
						pst2.setString(2, strs[0]);
						pst2.setString(3, rst.getString("idd05"));
						int rnum = pst2.executeUpdate();
						if(rnum<=0){
							ipn_new = rst.getString("sfb05");
							piList.add(rst.getString("idd04")+"_"+rst.getString("idd05"));
							logger.info(ipn_new+":pi sys has no info "+rst.getString("idd04")+"_"+rst.getString("idd05"));
						}
					}
					pst3.executeBatch();
				}/*else{
					
				}*/
				pst = connOfPi.prepareStatement("update zz_work_order_status set status='COMPLETED' where id ="+id);
				pst.executeUpdate();
				connOfPi.commit(); 
			}
			//发MAIL
			if(UtilValidate.isNotEmpty(piList)){
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.close(rst2, pst2);
			ConnUtil.close(null, pst3);
			ConnUtil.closeConn(connOfPi);
			ConnUtil.closeConn(connOfErp);
			ConnUtil.closeConn(connOfBj);
			ConnUtil.closeConn(connOfHk);
		}
			logger.info("Pass IPN From Erp Work Order End!");
	}

	@Override
	public void PassDataResolveToChart() {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		String erpDate = sf.format(date);
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		String query = "select * from z_wip_detail where erpdate=?";
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rst = null;
		try {
			pst2 = conn.prepareStatement("insert into z_wip_detail_chart(id,lid,qty,wid,status,erpdate,tpnFlow,productNo) values(?,?,?,?,?, ?,?,?)");
			conn.setAutoCommit(false);
//			pst = conn.prepareStatement("delete from z_wip_detail_chart where erpDate!=?");
			pst = conn.prepareStatement("delete from z_wip_detail_chart");
			pst.executeUpdate();
			pst = conn.prepareStatement(query);
			pst.setString(1, erpDate);
			rst = pst.executeQuery();
			while(rst.next()){
				pst2.setInt(1, rst.getInt("id"));
				pst2.setString(2, rst.getString("lid"));
				pst2.setString(3, rst.getString("qty"));
				pst2.setString(4, rst.getString("wid"));
				pst2.setString(5, rst.getString("status"));
				pst2.setString(6, rst.getString("erpdate"));
				pst2.setString(7, rst.getString("tpnFlow"));
				pst2.setString(8, rst.getString("productNo"));
				pst2.addBatch();
			}
			pst2.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
			ConnUtil.close(null, pst2);
		}
	}

	@Override
	public synchronized int passCpWipLatest() {
		// TODO Auto-generated method stub
//		logger.info("Pass Cp Wip Start!");
		ConnUtil connUtil = new ConnUtil();
		int returnNum = 0;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		PreparedStatement pst4 = null;
		ResultSet rst4 = null;
		Date date = new Date();
		Connection connOfPi = connUtil.getMysqlConnection();
		Connection currentConn = null;
		List<Map<String,Object>> listOfMap = new ArrayList<Map<String,Object>>();
		if (connOfPi != null) {
			// 查询PI系统中CP可出货记录
//			String sql = "select id,waferid,lotid,grade,productId,filePath,tpnId,erpProgram,ipn_real from t_fabside_wip where ((status in('Inkless map available','OP ship','OP to do') and abnormal='M') or (status in('Inkless map available','OP ship') and abnormal<>'M' and abnormal not like '%T%') or (status ='ERP to do') or (status ='Mapping to do')) and ipn is null and lotid is not null";
//			String sql = "select id,waferid,lotid,grade,productId,filePath,tpnId,erpProgram,ipn_real from t_fabside_wip where status ='ERP to do' and ipn is null and lotid is not null";
			String sql = "select id,waferid,lotid,grade,productId,filePath,tpnId,erpProgram,ipn_real,erpProgramTime,kgdRemarks from t_fabside_wip where status in('ERP to do','Mapping to do') and ipn is null and lotid is not null";
			String sqlOfQty = "select count(*) qty from t_fabside_wip where status in('ERP to do','Mapping to do') and ipn is null and lotid is not null";
			// 更新Pi系统
			List<String> updateWipId = new ArrayList<String>();
			try {
				pst2 = connOfPi.prepareStatement(sqlOfQty);
				rst2 = pst2.executeQuery();
				while(rst2.next()){
					logger.info("Pass Cp Wip Prepare Passing Number of Wips is "+rst2.getInt("qty"));
				}
				pst2 = connOfPi.prepareStatement(sql);
				rst2 = pst2.executeQuery();
				// 插入TIPTOP语句
				String insertSql = "insert into tc_cpi_file"
						+ "(tc_cpi01,tc_cpi02,tc_cpi03,tc_cpi05,tc_cpi06,tc_cpi07,"
						+ "tc_cpi10,tc_cpi11,tc_cpi09,tc_cpi12,tc_cpi04,tc_cpi08,tc_cpi13,"
						+ "tc_cpi14,tc_cpi15,tc_cpi16,tc_cpi17,tc_cpi18) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				while (rst2.next()) {
					updateWipId.add(rst2.getString("id"));
					Map<String,Object> newMap = new HashMap<String,Object>();
					String firm = " ";
					String lid = " ";
					String ipn = rst2.getString("ipn_real");
					String cpn = " ";
					//查询yield
					String yield = " ";
					String sqlOfYield = "select lotid,waferid,yield from t_fabside_yield where lotid=? and waferid= ? and stage='CP2' and lotid is not null and lotid!=' ' order by starttime desc limit 0,1";
					pst4 = connOfPi.prepareStatement(sqlOfYield);
					String lotid = rst2.getString("lotid");
					if(lotid.contains(".")){
						pst4.setString(1, rst2.getString("lotid").substring(0, lotid.indexOf(".")));
					}else{
						pst4.setString(1, lotid);
					}
					pst4.setString(2, rst2.getString("waferid"));
					rst4 = pst4.executeQuery();
					while (rst4.next()) {
						yield = rst4.getString("yield");
					}
					// 查询z_wip_detail 得到ipn fabid
					/*String sqlOfWip = "select ipn,cpn,lid,wid,location,firm from z_wip_detail where lid like ? and wid = ? and (location not in('wafer','B1','FAB7','S1','Fab12') or location is null) and pn not like '%-7%' order by lid,wid,firm,senddate desc limit 0,1";
					pst4 = connOfPi.prepareStatement(sqlOfWip);
					pst4.setString(1, rst2.getString("lotid") + "%");
					pst4.setString(2, rst2.getString("waferid"));
					rst4 = pst4.executeQuery();
					String location = " ";
					String detail_firm = " ";
					while (rst4.next()) {
						lid = rst4.getString("lid");
						location = rst4.getString("location");
						cpn = rst4.getString("cpn");
						detail_firm = rst4.getString("firm");
					}
					if (detail_firm.equals("smic")) {
						firm = "SMIC-SH";
					} else if (detail_firm.equals("xmc")) {
						if (location.equals("SH_TESTING")) {
							firm = "XMC-SH";
						} else {
							firm = "XMC-WH";
						}
					} else if (detail_firm.equals("klt")) {
						firm = "KLT";
					} else if (detail_firm.equals("chipmos")) {
						firm = "CHIPMOS";
					}*/
					newMap.put("firm", firm);
					newMap.put("productId", rst2.getString("productId"));
					// IPN
					if(UtilValidate.isEmpty(ipn)){
						ipn = " ";
					}
					newMap.put("ipn", ipn);
					if (lid.equals(" ")) {
						lid = rst2.getString("lotid");
					}
					newMap.put("lid", lid);
					newMap.put("waferid", rst2.getString("waferid"));
					if(UtilValidate.isEmpty(cpn)){
						cpn = " ";
					}
					newMap.put("cpn", cpn);
					Timestamp time = rst2.getTimestamp("erpProgramTime");
					String time_ = time.toString();
					String[] times = time_.split(" ");
					newMap.put("date", times[0]);
					newMap.put("time", times[1].substring(0,8));
					newMap.put("grade", rst2.getString("grade"));
					newMap.put("yield", yield);
					newMap.put("tpnId", rst2.getString("tpnId"));
					newMap.put("erpProgram", rst2.getString("erpProgram"));
					String kgdRemarks = rst2.getString("kgdRemarks");
					if(UtilValidate.isEmpty(kgdRemarks)){
						kgdRemarks = " ";
					}
					newMap.put("kgdRemarks", kgdRemarks);
					listOfMap.add(newMap);
					returnNum++;
				}
				if(UtilValidate.isNotEmpty(listOfMap)){
					List<String> listOfDb = new ArrayList<String>();
					listOfDb.add("dsbj");
					/*listOfDb.add("dshk");
					listOfDb.add("dssh");
					listOfDb.add("dshf");*/
					for(String db : listOfDb){
						currentConn = connUtil.getOracleConnection(db,db);
						currentConn.setAutoCommit(false);
						pst2 = null;
						pst2 = currentConn.prepareStatement(insertSql);
						for(Map<String,Object> submap : listOfMap){
							pst2.setString(1, (String)submap.get("firm"));
							pst2.setString(2, (String)submap.get("productId"));
							pst2.setString(3, (String)submap.get("ipn"));
							pst2.setString(4, (String)submap.get("lid"));
							pst2.setString(5, (String)submap.get("waferid"));
							pst2.setInt(6, 1);
							String dd = (String)submap.get("date");
							SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd");
							Date d = sdf_.parse(dd);
							pst2.setDate(7, new java.sql.Date(d.getTime()));
//							pst2.setString(8, (String)submap.get("time"));
							pst2.setString(8, "00:00:00");
							pst2.setString(9, "N");
							pst2.setString(10, "N");
							pst2.setString(11, (String)submap.get("cpn"));
							pst2.setDouble(12, 0);
							pst2.setString(13, (String)submap.get("grade"));
							pst2.setString(14, (String)submap.get("yield"));
							pst2.setString(15, (String)submap.get("kgdRemarks"));
							pst2.setString(16, (String)submap.get("tpnId"));
							pst2.setString(17, (String)submap.get("erpProgram"));
							pst2.setString(18, (String)submap.get("kgdRemarks"));
							pst2.addBatch();
						}
						pst2.executeBatch();
						ConnUtil.closeConn(currentConn);
					}
				}
				if (UtilValidate.isNotEmpty(updateWipId)) {
					StringBuffer strs = new StringBuffer();
					for (String str : updateWipId) {
						strs.append(",'" + str + "'");
					}
					connOfPi.setAutoCommit(false);
					pst2 = connOfPi.prepareStatement("update t_fabside_wip set ipn='Y' where id in ("+ strs.toString().substring(1) + ")");
					pst2.executeUpdate();
					connOfPi.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ConnUtil.close(rst2, pst2);
				ConnUtil.close(rst4, pst4);
				ConnUtil.closeConn(connOfPi);
				ConnUtil.closeConn(currentConn);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ConnUtil.close(rst2, pst2);
				ConnUtil.close(rst4, pst4);
				ConnUtil.closeConn(connOfPi);
				ConnUtil.closeConn(currentConn);
			}
		}
		logger.info("Pass Cp Wip ReturnNum is "+returnNum);
		return returnNum;
	}

	

}
