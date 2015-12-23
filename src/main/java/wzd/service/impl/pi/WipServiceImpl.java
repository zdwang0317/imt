package wzd.service.impl.pi;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wzd.dao.IBaseDao;
import wzd.model.pi.ToptionContent;
import wzd.model.pi.TprodContent;
import wzd.model.pi.Ttpn;
import wzd.model.pi.TtpnStage;
import wzd.model.pi.TturnkeyDetail;
import wzd.model.pi.TturnkeyOrderItemDetail;
import wzd.model.pi.Twip;
import wzd.model.pi.TwipDetail;
import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.Series;
import wzd.pageModel.pi.Wip;
import wzd.pageModel.pi.WipCompare;
import wzd.pageModel.pi.WipDetailUnique;
import wzd.service.IWipService;
import wzd.util.ConnUtil;
import wzd.util.PiUtil;
import wzd.util.TableOfMysqlUtil;
import wzd.util.UtilValidate;
import wzd.util.WaferIdFormat;

@Service("wipService")
public class WipServiceImpl implements IWipService {
	private static Logger logger = Logger.getLogger(WipServiceImpl.class.getName());
	
	private IBaseDao<Twip> wipDao;
	private IBaseDao<TwipDetail> wipDetailDao;
	private IBaseDao<Ttpn> tpnDao;
	private IBaseDao<TtpnStage> stageDao;
	private IBaseDao<ToptionContent> optionDao;
	private IBaseDao<TprodContent> prodDao;
	private IBaseDao<TturnkeyDetail> turnkeyDetailDao;
	private IBaseDao<TturnkeyOrderItemDetail> turnkeyOrderItemDetail;
	@Autowired
	public void setTurnkeyOrderItemDetail(
			IBaseDao<TturnkeyOrderItemDetail> turnkeyOrderItemDetail) {
		this.turnkeyOrderItemDetail = turnkeyOrderItemDetail;
	}
	@Autowired
	public void setTurnkeyDetailDao(IBaseDao<TturnkeyDetail> turnkeyDetailDao) {
		this.turnkeyDetailDao = turnkeyDetailDao;
	}
	@Autowired
	public void setProdDao(IBaseDao<TprodContent> prodDao) {
		this.prodDao = prodDao;
	}
	@Autowired
	public void setOptionDao(IBaseDao<ToptionContent> optionDao) {
		this.optionDao = optionDao;
	}
	public IBaseDao<Twip> getWipDao() {
		return wipDao;
	}
	@Autowired
	public void setWipDao(IBaseDao<Twip> wipDao) {
		this.wipDao = wipDao;
	}
	public IBaseDao<TwipDetail> getWipDetailDao() {
		return wipDetailDao;
	}
	@Autowired
	public void setWipDetailDao(IBaseDao<TwipDetail> wipDetailDao) {
		this.wipDetailDao = wipDetailDao;
	}
	public IBaseDao<Ttpn> getTpnDao() {
		return tpnDao;
	}
	@Autowired
	public void setTpnDao(IBaseDao<Ttpn> tpnDao) {
		this.tpnDao = tpnDao;
	}

	public IBaseDao<TtpnStage> getStageDao() {
		return stageDao;
	}
	@Autowired
	public void setStageDao(IBaseDao<TtpnStage> stageDao) {
		this.stageDao = stageDao;
	}

	public IBaseDao<Object[]> wiplistDao;

	public IBaseDao<Object[]> getWiplistDao() {
		return wiplistDao;
	}

	@Autowired
	public void setWiplistDao(IBaseDao<Object[]> wiplistDao) {
		this.wiplistDao = wiplistDao;
	}

	@Override
	public DataGrid datagrid(Wip wip) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from Twip t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(wip, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(wip, hql);
		List<Twip> l = wipDao.find(hql, params, wip.getPage(), wip.getRows());
		List<Wip> nl = new ArrayList<Wip>();
		changeModel(l, nl);
		dg.setTotal(wipDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	@Override
	public DataGrid datagridBySql(Wip wip) {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		int rows = 0;
		List<Wip> nl = new ArrayList<Wip>();
		DataGrid dg = new DataGrid();
		String erpDate = wip.getErpDate().replace("-", "").substring(0, 6);
		if(UtilValidate.isNotEmpty(erpDate)){
			Connection connOfPi = connUtil.getMysqlConnectionByName("cp_wip_history");
//			boolean zhouQi = erpDate.contains(":");
			String table = "t_"+erpDate;
			boolean hasTable = TableOfMysqlUtil.checkTableExist(connOfPi, table);
			if(hasTable){
				String hql = "select id,startDate,status,pn,cpn,ipn,lid,qty,wid,stage,remLayer,location,sendDate,firm,erpDate,tpnFlow,productNo from "+table+" where 1=1 ";
				hql = addWhereBySql(wip, hql);
				if(wip.getPage()==0){
					wip.setPage(1);
				}
				hql = hql+" order by sendDate desc,lid,wid limit "+(wip.getPage() - 1) * wip.getRows()+","+wip.getRows();
				String totalHql = "select count(*) as rows from "+table+" where 1=1 " + addWhereBySql(wip, "");
		//		Connection connOfPi = connUtil.getMysqlConnection();
				PreparedStatement pst = null;
				ResultSet rst = null;
				try {
					pst = connOfPi.prepareStatement(hql);
					rst = pst.executeQuery();
					while(rst.next()){
						Wip w = new Wip();
						w.setId(rst.getInt("id"));
						w.setPn(rst.getString("pn"));
						w.setCpn(rst.getString("cpn"));
						w.setIpn(rst.getString("ipn"));
						w.setLid(rst.getString("lid"));
						w.setQty(rst.getInt("qty"));
						w.setWid(rst.getString("wid"));
						w.setStage(rst.getString("stage"));
						w.setStatus(rst.getString("status"));
						w.setRemLayer(rst.getString("remLayer"));
						w.setLocation(rst.getString("location"));
						w.setSendDate(rst.getDate("sendDate"));
						w.setStartDate(rst.getDate("startDate"));
						w.setFirm(rst.getString("firm"));
						w.setErpDate(rst.getString("erpDate"));
						w.setTpnFlow(rst.getString("tpnFlow"));
						w.setProductNo(rst.getString("productNo"));
						nl.add(w);
					}
					pst = connOfPi.prepareStatement(totalHql);
					rst = pst.executeQuery();
					while(rst.next()){
						rows = rst.getInt("rows");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dg.setTotal(Long.valueOf(rows));
			dg.setRows(nl);
		}
		return dg;
	}
	private String addWhereBySql(Wip wip, String hql) {
		if (UtilValidate.isNotEmpty(wip.getPn())) {
			hql += " and pn like '%" + wip.getPn().trim() + "%'";
		}
		if (UtilValidate.isNotEmpty(wip.getLid())) {
			hql += " and lid like '%" + wip.getLid().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getProductNo())){
			hql += " and productNo like '%" + wip.getProductNo().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getErpDate())){
			hql += " and erpDate = '"+wip.getErpDate().substring(2).replace("-", "/")+"'";
		}
		if(UtilValidate.isNotEmpty(wip.getTpnFlow())){
			hql += " and tpnFlow like '%" + wip.getTpnFlow().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getLocation())){
			hql += " and location like '%" + wip.getLocation().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getCpn())){
			hql += " and cpn like '%" + wip.getCpn().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			hql += " and ipn like '%" + wip.getIpn().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getStage())){
			hql += " and stage like '%" + wip.getStage().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getStatus())){
			hql += " and status like '%" + wip.getStatus().trim() + "%'";
		}
		if(UtilValidate.isNotEmpty(wip.getFirm())){
			hql += " and firm like '%" + wip.getFirm().trim() + "%'";
		}
		return hql;
	}
	private String addWhere(Wip wip, String hql, Map<String, Object> params) {
		if (UtilValidate.isNotEmpty(wip.getLid())) {
			hql += " and t.lid like :lid";
			params.put("lid", "%%" + wip.getLid().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getPn())){
			hql += " and t.pn like :pn";
			params.put("pn", "%%" + wip.getPn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getProductNo())){
			hql += " and t.productNo like :productNo";
			params.put("productNo", "%%" + wip.getProductNo().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getErpDate())){
			hql += " and t.erpDate = :erpDate";
			params.put("erpDate", wip.getErpDate().substring(2).replace("-", "/"));
		}
		if(UtilValidate.isNotEmpty(wip.getTpnFlow())){
			hql += " and t.tpnFlow like :tpnFlow";
			params.put("tpnFlow", "%%" + wip.getTpnFlow().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getLocation())){
			hql += " and t.location like :location";
			params.put("location", "%%" + wip.getLocation().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getCpn())){
			hql += " and t.cpn like :cpn";
			params.put("cpn", "%%" + wip.getCpn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			hql += " and t.ipn like :ipn";
			params.put("ipn", "%%" + wip.getIpn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getStage())){
			hql += " and t.stage like :stage";
			params.put("stage", "%%" + wip.getStage().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getStatus())){
			hql += " and t.status like :status";
			params.put("status", "%%" + wip.getStatus().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getFirm())){
			hql += " and t.firm like :firm";
			params.put("firm", "%%" + wip.getFirm().trim() + "%%");
		}
		return hql;
	}

	private String addOrder(Wip wip, String hql) {
		hql += " order by t.sendDate desc,t.lid,t.wid";
		return hql;
	}

	private void changeModel(List<Twip> l, List<Wip> nl) {
		if (l != null && l.size() > 0) {
			for (Twip t : l) {
				Wip u = new Wip();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	
	private void changeModel2(List<TwipDetail> l, List<TwipDetail> nl) {
		if (l != null && l.size() > 0) {
			for (TwipDetail t : l) {
				TwipDetail u = new TwipDetail();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	
	private void changeModel3(List<TturnkeyDetail> l, List<WipDetailUnique> nl) {
		if (l != null && l.size() > 0) {
			for (TturnkeyDetail t : l) {
				WipDetailUnique u = new WipDetailUnique();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	@Override
	public int analysisProductNo() {
		// TODO Auto-generated method stub
		logger.info("AnalysisProductNo Start!");
		String hql = "from Twip t where t.productNo is null";
		List<Twip> l = wipDao.find(hql);
		/*
		 * the start of prepare stage info 
		 */
		String sqlOfStage = "from TtpnStage t order by t.firm,t.stageOrder";
		List<TtpnStage> listOfSh = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfWh = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfKlt = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfChipmos = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfStageDb = stageDao.find(sqlOfStage);
		for(TtpnStage t: listOfStageDb){
			if(t.getFirm().equals("CHIPMOS")){
				listOfChipmos.add(t);
			}else if(t.getFirm().equals("KLT")){
				listOfKlt.add(t);
			}else if(t.getFirm().equals("SH_TESTING")){
				listOfSh.add(t);
			}else if(t.getFirm().equals("WH_TESTING")){
				listOfWh.add(t);
			}
		}
		Map<String,List<TtpnStage>> mapOfFactory = new HashMap<String,List<TtpnStage>>();
		mapOfFactory.put("CHIPMOS", listOfChipmos);
		mapOfFactory.put("KLT", listOfKlt);
		mapOfFactory.put("SH_TESTING", listOfSh);
		mapOfFactory.put("WH_TESTING", listOfWh);
		/*
		 * the end of prepare stage info
		 */
		ConnUtil connUtil = new ConnUtil();
		Connection connOfPi = connUtil.getMysqlConnection();
		int returnNum = 0;
		try {
			connOfPi.setAutoCommit(false);
			PreparedStatement pst = null;
			pst = connOfPi.prepareStatement("select pn,tpn,remLayer,nm from z_tpn_product");
			ResultSet rst = pst.executeQuery();
			Map<String, Object> mapOfTpn = new HashMap<String, Object>();
			//the info of product's nm
			/*Map<String, Object> mapOfNm = new HashMap<String, Object>();*/
			/*Map<String, String> mapOfNmOfTpn = new HashMap<String, String>();*/
			while (rst.next()) {
				mapOfTpn.put(rst.getString("pn") + String.valueOf(rst.getInt("remLayer")),rst.getString("tpn"));
				/*mapOfNm.put(rst.getString("pn"), rst.getString("nm"));*/
			}
			/*pst = connOfPi.prepareStatement("select pn,nm from z_tpn_product group by pn");
			rst = pst.executeQuery();
			while (rst.next()) {
				mapOfNmOfTpn.put(rst.getString("pn"), rst.getString("nm"));
			}*/
			pst = connOfPi.prepareStatement("update cp_wip set productNo=?,tpnFlow=? where id =?");
			int maxNum = 0;
			for (Twip t : l) {
				String productNo = PiUtil.filterByProductNo(t.getPn());
				String tpnflow = null;
//				logger.info(t.getId()+"_"+productNo);
				//logger.info(returnNum + "_" + productNo+"_"+t.getId());
				if (UtilValidate.isNotEmpty(productNo)) {
					//tpnflow logic
					//String tpnflow = PiUtil.getTpnFLow(productNo,t,mapOfTpn,mapOfFactory,String.valueOf(mapOfNm.get(productNo)));
					String location = t.getLocation();
					// FAB
					// if location is not null and location doesn't contain TESTING and CP
					if ((UtilValidate.isNotEmpty(location)&&(!location.contains("TESTING"))&&(!location.contains("CP")))) {
						boolean checkSmicLocation = true;
						if(t.getFirm().equals("smic")&&((!location.equals("B1"))&&(!location.equals("CP"))&&(!location.equals("FAB7")))){
							checkSmicLocation = false;
						}
						// 如果remLayer不为空
						if (UtilValidate.isNotEmpty(t.getRemLayer())&&checkSmicLocation) {
							String pn = "";
							if (productNo.length() > 4) {
								pn = productNo.substring(0, 4);
							} else {
								pn = productNo;
							}
							String remLayer = t.getRemLayer().trim();
							int checkNum = remLayer.indexOf("/");
							if (checkNum == 0) { // format /xxx
								remLayer = "0";
							} else if (checkNum > 0) { // format xxx/xxx
								remLayer = remLayer.substring(0, checkNum);
							}
							if (remLayer.equals("0")) {
								String stage = t.getStage();
								if (UtilValidate.isNotEmpty(stage)) {
									stage = stage.toUpperCase();
									if (stage.contains("PAD")||stage.contains("PAD-PH")||stage.contains("PAD-DEP")||stage.contains("PAD-ET")) {
										tpnflow = "PAD";
									}else if(stage.contains("PAS")||stage.contains("FIN-ALLY")){
										tpnflow = "PAS2";
									}else if(stage.contains("UV")||stage.contains("WF-MARK")){
										tpnflow = "UV";
									}else if(stage.contains("WAT")||stage.contains("FIN-WAT")){
										tpnflow = "WAT";
									}else if(stage.contains("QC-INSP")){
										tpnflow = "QC";
									}else if(stage.contains(" ")||stage.contains("INVENTORY")||stage.contains("INV")||stage.contains("QC-PACK")||
											stage.contains("QC1-PACK")||stage.contains("QC-INSP")||stage.contains("QC1-INSP")||stage.contains("CRYSTAL-STRIP")||
											stage.contains("OUT-INSP")||stage.contains("OUT-INSP1")){
										tpnflow = "INV1";
									}
								}
							} else {
								String keyOfMap = pn + (int) Double.parseDouble(remLayer);
								String tpn = null;
								if((int) Double.parseDouble(remLayer)>=760){
									tpn = "PAS1";
								}else{
									tpn = String.valueOf(mapOfTpn.get(keyOfMap));
								}
								if (UtilValidate.isNotEmpty(tpn) && !tpn.equals("null")) {
									tpnflow = tpn;
								}
							}

						}
					}else{	// CP Stage
						String firm = t.getFirm();
//						logger.info("____________2:"+"_stage:"+t.getStage()+"_location:"+t.getLocation()+"_firm:"+t.getFirm());
						//logger.info("Entering application.");
						//Confirm WIP Factory 
						String firmName = null;
						if(UtilValidate.isNotEmpty(firm)){
							if(firm.equals("chipmos")){
								firmName = "CHIPMOS";
							}else if(firm.equals("klt")){
								firmName = "KLT";
							}else if(firm.equals("smic")||firm.equals("xmc")){
								String loc = t.getLocation();
								if(UtilValidate.isNotEmpty(loc)){
									if(loc.equals("CP")||loc.equals("SH_TESTING")){
										firmName = "SH_TESTING";
									}else if(loc.equals("WH_TESTING")){
										firmName = "WH_TESTING";
									}
								}
							}
						}
//						logger.info("firmName is "+firmName);
						if(UtilValidate.isNotEmpty(firmName)){
							String stage = t.getStage();
							if(UtilValidate.isNotEmpty(stage)){
								if(firmName.equals("CHIPMOS")){
									for(TtpnStage type1 : listOfChipmos){
										if(stage.equals("BAK")||stage.equals("WBK")||stage.equals("FBK")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(stage.equals("FPOE")||stage.equals("FPOE2")||stage.equals("FPOE")||stage.equals("FPOE2")){
											tpnflow = PiUtil.processSpecialStage(stage);
										}else if(stage.contains(type1.getStage())){
											tpnflow = type1.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("KLT")){
									for(TtpnStage type2 : listOfKlt	){
										if(stage.equals("BAKE")||stage.equals("DIEBANK")||stage.equals("DIEBANK1")){
											tpnflow = PiUtil.processSpecialStage(stage);
										}else if(t.getStage().contains(type2.getStage())){
											tpnflow = type2.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("SH_TESTING")){
									for(TtpnStage type3 : listOfSh	){
										if(stage.equals("Baking")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(t.getStage().contains(type3.getStage())){
											tpnflow = type3.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("WH_TESTING")){
									for(TtpnStage type4 : listOfWh	){
										logger.info(type4.getStage());
										if(stage.toUpperCase().contains("BAK")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(t.getStage().contains(type4.getStage())){
											tpnflow = type4.getTpn().getTpn();
											break;
										}
									}
								}
							}
//							logger.info("tpnflow is "+tpnflow);
						}
					}
					//end
					int id = t.getId();
					pst.setString(1, productNo);
					pst.setString(2, tpnflow);
					pst.setInt(3, id);
//					logger.info("id:"+t.getId()+"_p:"+productNo+"_t:"+tpnflow);
					pst.addBatch();
					if (maxNum > 1000) {
						pst.executeBatch();
						connOfPi.commit();
						maxNum = 0;
					}
					maxNum++;
					returnNum++;
				}
			}
			pst.executeBatch();
			connOfPi.commit();
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(connOfPi);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("AnalysisProductNo End!");
		return returnNum;
	}

	@Override
	public String getTpnToXAxis() {
		// TODO Auto-generated method stub
		String hql = "from Ttpn t where t.status = 'VALID' and t.ruleTypeId='FLASH' order by t.tpnOrder";
		List<Ttpn> l = tpnDao.find(hql);
		StringBuffer sb = new StringBuffer();
		for (Ttpn t : l) {
			sb.append("," + t.getTpn());
		}
		return sb.toString().substring(1);
	}

	@Override
	public List<Series> getDataToCharts() {
		// TODO Auto-generated method stub
		List<Series> returnList = new ArrayList<Series>();
		String mql = "from Ttpn t where t.status = 'VALID' and t.ruleTypeId='FLASH'";
		List<Ttpn> tpnList = tpnDao.find(mql);
		Map<String, Integer> mapOfTpn = new HashMap<String, Integer>();
		for (Ttpn t : tpnList) {
			mapOfTpn.put(t.getTpn(), t.getTpnOrder());
		}
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		// String hql =
		// "select sum(qty),productNo,tpnflow from Twip  where tpnflow is not null group by productNo,tpnflow";
		/*String hql = "select sum(t.qty),t.productNo,t.tpnFlow from TwipDetail t where t.tpnFlow is not null and t.erpDate = '"
				+ sf.format(new Date()) + "' group by t.productNo,t.tpnFlow order by t.productNo";*/
		String hql = "select sum(t.qty),t.productNo,t.tpnFlow from TwipDetailChart t where t.status='Y' and t.productNo not like '5220%' and t.productNo not like '5973%' and t.productNo not like '5688%' and t.productNo not like '5232%' and t.productNo not like '6007%'" +
				" and t.erpDate = '"+ sf.format(new Date()) + "' group by t.productNo,t.tpnFlow order by t.productNo";
		List<Object[]> l = wiplistDao.find(hql);
		Map<String, Series> mapOfSeries = new HashMap<String, Series>();
		List<String> listOfProductNoOrder = new ArrayList<String>();
		String record = null;
		for (Object[] o : l) {
			String name = o[1].toString();
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
				data = new int[tpnList.size()];
				series.setName(o[1].toString());
			} else {
				data = series.getData();
			}
			data[mapOfTpn.get(o[2].toString())] = Integer.parseInt(o[0]
					.toString());
			series.setData(data);
			mapOfSeries.put(o[1].toString(), series);
		}
		/*for (Map.Entry<String, Series> entry : mapOfSeries.entrySet()) {
			returnList.add(entry.getValue());
		}*/
		for(String o:listOfProductNoOrder){
			//logger.info(o);
			returnList.add(mapOfSeries.get(o));
		}
		return returnList;
	}

	@Override
	public DataGrid datagridOfDetail(Wip wip) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		String hql = "from TwipDetail t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(wip, hql, params);
		String totalHql = "select count(*) " + hql;
		hql = addOrder(wip, hql);
		List<TwipDetail> l = wipDetailDao.find(hql, params, wip.getPage(), wip.getRows());
		List<TwipDetail> nl = new ArrayList<TwipDetail>();
		changeModel2(l, nl);
		dg.setTotal(wipDetailDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	/*
	 * 每天WIP数据进来后更新t_fabside_wip表中的productId
	 * @see wzd.service.IWipService#updateProductNoOfPi()
	 */
	@Override
	public void updateProductNoOfPi() {
		// TODO Auto-generated method stub
		logger.info("UpdateProductNoOfPi Start!");
		PreparedStatement pst = null;
		ResultSet rst = null;
		Date date = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yy/MM/dd");
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		String sqlOfQuery = "select productNo,lid,wid from z_wip_detail where erpDate = ? and tpnflow in('PAS1','PAD','PAS2','UV','WAT','QC','INV1') order by productNo,lid";
		String sqlOfUpdate = "update t_fabside_wip set latestPn = 'Y',productId = ? where lotid = ? and waferid in (%s) and latestPn<>'Y'";//此处改成调用WebService
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sqlOfQuery);
			pst.setString(1, sf2.format(date));
			rst = pst.executeQuery();
			String lotId = "";
			String productNo = "";
			List<String> ids = null;
			while (rst.next()) {
				String lid = rst.getString("lid");
				String wid = rst.getString("wid");
				String pn = rst.getString("productNo");
				if(lotId.equals(lid)&&productNo.equals(pn)){
					ids.add(wid);
				}else{
					if(!lotId.equals("")){
						//logger.info(String.format(sqlOfUpdate, preparePlaceHolders(ids.size())));
						pst = conn.prepareStatement(String.format(sqlOfUpdate, preparePlaceHolders(ids.size())));
						pst.setString(1, pn);
						if(lid.contains(".")){
							pst.setString(2, lid.substring(0, lid.indexOf(".")));
						}else{
							pst.setString(2, lid);
						}
						setValues(pst, ids);
						pst.executeUpdate();
					}
					ids = new ArrayList<String>();
					lotId = lid;
					productNo = pn;
					ids.add(wid);
				}
			}
			//logger.info(String.format(sqlOfUpdate, preparePlaceHolders(ids.size())));
			pst = conn.prepareStatement(String.format(sqlOfUpdate, preparePlaceHolders(ids.size())));
			pst.setString(1, productNo);
			if(lotId.contains(".")){
				pst.setString(2, lotId.substring(0, lotId.indexOf(".")));
			}else{
				pst.setString(2, lotId);
			}
			setValues(pst, ids);
			pst.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		logger.info("UpdateProductNoOfPi End!");
	}
	
	public static String preparePlaceHolders(int length) {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < length;) {
	        builder.append("?");
	        if (++i < length) {
	            builder.append(",");
	        }
	    }
	    return builder.toString();
	}
	
	public static void setValues(PreparedStatement preparedStatement, List<String> values) throws SQLException {
	    for (int i = 0; i < values.size(); i++) {
	        preparedStatement.setObject(i + 3, values.get(i));
	    }
	}

	@Override
	public int analysisProductNoForSql() {
		// TODO Auto-generated method stub
		logger.info("AnalysisProductNoForSql Start!");
		PreparedStatement pst = null;
		ResultSet rst = null;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		try {
			pst = conn.prepareStatement("select id,pn,firm,lid,wid,location,firm,remlayer,stage from cp_wip  where productNo is null");
			rst = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * the start of prepare stage info 
		 */
		PreparedStatement pst3 = null;
		ResultSet rst3 = null;
		/*
		 * prepare history productNo
		 */
		Map<String,String> mapOfPn = new HashMap<String,String>();
		try {
			pst3 = conn.prepareStatement("select productNo,lid,wid from z_wip_detail where  tpnflow in('PAS1','PAD','PAS2','UV','WAT','QC','INV1') group by productNo,lid,wid");
			rst3 = pst3.executeQuery();
			while(rst3.next()){
				mapOfPn.put(rst3.getString("lid")+rst3.getString("wid"), rst3.getString("productNo"));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		List<TtpnStage> listOfSh = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfWh = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfKlt = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfChipmos = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfHlmc = new ArrayList<TtpnStage>();
		try {
			pst3 = conn.prepareStatement("select stage,firm,tpn from z_tpn_stage order by firm,stageOrder");
			rst3 = pst3.executeQuery();
			while(rst3.next()){
				String firm = rst3.getString("firm");
				String tpn = rst3.getString("tpn");
				String stage = rst3.getString("stage");
				TtpnStage obj = new TtpnStage();
				obj.setFirm(firm);
				Ttpn tt = new Ttpn();
				tt.setTpn(tpn);
				obj.setTpn(tt);
				obj.setStage(stage);
				if(firm.equals("CHIPMOS")){
					listOfChipmos.add(obj);
				}else if(firm.equals("KLT")){
					listOfKlt.add(obj);
				}else if(firm.equals("SH_TESTING")){
					listOfSh.add(obj);
				}else if(firm.equals("WH_TESTING")){
					listOfWh.add(obj);
				}else if(firm.equals("HLMC")){
					listOfHlmc.add(obj);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * the end of prepare stage info
		 */
		Connection connOfPi = connUtil.getMysqlConnection();
		int returnNum = 0;
		try {
			connOfPi.setAutoCommit(false);
			PreparedStatement pst2 = null;
//			pst2 = connOfPi.prepareStatement("select pn,tpn,remLayer,nm from z_tpn_product");
			//update on 2015-04-28 全用新规则生成Wafer段的WIPFLOW
			String newsql = "select a.tpn,a.remLayer,b.pn from z_tpn_rule_item a left join z_tpn_pn_relation b on b.ruleid=a.ruleid";
			pst2 = connOfPi.prepareStatement(newsql);
			ResultSet rst2 = pst2.executeQuery();
			Map<String, Object> mapOfTpn = new HashMap<String, Object>();
			while (rst2.next()) {
//				mapOfTpn.put(rst2.getString("pn") + rst2.getString("remLayer"),rst2.getString("tpn"));
				mapOfTpn.put(rst2.getString("pn") + String.valueOf(rst2.getInt("remLayer")),rst2.getString("tpn"));
			}
			pst2 = connOfPi.prepareStatement("update cp_wip set productNo=?,tpnFlow=? where id =?");
			while(rst.next()){
				String productNo = mapOfPn.get(rst.getString("lid")+rst.getString("wid"));
				if(UtilValidate.isEmpty(productNo)){
					productNo = PiUtil.filterByProductNo(rst.getString("pn"));
					//special pn like 'W041X'
					if(UtilValidate.isEmpty(productNo)){
						productNo =PiUtil.filterByProductNoIncludeW(rst.getString("pn"));
					}
				}
				String tpnflow = null;
				String stage = rst.getString("stage");
				String location = rst.getString("location");
				String firm = rst.getString("firm");
				String remLayer = rst.getString("remLayer");
				if (UtilValidate.isNotEmpty(productNo)) {
					//tpnflow logic
					//String tpnflow = PiUtil.getTpnFLow(productNo,t,mapOfTpn,mapOfFactory,String.valueOf(mapOfNm.get(productNo)));
					// FAB
					// if location is not null and location doesn't contain TESTING and CP
					if ((UtilValidate.isNotEmpty(location)&&(!location.contains("TESTING"))&&(!location.contains("CP")))||firm.equals("hlmc")||firm.equals("umc")) {
						boolean checkSmicLocation = true;
						if(firm.equals("smic")&&((!location.equals("B1"))&&(!location.equals("S1"))&&(!location.equals("FAB7"))&&(!location.equals("FAB8")))){
							checkSmicLocation = false;
						}
						// 如果remLayer不为空
						if (UtilValidate.isNotEmpty(rst.getString("remlayer"))&&checkSmicLocation) {
							String pn = "";
							if (productNo.length() > 4) {
								pn = productNo.substring(0, 4); 
							} else {
								pn = productNo;
							}
							remLayer = remLayer.trim();
							int checkNum = remLayer.indexOf("/");
							if (checkNum == 0) { // format /xxx
								remLayer = "0";
							} else if (checkNum > 0) { // format xxx/xxx
								remLayer = remLayer.substring(0, checkNum);
							}
							if (remLayer.equals("0")) {
								if (UtilValidate.isNotEmpty(stage)) {
									stage = stage.toUpperCase();
									if (stage.contains("PAD")||stage.contains("PAD-PH")||stage.contains("PAD-DEP")||stage.contains("PAD-ET")) {
										tpnflow = "PAD";
									}else if(stage.contains("PAS")||stage.contains("FIN-ALLY")){
										tpnflow = "PAS2";
									}else if(stage.contains("UV")||stage.contains("WF-MARK")){
										tpnflow = "UV";
									}else if(stage.contains("WAT")||stage.contains("FIN-WAT")){
										tpnflow = "WAT";
									}else if(stage.contains("QC-INSP")){
										tpnflow = "QC";
									}else if(stage.contains(" ")||stage.contains("INVENTORY")||stage.contains("INV")||stage.contains("QC-PACK")||
											stage.contains("QC1-PACK")||stage.contains("QC-INSP")||stage.contains("QC1-INSP")||stage.contains("CRYSTAL-STRIP")||
											stage.contains("OUT-INSP")||stage.contains("OUT-INSP1")){
										tpnflow = "INV1";
									}
								}
							} else {
								String keyOfMap = pn + (int) Double.parseDouble(remLayer);
								String tpn = null;
								if((int) Double.parseDouble(remLayer)>=760){
									tpn = "PAS1";
								}else{
									tpn = String.valueOf(mapOfTpn.get(keyOfMap));
								}
								if (UtilValidate.isNotEmpty(tpn) && !tpn.equals("null")) {
									tpnflow = tpn;
								}
							}

						}
					}else{	// CP Stage
//						logger.info("____________2:"+"_stage:"+t.getStage()+"_location:"+t.getLocation()+"_firm:"+t.getFirm());
						//logger.info("Entering application.");
						//Confirm WIP Factory 
						String firmName = null;
						if(UtilValidate.isNotEmpty(firm)){
							if(firm.equals("chipmos")){
								firmName = "CHIPMOS";
							}else if(firm.equals("klt")){
								firmName = "KLT";
							}else if(firm.equals("smic")||firm.equals("xmc")){
								String loc = rst.getString("location");
								if(UtilValidate.isNotEmpty(loc)){
									if(loc.equals("CP")||loc.equals("SH_TESTING")){
										firmName = "SH_TESTING";
									}else if(loc.equals("WH_TESTING")){
										firmName = "WH_TESTING";
									}
								}
							}
						}
//						logger.info("firmName is "+firmName);
						if(UtilValidate.isNotEmpty(firmName)){
							if(UtilValidate.isNotEmpty(stage)){
								if(firmName.equals("CHIPMOS")){
									for(TtpnStage type1 : listOfChipmos){
										if(stage.equals("BAK")||stage.equals("WBK")||stage.equals("FBK")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(stage.equals("FPOE")||stage.equals("FPOE2")||stage.equals("WPOE")||stage.equals("WPOE2")){
											tpnflow = PiUtil.processSpecialStage(stage);
										}else if(stage.contains(type1.getStage())){
											tpnflow = type1.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("KLT")){
									for(TtpnStage type2 : listOfKlt	){
										if(stage.equals("BAKE")||stage.equals("DIEBANK")||stage.equals("DIEBANK1")){
											tpnflow = PiUtil.processSpecialStage(stage);
										}else if(stage.contains(type2.getStage())){
											tpnflow = type2.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("SH_TESTING")){
									for(TtpnStage type3 : listOfSh	){
										if(stage.equals("Baking")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(UtilValidate.isEmpty(stage)){
											tpnflow = "INV2";
										}else if(stage.contains(type3.getStage())){
											tpnflow = type3.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("WH_TESTING")){
									for(TtpnStage type4 : listOfWh	){
										if(stage.toUpperCase().contains("BAK")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(stage.contains(type4.getStage())){
											tpnflow = type4.getTpn().getTpn();
											break;
										}
									}
								}
							}
//							logger.info("tpnflow is "+tpnflow);
						}
					}
					//end
					int id = rst.getInt("id");
					pst2.setString(1, productNo);
					pst2.setString(2, tpnflow);
					pst2.setInt(3, id);
//					logger.info("id:"+t.getId()+"_p:"+productNo+"_t:"+tpnflow);
					pst2.addBatch();
					returnNum++;
				}
			}
			pst2.executeBatch();
			connOfPi.commit();
			ConnUtil.close(rst, pst);
			ConnUtil.close(rst2, pst2);
			ConnUtil.close(rst3, pst3);
			ConnUtil.closeConn(connOfPi);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("AnalysisProductNoForSql End!");
		return returnNum;
	}

	@Override
	public void updateProductNoOfPi2() {
		// TODO Auto-generated method stub
		logger.info("UpdateProductNoOfPi2 Start!");
		PreparedStatement pst = null;
		ResultSet rst = null;
		Date date = new Date();
		SimpleDateFormat sf2 = new SimpleDateFormat("yy/MM/dd");
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		String sqlOfQuery = "select productNo,lid,wid from z_wip_detail where erpDate = ? and tpnflow in('PAS1','PAD','PAS2','UV','WAT','QC','INV1') order by productNo,lid";
		String sqlOfUpdate = "update t_fabside_wip set latestPn = 'Y',productId = ? where lotid = ? and waferid = ?";
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sqlOfQuery);
			pst.setString(1, sf2.format(date));
			rst = pst.executeQuery();
			pst = conn.prepareStatement(sqlOfUpdate);
			while (rst.next()) {
				String lid = rst.getString("lid");
				String wid = rst.getString("wid");
				String pn = rst.getString("productNo");
				pst.setString(1, pn);
				if(lid.contains(".")){
					pst.setString(2, lid.substring(0, lid.indexOf(".")));
				}else{
					pst.setString(2, lid);
				}
				pst.setString(3, wid);
				pst.addBatch();
			}
			//logger.info(String.format(sqlOfUpdate, preparePlaceHolders(ids.size())));
			logger.info("execute start!");
			pst.executeBatch();
			logger.info("execute end!");
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		logger.info("UpdateProductNoOfPi2 End!");
	}

	@Override
	public List<Twip> getListOfTwip(Wip wip) {
		// TODO Auto-generated method stub
		String hql = "from Twip t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(wip, hql, params);
		hql = addOrder(wip, hql);
		List<Twip> l = wipDao.find(hql, params);
		return l;
	}

	@Override
	public List<ToptionContent> getListOfOptions() {
		// TODO Auto-generated method stub
		String hql = "from ToptionContent t where 1=1 ";
		return optionDao.find(hql);
	}
	@Override
	public List<TprodContent> getListOfProd() {
		// TODO Auto-generated method stub
		String hql = "from TprodContent t where 1=1 group by t.name";
		return prodDao.find(hql);
	}
	@Override
	public List<TprodContent> getContentOfCpn(String name) {
		// TODO Auto-generated method stub
		String hql = "from TprodContent t where 1=1 and t.name ='"+name+"'";
		return prodDao.find(hql);
	}
	
	@Override
	public DataGrid datagridOfWip(Wip wip) {
		// TODO Auto-generated method stub
		DataGrid dg = new DataGrid();
		//hql = addOrder(wip, hql);
		List<Wip> nl = new ArrayList<Wip>();
		PreparedStatement pst = null;
		ResultSet rst = null;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		StringBuilder strBur1 = new StringBuilder("select sum(a.qty) q,id_,a.lid,a.wid,a.ipn,a.cpn,a.pn from zz_turnkey_detail a " +
				"left join t_fabside_wip b on b.id= a.id_ "+
				"where 1=1 and a.status = 'CREATED' and b.abnormal not like '%Q' and b.abnormal not like '%srcap%'");
		if(UtilValidate.isNotEmpty(wip.getPn())){
			strBur1.append(" and a.pn like '%"+wip.getPn()+"%'");
		}
		String lid = wip.getLid();
		if(UtilValidate.isNotEmpty(lid)){
			String[] b = lid.split(" ");
			StringBuilder conditions = new StringBuilder();
			for(String str:b){
//				conditions.append(",'"+str+"'");
				conditions.append("or a.lid like '%"+str+"%'");
			}
			strBur1.append("and ("+conditions.toString().substring(3)+")");
//			strBur1.append(" and a.lid in ("+conditions.toString().substring(1)+")");
			//strBur1.append(" and lid like '%"+wip.getLid()+"%'");
		}
		
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			strBur1.append(" and a.ipn like '%"+wip.getIpn()+"%'");
		}
		strBur1.append("group by a.lid ");
		if(wip.getPage()==0){
			wip.setPage(1);
		}
		strBur1.append(" limit "+wip.getRows()*(wip.getPage()-1)+","+wip.getRows());
		int totalNum = 0;
		try {
			pst = conn.prepareStatement(strBur1.toString());
			rst = pst.executeQuery();
			while (rst.next()) {
				Wip u = new Wip();
				u.setId_(rst.getString("id_"));
				u.setQty(rst.getInt("q"));
				u.setLid(rst.getString("lid"));
				u.setWid(rst.getString("wid"));
				u.setIpn(rst.getString("ipn"));
				u.setCpn(rst.getString("cpn"));
				u.setPn(rst.getString("pn"));
				nl.add(u);
				totalNum++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		List<Wip> footerList = new ArrayList<Wip>();
		Wip w = new Wip();
		w.setLid("Total");
		w.setQty(0);
		footerList.add(w);
		dg.setTotal(Long.valueOf(String.valueOf(totalNum)));
		dg.setRows(nl);
		dg.setFooter(footerList);
		return dg;
	}
	
	private String addWhereOfWip(Wip wip, String hql, Map<String, Object> params) {
		if (UtilValidate.isNotEmpty(wip.getLid())) {
			hql += " and t.lid like :lid";
			params.put("lid", "%%" + wip.getLid().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			hql += " and t.ipn like :ipn";
			params.put("ipn", "%%" + wip.getIpn().trim() + "%%");
		}
		return hql;
	}
	
	private void changeModelOfWip(List<TturnkeyDetail> l, List<Wip> nl) {
		if (l != null && l.size() > 0) {
			for (TturnkeyDetail t : l) {
				Wip u = new Wip();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
	@Override
	public DataGrid datagridOfWipDetail(Wip wip) {
		// TODO Auto-generated method stub
		List<TturnkeyDetail> l = turnkeyDetailDao.find("from TturnkeyDetail t where 1=1 and t.status='CREATED' and t.lid ='"+wip.getLid()+"'");
		DataGrid dg = new DataGrid();	
		dg.setTotal(Long.valueOf(String.valueOf(l.size())));
		dg.setRows(l);
		return dg;
	}
	@Override
	public DataGrid datagridOfWipDetailUnique(WipDetailUnique wip) {
		// TODO Auto-generated method stub
		
		DataGrid dg = new DataGrid();
		String hql = "from TturnkeyDetail t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhereOfWipDetailUnique(wip, hql, params);
		String totalHql = "select count(*) " + hql;
//		hql = addOrder(wip, hql);
		hql += " order by t.lid,t.wid";
		List<TturnkeyDetail> l = turnkeyDetailDao.find(hql, params, wip.getPage(), wip.getRows());
		List<WipDetailUnique> nl = new ArrayList<WipDetailUnique>();
		changeModel3(l, nl);
		dg.setTotal(turnkeyDetailDao.count(totalHql, params));
		dg.setRows(nl);
		return dg;
	}
	
	private String addWhereOfWipDetailUnique(WipDetailUnique wip, String hql, Map<String, Object> params) {
		if (UtilValidate.isNotEmpty(wip.getLid())) {
			hql += " and t.lid like :lid";
			params.put("lid", "%%" + wip.getLid().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getCpn())){
			hql += " and t.cpn like :cpn";
			params.put("cpn", "%%" + wip.getCpn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			hql += " and t.ipn like :ipn";
			params.put("ipn", "%%" + wip.getIpn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getIpn_new())){
			hql += " and t.ipn_new like :ipn_new";
			params.put("ipn_new", "%%" + wip.getIpn_new().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getStatus())){
			hql += " and t.status like :status";
			params.put("status", "%%" + wip.getStatus().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(wip.getIpn_new())) {
			hql += " and t.ipn_new like :ipn_new";
			params.put("ipn_new", "%%" + wip.getIpn_new().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(wip.getTpn())) {
			hql += " and t.tpn like :tpn";
			params.put("tpn", "%%" + wip.getTpn().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(wip.getPn())) {
			hql += " and t.pn like :pn";
			params.put("pn", "%%" + wip.getPn().trim() + "%%");
		}
		return hql;
	}
	private String addWhereOfWipDetailUniqueByJdbc(WipDetailUnique wip, String hql) {
		if (UtilValidate.isNotEmpty(wip.getLid())) {
			hql += " and a.lid like '%"+wip.getLid()+"%'";
		}
		if(UtilValidate.isNotEmpty(wip.getCpn())){
			hql += " and a.cpn like '%"+wip.getCpn()+"%'";
		}
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			hql += " and a.ipn like '%"+wip.getIpn()+"%'";
		}
		if(UtilValidate.isNotEmpty(wip.getIpn_new())){
			hql += " and a.ipn_new like '%"+wip.getIpn_new()+"%'";
		}
		if(UtilValidate.isNotEmpty(wip.getStatus())){
			hql += " and a.status like '%"+wip.getStatus()+"%'";
		}
		if (UtilValidate.isNotEmpty(wip.getTpn())) {
			hql += " and a.tpn like '%"+wip.getTpn()+"%'";
		}
		if (UtilValidate.isNotEmpty(wip.getTpnFlow())) {
			hql += " and a.tpnFlow like '%"+wip.getTpnFlow()+"%'";
		}
		if (UtilValidate.isNotEmpty(wip.getPn())) {
			hql += " and a.pn like '%"+wip.getPn()+"%'";
		}
		if (UtilValidate.isNotEmpty(wip.getWaferType())) {
			hql += " and b.abnormal like '%"+wip.getWaferType()+"%'";
		}
		if (UtilValidate.isNotEmpty(wip.getPiStatus())) {
			String status = wip.getPiStatus();
			boolean multi = status.contains(",");
			if(multi){
				String[] listOfStatus = status.split(",");
				hql += " and b.status in(";
				String subhql = "";
				for(String str:listOfStatus){
					subhql+=",'"+str+"'";
				}
				hql += subhql.substring(1)+")";
			}else{
				hql += " and b.status = '"+wip.getPiStatus()+"'";
			}
		}else{
			hql += " and (b.status<>'Finish' or b.status is null)";
		}
		return hql;
	}
	@Override
	public void CopyDataOfWipToDbOfHistory() {
		// TODO Auto-generated method stub
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		Connection connOfHistory = connUtil.getMysqlConnectionByName("cp_wip_history");
		String query = "select * from cp_wip where senddate between '2014-02-01 00:00:00' and '2014-02-31 23:59:59'";
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rst = null;
		try {
			pst2 = connOfHistory.prepareStatement("insert into t_201402(id,pn,cpn,ipn,lid,qty,wid,startDate," +
					"stage,status,fotime,remlayer,holddate,holdremark,location,senddate,firm,filename,erpdate,tpnFlow,productNo) " +
					"values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)");
			connOfHistory.setAutoCommit(false);
			pst = conn.prepareStatement(query);
			rst = pst.executeQuery();
			while(rst.next()){
				pst2.setInt(1, rst.getInt("id"));
				pst2.setString(2, rst.getString("pn"));
				pst2.setString(3, rst.getString("cpn"));
				String ipn = rst.getString("ipn");
				if(ipn.length()>40){
					ipn = ipn.substring(0,40);
				}
				pst2.setString(4, ipn);
				pst2.setString(5, rst.getString("lid"));
				pst2.setString(6, rst.getString("qty"));
				pst2.setString(7, rst.getString("wid"));
				pst2.setDate(8, rst.getDate("startDate"));
				pst2.setString(9, rst.getString("stage"));
				pst2.setString(10, rst.getString("status"));
				pst2.setDate(11, rst.getDate("fotime"));
				pst2.setString(12, rst.getString("remlayer"));
				pst2.setDate(13, rst.getDate("holddate"));
				pst2.setString(14, rst.getString("holdremark"));
				pst2.setString(15, rst.getString("location"));
				pst2.setDate(16, rst.getDate("senddate"));
				pst2.setString(17, rst.getString("firm"));
				pst2.setString(18, rst.getString("filename"));
				pst2.setString(19, rst.getString("erpdate"));
				pst2.setString(20, rst.getString("tpnFlow"));
				pst2.setString(21, rst.getString("productNo"));
				pst2.addBatch();
			}
			pst2.executeBatch();
			connOfHistory.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
			ConnUtil.closeConn(connOfHistory);
			ConnUtil.close(null, pst2);
		}
		
	}
	@Override
	public void dataClean() {
		// TODO Auto-generated method stub
		logger.info("Data Clean Start!");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		String erpDate = sf.format(date);
		ConnUtil conn = new ConnUtil();
		Connection connOfMysql = conn.getMysqlConnection();
		PreparedStatement pst = null;
		int rows = 0;
		try {
			pst = connOfMysql.prepareStatement("delete from z_wip_detail where erpdate=?");
			pst.setString(1, erpDate);
			rows= pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.closePst(pst);
			ConnUtil.closeConn(connOfMysql);
		}
		logger.info("Data Clean End!"+rows);
	}
	@Override
	public void CopyDataOfWipToDbOfHistoryDaily() {
		// TODO Auto-generated method stub
		logger.info("Copy Wip Data to HistoryDb Start!");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMM");
		String erpDate = sf.format(date);
		String tableName = "t_"+sf2.format(date); 
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		Connection connOfHistory = connUtil.getMysqlConnectionByName("cp_wip_history");
		String query = "select * from cp_wip where erpdate=? and hasPass is null and id not in(select id from cp_wip where erpdate=? and status='TRAN' and firm='csmc')";//懒了
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rst = null;
		try {
			boolean hasTable = TableOfMysqlUtil.checkTableExist(connOfHistory, tableName);
			if(!hasTable){
				TableOfMysqlUtil.createTableToCpWipHistory(connOfHistory, tableName);
			}
			pst2 = connOfHistory.prepareStatement("insert into "+tableName+"(id,pn,cpn,ipn,lid,qty,wid,startDate," +
					"stage,status,fotime,remlayer,holddate,holdremark,location,senddate,firm,filename,erpdate,tpnFlow,productNo) " +
					"values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)");
			connOfHistory.setAutoCommit(false);
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(query);
			pst.setString(1, erpDate);
			pst.setString(2, erpDate);
			rst = pst.executeQuery();
			while(rst.next()){
				pst2.setInt(1, rst.getInt("id"));
				pst2.setString(2, rst.getString("pn"));
				pst2.setString(3, rst.getString("cpn"));
				String ipn = rst.getString("ipn");
				if(UtilValidate.isEmpty(ipn)){
					ipn = "";
				}
				if(ipn.length()>40){
					ipn = ipn.substring(0,40);
				}
				pst2.setString(4, ipn);
				pst2.setString(5, rst.getString("lid"));
				pst2.setString(6, rst.getString("qty"));
				pst2.setString(7, rst.getString("wid"));
				pst2.setDate(8, rst.getDate("startDate"));
				pst2.setString(9, rst.getString("stage"));
				pst2.setString(10, rst.getString("status"));
				pst2.setDate(11, rst.getDate("fotime"));
				pst2.setString(12, rst.getString("remlayer"));
				pst2.setDate(13, rst.getDate("holddate"));
				pst2.setString(14, rst.getString("holdremark"));
				pst2.setString(15, rst.getString("location"));
				pst2.setDate(16, rst.getDate("senddate"));
				pst2.setString(17, rst.getString("firm"));
				pst2.setString(18, rst.getString("filename"));
				pst2.setString(19, rst.getString("erpdate"));
				pst2.setString(20, rst.getString("tpnFlow"));
				pst2.setString(21, rst.getString("productNo"));
				pst2.addBatch();
			}
			pst2.executeBatch();
			pst = conn.prepareStatement("update cp_wip set hasPass='Y' where erpDate=?");
			pst.setString(1, erpDate);
			int updateRows = pst.executeUpdate();
			logger.info("Copy Wip Data Rows is "+updateRows);
			conn.commit();
			connOfHistory.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
			ConnUtil.closeConn(connOfHistory);
			ConnUtil.close(null, pst2);
		}
		logger.info("Copy Wip Data to HistoryDb End!");
	}
	@Override
	public List<TturnkeyDetail> getListOfTwipDetail(WipDetailUnique wip) {
		// TODO Auto-generated method stub
		String hql = "from TturnkeyDetail t where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhereForUniqueWip(wip, hql, params);
		List<TturnkeyDetail> l = turnkeyDetailDao.find(hql, params);
		return l;
	}
	
	private String addWhereForUniqueWip(WipDetailUnique wip, String hql, Map<String, Object> params) {
		if (UtilValidate.isNotEmpty(wip.getLid())) {
			hql += " and t.lid like :lid";
			params.put("lid", "%%" + wip.getLid().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getCpn())){
			hql += " and t.cpn like :cpn";
			params.put("cpn", "%%" + wip.getCpn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getIpn())){
			hql += " and t.ipn like :ipn";
			params.put("ipn", "%%" + wip.getIpn().trim() + "%%");
		}
		if(UtilValidate.isNotEmpty(wip.getStatus())){
			hql += " and t.status like :status";
			params.put("status", "%%" + wip.getStatus().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(wip.getIpn_new())) {
			hql += " and t.ipn_new like :ipn_new";
			params.put("ipn_new", "%%" + wip.getIpn_new().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(wip.getTpn())) {
			hql += " and t.tpn like :tpn";
			params.put("tpn", "%%" + wip.getTpn().trim() + "%%");
		}
		if (UtilValidate.isNotEmpty(wip.getPn())) {
			hql += " and t.pn like :pn";
			params.put("pn", "%%" + wip.getPn().trim() + "%%");
		}
		return hql;
	}
	@Override
	public int UploadDataForUpdateTpn(Map<String, String> mapOfUpdateData) {
		// TODO Auto-generated method stub
		PreparedStatement pst = null;
		ResultSet rst = null;
		PreparedStatement pst2 = null;
		ResultSet rst2 = null;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		String sqlOfUpdate = "update zz_turnkey_detail set tpn = ? where lid=? and wid = ? and status = 'COMPLETED'";
		int rs = 0;
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sqlOfUpdate);
			for(Map.Entry<String, String> entry: mapOfUpdateData.entrySet()) {
//				boolean canUpdate = true;
				 String keyValue = entry.getKey();
				 String[] lidAndWid = keyValue.split("_");
/*				 String lid = lidAndWid[0];
				 if(lid.contains(".")){
					 lid = lid.substring(0, lid.indexOf("."));
				 }
				 pst2 = conn.prepareStatement("select abnormal from t_fabside_wip where id='"+lid+"_"+lidAndWid[1]+"' and abnormal like '%S%'");
				rst2 = pst2.executeQuery();
				while(rst2.next()){
					canUpdate = false;
				}*/
				 pst.setString(1, entry.getValue());
				 pst.setString(2, lidAndWid[0]);
				 pst.setString(3, lidAndWid[1]);
				 pst.addBatch();
			}
			//logger.info(String.format(sqlOfUpdate, preparePlaceHolders(ids.size())));
			logger.info("execute update tpn start!");
			int[] eb = pst.executeBatch();
			for(int i:eb){
				if(i>0){
					rs++;
				}
			}
			logger.info("execute update tpn commit!");
			conn.commit();
			logger.info("execute update tpn end!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.close(rst2, pst2);
			ConnUtil.closeConn(conn);
		}
		return rs;
	}
	@Override
	public DataGrid datagridOfWipDetailUniqueByJdbc(WipDetailUnique wip) {
		// TODO Auto-generated method stub
		int rows = 0;
		DataGrid dg = new DataGrid();
		List<WipDetailUnique> nl = new ArrayList<WipDetailUnique>();
		if(wip.getId()>0){
			PreparedStatement pst = null;
			ResultSet rst = null;
			ConnUtil connUtil = new ConnUtil();
			Connection conn = connUtil.getMysqlConnection();
//			String sql = "select a.cpn,a.ipn,a.lid,a.pn,a.status,a.wid,a.ipn_new,a.tpn,b.status pistatus,a.tpnFLow,b.abnormal from zz_turnkey_detail a left join t_fabside_wip b on a.lid = b.lotid and a.wid = b.waferid where 1=1";
			String sql = "select a.cpn,a.ipn,a.lid,a.pn,a.status,a.wid,a.ipn_new,a.tpn,b.status pistatus,a.tpnFLow,b.abnormal from zz_turnkey_detail a left join t_fabside_wip b on b.Id = concat(parent_lid,'_',wid) where 1=1";
			sql = addWhereOfWipDetailUniqueByJdbc(wip, sql);
			if(wip.getPage()==0){
				wip.setPage(1);
			}
			sql += " order by a.lid,a.wid limit "+(wip.getPage() - 1) * wip.getRows()+","+wip.getRows();
			String totalHql = "select count(*) as rows from zz_turnkey_detail a left join t_fabside_wip b on b.Id = concat(parent_lid,'_',wid) where 1=1 " + addWhereOfWipDetailUniqueByJdbc(wip, "");
			try {
				pst = conn.prepareStatement(sql);
				rst = pst.executeQuery();
				while(rst.next()){
					WipDetailUnique obj = new WipDetailUnique();
					obj.setCpn(rst.getString("cpn"));
					obj.setIpn(rst.getString("ipn"));
					obj.setLid(rst.getString("lid"));
					obj.setPn(rst.getString("pn"));
					obj.setStatus(rst.getString("status"));
					obj.setWid(rst.getString("wid"));
					obj.setIpn_new(rst.getString("ipn_new"));
					obj.setTpn(rst.getString("tpn"));
					obj.setTpnFlow(rst.getString("tpnFlow"));
					obj.setPiStatus(rst.getString("pistatus"));
					obj.setWaferType(rst.getString("abnormal"));
					nl.add(obj);
				}
				pst = conn.prepareStatement(totalHql);
				rst = pst.executeQuery();
				while(rst.next()){
					rows = rst.getInt("rows");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				ConnUtil.close(rst, pst);
				ConnUtil.closeConn(conn);
			}
		}
		dg.setTotal(Long.valueOf(rows));
		dg.setRows(nl);
		return dg;
	}
	@Override
	public List<Series> getLogicDataToCharts() {
		// TODO Auto-generated method stub
		List<Series> returnList = new ArrayList<Series>();
		String mql = "from Ttpn t where t.status = 'VALID' and t.ruleTypeId='LOGIC'";
		List<Ttpn> tpnList = tpnDao.find(mql);
		Map<String, Integer> mapOfTpn = new HashMap<String, Integer>();
		for (Ttpn t : tpnList) {
			mapOfTpn.put(t.getTpn(), t.getTpnOrder());
		}
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		// String hql =
		// "select sum(qty),productNo,tpnflow from Twip  where tpnflow is not null group by productNo,tpnflow";
		/*String hql = "select sum(t.qty),t.productNo,t.tpnFlow from TwipDetail t where t.tpnFlow is not null and t.erpDate = '"
				+ sf.format(new Date()) + "' group by t.productNo,t.tpnFlow order by t.productNo";*/
		String hql = "select sum(t.qty),t.productNo,t.tpnFlow from TwipDetailChart t where t.status='Y' and (t.productNo like '5220%' or t.productNo like '5973%' or t.productNo like '5688%' or t.productNo like '5232%' or t.productNo like '6007%')" +
				" and t.erpDate = '"+ sf.format(new Date()) + "' group by t.productNo,t.tpnFlow order by t.productNo";
		List<Object[]> l = wiplistDao.find(hql);
		Map<String, Series> mapOfSeries = new HashMap<String, Series>();
		List<String> listOfProductNoOrder = new ArrayList<String>();
		String record = null;
		for (Object[] o : l) {
			String name = o[1].toString();
			Integer tpn = mapOfTpn.get(o[2].toString());
			if(null!=tpn){
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
					data = new int[tpnList.size()];
					series.setName(o[1].toString());
				} else {
					data = series.getData();
				}
				data[tpn] = Integer.parseInt(o[0]
						.toString());
				series.setData(data);
				mapOfSeries.put(o[1].toString(), series);
			}
			
		}
		/*for (Map.Entry<String, Series> entry : mapOfSeries.entrySet()) {
			returnList.add(entry.getValue());
		}*/
		for(String o:listOfProductNoOrder){
			//logger.info(o);
			returnList.add(mapOfSeries.get(o));
		}
		return returnList;
	}
	@Override
	public String getLogicTpnToXAxis() {
		// TODO Auto-generated method stub
		String hql = "from Ttpn t where t.status = 'VALID' and t.ruleTypeId='LOGIC' order by t.tpnOrder";
		List<Ttpn> l = tpnDao.find(hql);
		StringBuffer sb = new StringBuffer();
		for (Ttpn t : l) {
			sb.append("," + t.getTpn());
		}
		return sb.toString().substring(1);
	}
	@Override
	public List<WipDetailUnique> getListOfTwipDetailByJdbc(WipDetailUnique wip) {
		// TODO Auto-generated method stub
		List<WipDetailUnique> list = new ArrayList<WipDetailUnique>();
		PreparedStatement pst = null;
		ResultSet rst = null;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		String sql = "select a.cpn,a.ipn,a.lid,a.pn,a.status,a.wid,a.ipn_new,a.tpn,b.abnormal,b.status pistatus,a.tpnFLow from zz_turnkey_detail a left join t_fabside_wip b on a.parent_lid = b.lotid and a.wid = b.waferid where 1=1";
		sql = addWhereOfWipDetailUniqueByJdbc(wip, sql);
		sql += " order by a.lid,a.wid";
		try {
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			while(rst.next()){
				WipDetailUnique obj = new WipDetailUnique();
				obj.setCpn(rst.getString("cpn"));
				obj.setIpn(rst.getString("ipn"));
				obj.setLid(rst.getString("lid"));
				obj.setPn(rst.getString("pn"));
				obj.setStatus(rst.getString("status"));
				obj.setWid(rst.getString("wid"));
				obj.setIpn_new(rst.getString("ipn_new"));
				obj.setTpn(rst.getString("tpn"));
				obj.setTpnFlow(rst.getString("tpnFlow"));
				obj.setPiStatus(rst.getString("pistatus"));
				obj.setWaferType(rst.getString("abnormal"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnUtil.close(rst, pst);
			ConnUtil.closeConn(conn);
		}
		return list;
	}
	@Override
	public List<WipCompare> getWipCompareData(Wip wip) {
		// TODO Auto-generated method stub
		String hql = "from TwipDetail t where ((t.firm='XMC' and t.location='WH_TESTING') or (t.firm='SMIC' and t.location='CP') or t.firm='chipmos' or t.firm='klt') and t.firm<>'csmc' and t.erpDate='"+wip.getErpDate()+"' order by t.lid,t.wid";
		List<TwipDetail> l = wipDetailDao.find(hql);
		String hql2 = "from TwipDetail t where ((t.firm='XMC' and t.location='WH_TESTING') or (t.firm='SMIC' and t.location='CP') or t.firm='chipmos' or t.firm='klt') and t.firm<>'csmc' and t.erpDate='"+wip.getLocation()+"' order by t.lid,t.wid";
		List<TwipDetail> l2 = wipDetailDao.find(hql2);
		Map<String,String> oldMap = new HashMap<String,String>();
		for(TwipDetail obj : l2){
			oldMap.put(obj.getPn()+"_"+obj.getLid()+"_"+obj.getWid(), obj.getStage());
		}
		List<WipCompare> returnlist = new ArrayList<WipCompare>();
		for(TwipDetail obj : l){
			String key = obj.getPn()+"_"+obj.getLid()+"_"+obj.getWid();
			String oldValue = oldMap.get(key);
			if(UtilValidate.isNotEmpty(oldValue)){
				if(!oldValue.equals(obj.getStage())){
					WipCompare newWc = new WipCompare(obj.getPn(), obj.getLid(), obj.getWid(), obj.getStage(), oldValue);
					returnlist.add(newWc);
				}
			}
		}
		return returnlist;
	}
	@Override
	public int analysisProductNoForSqlByNewRule() {
		// TODO Auto-generated method stub
		logger.info("AnalysisProductNoForSqlByNewRule Start!");
		SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd");
		PreparedStatement pst = null;
		ResultSet rst = null;
		ConnUtil connUtil = new ConnUtil();
		Connection conn = connUtil.getMysqlConnection();
		try {
			pst = conn.prepareStatement("select id,pn,firm,lid,wid,location,firm,remlayer,stage from cp_wip  where erpDate='"+sf.format(new Date())+"' and  productNo is null");
			rst = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * the start of prepare stage info 
		 */
		PreparedStatement pst3 = null;
		ResultSet rst3 = null;
		/*
		 * prepare history productNo
		 */
		Map<String,String> mapOfPn = new HashMap<String,String>();
		try {
			pst3 = conn.prepareStatement("select productNo,lid,wid from z_wip_detail where  tpnflow in('PAS1','PAD','PAS2','UV','WAT','QC','INV1') group by productNo,lid,wid");
			rst3 = pst3.executeQuery();
			while(rst3.next()){
				mapOfPn.put(rst3.getString("lid")+rst3.getString("wid"), rst3.getString("productNo"));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		List<TtpnStage> listOfSh = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfWh = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfKlt = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfChipmos = new ArrayList<TtpnStage>();
		List<TtpnStage> listOfHlmc = new ArrayList<TtpnStage>();
		try {
			pst3 = conn.prepareStatement("select stage,firm,tpn from z_tpn_stage order by firm,stageOrder");
			rst3 = pst3.executeQuery();
			while(rst3.next()){
				String firm = rst3.getString("firm");
				String tpn = rst3.getString("tpn");
				String stage = rst3.getString("stage");
				TtpnStage obj = new TtpnStage();
				obj.setFirm(firm);
				Ttpn tt = new Ttpn();
				tt.setTpn(tpn);
				obj.setTpn(tt);
				obj.setStage(stage);
				if(firm.equals("CHIPMOS")){
					listOfChipmos.add(obj);
				}else if(firm.equals("KLT")){
					listOfKlt.add(obj);
				}else if(firm.equals("SH_TESTING")){
					listOfSh.add(obj);
				}else if(firm.equals("WH_TESTING")){
					listOfWh.add(obj);
				}else if(firm.equals("HLMC")){
					listOfHlmc.add(obj);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * the end of prepare stage info
		 */
		Connection connOfPi = connUtil.getMysqlConnection();
		int returnNum = 0;
		try {
			connOfPi.setAutoCommit(false);
			PreparedStatement pst2 = null;
			pst2 = connOfPi.prepareStatement("select tpn,remLayer,pn from z_tpn_rule_item a left join z_tpn_pn_relation b on a.ruleId=b.ruleId");
			ResultSet rst2 = pst2.executeQuery();
			Map<String, Object> mapOfTpnNewRule = new HashMap<String, Object>();
			while (rst2.next()) {
				mapOfTpnNewRule.put(rst2.getString("pn") + String.valueOf(rst2.getInt("remLayer")),rst2.getString("tpn"));
			}
			pst2 = connOfPi.prepareStatement("select pn,tpn,remLayer,nm from z_tpn_product");
			rst2 = pst2.executeQuery();
			Map<String, Object> mapOfTpn = new HashMap<String, Object>();
			while (rst2.next()) {
//				mapOfTpn.put(rst2.getString("pn") + rst2.getString("remLayer"),rst2.getString("tpn"));
				mapOfTpn.put(rst2.getString("pn") + String.valueOf(rst2.getInt("remLayer")),rst2.getString("tpn"));
			}
			pst2 = connOfPi.prepareStatement("update cp_wip set productNo=?,tpnFlow=? where id =?");
			while(rst.next()){
				String productNo = mapOfPn.get(rst.getString("lid")+rst.getString("wid"));
				if(UtilValidate.isEmpty(productNo)){
					String pn = rst.getString("pn");
					if(UtilValidate.isNotEmpty(pn)){
						productNo = PiUtil.filterByProductNo(rst.getString("pn"));
						//special pn like 'W041X'
						if(UtilValidate.isEmpty(productNo)){
							productNo =PiUtil.filterByProductNoIncludeW(rst.getString("pn"));
						}
					}
				}
				String tpnflow = null;
				String stage = rst.getString("stage");
				String location = rst.getString("location");
				String firm = rst.getString("firm");
				String remLayer = rst.getString("remLayer");
				if (UtilValidate.isNotEmpty(productNo)) {
					//tpnflow logic
					//String tpnflow = PiUtil.getTpnFLow(productNo,t,mapOfTpn,mapOfFactory,String.valueOf(mapOfNm.get(productNo)));
					// FAB
					// if location is not null and location doesn't contain TESTING and CP
					if ((UtilValidate.isNotEmpty(location)&&(!location.contains("TESTING"))&&(!location.contains("CP")))||firm.equals("hlmc")) {
						boolean checkSmicLocation = true;
						if(firm.equals("smic")&&((!location.equals("B1"))&&(!location.equals("S1"))&&(!location.equals("FAB7"))&&(!location.equals("FAB8")))){
							checkSmicLocation = false;
						}
						// 如果remLayer不为空
						if (UtilValidate.isNotEmpty(rst.getString("remlayer"))&&checkSmicLocation) {
							String pn = "";
							if (productNo.length() > 4) {
								pn = productNo.substring(0, 4); 
							} else {
								pn = productNo;
							}
							remLayer = remLayer.trim();
							int checkNum = remLayer.indexOf("/");
							if (checkNum == 0) { // format /xxx
								remLayer = "0";
							} else if (checkNum > 0) { // format xxx/xxx
								remLayer = remLayer.substring(0, checkNum);
							}
							if (remLayer.equals("0")) {
								if (UtilValidate.isNotEmpty(stage)) {
									stage = stage.toUpperCase();
									if (stage.contains("PAD")||stage.contains("PAD-PH")||stage.contains("PAD-DEP")||stage.contains("PAD-ET")) {
										tpnflow = "PAD";
									}else if(stage.contains("PAS")||stage.contains("FIN-ALLY")){
										tpnflow = "PAS2";
									}else if(stage.contains("UV")||stage.contains("WF-MARK")){
										tpnflow = "UV";
									}else if(stage.contains("WAT")||stage.contains("FIN-WAT")){
										tpnflow = "WAT";
									}else if(stage.contains("QC-INSP")){
										tpnflow = "QC";
									}else if(stage.contains(" ")||stage.contains("INVENTORY")||stage.contains("INV")||stage.contains("QC-PACK")||
											stage.contains("QC1-PACK")||stage.contains("QC-INSP")||stage.contains("QC1-INSP")||stage.contains("CRYSTAL-STRIP")||
											stage.contains("OUT-INSP")||stage.contains("OUT-INSP1")){
										tpnflow = "INV1";
									}
								}
							} else {
								String keyOfMap = pn + (int) Double.parseDouble(remLayer);
								String tpn = null;
								if((int) Double.parseDouble(remLayer)>=760){
									tpn = "PAS1";
								}else{
									String newTpn = (String)mapOfTpnNewRule.get(keyOfMap);
									if(UtilValidate.isNotEmpty(newTpn)){
										tpn = newTpn;
									}else{
										tpn = String.valueOf(mapOfTpn.get(keyOfMap));
									}
								}
								if (UtilValidate.isNotEmpty(tpn) && !tpn.equals("null")) {
									tpnflow = tpn;
								}
							}

						}
					}else{	// CP Stage
//						logger.info("____________2:"+"_stage:"+t.getStage()+"_location:"+t.getLocation()+"_firm:"+t.getFirm());
						//logger.info("Entering application.");
						//Confirm WIP Factory 
						String firmName = null;
						if(UtilValidate.isNotEmpty(firm)){
							if(firm.equals("chipmos")){
								firmName = "CHIPMOS";
							}else if(firm.equals("klt")){
								firmName = "KLT";
							}else if(firm.equals("smic")||firm.equals("xmc")){
								String loc = rst.getString("location");
								if(UtilValidate.isNotEmpty(loc)){
									if(loc.equals("CP")||loc.equals("SH_TESTING")){
										firmName = "SH_TESTING";
									}else if(loc.equals("WH_TESTING")){
										firmName = "WH_TESTING";
									}
								}
							}
						}
//						logger.info("firmName is "+firmName);
						if(UtilValidate.isNotEmpty(firmName)){
							if(UtilValidate.isNotEmpty(stage)){
								if(firmName.equals("CHIPMOS")){
									for(TtpnStage type1 : listOfChipmos){
										if(stage.equals("BAK")||stage.equals("WBK")||stage.equals("FBK")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(stage.equals("FPOE")||stage.equals("FPOE2")||stage.equals("WPOE")||stage.equals("WPOE2")){
											tpnflow = PiUtil.processSpecialStage(stage);
										}else if(stage.contains(type1.getStage())){
											tpnflow = type1.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("KLT")){
									for(TtpnStage type2 : listOfKlt	){
										if(stage.equals("BAKE")||stage.equals("DIEBANK")||stage.equals("DIEBANK1")){
											tpnflow = PiUtil.processSpecialStage(stage);
										}else if(stage.contains(type2.getStage())){
											tpnflow = type2.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("SH_TESTING")){
									for(TtpnStage type3 : listOfSh	){
										if(stage.equals("Baking")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(UtilValidate.isEmpty(stage)){
											tpnflow = "INV2";
										}else if(stage.contains(type3.getStage())){
											tpnflow = type3.getTpn().getTpn();
											break;
										}
									}
								}else if(firmName.equals("WH_TESTING")){
									for(TtpnStage type4 : listOfWh	){
										if(stage.toUpperCase().contains("BAK")){
											tpnflow = PiUtil.processSpecialStage("BAKE");
										}else if(stage.contains(type4.getStage())){
											tpnflow = type4.getTpn().getTpn();
											break;
										}
									}
								}
							}
//							logger.info("tpnflow is "+tpnflow);
						}
					}
					//end
					int id = rst.getInt("id");
					pst2.setString(1, productNo);
					pst2.setString(2, tpnflow);
					pst2.setInt(3, id);
					logger.info("id:"+id+"_p:"+productNo+"_t:"+tpnflow);
					pst2.addBatch();
					returnNum++;
				}
			}
			logger.info("execute batch!");
			pst2.executeBatch();
			logger.info("execute batch end!");
			connOfPi.commit();
			ConnUtil.close(rst, pst);
			ConnUtil.close(rst2, pst2);
			ConnUtil.close(rst3, pst3);
			ConnUtil.closeConn(connOfPi);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("AnalysisProductNoForSqlByNewRule End!");
		return returnNum;
	}
	@Override
	public String addWip(WipDetailUnique wip) {
		// TODO Auto-generated method stub
		int insertNumber = 0;
		String lid = wip.getLid();
		String parentLid = "";
		if(lid.contains(".")){
			parentLid = lid.substring(0,lid.indexOf("."));
		}else{
			parentLid = lid;
		}
		String wid = wip.getWid();
		List<String> wids = WaferIdFormat.getWaferIdList(wid);
		if(UtilValidate.isNotEmpty(wids)){
			ConnUtil connUtil = new ConnUtil();
			Connection conn = connUtil.getMysqlConnection();
			
			PreparedStatement pst = null;
			PreparedStatement pst2 = null;
			ResultSet rst = null;
			try {
				conn.setAutoCommit(false);
				pst2 = conn.prepareStatement("insert into zz_turnkey_detail(cpn,ipn,lid,pn,qty,status,wid,parent_lid,id_) values(?,?,?,?,?,?,?,?,?)");
				for(String str : wids){
					boolean ifExists = false;
					String id = parentLid+"_"+str;
					pst = conn.prepareStatement("select id_ from zz_turnkey_detail where id_='"+id+"'");
					rst = pst.executeQuery();
					while(rst.next()){
						ifExists = true;
						break;
					}
					if(ifExists){
						continue;
					}else{
						pst2.setString(1, wip.getCpn());
						pst2.setString(2, wip.getIpn());
						pst2.setString(3, lid);
						pst2.setString(4, wip.getPn());
						pst2.setInt(5, 1);
						pst2.setString(6, "CREATED");
						pst2.setString(7, str);
						pst2.setString(8, parentLid);
						pst2.setString(9, id);
						pst2.addBatch();
						insertNumber++;
					}
				}
				if(insertNumber>0){
					pst2.executeBatch();
					conn.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				ConnUtil.close(rst, pst);
				ConnUtil.close(null, pst2);
				ConnUtil.closeConn(conn);
			}
		}
		return String.valueOf(insertNumber);
	}
	
}
