package kg.kg;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Excel{
	
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static MongoCollection<Document> mongoCollection;
	static {
        try {
			mongoClient = new MongoClient("192.168.1.207", 27017);
			mongoDatabase = mongoClient.getDatabase("xz_sys");
			mongoCollection = mongoDatabase.getCollection("websites");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<String []> values, HSSFWorkbook wb) {

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		// 声明列对象
		HSSFCell cell = null;

		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		// 创建内容
		for (int i = 0; i < values.size(); i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values.get(i).length; j++) {
				// 将内容按顺序赋给对应的列对象
				row.createCell(j).setCellValue(values.get(i)[j]);
			}
		}
		return wb;
	}

	public static void main(String[] args) throws Exception {
		// excel标题
		String[] title = { "名称", "性别" };

		// excel文件名
		String fileName = "学生信息表" + System.currentTimeMillis() + ".xls";

		// sheet名
		String sheetName = "学生信息表";
		List<String []> content = new ArrayList<>();
		
		MongoCursor<Document> mongoCursor = mongoCollection.find().iterator();
		List<JSONObject> list = new ArrayList<>();
		while (mongoCursor.hasNext()) {
			Document document = mongoCursor.next();
			JSONObject object = new JSONObject();
			object.put("name", document.getString("name"));
			object.put("url", document.getString("url"));
			list.add(object);
		}
		for (int i = 0; i < list.size(); i++) {
			String[] a = new String[2];
			
			JSONObject obj = list.get(i);
			a[0] = obj.get("name").toString();
			a[1] = obj.get("url").toString();
			content.add(i, a);
		}

		// 创建HSSFWorkbook
		HSSFWorkbook wb = Excel.getHSSFWorkbook("1", title, content, null);
		File file = new File("d:/a.xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);// 写文件
	}
}
