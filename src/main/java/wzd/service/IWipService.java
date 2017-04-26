package wzd.service;

import java.util.List;
import java.util.Map;

import wzd.model.pi.ToptionContent;
import wzd.model.pi.TprodContent;
import wzd.model.pi.TturnkeyDetail;
import wzd.model.pi.Twip;
import wzd.model.pi.TwipCycleTime;
import wzd.model.pi.TwipDetail;
import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.Series;
import wzd.pageModel.pi.Wip;
import wzd.pageModel.pi.WipCompare;
import wzd.pageModel.pi.WipDetailUnique;

public interface IWipService {
	DataGrid datagrid(Wip wip);
	DataGrid datagridBySql(Wip wip);
	int analysisProductNo();
	int analysisProductNoForSql();
	int analysisProductNoForSqlByNewRule();
	String getTpnToXAxis();
	String getLogicTpnToXAxis();
	List<Series> getDataToCharts();
	List<Series> getLogicDataToCharts();
	DataGrid datagridOfDetail(Wip wip);
	void updateProductNoOfPi();
	void updateProductNoOfPi2();
	List<Twip> getListOfTwip(Wip wip);
	List<TturnkeyDetail> getListOfTwipDetail(WipDetailUnique wip);
	List<WipDetailUnique> getListOfTwipDetailByJdbc(WipDetailUnique wip);
	List<ToptionContent> getListOfOptions();
	List<TprodContent> getListOfProd();
	List<TprodContent> getContentOfCpn(String name);
	DataGrid datagridOfWip(Wip wip);
	DataGrid datagridOfWipDetail(Wip wip);
	DataGrid datagridOfWipDetailUnique(WipDetailUnique wip);
	DataGrid datagridOfWipDetailUniqueByJdbc(WipDetailUnique wip);
	void CopyDataOfWipToDbOfHistory();
	void dataClean();
	void CopyDataOfWipToDbOfHistoryDaily();
	Map<String,String> UploadDataForUpdateTpn(Map<String,String> mapOfUpdateData);
	List<WipCompare> getWipCompareData(Wip wip);
	String addWip(WipDetailUnique wip);
	int ReplaceBaoFeiWip(List<Map<String, Object>> list);
	int CreateGongHuo(List<Map<String, Object>> list);
	void dataResolveForWipFlow();
	void dataResolveForTurnkeyDetailFlowdate();
}
