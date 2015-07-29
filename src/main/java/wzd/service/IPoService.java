package wzd.service;

import java.util.List;
import java.util.Map;

import wzd.model.pi.ToptionContent;
import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.OptionContent;
import wzd.pageModel.pi.TurnkeyOrder;

public interface IPoService {
	String createProductOrder(OptionContent option);
	String createProductOrderAndValidateIpn(OptionContent option);
	DataGrid datagrid(TurnkeyOrder turnkeyOrder);
	DataGrid datagridItem(TurnkeyOrder turnkeyOrder);
	String deletePoFromId(TurnkeyOrder turnkeyOrder);
	List<String> completedProductOrder(TurnkeyOrder turnkeyOrder);
	void createProductOrderItem(OptionContent optionContent);
	String deletePoItemFromIdAndSeqId(TurnkeyOrder turnkeyOrder);
	void cancelProductOrder(TurnkeyOrder turnkeyOrder);
	DataGrid datagridOfTurnkeyDetail(TurnkeyOrder turnkeyOrder);
	String deletePoItemDetailsFromFid(TurnkeyOrder turnkeyOrder);
	void passIpnOfTurnkeyOrderToPi();
	DataGrid datagridTpnTestFlows(TurnkeyOrder turnkeyOrder);
	void deletePoItemFromIdAndSeqIdByChange(TurnkeyOrder turnkeyOrder);
	void deletePoItemDetailsFromFidByChange(TurnkeyOrder turnkeyOrder);
	DataGrid datagridOfChangeList(TurnkeyOrder turnkeyOrder);
	Map<String,List<ToptionContent>> passOptionsByStr(String ipn_ipn);
	void completedProductOrderAndInvokeWebService(TurnkeyOrder turnkeyOrder);
}
