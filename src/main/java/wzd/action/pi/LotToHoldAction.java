package wzd.action.pi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import wzd.action.BaseAction;
import wzd.model.pi.TturnkeyOrderItemDetail;
import wzd.pageModel.Json;
import wzd.pageModel.pi.LotToHold;
import wzd.service.ILotToHold;
import wzd.service.IWipService;
import wzd.util.UtilValidate;
import wzd.util.WaferIdFormat;
import wzd.util.WebServiceUtil;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.logging.Logger;

@Namespace("/")
@Action(value = "lotToHoldAction")
public class LotToHoldAction extends BaseAction implements ModelDriven<LotToHold>{

	private LotToHold lotToHold = new LotToHold();
	
	@Override
	public LotToHold getModel() {
		// TODO Auto-generated method stub
		return lotToHold;
	}

	private ILotToHold lthService;

	public ILotToHold getLthService() {
		return lthService;
	}
	@Autowired
	public void setLthService(ILotToHold lthService) {
		this.lthService = lthService;
	}
	
	private IWipService wipService;
	
	public IWipService getWipService() {
		return wipService;
	}
	@Autowired
	public void setWipService(IWipService wipService) {
		this.wipService = wipService;
	}
	
	public void datagrid() {
		super.writeJson(lthService.datagridOfHoldLot(lotToHold));
	}
	
	public synchronized void addHoldLot(){
		lthService.addHoldLot(lotToHold);
		Json j = new Json();
		j.setMsg("添加成功");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public synchronized void deleteHoldLot(){
		lthService.deleteHoldLot(lotToHold);
		Json j = new Json();
		j.setMsg("删除成功!");
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void holdLotCompare() throws IOException{
		Map<String,String> mapOfHoldLot = new HashMap<String,String>();
		StringBuilder conditions = new StringBuilder();
		String fileName = lotToHold.getLotfileFileName();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(new File(lotToHold.getLotfile()));
			fos = new FileOutputStream("d:/"+fileName);
			int len;
	        byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fis.close();
			fos.close();
		}
		File file = new File("d:/"+fileName);
		FileInputStream fin = new FileInputStream(file);
		int typeLength = fileName.substring(fileName.indexOf(".")+1).length();
		if(typeLength>3){
			//excel 2007
			XSSFWorkbook xwb = new XSSFWorkbook(fin);
			XSSFSheet sheet = xwb.getSheetAt(0);
			XSSFRow row;   
			// 循环输出表格中的内容
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				String lid = row.getCell(2).toString().trim();
				String wid = row.getCell(3).toString().trim();
				if(lid.indexOf(".")>=0){
					lid = lid.substring(0, lid.indexOf("."));
				}
				conditions.append(" or lid like '%"+lid+"%'");
				if(UtilValidate.isNotEmpty(lid)){
					mapOfHoldLot.put(lid, wid);
				}
			}
		}else{
			//excel 2003
			POIFSFileSystem fs = new POIFSFileSystem(fin);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet wipSheet = wb.getSheetAt(0);
			for (int i = 1; i <= wipSheet.getLastRowNum(); i++) {
				HSSFRow currentRow = wipSheet.getRow(i);
				HSSFCell cell = currentRow.getCell(2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				HSSFCell cell2 = currentRow.getCell(3);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				String lid = cell.getRichStringCellValue().toString().trim();
				String wid = cell2.getRichStringCellValue().toString().trim();
				if (lid.indexOf(".") >= 0) {
					lid = lid.substring(0, lid.indexOf("."));
				}
				conditions.append(" or lid like '%" + lid + "%'");
				if (UtilValidate.isNotEmpty(lid)) {
					mapOfHoldLot.put(lid, wid);
				}
			}
		}
		file.delete();
		super.writeJson(lthService.holdLotCompare(mapOfHoldLot, conditions.toString()));
	}
	
	public void UploadDataForUpdateTpn() throws IOException{
		Map<String,String> mapOfUpdateData = new HashMap<String,String>();
		String fileName = lotToHold.getLotfileFileName();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(new File(lotToHold.getLotfile()));
			fos = new FileOutputStream("d:/"+fileName);
			int len;
	        byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fis.close();
			fos.close();
		}
		File file = new File("d:/"+fileName);
		FileInputStream fin = new FileInputStream(file);
		int typeLength = fileName.substring(fileName.indexOf(".")+1).length();
		Map<String,List<Map<String,String>>> mapOfInvokeService = new HashMap<String,List<Map<String,String>>>();
		if(typeLength>3){
			//excel 2007
			XSSFWorkbook xwb = new XSSFWorkbook(fin);
			XSSFSheet sheet = xwb.getSheetAt(0);
			XSSFRow row;   
			// 循环输出表格中的内容
			for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				String status = row.getCell(7).toString().trim();
				if(status.equals("COMPLETED")){
					String tpn = row.getCell(6).toString().trim();
					String ipn = row.getCell(5).toString().trim();
					String lid = row.getCell(0).toString().trim();
					String wid = row.getCell(1).toString().trim();
					if(UtilValidate.isNotEmpty(tpn)&&UtilValidate.isNotEmpty(ipn)){
						String key = ipn+"_"+tpn;
						Map<String,String> value = new HashMap<String,String>();
						List<Map<String,String>> list = mapOfInvokeService.get(key);
						if(UtilValidate.isEmpty(list)){
							list = new ArrayList<Map<String,String>>();
						}
						value.put("lid", lid);
						value.put("wid", wid);
						list.add(value);
						mapOfInvokeService.put(key, list);
					}
					if(UtilValidate.isNotEmpty(tpn)){
						if(UtilValidate.isNotEmpty(lid)){
							mapOfUpdateData.put(lid+"_"+wid, tpn);
						}
					}
				}
			}
		}else{
			//excel 2003
			POIFSFileSystem fs = new POIFSFileSystem(fin);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet wipSheet = wb.getSheetAt(0);
			for (int i = 4; i <= wipSheet.getLastRowNum(); i++) {
				HSSFRow currentRow = wipSheet.getRow(i);
				HSSFCell cell7 = currentRow.getCell(7);
				if(null!=cell7){
					cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
					String status = cell7.getRichStringCellValue().toString().trim();
					if(status.equals("COMPLETED")){
						HSSFCell cell6 = currentRow.getCell(6);
						cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
						String tpn = cell6.getRichStringCellValue().toString().trim();
						HSSFCell cell5 = currentRow.getCell(5);
						cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
						String ipn = cell5.getRichStringCellValue().toString().trim();
						HSSFCell cell = currentRow.getCell(0);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						HSSFCell cell2 = currentRow.getCell(1);
						cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
					    String lid = cell.getRichStringCellValue().toString().trim();
					    String wid = cell2.getRichStringCellValue().toString().trim();
						if(UtilValidate.isNotEmpty(tpn)&&UtilValidate.isNotEmpty(ipn)){
							String key = ipn+"_"+tpn;
							Map<String,String> value = new HashMap<String,String>();
							List<Map<String,String>> list = mapOfInvokeService.get(key);
							if(UtilValidate.isEmpty(list)){
								list = new ArrayList<Map<String,String>>();
							}
							value.put("lid", lid);
							value.put("wid", wid);
							list.add(value);
							mapOfInvokeService.put(key, list);
						}
						if(UtilValidate.isNotEmpty(tpn)){
						    if(UtilValidate.isNotEmpty(lid)){
						    	mapOfUpdateData.put(lid+"_"+wid, tpn);
							}
						}
					}
				}else{
					break;
				}
			}
		}
		file.delete();
		int rows = wipService.UploadDataForUpdateTpn(mapOfUpdateData);
		for(Map.Entry<String,List<Map<String,String>>> entry: mapOfInvokeService.entrySet()) {
			Object[] objs = new Object[3];
			objs[0] = entry.getKey().split("_")[0];
			objs[1] = entry.getKey().split("_")[1];
			List<Map<String,String>> listForUpdate = new ArrayList<Map<String,String>>();
			for (Map<String,String> obj : entry.getValue()) {
				Map<String, String> mapForUpdate = new HashMap<String, String>();
				mapForUpdate.put("productId", "");
				mapForUpdate.put("lotId", WaferIdFormat.getMainLot(obj.get("lid")));
				mapForUpdate.put("waferId", obj.get("wid"));
				listForUpdate.add(mapForUpdate);
			}
			objs[2] = listForUpdate;
			String result = WebServiceUtil
					.invokeWebService(
							"http://192.168.15.24:8080/productInformation/services/WebService",
							"setTpnIpn", objs);
		}
		
		Json j = new Json();
		j.setMsg("更新完成!更新行数："+rows);
		j.setSuccess(true);
		super.writeJson(j);
	}
	
	public void ReplaceBaoFeiWip() throws IOException{
		String fileName = lotToHold.getLotfileFileName();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(new File(lotToHold.getLotfile()));
			fos = new FileOutputStream("d:/"+fileName);
			int len;
	        byte[] buffer = new byte[1024];
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fis.close();
			fos.close();
		}
		File file = new File("d:/"+fileName);
		FileInputStream fin = new FileInputStream(file);
		//excel 2007
		XSSFWorkbook xwb = new XSSFWorkbook(fin);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row;   
		// 循环输出表格中的内容
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			Map<String,Object> record = new HashMap<String,Object>();
			String id = row.getCell(0).toString().trim().replace(".0", "");
			String type = row.getCell(1).toString().trim();
			String qty = row.getCell(2).toString().trim().replace(".0", "");
			record.put("id", id);
			record.put("type", type);
			record.put("qty", qty);
			list.add(record);
		}
		file.delete();
		int rows = wipService.ReplaceBaoFeiWip(list);
		Json j = new Json();
		j.setMsg("更新完成!更新行数："+rows);
		j.setSuccess(true);
		super.writeJson(j);
	}
}
/**/