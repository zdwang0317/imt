package wzd.service;

import wzd.pageModel.tt.Tt;

public interface ITtService {
	public int dataResolve(); 
	public int passCpWip();
	public int passWaferWip();
	public int updateBjGooddie();
	public int updateHkGooddie();
	public int updateGooddie(Tt tt);
	public int returnGooddieOfPi(Tt tt);
	public void test();
	public int CancelShipTo(String num,String db, String string);
	public int deleteRepetitiveData();
	int dataResolveForPo();
	void helperOfWorkOrder();
	void PassDataResolveToChart();
	int passCpWipLatest();
}
