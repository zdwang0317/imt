package wzd.service;

import java.util.Map;

import wzd.pageModel.DataGrid;
import wzd.pageModel.pi.LotToHold;

public interface ILotToHold {
	DataGrid datagridOfHoldLot(LotToHold lotToHold);
	int addHoldLot(LotToHold lotToHold);
	void deleteHoldLot(LotToHold lotToHold);
	DataGrid holdLotCompare(Map<String,String> mapOfHoldLot,String conditions);
}
