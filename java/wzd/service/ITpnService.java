package wzd.service;

import java.util.List;

import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.Series;
import wzd.pageModel.tpn.TpnRule;

public interface ITpnService {
	DataGrid datagrid(TpnRule tpnRule);
	DataGrid datagridItem(TpnRule tpnRule);
	DataGrid datagridPn(TpnRule tpnRule);
	DataGrid datagridOfTpnFlow(TpnRule tpnRule);
	void createTpnRuleItem(TpnRule tpnRule);
	void createPnRelation(TpnRule tpnRule);
	void deletePnRelation(TpnRule tpnRule);
	DataGrid getRuleHeaderFromRuleTypeId(TpnRule tpnRule);
	List<Series> getDataToChartsByNewRule(TpnRule tpnRule, String xAxis);
	String getTpnToXAxis(TpnRule tpnRule);
}
